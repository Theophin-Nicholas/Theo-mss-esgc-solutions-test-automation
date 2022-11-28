package com.esgc.Utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataProviderUtilities {

    public static List<String> getDates() {
        List<String> dates = new ArrayList<>();
        String date1 = "12-2020";
        Date today = new Date();

        DateFormat formatter = new SimpleDateFormat("MM-yyyy");

        Calendar beginCalendar = Calendar.getInstance();
        Calendar finishCalendar = Calendar.getInstance();

        try {
            beginCalendar.setTime(formatter.parse(date1));
            finishCalendar.setTime(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        finishCalendar.add(Calendar.MONTH, -1);
        while (finishCalendar.after(beginCalendar)) {
            // add one month to date per loop
            String date = formatter.format(finishCalendar.getTime()).toUpperCase();
            dates.add(date);
            finishCalendar.add(Calendar.MONTH, -1);
        }
        return dates;
    }

    public static List<String> getRandom5Dates() {
        List<String> result = new ArrayList<>();
        List<String> dates = getDates();
        result.add(dates.remove(0));
        Collections.shuffle(dates);
        result.addAll(dates.subList(0,4));
        return result;
    }

    public static Object[][] getDataProvider() {
        List<String> rls = Arrays.asList(
                "ESG",
                "Temperature Alignment",
                "Carbon Footprint",
                "Green Share",
                "Brown Share",
                "Physical Risk Management",
                "operationsrisk",
                "supplychainrisk",
                "marketrisk"
        );

        List<String> dates = getRandom5Dates();

        return rls.stream().flatMap(rl -> dates.stream()
                        .map(date -> new Object[]{rl, date.substring(0, date.indexOf("-")), date.substring(date.indexOf("-") + 1)}))
                .toArray(Object[][]::new);
    }
}
