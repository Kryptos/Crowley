package styx.habbo.message.incoming;

import styx.Crowley;
import styx.habbo.game.GameSession;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.IncomingMessage;
import styx.habbo.message.outgoing.MessengerFriends;
import styx.habbo.message.outgoing.MessengerRequests;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class InitMessenger implements IncomingMessage {

    public void handle(GameSession gameSession, ClientMessage message) {
        Crowley.getExecutorService().execute(new MessengerFriends(gameSession));
        Crowley.getExecutorService().execute(new MessengerRequests(gameSession));
    }
}
