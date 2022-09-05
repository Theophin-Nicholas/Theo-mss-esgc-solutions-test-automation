package com.esgc.Test.TestBases;

import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Controllers.EntityIssuerPageController.EntityPageAPIController;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.DataValidationUtilities;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.PortfolioUtilities;
import org.testng.annotations.BeforeClass;

public abstract class EntityPageDataValidationTestBase extends TestBase {

    public EntityPageAPIController controller = new EntityPageAPIController();
    public EntityIssuerPageAPIController controller_issuer = new EntityIssuerPageAPIController();
    public EntityPageQueries entitypagequeries = new EntityPageQueries();
    public PortfolioUtilities portfolioUtilities = new PortfolioUtilities();
    public DataValidationUtilities dataValidationUtilities = new DataValidationUtilities();

    public static boolean isP3Test = false;

    @BeforeClass(alwaysRun = true)
    public void setupForIssuerDataValidation(){
        isP3Test = this.getClass().getName().contains("P3");
        EntityPageTestBase.getEntityPageAccessToken();
        DatabaseDriver.createDBConnection();
    }

}
