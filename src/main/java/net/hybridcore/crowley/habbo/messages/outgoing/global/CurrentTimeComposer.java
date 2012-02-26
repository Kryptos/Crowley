package net.hybridcore.crowley.habbo.messages.outgoing.global;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.DateTime;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Jordan M.
 */
public class CurrentTimeComposer extends OutgoingMessage {

    public CurrentTimeComposer(GameSession gameSession, ClientMessage clientMessage) {
        super(gameSession, clientMessage);
    }

    @Override
    public void run() {
        this.gameSession.sendMessage(
                new ServerMessage(163)
                .appendString(DateTime.now())
        );
    }
}
