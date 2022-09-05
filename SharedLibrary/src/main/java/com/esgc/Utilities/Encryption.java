package com.esgc.Utilities;


import org.testng.annotations.Test;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryption {
    static Cipher cipher;

    static{
        try {
            cipher = Cipher.getInstance("AES");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String createEncodedKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            byte[] secretKey = keyGenerator.generateKey().getEncoded();
            return Base64.getEncoder().encodeToString(secretKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static SecretKey convertStringToSecretKeyTo(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    public static String encrypt(String plainText, SecretKey secretKey)
            throws Exception {
        cipher = Cipher.getInstance("AES");
        byte[] plainTextByte = plainText.getBytes();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedByte = cipher.doFinal(plainTextByte);
        Base64.Encoder encoder = Base64.getEncoder();
        String encryptedText = encoder.encodeToString(encryptedByte);
        return encryptedText;
    }

    public static String decrypt(String encryptedText, SecretKey secretKey) {
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedTextByte = decoder.decode(encryptedText);
            byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
            String decryptedText = new String(decryptedByte);

            return decryptedText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void test() throws Exception {
        //SAMPLE IMPLEMENTATION
        /**
         * 1-) Call the createEncodedKey() to create the "key" and store it.
         * 2-) Call the encrypt("raw password", convertStringToSecretKeyTo(key))
         * 3-) Store the encrypted password in properties file.
         * Above part is one time process.
         * 4-) While using the password you need to use the key and encrypted password in
         * decrypt("encrypted password", convertStringToSecretKeyTo(key))
         */
        String key = createEncodedKey();
        System.out.println(key);
        String encPassword = encrypt("Basic c3lzX2UyZV9lc2dfcWE6TWVtIWdpajZmZWlnaW5lbGs=", convertStringToSecretKeyTo(key));
        System.out.println("Encrypted pass : " + encPassword);


        String dcPassword = decrypt("P/Yq77dNA0VphM0ZJ1yTHmzoSZET1YXI2QYtbjVRVgUuCu830JY/Uo3r0NbeYnR9PBFJ/iQN57Wcof95AnFq2A==", convertStringToSecretKeyTo("EbGP9eCRBvM2AWUf7n0o2wyIDfQPNmEDjpnGe+sGDLI="));
        System.out.println(" converted Pass " + dcPassword);

       /* String dcPassword1 = decrypt("V2UFuSbl2d42yiK1fuoviplcQCyTxUpMC9dlH1e4FvJx3ECDRz94+BvBZL5K52w2t3M10JHwtRN0crckMHz", convertStringToSecretKeyTo("VzdrHQeWFRMMu4fjbYYlHg=="));
        System.out.println(" converted Pass " + dcPassword1);*/

    }
}
