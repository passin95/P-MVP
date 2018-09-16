package com.passin.pmvp.util;

/**
 * <pre>
 * @author : passin
 * Contact me : https://github.com/passin95
 * Date: 2018/9/16 9:58
 * </pre>
 */
public class UrlEncoderUtils {
    /**
     * 判断str是否urlEncoder.encode过，
     * 经常遇到这样的情况，拿到一个URL,但是搞不清楚到底要不要encode，
     * 不做encode吧，担心出错，做encode吧，又怕重复了。
     *
     */
    public static boolean hasUrlEncoded(String str) {

        boolean needEncode = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '%' && (i + 2) < str.length()) {
                // 判断是否符合urlEncode规范
                char c1 = str.charAt(++i);
                char c2 = str.charAt(++i);
                if (isDigit16Char(c1) && isDigit16Char(c2)) {
                    continue;
                }
            }
            // 其他字符，肯定需要urlEncode
            needEncode = true;
            break;
        }

        return !needEncode;
    }

    /**
     * 判断c是否是16进制的字符
     *
     * @param c
     * @return
     */
    private static boolean isDigit16Char(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F');
    }
}
