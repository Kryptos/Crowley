package net.hybridcore.crowley.habbo.messages.outgoing_old.user;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class UpdateSoundPreference implements Runnable {
    private GameSession gameSession;
    private int soundPreferences;

    public UpdateSoundPreference(GameSession gameSession, int soundPreferences) {
        this.gameSession = gameSession;
        this.soundPreferences = soundPreferences;
    }
    
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();
        habbo.setSoundLevel(this.soundPreferences);

        this.gameSession.sendMessage(
                new ServerMessage(354)
                .append(habbo.getSoundLevel())
        );
    }
}
