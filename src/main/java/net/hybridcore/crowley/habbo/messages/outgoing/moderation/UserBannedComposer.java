package net.hybridcore.crowley.habbo.messages.outgoing.moderation;

import net.hybridcore.crowley.habbo.beans.Ban;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

import java.text.SimpleDateFormat;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserBannedComposer extends OutgoingMessage {
    private Ban ban;

    public UserBannedComposer(GameSession gameSession, Ban ban) {
        super(gameSession);
        this.ban = ban;
    }

    @Override
    public void run() {
        this.gameSession.sendMessage(
                new ServerMessage(35)
                .appendString("You have been banned from the hotel: ", 13)
                .appendString(this.ban.getReason(), 13)
                .appendString("This ban will expire on " + (new SimpleDateFormat("dd-MM-yyyy")).format(this.ban.getExpires()))
        );

        this.gameSession.getChannel().disconnect();
    }
}
