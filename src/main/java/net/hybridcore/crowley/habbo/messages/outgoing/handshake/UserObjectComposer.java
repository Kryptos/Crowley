package net.hybridcore.crowley.habbo.messages.outgoing.handshake;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserObjectComposer extends OutgoingMessage {
    public UserObjectComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        this.gameSession.sendMessage(
                new ServerMessage(5)
                .appendString(habbo.getId().toString())
                .appendString(habbo.getName())
                .appendString(habbo.getFigure())
                .appendString(habbo.getGender().toUpperCase())
                .appendString(habbo.getMotto())
                .appendString(habbo.getRealName())
                .append(0)
                .appendString("")
                .append(0)
                .append(0)
                .append(0) //TODO: respect
                .append(0) //TODO: respect to give away
                .append(0) //TODO: daily pet respect points
        );
    }
}
