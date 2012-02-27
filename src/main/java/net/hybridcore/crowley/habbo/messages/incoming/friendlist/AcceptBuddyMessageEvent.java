package net.hybridcore.crowley.habbo.messages.incoming.friendlist;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;
import net.hybridcore.crowley.habbo.messages.outgoing.friendlist.FriendListUpdateComposer;
import net.hybridcore.crowley.util.DatastoreUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class AcceptBuddyMessageEvent implements IncomingMessage, Runnable {
    private GameSession gameSession;
    private ClientMessage clientMessage;

    public void handle(GameSession gameSession, ClientMessage clientMessage) {
        this.gameSession = gameSession;
        this.clientMessage = clientMessage;

        Crowley.getExecutorService().execute(this);
        //..
        // int count [id, id, ...]
        // check request exists
        // add to habbo's friend list
        // mark as updated
        // force update
        // delete request from db
    }
    
    public void run() {
        Session session = DatastoreUtil.currentSession();
        int count = this.clientMessage.readInt();

        for (int i = 0; i < count; i++) {
            int habboId = this.clientMessage.readInt();
            Habbo buddy = (Habbo)session.createCriteria(Habbo.class).add(Restrictions.idEq((long)habboId)).uniqueResult();

            // Invalid id, the fuck? We should probably delete this request at some point
            if (buddy == null) {
                break;
            }

            boolean valid = false;
            
            for (Habbo habbo : this.gameSession.getHabbo().getFriendRequests()) {
                if (habbo.getId().equals((long)habboId)) {
                    valid = true;
                }
            }
            
            // Somebody is trying to add friends who didn't request it!
            if (! valid) {
                break;
            }

            if (buddy.isOnline()) {
                // fetch active session
                GameSession target = Crowley.getHabbo().getSessions().getSessionByHabboId(buddy.getId());
                buddy = target.getHabbo();

                buddy = (Habbo)session.merge(buddy);
                buddy.friendRequiresUpdate(this.gameSession.getHabbo().getId());
                session.saveOrUpdate(buddy);

                Crowley.getExecutorService().execute(new FriendListUpdateComposer(target));
            }

            this.gameSession.setHabbo((Habbo)session.merge(this.gameSession.getHabbo()));
            this.gameSession.getHabbo().addFriend(buddy);
            this.gameSession.getHabbo().friendRequiresUpdate(buddy.getId());

            session.saveOrUpdate(this.gameSession.getHabbo());

            Crowley.getExecutorService().execute(new FriendListUpdateComposer(this.gameSession));
        }
    }
}
