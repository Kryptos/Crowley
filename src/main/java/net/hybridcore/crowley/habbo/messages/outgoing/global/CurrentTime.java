package net.hybridcore.crowley.habbo.messages.outgoing.global;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.DateTime;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class CurrentTime implements Runnable {
    private GameSession gameSession;

    public CurrentTime(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void run() {
        this.gameSession.sendMessage(
                new ServerMessage(163)
                        .append(
                                DateTime.now()
                        )
        );
    }
}
