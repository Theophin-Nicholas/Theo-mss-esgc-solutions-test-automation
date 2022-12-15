package com.esgc.Base.TestBases;

import com.esgc.Utilities.Driver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;


public abstract class EntityClimateProfileTestBase extends TestBaseClimate {



    @BeforeClass(alwaysRun = true)
    public void setupToGetTokenForClimateProfile() {
        getAccessTokenDataValidation();
    }

    @AfterClass(alwaysRun = true)
    public void tearDownAfterClimateProfileTests() {
        Driver.closeDriver();
    }



    @DataProvider(name = "API Research Lines")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"temperaturealgmt", "000411117"},

                {"carbonfootprint","108645382"},
                {"carbonfootprint", "108645382"},
                {"carbonfootprint", "001812590"},

                {"physicalriskhazard", "006105314"},
                {"physicalriskhazard", "051058092"},

                {"brownshareasmt","108645382"},

                {"greenshareasmt", "108645382"},
                {"greenshareasmt", "108645382"},

                {"physicalriskmgmt", "000411117"},
                {"physicalriskmgmt", "000411117"}


        };
    }


    @DataProvider(name = "API Research Lines2 Carbon Footprint")
    public Object[][] dpMethod2ddd() {

        return new Object[][]{

                {"carbonfootprint", "006105314"},
                {"carbonfootprint", "051058092"}

        };
    }

    @DataProvider(name = "API Research Lines2 Operations Risk")
    public Object[][] dpMethod2dddD() {

        return new Object[][]{

                {"Operations Risk", "006105314"},
                {"Operations Risk", "051058092"}

        };
    }

    @DataProvider(name = "API Research Lines2 Market Risk")
    public Object[][] dpMethod2MarketRisk() {

        return new Object[][]{

                {"Market Risk", "006105314"},
                {"Market Risk", "000411117"}//Apple, INC.

        };
    }
    @DataProvider(name = "API Research Lines2 Supply Chain Risk")
    public Object[][] dpMethod2SupplyChainRisk() {

        return new Object[][]{

                {"Supply Chain Risk", "006105314"},
                {"Supply Chain Risk", "000411117"}//Apple, INC.

        };
    }

    @DataProvider(name = "Company With Orbis ID")
    public Object[][] companyWithOrbisID() {

        return new Object[][]{

                {"Apple, Inc.", "000411117"},
                {"Samsung Electronics Co., Ltd.","006529401"}

        };
    }
}

