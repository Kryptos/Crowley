package styx.habbo.message.outgoing;

import styx.habbo.game.Session;
import styx.habbo.message.ServerMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class CurrentTime implements Runnable {
    private Session networkSession;

    public CurrentTime(Session networkSession) {
        this.networkSession = networkSession;
    }
    
    public Date date() {
        return GregorianCalendar.getInstance().getTime();
    }
    
    public String now() {
        return (new SimpleDateFormat("dd-MM-yyyy")).format(this.date());
    }

    public void run() {
        this.networkSession.sendMessage(
                new ServerMessage(163)
                        .append(
                                this.now()
                        )
        );
    }
}
