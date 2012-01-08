package styx.habbo.message;

import org.apache.log4j.Logger;

import java.util.HashMap;

import styx.habbo.game.Session;
import styx.habbo.message.incoming.GenerateKey;
import styx.habbo.message.incoming.InitCrypto;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessageHandler {
    private HashMap<Integer, HabboMessage> messages = new HashMap<Integer, HabboMessage>();
    private Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public MessageHandler() {
        this.registerSecurityHandlers();
    }

    public void registerSecurityHandlers() {
        this.messages.put(206, new InitCrypto());
        this.messages.put(202, new GenerateKey());
    }
    public void invoke(Session session, ClientMessage message) {
        if (!this.messages.containsKey(message.getID())) {
            logger.warn("Unknown message (id: " + message.getID() + " client session: " + session.getID() + ")");
            return;
        }

        this.messages.get(message.getID()).handle(session, message);
    }
}
