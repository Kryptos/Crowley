package styx.habbo.message.incoming;

import org.hibernate.criterion.Restrictions;
import styx.Crowley;
import styx.habbo.beans.User;
import styx.habbo.game.Session;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.HabboMessage;
import styx.habbo.message.ServerMessage;

import java.util.regex.Pattern;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class ApproveName implements HabboMessage {
    private boolean nameValid(String name) {
        if (name.length() > 15) {
            return false;
        }

        if (! Pattern.compile("^[a-zA-Z0-9-+=?!@:.,$]+$").matcher(name).find()) {
            return false;
        }
        
        if (Pattern.compile("^MOD-").matcher(name).find() ||
                Pattern.compile("^ADM-").matcher(name).find() ||
                Pattern.compile("^SOS-").matcher(name).find()) {
            return false;
        }

        return true;
    }

    private int statusCode(String name) {
        
        if (! this.nameValid(name)) {
            return 2;
        } else {
            org.hibernate.Session hs = Crowley.getDatastore().openSession();
            hs.beginTransaction();

            User user = (User)hs.createCriteria(User.class).add(Restrictions.eq("name", name)).uniqueResult();

            hs.getTransaction().commit();

            if (user != null) {
                return 4;
            }

            return 0;
        }
    }
    public void handle(Session session, ClientMessage message) {
        String name = message.readString();
        boolean pet = message.readBoolean();

        session.sendMessage(
                new ServerMessage(36)
                        .append(
                                this.statusCode(
                                        name
                                )
                        )
        );
    }
}
