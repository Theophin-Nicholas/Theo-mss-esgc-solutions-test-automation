package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Tests.UI.DashboardPage.Export.ExportUtils;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EsgPredictedScoreTests extends UITestBase {
//TODO check ESG Assessment Page, it is descoped as of now
    @Test(enabled = false, groups = {"esg", "regression", "ui"})
    @Xray(test = {11131,11133})
    public void verifyScoreQualityToggle() {
        DashboardPage dashboardPage = new DashboardPage();
        assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOff(),"Verify Score Quality Button is off in Dashboard Page");

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOff(),"Verify Score Quality Button is off in Dashboard Page");

        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");
        assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");

    }

    @Test(groups = {"esg", "regression", "ui"})
    @Xray(test = {11136})
    public void verifyScoreQualityLevels() {
        DashboardPage dashboardPage = new DashboardPage();
        assertTestCase.assertTrue(dashboardPage.scoreQualityButton.isDisplayed(),"Verify Score Quality Button in Dashboard Page");
        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

        dashboardPage.clickCoverageLink();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesInCoveragePopup(),"Verify Score Quality icon with Entity Names in Coverage popup in Dashboard Page");
        dashboardPage.verifyScoreQualityLevelsInIconInCoveragePopup();
    }

    @Test(groups = {"esg", "regression", "ui"})
    @Xray(test = {11135})
    public void verifyScoreQualityIconWithEntities_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();

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
        dashboardPage.selectOrDeselectResearchLineUnderAnalysisSection("Overall ESG Score");
        dashboardPage.overallESGCell.click();
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityIconWithEntitiesUnderCompareResearchLines(),"Verify Score Quality icon with Entity Names in Performance Laggards table in Dashboard Page");

    }

    @Test(enabled = false, groups = {"esg", "regression", "ui"})
    @Xray(test = {11163, 11175})
    public void verifyScoreQualityIconWithEntities_PortfolioAnalysis() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");

        dashboardPage.scoreQualityButton.click();
        BrowserUtils.wait(5);
        assertTestCase.assertTrue(dashboardPage.verifyScoreQualityToggleIsOn(),"Verify Score Quality Button is on in Dashboard Page");

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

    @Test(groups = {"esg", "regression", "ui"})
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

    @Test(enabled = false, groups = {"esg", "regression", "ui"})
    @Xray(test = {11074, 11078})
    public void verifyEntitiesWithPredictedScoresInLeadersAndLaggardsTables_PortfolioAnalysis() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithPredictedScores();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        dashboardPage.waitForDataLoadCompletion();
        BrowserUtils.wait(10);
        dashboardPage.closePopUp();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");

        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(researchLine.leadersTableRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Leaders table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(researchLine.leadersTableCompanyNames),"Verify Entities with Predicted Scores are not clickable in Leaders table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(researchLine.laggardsTableRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Laggards table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(researchLine.laggardsTableCompanyNames),"Verify Entities with Predicted Scores are not clickable in Laggards table");

        researchLine.moreCompaniesRankedIn.get(0).click();
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(researchLine.leadersPopupRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Leaders popup");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(researchLine.leadersPopupCompanyNames),"Verify Entities with Predicted Scores are not clickable in Leaders popup");
        researchLine.hideButton.click();

        researchLine.moreCompaniesRankedIn.get(1).click();
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(researchLine.laggardsPopupRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Laggards popup");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(researchLine.laggardsPopupCompanyNames),"Verify Entities with Predicted Scores are not clickable in Laggards popup");
        researchLine.hideButton.click();
    }

    @Test(enabled = false, groups = {"esg", "regression", "ui"})
    @Xray(test = {10901, 10903})
    public void verifyEntitiesWithPredictedScoresInCoveragePopup_PortfolioAnalysis() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.navigateToPageFromMenu("Portfolio Analysis");
        dashboardPage.selectResearchLineFromDropdown("ESG Assessments");
        researchLine.selectEsgPortfolioCoverage();

        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(researchLine.coveragePopupRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Coverage popup");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(researchLine.coveragePopupCompanyNames),"Verify Entities with Predicted Scores are not clickable in Coverage popup");

        researchLine.hideButton.click();
    }

    @Test(groups = {"esg", "regression", "ui"})
    @Xray(test = {11395})
    public void verifyEntitiesWithPredictedScoresInCoveragePopup_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.viewAllCompaniesButton.click();

        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(dashboardPage.coveragePopupRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Coverage popup");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(dashboardPage.coveragePopupCompanyNames),"Verify Entities with Predicted Scores are not clickable in Coverage popup");

        dashboardPage.closePanelBtn.click();
    }

    @Test(groups = {"esg", "regression", "ui"})
    @Xray(test = {11399})
    public void verifyEntitiesWithPredictedScoresInHeatMap_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(5);
        dashboardPage.waitForHeatMap();
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        dashboardPage.waitForHeatMap();
        BrowserUtils.wait(5);
        dashboardPage.heatMapResearchLines.get(1).click();
        dashboardPage.waitForHeatMap();
        dashboardPage.heatMapEsgScoreCells.get(0).click();
        BrowserUtils.waitForVisibility(dashboardPage.heatMapWidgetTitle, 10);

        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(dashboardPage.heatmapPopupRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Coverage popup");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(dashboardPage.heatmapPopupCompanyNames),"Verify Entities with Predicted Scores are not clickable in Coverage popup");

        dashboardPage.heatMapResearchLines.get(1).click();
    }

    @Test(groups = {"esg", "regression", "ui"})
    @Xray(test = {11466})
    public void verifyEntitiesWithPredictedScoresInLeadersAndLaggardsTables_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLine =new ResearchLinePage();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(dashboardPage.performanceRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Leaders table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(dashboardPage.performanceChartCompanyNames),"Verify Entities with Predicted Scores are not clickable in Leaders table");

        dashboardPage.clickAndSelectAPerformanceChart("Leaders");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(dashboardPage.performanceRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Leaders table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(dashboardPage.performanceChartCompanyNames),"Verify Entities with Predicted Scores are not clickable in Leaders table");

        dashboardPage.clickAndSelectAPerformanceChart("Laggards");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresInYellow_PA(dashboardPage.performanceRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Leaders table");
        assertTestCase.assertTrue(researchLine.verifyEntitiesWithPredictedScoresAreNotClickable_PA(dashboardPage.performanceChartCompanyNames),"Verify Entities with Predicted Scores are not clickable in Leaders table");

    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {11427})
    public void verifyScoreTypeOfPredictedEntities() {

        DashboardPage dashboardPage = new DashboardPage();
        ExportUtils utils = new ExportUtils();
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("EsgWithPredictedScores");
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);
        ArrayList<String> expScoreTypes = new ArrayList<>();
        expScoreTypes.add("Predicted");
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String actualScoreType = String.valueOf(excelResult.get("Score Type"));
            System.out.println("**** Record: "+(++i)+" -- Orbis Id: "+excelResult.get("ORBIS_ID")+" -- Score Type: "+actualScoreType);
            if(!actualScoreType.isEmpty())
                assertTestCase.assertTrue(expScoreTypes.contains(actualScoreType),"Score Type Verification");
        }
        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {11398})
    public void verifyScoreTypeIsEmptyForPredictedEntities() {

        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ESG_PS_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        ExportUtils utils = new ExportUtils();

        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        List<Map<String,String>> excelResults = utils.convertExcelToNestedMap(filePath);
        int i=0;
        for(Map<String, String> excelResult:excelResults){
            String actualScoreType = String.valueOf(excelResult.get("Score Type"));
            System.out.println("**** Record: "+(++i)+" -- Orbis Id: "+excelResult.get("ORBIS_ID")+" -- Score Type: "+actualScoreType);
            assertTestCase.assertFalse(actualScoreType.equals("Predicted"),"Score Type should be empty when no Predicted Score entitlement");
        }
        dashboardPage.deleteDownloadFolder();
    }

}
