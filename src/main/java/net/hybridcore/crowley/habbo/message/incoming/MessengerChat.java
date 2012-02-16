package net.hybridcore.crowley.habbo.message.incoming;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ClientMessage;
import net.hybridcore.crowley.habbo.message.IncomingMessage;
import net.hybridcore.crowley.habbo.message.outgoing.InstantMessage;
import net.hybridcore.crowley.habbo.security.UserInputFilter;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerChat implements IncomingMessage {

    public void handle(GameSession gameSession, ClientMessage message) {
        int target = message.readInt();
        String instantMessage = UserInputFilter.filterString(message.readString()); // TODO: Log


        Crowley.getExecutorService().execute(new InstantMessage(gameSession, target, instantMessage));
    }
}
