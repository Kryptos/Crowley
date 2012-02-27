package net.hybridcore.crowley.habbo.messages;

import net.hybridcore.crowley.habbo.game.GameSession;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class OutgoingMessage implements Runnable {
    protected GameSession gameSession;
    protected ClientMessage clientMessage;

    public OutgoingMessage(GameSession gameSession) {}

    public OutgoingMessage(GameSession gameSession, ClientMessage clientMessage) {
        this.gameSession = gameSession;
        this.clientMessage = clientMessage;
    }
    
    public void run() {
        // Stub
    }
}
