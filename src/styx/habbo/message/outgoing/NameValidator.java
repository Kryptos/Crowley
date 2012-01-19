package styx.habbo.message.outgoing;

import org.hibernate.criterion.Restrictions;
import styx.habbo.beans.User;
import styx.habbo.game.Session;
import styx.habbo.message.ServerMessage;
import styx.util.DatastoreUtil;

import java.util.regex.Pattern;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class NameValidator implements Runnable {
    private Session networkSession;
    private String name;
    private boolean pet;

    public NameValidator(Session networkSession, String name, boolean pet) {
        this.networkSession = networkSession;
        this.name = name;
        this.pet = pet;
    }

    private boolean valid() {
        if (this.name.length() > 15) {
            return false;
        }

        if (! Pattern.compile("^[a-zA-Z0-9-+=?!@:.,$]+$").matcher(this.name).find()) {
            return false;
        }

        if (Pattern.compile("^MOD-").matcher(this.name).find() ||
                Pattern.compile("^ADM-").matcher(this.name).find() ||
                Pattern.compile("^SOS-").matcher(this.name).find()) {
            return false;
        }

        return true;
    }

    private int statusCode() {

        if (! this.valid()) {
            return 2;
        } else {

            if (! pet) {
                org.hibernate.Session session = DatastoreUtil.currentSession();

                User user = (User)session.createCriteria(User.class).add(Restrictions.eq("name", this.name)).uniqueResult();

                if (user != null) {
                    return 4;
                }
            }
            return 0;
        }
    }

    public void run() {
        this.networkSession.sendMessage(
                new ServerMessage(36)
                        .append(
                                this.statusCode()
                        )
        );
    }
}
