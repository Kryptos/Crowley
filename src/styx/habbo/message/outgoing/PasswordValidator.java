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
public class PasswordValidator implements Runnable {
    private Session networkSession;
    private String name;
    private String password;

    public PasswordValidator(Session networkSession, String name, String password) {
        this.networkSession = networkSession;
        this.name = name;
        this.password = password;
    }

    public boolean valid() {
        if ((this.password.length() > 5)
                || !(this.password.length() > 10)
                || !(this.name == this.password)
                || Pattern.compile("\\d+").matcher(this.password).find()) {
            return true;
        }

        return false;
    }

    public void run() {
        this.networkSession.sendMessage(
                new ServerMessage(282)
                        .append(
                                !this.valid()
                        )
        );
    }
}
