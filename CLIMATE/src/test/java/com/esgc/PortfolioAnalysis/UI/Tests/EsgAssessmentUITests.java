package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static com.esgc.Utilities.Groups.*;


public class EsgAssessmentUITests extends UITestBase {


    @Test(groups = {REGRESSION, UI, ESG})
    @Xray(test = {11371})
    public void verifyEsgAssessmentExcelColumnsOrder_PortfolioAnalysis() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.clickExportDropdown();
        researchLinePage.selectExportData("Data", "ESG Assessments");
        assertTestCase.assertTrue(researchLinePage.checkIfExportingLoadingMaskIsDisplayed(),
                "Exporting... Load Mask is displayed");
        ExcelUtil exportedDocument = new ExcelUtil(BrowserUtils.exportPath("ESG Assessment"), "Data - ESG");
        List<String> excelColumnNames = exportedDocument.getColumnsNames();
        List<String> expColumnNames = new ArrayList<>();
        expColumnNames.add("Entity Name");
        expColumnNames.add("Orbis ID");
        expColumnNames.add("Sector");
        expColumnNames.add("Scored Orbis ID");
        expColumnNames.add("LEI");
        expColumnNames.add("Methodology Model Version");
        expColumnNames.add("Scored Date");
        expColumnNames.add("Evaluation Year");
        expColumnNames.add("Score Type");
        expColumnNames.add("Overall Score");
        expColumnNames.add("Environment Qualifier");
        expColumnNames.add("Social Qualifier");
        expColumnNames.add("Governance Qualifier");
        expColumnNames.add("Environment Score");
        expColumnNames.add("Social Score");
        expColumnNames.add("Governance Score");

        for(int i=0;i<expColumnNames.size();i++){
            assertTestCase.assertEquals(expColumnNames.get(i),excelColumnNames.get(i), "Validating the order of ESG Columns in the Exported Excel");
        }

    }

    @Test(groups = {REGRESSION, UI, ESG})
    @Xray(test = {5085})
    public void verifyEsgAssessmentExcelColumnsOrder_Dashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        ExcelUtil exportedDocument = new ExcelUtil(filePath, "Data - All research lines");
        List<String> excelColumnNames = exportedDocument.getColumnsNames();
        List<String> expColumnNames = new ArrayList<>();
        expColumnNames.add("ENTITY");
        expColumnNames.add("ISIN(PRIMARY)");
        expColumnNames.add("ISIN(USER_INPUT)");
        expColumnNames.add("ORBIS_ID");
        expColumnNames.add("SECTOR");
        expColumnNames.add("REGION");
        expColumnNames.add("Portfolio Upload Date");
        expColumnNames.add("Scored Orbis ID");
        expColumnNames.add("LEI");
        expColumnNames.add("Methodology Model Version");
        expColumnNames.add("Scored Date");
        expColumnNames.add("Evaluation Year");
        expColumnNames.add("Score Type");
        expColumnNames.add("Overall Score");

        for(int i=0;i<expColumnNames.size();i++){
            assertTestCase.assertEquals(expColumnNames.get(i),excelColumnNames.get(i), "Validating the order of ESG Columns in the Exported Excel");
        }

    }

    @Test(groups = {REGRESSION, UI, ESG, EXPORT})
    @Xray(test = {4899})
    public void VerifyFileUploadSubsidiaryCompany() {
        DashboardPage dashboardPage = new DashboardPage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWith10KCompanies();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        dashboardPage.closePopUp();
        dashboardPage.waitForDataLoadCompletion();
        test.info("Waited for the Successful popup's visibility");
        verifyEsgAssessmentExcelColumnsOrder_Dashboard();
    }

    @Test(groups = {REGRESSION, UI, ESG, EXPORT})
    @Xray(test = {4912})
    public void VerifyRLScoresFromExcelToEntityProfilePage() {
        DashboardPage dashboardPage = new DashboardPage();
        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        dashboardPage.downloadDashboardExportFile();

        // Read the data from Excel File
        String filePath = dashboardPage.getDownloadedCompaniesExcelFilePath();
        ExcelUtil exportedDocument = new ExcelUtil(filePath, "Data - All research lines");
        String companyName = exportedDocument.getCellData(1,0);
        String brownShareScore = exportedDocument.getCellData(1,37);
        String greenShareScore = exportedDocument.getCellData(1,39);
        String physicalRiskMgmtScore = exportedDocument.getCellData(1,50);

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(companyName);
        int brownShareValue = Integer.valueOf(entityProfilePage.BrownShareWidgetValue.getText().split("[^\\d]")[0]);
        int greenShareValue = Integer.valueOf(entityProfilePage.GreenShareWidgetValue.getText().split("[^\\d]")[0]);
        int physicalRiskValue = Integer.valueOf(entityProfilePage.PhysicalRiskMgmtWidgetValue.getText().split("[^\\d]")[0]);

        assertTestCase.assertEquals(brownShareScore,brownShareValue, "Compare brown share value from excel and entity profile page");
        assertTestCase.assertEquals(greenShareScore,greenShareValue, "Compare green share value from excel and entity profile page");
        assertTestCase.assertEquals(physicalRiskMgmtScore,physicalRiskValue, "Compare physical risk management value from excel and entity profile page");

        entityProfilePage.closeEntityProfilePage();
    }
}
