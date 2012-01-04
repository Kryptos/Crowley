package styx.habbo.message;

import org.jboss.netty.buffer.ChannelBuffer;

import styx.habbo.encoding.Base64Encoding;
import styx.habbo.encoding.WireEncoding;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class ClientMessage {
    private final static int ID_LENGTH = 2;

    private int id;
    private int length;
    private int originIndex = 0;

    private ChannelBuffer buffer;
    
    public ClientMessage(int length, int id, ChannelBuffer buffer) {
        this.length = length;
        this.id = id;
        this.buffer = buffer;
        this.originIndex = buffer.readerIndex();
    }

    public int getID() {
        return id;
    }

    public int getLength() {
        return length;
    }

    public void resetIndex() {
        this.buffer.readerIndex(this.originIndex);
    }

    public void advanceOffset(int i) {
        buffer.readerIndex((this.originIndex + i));
    }

    public byte[] readBytes(int i) {
        return buffer.readBytes(i).array();
    }

    public int readInt() {
        int size = this.remaining();
        
        if (size > WireEncoding.MAX_INTEGER_BYTE_AMOUNT) {
            size = WireEncoding.MAX_INTEGER_BYTE_AMOUNT;
        }

        return WireEncoding.decodeInt(this.buffer.readBytes(size).array())[0];
    }

    public int readFixedInt() {
        return Base64Encoding.PopInt(this.readBytes(2));
    }
    
    public int parseFixedInt() {
        try {
            return Integer.parseInt(this.readString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public boolean readBoolean() {
        return (this.buffer.readByte() == WireEncoding.POSITIVE);
    }

    public String readString() {
        return new String(this.readBytes(this.readFixedInt()));
    }
    
    private int remaining() {
        return (this.length - (this.buffer.readerIndex() - this.originIndex));
    }
    
    public String getHeader() {
        String resp = "";
        resp += new String(Base64Encoding.encodeInt(this.getID(), ClientMessage.ID_LENGTH));

        return resp;
    }
    
    public String getBody() {
        int readerIndex = this.buffer.readerIndex();
        this.resetIndex();

        String resp = "";
        resp += new String(this.buffer.readBytes(this.length).array());

        this.buffer.readerIndex(readerIndex);

        return resp;
    }
    
    public String toString() {
        return this.getHeader() + this.getBody();
    }
}
