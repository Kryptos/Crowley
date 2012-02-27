package net.hybridcore.crowley.habbo.messages.outgoing.notifications;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.DatastoreUtil;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class HabboActivityPointNotificationComposer extends OutgoingMessage {
    private int addPoints;

    public HabboActivityPointNotificationComposer(GameSession gameSession, int addPoints) {
        super(gameSession);
        this.addPoints = addPoints;
    }

    @Override
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        this.gameSession.sendMessage(
                new ServerMessage(438)
                .append(habbo.getActivityPoints())
                .append(this.addPoints)
        );

        if (this.addPoints > 0) {
            habbo.setActivityPoints((habbo.getActivityPoints() + addPoints));
            DatastoreUtil.currentSession().update(habbo);
        }
    }
}
