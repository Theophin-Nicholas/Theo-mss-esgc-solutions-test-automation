package com.esgc.Base.TestBases;

import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries;
import com.esgc.Utilities.DataValidationUtilities;
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
    }

}
