package styx.net;

import org.jboss.netty.channel.Channel;
import styx.habbo.message.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Session {
    private int id;
    private Channel channel;

    public Session(Channel channel, int id) {
        this.channel = channel;
        this.id = id;
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
        this.channel.write(new ServerMessage(0));
    }
}
