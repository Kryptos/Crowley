package styx.habbo.message.incoming;

import org.apache.log4j.Logger;
import styx.habbo.game.Session;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.HabboMessage;
import styx.habbo.message.ServerMessage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class CurrentDate implements HabboMessage {
    public void handle(Session session, ClientMessage message) {
        Calendar calendar = GregorianCalendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        session.sendMessage(
                new ServerMessage(163)
                        .append(
                                formatter.format(
                                        calendar.getTime()
                                )
                        )
        );
    }
}
