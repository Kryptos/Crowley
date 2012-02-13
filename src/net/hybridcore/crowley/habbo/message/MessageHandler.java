package net.hybridcore.crowley.habbo.message;

import org.apache.log4j.Logger;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.incoming.*;

import java.util.HashMap;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class MessageHandler {
    private HashMap<Integer, IncomingMessage> messages = new HashMap<Integer, IncomingMessage>();
    private Logger logger = Logger.getLogger(MessageHandler.class.getName());

    public MessageHandler() {
        this.registerGlobalHandlers();
        this.registerSecurityHandlers();
        this.registerMessenger();
        this.registerUser();
    }

    public void registerGlobalHandlers() {
        this.messages.put(49, new CurrentDate());
    }

    public void registerSecurityHandlers() {
        this.messages.put(206, new InitCrypto());
        this.messages.put(2002, new GenerateKey());
    }

    public void unregisterSecurityHandlers() {
        this.messages.remove(206);
        this.messages.remove(2002);
    }

    public void registerLoginHandlers() {
        this.messages.put(415, new SSOTicket());
    }

    public void unregisterLoginHandlers() {
        this.messages.remove(415);
    }
    
    public void registerMessenger() {
        this.messages.put(12, new InitMessenger());
    }

    public void unregisterMessenger() {
        this.messages.remove(12);
    }
    
    public void registerUser() {
        this.messages.put(7, new UserInfo());
        this.messages.put(8, new UpdateCredits());
        this.messages.put(26, new SubscriptionStatus());
        this.messages.put(228, new SoundSettings());
        this.messages.put(229, new UpdateSoundSettings());
    }

    public void unregisterUser() {
        this.messages.remove(7);
        this.messages.remove(8);
        this.messages.remove(26);
        this.messages.remove(228);
        this.messages.remove(229);
    }

    public void invoke(GameSession gameSession, ClientMessage message) {
        if (!this.messages.containsKey(message.getID())) {
            logger.warn("Unknown message (id: " + message.getID() + " client #" + gameSession.getID() + ")");
            return;
        }

        this.messages.get(message.getID()).handle(gameSession, message);
    }
}
