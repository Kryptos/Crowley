package net.hybridcore.crowley.habbo.game;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.message.ServerMessage;

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
    private Map<Long, GameSession> habbos = new HashMap<Long, GameSession>();
    private int clientCount = 0;
    
    public GameSession addConnection(Channel channel) {
        GameSession gameSession = new GameSession(channel, ++clientCount);
        this.sessions.put(channel, gameSession);

        gameSession.start();
        logger.info("Accepted session (id: " + gameSession.getID() + " ip: "+ gameSession.getIP() + ")");

        return gameSession;
    }
    
    public void removeConnection(Channel channel) {
        GameSession gameSession = this.getSession(channel);
        this.sessions.remove(channel);

        if (gameSession.getHabbo() != null) {
            this.markOffline(gameSession);
        }

        logger.info("Closed session (id: " + gameSession.getID() + " ip: " + gameSession.getIP() + ")");
    }

    public GameSession getSession(Channel channel) {
        if (this.sessions.containsKey(channel)) {
            return this.sessions.get(channel);
        }

        return null;
    }
    
    public GameSession getSession(Habbo habbo) {
        if (this.habbos.containsKey(habbo.getId())) {
            return this.habbos.get(habbo.getId());
        }

        return null;
    }
    
    public void markOnline(GameSession gameSession) {
        if (this.habbos.containsKey(gameSession.getHabbo().getId())) {
            // Wtf already online...
            GameSession session = this.habbos.get(gameSession.getHabbo().getId());
            session.getChannel().disconnect();
        } else {
            this.habbos.put(gameSession.getHabbo().getId(), gameSession);
            updateMessenger(gameSession.getHabbo());
        }
    }

    public void markOffline(GameSession gameSession) {
        if (this.habbos.containsKey(gameSession.getHabbo().getId())) {
            updateMessenger(gameSession.getHabbo());
        }
    }

    private void updateMessenger(Habbo habbo) {
        // guess we need to alert their friends were here!
        for (Habbo friend : habbo.getFriends()) {
            if  (this.isOnline(friend.getId())) {
                GameSession gameSession = this.getSession(friend);

                gameSession.sendMessage(
                        new ServerMessage(13)
                                .append(0)
                                .append(1) // update count
                                .append(0)
                                .append(habbo.getId().intValue())
                                .appendString(habbo.getName())
                                .append(true)
                                .append(habbo.isOnline())
                                .append(false) //TODO: In Room?
                                .appendString(habbo.getFigure())
                                .append(false)
                                .appendString(habbo.getMotto())
                                .append("") //TODO: Last online?
                                .appendString(habbo.getRealName())
                );
            }
        }
    }

    public boolean isOnline(Long id) {
        return this.habbos.containsKey(id);
    }
}
