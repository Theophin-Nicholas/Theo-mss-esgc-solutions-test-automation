package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.RobotRunner;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class NoDataMessageTests extends UITestBase {

    //TODO New approach needed: import no data portfolio before execution starts and delete it after execution is done
    @Test(priority = 1000, groups = {REGRESSION, UI,ROBOT_DEPENDENCY})
    @Xray(test = {4961, 4913, 5041})
    public void validateDataMessagesWhenPartialDataAvailable() {
//Check if the sample portfolio not selected, first select the sample portfolio.
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.clickUploadPortfolioButton();
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithNoData1();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        researchLinePage.clickUploadButton();
        researchLinePage.waitForDataLoadCompletion();
        BrowserUtils.wait(10);

        // ESGT-4961: Verify no positive impact message
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Physical Risk Hazards: Operations Risk", "There are no companies in this portfolio that contribute to positive impacts."),
                "Verify No Companies for Positive impact message");
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Physical Risk Hazards: Operations Risk", "There are no positive impacts in All Regions,All Sectors in your portfolio."),
                "Verify No Companies for Positive impact message");

        // ESGT-4913: Verify No Updates under Laggards by Score when partial data available
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Laggards by Score", "No Updates for this time period."),
                "Verify No Updates under Laggards by Score");
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Laggards by Score", "There are no updates in All Regions, All Sectors for the time period you selected."),
                "Verify No Updates under Laggards by Score");

        researchLinePage.navigateToPageFromMenu("Climate Dashboard");
        BrowserUtils.wait(5);

        // ESGT-5041: Verify No Controversies messages
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Portfolio Monitoring", "No controversies to display."),
                "Verify No Controversies message");
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Portfolio Monitoring", "There are no controversies in this portfolio in All Regions,All Sectors over the selected time period."),
                "Verify No Controversies message");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4664})
    public void validateNoDataMessageOfNegativeImpact() {
        //Check if the sample portfolio not selected, first select the sample portfolio.
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        //Go to research line and choose one sector.
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        researchLinePage.selectSector("Sovereign");
        BrowserUtils.wait(5);

        // ESGT-4664: Verify no negative impact message
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Physical Risk Hazards: Operations Risk", "There are no companies in this portfolio that contribute to negative impacts."),
                "Verify No Companies for Negative impact message");
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Physical Risk Hazards: Operations Risk", "There are no negative impacts in All Regions, Sovereign in your portfolio."),
                "Verify No Companies for Negative impact message");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4785, 4864, 4367, 4846, 4915, 4727})
    public void validateNoDataMessages() {
//Check if the sample portfolio not selected, first select the sample portfolio.
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        // This dates might need to update depends on data avail
        researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        BrowserUtils.wait(1);
        researchLinePage.selectSector("Sovereign");
        researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        BrowserUtils.wait(1);
        researchLinePage.selectAsOfDate("November 2020");
        BrowserUtils.wait(10);

        // ESGT-4785: Verify No Updates message
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Updates", "No updates for this time period."),
                "Verify No Updates message");
        assertTestCase.assertTrue(researchLinePage.verifyMessage("Updates", "There are no updates in All Regions, Sovereign for the time period you selected."),
                "Verify No Updates message");

        // ESGT-4913: Verify Updates on Leaders Laggards section is hidden when no data available
        assertTestCase.assertTrue(!researchLinePage.isUpdatesAndLeadersAndLaggardsHeaderDisplayed(), "Verify Updates Leaders & Laggards section is hidden");

        // ESGT-4864: Verify Regions & Sectors is hidden
        assertTestCase.assertTrue(!researchLinePage.checkIfRegionMapIsDisplayed(), "Verify Regions & Sectors is hidden");

        // ESGT-4367: Verify Underlying Data Metrics is hidden
        assertTestCase.assertTrue(!researchLinePage.checkIfUnderLyingDataMetricsIsAvailable("Green Share Assessment"), "Verify Underlying Data Metrics is hidden");

        // ESGT-4846 & ESGT-4915: Verify No Portfolio Score and No Portfolio Coverage message
        assertTestCase.assertTrue(researchLinePage.verifyMessage(" Portfolio ", "No updates for this time period.", 2),
                "No Portfolio Score and No Portfolio Coverage messages");
        assertTestCase.assertTrue(researchLinePage.verifyMessage(" Portfolio ", "There are no updates in All Regions, Sovereign for the time period you selected.", 2),
                "No Portfolio Score and No Portfolio Coverage messages");

        // ESGCA-4727: Verify History chart is hidden
        assertTestCase.assertTrue(!researchLinePage.checkIfHistoryTableExists(""), "Verify History Chart is hidden");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4821, 5092})
    public void validateNoDataMessagesInEntityProfilePage() {
//Check if the sample portfolio not selected, first select the sample portfolio.
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        ResearchLinePage researchLinePage = new ResearchLinePage();

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Alibaba Group Holding Ltd.");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        entityProfilePage.navigateToPhysicalRisk();
        BrowserUtils.wait(10);

        // ESGCA-4821: Verify no summary information message
        assertTestCase.assertTrue(researchLinePage.verifyMessage("", "Not enough information for a projection."),
                "Verify no summary information message");

        // ESGCA-5092: Verify no underlying information message

        assertTestCase.assertTrue(researchLinePage.verifyMessage("OPERATIONS RISK", "No information available.", 3),
                "Verify no summary information message");

        assertTestCase.assertTrue(researchLinePage.verifyMessage("", "No sector comparison chart available."),
                "Verify no underlying information message");

        EntityClimateProfilePage entityClimateProfile = new EntityClimateProfilePage();

        entityClimateProfile.clickCloseIconInSearchBox();

        companyName = entityProfilePage.searchAndLoadClimateProfilePage("Axis Bank Ltd");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        entityClimateProfile.selectTransitionRiskTab();
        BrowserUtils.wait(10);

        assertTestCase.assertTrue(researchLinePage.verifyMessageTransitionRisk("Temperature Alignment", "No information available."),
                "Verify no summary information message");

        assertTestCase.assertTrue(researchLinePage.verifyMessageTransitionRisk("Carbon Footprint", "No information available."),
                "Verify no summary information message");

    }

}
