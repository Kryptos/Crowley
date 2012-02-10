package styx.habbo.game;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SessionManager {
    private static final Logger logger = Logger.getLogger(SessionManager.class.getName());
    
    private Map<Channel, GameSession> sessions = new HashMap<Channel, GameSession>();
    private int clientCount = 0;
    
    public void addConnection(Channel channel) {
        GameSession gameSession = new GameSession(channel, ++clientCount);
        this.sessions.put(channel, gameSession);

        gameSession.start();
        logger.info("Accepted gameSession (id: " + gameSession.getID() + " ip: "+ gameSession.getIP() + ")");
    }
    
    public GameSession getSession(Channel channel) {
        if (this.sessions.containsKey(channel)) {
            return this.sessions.get(channel);
        }

        return null;
    }
}
