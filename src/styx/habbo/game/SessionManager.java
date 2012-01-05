package styx.habbo.game;

import org.jboss.netty.channel.Channel;

import org.apache.log4j.Logger;

import java.util.Map;
import java.util.HashMap;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SessionManager {
    private static final Logger logger = Logger.getLogger(SessionManager.class.getName());
    
    private Map<Channel, Session> sessions = new HashMap<Channel, Session>();
    private int clientCount = 0;
    
    public void addConnection(Channel channel) {
        Session session = new Session(channel, ++clientCount);
        this.sessions.put(channel, session);

        session.start();
        logger.info("Accepted session (id: " + session.getID() + " ip: "+ session.getIP() + ")");
    }
    
    public Session getSession(Channel channel) {
        if (this.sessions.containsKey(channel)) {
            return this.sessions.get(channel);
        }

        return null;
    }
}
