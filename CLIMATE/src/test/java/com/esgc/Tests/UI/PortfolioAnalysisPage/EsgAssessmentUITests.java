package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class EsgAssessmentUITests extends UITestBase {

     @Test(enabled = false,groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {8704, 9969}) //TODO de-scoped , enable after scoped
    public void verifyESGGradeDistributionIsDisplayed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgGradeDistribution();
    }

    @Test(enabled = false,groups = {"regression", "ui", "smoke", "esg"})
    @Xray(test = {9133})
    public void verifyESGGRegionMapAndCountryTableDrawer() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateCountry();
    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {9967, 9898})
    public void verifyEsgAssessmentScoreLegend() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        test.info("Navigated to ESG Assessments Page");
        researchLinePage.validateEsgAssessmentLegends();
    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {8291})
    public void verifyEsgAssessmentWithBenchmark() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");

        researchLinePage.clickOnBenchmarkDropdown();
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.SelectAPortfolioFromBenchmark("Sample Portfolio");


    }

    @Test(groups = {"regression", "ui", "esg"})
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
            assertTestCase.assertEquals(expColumnNames.get(i),excelColumnNames.get(i));
        }

    }

    @Test(groups = {"regression", "ui", "esg"})
    @Xray(test = {11372})
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
            assertTestCase.assertEquals(expColumnNames.get(i),excelColumnNames.get(i));
        }

    }
}
