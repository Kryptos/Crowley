package net.hybridcore.crowley.habbo.messages.outgoing.messenger;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Jordan M.
 */
public class MessengerInitComposer extends OutgoingMessage {
    public MessengerInitComposer(GameSession gameSession, ClientMessage clientMessage) {
        super(gameSession, clientMessage);
    }

    @Override
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
