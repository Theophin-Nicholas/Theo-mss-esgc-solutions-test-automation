package com.esgc.Tests.DataValidation.DashboardPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.Dashboard.APIHeatMapPayload;
import com.esgc.APIModels.Dashboard.HeatMapAxisData;
import com.esgc.APIModels.Dashboard.HeatMapData;
import com.esgc.APIModels.Dashboard.HeatMapWrapper;
import com.esgc.APIModels.PortfolioDistribution;
import com.esgc.APIModels.PortfolioDistributionWrapper;
import com.esgc.APIModels.RangeAndScoreCategory;
import com.esgc.Controllers.DashboardAPIController;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import com.esgc.Utilities.APIUtilities;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class HeatMap extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation", "dashboard"}, dataProvider = "researchLines")
    @Xray(test = {4918, 4919, 4920, 4922, 4923, 4926, 5116})
    public void verifyHeatMapWithMixedIdentifiers(@Optional String sector, @Optional String region, @Optional String researchLine1, @Optional String month, @Optional String year) {
        SoftAssert softAssert = new SoftAssert();
        List<String> researchLines = Arrays.asList("ESG", "operationsrisk", "marketrisk", "supplychainrisk", "Physical Risk Management",
                "Temperature Alignment", "Carbon Footprint", "Green Share", "Brown Share");

        DashboardAPIController dashboardAPIController = new DashboardAPIController();


        for (String researchLine2 : researchLines) {
            if (researchLine1.equals(researchLine2)) continue;
            System.out.println("Comparing " + researchLine1 + " vs " + researchLine2);
            test.info("Comparing " + researchLine1 + " vs " + researchLine2);
            List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine1, month, year);
            double totalValues = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
            String fileName = String.format("Heat Map %s - %s - %s - %s - %s", researchLine1, researchLine2, sector, region, month, year);
            String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
            test.info("Portfolio saved to:");
            test.info(path);
            System.out.println("path = " + path);
            Response response = dashboardAPIController.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
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

            if (researchLine1.equals("Temperature Alignment")) {
                countOfDistinctCompaniesInPortfolio = (int) portfolioToUpload.stream().filter(e -> e.getSCORE() >= -2).count();
            }

            String portfolioId = response.getBody().jsonPath().get("portfolio_id");
            System.out.println("Portfolio Created, id: " + portfolioId);
            test.info("portfolio_id=" + portfolioId);
            test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));


            APIHeatMapPayload apiHeatMapPayload = new APIHeatMapPayload();
            apiHeatMapPayload.setSector(sector);
            apiHeatMapPayload.setRegion(region);
            apiHeatMapPayload.setResearch_line_1(researchLine1);
            apiHeatMapPayload.setResearch_line_2(researchLine2);
            apiHeatMapPayload.setYear(year);
            apiHeatMapPayload.setMonth(month);

            APIFilterPayload apiFilterPayload = new APIFilterPayload();
            apiFilterPayload.setSector(sector);
            apiFilterPayload.setRegion(region);
            apiFilterPayload.setBenchmark("");
            apiFilterPayload.setYear(year);
            apiFilterPayload.setMonth(month);

            Response coverageResponse = dashboardAPIController.getPortfolioCoverageResponse(portfolioId, researchLine1, apiFilterPayload);
            String coverage = coverageResponse.jsonPath().getString("[0].portfolio_coverage.companies");
            int coverageInvestmentPercentage = (int) Math.round(Double.parseDouble(coverageResponse.jsonPath()
                    .getString("[0].portfolio_coverage.investment")));

            System.out.println("Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio);
            System.out.println("coverage: " + coverage);

            //get all heat map distribution table
            HeatMapWrapper heatMapDataTable =
                    dashboardAPIController.getHeatMapData(portfolioId, apiHeatMapPayload)
                            .as(HeatMapWrapper[].class)[0];

            List<RangeAndScoreCategory> rangeAndCategoryList1 = dashboardAPIController.getResearchLineRangesAndScoreCategories(researchLine1);
            List<RangeAndScoreCategory> rangeAndCategoryList2 = dashboardAPIController.getResearchLineRangesAndScoreCategories(researchLine2);

            Double totalInvestmentPct1 = 0d; //to check if total of percentages in distribution

            List<HeatMapAxisData> researchLine1DistributionList = heatMapDataTable.getY_axis_total_invct_pct();
            List<HeatMapAxisData> researchLine2DistributionList = heatMapDataTable.getX_axis_total_invct_pct();


            System.out.println("researchLine1DistributionList = " + researchLine1DistributionList);
            System.out.println("researchLine2DistributionList = " + researchLine2DistributionList);

            List<PortfolioDistribution> portfolioDistributionList = Arrays.asList(
                    controller.getPortfolioDistributionResponse(portfolioId, researchLine1, apiFilterPayload)
                            .as(PortfolioDistributionWrapper[].class)).get(0).getPortfolio_distribution();

            //loop through researchLine1 Percentage Distribution
            for (int i = 0; i < rangeAndCategoryList1.size(); i++) {
                RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList1.get(i);

                PortfolioDistribution portfolioDistribution = portfolioDistributionList.stream().
                        filter(e -> e.getCategory().equals(rangeAndCategory.getCategory())).findFirst().get();

                //If% is 0, it will not appear in API response so skip that category

                if (portfolioDistribution.getInvestment_pct() == 0 &&
                        researchLine1DistributionList.stream()
                                .filter(x -> x.getResearch_line_score_category().equals(rangeAndCategory.getCategory()))
                                .findFirst().orElse(null) == null) {
                    System.out.println("There is no data");
                    continue;
                }


                HeatMapAxisData categoryPercentage = researchLine1DistributionList.stream()
                        .filter(x -> x.getResearch_line_score_category().equals(rangeAndCategory.getCategory())).findFirst().get();
                softAssert.assertEquals(
                        categoryPercentage.getResearch_line_score_category(), rangeAndCategory.getCategory(),
                        "Validating score category in " + researchLine1 + " Heat Map distribution");

                double min = rangeAndCategory.getMin();
                double max = rangeAndCategory.getMax();
                System.out.println("Min score:" + min);
                System.out.println("Max score:" + max);
                double min2;
                double max2;
                if (researchLine1.equals("ESG")) {
                    min2 = rangeAndCategory.getMin2();
                    max2 = rangeAndCategory.getMax2();
                    System.out.println("Min2 score:" + min2);
                    System.out.println("Max2 score:" + max2);
                }
                System.out.println("Score Category:" + rangeAndCategory.getCategory());
                System.out.println("Score Category Actual:" + categoryPercentage.getResearch_line_score_category());

                test.info("Score Range");
                test.info("Min score:" + min);
                test.info("Max score:" + max);
                test.info("Expected Score Category:" + rangeAndCategory.getCategory());
                test.info("Actual Score Category:" + categoryPercentage.getResearch_line_score_category());
                List<ResearchLineIdentifier> companiesInCategory = null;
                if (researchLine1.equals("ESG")) {
                    companiesInCategory = portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1008"))
                            .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max)
                            .collect(Collectors.toList());

                    companiesInCategory.addAll(portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1015"))
                            .filter(e -> e.getSCORE() >= rangeAndCategory.getMin2() && e.getSCORE() <= rangeAndCategory.getMax2())
                            .collect(Collectors.toList()));

                } else {
                    companiesInCategory = portfolioToUpload.stream()
                            .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).collect(Collectors.toList());
                }
                System.out.println(companiesInCategory);
                Double investmentPct = 0d;

                if (researchLine1.equals("Temperature Alignment")) {
                    investmentPct = dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(companiesInCategory, totalValues);
                } else {
                    investmentPct = dataValidationUtilities.getTotalInvestmentPercentage(companiesInCategory, totalValues);
                }

                String expectedPercentage = String.format("%.2f", investmentPct);
                String actualPercentage = String.format("%.2f", categoryPercentage.getTotal_investment());
                String actualPercentagePortfolioDistribution = String.format("%.2f", portfolioDistribution.getInvestment_pct());

                System.out.println("Actual Portfolio Distribution:" + actualPercentagePortfolioDistribution);
                System.out.println("Actual:" + actualPercentage);
                System.out.println("Expected:" + expectedPercentage);

                softAssert.assertEquals(actualPercentagePortfolioDistribution, expectedPercentage,
                        (researchLine1 + " Validating portfolio distribution investment pct for: " + rangeAndCategory.getCategory()));

                softAssert.assertEquals(actualPercentagePortfolioDistribution, actualPercentage,
                        (researchLine1 + " Validating Dashboard investment pct matching with Portfolio Analysis for: " + rangeAndCategory.getCategory()));

                softAssert.assertEquals(actualPercentage, expectedPercentage,
                        (researchLine1 + " Validating investment pct for: " + rangeAndCategory.getCategory()));
                totalInvestmentPct1 += investmentPct;
            }

            softAssert.assertEquals(coverageInvestmentPercentage, (int) Math.round(totalInvestmentPct1),
                    researchLine1 + " Validating total sum of investment percentage in categories"); // "Total % investment in Distribution has summed up to 100%");


            Response coverageResponse2 = dashboardAPIController.getPortfolioCoverageResponse(portfolioId, researchLine2, apiFilterPayload);
            String coverage2 = coverageResponse2.jsonPath().getString("[0].portfolio_coverage.companies");
            int coverageInvestmentPercentage2 = (int) Math.round(Double.parseDouble(coverageResponse2.jsonPath().getString("[0].portfolio_coverage.investment")));

            List<ResearchLineIdentifier> portfolioForResearchLine2 =
                    dataValidationUtilities.updateIdentifierScoresByDateAndAnotherResearchLine(researchLine2, month, year, portfolioId, portfolioToUpload);

            int countOfDistinctCompaniesInPortfolio2 = (int) portfolioForResearchLine2.stream().filter(e -> e.getSCORE() >= 0).count();

            if (researchLine2.equals("Temperature Alignment")) {
                countOfDistinctCompaniesInPortfolio2 = (int) portfolioToUpload.stream().filter(e -> e.getSCORE() >= -2).count();
            }

            System.out.println("2nd researchline Count of distinct companies after filters: " + countOfDistinctCompaniesInPortfolio2);
            System.out.println("2nd researchline coverage: " + coverage2);


            Double totalInvestmentPct2 = 0d;

            //loop through researchLine2 Percentage Distribution
            for (int i = 0; i < rangeAndCategoryList2.size(); i++) {

                RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList2.get(i);

                //If% is 0, it will not appear in API response so skip that category
                if (researchLine2DistributionList.stream().noneMatch(x -> x.getResearch_line_score_category().equals(rangeAndCategory.getCategory())))
                    continue;

                HeatMapAxisData categoryPercentage = researchLine2DistributionList.stream()
                        .filter(x -> x.getResearch_line_score_category().equals(rangeAndCategory.getCategory())).findFirst().get();


                softAssert.assertEquals(
                        categoryPercentage.getResearch_line_score_category(), rangeAndCategory.getCategory(),
                        "Validating score category in " + researchLine2 + " Heat Map distribution");

                double min = rangeAndCategory.getMin();
                double max = rangeAndCategory.getMax();
                System.out.println("Min score:" + min);
                System.out.println("Max score:" + max);
                double min2;
                double max2;
                if (researchLine2.equals("ESG")) {
                    min2 = rangeAndCategory.getMin2();
                    max2 = rangeAndCategory.getMax2();
                    System.out.println("Min score:" + min2);
                    System.out.println("Max score:" + max2);
                }
                System.out.println("Score Category:" + rangeAndCategory.getCategory());
                System.out.println("Score Category Actual:" + categoryPercentage.getResearch_line_score_category());

                test.info("Score Range");
                test.info("Min score:" + min);
                test.info("Max score:" + max);
                test.info("Expected Score Category:" + rangeAndCategory.getCategory());
                test.info("Actual Score Category:" + categoryPercentage.getResearch_line_score_category());
                List<ResearchLineIdentifier> companiesInCategory = null;
                if (researchLine2.equals("ESG")) {
                    companiesInCategory = portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1008"))
                            .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max)
                            .collect(Collectors.toList());

                    companiesInCategory.addAll(portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1015"))
                            .filter(e -> e.getSCORE() >= rangeAndCategory.getMin2() && e.getSCORE() <= rangeAndCategory.getMax2())
                            .collect(Collectors.toList()));
                } else {
                    companiesInCategory = portfolioForResearchLine2.stream()
                            .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).collect(Collectors.toList());
                }
                System.out.println(companiesInCategory);

                Double investmentPct = 0d;
                if (researchLine2.equals("Temperature Alignment")) {
                    investmentPct = dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(companiesInCategory, totalValues);
                } else {
                    investmentPct = dataValidationUtilities.getTotalInvestmentPercentage(companiesInCategory, totalValues);
                }


                String expectedPercentage = String.format("%.2f", investmentPct);
                String actualPercentage = String.format("%.2f", categoryPercentage.getTotal_investment());

                System.out.println("Actual:" + actualPercentage);
                System.out.println("Expected:" + expectedPercentage);
                softAssert.assertEquals(actualPercentage, expectedPercentage,
                        (researchLine2 + " Validating investment pct for: " + rangeAndCategory.getCategory()));
                totalInvestmentPct2 += investmentPct;
            }
