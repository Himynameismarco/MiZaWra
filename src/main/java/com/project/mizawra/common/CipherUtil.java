package com.project.mizawra.common;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class CipherUtil {
    private static Cipher getCipher(int mode) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(System.getenv("CRYPTO_KEY").getBytes(), "AES");
        cipher.init(mode, secretKey);
        return cipher;
    }

    public static String encryptString(String text)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        return new String(CipherUtil.getCipher(Cipher.ENCRYPT_MODE).doFinal(text.getBytes(
                StandardCharsets.ISO_8859_1)), StandardCharsets.ISO_8859_1);
    }

    public static String decryptString(String text)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        return new String(CipherUtil.getCipher(Cipher.DECRYPT_MODE).doFinal(text.getBytes(
                StandardCharsets.ISO_8859_1)), StandardCharsets.ISO_8859_1);
    }
}
