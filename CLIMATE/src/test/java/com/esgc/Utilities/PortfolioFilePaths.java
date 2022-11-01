package com.esgc.Utilities;

import java.io.File;

/**
 * This utility class created to store file paths.
 *
 */

public class PortfolioFilePaths {
//TODO check portfolio names
    /**
     * This method provides a directory path of created portfolios by automation
     * @return path of portfolio repository directory path
     */
    public static String portfolioRepositoryPath(){
        String path = System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"portfolio_repository";
        return path;
    }

    /**
     * This method provides a csv file path for the valid portfolio
     * @return path of valid portfolio
     */
    public static String goodPortfolioPath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"Portfolio Upload updated_good.csv";
    }

    /**
     * This method provides a csv file path for the valid portfolio
     * @return path of valid portfolio
     */
    public static String scorePortfolioPath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"Portfolio_Score_Data.csv";
    }

    /**
     * This method provides a csv file path for the valid portfolio without portfolio name
     * @return path of valid portfolio without name
     */
    public static String portfolioWithoutNamePath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"PortfolioWithoutName.csv";
    }

    /**
     * This method provides a csv file path for the portfolio with long portfolio name
     * @return path of portfolio with long portfolio name
     */
    public static String portfolioWithLongNamePath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"Portfolio with very long name to check ellipses after limit.csv";
    }

    public static String portfolioWithNoData1(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"NoData1.csv";
    }

    public static String portfolioWithPredictedScores(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"EsgWithPredictedScores.csv";
    }

    /**
     * This method provides a csv file path for the valid portfolio without as of date
     * @return path of valid portfolio without as of date
     */
    public static String portfolioWithoutDatePath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"PortfolioWithoutDate.csv";
    }

    /**
     * This method provides a csv file path for the valid portfolio without portfolio name and without as of date
     * @return path of valid portfolio without name and without as of date
     */
    public static String portfolioWithoutNameAndWithoutAsOfDatePath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"PortfolioWithoutNameAndWithoutAsOfDate.csv";
    }



    /**
     * This method provides a csv file path for the valid portfolio without AsOfDate
     * @return path of valid portfolio without AsOfDate
     */
    public static String portfolioWithoutAsOfDatePath(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"Portfolio Upload updated_good.csv";
    }



    /**
     * This method provides a csv file path for invalid portfolios
     * Will be used with DataProvider to test invalid imports
     * Needs to provide file name with extension (ex: .csv)
     * @param fileName - name of file with extension .csv or other
     * @return path of invalid portfolio
     */
    public static String getFilePathForInvalidPortfolio(String fileName){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"invalid"+File.separator+fileName;
    }

    /**
     * This method provides a csv file path for the valid portfolio
     * @return path of valid portfolio
     */
    public static String portfolioTemplate45ISIN(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"data_validation"+File.separator+"45_ISIN_with_Unique_Scores.csv";
    }

    /**
     * This method provides a csv file path for the data validation
     * @return path of data validation portfolio1
     * This portfolio contains 130 ISINs
     * We do not have matching for 1 ISIN
     * 123 Identifier can be covered under Physical Risk Management
     * Portfolio Coverage for Physical Risk Management 123/129
     */
    public static String getFilePathForDataValidationPortfolio1(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"data_validation"+File.separator+"DataValidationPortfolio1.csv";
    }

    /**
     * This method provides a csv file path for the valid portfolio without AsOfDate
     * @return path of valid portfolio without AsOfDate
     */
    public static String portfolioForRegionSectorFilterValidation(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"MyRegionSectorFilterTest.csv";
    }

    public static String portfolioWithSubsidiaryCompany(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"PortfolioWithSubsidiaryCompany.csv";
    }

    public static String portfolioWithInactiveSubsidiaryCompany(){
        return System.getProperty("user.dir")+ File.separator+"src"+
                File.separator+"test"+File.separator+"resources"+
                File.separator+"upload"+File.separator+"PortfolioWithInactiveSubsidiaryCompany.csv";
    }



}
