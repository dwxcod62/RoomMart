package com.codebrew.roommart.utils;

public class StringUtils {
    public String maskCccd(String cccd) {
        String prefix = cccd.substring(0, 4);

        String asterisks = "*".repeat(cccd.length() - 4);

        return prefix + asterisks;
    }


}
