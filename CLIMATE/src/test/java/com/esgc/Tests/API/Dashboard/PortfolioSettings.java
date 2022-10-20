package com.esgc.Tests.API.Dashboard;

import com.esgc.Controllers.APIController;
import com.esgc.Tests.TestBases.TestBaseClimate;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class PortfolioSettings {

    @Test(groups = {"api", "regression"})
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
