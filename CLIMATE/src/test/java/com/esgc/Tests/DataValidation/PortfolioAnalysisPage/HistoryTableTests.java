package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.*;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utulities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class HistoryTableTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {5060, 5063, 5934, 5917})
    public void verifyHistoryTableWithMixedIdentifiers(@Optional String sector, @Optional String region,
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

        List<HistoryTableWrapper> historyTables = Arrays.asList(
                controller.getHistoryTablesResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(HistoryTableWrapper[].class));

        List<String> years = historyTables.get(0).getPortfolio().getYearlydata();


        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);



        for (int j = years.size() - 1; j >= 0; j--) {

            List<HistoryTableCategory> historyTableCategories = historyTables.get(0).getPortfolio().getCategorydata();

            apiFilterPayload.setYear(years.get(j));
            List<PortfolioDistribution> categoryDistributionList = getCategoryDistribution(portfolioId, researchLine, apiFilterPayload);
            if (categoryDistributionList == null) continue;

            Double totalInvestmentPct = 0d; //to check if total of percentages in distribution
            //loop through Score categories
            for (int i = 0; i < historyTableCategories.size() - 1; i++) {

                RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList.get(i);
                HistoryTableCategory category = historyTableCategories.stream()
                        .filter(e -> e.getName().equals(rangeAndCategory.getCategory())).findFirst().get();
                assertTestCase.assertEquals(category.getName(), rangeAndCategory.getCategory(), "Validating score category1");

                PortfolioDistribution categoryDistribution = categoryDistributionList.stream()
                        .filter(e -> e.getCategory().equals(category.getName())).findFirst().get();
                assertTestCase.assertEquals(categoryDistribution.getCategory(), rangeAndCategory.getCategory(), "Validating score category2");

                double min = rangeAndCategory.getMin();
                double max = rangeAndCategory.getMax();
                System.out.println("Min score:" + min);
                System.out.println("Max score:" + max);
                System.out.println("Score Category:" + rangeAndCategory.getCategory());
                System.out.println("Score Category Actual:" + category.getName());


                /*Double totalValuesInCategory = portfolioToUpload.stream()
                        .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max)
                        .mapToDouble(ResearchLineIdentifier::getValue).sum();*/


                //Double investmentPct = (totalValuesInCategory / totalValues) * 100;
                Double investmentPct = categoryDistribution.getInvestment_pct();

                String expectedPercentage = String.format("%.2f", investmentPct);
                String actualPercentage = String.format("%.2f", category.getData().get(j));

                System.out.println("Actual:" + actualPercentage);
                System.out.println("Expected:" + expectedPercentage);
                assertTestCase.assertEquals(actualPercentage, expectedPercentage, "Validating investment pct for: " + category.getName() + " in " + years.get(j));
                totalInvestmentPct += category.getData().get(j);
            }

            assertTestCase.assertEquals(coverageInvestmentPercentage, (int) Math.round(totalInvestmentPct), "portfolio_id" + portfolioId + " Validating total sum of investment percentage"); // "Total % investment in Distribution has summed up to 100%");
        }


    }

    private List<PortfolioDistribution> getCategoryDistribution(String portfolioId, String researchLine, APIFilterPayload apiFilterPayload) {

        List<PortfolioDistribution> portfolioDistributionList = Arrays.asList(
                controller.getPortfolioDistributionResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(PortfolioDistribution[].class));

        int countOfCompanies = portfolioDistributionList.stream().mapToInt(PortfolioDistribution::getCompanies).sum();

        if (countOfCompanies == 0) {

            int previousMonth = (Integer.parseInt(apiFilterPayload.getMonth()) - 1);
            if (previousMonth == 0) return null;
            apiFilterPayload.setMonth(String.format("%02d", previousMonth));
            portfolioDistributionList = getCategoryDistribution(portfolioId, researchLine, apiFilterPayload);
        }

        return portfolioDistributionList;

    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {


                       {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Carbon Footprint", "02", "2021"},
                        {"all", "AMER", "Carbon Footprint", "02", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "02", "2021"},
                        {"all", "all", "Carbon Footprint", "02", "2021"},
                          { "all", "APAC", "Carbon Footprint", "03", "2021"},
                        { "all", "AMER", "Carbon Footprint", "03", "2021"},
                        { "all", "EMEA", "Carbon Footprint", "03", "2021"},
                        { "all", "all", "Carbon Footprint", "03", "2021"},
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
