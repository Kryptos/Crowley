package net.hybridcore.crowley.habbo.messages.incoming.security;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class InitCryptoMessageEvent implements IncomingMessage {
    public void handle(GameSession gameSession, ClientMessage message) {
        gameSession.sendMessage(
                new ServerMessage(277)
                .append("576b145a0c17f8a385971e0b6324a4bc")
        );
    }
}
