package com.project.mizawra.common;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
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
        return Base64.getEncoder().encodeToString(CipherUtil.getCipher(Cipher.ENCRYPT_MODE).doFinal(text.getBytes()));
    }

    public static String decryptString(String text)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException {
        return new String(CipherUtil.getCipher(Cipher.DECRYPT_MODE).doFinal(Base64.getDecoder().decode(text)));
    }
}
