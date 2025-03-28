package com.seol.communityfeed.auth.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Password {

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    private final String encryptedPassword;

    private Password(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public static Password createEncryptPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new IllegalArgumentException("패스워드는 빈값이 될 수 없습니다.");
        }
        return new Password(encoder.encode(rawPassword));
    }

    public static Password createPassword(String password){
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("패스워드는 빈값이 될 수 없습니다.");
        }
        return new Password(password);
    }

    public boolean matchPassword(String rawPassword) {
        return encoder.matches(rawPassword, this.encryptedPassword);
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }
}