package com.codebrew.roommart.utils;

import java.util.function.Supplier;

public class Decorations {
    public static <T> T measureExecutionTime(Supplier<T> function, String functionName) {
        long startTime = System.nanoTime();
        System.out.println(" ");
        System.out.println("----- Bat dau thuc thi: " + functionName);
        T result = function.get();
        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime) / 1_000_000;
        String unit = "ms";
        if (executionTime >= 1_000) {
            executionTime /= 1_000;
            unit = "s";
        }
        System.out.println("Function executed in " + executionTime + " " + unit);
        System.out.println("----- Ket thuc thuc thi: " + functionName);
        System.out.println(" ");
        return result;
    }
}
