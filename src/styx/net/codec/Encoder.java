package styx.net.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import styx.habbo.message.ServerMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Encoder extends SimpleChannelHandler {
    private static final Logger logger = Logger.getLogger(Encoder.class.getName());

    @Override
    public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) {
        if (e.getMessage() instanceof ServerMessage) {
            ServerMessage message = (ServerMessage)e.getMessage();

            Channels.write(ctx, e.getFuture(), message.getBytes());
            logger.info("Message sent (id: " + message.getID() + " length: " + message.getLength() + ") to client #" + ctx.getChannel().getId());
            logger.debug("Message data: " + new String(message.getBytes().array()));
        }
    }
}
