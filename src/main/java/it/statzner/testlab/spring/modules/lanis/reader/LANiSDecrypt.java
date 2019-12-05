/*
 * Copyright (c) 2019.
 * Created at 16.11.2019
 * ---------------------------------------------
 * @author hyWse
 * @see https://hywse.eu
 * ---------------------------------------------
 * If you have any questions, please contact
 * E-Mail: admin@hywse.eu
 * Discord: hyWse#0126
 */

package it.statzner.testlab.spring.modules.lanis.reader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LANiSDecrypt {

    private static final String CUSTOM_ALPHABET = "+-0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DEFAULT_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    public static String strtr(String str, String from, String to) {
        char[] out = null;
        for (int i = 0, len = str.length(); i < len; i++) {
            char c = str.charAt(i);
            int p = from.indexOf(c);
            if(p >= 0) {
                if(out == null) {
                    out = str.toCharArray();
                }
                out[i] = to.charAt(p);
            }
        }
        return out == null ? str : new String(out);
    }

    public static String decryptLine(String line) {
        String base64 = strtr(line, CUSTOM_ALPHABET, DEFAULT_ALPHABET);

        final byte[] bytes = base64.getBytes();
        final byte[] decode = Base64.getDecoder().decode(bytes);

        return new String(decode, StandardCharsets.ISO_8859_1);
    }

}
