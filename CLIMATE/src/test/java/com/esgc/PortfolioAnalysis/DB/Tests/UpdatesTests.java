package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.RangeAndScoreCategory;
import com.esgc.PortfolioAnalysis.API.APIModels.UpdatesModel;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

public class UpdatesTests extends DataValidationTestBase {

    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {2580, 2475, 2604, 2605, 2621, 2623, 2622, 2606, 2958, 2947, 4990, 2284, 2466})
    public void validateUpdates(@Optional String sector, @Optional String region, @Optional String researchLine, @Optional String month, @Optional String year) {


        List<ResearchLineIdentifier> portfolioToUpload = dataValidationUtilities.getPortfolioToUpload(researchLine, month, year);

        String fileName = String.format("Updates %s - %s - %s", researchLine, sector, region, month, year);
        String path = portfolioUtilities.createPortfolio(fileName, portfolioToUpload);
        System.out.println(path);
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
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);
        test.info(String.format("Filter= %s %s %s %s", region, sector, month, year));

        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        response = controller.getPortfolioUpdatesResponse(portfolioId, researchLine, apiFilterPayload);

        if (researchLine.equals("operationsrisk") && year.equals("2021")) {
            portfolioToUpload = dataValidationUtilities.updatePreviousScoreForOperationsRisk(month, year, portfolioId, portfolioToUpload);
        }

        UpdatesModel[] updates = response.as(UpdatesModel[].class);
        List<RangeAndScoreCategory> rangeAndCategoryList = controller.getResearchLineRangesAndScoreCategories(researchLine);

        DecimalFormat df = new DecimalFormat("0.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        DecimalFormat df2 = new DecimalFormat("0.##");

        for (UpdatesModel each : updates) {
            System.out.println("Company Verification for : " + each.getCompanyName());
            ResearchLineIdentifier researchLineIdentifier = portfolioToUpload.stream()
                    .filter(e -> e.getCOMPANY_NAME().equals(each.getCompanyName())).findFirst().get();

            RangeAndScoreCategory dbScoreCategory = rangeAndCategoryList.stream()
                    .filter(e -> e.getCategory().equals(each.getScoreCategory())).findFirst().get();

            RangeAndScoreCategory dbScorePreviousCategory = rangeAndCategoryList.stream()
                    .filter(e -> e.getCategory().equals(each.getPreviousScoreCategory())).findFirst().orElse(null);

            assertTestCase.assertEquals(each.getPreviousScore() == null ? 0.0d : df.format(each.getPreviousScore()), researchLineIdentifier.getPREVIOUS_SCORE(),
                    String.format("Validating %s previous score", each.companyName));

            assertTestCase.assertEquals(each.getPreviousUpdateDate(), researchLineIdentifier.getPREVIOUS_PRODUCED_DATE().equals("") ? null : researchLineIdentifier.getPREVIOUS_PRODUCED_DATE(),
                    String.format("Validating %s previous produced date", each.companyName));

            assertTestCase.assertEquals(each.getPreviousScoreCategory(), dbScorePreviousCategory == null ? null : dbScorePreviousCategory.getCategory(),
                    String.format("Validating %s previous category", each.companyName));

            assertTestCase.assertEquals(each.getCompanyName(), researchLineIdentifier.getCOMPANY_NAME(),
                    String.format("Validating %s company name", each.companyName));

            assertTestCase.assertEquals(df2.format(each.getScore()), df2.format(researchLineIdentifier.getSCORE()),
                    String.format("Validating %s current score", each.companyName));

            assertTestCase.assertEquals(each.getScoreCategory(), dbScoreCategory.getCategory(),
                    String.format("Validating %s score category", each.companyName));

            assertTestCase.assertEquals(each.getInvestmentPct() + "", df.format(dataValidationUtilities.getTotalInvestmentPercentageForCompany(portfolioToUpload,
                            researchLineIdentifier.getBVD9_NUMBER())),
                    String.format("Validating %s investment percentage", each.companyName));

            assertTestCase.assertEquals(each.getCountry().toLowerCase(), researchLineIdentifier.getCOUNTRY_CODE().toLowerCase(),
                    String.format("Validating %s country code", each.companyName));
        }

    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
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
                        {"all", "AMER", "Physical Risk Management", "04", "2021"},
                        {"all", "EMEA", "Physical Risk Management", "04", "2021"},
                        {"all", "APAC", "Physical Risk Management", "04", "2021"},
                        {"all", "all", "Physical Risk Management", "04", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},
                        {"all", "APAC", "Carbon Footprint", "04", "2021"},
                        {"all", "AMER", "Carbon Footprint", "04", "2021"},
                        {"all", "EMEA", "Carbon Footprint", "04", "2021"},
                        {"all", "all", "Carbon Footprint", "04", "2021"},
//                        {"all", "APAC", "Energy Transition Management", "04", "2021"},
//                        {"all", "AMER", "Energy Transition Management", "04", "2021"},
//                        {"all", "EMEA", "Energy Transition Management", "04", "2021"},
//                        {"all", "all", "Energy Transition Management", "04", "2021"},
                        {"all", "APAC", "Brown Share", "04", "2021"},
                        {"all", "AMER", "Brown Share", "04", "2021"},
                        {"all", "EMEA", "Brown Share", "04", "2021"},
                        {"all", "all", "Brown Share", "04", "2021"},
//                        {"all", "all", "TCFD", "04", "2021"},
//                        {"all", "APAC", "TCFD", "04", "2021"},
//                        {"all", "AMER", "TCFD", "04", "2021"},
//                        {"all", "EMEA", "TCFD", "04", "2021"},
                        {"all", "all", "Green Share", "04", "2021"},
                        {"all", "APAC", "Green Share", "04", "2021"},
                        {"all", "EMEA", "Green Share", "04", "2021"},
                        {"all", "AMER", "Green Share", "04", "2021"}
                };
    }
}
