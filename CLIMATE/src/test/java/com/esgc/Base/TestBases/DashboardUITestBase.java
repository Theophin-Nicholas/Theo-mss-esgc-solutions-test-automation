package com.esgc.Base.TestBases;

import org.testng.annotations.DataProvider;

public abstract class DashboardUITestBase extends UITestBase {

    @DataProvider(name = "Research Lines")
    public Object[][] availableResearchLinesForDashboard() {

        return new Object[][]{
                {"Operations Risk"},
                {"Market Risk"},
                {"Supply Chain Risk"},
                {"Temperature Alignment"},
                {"Physical Risk Management"},
                {"Carbon Footprint"},
                {"Brown Share Assessment"},
                {"Green Share Assessment"},
        };
    }

    @DataProvider(name = "filters")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"All Sectors", "All Regions", "03", "2022"},
                        {"All Sectors", "APAC", "03", "2021"},
                        {"All Sectors", "EMEA", "09", "2022"},
                        {"All Sectors", "AMER", "03", "2022"}

                };
    }
}
