package com.esgc.Dashboard.TestDataProviders;


import org.testng.annotations.DataProvider;


public class EntityWithEsgDataOnlyDataProviders {
    @DataProvider(name = "entityWithEsgDataOnly-DP")
    public Object[][] getEntityMethod() {

        return new Object[][]{{"C5 Eiendom AS"},
                {"Entergy Utility Affiliates LLC"},
                {"Solutia, Inc."},
                {"Resolution Life Australasia Ltd."}
        };

    }
}