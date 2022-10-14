package com.esgc.Test.TestBases;

import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.EntityPageQueries;
import org.testng.annotations.BeforeClass;

public abstract class EntityIssuerPageDataValidationTestBase extends TestBase {

    public EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
    public EntityPageQueries entitypagequeries = new EntityPageQueries();

    public static boolean isP3Test = false;

    @BeforeClass(alwaysRun = true)
    public void setupForIssuerDataValidation() {
        isP3Test = this.getClass().getName().contains("P3");
        EntityPageTestBase.getEntityPageAccessToken();
        DatabaseDriver.createDBConnection();
    }

}
