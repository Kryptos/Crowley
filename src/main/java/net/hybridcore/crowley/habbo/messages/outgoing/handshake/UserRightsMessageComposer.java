package net.hybridcore.crowley.habbo.messages.outgoing.handshake;

import net.hybridcore.crowley.habbo.beans.Fuseright;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserRightsMessageComposer extends OutgoingMessage {
    private Set<Fuseright> rights;
    public UserRightsMessageComposer(GameSession gameSession, Set<Fuseright> rights) {
        super(gameSession);
        this.rights = rights;
    }

    @Override
    public void run() {
        ServerMessage serverMessage = new ServerMessage(2);
        serverMessage.append(rights.size());

        for (Fuseright right : rights) {
            if (right.getRight().equals("fuse_mod")) {
                this.gameSession.getHabbo().isModerator(true);
            }

            serverMessage.appendString(right.getRight());
        }

        this.gameSession.sendMessage(serverMessage);
    }
}
