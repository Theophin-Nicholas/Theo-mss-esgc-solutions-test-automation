package com.esgc.Tests.TestBases;


import com.esgc.Utilities.APIUtilities;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;



public abstract class APITestBase extends TestBaseClimate {

    public static String portfolioID = null;
    public static String portfolioName = null;

    @BeforeClass(alwaysRun = true)
    public synchronized void setupToGetAPIToken() {
        getAccessTokenDataValidation();
    }

    @BeforeMethod(onlyForGroups = {"api"}, groups = {"regression", "smoke", "api"})
    public synchronized void importPortfolioBeforeAPITests() {

        if (portfolioID == null || portfolioID.trim().length() == 0) {
            String user_id = APIUtilities.userID();
            Response response = APIUtilities.importScorePortfolio(user_id);
            portfolioID = response.jsonPath().getString("portfolio_id");
            portfolioName = response.jsonPath().getString("portfolio_name");
        }
    }


    @DataProvider(name = "API Research Lines")
    public Object[][] availableResearchLinesForAPITests() {

        return new Object[][]{
                {"operationsrisk"},
                {"marketrisk"},
                {"supplychainrisk"},
                {"physicalriskmgmt"},
                {"carbonfootprint"},
                {"brownshareasmt"},
                {"greenshareasmt"},
        };
    }


    @DataProvider(name = "API Research Lines2")
    public Object[][] availableResearchLinesForAPITests2() {

        return new Object[][]{
//                {"operationsrisk"},
//                {"marketrisk"},
//                {"supplychainrisk"},
                //{"physicalriskmgmt"},
                {"temperaturealgmt"},
                {"carbonfootprint"},
                {"brownshareasmt"},
                //{"energytransmgmt"},
                //{"tcfdstrategy"},
                {"greenshareasmt"},
        };
    }

}

