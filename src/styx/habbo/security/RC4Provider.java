package styx.habbo.security;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class RC4Provider extends RC4Core {
    private int i, j;
    
    private short[] key = new short[256];
    private short[] table = new short[256];

    
    public RC4Provider(String publicKey) {
        this.i = 0;
        this.j = 0;

        this.initialize(this.decodeKey(publicKey));
        this.premixTable(this.premixString);
    }

    private void initialize(String checksum) {
        int keyValue = Integer.parseInt(checksum);
        int keyLength = (keyValue & 0xf8) / 8;
        if(keyLength < 20)
            keyLength += 20;
        int keyOffset = keyValue % keyWindow.length;
        int keySkip = 0;
        int prevKey = 0;
        int m = 2;
        short w[] = new short[keyLength];
        for(int i = 0; i < keyLength; i++)
        {
            keySkip = prevKey % 29 - i % 6;
            m *= -1;
            w[i] = keyWindow[Math.abs(keyOffset + i * m + keySkip) % keyWindow.length];
            prevKey = w[i];
        }

        for(short a = 0; a < 256; a++)
        {
            key[a] = w[a % w.length];
            table[a] = a;
        }

        short t = 0;
        short b = 0;
        for(int a = 0; a < 256; a++)
        {
            b = (short)((b + table[a] + key[a]) % 256);
            t = table[a];
            table[a] = table[b];
            table[b] = t;
        }
    }

    private void premixTable(String s) {
        for (int a = 0; a < 17; a++) {
            this.encipher(s.getBytes());
        }
    }
    
    private byte mixTable() {
        this.i = ((this.i + 1) % 256);
        this.j = ((j + table[i]) % 256);

        short b = table[this.i];
        table[this.i] = table[this.j];
        table[this.j] = b;

        return (byte)table[(table[this.i] + table[this.j]) % 256];
    }

    // Cheers Myrax
    private byte ConvertTwoHexBytesToByte(byte A, byte B) {
        int C = 0; // The output value
        int D = 0; // Counter used for determining hex value

        while (D < di.length) {
            if (di[D] == (A & 0xff)) {
                C = (D << 4);
                break;
            }
            ++D;
        }

        D = 0;
        while (D < di.length) {
            if (di[D] == (B & 0xff)) {
                C += D;
                break;
            }
            ++D;
        }

        return (byte)C;
    }

    public ChannelBuffer decipher(ChannelBuffer buffer) {
        if (buffer.readableBytes() % 2 != 0) {
            return null;
        }
        
        ChannelBuffer res = ChannelBuffers.buffer(buffer.readableBytes());

        int b = 0;

        while (buffer.readableBytes() > 0) {
            byte c = ConvertTwoHexBytesToByte(buffer.readByte(), buffer.readByte());
            byte cB = (byte)(c ^ this.mixTable());

            res.writeByte(cB);
            b++;
        }

        return res;
    }
    
    public byte[] encipher(byte data[]) {
        byte cipher[] = new byte[data.length * 2];
        short t = 0;
        short k = 0;
        int pos = 0;
        
        for (int a = 0; a < data.length; a++) {
            int c = data[a] ^ this.mixTable();
            
            if (c <= 0) {
                cipher[pos++] = 0;
                cipher[pos++] = 0;
            } else {
                cipher[pos++] = (byte)di[c >> 4 & 0xf];
                cipher[pos++] = (byte)di[c & 0xf];
            }
        }

        return cipher;
    }
}
