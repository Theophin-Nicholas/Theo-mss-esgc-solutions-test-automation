package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.*;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.DateTimeUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RegionTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {2490, 2723, 3688, 3689, 2724, 2726, 2725, 2634, 2633, 2959, 2948, 3690,
            4991, 3685, 5142, 5143, 1383, 1384,
            6785, 6787})
    public void verifyRegionSummaryAndDetails(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) {

        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValuesInPortfolio = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
        String fileName = String.format("Region Summary Details %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
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

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));
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

        //Get all regions
        List<RegionSummary> regionSummaryList = Arrays.asList(
                controller.getPortfolioRegionSummaryResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSummary[].class));

        //Get Score ranges and categories for research line
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        //Get all Region Details
        List<RegionSectorDetail> regionSectorDetails = Arrays.asList(
                controller.getPortfolioRegionDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

        //loop through Regions
        for (int i = 0; i < regionSummaryList.size(); i++) {
            RegionSummary regionSummary = regionSummaryList.get(i);
            test.info(String.format("Check %s Region", regionSummary.getRegionName()));

            RegionSectorDetail regionSectorDetail = regionSectorDetails.stream()
                    .filter(e -> regionSummary.getRegionName().contains(e.getName())).findFirst().get();
            System.out.println(String.format("Check %s Region", regionSummary.getRegionName()));
            System.out.println("====== Region:" + regionSectorDetail.getName());

            //score category check
            RangeAndScoreCategory regionScoreRangeAndCategory = rangeAndCategoryList.stream()
                    .filter(e -> e.getCategory().equals(regionSummary.getCategory())).findFirst().get();
            assertTestCase.assertEquals(regionSummary.getCategory(), regionScoreRangeAndCategory.getCategory());

            //weighted average score and category calculations
//            double totalWeightedAverage = portfolioUtilities.weightedAverageScore(
//                    portfolioToUpload.stream()
//                            .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode())).collect(Collectors.toList())
//            );

            Double totalValues = portfolioToUpload.stream()
                    .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode()))
                    .mapToDouble(ResearchLineIdentifier::getValue).sum();

//            int score = (int) Math.round(totalWeightedAverage);

            double maxScore = regionScoreRangeAndCategory.getMax();
            double minScore = regionScoreRangeAndCategory.getMin();
            int regionScore = regionSummary.getWeighted_average_score();
            System.out.println("expected score category:" + regionScoreRangeAndCategory.getCategory());
            System.out.println("actual score category:" + regionSummary.getCategory());
