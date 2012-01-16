package styx.habbo.message.incoming;

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
public class ApprovePassword implements HabboMessage {
    public boolean passwordValid(String name, String password) {
        if ((password.length() > 5)
                || !(password.length() > 10)
                || !(name == password)
                || Pattern.compile("\\d+").matcher(password).find()) {
            return true;
        }

        return false;
    }
    public void handle(Session session, ClientMessage message) {

        String name = message.readString();
        String password = message.readString();

        session.sendMessage(
                new ServerMessage(282)
                    .append(
                            !this.passwordValid(name, password)
                    )
        );
    }
}
