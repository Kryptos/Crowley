package net.hybridcore.crowley.habbo.game;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.messages.outgoing.messenger.MessengerUpdate;
import net.hybridcore.crowley.util.DatastoreUtil;
import net.hybridcore.crowley.util.DateTime;
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
        return this.getSessionByHabboId(habbo.getId().intValue());
    }
    
    public GameSession getSessionByHabboId(Integer id) {
        if (this.habbos.containsKey(id.longValue())) {
            return this.habbos.get(id.longValue());
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
        Habbo habbo = gameSession.getHabbo();

        if (this.habbos.containsKey(habbo.getId())) {
            updateMessenger(gameSession.getHabbo());
            habbo.setLastOnline(DateTime.now());
            DatastoreUtil.currentSession().update(habbo);
        }
    }

    private void updateMessenger(Habbo habbo) {
        // guess we need to alert their friends were here!
        for (Habbo friend : habbo.getFriends()) {
            if (this.isOnline(friend.getId())) {
                friend.friendRequiresUpdate(habbo.getId().intValue());
                Crowley.getExecutorService().execute(new MessengerUpdate(this.getSession(friend)));
            }
        }
    }

    public boolean isOnline(Long id) {
        return this.habbos.containsKey(id);
    }
}
