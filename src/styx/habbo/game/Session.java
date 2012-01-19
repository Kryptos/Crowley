package styx.habbo.game;

import org.jboss.netty.channel.Channel;
import styx.habbo.message.ClientMessage;
import styx.habbo.message.MessageHandler;
import styx.habbo.message.OutgoingMessages;
import styx.habbo.message.ServerMessage;
import styx.habbo.security.RC4Provider;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Session {
    private int id;
    private Channel channel;
    private MessageHandler messageHandler;
    private RC4Provider rc4Provider;

    public Session(Channel channel, int id) {
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
        this.sendMessage(new ServerMessage(OutgoingMessages.HELLO));
    }

    public void sendMessage(ServerMessage message) {
        this.channel.write(message);
    }
    
    public void handleMessage(ClientMessage message) {
        this.messageHandler.invoke(this, message);
    }
    
    public void setEncryptionContext(String uuid) {
        this.rc4Provider = new RC4Provider(uuid);
    }

    public RC4Provider getEncryptionContext() {
        return this.rc4Provider;
    }

    public boolean encryptionEnabled() {
        return (this.getEncryptionContext() != null) ? true : false;
    }

    public MessageHandler getMessageHandler() {
        return this.messageHandler;
    }
}
