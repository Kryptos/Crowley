package net.hybridcore.crowley.habbo.messages.outgoing.messenger;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class InstantMessage implements Runnable {
    private GameSession gameSession;
    private int target;
    private String message;

    public InstantMessage(GameSession gameSession, int target, String message) {
        this.gameSession = gameSession;
        this.target = target;
        this.message = message;
    }
    
    public void run() {
        GameSession targetSession = Crowley.getHabbo().getSessions().getSessionByHabboId(this.target);

        Habbo habbo = this.gameSession.getHabbo();
        Habbo friend = targetSession.getHabbo();

        if (friend.getFriends().contains(habbo)) {
            targetSession.sendMessage(
                    new ServerMessage(134)
                    .append(habbo.getId().intValue())
                    .appendString(message)
            );
        }
    }
}
