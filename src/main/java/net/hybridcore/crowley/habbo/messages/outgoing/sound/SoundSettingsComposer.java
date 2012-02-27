package net.hybridcore.crowley.habbo.messages.outgoing.sound;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import net.hybridcore.crowley.habbo.messages.OutgoingMessage;
import net.hybridcore.crowley.habbo.messages.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class SoundSettingsComposer extends OutgoingMessage {
    public SoundSettingsComposer(GameSession gameSession) {
        super(gameSession);
    }

    @Override
    public void run() {
        Habbo habbo = this.gameSession.getHabbo();

        this.gameSession.sendMessage(
                new ServerMessage(308)
                .append(habbo.getSoundLevel())
        );
    }
}
