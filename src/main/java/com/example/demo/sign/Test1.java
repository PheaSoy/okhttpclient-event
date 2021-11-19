package com.example.demo.sign;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.stream.IntStream;

public class Test1 {

    private static final String HMAC_SHA512 = "HmacSHA512";

    public static void main(String[] args) {
        Mac sha512Hmac;
        String result;
        final String key = "Welcome1";

        try {
            final byte[] byteKey = key.getBytes(StandardCharsets.UTF_8);
            sha512Hmac = Mac.getInstance(HMAC_SHA512);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HMAC_SHA512);
            sha512Hmac.init(keySpec);
            byte[] macData = sha512Hmac.doFinal("My message".getBytes(StandardCharsets.UTF_8));

            // Can either base64 encode or put it right into hex
            IntStream.range(1,10).forEach(i -> {
                System.out.println(Base64.getEncoder().encodeToString(macData));
            });
            result = Base64.getEncoder().encodeToString(macData);
            //result = bytesToHex(macData);
            System.out.println(result);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            // Put any cleanup here
            System.out.println("Done");
        }
    }
}