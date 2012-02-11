package styx.habbo.message.outgoing;

import styx.habbo.beans.Habbo;
import styx.habbo.game.GameSession;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.IncomingMessage;
import styx.habbo.message.ServerMessage;

public class UserProperties implements Runnable {
    private GameSession gameSession;

    public UserProperties(GameSession gameSession) {
        this.gameSession = gameSession;
    }

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
                        .append(0) //respect
                        .append(0) // respect to give away
                        .append(0) // daily pet respect points
        );
    }
}