
package com.caoyanming.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;



public final class CryptoUtil {

	
    
    /**
     * 
     */
    private CryptoUtil() {}

   

    /**
     * @param seed
     *            : the seed will be used to produce raw key.
     * @return the key bytes
     * @throws Exception
     *             : the exception
     */
    private static byte[] getRawKey(byte[] seed) throws Exception {
        //        KeyGenerator kgen = KeyGenerator.getInstance("AES");      
        //        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");      
        //        sr.setSeed(seed);      
        //        kgen.init(128, sr);     
        //        SecretKey skey = kgen.generateKey();  
        SecretKeySpec key = new SecretKeySpec(seed, "AES");
        byte[] raw = key.getEncoded();
        return raw;
    }

    /**
     * @param raw
     *            : the raw key.
     * @param clear
     *            : the clear bytes.
     * @return the encrypted bytes.
     * @throws Exception
     *             : the exception.
     */
    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(clear);
        return encrypted;
    }

    /**
     * @param raw
     *            : the raw key.
     * @param encrypted
     *            : the encrypted bytes.
     * @return the clear bytes.
     * @throws Exception
     *             : the exception.
     */
    private static byte[] decrypt(byte[] raw, byte[] encrypted)
        throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
        byte[] decrypted = cipher.doFinal(encrypted);
        return decrypted;
    }

    public static String getMD5(String s) {
        return toMd5(s.getBytes());
    }

    private static String toMd5(byte[] bytes) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        for (byte b: bytes) {
            hexString.append(byteHEX(b)).append(separator);
        }
        return hexString.toString();
    }
    
    public static String getMD5(File file) {
        InputStream fis;
        byte[] buffer = new byte[1024];
        int numRead = 0;
        MessageDigest md5;
        try {
            fis = new FileInputStream(file);
            md5 = MessageDigest.getInstance("MD5");
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            return toHexString(md5.digest(), "");
        } catch (Exception e) {
            return null;
        }
    }

    public static String byteHEX(byte ib) {
        char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
            'b', 'c', 'd', 'e', 'f' };
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0F];
        ob[1] = digit[ib & 0X0F];
        String s = new String(ob);
        return s;
    }
    



}
