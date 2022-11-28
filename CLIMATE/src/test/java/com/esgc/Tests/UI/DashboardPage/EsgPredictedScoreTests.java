package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Tests.UI.DashboardPage.Export.ExportUtils;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DashboardQueries;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class EsgPredictedScoreTests extends UITestBase {

    @Test(groups = {"esg", "regression", "ui"})
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

    @Test(groups = {"esg", "regression", "ui"})
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
