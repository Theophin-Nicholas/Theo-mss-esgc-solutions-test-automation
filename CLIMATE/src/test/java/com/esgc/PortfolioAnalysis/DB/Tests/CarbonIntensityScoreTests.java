package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.PortfolioScoreWrapper;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

public class CarbonIntensityScoreTests extends DataValidationTestBase {

    //TODO update flow instead of getting results from DB with query
    @Test(groups = {REGRESSION, DATA_VALIDATION}, dataProvider = "researchLines")
    @Xray(test = {3566, 3446, 3434, 3579, 3652, 3623, 3416, 3489})
    public void verifyCarbonIntensityScoreWithMixedIdentifiers(@Optional String sector, @Optional String region,
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

        String portfolioId = response.getBody().jsonPath().get("portfolio_id");
        System.out.println("Portfolio Created, id: " + portfolioId);
        test.info("portfolio_id=" + portfolioId);


        APIFilterPayload apiFilterPayload = new APIFilterPayload();
        apiFilterPayload.setSector(sector);
        apiFilterPayload.setRegion(region);
        apiFilterPayload.setBenchmark("");
        apiFilterPayload.setYear(year);
        apiFilterPayload.setMonth(month);

        List<PortfolioScoreWrapper> portfolioScore = Arrays.asList(controller.getPortfolioScoreResponse(portfolioId, researchLine, apiFilterPayload)
                .as(PortfolioScoreWrapper[].class));

        String score = portfolioScore.get(0).portfolioScore.stream().filter(c -> c.getName().equals("Carbon Intensity")).findFirst().get().getScore();
        int scoreFromDB = portfolioQueries.getCarbonFootPrintIntensityScore(portfolioId, month, year);
        assertTestCase.assertEquals(Integer.parseInt(score), scoreFromDB, "Intensity Score Validation");
        //TODO choosing random portfolio and some time causing fail.
    }

    @DataProvider(name = "researchLines")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"all", "all", "Carbon Footprint", "03", "2021"},
                        //{"all", "AMER", "Carbon Footprint", "03", "2021"}
                };
    }

}
