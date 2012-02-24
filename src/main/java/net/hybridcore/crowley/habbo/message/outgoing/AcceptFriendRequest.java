package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.util.DatastoreUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Jordan M.
 */
public class AcceptFriendRequest implements Runnable {
    private GameSession gameSession;
    private String name;

    public AcceptFriendRequest(GameSession gameSession, String name) {
        this.gameSession = gameSession;
        this.name = name;
    }

    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        if (this.name == null || this.name.isEmpty()) {
            return; // duff name
        }

// Should be handled by habbo.addFriend
//        for (Habbo friend : habbo.getFriends()) {
//            if (friend.getName().equals(this.name)) {
//                return; // already friends
//            }
//        }
        
        Session session = DatastoreUtil.currentSession();
        
        Habbo friend = (Habbo)session.createCriteria(Habbo.class).add(Restrictions.eq("name", this.name)).uniqueResult();

        if (friend == null) {
            return; // not found
        }

        habbo.addFriend(friend);
        session.update(habbo);
    }
}
