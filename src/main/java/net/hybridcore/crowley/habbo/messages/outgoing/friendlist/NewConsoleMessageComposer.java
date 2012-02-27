package net.hybridcore.crowley.habbo.messages.outgoing.friendlist;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.habbo.security.UserInputFilter;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class NewConsoleMessageComposer extends OutgoingMessage {
    public NewConsoleMessageComposer(GameSession gameSession, ClientMessage clientMessage) {
        super(gameSession, clientMessage);
    }

    @Override
    public void run() {
        GameSession target = Crowley.getHabbo().getSessions().getSessionByHabboId((long)this.clientMessage.readInt());
        
        if (target == null || target.getHabbo() == null) {
            return;
        }
        
        boolean friends = false;
        
        for (Habbo habbo : target.getHabbo().getFriends()) {
            if (habbo.getId().equals(this.gameSession.getHabbo().getId())) {
                friends = true;
                break;
            }
        }

        if (friends) {
            String message = UserInputFilter.filterString(this.clientMessage.readString());
            //TODO: Log message

            target.sendMessage(
                    new ServerMessage(134)
                    .append(this.gameSession.getHabbo().getId().intValue())
                    .appendString(message)
            );
        }
    }
}
