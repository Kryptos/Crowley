package net.hybridcore.crowley.habbo.messages.incoming.friendlist;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessengerAcceptFriendRequest implements IncomingMessage {
    public void handle(GameSession gameSession, ClientMessage message) {
        //TODO: Accept friend request
//        String target = message.readString();
//
//        Crowley.getExecutorService().execute(new AcceptFriendRequest(gameSession, target));
    }
}
