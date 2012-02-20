package net.hybridcore.crowley.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class DateTime {

    public static Date date() {
        return GregorianCalendar.getInstance().getTime();
    }

    public static String now() {
        return (new SimpleDateFormat("dd-MM-yyyy")).format(date());
    }
}
