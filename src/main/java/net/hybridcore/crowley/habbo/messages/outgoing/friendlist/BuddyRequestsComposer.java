package net.hybridcore.crowley.habbo.messages.outgoing.friendlist;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.DatastoreUtil;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class BuddyRequestsComposer extends OutgoingMessage {
    public BuddyRequestsComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        ServerMessage serverMessage = new ServerMessage(314);

        Set<Habbo> requests = habbo.getFriendRequests();

        serverMessage.append(requests.size());
        serverMessage.append(requests.size());

        for (Habbo request : requests) {
            serverMessage.append(request.getId().intValue());
            serverMessage.appendString(request.getName());
            serverMessage.appendString(request.getFigure());
        }

        this.gameSession.sendMessage(serverMessage);
    }
}
