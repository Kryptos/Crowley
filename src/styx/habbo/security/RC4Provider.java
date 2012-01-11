package styx.habbo.security;

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

    public void decipher(byte cipher[], int length, byte result[]) {
        short t = 0;
        short k = 0;
        int z = 0;

        for (int a = 0; a < length; a += 2) {
            i = (i + 1) % 256;
            j = (j + table[i]) % 256;

            short temp = table[i];
            table[i] = table[j];
            table[j] = temp;

            k = table[(table[i] + table[j]) % 256];
            t = 0;

            short d = 0;

            do {
                if (d >= di.length) {
                    break;
                }

                if (di[d] == (cipher[a] & 0xff)) {
                    t = (short)(d << 4);
                    break;
                }

                d++;
            } while (true);

            d = 0;

            do {
                if (d >= di.length) {
                    break;
                }

                if (di[d] == (cipher[a + 1] & 0xff)) {
                    t += d;
                    break;
                }

                d++;
            } while (true);

            result[z] = (byte)(t ^ k);
            z++;
        }
    }
    
    public byte[] encipher(byte data[]) {
        byte cipher[] = new byte[data.length * 2];
        short t = 0;
        short k = 0;
        int pos = 0;
        
        for (int a = 0; a < data.length; a++) {
            i = (i + 1) % 256;
            j = (j + table[i]) % 256;
            t = table[i];

            table[i] = table[j];
            table[j] = t;

            k = table[(table[i] + table[j]) % 256];
            int c = data[a] & 0xff ^ k;
            
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

    /*
    public String decipher(String s)
    {
        try
        {
            StringBuilder ret = new StringBuilder(s.length());
            int t = 0;
            int k = 0;
            
            for (int a = 0; a < s.length(); a += 2) {
                i = (i + 1) % 256;
                j = (j + table[i]) % 256;
                t = table[i];
                table[i] = table[j];
                table[j] = t;
                k = table[(table[i] + table[j]) % 256];
                t = Integer.parseInt(s.substring(a, (a + 2)), 16);
                ret = ret.append((char) (t ^ k));
            }

            return ret.toString();
        }
        catch (Exception e) {
            return "";
        }
    }
    */
}
