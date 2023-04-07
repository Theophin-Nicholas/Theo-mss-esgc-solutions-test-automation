package com.esgc.Common.DB.TestBases;

import com.esgc.Common.API.TestBase.CommonTestBase;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import static com.esgc.Utilities.Groups.*;


public abstract class DataValidationTestBase extends CommonTestBase {

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
