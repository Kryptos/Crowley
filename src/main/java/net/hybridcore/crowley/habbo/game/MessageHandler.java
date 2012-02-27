package net.hybridcore.crowley.habbo.game;

import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;
import net.hybridcore.crowley.habbo.messages.incoming.friendlist.*;
import net.hybridcore.crowley.habbo.messages.incoming.global.CurrentDateMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.handshake.GenerateSecretKeyMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.handshake.InfoRetrieveMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.handshake.InitCryptoMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.handshake.SSOTicketMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.sound.GetSoundSettingsEvent;
import net.hybridcore.crowley.habbo.messages.incoming.sound.SetSoundSettingsEvent;
import net.hybridcore.crowley.habbo.messages.incoming.tracking.LatencyPingRequestMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.global.NOPMessageEvent;
import net.hybridcore.crowley.habbo.messages.incoming.purse.GetCreditsInfoEvent;
import net.hybridcore.crowley.habbo.messages.incoming.user.*;
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
    }

    public void registerGlobalHandlers() {
        this.messages.put(49, new CurrentDateMessageEvent());
        this.messages.put(421, new NOPMessageEvent());  // PerformanceLogMessageEvent
        this.messages.put(482, new NOPMessageEvent());  // EventLogMessageEvent
        this.messages.put(3032, new NOPMessageEvent()); // TODO: GetBadgePointLimitsEvent
        this.messages.put(3011, new NOPMessageEvent()); // TODO: GetMarketplaceConfigurationMessageEvent
        this.messages.put(473, new NOPMessageEvent());  // TODO: GetGiftWrappingConfigurationEvent
        this.messages.put(126, new NOPMessageEvent());  // TODO: GetRoomAdMessageEvent
        this.messages.put(3105, new NOPMessageEvent()); // TODO: GetUserNotificationsMessageEvent
        this.messages.put(316, new NOPMessageEvent());  // TODO: LatencyPingReportMessageEvent
        this.messages.put(3210, new NOPMessageEvent()); // TODO: FriendRequestQuestCompleteMessageEvent
        this.messages.put(440, new NOPMessageEvent());  // TODO: CallGuideBotMessageEvent
        this.messages.put(422, new NOPMessageEvent());  // LagWarningReportMessageEvent
        this.messages.put(315, new LatencyPingRequestMessageEvent());
    }

    public void registerSecurityHandlers() {
        this.messages.put(206, new InitCryptoMessageEvent());
        this.messages.put(2002, new GenerateSecretKeyMessageEvent());
    }

    public void unregisterSecurityHandlers() {
        this.messages.remove(206);
        this.messages.remove(2002);
    }

    public void registerLoginHandlers() {
        this.messages.put(415, new SSOTicketMessageEvent());
    }

    public void unregisterLoginHandlers() {
        this.messages.remove(415);
    }
    
    public void registerMessenger() {
        this.messages.put(12, new MessengerInitMessageEvent());
        this.messages.put(15, new FriendListUpdateMessageEvent());
        this.messages.put(33, new SendMsgMessageEvent());
        this.messages.put(37, new AcceptBuddyMessageEvent());
        this.messages.put(41, new HabboSearchMessageEvent());
        /*
        37 = AcceptBuddyMessageEvent
        38 = DeclineBuddyMessageEvent
        39 = RequestBuddyMessageEvent
        40 = RemoveBuddyMessageEvent
         */
        this.messages.put(233, new GetBuddyRequestsMessageEvent());
    }

    public void unregisterMessenger() {
        this.messages.remove(12);
        this.messages.remove(15);
        this.messages.remove(33);
        //this.messages.remove(39);
        this.messages.remove(41);
    }
    
    public void registerUser() {
        this.messages.put(7, new InfoRetrieveMessageEvent());
        this.messages.put(8, new GetCreditsInfoEvent());
        this.messages.put(26, new ScrGetUserInfoMessageEvent());
        this.messages.put(228, new GetSoundSettingsEvent());
        this.messages.put(229, new SetSoundSettingsEvent());
        this.messages.put(3110, new GetMOTDMessageEvent());
    }

    public void unregisterUser() {
        this.messages.remove(7);
        this.messages.remove(8);
        this.messages.remove(26);
        this.messages.remove(228);
        this.messages.remove(229);
        this.messages.remove(233);
        this.messages.remove(315);
        this.messages.remove(3110);
    }

    public void invoke(GameSession gameSession, ClientMessage message) {
        if (!this.messages.containsKey(message.getID())) {
            logger.warn("Unknown messages (id: " + message.getID() + " client #" + gameSession.getID() + ")");
            return;
        }

        //151 = Navigator Categories
        //321 = Ignored users

        this.messages.get(message.getID()).handle(gameSession, message);
    }
}
