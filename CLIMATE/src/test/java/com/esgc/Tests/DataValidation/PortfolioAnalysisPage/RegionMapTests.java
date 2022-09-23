package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.RangeAndScoreCategory;
import com.esgc.APIModels.RegionMap;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RegionMapTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines", threadPoolSize = 10)
    @Xray(test = {4758, 4755, 4752, 4751, 4759, 4756, 4754, 4753})
    //Energy Transition 4757
    //TCFD 4758
    public void verifyRegionMap(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) {

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        String fileName = String.format("Region Map - %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
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
        test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));

        int countOfDistinctCompaniesInPortfolio = dataValidationUtilities.getCoverageOfPortfolio(portfolioToUpload);
        System.out.println("portfolio_id:" + portfolioId);

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
            RangeAndScoreCategory scoreRangeAndCategory = rangeAndCategoryList.stream()
                    .filter(e -> e.getCategory().equals(countryDetails.getScore_category())).findFirst().get();
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

            Double expectedScore = totalWeightedAverage == null ? null : researchLine.equals("Temperature Alignment") ?
                    PortfolioUtilities.round(totalWeightedAverage, 2) : Math.round(totalWeightedAverage);

            double maxScore = scoreRangeAndCategory.getMax();
            double minScore = scoreRangeAndCategory.getMin();
            double actualCountryScore = PortfolioUtilities.round(countryDetails.getScore(), 2);
            String actualScoreCategory = countryDetails.getScore_category();
            String expectedScoreCategory = scoreRangeAndCategory.getCategory();

            System.out.println("minScore = " + minScore);
            System.out.println("maxScore = " + maxScore);
            System.out.println("Actual countryScore = " + actualCountryScore);
            System.out.println("Expected countryScore = " + expectedScore);
            System.out.println("actualScoreCategory = " + actualScoreCategory);
            System.out.println("expectedScoreCategory = " + expectedScoreCategory);

            if (actualCountryScore != -1 && !researchLine.equals("Temperature Alignment"))
                assertTestCase.assertEquals(actualCountryScore, expectedScore, String.format("%s Weighted Avg Score Validation", countryDetails.getCountry_name()));
            assertTestCase.assertTrue(actualCountryScore <= maxScore && actualCountryScore >= minScore, String.format("%s Score Category Validation", countryDetails.getCountry_name()));
            assertTestCase.assertEquals(actualScoreCategory, expectedScoreCategory, "Country average score category validation");

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
//                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
//                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
//                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
//                        {"all", "all", "Physical Risk Management", "12", "2020"},
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
//                        {"all", "APAC", "Energy Transition Management", "03", "2021"},
//                        {"all", "AMER", "Energy Transition Management", "03", "2021"},
//                        {"all", "EMEA", "Energy Transition Management", "03", "2021"},
//                        {"all", "all", "Energy Transition Management", "03", "2021"},
                        {"all", "APAC", "Brown Share", "03", "2021"},
                        {"all", "AMER", "Brown Share", "03", "2021"},
                        {"all", "EMEA", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},
//                        {"all", "all", "TCFD", "03", "2021"},
//                        {"all", "APAC", "TCFD", "03", "2021"},
//                        {"all", "AMER", "TCFD", "03", "2021"},
//                        {"all", "EMEA", "TCFD", "03", "2021"},
                        {"all", "all", "Green Share", "03", "2021"},
                        {"all", "APAC", "Green Share", "03", "2021"},
                        {"all", "EMEA", "Green Share", "03", "2021"},
                        {"all", "AMER", "Green Share", "03", "2021"}
                };
                /*{
                        {"all", "all", "Green Share", "03", "2021"},
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
                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},
                    /*    {"all", "APAC", "Carbon Footprint", "02", "2021"},
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
                        {"all", "APAC", "Energy Transition Management", "03", "2021"},
                        {"all", "AMER", "Energy Transition Management", "03", "2021"},
                        {"all", "EMEA", "Energy Transition Management", "03", "2021"},
                        {"all", "all", "Energy Transition Management", "03", "2021"},
                        {"all", "APAC", "Brown Share", "03", "2021"},
                        {"all", "AMER", "Brown Share", "03", "2021"},
                        {"all", "EMEA", "Brown Share", "03", "2021"},
                        {"all", "all", "Brown Share", "03", "2021"},
                        {"all", "all", "TCFD", "03", "2021"},
                        {"all", "APAC", "TCFD", "03", "2021"},
                        {"all", "AMER", "TCFD", "03", "2021"},
                        {"all", "EMEA", "TCFD", "03", "2021"},
                        {"all", "all", "Green Share", "03", "2021"},
                        {"all", "APAC", "Green Share", "03", "2021"},
                        {"all", "EMEA", "Green Share", "03", "2021"},
                        {"all", "AMER", "Green Share", "03", "2021"}
                };*/
    }

}
