package styx.net.codec;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import org.apache.log4j.Logger;

import styx.Crowley;
import styx.habbo.encoding.Base64Encoding;
import styx.habbo.game.Session;
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
        Session session = Crowley.getHabbo().getSessions().getSession(channel);

        //if (session.encryptionEnabled()) {
        //    int readable = buffer.readableBytes();
        //    ChannelBuffer cipher = buffer.readBytes(readable);
        //
        //    buffer = session.getEncryptionContext().decipher(cipher);
        //}

        int messageLength = Base64Encoding.PopInt(buffer.readBytes(3).array());
        
        if (!(buffer.readableBytes() >= messageLength)) {
            buffer.resetReaderIndex();
            return null;
        }
        
        int messageID = Base64Encoding.PopInt( buffer.readBytes(2).array() );

        // messageLength passed to ClientMessage is (messageLength - 2) to account for the messageID
        ClientMessage message =  new ClientMessage((messageLength - 2), messageID, buffer);


        logger.info("Message received (id: " + message.getID() + " length: " + message.getLength() + ") from client #" + session.getID());
        logger.debug("Message data: " + message.toString());

        session.handleMessage(message);
        
        return message;
    }
}
