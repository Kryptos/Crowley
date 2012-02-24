package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Ban;
import net.hybridcore.crowley.habbo.beans.Fuseright;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ServerMessage;
import net.hybridcore.crowley.util.DatastoreUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class LoginHabbo implements Runnable {
    private static Logger logger = Logger.getLogger(LoginHabbo.class.getName());

    private GameSession gameSession;
    private String ssoTicket;

    public LoginHabbo(GameSession gameSession, String ssoTicket) {
        this.gameSession = gameSession;
        this.ssoTicket = ssoTicket;
    }

    public void run() {
        Session session = DatastoreUtil.currentSession();

        Habbo habbo = (Habbo)session.createCriteria(Habbo.class).add(Restrictions.eq("ssoTicket", this.ssoTicket)).uniqueResult();

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
                this.gameSession.sendMessage(
                        new ServerMessage(35)
                        .appendString("You have been banned from the hotel: ", 13)
                        .appendString(ban.getReason(), 13)
                        .appendString("This ban will expire on " + (new SimpleDateFormat("dd-MM-yyyy")).format(ban.getExpires()))
                );

                this.gameSession.getChannel().disconnect();
                return;
            }
        }

        this.gameSession.setHabbo(habbo);

        Crowley.getHabbo().getSessions().markOnline(this.gameSession);

        Set<Fuseright> rights = habbo.getFuserank().getRights();

        ServerMessage serverMessage = new ServerMessage(2);
        serverMessage.append(rights.size());
        
        boolean isMod = false;
        for (Fuseright right : rights) {
            if (right.getRight().equals("fuse_mod")) {
                isMod = true;
            }

            serverMessage.appendString(right.getRight());
        }

        this.gameSession.sendMessage(serverMessage);

        if (isMod) {
            //TODO: Show mod tools
        }

        //TODO: Send effects inventory

        this.gameSession.sendMessage(
                new ServerMessage(290)
                .append(true)
                .append(false)
        );

        this.gameSession.sendMessage(
                new ServerMessage(3)
        );

        this.gameSession.sendMessage(
                new ServerMessage(517)
                .append(true)
        );

        //TODO: Update pixels
        //TODO: Home room
        //TODO: Favourite rooms

        this.gameSession.getMessageHandler().unregisterLoginHandlers();
        this.gameSession.getMessageHandler().registerMessenger();
        this.gameSession.getMessageHandler().registerUser();
    }
}
