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
public class SoundPreference implements Runnable {
    private GameSession gameSession;
    
    public SoundPreference(GameSession gameSession) {
        this.gameSession = gameSession;
    }
    
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        this.gameSession.sendMessage(
                new ServerMessage(308)
                .append(habbo.getSoundLevel())
        );
    }
}
