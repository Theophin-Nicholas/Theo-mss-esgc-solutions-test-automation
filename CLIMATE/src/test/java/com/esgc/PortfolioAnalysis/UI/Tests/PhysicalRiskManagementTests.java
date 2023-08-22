package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class PhysicalRiskManagementTests extends UITestBase {

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {5082})
    public void validatePhysicalRiskManagementPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
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

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4904})
    public void validateNoDataMessage() {
        //TODO we need to handle no data messages in one place, should not be separate tests for each section/research line
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.selectResearchLineFromDropdown("Physical Risk Management");
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();

        assertTestCase.assertTrue(researchLinePage.verifyMessage("Updates", "No updates for this time period."),
                "Verify No Updates message");
    }
}
