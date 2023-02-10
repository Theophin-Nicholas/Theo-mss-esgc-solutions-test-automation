package com.esgc.DB.TestBases;

import com.esgc.API.TestBase.TestBaseOnDemand;
import com.esgc.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.Utilities.Database.DatabaseDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;


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

}
