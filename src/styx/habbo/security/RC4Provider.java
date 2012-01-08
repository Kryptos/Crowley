package styx.habbo.security;

/**
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <crowlie@hybridcore.net> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Crowley.
 */
public class RC4Provider extends RC4Core {
    private int i, j;
    
    private int[] key = new int[256];
    private int[] table = new int[256];
    
    public RC4Provider(String publicKey) {
        this.i = 0;
        this.j = 0;
        
        String decodedKey = this.decodeKey(publicKey);
        this.initialize(decodedKey);
        this.premixTable(this.premixString);
    }

    private void initialize(String key) {
        int keyValue = Integer.parseInt(key);
        int keyLength = (keyValue & 0xf8) / 8;

        if (keyLength < 20) {
            keyLength += 20;
        }

        int keyOffset = keyValue % keyWindow.length;
        int tGiven = keyValue;
        int tOwn;

        int[] w = new int[keyLength];

        for (int a = 0; a < keyLength; a++) {
            tOwn = keyWindow[Math.abs((keyOffset + a) % keyWindow.length)];
            w[a] = Math.abs(tGiven ^ tOwn);

            if (a == 31) {
                tGiven = keyValue;
            } else {
                tGiven = (tGiven / 2);
            }
        }

        for (int b = 0; b < 256; b++) {
            this.key[b] = w[b % w.length];
            table[b] = b;
        }

        int t;
        int u = 0;
        for (int a = 0; a < 256; a++) {
            u = (int)((u + table[a] + this.key[a]) % 256);
            t = table[a];
            table[a] = table[u];
            table[u] = t;
        }
    }

    private void premixTable(String s) {
        for (int a = 0; a < 17; a++) {
            this.encipher(s);
        }
    }

    public String encipher(String s)
    {
        StringBuilder ret = new StringBuilder(s.length() * 2);

        int t = 0;
        int k = 0;

        for (int a = 0; a < s.length(); a++) {
            i = (i + 1) % 256;
            j = (j + table[i]) % 256;
            t = table[i];
            table[i] = table[j];
            table[j] = t;

            k = table[(table[i] + table[j]) % 256];

            int c = (char)s.substring(a, 1).toCharArray()[0] ^ k;

            if (c <= 0) {
                ret.append("00");
            } else {
                ret.append(di[c >> 4 & 0xf]);
                ret.append(di[c & 0xf]);
            }

        }

        return ret.toString();
    }
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
}
