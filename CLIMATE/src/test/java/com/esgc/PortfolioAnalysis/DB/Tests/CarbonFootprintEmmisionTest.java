package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.DB.DBModels.ResearchLineIdentifier;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.carbonFootprintEmissionWrapper;
import com.esgc.PortfolioAnalysis.DB.DBModels.carbonFootPrintEmissionDBModel;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;



public class CarbonFootprintEmmisionTest extends DataValidationTestBase {

    @Test(groups = {"regression", "data_validation"}, dataProvider = "CarbonFootprint")
    @Xray(test = {3018, 3021, 3022, 3023, 3025, 3026, 3201})
    public void verifyCarbonFootprintEmmisionTest(@Optional String sector, @Optional String region,
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

       List<carbonFootprintEmissionWrapper> emmissionAPIResponse = Arrays.asList(controller.getCarbonFootprintEmissionAPIResponse(portfolioId, apiFilterPayload)
                .as(carbonFootprintEmissionWrapper[].class));
        List<carbonFootPrintEmissionDBModel> emmissionDBData = portfolioQueries.getCarbonFootPrintEmimmisionTest(portfolioId, month, year);
        for(carbonFootprintEmissionWrapper apiEntity :emmissionAPIResponse){
            carbonFootPrintEmissionDBModel dbEntity = emmissionDBData.stream().filter(e -> e.getCategory().equals(apiEntity.getCategory())).findFirst().get();
            Assert.assertTrue(apiEntity.getData().get(0).getTotalAssets().setScale(2, RoundingMode.CEILING).equals(dbEntity.getTotalAssets().setScale(2, RoundingMode.CEILING)));
            Assert.assertTrue(apiEntity.getData().get(0).getMarketCapitalization().setScale(2, RoundingMode.CEILING) .equals(dbEntity.getMarketCapitalization().setScale(2, RoundingMode.CEILING)));
        }

    }

    @DataProvider(name = "CarbonFootprint")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"all", "all", "Carbon Footprint", "03", "2021"}
                };
    }

}
