package com.esgc.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtilities {

    /**
     * This method returns current date as a string.
     * Provide a format as a parameter
     * MM - to specify month like: 01, 02, 03,
     * MMM - to specify month like: Jan, Feb, Mar
     * dd - to specify day, like 01, 02, 03
     * yyyy - to specify year like: 2010, 2020
     *
     * @param format for example: MMM dd, yyyy = Mar 29, 2020
     * @return current date as a string
     * https://www.journaldev.com/17899/java-simpledateformat-java-date-format
     */

    public static String getCurrentDate(String format) {
        //for example: MMM dd -- acc. to the requirement
        return LocalDate.now().format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * This method returns difference between end and start time
     *
     * @param start  time
     * @param end    time
     * @param format like h:m a --> 5:15 AM, 8:07 PM
     * @return difference between end time and start time as a long
     */
    public static long getTimeDifference(String start, String end, String format) {
        LocalTime startTime = LocalTime.parse(start, DateTimeFormatter.ofPattern(format));
        LocalTime endTime = LocalTime.parse(end, DateTimeFormatter.ofPattern(format));
        return ChronoUnit.HOURS.between(startTime, endTime);
    }

    public static long getDayDifference(String start, String end, String format) {
        LocalDate startTime = LocalDate.parse(start, DateTimeFormatter.ofPattern(format));
        LocalDate endTime = LocalDate.parse(end, DateTimeFormatter.ofPattern(format));
        System.out.println("startTime = " + startTime);
        System.out.println("endTime = " + endTime);
        return ChronoUnit.DAYS.between(startTime,endTime);
    }

    public static String getFormattedDate(String date, String format) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date1);

    }
    public static Date convertStringToDate(String date, String currentDateformat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(currentDateformat);
        return formatter.parse(date);
    }
    public static String getFormattedDate(String date, String inputFormat, String outputFormat) {
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat(inputFormat).parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(outputFormat);
        return formatter.format(date1);

    }

    public static String getPreviousMonthAndYear(String currentMonth, String currentYear) throws ParseException {

        Date dt = new SimpleDateFormat("yyyy-MM").parse(String.format("%s-%s", currentYear, currentMonth));
        final SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.MONTH, -1);
        return format.format(cal.getTime());

    }

    public static boolean isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date != null;
    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    public static String getMonthAndYearFromDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        SimpleDateFormat finalDateFormat = new SimpleDateFormat("MMMM yyyy");
        dateFormat.setLenient(false);
        Date date = null;
        try {
            date = dateFormat.parse(inDate.trim());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return finalDateFormat.format(date);

    }

    public static String getCurrentYear() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));
    }

    public static String getCurrentYear(int year) {
        return LocalDate.now().plusYears(year).format(DateTimeFormatter.ofPattern("yyyy"));
    }

    public static String getCurrentMonthNumeric() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    }

    /**
     * get a date of a given date plus/minus days
     */
    public static String getDatePlusMinusDays(String date, int days, String format) {
        date = getFormattedDate(date, format, format);
        System.out.println("date = " + date);
        //increase date by days
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(format));
        localDate = localDate.plusDays(days);
        return localDate.format(DateTimeFormatter.ofPattern(format));
    }

    public static String getDatePlusMinusDays(String date, int days, String inputFormat, String outputFormat) {
        date = getFormattedDate(date, inputFormat, outputFormat);
        System.out.println("date = " + date);
        //increase date by days
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(outputFormat));
        localDate = localDate.plusDays(days);
        return localDate.format(DateTimeFormatter.ofPattern(outputFormat));
    }

    public static String getMonthNumber(String month){
        switch (month){
            case "January":
                return "01";
            case "February":
                return "02";
            case "March":
                return "03";
            case "April":
                return "04";
            case "May":
                return "05";
            case "June":
                return "06";
            case "July":
                return "07";
            case "August":
                return "08";
            case "September":
                return "09";
            case "October":
                return "10";
            case "November":
                return "11";
            case "December":
                return "12";
            default:
                return "Invalid month";
        }
    }
}





