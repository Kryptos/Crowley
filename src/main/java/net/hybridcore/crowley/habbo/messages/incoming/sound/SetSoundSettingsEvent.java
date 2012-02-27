package net.hybridcore.crowley.habbo.messages.incoming.sound;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.IncomingMessage;
import net.hybridcore.crowley.habbo.messages.outgoing.sound.SoundSettingsComposer;
import net.hybridcore.crowley.util.DatastoreUtil;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SetSoundSettingsEvent implements IncomingMessage, Runnable {
    private GameSession gameSession;
    private ClientMessage clientMessage;

    public void handle(GameSession gameSession, ClientMessage clientMessage) {
        this.gameSession = gameSession;
        this.clientMessage = clientMessage;

        Crowley.getExecutorService().execute(this);
    }
    
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();
        habbo.setSoundLevel(this.clientMessage.readInt());

        DatastoreUtil.currentSession().update(habbo);

        Crowley.getExecutorService().execute(new SoundSettingsComposer(gameSession));
    }
}
