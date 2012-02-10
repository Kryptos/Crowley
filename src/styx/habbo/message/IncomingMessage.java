package styx.habbo.message;

import styx.habbo.game.GameSession;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public interface IncomingMessage {
    public void handle(GameSession gameSession, ClientMessage message);
}
