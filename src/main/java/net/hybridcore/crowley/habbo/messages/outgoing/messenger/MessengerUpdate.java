package net.hybridcore.crowley.habbo.messages.outgoing.messenger;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerUpdate implements Runnable {
    private GameSession gameSession;

    public MessengerUpdate(GameSession gameSession) {
        this.gameSession = gameSession;
    }
    
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        List<Integer> updates = habbo.getFriendUpdates();
        List<Habbo> friends = new ArrayList<Habbo>(updates.size());
        
        for (Integer friendId : updates) {
            GameSession friend = Crowley.getHabbo().getSessions().getSessionByHabboId(friendId);

            if (friend != null && friend.getHabbo() != null) {
                friends.add(friend.getHabbo());
            }
        }

        ServerMessage serverMessage = (
                new ServerMessage(13)
                        .append(0)
                        .append(friends.size()) // update count
                        .append(0)
        );

        for (Habbo friend : friends) {
            friend.serializeMessenger(serverMessage);
            habbo.friendUpdated(friend.getId().intValue());
        }

        this.gameSession.sendMessage(serverMessage);
    }
}
