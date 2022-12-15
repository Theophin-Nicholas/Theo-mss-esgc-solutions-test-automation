package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioDistribution;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioDistributionWrapper;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.PortfolioAnalysis.DB.DBModels.ESGLeaderANDLaggers;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class PortfolioDistributionTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {2704, 2703, 2558, 2557, 2188, 2189, 2559,
            2705, 2190, 2192, 2315, 2308, 2309, 2560, 2706, 2191, 2310, 2561, 2707,
            4217, 4216, 4218, 4221,
            6744,
            11060,11061 //ESG Predicted
    })
    //Operations risk 4212, 2306, 2310, 2308, 2309, 2315
    //Market risk 4214, 4211
    //supply chain risk 4215
    //TCFD 4220
    //Energy Transition 4219
    public void verifyPortfolioDistributionWithMixedIdentifiers(@Optional String sector, @Optional String region,
                                                                @Optional String researchLine, @Optional String month, @Optional String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
        String fileName = String.format("Portfolio Distribution %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
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

        int countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream().filter(e -> e.getSCORE() >= 0).count();

        if (researchLine.equals("Temperature Alignment")) {
            countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream().filter(e -> e.getSCORE() >= -2).count();
        }

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

        Response coverageResponse = controller.getPortfolioCoverageResponse(portfolioId, researchLine, apiFilterPayload);
        String coverage = coverageResponse.jsonPath().getString("[0].portfolio_coverage.companies");
        int coverageInvestmentPercentage = (int) Math.round(Double.parseDouble(coverageResponse.jsonPath().getString("[0].portfolio_coverage.investment")));

        System.out.println("Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio);
        System.out.println("coverage: " + coverage);

        //get all distribution table
        List<PortfolioDistribution> portfolioDistributionList = Arrays.asList(
                controller.getPortfolioDistributionResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(PortfolioDistributionWrapper[].class)).get(0).getPortfolio_distribution();

        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        int total = 0; //to check if total of percentages in distribution
        Double totalInvestmentPct = 0d; //to check if total of percentages in distribution

        //loop through Score categories
        for (int i = 0; i < portfolioDistributionList.size(); i++) {
            PortfolioDistribution portfolioDistribution = portfolioDistributionList.get(i);
            RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList.get(i);
            assertTestCase.assertEquals(portfolioDistribution.getCategory(), rangeAndCategory.getCategory(), "Validating score category");

            double min = rangeAndCategory.getMin();
            double max = rangeAndCategory.getMax();
            System.out.println("Min score:" + min);
            System.out.println("Max score:" + max);
            System.out.println("Score Category:" + rangeAndCategory.getCategory());
            System.out.println("Score Category Actual:" + portfolioDistribution.getCategory());
            System.out.println("Actual count:" + portfolioDistribution.getCompanies());

            List<ResearchLineIdentifier> companiesInCategory = portfolioToUpload.stream()
                    .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).collect(Collectors.toList());
            System.out.println(companiesInCategory);

            Integer countOfCompaniesInCategory = companiesInCategory.size();

            System.out.println("Expected count:" + countOfCompaniesInCategory);
            assertTestCase.assertEquals(portfolioDistribution.getCompanies(), countOfCompaniesInCategory, "Validating company count in category");
            total += countOfCompaniesInCategory;

            Double investmentPct = dataValidationUtilities.getTotalInvestmentPercentage(companiesInCategory, totalValues);

            if (rangeAndCategory.getCategory().equals("No Info")) {
                investmentPct = dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(companiesInCategory, totalValues);
            }

            String expectedPercentage = String.format("%.2f", investmentPct);
            String actualPercentage = String.format("%.2f", portfolioDistribution.getInvestment_pct());

            System.out.println("Actual:" + actualPercentage);
            System.out.println("Expected:" + expectedPercentage);
            assertTestCase.assertEquals(actualPercentage, expectedPercentage, "Validating investment pct for: " + portfolioDistribution.getCategory());
            totalInvestmentPct += investmentPct;
        }

        assertTestCase.assertEquals(total, countOfDistinctCompaniesInPortfolio, "Validation total count of companies"); // "Total count in Distribution is matched with expectation");
        assertTestCase.assertEquals(coverageInvestmentPercentage, (int) Math.round(totalInvestmentPct), "portfolio_id" + portfolioId + " Validating total sum of investment percentage"); // "Total % investment in Distribution has summed up to 100%");

    }

    @Test(groups = {"regression", "data_validation"})
    @Xray(test = {8727})
    public void verifyESGDistribution() {
        String sector = "all";
        String region = "all";
        String researchLine = "ESG Assessments";
        String month = "08";
        String year = "2022";


        String portfolioId = "00000000-0000-0000-0000-000000000000";
        List<ESGLeaderANDLaggers> dbData = portfolioQueries.getESGLeadersAndLaggersData(portfolioId, year + month);
        Map<Integer, DoubleSummaryStatistics> groupedData = dbData.stream().collect(
                Collectors.groupingBy(ESGLeaderANDLaggers::getScale,
                        Collectors.summarizingDouble(ESGLeaderANDLaggers::getInvestmentPercentage))
        );
        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        List<PortfolioDistributionWrapper> esgDistributionList = Arrays.asList(
                controller.getPortfolioDistributionResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(PortfolioDistributionWrapper[].class));

        for (int i = 1; i <= 4; i++) {
            String tempcat = "";
            switch (i) {
                case 1:
                    tempcat = "Weak";
                    break;
                case 2:
                    tempcat = "Limited";
                    break;
                case 3:
                    tempcat = "Robust";
                    break;
                case 4:
                    tempcat = "Advanced";
                    break;
            }
            ;
            String category = tempcat;
            PortfolioDistribution entity = esgDistributionList.get(0).getPortfolio_distribution().stream().filter(f -> f.getCategory().equals(category)).findFirst().get();
            assertTestCase.assertTrue(PortfolioUtilities.round(entity.getInvestment_pct(), 0) ==
                    PortfolioUtilities.round(groupedData.get(i).getSum(), 0));
            assertTestCase.assertTrue(entity.getCompanies() ==
                    groupedData.get(i).getCount());
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
//                        {"all", "all", "operationsrisk", "12", "2020"},
//                        {"all", "APAC", "operationsrisk", "12", "2020"},
//                        {"all", "EMEA", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2020"},
//                        {"all", "APAC", "supplychainrisk", "12", "2020"},
//                        {"all", "all", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "marketrisk", "12", "2020"},
//                        {"all", "AMER", "marketrisk", "12", "2020"},
//                        {"all", "APAC", "marketrisk", "12", "2020"},
//                        {"all", "all", "marketrisk", "12", "2020"},
//                        {"all", "all", "operationsrisk", "12", "2021"},
//                        {"all", "APAC", "operationsrisk", "12", "2021"},
//                        {"all", "EMEA", "operationsrisk", "12", "2021"},
//                        {"all", "AMER", "operationsrisk", "12", "2021"},
//                        {"all", "AMER", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2021"},
//                        {"all", "APAC", "supplychainrisk", "12", "2021"},
//                        {"all", "all", "supplychainrisk", "12", "2021"},
//                        {"all", "EMEA", "marketrisk", "12", "2021"},
//                        {"all", "AMER", "marketrisk", "12", "2021"},
//                        {"all", "APAC", "marketrisk", "12", "2021"},
//                        {"all", "all", "marketrisk", "12", "2021"},
                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                        /**  { "all", "APAC", "Carbon Footprint", "03", "2021"},
                        { "all", "AMER", "Carbon Footprint", "03", "2021"},
                        { "all", "EMEA", "Carbon Footprint", "03", "2021"},
                        { "all", "all", "Carbon Footprint", "03", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},*/
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
    }

}
