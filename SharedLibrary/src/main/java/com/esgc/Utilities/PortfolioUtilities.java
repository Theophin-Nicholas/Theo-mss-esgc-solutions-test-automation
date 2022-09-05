package com.esgc.Utilities;

import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public class PortfolioUtilities extends DatabaseDriver {

    public static int randomBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    private String getRandomCurrency() {
        return Arrays.asList("USD", "EUR", "GBP").get(randomBetween(0, 2));
    }

    private String getRandomDate() {
        GregorianCalendar gc = new GregorianCalendar();

        int year = randomBetween(2018, 2021);
        gc.set(gc.YEAR, year);

        int dayOfYear = randomBetween(1, gc.getActualMaximum(gc.DAY_OF_YEAR));
        gc.set(gc.DAY_OF_YEAR, dayOfYear);

        return (gc.get(gc.MONTH)) + 1 + "/" + gc.get(gc.DAY_OF_MONTH) + "/" + gc.get(gc.YEAR);
    }


    public String createPortfolio(String condition, String researchLine, List<ResearchLineIdentifier> identifiers) {
        String portfolioRepository = PortfolioFilePaths.portfolioRepositoryPath();

        File directory = new File(portfolioRepository);
        int count = directory.list().length + 1;

        String portfolioName = String.format("%d - %s %s Scored Portfolio", count, researchLine, condition);
        File file = new File(portfolioRepository + File.separator + portfolioName + ".csv");
        try {
            FileWriter output = new FileWriter(file);
            CSVWriter write = new CSVWriter(output);

            String[] header = {"Identifier", "Value"};
            write.writeNext(header);
            String[] name = {"!Name", portfolioName};
            write.writeNext(name);
            String[] currency = {"!Currency", getRandomCurrency()};
            write.writeNext(currency);
            String[] asOfDate = {"!AsOfDate", getRandomDate()};
            write.writeNext(asOfDate);
            for (ResearchLineIdentifier identifier : identifiers) {
                int selectIdentifier = randomBetween(0, 1);
                String[] data = {identifier.getRandomIdentifier(selectIdentifier), identifier.getValue() + ""};
                write.writeNext(data);
            }
            write.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Portfolio Generation is NOT completed");
            e.printStackTrace();

        }
        return file.getAbsolutePath();
    }

    public String createPortfolio(String title, List<ResearchLineIdentifier> identifiers) {
        String portfolioRepository = PortfolioFilePaths.portfolioRepositoryPath();

        File directory = new File(portfolioRepository);
        int count = 0;
        try {
            count = directory.list().length + 1;
        } catch (NullPointerException e) {
            count = 1;
        }

        String portfolioName = String.format("%.80s", title);
        File file = new File(portfolioRepository + File.separator + portfolioName + ".csv");
        try {
            FileWriter output = new FileWriter(file);
            CSVWriter write = new CSVWriter(output, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

//            String[] firstLine = {"Name: This is the name of the portfolio that will appear as a selection on the Platform", "", ""};
//            write.writeNext(firstLine);
            String[] secondLine = {"\"Currency: Please only enter in values of USD, GBP and EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio\"", "", ""};
            write.writeNext(secondLine);
            String[] thirdLine = {"As of Date: Please enter date in yyyy-mm-dd format", "", ""};
            write.writeNext(thirdLine);
            String[] forthLine = {"\"Identifier type: Please specify the values as one of the following Identifiers: ISIN, BBG_TICKER\"", "", ""};
            write.writeNext(forthLine);
            String[] fifthLine = {"\"Identifier: First column below !AsOfDate: Enter an ISIN or a full Bloomberg Ticker, example: T UN Equity or CMP UN Equity\"", "", ""};
            write.writeNext(fifthLine);
            String[] sixthLine = {"Value: Second Column below !AsOfDate: Please enter total monetary value of investment; this must be in whole number format and in one single currency", "", ""};
            write.writeNext(sixthLine);
            write.writeNext(new String[]{"", "", ""});
//            String[] name = {"!Name", portfolioName, ""};
//            write.writeNext(name);
            String[] currency = {"!Currency", getRandomCurrency(), ""};
            write.writeNext(currency);
            String[] asOfDate = {"!AsOfDate", "07/01/2021", ""};
            write.writeNext(asOfDate);
            write.writeNext(new String[]{"", "", ""});
            String[] header = {"Identifier type", "Identifier", "Value"};
            write.writeNext(header);

            for (ResearchLineIdentifier identifier : identifiers) {
                int selectIdentifier = randomBetween(0, 1);
                String identifierType = selectIdentifier == 1 ? "BBG_TICKER" : "ISIN";
                String[] data = {identifierType, identifier.getRandomIdentifier(selectIdentifier), identifier.getValue() + ""};
                write.writeNext(data);
            }
            write.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Portfolio Generation is NOT completed");
            e.printStackTrace();

        }
        return file.getAbsolutePath();
    }



    public double calculateTotalSumOfInvestment(List<ResearchLineIdentifier> list) {
        return list.stream().mapToDouble(ResearchLineIdentifier::getValue).sum();
    }

    public double calculateTotalSumOfInvestmentWithoutNulls(List<ResearchLineIdentifier> list) {
        return list.stream().filter(e -> e.getSCORE() >= 0).mapToDouble(ResearchLineIdentifier::getValue).sum();
    }


    public Double weightedAverageScore(List<ResearchLineIdentifier> list) {
        double total = calculateTotalSumOfInvestmentWithoutNulls(list);//calculateTotalSumOfInvestment(list);
        Double tf = null;
        for (ResearchLineIdentifier each : list) {
            if (each.getSCORE() >= 0) {
                BigDecimal bd = BigDecimal.valueOf((each.getValue() * each.getSCORE()) / total).setScale(6, RoundingMode.HALF_UP);
                if (tf == null) {
                    tf = 0d;
                }
                tf += bd.doubleValue();
            }
        }
        return tf;

    }

    public Double portfolioScoreCalculation(List<ResearchLineIdentifier> list, double total) {
        if (list.stream().filter(e -> e.getSCORE() >= 0).count() == 0) return null;
        Double tf = 0.0d;
        for (ResearchLineIdentifier each : list) {
            if (each.getSCORE() >= 0) {
                BigDecimal bd = BigDecimal.valueOf(each.getValue() / total).setScale(6, RoundingMode.HALF_UP);
                tf += bd.doubleValue() * each.getSCORE();
            }
        }
        return tf;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal("" + value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
