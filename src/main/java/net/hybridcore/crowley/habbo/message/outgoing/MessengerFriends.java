package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ServerMessage;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerFriends implements Runnable {
    private GameSession gameSession;

    public MessengerFriends(GameSession networkGameSession) {
        this.gameSession = networkGameSession;
    }
    
    public void run() {
        ServerMessage serverMessage = 
                (new ServerMessage(12)
                        .append(600)
                        .append(200)
                        .append(600)
                        .append(900)
                        .append(false)
                );

        Set<Habbo> friends = this.gameSession.getHabbo().getFriends();
        
        serverMessage.append(friends.size());
        
        for (Habbo friend : friends) {
            friend.serializeMessenger(serverMessage);
        }

        this.gameSession.sendMessage(serverMessage);
    }
}
