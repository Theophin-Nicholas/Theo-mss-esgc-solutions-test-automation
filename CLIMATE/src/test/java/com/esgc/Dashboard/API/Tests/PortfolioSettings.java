package com.esgc.Dashboard.API.Tests;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.TestBaseClimate;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class PortfolioSettings {

    @Test(groups = {API, REGRESSION})
    @Xray(test = {9630,9638})
    public void verifyPortfolioDeletion() {
        APIController apiController = new APIController();
        //First uploading a portfolio to delete
        TestBaseClimate tb = new TestBaseClimate();
        tb.getAccessTokenDataValidations();
        String portfolioId= apiController.postValidPortfolio("SamplePortfolioToDelete.csv");

        //Delete Portfolio
        System.out.println("Deleting the imported portfolio");
        apiController.deletePortfolio(portfolioId).then().statusCode(200);
    }
}
