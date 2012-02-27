package net.hybridcore.crowley.habbo.messages.outgoing.handshake;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;
import net.hybridcore.crowley.util.Random;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SessionParamsComposer extends OutgoingMessage {
    public SessionParamsComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        ServerMessage serverMessage = new ServerMessage(257);

        for (int i = 0; i <= 9; i++) {
            if (i == 2) {
                serverMessage.append(3);
            } else if (i == 3) {
                serverMessage.append(2);
            } else {
                serverMessage.append(i);
            }

            if (i == 5) {
                serverMessage.appendString("dd-MM-yyyy");
            } else if (i == 8) {
                serverMessage.appendString("hotel-co.uk");
            } else {
                serverMessage.append(new Random().nextInt(0, 2));
            }
        }

        this.gameSession.sendMessage(serverMessage);

        this.gameSession.getMessageHandler().unregisterSecurityHandlers();
        this.gameSession.getMessageHandler().registerLoginHandlers();
    }
}