//            assertTestCase.assertEquals(regionScore, score, String.format("%s Region Weighted Avg Score Validation", regionSummary.getRegionName()));
//            assertTestCase.assertTrue(regionScore <= maxScore && regionScore >= minScore, String.format("%s Region Score Category Validation", regionSummary.getRegionName()));

            //holding companies number in region
            Integer countOfCompaniesInRegion = (int) portfolioToUpload.stream()
                    .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode()))
                    .filter(r -> r.getSCORE() >= 0).count();
            if (researchLine.equals("Temperature Alignment")) {
                countOfCompaniesInRegion = (int) portfolioToUpload.stream()
                        .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode()))
                        .filter(r -> r.getSCORE() >= -1).count();
            }
            //Get count of companies in each category in one region details
            Integer totalNumberOfCompanies = (int) Stream.of(regionSectorDetail.getCategory1(),
                            regionSectorDetail.getCategory2(),
                            regionSectorDetail.getCategory3(),
                            regionSectorDetail.getCategory4(),
                            regionSectorDetail.getCategory5())
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream).count();

            //Get total percentages in each category in one region details
            Double totalPercentages = Stream.of(regionSectorDetail.getCategory1(),
                            regionSectorDetail.getCategory2(),
                            regionSectorDetail.getCategory3(),
                            regionSectorDetail.getCategory4(),
                            regionSectorDetail.getCategory5())
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream)
                    .mapToDouble(Company::getInvestment_pct).sum();

            System.out.println("Total companies in details:" + totalNumberOfCompanies);
            System.out.println("Total percentage in details:" + totalPercentages);

            test.info("Expected count:" + countOfCompaniesInRegion);
            test.info("Actual count:" + regionSummary.getCountOfCompanies());
            assertTestCase.assertEquals(regionSummary.getCountOfCompanies(), countOfCompaniesInRegion, String.format("Holding Companies Count Validation in %s Region Summary", regionSummary.getRegionName()));
            assertTestCase.assertEquals(regionSummary.getCountOfCompanies(), totalNumberOfCompanies, String.format("Holding Companies Count Validation in %s Region Details", regionSummary.getRegionName()));

            //loop through region distribution table
            List<PortfolioDistribution> portfolioDistributionList = regionSummary.getPortfolioDistributionList();

            Integer total = 0; //to check if total of percentages in region distribution
            Double totalInvestmentPct = 0d; //to check if total of percentages in region details
            test.info("Check each row in region distribution table");
            for (int j = 0; j < portfolioDistributionList.size(); j++) {
                test.info("Row:" + (j + 1));
                System.out.println("===============================================");
                PortfolioDistribution portfolioDistribution = portfolioDistributionList.get(j);
                RangeAndScoreCategory rangeAndCategory = rangeAndCategoryList.get(j);
                assertTestCase.assertEquals(portfolioDistribution.getCategory(), rangeAndCategory.getCategory(), "Score Category verification");

                Double min = (double) rangeAndCategory.getMin();
                Double max = (double) rangeAndCategory.getMax();
                System.out.println("Min score:" + min);
                System.out.println("Max score:" + max);
                System.out.println("Score Category:" + rangeAndCategory.getCategory());
                System.out.println("Score Category Actual:" + portfolioDistribution.getCategory());
                System.out.println("Actual count:" + portfolioDistribution.getCompanies());

                Integer countOfCompaniesInCategory = (int) portfolioToUpload.stream()
                        .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode()))
                        .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).count();

                System.out.println("Expected count:" + countOfCompaniesInCategory);
                assertTestCase.assertEquals(portfolioDistribution.getCompanies(), countOfCompaniesInCategory, String.format("Holding companies count in %s score category validation", portfolioDistribution.getCategory()));
                total += countOfCompaniesInCategory;

                List<ResearchLineIdentifier> companiesInCategory = portfolioToUpload.stream()
                        .filter(r -> r.getWORLD_REGION().equals(regionSummary.getRegionCode()))
                        .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).collect(Collectors.toList());

                Double investmentPct = dataValidationUtilities.getTotalInvestmentPercentage(companiesInCategory, totalValuesInPortfolio);

                if (researchLine.equals("Temperature Alignment")) {
                    investmentPct = dataValidationUtilities.getTotalInvestmentForTemperatureAlignment(companiesInCategory, totalValuesInPortfolio);
                }

                String expectedPercentage = String.format("%.2f", investmentPct);
                String actualPercentage = String.format("%.2f", portfolioDistribution.getInvestment_pct());

                System.out.println("Actual:" + actualPercentage);
                System.out.println("Expected:" + expectedPercentage);
                assertTestCase.assertEquals(actualPercentage, expectedPercentage, String.format("%% investment in %s score category validation", portfolioDistribution.getCategory()));

                totalInvestmentPct += investmentPct;
            }

            assertTestCase.assertEquals(total, countOfCompaniesInRegion, "Total count in Region Distribution match with Region Summary");
            assertTestCase.assertEquals(String.format("%.1s", totalInvestmentPct), String.format("%.1s", regionSummary.getInvestment_pct()), "Total % investment in Region Distribution match with Region Summary");
            assertTestCase.assertEquals(String.format("%.1s", totalPercentages), String.format("%.1s", regionSummary.getInvestment_pct()), "Total % investment in Region Details match with Region Summary");


        }

    }

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {4233, 4237})
    public void verifyRegionMonthlyorQuarterlyChanges(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) throws ParseException {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValuesInPortfolio = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
        String fileName = String.format("Region Summary Details %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
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

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));
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

        //Get all regions
        List<RegionSummary> regionSummaryList = Arrays.asList(
                controller.getPortfolioRegionSummaryResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSummary[].class));

        //Get Score ranges and categories for research line
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        //Get all Region Details
        List<RegionSectorDetail> regionSectorDetailsforCurrentMonth = Arrays.asList(
                controller.getPortfolioRegionDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

//        if (researchLine.equals("operationsrisk") && apiFilterPayload.getYear().equals("2021")) {
//            apiFilterPayload.setYear("2020");
//        } else {
        System.out.println("Current Month Year =" + month + "-" + year);
        String previousMonthAndYear = DateTimeUtilities.getPreviousMonthAndYear(month, year);
        apiFilterPayload.setYear(previousMonthAndYear.split("-")[1]);
        apiFilterPayload.setMonth(previousMonthAndYear.split("-")[0]);
        System.out.println("Previous Month Year =" + previousMonthAndYear);
//        }


        List<RegionSectorDetail> regionSectorDetailsforPreviousMonth = Arrays.asList(
                controller.getPortfolioRegionDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

        //loop through Sectors
        for (int j = 0; j < regionSummaryList.size(); j++) {
            RegionSummary regionSummary = regionSummaryList.get(j);
            test.info(String.format("Check %s Region", regionSummary.getRegionName()));

            RegionSectorDetail regionSectorDetail = regionSectorDetailsforCurrentMonth.stream()
                    .filter(e -> regionSummary.getRegionName().contains(e.getName())).findFirst().get();
            System.out.println(String.format("Check %s Region", regionSummary.getRegionName()));
            System.out.println("====== Region:" + regionSectorDetail.getName());


            int chnageCount = 0;
            List<Company> currentMonth = Stream.of(regionSectorDetail.getCategory1(),
                            regionSectorDetail.getCategory2(),
                            regionSectorDetail.getCategory3(),
                            regionSectorDetail.getCategory4(),
                            regionSectorDetail.getCategory5())
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream).collect(Collectors.toList());

            RegionSectorDetail regionSectorDetailPreviousMonth = regionSectorDetailsforPreviousMonth.stream()
                    .filter(e -> regionSummary.getRegionName().contains(e.getName())).findFirst().orElse(null);

            if (regionSectorDetailsforPreviousMonth.size() != 0 && regionSectorDetailPreviousMonth != null) {

                List<Company> previousMonth = Stream.of(
                                regionSectorDetailPreviousMonth.getCategory1(),
                                regionSectorDetailPreviousMonth.getCategory2(),
                                regionSectorDetailPreviousMonth.getCategory3(),
                                regionSectorDetailPreviousMonth.getCategory4(),
                                regionSectorDetailPreviousMonth.getCategory5())
                        .filter(Objects::nonNull)
                        .flatMap(Collection::stream).collect(Collectors.toList());

                for (int i = 0; i < currentMonth.size(); i++) {
                    Double score = currentMonth.get(i).getScore();
                    String CompanyName = currentMonth.get(i).getCompany_name();
                    String ScoreCategory = rangeAndCategoryList.stream().filter(e -> e.getMin() <= score && e.getMax() >= score).findAny().get().getCategory();

                    System.out.println("Company Name:" + CompanyName);
                    System.out.println("Score:" + score);
                    System.out.println("Score Category:" + ScoreCategory);

                    Company prevoiusMonthMatchedCompany = previousMonth.stream().filter(p -> p.getCompany_name().equals(CompanyName)).findAny().orElse(null);
                    if (prevoiusMonthMatchedCompany != null) {
                        Double previousScore = prevoiusMonthMatchedCompany.getScore();
                        String previousCompany = prevoiusMonthMatchedCompany.getCompany_name();
                        String previousCompanyScoreCategory = rangeAndCategoryList.stream().filter(e -> e.getMin() <= prevoiusMonthMatchedCompany.getScore() && e.getMax() >= prevoiusMonthMatchedCompany.getScore()).findFirst().get().getCategory();
                        System.out.println("Previous Company:" + previousCompany);
                        System.out.println("Previous Score:" + previousScore);
                        System.out.println("Previous Score Category:" + previousCompanyScoreCategory);
                        if (!previousCompanyScoreCategory.equals(ScoreCategory)) {
                            System.out.println("Change!");
                            chnageCount++;
                        }
                    } else {
                        System.out.println("NULL");

                        chnageCount++;
                    }
                }
            } else chnageCount = currentMonth.size();
            assertTestCase.assertEquals(regionSummary.getChange_companies(), Integer.valueOf(chnageCount), "Monthly/Quarterly change validation");
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
                        {"all", "APAC", "operationsrisk", "12", "2021"},
                        {"all", "EMEA", "operationsrisk", "12", "2020"},
                        {"all", "AMER", "operationsrisk", "12", "2020"},
//                        {"all", "AMER", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "supplychainrisk", "12", "2020"},
//                        {"all", "APAC", "supplychainrisk", "12", "2020"},
//                        {"all", "all", "supplychainrisk", "12", "2020"},
//                        {"all", "EMEA", "marketrisk", "12", "2020"},
//                        {"all", "AMER", "marketrisk", "12", "2020"},
//                        {"all", "APAC", "marketrisk", "12", "2020"},
//                        {"all", "all", "marketrisk", "12", "2020"},
                        {"all", "all", "operationsrisk", "12", "2021"},
                        {"all", "APAC", "operationsrisk", "12", "2021"},
                        {"all", "EMEA", "operationsrisk", "12", "2021"},
                        {"all", "AMER", "operationsrisk", "12", "2021"},
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
                        {"all", "APAC", "Carbon Footprint", "01", "2021"},
                        {"all", "AMER", "Carbon Footprint", "01", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "01", "2021"},
                        {"all", "all", "Carbon Footprint", "01", "2021"},
                        {"all", "APAC", "Carbon Footprint", "01", "2022"},
                        {"all", "AMER", "Carbon Footprint", "01", "2022"},
                        {"all", "EMEA", "Carbon Footprint", "01", "2022"},
                        {"all", "all", "Carbon Footprint", "01", "2022"},
                        {"all", "APAC", "Carbon Footprint", "05", "2022"},
                        {"all", "AMER", "Carbon Footprint", "05", "2022"},
                        {"all", "EMEA", "Carbon Footprint", "05", "2022"},
                        {"all", "all", "Carbon Footprint", "05", "2022"},
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
