package com.esgc.Tests.TestBases;

import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.Utilities.DataValidationUtilities;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.EntityClimateProfilePageQueries;
import com.esgc.Utilities.PortfolioUtilities;
import org.testng.annotations.BeforeClass;

public abstract class EntityClimateProfileDataValidationTestBase extends TestBaseClimate{

    public EntityProfileClimatePageAPIController controller = new EntityProfileClimatePageAPIController();
    public EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
    public PortfolioUtilities portfolioUtilities = new PortfolioUtilities();
    public DataValidationUtilities dataValidationUtilities = new DataValidationUtilities();

    @BeforeClass(alwaysRun = true)
    public void setupMethodForClimateProfileDataValidation(){
        getAccessTokenDataValidation();
        DatabaseDriver.createDBConnection();
    }

}
