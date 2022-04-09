package jp.btsol.mahjong.config;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.mahjong4j.Utils;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ShiftPasswordEncoder implements PasswordEncoder {
    /**
     * letters String
     */
    private String letters = "";
    /**
     * sb StringBuffer
     */
    private StringBuffer sb = new StringBuffer("");

    private ShiftPasswordEncoder() {

        for (int i = 'a'; i <= 'z'; i++) {
            sb.append((char) i);
        }
        for (int i = 'A'; i <= 'Z'; i++) {
            sb.append((char) i);
        }
        for (int i = '0'; i <= '9'; i++) {
            sb.append((char) i);
        }
        sb.append("!\"#$%&'()`{}*+-_/\\,.@[]:;");
        int[] indexes = IntStream.rangeClosed(0, sb.length() - 1).toArray();
        Utils.shuffleArray(indexes);
        Arrays.stream(indexes).forEach(i -> letters = letters + sb.charAt(i));
        log.info(letters);
    }

    public static ShiftPasswordEncoder getInstance() {
        return new ShiftPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        String encoded = "";
        for (int i = 0; i < rawPassword.length(); i++) {
            encoded += letters.charAt(sb.indexOf(String.valueOf(rawPassword.charAt(i))));
        }
        return encoded;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
