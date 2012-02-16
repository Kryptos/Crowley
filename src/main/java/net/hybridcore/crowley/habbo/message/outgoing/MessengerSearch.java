package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ServerMessage;
import net.hybridcore.crowley.util.DatastoreUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerSearch implements Runnable {
    private GameSession gameSession;
    private String search;
    
    public MessengerSearch(GameSession gameSession, String search) {
        this.gameSession = gameSession;
        this.search = search;
    }
    
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();
        Session session = DatastoreUtil.currentSession();

        List results = session.createCriteria(Habbo.class)
                .add(
                        Restrictions.like("name", "%" + this.search + "%").ignoreCase()
                ).add(
                        Restrictions.ne("id", habbo.getId())
                ).setMaxResults(25).list();
        
        List<Habbo> friends = new ArrayList<Habbo>();
        List<Habbo> others = new ArrayList<Habbo>();
        
        for (Object result : results) {
            if (result instanceof Habbo) {
                boolean resultFriend = false;

                for (Habbo friend : habbo.getFriends()) {
                    if (((Habbo) result).getId() == friend.getId()) {
                        resultFriend = true;
                        break;
                    }
                }

                if (resultFriend) {
                    friends.add((Habbo)result);
                } else {
                    others.add((Habbo)result);
                }
            }
        }

        ServerMessage serverMessage = new ServerMessage(435);
        
        serverMessage.append(friends.size());
        
        for (Habbo friend : friends) {
            friend.serializeMessenger(serverMessage, true);
        }
        
        serverMessage.append(others.size());
        
        for (Habbo other : others) {
            other.serializeMessenger(serverMessage, true);
        }

        this.gameSession.sendMessage(serverMessage);
    }
}
