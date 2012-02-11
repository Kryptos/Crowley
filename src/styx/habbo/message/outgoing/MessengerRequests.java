package styx.habbo.message.outgoing;

import styx.habbo.game.GameSession;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerRequests implements Runnable {
    private GameSession gameSession;

    public MessengerRequests(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void run() {
        //TODO: Friend requests
    }
}
