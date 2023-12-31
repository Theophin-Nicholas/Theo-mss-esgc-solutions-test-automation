package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.ImpactDistribution;
import com.esgc.PortfolioAnalysis.API.APIModels.ImpactDistributionWrappers;
import com.esgc.PortfolioAnalysis.API.APIModels.InvestmentAndScore;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.apache.commons.text.CaseUtils;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

public class ImpactDistributionDataValidation extends DataValidationTestBase {

    // private Object APIFilterPayloadWithImpactFilter;

    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {3372, 3603, 3717, 3527, 3373, 3334, 3503})
    public void ImpactDistributionDataValidation(@Optional String sector, @Optional String region,
                                                 @Optional String researchLine, @Optional String month, @Optional String year, @Optional String filter) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        PortfolioUtilities portfolioUtilities = new PortfolioUtilities();
        String fileName = String.format("Impact Distribution %s - %s - %s", sector, region, researchLine);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);

        String debuggerFileName = String.format("debugger %s - %s - %s", sector, region, researchLine);
        portfolioUtilities.createPortfolio(debuggerFileName, portfolioToUpload);
        System.out.println(path);

        Response response = controller.importPortfolio(APIUtilities.userID(), fileName + ".csv", path);
        response.then().assertThat().body("portfolio_name", Matchers.notNullValue());

        portfolioToUpload = dataValidationUtilities.preparePortfolioForTesting(portfolioToUpload);
        Double totalValuesInPortfolio = portfolioUtilities.calculateTotalSumOfInvestment(portfolioToUpload);
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
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));

        APIFilterPayloadWithImpactFilter apiFilterPayloadWithImpactFilter = new APIFilterPayloadWithImpactFilter();
        apiFilterPayloadWithImpactFilter.setSector(sector);
        apiFilterPayloadWithImpactFilter.setRegion(region);
        apiFilterPayloadWithImpactFilter.setFilter(filter);
        apiFilterPayloadWithImpactFilter.setYear(year);
        apiFilterPayloadWithImpactFilter.setMonth(month);


        //get Impact distribution API data
        List<ImpactDistributionWrappers> impactDistribution = Arrays.asList(
                controller.getImpactDistributionResponse(portfolioId, researchLine, apiFilterPayloadWithImpactFilter)
                        .as(ImpactDistributionWrappers[].class));


        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);
        if (impactDistribution.get(0).getPositive_impact().getDistribution().size() != 0) {
            double max_PositiveNumber = impactDistribution.get(0).getPositive_impact().getDistribution().stream()
                    .max(Comparator.comparing(p -> p.getTotal())).get().getTotal();
            int x_axisOfPositiveImpact;
            if (max_PositiveNumber % 10 != 0)
                x_axisOfPositiveImpact = (int) (Math.round((max_PositiveNumber + 5) / 10.0) * 10);
            else x_axisOfPositiveImpact = (int) max_PositiveNumber;

            assertTestCase.assertTrue(x_axisOfPositiveImpact == impactDistribution.get(0).getPositive_impact().getX_axis_investment(),
                    "Positive impact x axis did not match");
        }

        if (impactDistribution.get(0).getNegative_impact().getDistribution().size() != 0) {
            double max_NegativeNumber = impactDistribution.get(0).getNegative_impact().getDistribution()
                    .stream().max(Comparator.comparing(p -> p.getTotal())).get().getTotal();
            int x_axisOfNegativeImpact;
            if (max_NegativeNumber % 10 != 0)
                x_axisOfNegativeImpact = (int) (Math.round((max_NegativeNumber + 5) / 10.0) * 10);
            else x_axisOfNegativeImpact = (int) max_NegativeNumber;


            assertTestCase.assertTrue(x_axisOfNegativeImpact == impactDistribution.get(0).getNegative_impact().getX_axis_investment(),
                    "Negaitive impact x axis did not match");

        }

        List<RangeAndScoreCategory> positiveRanges = rangeAndCategoryList
                .stream().filter(range -> range.getImpact().equals("positive")).collect(Collectors.toList());
        List<RangeAndScoreCategory> negativeRanges = rangeAndCategoryList
                .stream().filter(range -> range.getImpact().equals("negative")).collect(Collectors.toList());

        List<ResearchLineIdentifier> Positiveportfolio = portfolioToUpload.stream().filter(e -> (e.getSCORE() >=
                positiveRanges.stream().min(Comparator.comparing(c -> c.getMin())).get().getMin()) && (e.getSCORE() <=
                positiveRanges.stream().max(Comparator.comparing(c -> c.getMax())).get().getMax())).collect(Collectors.toList());

        List<ResearchLineIdentifier> Negativeportfolio = portfolioToUpload.stream().filter(e -> (e.getSCORE() >=
                negativeRanges.stream().min(Comparator.comparing(c -> c.getMin())).get().getMin()) && (e.getSCORE() <=
                negativeRanges.stream().max(Comparator.comparing(c -> c.getMax())).get().getMax())).collect(Collectors.toList());


        List<ResearchLineIdentifier> expectedListForPositive = controller.getfilteredData(Positiveportfolio, filter);
        for (int i = 0; i < positiveRanges.size(); i++) {
            double minRange = positiveRanges.get(i).getMin();
            double maxRange = positiveRanges.get(i).getMax();
            List<ResearchLineIdentifier> rangeData = (expectedListForPositive.stream()
                    .filter(e -> e.getSCORE() >= minRange && e.getSCORE() <= maxRange)).collect(Collectors.toList());
            Double totalValueInCategory = PortfolioUtilities.round(((rangeData.stream()
                    .mapToDouble(ResearchLineIdentifier::getValue).sum()) / totalValuesInPortfolio) * 100, 2);

            String scoreCategory = "";
            if (researchLine.equals("operationsrisk")) {
                scoreCategory = controller.getResearchLineRangesAndScoreCategoriesForUpdates(researchLine)
                        .stream().filter(e -> e.getMin() == minRange && e.getMax() == maxRange).findFirst().get().getCategory();
            } else {
                scoreCategory = positiveRanges.get(i).getCategory();
            }
            //String scoreCategory = controller.getScoreCategory(minRange, maxRange, researchLine, rangeAndCategoryList);
            String finalScoreCategory = scoreCategory;
            List<ImpactDistribution> distribution = impactDistribution.get(0).getPositive_impact().getDistribution().stream().
                    filter(e -> e.getCategory().equals(finalScoreCategory)).collect(Collectors.toList());
            if (distribution.size() > 0) {

                Double distributionRangeTotal = (Positiveportfolio.stream()
                        .filter(e -> e.getSCORE() >= minRange && e.getSCORE() <= maxRange)
                        .mapToDouble(ResearchLineIdentifier::getInvestmentPercentage).sum());
                assertTestCase.assertEquals(String.format("%.1f", distribution.get(0).getTotal()), String.format("%.1f", distributionRangeTotal),
                        "Total in " + scoreCategory + "score category didnt match");
                assertTestCase.assertEquals(PortfolioUtilities.round(distribution.get(0).getSelection(), 2), totalValueInCategory,
                        "Selection in " + scoreCategory + "score category didnt match");
                assertTestCase.assertEquals(distribution.get(0).getCategory(), scoreCategory,
                        "Category in " + scoreCategory + "score category didnt match");
            }
            String finalScoreCategory1 = scoreCategory;
            List<InvestmentAndScore> investment_and_score = impactDistribution.get(0).getPositive_impact().getInvestment_and_score()
                    .stream().filter(e -> e.getScore_category().equals(finalScoreCategory1)).collect(Collectors.toList());
            for (int j = 0; j < rangeData.size(); j++) {
                String companyName = rangeData.get(j).getCOMPANY_NAME();
                InvestmentAndScore invetmentScorecompanies = investment_and_score
                        .stream().filter(e -> e.getCompany_name().equals(companyName)).findFirst().orElse(null);//collect(Collectors.toList());
                Double invper = PortfolioUtilities.round((rangeData.get(j).getValue() / totalValuesInPortfolio) * 100, 2);

                assertTestCase.assertEquals(String.format("%.2f", invetmentScorecompanies.getInvestment_pct()), String.format("%.2f", invper),
                        "Investment Percentage  in " + scoreCategory + "score category for " + companyName + " Company didnt match");
                assertTestCase.assertEquals(invetmentScorecompanies.getScore(), (int) Math.floor(rangeData.get(j).getSCORE()),
                        "Score  in " + scoreCategory + "score category for " + companyName + " Company didnt match");

            }

        }

        List<ResearchLineIdentifier> expectedListForNegative = controller.getfilteredData(Negativeportfolio, filter);

        for (int i = 0; i < negativeRanges.size(); i++) {
            double minRange = negativeRanges.get(i).getMin();
            double maxRange = negativeRanges.get(i).getMax();
            List<ResearchLineIdentifier> rangeData = (expectedListForNegative.stream()
                    .filter(e -> e.getSCORE() >= minRange && e.getSCORE() <= maxRange)).collect(Collectors.toList());
            Double totalValueInCategory = PortfolioUtilities.round(((rangeData.stream()
                    .mapToDouble(ResearchLineIdentifier::getValue).sum()) / totalValuesInPortfolio) * 100, 2);
            String scoreCategory = "";
            if (researchLine.equals("operationsrisk")) {
                scoreCategory = controller.getResearchLineRangesAndScoreCategoriesForUpdates(researchLine)
                        .stream().filter(e -> e.getMin() == minRange && e.getMax() == maxRange).findFirst().get().getCategory();
            } else {
                scoreCategory = negativeRanges.get(i).getCategory();
            }
            //String scoreCategory = controller.getScoreCategory(minRange, maxRange, researchLine, rangeAndCategoryList);
            String finalScoreCategory = scoreCategory;
            List<ImpactDistribution> distribution = impactDistribution.get(0).getNegative_impact().getDistribution().stream().
                    filter(e -> e.getCategory().equals(finalScoreCategory)).collect(Collectors.toList());
            if (distribution.size() > 0) {
                Double distributionRangeTotal = (Negativeportfolio.stream()
                        .filter(e -> e.getSCORE() >= minRange && e.getSCORE() <= maxRange)
                        .mapToDouble(ResearchLineIdentifier::getInvestmentPercentage).sum());
                assertTestCase.assertEquals(String.format("%.2f", distribution.get(0).getTotal()),
                        String.format("%.2f", distributionRangeTotal), "Total in " + scoreCategory + "score category didnt match");
                assertTestCase.assertEquals(PortfolioUtilities.round(distribution.get(0).getSelection(), 2),
                        totalValueInCategory, "Selection in " + scoreCategory + "score category didnt match");
                assertTestCase.assertEquals(distribution.get(0).getCategory(), scoreCategory,
                        "Category in " + scoreCategory + "score category didnt match");
            }
            String finalScoreCategory1 = scoreCategory;
            List<InvestmentAndScore> investment_and_score = impactDistribution.get(0).getNegative_impact().getInvestment_and_score()
                    .stream().filter(e -> e.getScore_category().equals(finalScoreCategory1)).collect(Collectors.toList());
            for (int j = 0; j < rangeData.size(); j++) {
                String companyName = rangeData.get(j).getCOMPANY_NAME();
                List<InvestmentAndScore> invetmentScorecompanies = investment_and_score
                        .stream().filter(e -> e.getCompany_name().equals(companyName)).collect(Collectors.toList());

                Double invper = PortfolioUtilities.round((rangeData.get(j).getValue() / totalValuesInPortfolio) * 100, 2);
                assertTestCase.assertEquals(String.format("%.2f", invetmentScorecompanies.get(0).getInvestment_pct()),
                        String.format("%.2f", invper), "Investment Percentage  in " + scoreCategory + "score category for " + companyName + " Company didnt match");
                assertTestCase.assertEquals(invetmentScorecompanies.get(0).getScore(), (int) Math.floor(rangeData.get(j).getSCORE()),
                        "Score  in " + scoreCategory + "score category for " + companyName + " Company didnt match");

            }

        }


    }

    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {3661})
    public void ImpactDistributionDataTableSum(@Optional String sector, @Optional String region,
                                               @Optional String researchLine, @Optional String month, @Optional String year, @Optional String filter) {
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        String Regions = "";
        switch (region){
            case "AMER":
                Regions = "Americas";
                break;
            case "EMEA":
                Regions = "Europe, Middle East & Africa";
                break;
            case "APAC":
                Regions = "Asia Pacific";
                break;
            default:
                Regions = "All Regions";
        }

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

       String dateFilter = CaseUtils.toCamelCase(Month.of(Integer.valueOf(month)).name(),true,' ') + " " + year;

        researchLinePage.navigateToResearchLine(researchLine);
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("regions", Regions);
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", dateFilter);
        researchLinePage.waitForDataLoadCompletion();

        String UifilterOptions = "";

        switch (filter){
            case "top5":
                UifilterOptions = "Top 5" ;
                break;
            case "top10":
                UifilterOptions = "Top 10" ;
                break;
            case "bottom5":
                UifilterOptions = "Bottom 5" ;
                break;
            case "bottom10":
                UifilterOptions = "Bottom 10" ;
                break;
        }
        researchLinePage.selectImpactFilterOption(UifilterOptions);

        Double UI_postitiveSideSum = researchLinePage.getInvestmentPercentSum("Positive");
        Double UI_negativeSideSum = researchLinePage.getInvestmentPercentSum("Negative");


        List<String> UI_positiveCompanyList = researchLinePage.getInvestmentCompanies("Positive");
        List<String> UI_negativeCompanyList = researchLinePage.getInvestmentCompanies("Negative");

        test.info(String.format("Research Line=%s Filter= %s %s %s %s", researchLine, region, sector, month, year));

        APIFilterPayloadWithImpactFilter apiFilterPayloadWithImpactFilter = new APIFilterPayloadWithImpactFilter();
        apiFilterPayloadWithImpactFilter.setSector(sector);
        apiFilterPayloadWithImpactFilter.setRegion(region);
        apiFilterPayloadWithImpactFilter.setFilter(filter);
        apiFilterPayloadWithImpactFilter.setYear(year);
        apiFilterPayloadWithImpactFilter.setMonth(month);

        List<ImpactDistributionWrappers> impactDistribution = Arrays.asList(
                controller.getImpactDistributionResponse(portfolioId, researchLine, apiFilterPayloadWithImpactFilter)
                        .as(ImpactDistributionWrappers[].class));


     Double positiveSideSum =    impactDistribution.get(0).getPositive_impact().getInvestment_and_score().stream().
                filter(f->UI_positiveCompanyList.contains(f.getCompany_name())).collect(Collectors.toList())
             .stream().mapToDouble(InvestmentAndScore::getInvestment_pct).sum();

     Double negativeSideSum =    impactDistribution.get(0).getNegative_impact().getInvestment_and_score().stream().
                filter(f->UI_negativeCompanyList.contains(f.getCompany_name())).collect(Collectors.toList())
                .stream().mapToDouble(InvestmentAndScore::getInvestment_pct).sum();

     if (UI_postitiveSideSum != 0.00)
     assertTestCase.assertTrue(Math.round(positiveSideSum *100.0)/100.0==UI_postitiveSideSum);

     if (UI_negativeSideSum != 0.00)
     assertTestCase.assertTrue(Math.round(negativeSideSum *100.0)/100.0==UI_negativeSideSum);


    }

    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {3637})
    public void VerifyXAxisImpactDistribution(@Optional String sector, @Optional String region,
                                                 @Optional String researchLine, @Optional String month, @Optional String year, @Optional String filter) {

        String portfolioId = "00000000-0000-0000-0000-000000000000";
        test.info("portfolio_id=" + portfolioId);

        APIFilterPayloadWithImpactFilter apiFilterPayloadWithImpactFilter = new APIFilterPayloadWithImpactFilter();
        apiFilterPayloadWithImpactFilter.setSector(sector);
        apiFilterPayloadWithImpactFilter.setRegion(region);
        apiFilterPayloadWithImpactFilter.setFilter(filter);
        apiFilterPayloadWithImpactFilter.setYear(year);
        apiFilterPayloadWithImpactFilter.setMonth(month);


        //get Impact distribution API data
        List<ImpactDistributionWrappers> impactDistribution = Arrays.asList(
                controller.getImpactDistributionResponse(portfolioId, researchLine, apiFilterPayloadWithImpactFilter)
                        .as(ImpactDistributionWrappers[].class));


        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);
        if (impactDistribution.get(0).getPositive_impact().getDistribution().size() != 0) {
            double max_PositiveNumber = impactDistribution.get(0).getPositive_impact().getDistribution().stream()
                    .max(Comparator.comparing(p -> p.getTotal())).get().getTotal();
            int x_axisOfPositiveImpact;
            if (max_PositiveNumber % 10 != 0)
                x_axisOfPositiveImpact = (int) (Math.round((max_PositiveNumber + 5) / 10.0) * 10);
            else x_axisOfPositiveImpact = (int) max_PositiveNumber;

            assertTestCase.assertTrue(x_axisOfPositiveImpact == impactDistribution.get(0).getPositive_impact().getX_axis_investment(),
                    "Positive impact x axis did not match");
        }

        if (impactDistribution.get(0).getNegative_impact().getDistribution().size() != 0) {
            double max_NegativeNumber = impactDistribution.get(0).getNegative_impact().getDistribution()
                    .stream().max(Comparator.comparing(p -> p.getTotal())).get().getTotal();
            int x_axisOfNegativeImpact;
            if (max_NegativeNumber % 10 != 0)
                x_axisOfNegativeImpact = (int) (Math.round((max_NegativeNumber + 5) / 10.0) * 10);
            else x_axisOfNegativeImpact = (int) max_NegativeNumber;


            assertTestCase.assertTrue(x_axisOfNegativeImpact == impactDistribution.get(0).getNegative_impact().getX_axis_investment(),
                    "Negaitive impact x axis did not match");

        }
    }


    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"all", "all", "Temperature Alignment", "03", "2022", "top5"},
                        {"all", "APAC", "Temperature Alignment", "03", "2022", "top10"},
                        {"all", "EMEA", "Temperature Alignment", "03", "2022", "bottom5"},
                        {"all", "AMER", "Temperature Alignment", "03", "2022", "bottom10"},
                        {"all", "all", "Physical Risk Management", "04", "2021", "top5"},
                        {"all", "all", "Physical Risk Management", "12", "2020", "top10"},
                        {"all", "all", "Physical Risk Management", "12", "2020", "bottom5"},
                        {"all", "all", "Physical Risk Management", "12", "2020", "bottom10"},
                        {"all", "all", "Brown Share", "04", "2021", "top5"},
                        {"all", "all", "Brown Share", "12", "2020", "top10"},
                        {"all", "all", "Brown Share", "12", "2020", "bottom5"},
                        {"all", "all", "Brown Share", "12", "2020", "bottom10"},
                        {"all", "all", "Carbon Footprint", "04", "2021", "top5"},
                        {"all", "all", "Carbon Footprint", "12", "2020", "top10"},
                        {"all", "all", "Carbon Footprint", "12", "2020", "bottom5"},
                        {"all", "all", "Carbon Footprint", "12", "2020", "bottom10"},
                        {"all", "all", "Green Share", "04", "2021", "top5"},
                        {"all", "all", "Green Share", "12", "2020", "top10"},
                        {"all", "all", "Green Share", "12", "2020", "bottom5"},
                        {"all", "all", "Green Share", "12", "2020", "bottom10"},
                };
    }

}
