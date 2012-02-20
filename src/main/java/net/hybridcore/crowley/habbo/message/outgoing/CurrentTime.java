package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ServerMessage;
import net.hybridcore.crowley.util.DateTime;

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
    private GameSession networkGameSession;

    public CurrentTime(GameSession networkGameSession) {
        this.networkGameSession = networkGameSession;
    }

    public void run() {
        this.networkGameSession.sendMessage(
                new ServerMessage(163)
                        .append(
                                DateTime.now()
                        )
        );
    }
}
