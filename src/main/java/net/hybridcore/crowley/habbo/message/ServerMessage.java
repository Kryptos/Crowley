package net.hybridcore.crowley.habbo.message;

import net.hybridcore.crowley.habbo.encoding.Base64Encoding;
import net.hybridcore.crowley.habbo.encoding.WireEncoding;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class ServerMessage {
    private static final int MESSAGE_SIZE = 1024;
    private static final int MESSAGE_ID_LENGTH = 2;

    private int id;
    private ChannelBuffer body;

    public ServerMessage(int id) {
        this.id = id;
        this.body = ChannelBuffers.buffer(ServerMessage.MESSAGE_SIZE);
    }

    public int getID() {
        return this.id;
    }
    
    public String getHeader() {
        String resp = "";
        resp += new String(Base64Encoding.encodeInt(this.getID(), ServerMessage.MESSAGE_ID_LENGTH));

        return resp;
    }
    
    public String getBody() {
        String resp = "";
        resp += new String(this.body.readBytes(this.body.writerIndex()).array());
        
        this.body.readerIndex(0);
        return resp;
    }
    
     public int getLength() {
         return this.body.writerIndex();
     }
    
    public void reset() {
        this.body.clear();
    }

    public ServerMessage append(char c) {
        this.body.writeChar(c);
        return this;
    }

    public ServerMessage append(byte b) {
        this.body.writeByte(b);
        return this;
    }
    
    public ServerMessage append(byte[] data) {
        if (data != null && data.length > 0) {    
            this.body.writeBytes(data);
        }

        return this;
    }
    
    public ServerMessage append(String data) {
        this.append(data.getBytes());
        return this;
    }
    
    public ServerMessage appendString(String data) {
        this.appendString(data, 2);
        return this;
    }

    public ServerMessage appendString(String data, int breaker) {
        this.append(data);
        this.append((char)breaker);
        return this;
    }
    
    public ServerMessage append(boolean state) {
        if (state) {
            this.append(WireEncoding.POSITIVE);
        } else {
            this.append(WireEncoding.NEGATIVE);
        }

        return this;
    }
    
    public ServerMessage append(int i) {
        this.append(WireEncoding.encodeInt(i));
        return this;
    }

    public ChannelBuffer getBytes() {
        byte[] header = Base64Encoding.encodeInt(this.getID(), ServerMessage.MESSAGE_ID_LENGTH);

        ChannelBuffer buffer = ChannelBuffers.buffer(header.length + this.body.writerIndex() + 1);
        buffer.writeBytes(header);
        buffer.writeBytes(this.body.array(), 0, this.body.writerIndex());
        buffer.writeByte(1);

        return buffer;
    }
}
