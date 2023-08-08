package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.API.APIModels.APIEntityListPayload;
import com.esgc.Dashboard.API.APIModels.GeoMapCountryEntity;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.PortfolioAnalysis.API.APIModels.RegionMap;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class GeographicRiskMap extends DataValidationTestBase {

    //Test cases ESGT-3562 ESGT-3471 ESGT-3593 ESGT-3589 ESGT-3470  ESGT-3516
    @Test(enabled = false, groups = {REGRESSION, DATA_VALIDATION, DASHBOARD}, dataProvider = "researchLines", threadPoolSize = 1)
    @Xray(test = {3562, 3471, 3593, 3589, 3470, 3516})
    public void verifyGeographicRiskMap(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        List<ResearchLineIdentifier> portfolioForCalculations = new ArrayList<>(portfolioToUpload);
        double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
        System.out.println(portfolioToUpload);


        String fileName = String.format("Geo Map %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        test.info("Portfolio saved to:");
        test.info(path);
        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().log().all().assertThat().body("portfolio_name", Matchers.notNullValue());

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


        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));
        List<ResearchLineIdentifier> uniqueList = portfolioToUpload.stream()
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getBVD9_NUMBER))
                .filter(e -> e.getBVD9_NUMBER() != null).collect(Collectors.toList());
        System.out.println("uniqueList = " + uniqueList);
        int countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream()
                .filter(PortfolioUtilities.distinctByKey(ResearchLineIdentifier::getBVD9_NUMBER))
                .filter(e -> e.getBVD9_NUMBER() != null)
                .count();

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


        //Get all country details
        List<RegionMap> countryList = Arrays.asList(
                controller.getPortfolioRegionMapResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionMap[].class));
        System.out.println(countryList);

        //Get Score ranges and categories for research line
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        //loop through Countries
        for (int i = 0; i < countryList.size(); i++) {
            System.out.println("#######################################################");
            RegionMap countryDetails = countryList.get(i);
            test.info(String.format("Check %s Country Details in %s Region", countryDetails.getCountry_name(), countryDetails.getRegion()));

            System.out.println(String.format("Check %s Country Details in %s Region", countryDetails.getCountry_name(), countryDetails.getRegion()));
            //score category check
            RangeAndScoreCategory scoreRangeAndCategory;
            try {
                scoreRangeAndCategory = rangeAndCategoryList.stream()
                        .filter(e -> e.getCategory().equals(countryDetails.getScore_category())).findFirst().get();
            } catch (Exception e) {
                scoreRangeAndCategory = new RangeAndScoreCategory("N/A", (double) Integer.MIN_VALUE, 0d, "");
            }

            System.out.println("============");
            System.out.println("countryDetails.getScoreCategory() = " + countryDetails.getScore_category());
            System.out.println("ScoreRangeAndCategory.getCategory() = " + scoreRangeAndCategory.getCategory());
            assertTestCase.assertEquals(countryDetails.getScore_category(), scoreRangeAndCategory.getCategory());

            assertTestCase.assertEquals(countryDetails.getScore_category(), scoreRangeAndCategory.getCategory());

            //weighted average score and category calculations
            Double totalWeightedAverage = portfolioUtilities.weightedAverageScore(
                    portfolioToUpload.stream()
                            .filter(r -> r.getCOUNTRY_CODE().equalsIgnoreCase(countryDetails.getCountry_code())).collect(Collectors.toList())
            );

            Double totalValueInCountry = portfolioToUpload.stream()
                    .filter(r -> r.getCOUNTRY_CODE().equalsIgnoreCase(countryDetails.getCountry_code()))
                    .mapToDouble(ResearchLineIdentifier::getValue).sum();

            List<ResearchLineIdentifier> researchLineIdentifiersInCountry = portfolioToUpload.stream()
                    .filter(r ->

                            r.getCOUNTRY_CODE().equalsIgnoreCase(countryDetails.getCountry_code())

                    ).collect(Collectors.toList());
            System.out.println("researchLineIdentifiersInCountry = " + researchLineIdentifiersInCountry);

            Double expectedScore = totalWeightedAverage == null ? null : researchLine.equals("Temperature Alignment") ?
                    PortfolioUtilities.round(totalWeightedAverage, 2) : Math.round(totalWeightedAverage);


            double maxScore = scoreRangeAndCategory.getMax();
            double minScore = scoreRangeAndCategory.getMin();
            double actualCountryScore = PortfolioUtilities.round(countryDetails.getScore(), 2);
            String actualScoreCategory = countryDetails.getScore_category();
            String expectedScoreCategory = scoreRangeAndCategory.getCategory();

            double totalInvestmentInCountry = researchLineIdentifiersInCountry.stream().filter(e -> e.getSCORE() >= 0)
                    .mapToDouble(ResearchLineIdentifier::getValue).sum();

            if (researchLine.equals("Temperature Alignment")) {
                totalInvestmentInCountry = researchLineIdentifiersInCountry.stream().filter(e -> e.getSCORE() >= -1)
                        .mapToDouble(ResearchLineIdentifier::getValue).sum();
            }

            double expectedInvestmentInCountry = PortfolioUtilities.round((totalInvestmentInCountry / totalValues) * 100, 2);
            String expectedInvestmentPctInCountry = String.format("%.2f", expectedInvestmentInCountry);
            String actualInvestmentPctInCountry = String.format("%.2f", countryDetails.getHoldings());

            System.out.println("minScore = " + minScore);
            System.out.println("maxScore = " + maxScore);
            System.out.println("Actual countryScore = " + actualCountryScore);
            System.out.println("Expected countryScore = " + expectedScore);
            System.out.println("actualScoreCategory = " + actualScoreCategory);
            System.out.println("expectedScoreCategory = " + expectedScoreCategory);
            System.out.println("Expected %Investment = " + expectedInvestmentPctInCountry);
            System.out.println("Actual %Investment = " + actualInvestmentPctInCountry);

            if (researchLine.equals("Temperature Alignment") && actualCountryScore == -1) {
                assertTestCase.assertTrue(researchLineIdentifiersInCountry.stream().noneMatch(e -> e.getSCORE() > 0), String.format("%s Weighted Avg Score Validation", countryDetails.getCountry_name()));
            } else {
                assertTestCase.assertEquals(actualCountryScore, expectedScore, String.format("%s Weighted Avg Score Validation", countryDetails.getCountry_name()));
            }
            assertTestCase.assertTrue(actualCountryScore <= maxScore && actualCountryScore >= minScore, String.format("%s Score Category Validation", countryDetails.getCountry_name()));
            assertTestCase.assertEquals(actualScoreCategory, expectedScoreCategory, "Country average score category validation");
            assertTestCase.assertEquals(actualInvestmentPctInCountry, expectedInvestmentPctInCountry, " Percentages are not matching");

            APIEntityListPayload apiEntityListPayload = new APIEntityListPayload();
            apiEntityListPayload.setSector(sector);
            apiEntityListPayload.setRegion(region);
            apiEntityListPayload.setCountry_code(countryDetails.getCountry_code());
            apiEntityListPayload.setYear(year);
            apiEntityListPayload.setMonth(month);

            //Get all entities details
            List<GeoMapCountryEntity> geoMapCountryEntityList = Arrays.asList(
                    dashboardAPIController.getGeoMapEntityListResponse(portfolioId, researchLine, apiEntityListPayload)
                            .as(GeoMapCountryEntity[].class));
            System.out.println(geoMapCountryEntityList);

            //number of companies number in country
            Integer countOfCompaniesInCountry = (int) portfolioToUpload.stream()
                    .filter(r -> r.getCOUNTRY_CODE().equalsIgnoreCase(countryDetails.getCountry_code())).count();

            int totalCompany = 0; //to check if total of companies are equal in country distribution
            Double totalInvestmentPct = 0d; //to check if total of percentages are equal in country distribution
            test.info("Check each entity in country distribution");

            for (int j = 0; j < geoMapCountryEntityList.size(); j++) {
                System.out.println("===============================================");
                GeoMapCountryEntity geoMapCountryEntity = geoMapCountryEntityList.get(j);
                RangeAndScoreCategory entityScoreRangeAndCategory;
                try {
                    entityScoreRangeAndCategory = rangeAndCategoryList.stream()
                            .filter(e -> e.getMax() >= geoMapCountryEntity.getScore() && e.getMin() <= geoMapCountryEntity.getScore()).findFirst().get();
                } catch (Exception e) {
                    entityScoreRangeAndCategory = new RangeAndScoreCategory("N/A", (double) Integer.MIN_VALUE, 0d, "");
                }
                assertTestCase.assertEquals(geoMapCountryEntity.getScore_category(), entityScoreRangeAndCategory.getCategory(), "Score Category verification");

                Double min = (double) entityScoreRangeAndCategory.getMin();
                Double max = (double) entityScoreRangeAndCategory.getMax();
                System.out.println("Min score:" + min);
                System.out.println("Max score:" + max);
                System.out.println("Score Category:" + entityScoreRangeAndCategory.getCategory());
                System.out.println("Score Category Actual:" + geoMapCountryEntity.getScore_category());
                System.out.println("BVD9 " + geoMapCountryEntity.getBvd9_number());

                System.out.println("portfolioForCalculations = " + portfolioForCalculations);

                int companycount = (int) portfolioForCalculations.stream()
                        .filter(each -> each.getBVD9_NUMBER().equals(geoMapCountryEntity.getBvd9_number())).count();

                System.out.println("companycount = " + companycount);
                Double companyValue = portfolioToUpload.stream()
                        .filter(each -> each.getBVD9_NUMBER().equals(geoMapCountryEntity.getBvd9_number()))
                        .mapToDouble(ResearchLineIdentifier::getValue).sum();
                System.out.println("companyValue = " + companyValue);
                System.out.println("totalValues = " + totalValues);
                double percentage = (companyValue / totalValues) * 100;
                Double expectedPercentage = PortfolioUtilities.round(percentage, 2);
                Double actualPercentage = PortfolioUtilities.round(geoMapCountryEntity.getHoldings(), 2);

                System.out.println("Actual:" + actualPercentage);
                System.out.println("Expected:" + expectedPercentage);
                assertTestCase.assertEquals(actualPercentage, expectedPercentage, String.format("%% investment in %s entity validation", geoMapCountryEntity.getCompany_name()));
                if (geoMapCountryEntity.getScore() >= 0) {
                    totalInvestmentPct += percentage;
                    totalCompany++;
                }

                if (researchLine.equals("Temperature Alignment") && (geoMapCountryEntity.getScore() < 0 && geoMapCountryEntity.getScore() >= -1)) {
                    totalInvestmentPct += percentage;
                    totalCompany++;
                }
            }

            System.out.println("Actual count:" + totalCompany);
            System.out.println("Expected count:" + countryDetails.getNumber_of_companies());

            Double totalInvestment = PortfolioUtilities.round(totalInvestmentPct, 2);
            Double expectedTotalInvestment = PortfolioUtilities.round(countryDetails.getHoldings(), 2);

            assertTestCase.assertEquals(totalCompany, countryDetails.getNumber_of_companies(), "Total count in Country Distribution match with Total count of Entities");
            assertTestCase.assertEquals(totalInvestment, expectedTotalInvestment, "Total % investment in Country Distribution match with Sum of Entity List");

            System.out.println("Total investment: " + totalInvestment);
            System.out.println("expected Investment: " + expectedTotalInvestment);
        }

    }


    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {

                        {"all", "all", "Temperature Alignment", "03", "2022"},
                        {"all", "APAC", "Temperature Alignment", "03", "2022"},
                        {"all", "EMEA", "Temperature Alignment", "03", "2022"},
                        {"all", "AMER", "Temperature Alignment", "03", "2022"},

                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},


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
                        {"all", "all", "marketrisk", "12", "2021"},


                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                        {"all", "APAC", "Carbon Footprint", "03", "2021"},
                        {"all", "AMER", "Carbon Footprint", "03", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "03", "2021"},
                        {"all", "all", "Carbon Footprint", "03", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},
                        {"all", "APAC", "Carbon Footprint", "12", "2020"},
                        {"all", "AMER", "Carbon Footprint", "12", "2020"},
                        {"all", "EMEA", "Carbon Footprint", "12", "2020"},
                        {"all", "all", "Carbon Footprint", "12", "2020"},

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
}
