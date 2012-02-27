package net.hybridcore.crowley.habbo.messages.outgoing.availability;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class AvailabilityStatusMessageComposer extends OutgoingMessage {
    public AvailabilityStatusMessageComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        this.gameSession.sendMessage(
                new ServerMessage(290)
                .append(true)
                .append(false)
        );
    }
}
