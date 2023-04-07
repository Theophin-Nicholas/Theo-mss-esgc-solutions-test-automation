package com.esgc.Base.TestBases;


import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.EndPoints.CommonEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;


public abstract class APITestBase extends TestBaseClimate {

    public static String portfolioID = null;
    public static String portfolioName = null;

    @BeforeClass(alwaysRun = true)
    public synchronized void setupToGetAPIToken() {
        getAccessTokenDataValidation();
    }

    @BeforeMethod(onlyForGroups = {API}, groups = {REGRESSION, SMOKE, API})
    public synchronized void importPortfolioBeforeAPITests() {
        boolean isImportTest = this.getClass().getName().contains("Import");
        if(!isImportTest) {
            if (portfolioID == null || portfolioID.trim().length() == 0) {
                String user_id = APIUtilities.userID();
                Response response = APIUtilities.importScorePortfolio(user_id);
                portfolioID = response.jsonPath().getString("portfolio_id");
                portfolioName = response.jsonPath().getString("portfolio_name");
            }
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

    @DataProvider(name = "No ESG API Research Lines")
    public Object[][] availableResearchLinesForAPITestsWithoutESG() {

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
                {"esgasmt"},
                {"physicalriskhazard"},
                {"physicalriskmgmt"},
                {"temperaturealgmt"},
                {"carbonfootprint"},
                {"brownshareasmt"},
                {"greenshareasmt"},
        };
    }



}

