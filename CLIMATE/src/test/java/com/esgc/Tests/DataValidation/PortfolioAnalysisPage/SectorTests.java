package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortoflioAnalysisModels.*;
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

public class SectorTests extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {1750, 2140, 2213, 2218, 2575, 3687, 3686, 4991, 3691, 1383, 1384, 5143, 11247})
    public void verifySectorSummaryAndDetails(@Optional String sector, @Optional String region,
                                              @Optional String researchLine, @Optional String month, @Optional String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);
        double totalValuesInPortfolio = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);

        String fileName = String.format("Sector Summary Details %s - %s - %s - %s - %s", researchLine, sector, region, month, year);
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
        System.out.println("Portfolio_id=" + portfolioId);
        int countOfDistinctCompaniesInPortfolio = dataValidationUtilities.getCoverageOfPortfolio(portfolioToUpload);

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

        //Get all Sectors
        List<SectorSummary> sectorSummaryList = Arrays.asList(
                controller.getPortfolioSectorSummaryResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(SectorSummary[].class));

        //Get Score ranges and categories for research line
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        //Get all Sector Details
        List<RegionSectorDetail> regionSectorDetails = Arrays.asList(
                controller.getPortfolioSectorDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

        //loop through Sectors
        for (int i = 0; i < sectorSummaryList.size(); i++) {
            SectorSummary sectorSummary = sectorSummaryList.get(i);
            test.info(String.format("Check %s Sector", sectorSummary.getSectorName()));

            RegionSectorDetail regionSectorDetail = regionSectorDetails.stream()
                    .filter(e -> sectorSummary.getSectorName().contains(e.getName())).findFirst().get();
            System.out.println(String.format("Check %s Sector", sectorSummary.getSectorName()));
            System.out.println("====== Sector:" + regionSectorDetail.getName());

            //score category check
            RangeAndScoreCategory sectorScoreRangeAndCategory = rangeAndCategoryList.stream()
                    .filter(e -> e.getCategory().equals(sectorSummary.getCategory())).findFirst().get();
            assertTestCase.assertEquals(sectorSummary.getCategory(), sectorScoreRangeAndCategory.getCategory());

            //weighted average score and category calculations
//            double totalWeightedAverage = portfolioUtilities.weightedAverageScore(
//                    portfolioToUpload.stream()
//                            .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName())).collect(Collectors.toList())
//            );

            Double totalValues = portfolioToUpload.stream()
                    .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName()))
                    .mapToDouble(ResearchLineIdentifier::getValue).sum();

            //int score = (int) (totalWeightedAverage);

            double maxScore = sectorScoreRangeAndCategory.getMax();
            double minScore = sectorScoreRangeAndCategory.getMin();
            double sectorScore = sectorSummary.getWeighted_average_score();

            System.out.println("Min score:" + minScore);
            System.out.println("Max score:" + maxScore);
//            System.out.println("Sector Score:" + score);
//            System.out.println("Sector Score Actual:" + sectorScore);
            System.out.println("Sector Score Category:" + sectorScoreRangeAndCategory.getCategory());
            System.out.println("Sector Score Actual:" + sectorSummary.getCategory());


