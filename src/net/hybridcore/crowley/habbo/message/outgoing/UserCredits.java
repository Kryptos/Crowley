package net.hybridcore.crowley.habbo.message.outgoing;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.message.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UserCredits implements Runnable {
    private GameSession gameSession;

    public UserCredits(GameSession gameSession) {
        this.gameSession = gameSession;
    }

    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        /*
        GetClient().GetMessageHandler().GetResponse().Init(6);
            GetClient().GetMessageHandler().GetResponse().AppendStringWithBreak(Credits + ".0");
            GetClient().GetMessageHandler().SendResponse();

            if (InDatabase)
            {
                using (DatabaseClient dbClient = UberEnvironment.GetDatabase().GetClient())
                {
                    dbClient.ExecuteQuery("UPDATE users SET credits = '" + Credits + "' WHERE id = '" + Id + "' LIMIT 1");
                }
            }
         */
        
        this.gameSession.sendMessage(
                new ServerMessage(6)
                .appendString(habbo.getCredits() + ".0")
        );
    }
}