/*
            ### This validation is removed after https://esjira/browse/ESGCA-9175
            softAssert.assertEquals(coverageInvestmentPercentage2, (int) Math.round(totalInvestmentPct2),
                    researchLine2 + " Validating total sum of investment percentage in categories");
                    // "Total % investment in Distribution has summed up to 100%");
*/

            List<HeatMapData> heatMapDataList = heatMapDataTable.getHeatMap_Data();

            Double totalInvestmentPercentageInRow = 0d;
            //loop through each cell of Heat Map Percentage Data
            for (int i = 0; i < rangeAndCategoryList1.size(); i++) {
                RangeAndScoreCategory rangeAndCategory1 = rangeAndCategoryList1.get(i);

                double min1 = rangeAndCategory1.getMin();
                double max1 = rangeAndCategory1.getMax();
                List<ResearchLineIdentifier> companiesInCategory1 = null;
                if (researchLine1.equals("ESG")) {
                    companiesInCategory1 = portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1008"))
                            .filter(e -> e.getSCORE() >= min1 && e.getSCORE() <= max1)
                            .collect(Collectors.toList());

                    companiesInCategory1.addAll(portfolioToUpload.stream()
                            .filter(e -> e.getResearchLineIdForESGModel()!=null
                                    && e.getResearchLineIdForESGModel().equals("1015"))
                            .filter(e -> e.getSCORE() >= rangeAndCategory1.getMin2() && e.getSCORE() <= rangeAndCategory1.getMax2())
                            .collect(Collectors.toList()));
                } else {
                    companiesInCategory1 = portfolioToUpload.stream()
                            .filter(e -> e.getSCORE() >= min1 && e.getSCORE() <= max1).collect(Collectors.toList());
                }
                System.out.println(companiesInCategory1);

                Double totalInvestmentPercentageInColumn = 0d;
                for (int j = 0; j < rangeAndCategoryList2.size(); j++) {
                    RangeAndScoreCategory rangeAndCategory2 = rangeAndCategoryList2.get(j);

                    double min2 = rangeAndCategory2.getMin();
                    double max2 = rangeAndCategory2.getMax();
                    List<ResearchLineIdentifier> companiesInCategory2 = null;
                    if (researchLine2.equals("ESG")) {
                        companiesInCategory2 = portfolioToUpload.stream()
                                .filter(e -> e.getResearchLineIdForESGModel()!=null
                                        && e.getResearchLineIdForESGModel().equals("1008"))
                                .filter(e -> e.getSCORE() >= min2 && e.getSCORE() <= max2)
                                .collect(Collectors.toList());

                        companiesInCategory2.addAll(portfolioToUpload.stream()
                                .filter(e -> e.getResearchLineIdForESGModel()!=null
                                        && e.getResearchLineIdForESGModel().equals("1015"))
                                .filter(e -> e.getSCORE() >= rangeAndCategory2.getMin2() && e.getSCORE() <= rangeAndCategory2.getMax2())
                                .collect(Collectors.toList()));
                    } else {
                        companiesInCategory2 = portfolioForResearchLine2.stream()
                                .filter(e -> e.getSCORE() >= min2 && e.getSCORE() <= max2).collect(Collectors.toList());
                    }
                    System.out.println(companiesInCategory2);

                 /*   List<ResearchLineIdentifier> list = companiesInCategory2.stream()
                            .filter(e ->
                                    companiesInCategory1.stream().anyMatch(a -> a.getBVD9_NUMBER().equals(e.getBVD9_NUMBER()))
                            ).collect(Collectors.toList());

                    List<String> companyListInCategory2 = companiesInCategory2.stream().map(ResearchLineIdentifier::getBVD9_NUMBER).collect(Collectors.toList());
*/
                    List<ResearchLineIdentifier> finalCompaniesInCategory2 = companiesInCategory2;
                    List<ResearchLineIdentifier> companiesInComparison = companiesInCategory1.stream()
                            .filter(e -> finalCompaniesInCategory2.stream().map(ResearchLineIdentifier::getBVD9_NUMBER)
                                    .collect(Collectors.toList())
                                    .contains(e.getBVD9_NUMBER())).collect(Collectors.toList());

              /*     List<ResearchLineIdentifier> companiesInComparison = companiesInCategory2.stream()
                            .filter(e ->
                                    companiesInCategory1.stream().anyMatch(a -> a.getBVD9_NUMBER().equals(e.getBVD9_NUMBER()))
                            ).collect(Collectors.toList());*/

                    int expectedCountOfCompaniesInComparison = companiesInComparison.size();

                    System.out.printf("Comparing %s %s vs %s %s%n",
                            researchLine1, rangeAndCategory1.getCategory(), researchLine2, rangeAndCategory2.getCategory());

                    test.info(String.format("Comparing %s %s vs %s %s%n",
                            researchLine1, rangeAndCategory1.getCategory(), researchLine2, rangeAndCategory2.getCategory()));

                   /* test.info("Expected Score Category1:" + rangeAndCategory.getCategory());
                    test.info("Actual Score Category1:" + categoryPercentage.getResearch_line_score_category());*/
                    System.out.println(heatMapDataList);
                    int comparisonCompanyCount = 0;

                    try {
                        comparisonCompanyCount = heatMapDataList.stream()
                                .filter(e -> e.getResearch_line_1_score_category().equals(rangeAndCategory1.getCategory())
                                        && e.getResearch_line_2_score_category().equals(rangeAndCategory2.getCategory())).findFirst().get().getCompanies();
                    } catch (NoSuchElementException e) {
                        assertTestCase.assertEquals(expectedCountOfCompaniesInComparison, comparisonCompanyCount, String.format("There is no company to compare between %s %s vs %s %s%n",
                                researchLine1, rangeAndCategory1.getCategory(), researchLine2, rangeAndCategory2.getCategory()));
                        continue;
                    }

                    assertTestCase.assertEquals(expectedCountOfCompaniesInComparison, comparisonCompanyCount, "Counts match comparison");

                    HeatMapData dataCell = heatMapDataList.stream()
                            .filter(e -> e.getResearch_line_1_score_category().equals(rangeAndCategory1.getCategory())
                                    && e.getResearch_line_2_score_category().equals(rangeAndCategory2.getCategory())).findFirst().get();

                    int actualCountOfCompaniesInComparison = dataCell.getCompanies();
                    Double actualInvestmentPercentageInComparison = dataCell.getPerc_investment();

                    Double expectedInvestmentPct = 0d;
                    if (researchLine1.equals("Temperature Alignment")) {
                        expectedInvestmentPct = dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(companiesInComparison, totalValues);
                    } else {
                        expectedInvestmentPct = dataValidationUtilities.getTotalInvestmentPercentage(companiesInComparison, totalValues);
                    }
                    System.out.println("Actual:" + actualCountOfCompaniesInComparison);
                    System.out.println("Expected:" + expectedCountOfCompaniesInComparison);
                    System.out.println("Actual:" + actualInvestmentPercentageInComparison);
                    System.out.println("Expected:" + expectedInvestmentPct);
                    softAssert.assertEquals(actualCountOfCompaniesInComparison, expectedCountOfCompaniesInComparison,
                            String.format("Validating count of companies in %s %s and %s %s ",
                                    researchLine1, rangeAndCategory1.getCategory(), researchLine2, rangeAndCategory2.getCategory()));
                    softAssert.assertEquals(actualInvestmentPercentageInComparison, expectedInvestmentPct,
                            String.format("Validating Investment pct in %s %s and %s %s "
                                    , researchLine1, rangeAndCategory1.getCategory(), researchLine2, rangeAndCategory2.getCategory()));
                    totalInvestmentPercentageInColumn += actualInvestmentPercentageInComparison;

                    if (i == j) {
                        totalInvestmentPercentageInRow += actualInvestmentPercentageInComparison;
                    }
                }

                //softAssert.assertEquals(totalInvestmentPercentageInColumn, );
            }

            portfolioToUpload = null;

        }
        //getExistingUsersAccessTokenFromUI();
        softAssert.assertAll();
    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {

                        {"all", "all", "ESG", "03", "2022"},
                        {"all", "APAC", "ESG", "03", "2022"},
                        {"all", "EMEA", "ESG", "03", "2022"},
                        {"all", "AMER", "ESG", "03", "2022"},
                        {"all", "all", "ESG", "12", "2021"},

                        {"all", "all", "Temperature Alignment", "03", "2022"},
                        {"all", "APAC", "Temperature Alignment", "03", "2022"},
                        {"all", "EMEA", "Temperature Alignment", "03", "2022"},
                        {"all", "AMER", "Temperature Alignment", "03", "2022"},
                        {"all", "all", "operationsrisk", "12", "2021"},

                        {"all", "all", "operationsrisk", "12", "2020"},
                        {"all", "APAC", "operationsrisk", "12", "2021"},
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
                        {"all", "AMER", "Physical Risk Management", "12", "2020"},
                        {"all", "EMEA", "Physical Risk Management", "12", "2020"},
                        {"all", "APAC", "Physical Risk Management", "12", "2020"},
                        {"all", "all", "Physical Risk Management", "12", "2020"},
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
                        {"all", "all", "Carbon Footprint", "04", "2021"},//
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
