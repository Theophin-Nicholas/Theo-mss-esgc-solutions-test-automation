package com.esgc.Utilities;

public class CommonUtility {
    public static int randomBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
}
