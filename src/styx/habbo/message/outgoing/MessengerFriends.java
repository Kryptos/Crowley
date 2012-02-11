package styx.habbo.message.outgoing;

import styx.habbo.beans.Habbo;
import styx.habbo.game.GameSession;
import styx.habbo.message.ServerMessage;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerFriends implements Runnable {
    private GameSession networkGameSession;

    public MessengerFriends(GameSession networkGameSession) {
        this.networkGameSession = networkGameSession;
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

        Set<Habbo> friends = this.networkGameSession.getHabbo().getFriends();
        
        serverMessage.append(friends.size());
        
        for (Habbo friend : friends) {
            serverMessage.append(friend.getId().intValue());
            serverMessage.appendString(friend.getName());
            serverMessage.append(true);
            serverMessage.append(false); //TODO: Online?
            serverMessage.append(false); //TODO: In Room?
            serverMessage.appendString(friend.getFigure());
            serverMessage.append(false);
            serverMessage.appendString(friend.getMotto());
            serverMessage.appendString(""); //TODO: Last Online?
            serverMessage.appendString(friend.getRealName());
        }

        this.networkGameSession.sendMessage(serverMessage);
    }
}
