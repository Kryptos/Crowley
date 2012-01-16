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
public class ApproveEmail implements HabboMessage {
    public boolean emailValid(String email) {
        if (Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$").matcher(email).find()) {
            return true;
        }

        return false;
    }
    public void handle(Session session, ClientMessage message) {
        String email = message.readString();

        session.sendMessage(
                new ServerMessage(271)
                        .append(
                                !this.emailValid(email)
                        )
        );
    }
}