//            assertTestCase.assertEquals(sectorScore, score, String.format("%s Sector Weighted Avg Score Validation", sectorSummary.getSectorName()));
//            assertTestCase.assertTrue(sectorScore <= maxScore && sectorScore >= minScore, String.format("%s Sector Score Category Validation", sectorSummary.getSectorName()));

            //holding companies number in Sector
            Integer countOfCompaniesInSector = (int) portfolioToUpload.stream()
                    .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName()))
                    .filter(r -> r.getSCORE() >= 0).count();
            if (researchLine.equals("Temperature Alignment")) {
                countOfCompaniesInSector = (int) portfolioToUpload.stream()
                        .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName()))
                        .filter(r -> r.getSCORE() >= -1).count();
            }

            //Get count of companies in each category in one sector details
            Integer totalNumberOfCompanies = (int) Stream.of(regionSectorDetail.getCategory1(),
                            regionSectorDetail.getCategory2(),
                            regionSectorDetail.getCategory3(),
                            regionSectorDetail.getCategory4(),
                            regionSectorDetail.getCategory5())
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream).count();

            //Get total percentages in each category in one sector details
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

            test.info("Expected count:" + countOfCompaniesInSector);
            test.info("Actual count:" + sectorSummary.getCountOfCompanies());
            assertTestCase.assertEquals(sectorSummary.getCountOfCompanies(), countOfCompaniesInSector,
                    String.format("Holding Companies Count Validation in %s Sector Summary", sectorSummary.getSectorName()));
            assertTestCase.assertEquals(sectorSummary.getCountOfCompanies(), totalNumberOfCompanies,
                    String.format("Holding Companies Count Validation in %s Sector Details", sectorSummary.getSectorName()));

            //loop through sector distribution table
            List<PortfolioDistribution> portfolioDistributionList = sectorSummary.getPortfolioDistributionList();

            Integer total = 0; //to check if total of percentages in sector distribution
            Double totalInvestmentPct = 0d; //to check if total of percentages in sector details
            test.info("Check each row in sector distribution table");
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
                        .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName()))
                        .filter(e -> e.getSCORE() >= min && e.getSCORE() <= max).count();

                System.out.println("Expected count:" + countOfCompaniesInCategory);
                assertTestCase.assertEquals(portfolioDistribution.getCompanies(), countOfCompaniesInCategory,
                        String.format("Holding companies count in %s score category validation", portfolioDistribution.getCategory()));
                total += countOfCompaniesInCategory;

                List<ResearchLineIdentifier> companiesInCategory = portfolioToUpload.stream()
                        .filter(r -> r.getPLATFORM_SECTOR().equals(sectorSummary.getSectorName()))
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

            assertTestCase.assertEquals(total, countOfCompaniesInSector, portfolioId + " Total count in Sector Distribution match with Sector Summary");
            assertTestCase.assertEquals(String.format("%.1s", totalInvestmentPct), String.format("%.1s", sectorSummary.getInvestment_pct()),
                    portfolioId + " Total % investment in Sector Details match with Sector Summary");
            assertTestCase.assertEquals(String.format("%.1s", totalPercentages), String.format("%.1s", sectorSummary.getInvestment_pct()),
                    portfolioId + " Total % investment in Sector Details match with Sector Summary");


        }
    }

    @Test(groups = {"regression", "data_validation"}, dataProvider = "researchLines")
    @Xray(test = {4234, 4236})
    public void verifySectorMonthlyOrQuarterlyChanges(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) throws ParseException {


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

        //filter for a given sector
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

        //Get all sectors
        List<SectorSummary> sectorSummaryList = Arrays.asList(
                controller.getPortfolioSectorSummaryResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(SectorSummary[].class));

        //Get Score ranges and categories for research line
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        //Get all Sector Details
        List<RegionSectorDetail> regionSectorDetailsforCurrentMonth = Arrays.asList(
                controller.getPortfolioSectorDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

        System.out.println("Current Month Year =" + month + "-" + year);
        String previousMonthAndYear = DateTimeUtilities.getPreviousMonthAndYear(month, year);
        apiFilterPayload.setYear(previousMonthAndYear.split("-")[1]);
        apiFilterPayload.setMonth(previousMonthAndYear.split("-")[0]);
        System.out.println("Previous Month Year =" + previousMonthAndYear);

        List<RegionSectorDetail> regionSectorDetailsforPreviousMonth = Arrays.asList(
                controller.getPortfolioSectorDetailsResponse(portfolioId, researchLine, apiFilterPayload)
                        .as(RegionSectorDetail[].class));

        //loop through Sectors
        for (int j = 0; j < sectorSummaryList.size(); j++) {
            SectorSummary sectorSummary = sectorSummaryList.get(j);
            test.info(String.format("Check %s Sector", sectorSummary.getSectorName()));

            RegionSectorDetail regionSectorDetail = regionSectorDetailsforCurrentMonth.stream()
                    .filter(e -> sectorSummary.getSectorName().contains(e.getName())).findFirst().get();
            System.out.println(String.format("Check %s Sector", sectorSummary.getSectorName()));
            System.out.println("====== Sector:" + regionSectorDetail.getName());


            int chnageCount = 0;
            List<Company> currentMonth = Stream.of(regionSectorDetail.getCategory1(),
                            regionSectorDetail.getCategory2(),
                            regionSectorDetail.getCategory3(),
                            regionSectorDetail.getCategory4(),
                            regionSectorDetail.getCategory5())
                    .filter(Objects::nonNull)
                    .flatMap(Collection::stream).collect(Collectors.toList());

            RegionSectorDetail regionSectorDetailPreviousMonth = regionSectorDetailsforPreviousMonth.stream()
                    .filter(e -> sectorSummary.getSectorName().contains(e.getName())).findFirst().orElse(null);

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
                    System.out.println("===================================================");
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
                        String previousCompanyScoreCategory = rangeAndCategoryList.stream()
                                .filter(e -> e.getMin() <= prevoiusMonthMatchedCompany.getScore() && e.getMax() >= prevoiusMonthMatchedCompany.getScore())
                                .findFirst().get().getCategory();
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
            assertTestCase.assertEquals(sectorSummary.getChange_companies(), Integer.valueOf(chnageCount), "Monthly/Quarterly change validation");
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
