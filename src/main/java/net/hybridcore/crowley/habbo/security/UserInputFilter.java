package net.hybridcore.crowley.habbo.security;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserInputFilter {
    public static String filterString(String input) {
        return  filterString(input, false);
    }
    
    public static String filterString(String input, boolean lineBreaks) {

        String res = input.replace((char)1, ' ').replace((char)2, ' ').replace((char)9, ' ');

        if (!lineBreaks) {
            res = res.replace((char)13, ' ');
        }

        return res;
    }
}
