package styx.habbo.message.incoming;

import styx.habbo.game.GameSession;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.IncomingMessage;
import styx.habbo.message.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserInfo implements IncomingMessage {
    public void handle(GameSession gameSession, ClientMessage message) {
        /*
                    GetResponse().Init(5);
            GetResponse().AppendStringWithBreak(Session.GetHabbo().Id.ToString());
            GetResponse().AppendStringWithBreak(Session.GetHabbo().Username);
            GetResponse().AppendStringWithBreak(Session.GetHabbo().Look);
            GetResponse().AppendStringWithBreak(Session.GetHabbo().Gender.ToUpper());
            GetResponse().AppendStringWithBreak(Session.GetHabbo().Motto);
            GetResponse().AppendStringWithBreak(Session.GetHabbo().RealName);
            GetResponse().AppendInt32(0);
            GetResponse().AppendStringWithBreak("");
            GetResponse().AppendInt32(0);
            GetResponse().AppendInt32(0);
            GetResponse().AppendInt32(Session.GetHabbo().Respect);
            GetResponse().AppendInt32(Session.GetHabbo().DailyRespectPoints); // respect to give away
            GetResponse().AppendInt32(Session.GetHabbo().DailyPetRespectPoints);
            SendResponse();
         */
        gameSession.sendMessage(
                new ServerMessage(5)
                .appendString(gameSession.getHabbo().getId().toString())
                .appendString(gameSession.getHabbo().getName())
                .appendString(gameSession.getHabbo().getFigure())
                .appendString(gameSession.getHabbo().getGender().toUpperCase())
                .appendString(gameSession.getHabbo().getMotto())
                .appendString(gameSession.getHabbo().getRealName())
                .append(0)
                .appendString("")
                .append(0)
                .append(0)
                .append(0) //respect
                .append(0) // respect to give away
                .append(0) // daily pet respect points
        );
    }
}
