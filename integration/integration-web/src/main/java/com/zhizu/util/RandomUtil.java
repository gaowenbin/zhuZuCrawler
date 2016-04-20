package com.zhizu.util;

import java.util.Random;

public class RandomUtil {

    public static String getNextInt18() {
        String s = "0.";
        Random random = new Random();
        for (int i = 0; i < 17; i++) {
            s += "" + random.nextInt(10);
        }
        System.out.println(s);
        return s;
    }

    public static void main(String[] args) {
        getNextInt18();
    }

}
