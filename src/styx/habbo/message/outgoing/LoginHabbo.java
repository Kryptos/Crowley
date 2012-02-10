package styx.habbo.message.outgoing;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import styx.habbo.beans.Habbo;
import styx.habbo.game.GameSession;
import styx.habbo.message.ServerMessage;
import styx.util.DatastoreUtil;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class LoginHabbo implements Runnable {
    private static Logger logger = Logger.getLogger(LoginHabbo.class.getName());

    private GameSession networkGameSession;
    private String ssoTicket;

    public LoginHabbo(GameSession networkGameSession, String ssoTicket) {
        this.networkGameSession = networkGameSession;
        this.ssoTicket = ssoTicket;
    }

    public void run() {
        Session session = DatastoreUtil.currentSession();

        Habbo habbo = (Habbo)session.createCriteria(Habbo.class).add(Restrictions.eq("ssoTicket", this.ssoTicket)).uniqueResult();

        // Invalid login ticket :o
        if (habbo == null) {
            this.networkGameSession.sendMessage(
                    new ServerMessage(161)
                    .appendString("Invalid login ticket, refresh the client and try again.")
            );

            this.networkGameSession.getChannel().close();
            return;
        }
        //TODO: Check if ticket has expired and the IP matches the current clients
        //TODO: Check for bans
        //TODO: Send fuse rights
        /*
        msg-id 2
        wired int count

        string right
         */

        //TODO: Show mod tools if has fuse_mod
        //TODO: Send effects inventory

        this.networkGameSession.getMessageHandler().unregisterLoginHandlers();
        //TODO: Register other handlers
    }
}
