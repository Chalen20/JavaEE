package com.example.demo.config;

import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


@Component
public class MyPasswordEncoder implements PasswordEncoder {

    @SneakyThrows
    @Override
    public String encode(final CharSequence rawPassword) {
        final MessageDigest digest = MessageDigest.getInstance("SHA3-256");
        final byte[] hashbytes = digest.digest(
                rawPassword.toString().getBytes(StandardCharsets.UTF_8));
        String sha = bytesToHex(hashbytes);
        System.out.println(rawPassword  + " - " + sha);
        return sha;
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public boolean matches(final CharSequence rawPassword, final String encodedPassword) {
        System.out.println(encode(rawPassword).equals(encodedPassword));
        System.out.println(rawPassword + ": " +  encode(rawPassword) + " - " + encodedPassword);
        return encode(rawPassword).equals(encodedPassword);
    }
}
