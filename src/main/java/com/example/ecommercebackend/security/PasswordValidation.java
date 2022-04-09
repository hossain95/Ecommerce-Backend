package com.example.ecommercebackend.security;

public class PasswordValidation {


    public boolean isPasswordValid(String password){
        int passwordLength = password.length();
        int lowerCase = (int) password.chars().filter((s)->Character.isLowerCase(s)).count();
        int upperCase = (int) password.chars().filter((s)->Character.isUpperCase(s)).count();
        int nonAlphabetic = passwordLength - (lowerCase+upperCase);
        if(passwordLength < 6 || lowerCase == 0 || upperCase == 0 || nonAlphabetic == 0){
            return false;
        }
        return true;
    }

    public String passwordMustBeContain(){
        String password = "password must be contains:" +
                "\nminimum 6 characters" +
                "\nuppercase" +
                "\nlowercase" +
                "\nnon-alphabetic character";

        return password;
    }
}
