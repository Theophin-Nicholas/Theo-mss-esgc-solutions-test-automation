package com.esgc.Utilities;


import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.util.*;

import static com.esgc.Utilities.CommonUtility.*;

public class ImportPortfolioUtility {

    public static String getOnDemandPortfolioFileToUpload(List<String> ScoreQuality, String dataAlliance, int dataCount, String portfolioName, boolean addNull) {
        OnDemandAssessmentQueries portfolioToUpload = new OnDemandAssessmentQueries();

     List<String> orbisIds = new ArrayList<>();
     for (String e : ScoreQuality) {
         List<String> temp = portfolioToUpload.getOnDemandOrbisIDsForPortfolioCreation(e, dataAlliance, dataCount);
         for(String t : temp ){
             orbisIds.add(t);
         }
     }
     if (addNull){
         for(int i = 0 ; i<2 ; i++){
             int randomOrbisID = (int)Math.floor(Math.random() * (999 - 111 + 1) + 111);
             orbisIds.add(String.valueOf(randomOrbisID));
         }

     }
        String portfolioUploadRepository = CommonUtility.portfolioRepositoryPath();

        File directory = new File(portfolioUploadRepository);
       // int count = directory.list().length + 1;

       // String portfolioName = PortfolioName;
        File file = new File(portfolioUploadRepository + File.separator + portfolioName + ".csv");
        try {
            FileWriter output = new FileWriter(file);
            //CSVWriter write = new CSVWriter(output);
            CSVWriter write = new CSVWriter(output, ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
            write.writeNext(new String[]{"Currency: Please only enter in values of USD or GBP or EUR. Platform values will be converted into USD. Only one currency is allowed per portfolio"});
            write.writeNext(new String[]{"As of Date: Please enter date in yyyy-mm-dd format"});
            write.writeNext(new String[]{"Identifier type: Please specify the values as one of the following Identifiers: ISIN or BBG_TICKER"});
            write.writeNext(new String[]{"Identifier: First column below !AsOfDate: Enter an ISIN or a Bloomberg Ticker. For example: T UN or CMP UN"});
            write.writeNext(new String[]{"Value: Second Column below !AsOfDate: Please enter total monetary value of investment. This must be in one single currency"});
            write.writeNext(new String[]{""});

            String[] currency = {"!Currency", Arrays.asList("USD", "EUR", "GBP").get(randomBetween(0, 2))};
            write.writeNext(currency);
            String[] asOfDate = {"!AsOfDate", CommonUtility.getRandomDate()};
            write.writeNext(asOfDate);
            write.writeNext(new String[]{""});

            String[] header = {"Identifier type","Identifier", "Value"};
            write.writeNext(header);


            for (String identifier : orbisIds) {
                Integer value = randomBetween(1, 10);
                String[] data = {"ORBIS_ID",identifier, value +""};
                write.writeNext(data);
            }
            write.close();
        } catch (Exception e) {

            System.out.println("Portfolio Generation is NOT completed");
            e.printStackTrace();

        }
        return file.getAbsolutePath();



    }


}