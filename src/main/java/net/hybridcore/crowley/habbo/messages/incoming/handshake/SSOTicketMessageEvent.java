package net.hybridcore.crowley.habbo.messages.incoming.handshake;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Ban;
import net.hybridcore.crowley.habbo.beans.Fuseright;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;
import net.hybridcore.crowley.habbo.messages.outgoing.availability.AvailabilityStatusMessageComposer;
import net.hybridcore.crowley.habbo.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import net.hybridcore.crowley.habbo.messages.outgoing.handshake.UserRightsMessageComposer;
import net.hybridcore.crowley.habbo.messages.outgoing.moderation.UserBannedComposer;
import net.hybridcore.crowley.habbo.messages.outgoing.notifications.InfoFeedEnableComposer;
import net.hybridcore.crowley.util.DatastoreUtil;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SSOTicketMessageEvent implements IncomingMessage, Runnable {
    private GameSession gameSession;
    private ClientMessage clientMessage;

    public void handle(GameSession gameSession, ClientMessage message) {
        this.gameSession = gameSession;
        this.clientMessage = message;

        Crowley.getExecutorService().execute(this);
    }

    public void run() {
        Session session = DatastoreUtil.currentSession();

        Habbo habbo = (Habbo)session.createCriteria(Habbo.class).add(Restrictions.eq("ssoTicket", this.clientMessage.readString())).uniqueResult();

        // Invalid login ticket :o
        if (habbo == null) {
            this.gameSession.sendAlert("Invalid login ticket, refresh the client and try again.");
            this.gameSession.getChannel().close();
            return;
        }

        Date now = GregorianCalendar.getInstance().getTime();
        if (! habbo.getSsoIp().equals(this.gameSession.getIP()) || now.after(habbo.getSsoExpires())) {
            this.gameSession.sendAlert("Invalid login ticket, refresh the client and try again.");
            this.gameSession.getChannel().disconnect();
            return;
        }

        // Nullify login ticket
        if (! Crowley.DEBUG) {
            habbo.setSsoTicket(null);
            habbo.setSsoExpires(now);
            session.update(habbo);
        }

        for (Ban ban : habbo.getBans()) {
            if (ban.getExpires().after(now)) {
                Crowley.getExecutorService().execute(new UserBannedComposer(this.gameSession, ban));
                return;
            }
        }

        this.gameSession.setHabbo(habbo);
        Crowley.getHabbo().getSessions().markOnline(this.gameSession);

        Set<Fuseright> rights = habbo.getFuserank().getRights();
        Crowley.getExecutorService().execute(new UserRightsMessageComposer(this.gameSession, rights));

        //TODO: Send effects inventory

        Crowley.getExecutorService().execute(new AvailabilityStatusMessageComposer(this.gameSession));
        Crowley.getExecutorService().execute(new InfoFeedEnableComposer(this.gameSession));
        Crowley.getExecutorService().execute(new AuthenticationOKMessageComposer(this.gameSession));

        this.gameSession.getMessageHandler().unregisterLoginHandlers();
        this.gameSession.getMessageHandler().registerMessenger();
        this.gameSession.getMessageHandler().registerUser();
    }
}
