package com.esgc.DB.TestBases;

import com.esgc.API.TestBase.TestBaseOnDemand;
import com.esgc.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static com.esgc.Utilities.Groups.*;


public abstract class DataValidationTestBase extends TestBaseOnDemand {

    OnDemandAssessmentQueries onDemandAssessmentQueries= new OnDemandAssessmentQueries();

    @BeforeClass(alwaysRun = true)
    public synchronized void setupTokenForPlatformDataValidation(){
        getAccessTokenDataValidation();
        DatabaseDriver.createDBConnection();
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void refreshTokenForPlatformDataValidation(){
        refreshToken();
    }

    @AfterMethod(onlyForGroups = {DATA_VALIDATION}, groups = {SMOKE, REGRESSION, DATA_VALIDATION})
    public void refreshPageToContinueUITesting(ITestResult result) {
        getScreenshot(result);
        Driver.getDriver().navigate().refresh();
    }



}
