package com.seol.communityfeed.auth.domain;

import java.util.regex.Pattern;

public class Email {

   private static final String EMAIL_PATTERN = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
   private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    private final String emailText;

    private Email(String email){
        this.emailText = email;
    }

    public static Email createEmail(String email){
        if (email == null || email.isBlank()){
            throw new IllegalArgumentException("email is not valid");
        }

        if(isNotValidEmailString(email)){
            throw new IllegalArgumentException("email is not valid");
        }

        return new Email(email);
    }

    public String getEmailText(){
        return this.emailText;
    }

    private static boolean isNotValidEmailString(String email){
        return !pattern.matcher(email).matches();
    }
}
