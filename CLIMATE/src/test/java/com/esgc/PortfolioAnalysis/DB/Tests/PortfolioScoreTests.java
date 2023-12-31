package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.EntityProfile.DB.DBModels.PhysicalScore;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioPhysicalHazard;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.DataProviderUtilities;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class PortfolioScoreTests extends DataValidationTestBase {


    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {4883, 4419, 2094, 4284, 4808, 4918, 4503, 4970, 4465, 4348, 5079, 4609,
            4552, 4703, 4675, 1987, 1302, 3550, 3693,
            3984,
            4497,//ESG predicted
            3689
    })
    //TCFD 1992
    //Energy Transition 3027, 3029, 3020, 3019, 3024
    public void verifyPortfolioScoreWithMixedIdentifiers(
            //@Optional String sector, @Optional String region,
            @Optional String researchLine,
            @Optional String month, @Optional String year) {

        //TODO ESG will not be checked
        if(researchLine.contains("ESG"))
            throw new SkipException("ESG is in scope");
        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        String fileName = String.format("Portfolio Score %s - %s - %s - %s - %s", researchLine, dataValidationUtilities.sectorFilter, dataValidationUtilities.regionFilter, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);

        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().log().all().assertThat().body("portfolio_name", Matchers.notNullValue());
        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);
        for (int i = 1; i <= 4; i++) {
            List<ResearchLineIdentifier> portfolio = new ArrayList<>(portfolioToUpload);
            String region = "all";
            String sector = "all";
            if (i == 2 || i == 4) {
                portfolio = portfolio.stream().filter(r -> r.getWORLD_REGION()
                        .equals(dataValidationUtilities.regionFilter)).collect(Collectors.toList());
                region = dataValidationUtilities.regionFilter;
            }

            if (i == 3 || i == 4) {
                portfolio = portfolio.stream().filter(r -> r.getPLATFORM_SECTOR()
                        .equals(dataValidationUtilities.sectorFilter)).collect(Collectors.toList());
                sector = dataValidationUtilities.sectorFilter;
            }

            int countOfDistinctCompaniesInPortfolio = dataValidationUtilities.getCoverageOfPortfolio(portfolio);


            test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));

            double totalValues = portfolioUtilities.calculateTotalSumOfInvestmentWithoutNulls(portfolio);

            //request portfolio score from API
            APIFilterPayload apiFilterPayload = new APIFilterPayload();
            apiFilterPayload.setSector(sector);
            apiFilterPayload.setRegion(region);
            apiFilterPayload.setBenchmark("");
            apiFilterPayload.setYear(year);
            apiFilterPayload.setMonth(month);
            System.out.println("region = " + region);
            System.out.println("sector = " + sector);
            String coverage = controller.getPortfolioCoverageResponse(portfolioId, researchLine, apiFilterPayload).jsonPath().getString("[0].portfolio_coverage.companies");
            System.out.println("Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio);
            System.out.println("coverage: " + coverage);

            //calculate total weighted average
            Double totalWeightedAverage = portfolioUtilities.weightedAverageScore(portfolio);
            System.out.println("Calculated totalWeighted Average is :" + totalWeightedAverage);
            if (researchLine.equals("ESG")) {
                totalWeightedAverage = portfolioUtilities.ESGWeightedAverageScore(portfolio);
            }

            //calculate portfolio score according to the filters given
            Double expectedScore = totalWeightedAverage == null ? null : researchLine.endsWith("Share") || researchLine.equals("Temperature Alignment") || researchLine.equals("ESG") ?
                    PortfolioUtilities.round(totalWeightedAverage, 2) : Math.round(totalWeightedAverage);
            System.out.println("Calculated score is :" + expectedScore);

            List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

            String expectedScoreCategory = expectedScore == null ? null : rangeAndCategoryList.stream()
                    .filter(e -> e.getMin() <= expectedScore && e.getMax() >= expectedScore).findFirst().orElse(null).getCategory();

            if (researchLine.equals("ESG")) {
                expectedScoreCategory = rangeAndCategoryList.stream()
                        .filter(e -> e.getMinScale() <= expectedScore && e.getMaxScale() >= expectedScore).findFirst().orElse(null).getCategory();
            }

            response = controller.getPortfolioScoreResponse(portfolioId, researchLine, apiFilterPayload).prettyPeek();

            Double actualScore = null;
            try {
                actualScore = PortfolioUtilities.round(dataValidationUtilities.getPortfolioScoreFromPortfolioScoreResponse(researchLine, response), 2);
            } catch (NullPointerException e) {
                System.out.println("There is No Score to show");
            }
            String actualScoreCategory = dataValidationUtilities.getPortfolioScoreCategoryFromPortfolioScoreResponse(researchLine, response);

            assertTestCase.assertEquals(actualScore, expectedScore, "Portfolio Score Verification for " + researchLine + " in " + region + " - " + sector);
            assertTestCase.assertEquals(actualScoreCategory, expectedScoreCategory, "Portfolio Score Category Verification for " + researchLine + " in " + region + " - " + sector);
        }
    }


    //TODO needs to be updated
    @Test(groups = {REGRESSION, DATA_VALIDATION})
    @Xray(test = {3715, 3523})
    public void verifyPhysicalHazardPortfolioScore(
            //@Optional String sector, @Optional String region,
            @Optional String researchLine,
            @Optional String month, @Optional String year) {

        researchLine = "physicalriskhazard";
        month = "12";
        year = "2021";

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        String fileName = String.format("Portfolio Score %s - %s - %s - %s - %s", researchLine, dataValidationUtilities.sectorFilter, dataValidationUtilities.regionFilter, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);

        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().log().all().assertThat().body("portfolio_name", Matchers.notNullValue());
        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);
        test.info(String.format("Filter= %s %s %s %s", "all", "all", month, year));

        //request portfolio score from API
        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector("all");
        apiFilterPayload.setRegion("all");
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        List<PortfolioPhysicalHazard> apiResponse = Arrays.asList(controller.getPortfolioScoreResponse(portfolioId, researchLine, apiFilterPayload)
                .as(PortfolioPhysicalHazard[].class));
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        PhysicalScore score = portfolioQueries.getPhysicalHazardScore("'" + portfolioId + "'", year + "||" + month);
        assertTestCase.assertEquals(score.getHighest_risk_hazard(),apiResponse.get(0).getHighest_risk_hazard());
        assertTestCase.assertEquals(score.getFacilities_exposed(),apiResponse.get(0).getFacilities_exposed());
        assertTestCase.assertEquals(score.getHrh_risk_category(),apiResponse.get(0).getHrh_risk_category());
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, ESG})
    @Xray(test = {4833})
    public void verifyESGAssessmentPortfolioScore(
            //@Optional String sector, @Optional String region,
            @Optional String researchLine,
            @Optional String month, @Optional String year) {

        researchLine = "ESG";
        month = "08";
        year = "2022";

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        String fileName = String.format("Portfolio Score %s - %s - %s - %s - %s", researchLine, dataValidationUtilities.sectorFilter, dataValidationUtilities.regionFilter, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);

        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().log().all().assertThat().body("portfolio_name", Matchers.notNullValue());
        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);
        test.info(String.format("Filter= %s %s %s %s", "all", "all", month, year));

        //request portfolio score from API
        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector("all");
        apiFilterPayload.setRegion("all");
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        List<PortfolioPhysicalHazard> apiResponse = Arrays.asList(controller.getPortfolioScoreResponse(portfolioId, researchLine, apiFilterPayload)
                .as(PortfolioPhysicalHazard[].class));
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        PhysicalScore score = portfolioQueries.getPhysicalHazardScore("'" + portfolioId + "'", year + "||" + month);
        assertTestCase.assertTrue(score.getHighest_risk_hazard().equals(apiResponse.get(0).getHighest_risk_hazard()));
        assertTestCase.assertTrue(score.getFacilities_exposed().equals(apiResponse.get(0).getFacilities_exposed()));
        assertTestCase.assertTrue(score.getHrh_risk_category().equals(apiResponse.get(0).getHrh_risk_category()));
    }

    @DataProvider(name = "rs")
    public Object[][] research() {
        return DataProviderUtilities.getDataProvider();
    }

    @Test(dataProvider = "rs")
    public void v(String rl, String month, String year) {
        System.out.println(rl + " " + month + " " + year + " ");
    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"ESG", "09", "2022"},
                        {"ESG", "08", "2022"},
                        {"ESG", "07", "2022"},
                        {"ESG", "06", "2022"},
                        {"Temperature Alignment", "03", "2022"},
                        {"Temperature Alignment", "03", "2022"},
                        {"Temperature Alignment", "03", "2022"},
                        {"Temperature Alignment", "03", "2022"},

                        {"operationsrisk", "12", "2021"},
                        {"supplychainrisk", "12", "2021"},
                        {"marketrisk", "12", "2021"},

                        {"operationsrisk", "12", "2020"},
                        {"supplychainrisk", "12", "2020"},
                        {"marketrisk", "12", "2020"},

                        {"Carbon Footprint", "03", "2021"},
                        {"Carbon Footprint", "02", "2021"},
                        {"Carbon Footprint", "12", "2021"},
                        {"Carbon Footprint", "04", "2021"},

                        {"Brown Share", "03", "2021"},

                        {"Green Share", "03", "2021"},

                };
    }

                        /*@DataProvider(name = "researchLines", parallel = true)
                        public Object[][] provideFilterParameters() {

                            return new Object[][]
                                    {
                                            {"all", "all", "operationsrisk", "12", "2021"},
                                            {"all", "region", "operationsrisk", "12", "2021"},
                                            {"sector", "all", "operationsrisk", "12", "2021"},
                                            {"sector", "region", "operationsrisk", "12", "2021"},
                                            {"sector", "region", "supplychainrisk", "12", "2021"},
                                            {"sector", "all", "supplychainrisk", "12", "2021"},
                                            {"all", "region", "supplychainrisk", "12", "2021"},
                                            {"all", "all", "supplychainrisk", "12", "2021"},
                                            {"sector", "all", "marketrisk", "12", "2021"},
                                            {"sector", "region", "marketrisk", "12", "2021"},
                                            {"all", "region", "marketrisk", "12", "2021"},
                                            {"all", "all", "marketrisk", "12", "2021"},


                                            {"all", "all", "operationsrisk", "12", "2020"},
                                            {"all", "region", "operationsrisk", "12", "2020"},
                                            {"sector", "all", "operationsrisk", "12", "2020"},
                                            {"sector", "region", "operationsrisk", "12", "2020"},
                                            {"sector", "region", "supplychainrisk", "12", "2020"},
                                            {"sector", "all", "supplychainrisk", "12", "2020"},
                                            {"all", "region", "supplychainrisk", "12", "2020"},
                                            {"all", "all", "supplychainrisk", "12", "2020"},
                                            {"sector", "all", "marketrisk", "12", "2020"},
                                            {"sector", "region", "marketrisk", "12", "2020"},
                                            {"all", "region", "marketrisk", "12", "2020"},
                                            {"all", "all", "marketrisk", "12", "2020"},


                                            {"sector", "region", "Physical Risk Management", "12", "2020"},
                                            {"sector", "all", "Physical Risk Management", "12", "2020"},
                                            {"all", "region", "Physical Risk Management", "12", "2020"},
                                            {"all", "all", "Physical Risk Management", "12", "2020"},
                                            {"sector", "region", "Carbon Footprint", "02", "2021"},
                                            {"sector", "all", "Carbon Footprint", "02", "2021"},
                                            {"all", "region", "Carbon Footprint", "02", "2021"},
                                            {"all", "all", "Carbon Footprint", "02", "2021"},
                                            {"all", "region", "Carbon Footprint", "03", "2021"},
                                            {"sector", "region", "Carbon Footprint", "03", "2021"},
                                            {"sector", "all", "Carbon Footprint", "03", "2021"},
                                            {"all", "all", "Carbon Footprint", "03", "2021"},
                                            {"all", "region", "Carbon Footprint", "04", "2021"},
                                            {"sector", "region", "Carbon Footprint", "04", "2021"},
                                            {"sector", "all", "Carbon Footprint", "04", "2021"},
                                            {"all", "all", "Carbon Footprint", "04", "2021"},
                                            {"all", "region", "Carbon Footprint", "12", "2020"},
                                            {"sector", "region", "Carbon Footprint", "12", "2020"},
                                            {"sector", "all", "Carbon Footprint", "12", "2020"},
                                            {"all", "all", "Carbon Footprint", "12", "2020"},
                                            {"all", "region", "Energy Transition Management", "03", "2021"},
                                            {"sector", "region", "Energy Transition Management", "03", "2021"},
                                            {"sector", "all", "Energy Transition Management", "03", "2021"},
                                            {"all", "all", "Energy Transition Management", "03", "2021"},
                                            {"all", "region", "Brown Share", "03", "2021"},
                                            {"sector", "region", "Brown Share", "03", "2021"},
                                            {"sector", "all", "Brown Share", "03", "2021"},
                                            {"all", "all", "Brown Share", "03", "2021"},
                                            {"all", "all", "TCFD", "03", "2021"},
                                            {"all", "region", "TCFD", "03", "2021"},
                                            {"sector", "region", "TCFD", "03", "2021"},
                                            {"sector", "all", "TCFD", "03", "2021"},
                                            {"all", "all", "Green Share", "03", "2021"},
                                            {"all", "region", "Green Share", "03", "2021"},
                                            {"sector", "all", "Green Share", "03", "2021"},
                                            {"sector", "region", "Green Share", "03", "2021"}
                                    };
                        }

                        @DataProvider(name = "researchLinesSubScores", parallel = true)
                        public Object[][] provideFilterParameters2() {

                            return new Object[][]
                                    {


                                            {"sector", "region", "Physical Risk Management", "12", "2020"},
                                            {"sector", "all", "Physical Risk Management", "12", "2020"},
                                            {"all", "region", "Physical Risk Management", "12", "2020"},
                                            {"all", "all", "Physical Risk Management", "12", "2020"},

                                            {"all", "region", "Energy Transition Management", "03", "2021"},
                                            {"sector", "region", "Energy Transition Management", "03", "2021"},
                                            {"sector", "all", "Energy Transition Management", "03", "2021"},
                                            {"all", "all", "Energy Transition Management", "03", "2021"},

                                            {"all", "all", "TCFD", "03", "2021"},
                                            {"all", "region", "TCFD", "03", "2021"},
                                            {"sector", "region", "TCFD", "03", "2021"},
                                            {"sector", "all", "TCFD", "03", "2021"}

                                    };
                        }*/


}
