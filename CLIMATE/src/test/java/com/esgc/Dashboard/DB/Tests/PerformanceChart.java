package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.DB.DBModels.EntityWithScores;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.DBModels.DashboardPerformanceChart;
import com.esgc.Dashboard.API.APIModels.PerformanceChartCompany;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class PerformanceChart extends DataValidationTestBase {

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD}, dataProvider = "researchLines")
    @Xray(test = {1319, 3943, 3490, 3475})
    public void verifyPerformanceChartWithMixedIdentifiers(
            @Optional String sector, @Optional String region,
            @Optional String researchLine, @Optional String month, @Optional String year) {
        List<Integer> limits = Arrays.asList(10, 25, 50, 75, 100);
        Collections.shuffle(limits);
        int size = limits.get(0);

        System.out.println(size);

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        Double totalValueInPortfolio = portfolioToUpload.stream()
                .mapToDouble(ResearchLineIdentifier::getValue).sum();

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);


        String fileName = String.format("%s - %s - %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        test.info("Portfolio saved to:");
        test.info(path);
        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.prettyPeek().then().assertThat().body("portfolio_name", Matchers.notNullValue());


        //filter for a given region
        if (!region.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getWORLD_REGION()
                    .equals(dataValidationUtilities.regionFilter)).collect(Collectors.toList());
            region = dataValidationUtilities.regionFilter;
        }

        //filter for a given region
        if (!sector.equals("all")) {
            portfolioToUpload = portfolioToUpload.stream().filter(r -> r.getPLATFORM_SECTOR()
                    .equals(dataValidationUtilities.sectorFilter)).collect(Collectors.toList());
            sector = dataValidationUtilities.sectorFilter;
        }

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));
        System.out.println("portfolio_id:" + portfolioId);

        int countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream().filter(x -> x.getSCORE() >= 0).count();

        test.info("Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio);

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        String coverage = controller.getPortfolioCoverageResponse(portfolioId, researchLine, apiFilterPayload).jsonPath().getString("[0].portfolio_coverage.companies");
        System.out.println("Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio);
        System.out.println("coverage: " + coverage);
        Comparator<ResearchLineIdentifier> compareByValueThenName;

        List<String> performanceChartTypes = Arrays.asList("leaders", "laggards", "largest_holdings");

        for (String performanceChartType : performanceChartTypes) {
            System.out.println("Performance Chart:" + performanceChartType);
            //Get all regions
            List<PerformanceChartCompany> companyList = new ArrayList<>();
            companyList = Arrays.asList(
                    dashboardAPIController.getPerformanceChartList(portfolioId, researchLine, apiFilterPayload, performanceChartType, "" + size)
                            .as(PerformanceChartCompany[].class));

            //Get Score ranges and categories for research line
            // List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);
            companyList.forEach(System.out::println);

            List<String> sortHighToLow = Arrays.asList("Physical Risk Management", "Green Share");
            switch (performanceChartType) {
                case "largest_holdings":

                    compareByValueThenName = Comparator
                            .comparing(ResearchLineIdentifier::getInvestmentPercentage).reversed()
                            .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);

                    break;
                case "leaders":

                    if (sortHighToLow.contains(researchLine))
                        compareByValueThenName = Comparator
                                .comparing(ResearchLineIdentifier::getSCORE).reversed()
                                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
                    else
                        compareByValueThenName = Comparator
                                .comparing(ResearchLineIdentifier::getSCORE)
                                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);

                    break;
                case "laggards":

                    if (sortHighToLow.contains(researchLine))
                        compareByValueThenName = Comparator
                                .comparing(ResearchLineIdentifier::getSCORE)
                                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);
                    else
                        compareByValueThenName = Comparator
                                .comparing(ResearchLineIdentifier::getSCORE).reversed()
                                .thenComparing(ResearchLineIdentifier::getCOMPANY_NAME);


                    break;
                default:
                    throw new IllegalStateException("Unexpected performance chart value: " + performanceChartType);
            }
