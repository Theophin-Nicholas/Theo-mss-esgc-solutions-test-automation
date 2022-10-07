package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.PortfolioAnalysisPage.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ResearchLineColors;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PhysicalRiskManagementTests extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {7994})
    public void validatePhysicalRiskManagementPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Management");
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date","August 2022");
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();

        assertTestCase.assertTrue(!physicalRiskManagementPage.getPortfolioScoreCategoryFromUI().isEmpty(), "Portfolio Score Category verification");
        assertTestCase.assertTrue(!physicalRiskManagementPage.getPortfolioScoreFromUI().isEmpty(), "Portfolio Score verification");
        assertTestCase.assertTrue(researchLinePage.checkRadarGraph(), "Radar Chart verification");
        assertTestCase.assertTrue(physicalRiskManagementPage.checkIfHistoryTableExists(""), "History Table verification");
        assertTestCase.assertTrue(physicalRiskManagementPage.portfolioDistributionTable.isDisplayed(), "Portfolio Distribution verification");
        physicalRiskManagementPage.validatePhysicalRiskMgmtLegend();
        assertTestCase.assertTrue(physicalRiskManagementPage.verifyInvestmentsLessthanOnePercentInUpdatesSection(), "Updates section verification");
        assertTestCase.assertTrue(researchLinePage.isImpactTablePresent(), "Impact table verification");
        assertTestCase.assertTrue(researchLinePage.LeadersAndLaggardsTable.isDisplayed(), "Leaders and Laggards table verification");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {8029})
    public void validateNoDataMessage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Management");
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();

        assertTestCase.assertTrue(researchLinePage.verifyMessage("Updates", "No updates for this time period."),
                "Verify No Updates message");
    }
}
