package com.esgc.Utilities;

import java.io.File;
import java.util.Arrays;
import java.util.GregorianCalendar;

public class CommonUtility {
    public static int randomBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public static String portfolioRepositoryPath(){
        String path = System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload";
        return path;
    }

    public static String getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randomBetween(2018, 2021);
        gc.set(gc.YEAR, year);

        int dayOfYear = randomBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return (gc.get(gc.MONTH)) + 1 + "/" + gc.get(gc.DAY_OF_MONTH) + "/" + gc.get(gc.YEAR);
    }

    private String getRandomCurrency() {
        return Arrays.asList("USD", "EUR", "GBP").get(randomBetween(0, 2));
    }

}