/*
        //sum up values of duplicated identifiers
        List<ResearchLineIdentifier> expectedList = portfolioToUpload.stream()
                .collect(groupingBy(ResearchLineIdentifier::getBVD9_NUMBER))
                .entrySet().stream()
                .map(e -> e.getValue().stream()
                        .reduce((i1, i2) ->
                                new ResearchLineIdentifier(i1.getISIN(),
                                        i1.getRank(),
                                        i1.getBBG_Ticker(),
                                        i1.getSEDOL_CODE_PRIMARY(),
                                        i1.getSCORE(),
                                        i1.getBVD9_NUMBER(),
                                        i1.getPREVIOUS_PRODUCED_DATE(),
                                        i1.getPREVIOUS_SCORE(),
                                        i1.getPLATFORM_SECTOR(),
                                        i1.getCOMPANY_NAME(),
                                        i1.getCOUNTRY_CODE(),
                                        i1.getWORLD_REGION(),
                                        i1.getInvestmentPercentage(),
                                        i1.getValue() + i2.getValue())))
                .map(java.util.Optional::get)
                .collect(Collectors.toList()).stream()
                .sorted(compareByValueThenName)
                .filter(e -> e.getBVD9_NUMBER() != null)
                .limit(size)
                .collect(Collectors.toList());
*/
            List<EntityWithScores> entityListWithScores = dataValidationUtilities.getIdentifierScoresByDateForAllResearchLines(month, year, portfolioId);


//        //remove entities which has null score in all research lines
//        entityListWithScores = entityListWithScores.stream().filter(e ->
//                e.getBROWN_SHARE_SCORE() != null &&
//                        e.getGREEN_SHARE_SCORE() != null &&
//                        e.getCARBON_SCORE() != null
//        ).collect(Collectors.toList());

            List<String> entityBvd9s = entityListWithScores.stream().map(EntityWithScores::getBVD9_NUMBER).collect(Collectors.toList());

            System.out.println("=====scores vvvvvv====================================");
            entityListWithScores.forEach(System.out::println);

            System.out.println("=====Portfolio vvvvvv====================================");
            portfolioToUpload.forEach(System.out::println);

            portfolioToUpload = portfolioToUpload.stream().filter(e -> entityBvd9s.contains(e.getBVD9_NUMBER()))
                    .collect(Collectors.toList());

            //Because of SP, first entitled Research Line, null scores are removed from performance chart
            List<String> removedCompanies = entityListWithScores.stream()
                    .filter(e -> e.getOPERATIONS_RISK_SCORE() == null &&
                            e.getMARKET_RISK_SCORE() == null &&
                            e.getSUPPLY_CHAIN_RISK_SCORE() == null)
                    .map(EntityWithScores::getBVD9_NUMBER)
                    .collect(Collectors.toList());


            List<ResearchLineIdentifier> expectedList = new ArrayList<>();

            if (performanceChartType.equals("leaders") || performanceChartType.equals("laggards")) {
                expectedList = portfolioToUpload.stream()
                        .filter(e -> e.getSCORE() >= 0)
                        .sorted(compareByValueThenName)
                        .limit(size)
                        .collect(Collectors.toList());
            } else {
                expectedList = portfolioToUpload.stream()
                        .filter(e -> !removedCompanies.contains(e.getBVD9_NUMBER()))
                        .sorted(compareByValueThenName)
                        .limit(size)
                        .collect(Collectors.toList());
            }
//             portfolioToUpload.stream()
//                    .filter(e -> e.getSCORE() >= 0)
//                    .filter(e -> !removedCompanies.contains(e.getBVD9_NUMBER()))
//                    //.filter(e -> e.getBVD9_NUMBER() != null)
//                    .sorted(compareByValueThenName)
//                    .limit(size)
//                    .collect(Collectors.toList());


