package com.esgc.Tests.UI.DashboardPage.Export;

import com.esgc.Pages.DashboardPage;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;


public class ExportDashboardTests extends DashboardUITestBase {


    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6080})
    public void exportCompaniesInPortfolioBySectorTest() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Sector");
        dashboardPage.selectViewBySector();

        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 6080);

        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {6080})
    public void exportCompaniesInPortfolioByRegionTest() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickViewCompaniesAndInvestments();
        test.info("Navigated to Companies Page");

        test.info("Verification of Companies Export Based on Region");
        dashboardPage.selectViewByRegion();

        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");

        assertTestCase.assertTrue(dashboardPage.isCompaniesAndInvestmentsExcelDownloaded(), "Verify Download of Excel file with Companies and Investments details", 248);

        dashboardPage.deleteDownloadFolder();
    }

    @Test(groups = {"dashboard", "regression", "ui"})
    @Xray(test = {7167,7238})
    public void temperatureAlignmentFieldsInExportedFile() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.clickCoverageLink();
        test.info("Navigated to Companies Page");

        dashboardPage.clickExportCompaniesButton();
        test.info("Exported All Companies and Investments Details in excel format");

        dashboardPage.istemperatureAlignmentFieldsavailableInExportedExcel();
        dashboardPage.deleteDownloadFolder();

    }

}
