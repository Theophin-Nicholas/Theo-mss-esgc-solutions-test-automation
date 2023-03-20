package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class ScoreQualityTests extends UITestBase {

    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {11131,11133})
    public void verifyScoreQualityToggle() {
        DashboardPage dashboardPage = new DashboardPage();
        // remove to be de-scoped
       // assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOff(),"Verify Score Quality Button is off in Dashboard Page");

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        // remove to be de-scoped
        //assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        // remove to be de-scoped
      //  assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOff(),"Verify Score Quality Button is off in Dashboard Page");

        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");
        // remove to be de-scoped
      //  assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
    }


    // remove to be de-scoped
    /*
    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {11136})
    public void verifyScoreQualityLevels() {
        DashboardPage dashboardPage = new DashboardPage();
        // remove to be de-scoped
       // assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
       // dashboardPage.scoreQualityButton.click();
       // BrowserUtils.wait(5);
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

       // dashboardPage.clickCoverageLink();
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInCoveragePopup(),"Verify Score Quality icon with Entity Names in Coverage popup in Dashboard Page");
        //dashboardPage.verifyScoreQualityLevelsInIconInCoveragePopup();
    }

     */

    // to be de-scoped remove. validate with furkan ???
    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {11135})
    public void verifyScoreQualityIconWithEntities_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        // remove to be de-scoped

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Larger Holdings table in Dashboard Page");

        dashboardPage.tabPerformanceLeaders.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Leaders table in Dashboard Page");

        dashboardPage.tabPerformanceLaggards.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Laggards table in Dashboard Page");

        BrowserUtils.scrollTo(dashboardPage.overallESGCell);
        dashboardPage.overallESGCell.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesUnderCompareResearchLines(),"Verify Score Quality icon with Entity Names in Performance Laggards table in Dashboard Page");

        dashboardPage.selectOrDeselectResearchLineUnderAnalysisSection("Physical Risk: Market Risk");
        // to be de-scoped remove. validate with furkan ???
        dashboardPage.selectOrDeselectResearchLineUnderAnalysisSection("Overall ESG Score");
        dashboardPage.overallESGCell.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesUnderCompareResearchLines(),"Verify Score Quality icon with Entity Names in Performance Laggards table in Dashboard Page");

    }

    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {11163, 11175})
    public void verifyScoreQualityIconWithEntities_PortfolioAnalysis() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");

        // remove to be de-scoped
       // dashboardPage.scoreQualityButton.click();
       // BrowserUtils.wait(5);
      //  assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInLeadersAndLaggardsTables_PA(),"Verify Score Quality icon with Entity Names in Leaders and Laggards tables in Portfolio Analysis Page");

        researchLine.moreCompaniesRankedIn.get(0).click();
        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInLeadersPopup_PA(),"Verify Score Quality icon with Entity Names in Leaders popup in Portfolio Analysis Page");
        researchLine.hideButton.click();

        researchLine.moreCompaniesRankedIn.get(1).click();
        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInLaggardsPopup_PA(),"Verify Score Quality icon with Entity Names in Laggards popup in Portfolio Analysis Page");
        researchLine.hideButton.click();

        BrowserUtils.scrollTo(researchLine.portfolioCoverage);
        researchLine.portfolioCoverage.click();
        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInCoveragePopup_PA(),"Verify Score Quality icon with Entity Names in Coverage popup in Portfolio Analysis Page");
        researchLine.hideButton.click();

        researchLine.selectOptionFromFiltersDropdown("benchmark","Sample Portfolio");
        researchLine.portfolioCoverage.click();
        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInCoveragePopup_PA(),"Verify Score Quality icon with Entity Names in Coverage popup in Portfolio Analysis Page");
        researchLine.hideButton.click();
    }

    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {11216})
    public void verifyScoreQualityToggleWithoutEsgEntitlement() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_EXPORT_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        assertTestCase.assertTrue(!dashboardPage.isScoreQualityButtonAvailable(),"Verify Score Quality Button in Dashboard Page");
        assertTestCase.assertTrue(!dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Larger Holdings table in Dashboard Page");

        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        ResearchLinePage researchLine = new ResearchLinePage();
        assertTestCase.assertTrue(!researchLine.getAvailableResearchLines().contains("ESG Assessments"), "Verify ESG Assessment is not available in Research Line dropdown");
        assertTestCase.assertTrue(!researchLine.verifyScoreQualityIconWithEntitiesInLeadersAndLaggardsTables_PA(),"Verify Score Quality icon with Entity Names in Leaders and Laggards tables in Portfolio Analysis Page");

    }

    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {12069})
    public void verifyScoreQualityIconWithSubsidiaryCompanies_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");

        // remove to be de-scoped
       // assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
       // dashboardPage.scoreQualityButton.click();
       // BrowserUtils.wait(5);
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

       // dashboardPage.clickCoverageLink();
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInCoveragePopup(),"Verify Score Quality icon with Entity Names in Coverage popup in Dashboard Page");
       // dashboardPage.verifyScoreQualityLevelsInIconInCoveragePopup();
       // dashboardPage.pressESCKey();

        //TODO ESGCA-12069 Step4:  Dashboard - Portfolio Monitoring - Verify Score Quality icons for the subsidiary companies
        //TODO ESGCA-12069 Step5:  Dashboard - Geographic Risk Section - Verify Score Quality icons for the subsidiary companies
        //TODO ESGCA-12069 Step6:  Dashboard - Heat Map Section - Verify Score Quality icons for the subsidiary companies

        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Larger Holdings table in Dashboard Page");

        dashboardPage.tabPerformanceLeaders.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Leaders table in Dashboard Page");

        dashboardPage.tabPerformanceLaggards.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInPerformanceTable(),"Verify Score Quality icon with Entity Names in Performance Laggards table in Dashboard Page");

    }

    @Test(groups = {ESG, REGRESSION, UI})
    @Xray(test = {12071})
    public void verifyScoreQualityIconWithSubsidiaryCompanies_PortfolioAnalysis() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");
        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");

        // remove to be de-scoped
       // dashboardPage.scoreQualityButton.click();
      //  BrowserUtils.wait(5);
       // assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInLeadersAndLaggardsTables_PA(),"Verify Score Quality icon with Entity Names in Leaders and Laggards tables in Portfolio Analysis Page");

        BrowserUtils.scrollTo(researchLine.portfolioCoverage);
        researchLine.portfolioCoverage.click();
        assertTestCase.assertTrue(researchLine.verifyScoreQualityIconWithEntitiesInCoveragePopup_PA(),"Verify Score Quality icon with Entity Names in Coverage popup in Portfolio Analysis Page");
        researchLine.hideButton.click();
    }
}