//            if (researchLine.equals("Temperature Alignment")) {
//                if(performanceChartType.equals("leaders") || performanceChartType.equals("laggards")){
//                    expectedList = portfolioToUpload.stream()
//                            .filter(e -> e.getSCORE() >= 0)
//                            .sorted(compareByValueThenName)
//                            .limit(size)
//                            .collect(Collectors.toList());
//                }else{
//                    expectedList = portfolioToUpload.stream()
//                            .filter(e -> !removedCompanies.contains(e.getBVD9_NUMBER()))
//                            .sorted(compareByValueThenName)
//                            .limit(size)
//                            .collect(Collectors.toList());
//                }
//            }


            System.out.println("=====Expected list vvvvvv====================================");
            expectedList.forEach(System.out::println);

            List<String> companyNames = companyList.stream().map(PerformanceChartCompany::getBVD9_NUMBER).collect(Collectors.toList());
            List<ResearchLineIdentifier> filtered = expectedList.stream().filter(e -> !companyNames.contains(e.getBVD9_NUMBER())).collect(Collectors.toList());

            System.out.println("=====Filtered vvvvvv====================================");
            filtered.forEach(System.out::println);

            /*
            "BVD9_NUMBER" , "Highest Risk Hazard" ,  "% Facilities Exposed to High Risk and Red Flag"
             */

            List<Map<String, Object>> physicalRiskHazardList = dashboardQueries.getPhysicalRiskHazardForPortfolio(portfolioId);

            int totalCompany = 0;
            for (int j = 0; j < expectedList.size(); j++) {
                System.out.println("===============================================");
                PerformanceChartCompany company = companyList.get(j);

                String highestRiskHazardCategory = company.getPHYSICAL_RISK_HAZARD_CATEGORY_NAME();
                String facilitiesExposed = company.getPHYSICAL_RISK_HAZARD_PERCENT_FACILITIES_EXPOSED().toString();

                Map<String, Object> companyInDBResult = physicalRiskHazardList.stream().filter(e -> e.get("BVD9_NUMBER").equals(company.getBVD9_NUMBER()))
                        .findFirst().get();

                System.out.println("companyInDBResult = " + companyInDBResult);
                String companyName = companyInDBResult.get("COMPANY_NAME").toString();

                String expectedHighestRiskHazard = companyInDBResult.get("Highest Risk Hazard").toString();

                String expectedFacilitiesExposed = Double.valueOf(companyInDBResult.get("% Facilities Exposed to High Risk and Red Flag").toString()).intValue() + "";


                assertTestCase.assertEquals(highestRiskHazardCategory, expectedHighestRiskHazard,
                        "Highest Risk Hazard Verification for " + companyName + ":\n" +
                                "actual:" + highestRiskHazardCategory + "\n" +
                                "expected:" + expectedHighestRiskHazard);

                assertTestCase.assertEquals(facilitiesExposed, expectedFacilitiesExposed,
                        "Facilities Exposed Verification for " + companyName + ":\n" +
                                "actual:" + facilitiesExposed + "\n" +
                                "expected:" + expectedFacilitiesExposed);


                Double temperatureAlignmentScore = company.getCURRENT_TEMPERATURE_ALIGNMENT_SCORE();
                RangeAndScoreCategory entityScoreRangeAndCategory4 = getRangeAndScoreCategoryByScore("Temperature Alignment", temperatureAlignmentScore);
                String temperatureAlignmentScoreCategory = company.getSCORE_CATEGORY_TEMPERATURE_ALIGNMENT();
                assertTestCase.assertEquals(temperatureAlignmentScoreCategory, entityScoreRangeAndCategory4.getCategory(), company.getCOMPANY_NAME() + " Score Category verification");

                Integer carbonFootprintScore = company.getCURRENT_CARBON_SCORE();
                RangeAndScoreCategory entityScoreRangeAndCategory5 = getRangeAndScoreCategoryByScore("Carbon Footprint", carbonFootprintScore);
                String carbonFootprintScoreCategory = company.getSCORE_CATEGORY_CARBON_FOOTPRINT();
                assertTestCase.assertEquals(carbonFootprintScoreCategory, entityScoreRangeAndCategory5.getCategory(), company.getCOMPANY_NAME() + " Score Category verification");

                Integer brownShareScore =  (int) Math.round(company.getCURRENT_BROWN_SHARE_SCORE());
                RangeAndScoreCategory entityScoreRangeAndCategory6 = getRangeAndScoreCategoryByScore("Brown Share Ranges", brownShareScore);
                String brownShareScoreCategory = company.getSCORE_CATEGORY_BROWN_SHARE();
                assertTestCase.assertEquals(brownShareScoreCategory, entityScoreRangeAndCategory6.getCategory(), company.getCOMPANY_NAME() + " Score Category verification");

                Integer greenShareScore = company.getCURRENT_GREEN_SHARE_SCORE();
                RangeAndScoreCategory entityScoreRangeAndCategory9 = getRangeAndScoreCategoryByScore("Green Share", greenShareScore);
                String greenShareScoreCategory = company.getSCORE_CATEGORY_GREEN_SHARE();
                assertTestCase.assertEquals(greenShareScoreCategory, entityScoreRangeAndCategory9.getCategory(), company.getCOMPANY_NAME() + " Score Category verification");

                assertTestCase.assertEquals(company.getCOMPANY_NAME(), expectedList.get(j).getCOMPANY_NAME());

                Double investmentPct = PortfolioUtilities.round((expectedList.get(j).getValue() / totalValueInPortfolio) * 100, 6);
                double expectedPercentage = expectedList.get(j).getInvestmentPercentage();
                double actualPercentage = company.getPERCENT_HOLDINGS();


                assertTestCase.assertEquals(actualPercentage, expectedPercentage, String.format("%s  investment not matching", company.getCOMPANY_NAME()));
                assertTestCase.assertEquals(investmentPct, expectedPercentage, String.format("%s  investment not matching", company.getCOMPANY_NAME()));
                System.out.printf("%.2f %50s  Actual", company.getPERCENT_HOLDINGS(), company.getCOMPANY_NAME());
                System.out.println();
                System.out.printf("%.2f %50s   Expected", investmentPct, expectedList.get(j).getCOMPANY_NAME());
                System.out.println();
            }
            assertTestCase.assertTrue(size >= expectedList.size());
            System.out.println("Actual count:" + expectedList.size());
            System.out.println("Expected count:" + size);
        }
    }


    public RangeAndScoreCategory getRangeAndScoreCategoryByScore(String researchLine, Number scoreAsNumber) {
        APIController apiController = new APIController();
        System.out.println("researchLine = " + researchLine);
        System.out.println("scoreAsNumber = " + scoreAsNumber);
        if (researchLine.equals("Temperature Alignment")) {
            try {
                Double score = scoreAsNumber.doubleValue();

                return apiController.getResearchLineRangesAndScoreCategories(researchLine).stream()
                        .filter(e -> e.getMax() >= score && e.getMin() <= score).findFirst().get();
            } catch (Exception e) {
                return new RangeAndScoreCategory(null, (double) Integer.MIN_VALUE, 0d, "");
            }
        } else {
            try {
                Integer score = scoreAsNumber.intValue();

                if (score < 0) return new RangeAndScoreCategory("N/A", (double) Integer.MIN_VALUE, 0d, "");
                return apiController.getResearchLineRangesAndScoreCategories(researchLine).stream()
                        .filter(e -> e.getMax() >= score && e.getMin() <= score).findFirst().get();
            } catch (Exception e) {
                return new RangeAndScoreCategory(null, (double) Integer.MIN_VALUE, 0d, "");
            }
        }
    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {


                        {"sector", "region", "Temperature Alignment", "03", "2022"},
                        {"sector", "all", "Temperature Alignment", "03", "2022"},
                        {"all", "region", "Temperature Alignment", "03", "2022"},
                        {"all", "all", "Temperature Alignment", "03", "2022"},
                        {"sector", "region", "Carbon Footprint", "02", "2021"},
                        {"sector", "all", "Carbon Footprint", "02", "2021"},
                        {"all", "region", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                        {"sector", "region", "Brown Share", "03", "2021"},
                        {"sector", "all", "Brown Share", "03", "2021"},
                        {"all", "region", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},
                        {"all", "all", "Green Share", "03", "2021"},
                        {"sector", "all", "Green Share", "03", "2021"},
                        {"all", "region", "Green Share", "03", "2021"},
                        {"sector", "region", "Green Share", "03", "2021"},

                        {"all", "region", "Physical Risk Management", "12", "2020"},
                        {"sector", "all", "Physical Risk Management", "12", "2020"},
                        {"sector", "region", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},


                        /**  { "all", "APAC", "Carbon Footprint", "03", "2021"},
                        { "all", "AMER", "Carbon Footprint", "03", "2021"},
                        { "all", "EMEA", "Carbon Footprint", "03", "2021"},
                        { "all", "all", "Carbon Footprint", "03", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},*/
                        /*        {"all", "APAC", "Carbon Footprint", "12", "2020"},
                                {"all", "AMER", "Carbon Footprint", "12", "2020"},
                                {"all", "EMEA", "Carbon Footprint", "12", "2020"},
                                {"all", "all", "Carbon Footprint", "12", "2020"},
                                {"all", "APAC", "Energy Transition Management", "03", "2021"},
                                {"all", "AMER", "Energy Transition Management", "03", "2021"},
                                {"all", "EMEA", "Energy Transition Management", "03", "2021"},
                                {"all", "all", "Energy Transition Management", "03", "2021"},

                                {"all", "all", "TCFD", "03", "2021"},
                                {"all", "APAC", "TCFD", "03", "2021"},
                                {"all", "AMER", "TCFD", "03", "2021"},
                                {"all", "EMEA", "TCFD", "03", "2021"},*/


                };


    }
        @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
        @Xray(test = {4336})
        public void validateBrownshare(){
            String portfolioId = "00000000-0000-0000-0000-000000000000" ;
            APIFilterPayload apiFilterPayload = new APIFilterPayload();
            apiFilterPayload.setSector("all");
            apiFilterPayload.setRegion("all");
            apiFilterPayload.setBenchmark("");
            apiFilterPayload.setYear("2022");
            apiFilterPayload.setMonth("12");
            List<String> performanceChartTypes = Arrays.asList("leaders", "laggards");

            for (String performanceChartType : performanceChartTypes) {
                System.out.println("Performance Chart:" + performanceChartType);
                //Get all regions
                List<PerformanceChartCompany> companyList = Arrays.asList(
                        dashboardAPIController.getPerformanceChartList(portfolioId, "brownshareasmt", apiFilterPayload, performanceChartType, "10")
                                .as(PerformanceChartCompany[].class));
                System.out.println(companyList);

                List<DashboardPerformanceChart> dbResult= DashboardQueries.getBrownShareResultd(portfolioId, "2022", "12");

                System.out.println(dbResult);

                for (int i = 0 ; i < companyList.size(); i++){
                    int counter = i;
                    DashboardPerformanceChart dbRow = dbResult.stream().filter(e -> e.getBVD9_NUMBER().equals(companyList.get(counter).getBVD9_NUMBER())).findFirst().get();
                    Assert.assertEquals(dbRow.getSCORE_RANGE(),companyList.get(counter).getSCORE_CATEGORY_BROWN_SHARE(), "Validate Brown Share Category");
                    if (companyList.get(counter).getCURRENT_BROWN_SHARE_SCORE()==null){
                        Assert.assertEquals(dbRow.getBS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE(),"NA", "Validating when accurate score is not present");
                    }else{
                        Assert.assertEquals(String.format("%.2f",Double.valueOf(dbRow.getBS_FOSF_INDUSTRY_REVENUES_ACCURATE_SOURCE())),String.format("%.2f",Double.valueOf(companyList.get(counter).getCURRENT_BROWN_SHARE_SCORE())), "Validating when accurate score is present");
                    }

                }
            }


        }


}
