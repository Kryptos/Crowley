package net.hybridcore.crowley.habbo.messages.outgoing.user;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

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