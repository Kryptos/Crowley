package styx.habbo.message.incoming;

import styx.Crowley;
import styx.habbo.game.Session;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.IncomingMessage;
import styx.habbo.message.OutgoingMessages;
import styx.habbo.message.ServerMessage;
import styx.util.Random;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class InitCrypto implements IncomingMessage {
    public void handle(Session session, ClientMessage message) {
        session.sendMessage(
                new ServerMessage(277)
                .append("576b145a0c17f8a385971e0b6324a4bc")
        );
    }
}
