package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.PortfolioAnalysis.API.APIModels.UnderlyingDataMetricsTCFD;
import com.esgc.PortfolioAnalysis.API.APIModels.UnderlyingDataMetricsWrapper;
import com.esgc.PortfolioAnalysis.API.APIModels.UnderlyingDataMetricsWrapperNew;
import com.esgc.PortfolioAnalysis.DB.DBQueries.UnderlyingDataMetricsQueries;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;
import static org.hamcrest.Matchers.*;

public class UnderlyingMetricsTest extends DataValidationTestBase {
//TODO fails should be handled
    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {1404, 2410, 2411, 2413, 2414, 2953, 3677, 3842, 3843})
    public void verifyUnderlyingDataMetricsWithMixedIdentifiers(@Optional String sector, @Optional String region,
                                                                @Optional String researchLine, @Optional String month, @Optional String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
        String fileName = String.format("Underlying Data Metrics %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        test.info("Portfolio saved to:");
        test.info(path);
        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().assertThat().body("portfolio_name", Matchers.notNullValue());

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);

        //filter for a given region
        if (!region.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getWORLD_REGION()
                    .equals(region)).collect(Collectors.toList());
        }

        //filter for a given region
        if (!sector.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getPLATFORM_SECTOR()
                    .equals(sector)).collect(Collectors.toList());
        }

        UnderlyingDataMetricsQueries udmQueries = new UnderlyingDataMetricsQueries();


        int countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream().filter(e -> e.getSCORE() >= 0).count();

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        List<Map<String, Object>> dataMetricsFromDB = null;

        response = controller
                .getPortfolioUnderlyingDataMetricsResponse(portfolioId, researchLine, apiFilterPayload);

        List<UnderlyingDataMetricsWrapper> dataMetricsList1 = null;
        List<UnderlyingDataMetricsWrapperNew> dataMetricsList2 = null;
        List<UnderlyingDataMetricsTCFD> dataMetricsList3 = null;

        System.out.println("############################################");
        response.prettyPeek();
        System.out.println("############################################");

        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        if(researchLine.equalsIgnoreCase("Brown Share")){
            rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories("Brown Share Ranges");
        }

        switch (researchLine) {
            case "TCFD":
                dataMetricsList3 = Arrays.asList(response.as(UnderlyingDataMetricsTCFD[].class));
                break;
            case "Green Share":
                dataMetricsFromDB = udmQueries.getGreenShareUnderlyingDataMetrics(portfolioId, month, year);
                dataMetricsList2 = Arrays.asList(response.as(UnderlyingDataMetricsWrapperNew[].class));
                Collections.reverse(rangeAndCategoryList);
                break;
            case "operationsrisk":
                dataMetricsFromDB = udmQueries.getOperationsRiskUnderlyingDataMetrics(portfolioId, month, year, region, sector);
                dataMetricsList2 = Arrays.asList(response.as(UnderlyingDataMetricsWrapperNew[].class));
                Collections.reverse(rangeAndCategoryList);
                break;
            case "Energy Transition Management":
                dataMetricsFromDB = udmQueries.getEnergyTransitionManagementUnderlyingDataMetrics(portfolioId, month, year);
                dataMetricsList2 = Arrays.asList(response.as(UnderlyingDataMetricsWrapperNew[].class));
                Collections.reverse(rangeAndCategoryList);
                break;
            case "Brown Share":
                dataMetricsFromDB = udmQueries.getBrownShareUnderlyingDataMetrics(portfolioId, month, year);
                dataMetricsList2 = Arrays.asList(response.as(UnderlyingDataMetricsWrapperNew[].class));
                Collections.reverse(rangeAndCategoryList);
                break;
            default:
                dataMetricsList1 = Arrays.asList(response.as(UnderlyingDataMetricsWrapper[].class));
                break;
        }
        System.out.println("############################################");
        System.out.println("dataMetricsFromDB = " + dataMetricsFromDB);

        System.out.println("############################################");
        System.out.println("dataMetricsList1 = " + dataMetricsList1);
        System.out.println("dataMetricsList2 = " + dataMetricsList2);


        if (researchLine.equals("marketrisk") || researchLine.equals("supplychainrisk")) {

            if (researchLine.equals("marketrisk"))
                dataMetricsFromDB = udmQueries.getMarketRiskUnderlyingDataMetrics(portfolioId, month, year);
            else
                dataMetricsFromDB = udmQueries.getSupplyChainRiskUnderlyingDataMetrics(portfolioId, month, year);

            List<String> bvd9s = portfolioToUpload.stream().map(ResearchLineIdentifier::getBVD9_NUMBER).collect(Collectors.toList());

            dataMetricsFromDB = dataMetricsFromDB.stream().filter(e -> bvd9s.contains(e.get("BVD9_NUMBER").toString())).collect(Collectors.toList());

            for (int i = 0; i < dataMetricsList1.size(); i++) {
                String metricTitle = dataMetricsList1.get(i).getTitle();
                System.out.println("Metric title:" + metricTitle);
                System.out.println("-------------------------------");

                List<Map<String, Object>> filteredDataMetricsFromDB = dataMetricsFromDB
                        .stream().filter(e -> e.get("NAME").equals(metricTitle))
                        .filter(PortfolioUtilities.distinctByKey(e -> e.get("BVD9_NUMBER"))).collect(Collectors.toList());
                filteredDataMetricsFromDB.forEach(System.out::println);
                Double increment = 10.0d;

                Double minScore = 0.0;
                Double maxScore = 10.0;

                for (int j = 0; j < 10; j++) {

                    Integer actualCount = dataMetricsList1.get(i).getData().get(0).get(j).intValue();


                    Double finalMinScore = minScore;
                    Double finalMaxScore = maxScore;

                    Integer expectedCount = (int) filteredDataMetricsFromDB.stream()
                            .mapToDouble(e -> Double.parseDouble(e.get("ENTITY_CATEGORY_SCORE").toString()))
                            .filter(e -> e >= 0)
                            .filter(e -> e >= finalMinScore && e < finalMaxScore).count();

                    if (j == 9) {
                        expectedCount = (int) filteredDataMetricsFromDB.stream()
                                .mapToDouble(e -> Double.parseDouble(e.get("ENTITY_CATEGORY_SCORE").toString()))
                                .filter(e -> e >= 0)
                                .filter(e -> e >= finalMinScore && e <= finalMaxScore).count();
                    }

                    assertTestCase.assertEquals(actualCount, expectedCount, "Score group " + (j + 1) + " count verification for " + metricTitle);

                    minScore = maxScore;
                    maxScore += increment;
                }

            }

        } else if (researchLine.equals("Carbon Footprint")) {
            //loop through Scope1, Scope2, Scope3

            dataMetricsFromDB = udmQueries.getCarbonFootprintUnderlyingDataMetrics(portfolioId, month, year);

            List<String> bvd9s = portfolioToUpload.stream().map(ResearchLineIdentifier::getBVD9_NUMBER).collect(Collectors.toList());

            dataMetricsFromDB = dataMetricsFromDB.stream().filter(e -> bvd9s.contains(e.get("BVD9_NUMBER").toString())).collect(Collectors.toList());

            for (int i = 0; i < dataMetricsList1.size(); i++) {
                String metricTitle = dataMetricsList1.get(i).getTitle();
                System.out.println("Metric title:" + metricTitle);
                System.out.println("-------------------------------");

                Integer expectedMax = (int) dataMetricsFromDB.stream()
                        .mapToDouble(e -> (long) e.get(metricTitle))
                        .filter(e -> e >= 0)
                        .max().orElseThrow(NoSuchElementException::new);

                Integer expectedMin = (int) dataMetricsFromDB.stream()
                        .mapToDouble(e -> (long) e.get(metricTitle))
                        .filter(e -> e >= 0)
                        .min().orElseThrow(NoSuchElementException::new);

                Integer actualMax = dataMetricsList1.get(i).getMax_carbon_footprint_scope_1_2_score();
                Integer actualMin = dataMetricsList1.get(i).getMin_carbon_footprint_scope_1_2_score();

                assertTestCase.assertEquals(actualMin, expectedMin, "Min score is not matching for " + metricTitle);
                assertTestCase.assertEquals(actualMax, expectedMax, "Max score is not matching for " + metricTitle);

                Double increment = (expectedMax - expectedMin) / 10.0d;

                Double minScore = (double) expectedMin;
                Double maxScore = minScore + increment;

                for (int j = 0; j < 10; j++) {

                    Integer actualMinScore = dataMetricsList1.get(i).getData_export().get(j).getMin();
                    Integer actualMaxScore = dataMetricsList1.get(i).getData_export().get(j).getMax();
                    Integer actualCount1 = dataMetricsList1.get(i).getData_export().get(j).getData();
                    Integer actualCount2 = dataMetricsList1.get(i).getData().get(0).get(j).intValue();

                    Double finalMinScore = minScore;
                    Double finalMaxScore = maxScore;

                    Integer expectedCount = (int) dataMetricsFromDB.stream()
                            .mapToDouble(e -> (long) e.get(metricTitle))
                            .filter(e -> e >= 0)
                            .filter(e -> e >= finalMinScore && e <= finalMaxScore).count();

                    if (j == 9) {
                        expectedCount = (int) dataMetricsFromDB.stream()
                                .mapToDouble(e -> (long) e.get(metricTitle))
                                .filter(e -> e >= 0)
                                .filter(e -> e >= finalMinScore).count();
                    }

                    assertTestCase.assertEquals(actualMinScore, Integer.valueOf((int) Math.round(minScore)), "Score group " + (j + 1) + "  min score is not verified for " + metricTitle);
                    assertTestCase.assertEquals(actualMaxScore, Integer.valueOf((int) Math.round(maxScore)), "Score group " + (j + 1) + "  max score is not verified for " + metricTitle);
                    assertTestCase.assertEquals(actualCount1, expectedCount, "Score group " + (j + 1) + " count is not verified for export for " + metricTitle);
                    assertTestCase.assertEquals(actualCount2, expectedCount, "Score group " + (j + 1) + " count is not verified for " + metricTitle);

                    minScore = maxScore + 1;
                    maxScore += increment;
                }

            }

        } else {


            //loop through each data metric
            for (int i = 0; i < dataMetricsList2.size(); i++) {
                String metricTitle = dataMetricsList2.get(i).getTitle();
                System.out.println("Metric title:" + metricTitle);
                System.out.println("-------------------------------");
//                int countOfRecords = (int) dataMetricsFromDB.stream().filter(e -> e.get(metricTitle) != null).count();

                //  assertTestCase.assertFalse(countOfRecords == 0);

                //operations risk only check %investment, there is no count data
                if (researchLine.equals("operationsrisk")) {
                    double totalPerct = 0d;
                    double totalPerct2 = 0d;
                    //loop into each score category in data metric
                    for (int j = 0; j < dataMetricsList2.get(i).getData().get(0).size(); j++) {
                        String actualScoreCategory1 = dataMetricsList2.get(i).getData().get(0).get(j).getName();
                        Double actualInvestmentPercentage = dataMetricsList2.get(i).getData().get(0).get(j).getFacExposed().get(0);

                        RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList.get(j);

                        assertTestCase.assertEquals(actualScoreCategory1, rangeAndCategory.getCategory(), "Validating " + metricTitle + "-" +
                                rangeAndCategory.getCategory() + " score category");

//                        Double totalValue = dataMetricsFromDB.stream()
//                                .filter(e -> e.get("NAME").toString().equals(metricTitle))
//                                .mapToDouble(e -> Double.parseDouble(e.get("FAC_COUNT").toString())).sum();
//
//
//                        Double totalInvestmentInCategory = dataMetricsFromDB.stream()
//                                .filter(e -> e.get("NAME").toString().equals(metricTitle))
//                                .filter(e -> e.get("CATEGORY").toString().equals(rangeAndCategory.getCategory()))
//                                .mapToDouble(e -> Double.parseDouble(e.get("FAC_COUNT").toString())).sum();

                        double result = dataMetricsFromDB.stream()
                                .filter(e -> e.get("RISKHAZARD").toString().equals(metricTitle))
                                .filter(e -> e.get("RISKCATEGORY").toString().equals(rangeAndCategory.getCategory()))
                                .mapToDouble(e -> Double.parseDouble(e.get("PERCENTAGE").toString())).sum();
                        //(totalInvestmentInCategory / totalValue) * 100;

                        Double expectedInvestmentPercentage = Double.isNaN(result) ? 0 : PortfolioUtilities.round(result, 4);
                        System.out.println("Score Category:" + rangeAndCategory.getCategory());
                        System.out.println("Score:" + expectedInvestmentPercentage);
                        System.out.println("Score Category Actual:" + actualScoreCategory1);
                        System.out.println("Score Actual:" + actualInvestmentPercentage);


                        assertTestCase.assertEquals(actualInvestmentPercentage, expectedInvestmentPercentage, "Percentage is matching in " + metricTitle +
                                "-" + actualScoreCategory1 + " category");
                        totalPerct += expectedInvestmentPercentage;
                        totalPerct2 += actualInvestmentPercentage;
                    }
                    System.out.println("Total perc expected:" + totalPerct);
                    System.out.println("Total perc actual:" + totalPerct2);
                } else {

                    //loop into each score category in data metric
                    for (int j = 0; j < dataMetricsList2.get(i).getData().get(0).size(); j++) {
                        String actualScoreCategory1 = dataMetricsList2.get(i).getData().get(0).get(j).getName();
                        String actualScoreCategory2 = dataMetricsList2.get(i).getData().get(1).get(j).getName();
                        Integer actualCountOfCompanies = dataMetricsList2.get(i).getData().get(0).get(j).getCount();
                        Double actualInvestmentPercentage = dataMetricsList2.get(i).getData().get(1).get(j).getData().get(0);


                        if (actualScoreCategory1.equals("Yes") || actualScoreCategory1.equals("No")) {
                            continue;
                        }
                        RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList.get(j);

                        assertTestCase.assertEquals(actualScoreCategory1, rangeAndCategory.getCategory(), "Validating score category");
                        assertTestCase.assertEquals(actualScoreCategory2, rangeAndCategory.getCategory(), "Validating score category");

                        double min = rangeAndCategory.getMin();
                        double max = rangeAndCategory.getMax();

                        List<Map<String, Object>> companiesInCategory = null;

                        try {
                            companiesInCategory = dataMetricsFromDB.stream()
                                    .filter(e -> e.get(metricTitle) != null)
                                    .filter(e -> Double.parseDouble(e.get(metricTitle).toString()) >= min && Double.parseDouble(e.get(metricTitle).toString()) <= max)
                                    .collect(Collectors.toList());
                            System.out.println(companiesInCategory);
                        } catch (NullPointerException e) {
                            assertTestCase.assertEquals(actualCountOfCompanies, Integer.valueOf(0), "Zero count");
                            assertTestCase.assertEquals(actualInvestmentPercentage.doubleValue(), 0d, "Zero Investment");
                            continue;
                        }


                        List<String> companyBVD9s = companiesInCategory.stream()
                                .map(e -> e.get("BVD9_NUMBER"))
                                .map(String::valueOf).collect(Collectors.toList());

                        Integer expectedCountOfCompanies = (int) portfolioToUpload.stream()
                                .filter(e -> companyBVD9s.contains(e.getBVD9_NUMBER())).count();

                        Double totalInvestmentInCategory = portfolioToUpload.stream()
                                .filter(e -> companyBVD9s.contains(e.getBVD9_NUMBER()))
                                .mapToDouble(ResearchLineIdentifier::getValue).sum();

                        Double expectedInvestmentPercentage = PortfolioUtilities.round((totalInvestmentInCategory / totalValues) * 100, 2);
                        assertTestCase.assertEquals(actualCountOfCompanies, expectedCountOfCompanies, "Count verification in " + actualScoreCategory1 + " category");
                        System.out.println("Min score:" + min);
                        System.out.println("Max score:" + max);
                        System.out.println("Score Category:" + rangeAndCategory.getCategory());
                        System.out.println("Score Category Actual:" + actualScoreCategory1);

                        assertTestCase.assertEquals(actualInvestmentPercentage, expectedInvestmentPercentage, "Percentage is not matching in " + actualScoreCategory1 + "category");


                    }
                }
            }

        }


    }


    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {

                        {"all", "all", "operationsrisk", "12", "2020"},
                        {"all", "APAC", "operationsrisk", "12", "2020"},
                        {"all", "EMEA", "operationsrisk", "12", "2020"},
                        {"all", "AMER", "operationsrisk", "12", "2020"},
                        {"all", "AMER", "supplychainrisk", "12", "2020"},
                        {"all", "EMEA", "supplychainrisk", "12", "2020"},
                        {"all", "APAC", "supplychainrisk", "12", "2020"},
                        {"all", "all", "supplychainrisk", "12", "2020"},
                        {"all", "EMEA", "marketrisk", "12", "2020"},
                        {"all", "AMER", "marketrisk", "12", "2020"},
                        {"all", "APAC", "marketrisk", "12", "2020"},
                        {"all", "all", "marketrisk", "12", "2020"},
                        {"all", "all", "operationsrisk", "12", "2021"},
                        {"all", "APAC", "operationsrisk", "12", "2021"},
                        {"all", "EMEA", "operationsrisk", "12", "2021"},
                        {"all", "AMER", "operationsrisk", "12", "2021"},
                        {"all", "AMER", "supplychainrisk", "12", "2021"},
                        {"all", "EMEA", "supplychainrisk", "12", "2021"},
                        {"all", "APAC", "supplychainrisk", "12", "2021"},
                        {"all", "all", "supplychainrisk", "12", "2021"},
                        {"all", "EMEA", "marketrisk", "12", "2021"},
                        {"all", "AMER", "marketrisk", "12", "2021"},
                        {"all", "APAC", "marketrisk", "12", "2021"},
                        {"all", "all", "marketrisk", "12", "2021"},/*,
                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},*/
//                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
//                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
//                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
//                        {"all", "all", "Carbon Footprint", "02", "2021"},
//                        {"all", "APAC", "Carbon Footprint", "03", "2021"},
//                        {"all", "AMER", "Carbon Footprint", "03", "2021"},
//                        {"all", "EMEA", "Carbon Footprint", "03", "2021"},
//                        {"all", "all", "Carbon Footprint", "03", "2021"},
//                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
//                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
//                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
//                        {"all", "all", "Carbon Footprint", "04", "2021"},
//                        {"all", "APAC", "Carbon Footprint", "12", "2020"},
//                        {"all", "AMER", "Carbon Footprint", "12", "2020"},
//                        {"all", "EMEA", "Carbon Footprint", "12", "2020"},
//                        {"all", "all", "Carbon Footprint", "12", "2020"},
                        {"all", "APAC", "Brown Share", "03", "2021"},
                        {"all", "AMER", "Brown Share", "03", "2021"},
                        {"all", "EMEA", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},
                        {"all", "all", "Green Share", "03", "2021"},
                        {"all", "APAC", "Green Share", "03", "2021"},
                        {"all", "EMEA", "Green Share", "03", "2021"},
                        {"all", "AMER", "Green Share", "03", "2021"}
                };
    }

    /*    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")*/
    @Test
    public void brownShareFossilFuel(@Optional String researchLine) {
        String user_id = APIUtilities.userID();
        APIController apiController = new APIController();

        test.info("Import portfolio for user " + user_id);

        Response response = APIUtilities.importScorePortfolio(user_id);

        test.pass("Response received");

        String portfolioID = response.jsonPath().getString("portfolio_id");
        test.info("POST Request sending for UnderLying Data matrix");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");

            /* Steps:
            1. Get API response and deserialize
            2. Get the values from DB using the existing functions for count and name
            3. Assert the values using if conditions for Fossil fuel sections only
             */

        response = apiController
                .getPortfolioUnderlyingDataMetricsResponse(portfolioID, "brownshareasmt", apiFilterPayload);
        response.then().assertThat().statusCode(200);
        response.as(UnderlyingDataMetricsWrapperNew[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        UnderlyingDataMetricsWrapperNew[] array = response.as(UnderlyingDataMetricsWrapperNew[].class);

        // Step 2:
        DatabaseDriver.createDBConnection();
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        List<ResearchLineIdentifier> researchLineIdentifiers = portfolioQueries.getIdentifiersWithPositiveScores("Brown Share", "03", "2021");
        test.info(researchLineIdentifiers.size() + " positive score identifiers added");

        test.pass("UnderLying Data Matrix Call Completed Successfully");
    }
}
