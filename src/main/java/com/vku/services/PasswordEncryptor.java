package com.vku.services;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

public class PasswordEncryptor {
    private static final int INSERTION_INDEX = 3;
    private static final int RANDOM_STRING_LENGTH = 10;

    public static String encryptPassword(String password) {
        // Mã hóa mật khẩu bằng thuật toán Base64
        String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
        // Tạo chuỗi ngẫu nhiên
        String randomString = generateRandomString(RANDOM_STRING_LENGTH);
        // Chèn chuỗi ngẫu nhiên vào vị trí INSERTION_INDEX
        StringBuilder encryptedPassword = new StringBuilder(encodedPassword);
        encryptedPassword.insert(INSERTION_INDEX, randomString);
        
        
        return encryptedPassword.toString();
    }

    public static String decryptPassword(String encryptedPassword) {
        // Cắt chuỗi từ vị trí bắt đầu đến vị trí INSERTION_INDEX
        String encodedPassword = encryptedPassword.substring(0, INSERTION_INDEX);
        // Cắt chuỗi từ vị trí INSERTION_INDEX với độ dài RANDOM_STRING_LENGTH
//        String randomString = encryptedPassword.substring(INSERTION_INDEX, INSERTION_INDEX + RANDOM_STRING_LENGTH);
        String str2 = encryptedPassword.substring(INSERTION_INDEX + RANDOM_STRING_LENGTH );
        String decryptedPassword = encodedPassword + str2 ;
        byte[] decodedBytes = Base64.getDecoder().decode(decryptedPassword);
        
        return new String(decodedBytes);
    }

    private static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}