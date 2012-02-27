package net.hybridcore.crowley.habbo.messages.outgoing.users;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class ScrSendUserInfoComposer extends OutgoingMessage {
    public ScrSendUserInfoComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        //TODO: Not static data
        this.gameSession.sendMessage(
                new ServerMessage(7)
                .appendString("habbo_club")
                .append(0) // days left this month
                .append(false) // expired / active
                .append(0) // months remaining
                .append(2) // BoughtFromCato ? 2 : 1
                .append(1) // Unknown (gift points?)
                .append(false) // is vip
                .append(0) // past hc days
                .append(0) // past vip days
                .append(false) // discount messages
                .append(30) // regular price
                .append(25) // your price
        );
    }
}
