package com.esgc.Base.TestBases;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Utilities.DataValidationUtilities;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.PortfolioUtilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;


public abstract class DataValidationTestBase extends TestBaseClimate {

    public APIController controller = new APIController();
    public DashboardAPIController dashboardAPIController = new DashboardAPIController();
    public PortfolioQueries portfolioQueries = new PortfolioQueries();
    public PortfolioUtilities portfolioUtilities = new PortfolioUtilities();
    public DataValidationUtilities dataValidationUtilities = new DataValidationUtilities();
    public EntityPageQueries entitypagequeries = new EntityPageQueries();
    public DashboardQueries dashboardQueries = new DashboardQueries();

    @BeforeClass(alwaysRun = true)
    public synchronized void setupTokenForPlatformDataValidation(){
        getAccessTokenDataValidation();
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void refreshTokenForPlatformDataValidation(){
        refreshToken();
    }

   /* @DataProvider(name = "Company With Orbis ID")
    public Object[][] companyWithOrbisID() {

        return new Object[][]{

                {"Apple, Inc.", "000411117"},  // VE scored company
                {"FirstCash, Inc.","001668460"} // MESG scored Company

        };
    }
*/
}
