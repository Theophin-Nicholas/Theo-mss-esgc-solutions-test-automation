package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.esgc.Utilities.Groups.*;

public class ViewAllCompaniesPanel extends UITestBase {

    @Test(groups = {REGRESSION, DASHBOARD, UI})
    @Xray(test = {1542, 1553, 1884, 1879, 4755, 4322, 4075})
    public void verifyPanelTests() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        //Verify that the summary header has a 'View Companies and Investments in Portfolio' button
        assertTestCase.assertTrue(dashboardPage.viewAllCompaniesButton.isDisplayed(),
                "User can see and click on View Companies and Investments in Portfolio link on dashboard.");
        dashboardPage.viewAllCompaniesButton.click();
        //Step 3
        String expPanelTitle = "Companies in "+dashboardPage.selectPortfolioButton.getText();
        System.out.println("expPanelTitle = " + expPanelTitle);

        String actPanelTitle = dashboardPage.summaryCompaniesPanelTitle.getText();
        System.out.println("actPanelTitle = " + actPanelTitle);

        assertTestCase.assertEquals(actPanelTitle,expPanelTitle,
                "Panel title is verified as Companies in <portfolio name>");

        // ESGT-1879: Verify regions & sectors
        assertTestCase.assertTrue(dashboardPage.verifyPanel("sector"),
                "Panel elements verified with View By Sector filter");
        assertTestCase.assertTrue(dashboardPage.verifyPanel("region"),
                "Panel elements verified with View By Region filter");
        assertTestCase.assertTrue(dashboardPage.verifyPanel("sector"),
                "Panel elements verified with View By Sector filter");
        assertTestCase.assertTrue(dashboardPage.verifyPanel("region"),
                "Panel elements verified with View By Region filter");

        dashboardPage.selectViewByRegion();

        // ESGT-4755: Verify regions
        assertTestCase.assertTrue(dashboardPage.verifyRegion("Americas"),
                "Verify Americas region is available");
        assertTestCase.assertTrue(dashboardPage.verifyRegion("Asia Pacific"),
                "Verify Asia Pacific region is available");
        assertTestCase.assertTrue(dashboardPage.verifyRegion("Europe, Middle East & Africa"),
                "Verify Europe, Middle East & Africa region is available");

        // ESGT-7507: Verify table columns
        assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("Company"),
                "Verify table column 'Company' is available");
        assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("Controversies"),
                "Verify table column 'Controversies' is available");
        assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("Investment"),
                "Verify table column 'Investment' is available");
        assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableColumns("Sector"),
                "Verify table column 'Sector' is available");

        /* ESGCA-7508: Verify table sector columns values in right side drawer after clicking the
                Sample Portfolio
                Coverage: Across 315 companies, representing 79% of investments (click this link)
         */
        ArrayList<String> expectedSectorValues = new ArrayList<String>(
                Arrays.asList("Consumer Discretionary","Basic Materials","Technology",
                        "Health Care","Real Estate", "Communication",
                        "Consumer Staples","Energy",
                        "Financials","Sovereign", "Industry","Utilities"));

        assertTestCase.assertTrue(dashboardPage.verifyViewByRegionTableSectorColumnsValues(expectedSectorValues),
                "Verify sector column values for all the companies");

        dashboardPage.closePortfolioExportDrawer();
    }

}
