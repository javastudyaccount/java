package jp.btsol.mahjong.web.fw;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import org.springframework.security.crypto.password.PasswordEncoder;

public class AESPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        try {
            byte[] value = ((String) rawPassword).getBytes(StandardCharsets.UTF_8);
            // SecretKey
            int keySizeAsBit = 128; // 128 bit
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(keySizeAsBit);
            SecretKey secretKey = keyGenerator.generateKey();

            // encrypt
            Cipher encryptor = Cipher.getInstance("AES/ECB/PKCS5Padding");
            encryptor.init(Cipher.ENCRYPT_MODE, secretKey);

            byte[] encrypted = encryptor.doFinal(value);
            return new String(encrypted);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException
                | BadPaddingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
