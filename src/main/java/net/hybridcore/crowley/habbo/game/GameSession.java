package net.hybridcore.crowley.habbo.game;

import net.hybridcore.crowley.habbo.beans.Habbo;
import net.hybridcore.crowley.habbo.message.ClientMessage;
import net.hybridcore.crowley.habbo.message.MessageHandler;
import net.hybridcore.crowley.habbo.message.ServerMessage;
import org.jboss.netty.channel.Channel;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class GameSession {
    private int id;
    private Channel channel;
    private MessageHandler messageHandler;
    private Habbo habbo;

    public GameSession(Channel channel, int id) {
        this.channel = channel;
        this.id = id;
        this.messageHandler = new MessageHandler();
    }

    public int getID() {
        return this.id;
    }
    
    public Channel getChannel() {
        return this.channel;
    }

    public String getIP() {
        return this.getChannel().getRemoteAddress().toString().split(":")[0].substring(1);
    }

    public void start() {
        //this.sendMessage(new ServerMessage(OutgoingMessages.HELLO));
    }

    public void sendMessage(ServerMessage message) {
        this.channel.write(message);
    }

    public void sendAlert(String message) {
        this.sendMessage(
                new ServerMessage(161)
                .append(message)
        );
    }
    /*
    public void sendRawBytes(byte[] data) {
        this.channel.
    }
    */
    
    public void handleMessage(ClientMessage message) {
        this.messageHandler.invoke(this, message);
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }

    public Habbo getHabbo() {
        return habbo;
    }

    public void setHabbo(Habbo habbo) {
        this.habbo = habbo;
    }
}
