package net.hybridcore.crowley.habbo.message;

import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.incoming.*;
import org.apache.log4j.Logger;

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
        this.messages.put(421, new NOPMessage());
        this.messages.put(482, new NOPMessage());
        this.messages.put(3032, new NOPMessage()); // Badge Points Limit
        this.messages.put(3011, new NOPMessage()); // Market Place Configuration
        this.messages.put(473, new NOPMessage());  // Gift wrap bs
        this.messages.put(126, new NOPMessage());  // Room Ad
        this.messages.put(3105, new NOPMessage()); // User Notification's
        this.messages.put(316, new NOPMessage());  // Latency Report
        this.messages.put(3210, new NOPMessage()); // Friend Added Quest
        this.messages.put(440, new NOPMessage());  // Call Guide Bot
        this.messages.put(422, new NOPMessage());  // Lag Warning
        this.messages.put(3110, new MessageOfTheDay());
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
        this.messages.put(15, new MessengerUpdates());
        this.messages.put(33, new MessengerChat());
        this.messages.put(41, new MessengerFindFriends());
    }

    public void unregisterMessenger() {
        this.messages.remove(12);
        this.messages.remove(15);
        this.messages.remove(33);
        this.messages.remove(41);
    }
    
    public void registerUser() {
        this.messages.put(7, new UserInfo());
        this.messages.put(8, new UpdateCredits());
        this.messages.put(26, new SubscriptionStatus());
        this.messages.put(228, new SoundSettings());
        this.messages.put(229, new UpdateSoundSettings());
        this.messages.put(233, new NOPMessage());
        this.messages.put(315, new LatencyTest());
    }

    public void unregisterUser() {
        this.messages.remove(7);
        this.messages.remove(8);
        this.messages.remove(26);
        this.messages.remove(228);
        this.messages.remove(229);
        this.messages.remove(233);
        this.messages.remove(315);
    }

    public void invoke(GameSession gameSession, ClientMessage message) {
        if (!this.messages.containsKey(message.getID())) {
            logger.warn("Unknown message (id: " + message.getID() + " client #" + gameSession.getID() + ")");
            return;
        }

        //151 = Navigator Categories
        //321 = Ignored users

        this.messages.get(message.getID()).handle(gameSession, message);
    }
}
