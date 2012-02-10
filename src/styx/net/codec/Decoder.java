package styx.net.codec;

import org.apache.log4j.Logger;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import styx.Crowley;
import styx.habbo.encoding.Base64Encoding;
import styx.habbo.game.GameSession;
import styx.habbo.message.ClientMessage;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class Decoder extends FrameDecoder {
    private static final Logger logger = Logger.getLogger(Decoder.class.getName());

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception {
        if (buffer.readableBytes() < 5) {
            return null;
        }

        int bufferIndex = buffer.readerIndex();
        GameSession gameSession = Crowley.getHabbo().getSessions().getSession(channel);
        byte delimiter = buffer.readByte();

        if (delimiter == 0x3C) {
            buffer.discardReadBytes();

            gameSession.getChannel().write(
                    "<?xml version=\"1.0\"?>\r\n" +
                            "<!DOCTYPE cross-domain-policy SYSTEM \"http://www.macromedia.com/xml/dtds/cross-domain-policy.dtd\">\r\n" +
                            "<cross-domain-policy>\r\n" +
                            "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n" +
                            "</cross-domain-policy>\0"
            );

            logger.info("Sent policy to client #" + gameSession.getID());
            return null;
        } else if (delimiter == 0x40) {
            int messageLength = Base64Encoding.PopInt(new byte[] { delimiter, buffer.readByte(), buffer.readByte() });

            if (!(buffer.readableBytes() >= messageLength)) {
                buffer.resetReaderIndex();
                return null;
            }

            int messageID = Base64Encoding.PopInt( buffer.readBytes(2).array() );

            // messageLength passed to ClientMessage is (messageLength - 2) to account for the messageID
            ClientMessage message =  new ClientMessage((messageLength - 2), messageID, buffer);

            buffer.markReaderIndex();

            logger.info("Message received (id: " + message.getID() + " length: " + message.getLength() + ") from client #" + gameSession.getID());
            logger.debug("Message data: " + message.toString());

            gameSession.handleMessage(message);

            return message;
        }

        return null; //wtf
    }
}
