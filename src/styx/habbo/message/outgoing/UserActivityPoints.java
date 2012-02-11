package styx.habbo.message.outgoing;

import styx.habbo.beans.Habbo;
import styx.habbo.game.GameSession;
import styx.habbo.message.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserActivityPoints implements Runnable {
    private GameSession gameSession;
    private int addPoints;

    public UserActivityPoints(GameSession gameSession, int addPoints) {
        this.gameSession = gameSession;
        this.addPoints = addPoints;
    }

    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        this.gameSession.sendMessage(
                new ServerMessage(438)
                .append(habbo.getActivityPoints())
                .append(this.addPoints)
        );

        if (this.addPoints > 0) {
            habbo.setActivityPoints((habbo.getActivityPoints() + addPoints));
        }
    }
}
