package styx.util;

import java.security.SecureRandom;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Random {
    private SecureRandom secureRandom = new SecureRandom();
    public int nextInt() {
        return secureRandom.nextInt();
    }
    public int nextInt(int minimum, int maximum) {
        return  minimum + (secureRandom.nextInt() % ((maximum - minimum) + 1));
    }
}
