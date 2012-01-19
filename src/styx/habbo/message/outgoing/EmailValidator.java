package styx.habbo.message.outgoing;

import styx.habbo.game.Session;
import styx.habbo.message.ServerMessage;

import java.util.regex.Pattern;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class EmailValidator implements Runnable {
    private Session networkSession;
    private String email;

    public EmailValidator(Session networkSession, String email) {
        this.networkSession = networkSession;
        this.email = email;
    }

    public boolean valid() {
        if (Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$").matcher(this.email).find()) {
            return true;
        }

        return false;
    }

    public void run() {
        this.networkSession.sendMessage(
                new ServerMessage(271)
                        .append(
                                this.valid()
                        )
        );
    }
}
