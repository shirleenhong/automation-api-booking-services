package com.cwt.bpg.cbt.tpromigration.encryptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class Encryptor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private SecretKey secretKey;

    public Encryptor(String secretKey)
    {
        this.secretKey = new SecretKeySpec(secretKey.getBytes(), "AES");
    }

    private final static int GCM_IV_LENGTH = 12;
    private final static int GCM_TAG_LENGTH = 16;

    public String encrypt(String privateString) throws Exception {
        byte[] iv = new byte[GCM_IV_LENGTH];
        (new SecureRandom()).nextBytes(iv);

        Cipher cipher = getCipher(iv, Cipher.ENCRYPT_MODE);

        byte[] ciphertext = cipher.doFinal(privateString.getBytes("UTF8"));
        byte[] encrypted = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);

        return Base64.getEncoder().encodeToString(encrypted);
    }

    public String decrypt(String encrypted) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encrypted);

        byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);

        Cipher cipher = getCipher(iv, Cipher.DECRYPT_MODE);

        byte[] ciphertext = cipher.doFinal(decoded, GCM_IV_LENGTH, decoded.length - GCM_IV_LENGTH);

        return new String(ciphertext, "UTF8");
    }

    private Cipher getCipher(byte[] iv, int decryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException
    {
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
        cipher.init(decryptMode, secretKey, ivSpec);
        return cipher;
    }

    public SecretKey getSecretKey()
    {
        return secretKey;
    }

}
