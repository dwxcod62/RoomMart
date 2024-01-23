
package com.codebrew.roommart.utils;

import java.security.SecureRandom;

public class RandomUtils {
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static final String NUMBER = "0123456789";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++){
            sb.append(AB.charAt(rnd.nextInt( AB.length() )));

        }
        return sb.toString();
    }

    public static String randomToken(int len, String username){
        StringBuilder sb = new StringBuilder(len);
        sb.append(username);
        for (int i = 0; i < len; i++){
            sb.append(AB.charAt(rnd.nextInt( AB.length() )));
        }
        return sb.toString();
    }
    public static String randomResToken(int len){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++){
            sb.append(AB.charAt(rnd.nextInt( AB.length() )));
        }
        return sb.toString();
    }

    public static String randomInviteCode(int len, String roomId){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++){
            sb.append(AB.charAt(rnd.nextInt( AB.length() )));
        }
        sb.append(roomId);
        return sb.toString();
    }
    public static String randomPassword(int len,String username){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++){
            sb.append(AB.charAt(rnd.nextInt( AB.length() ))).append(username.charAt(rnd.nextInt( username.length() )));
        }
        return sb.toString();
    }

    public static String randomOTP(int len){
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++){
            sb.append(NUMBER.charAt(rnd.nextInt( NUMBER.length() )));
        }
        return sb.toString();
    }
}
