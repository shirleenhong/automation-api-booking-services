package com.cwt.bpg.cbt.tpromigration.encryptor;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Encryptor
{
    private static final Logger LOGGER = LoggerFactory.getLogger(Encryptor.class);

    private SecretKeySpec secretKeySpec;

    private String secretKey;

    public Encryptor(String secretKey)
    {
        this.secretKey = secretKey;
        try
        {
            byte[] key = secretKey.getBytes("UTF-8");
            key = MessageDigest.getInstance("SHA-1").digest(key);
            key = Arrays.copyOf(key, 16);
            this.secretKeySpec = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException | UnsupportedEncodingException e)
        {
//            LOGGER.error(e.getMessage(), e);
        }
    }

    public String encrypt(String strToEncrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public String decrypt(String strToDecrypt)
    {
        try
        {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    public String getSecretKey()
    {
        return secretKey;
    }

}
