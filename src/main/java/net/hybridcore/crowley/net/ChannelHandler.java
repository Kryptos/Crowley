package net.hybridcore.crowley.net;

import net.hybridcore.crowley.Crowley;
import net.hybridcore.crowley.habbo.game.GameSession;
import net.hybridcore.crowley.habbo.messages.ClientMessage;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.*;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class ChannelHandler extends SimpleChannelHandler {
    private static final Logger logger = Logger.getLogger(ChannelHandler.class.getName());

    private GameSession gameSession;

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        if (e instanceof ChannelStateEvent) {
            this.gameSession = Crowley.getHabbo().getSessions().addConnection(e.getChannel());
        }
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        if (e instanceof ChannelStateEvent) {
            Crowley.getHabbo().getSessions().removeConnection(e.getChannel());
        }
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        if (e.getMessage() instanceof ClientMessage) {
            this.gameSession.handleMessage((ClientMessage)e.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        e.getCause().printStackTrace();
        e.getChannel().close();
    }
}
