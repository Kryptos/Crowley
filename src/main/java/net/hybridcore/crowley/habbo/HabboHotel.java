package net.hybridcore.crowley.habbo;

import net.hybridcore.crowley.habbo.game.SessionManager;
import org.apache.log4j.Logger;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class HabboHotel {
    private Logger logger = Logger.getLogger(HabboHotel.class.getName());
    private SessionManager sessionManager = new SessionManager();

    public SessionManager getSessions() {
        return sessionManager;
    }
}
