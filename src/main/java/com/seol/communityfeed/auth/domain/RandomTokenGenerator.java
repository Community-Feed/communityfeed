package com.seol.communityfeed.auth.domain;

import java.security.SecureRandom;

public class RandomTokenGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TOKEN_LENGTH = 20; // ← 12에서 20으로 수정
    private static final SecureRandom random = new SecureRandom();

    private RandomTokenGenerator() {
    }

    public static String generateToken() {
        StringBuilder sb = new StringBuilder(TOKEN_LENGTH);
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}