package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DashboardQueries;
import com.esgc.Utilities.Database.EntityPageQueries;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SubsidiaryTests extends UITestBase {


    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {11042})
    public void VerifyFileUploadSubsidiaryCompany() {
        DashboardPage dashboardPage = new DashboardPage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithSubsidiaryCompany();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        assertTestCase.assertEquals(dashboardPage.AlertMessage.getText(), "Portfolio Upload Successfully Saved", "Success message verified");
        dashboardPage.clickCloseButton();
        dashboardPage.waitForDataLoadCompletion();
        test.info("Waited for the Successful popup's visibility");

        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbSubsidiaryCompanyInfo = dashboardQueries.getSubsidiaryCompany("XS0097093636");

        assertTestCase.assertTrue(dbSubsidiaryCompanyInfo.size()==1, "Verifying subsidiary company");
    }

    @Test(groups = {"regression", "ui", "search_entity"})
    @Xray(test = {11051, 11052, 11053, 11054})
    public void searchWithSubsidiaryCompanyName() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");

        String subsidiaryCompanyName = "National Grid North America, Inc.";
        String parentCompanyName = "National Grid Plc";

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();

        // Search with subsidiary company name
        entityProfilePage.searchAndLoadClimateProfilePage(subsidiaryCompanyName);

        // Parent company should be opened
        Assert.assertTrue(entityProfilePage.validateGlobalHeader(parentCompanyName));

        dashboardPage.pressESCKey();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");

        // Verify subsidiary company name along with parent company
        dashboardPage.clickCoverageLink();
        dashboardPage.verifyCompanyNameInCoveragePopup(subsidiaryCompanyName, parentCompanyName);
        dashboardPage.verifyCompanyIsNotClickableInCoveragePopup(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickableInCoveragePopup(parentCompanyName);
        dashboardPage.pressESCKey();

        dashboardPage.clickAndSelectAPerformanceChart("Largest Holdings");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName, parentCompanyName);
        dashboardPage.verifyCompanyIsNotClickable(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(parentCompanyName);

        dashboardPage.clickAndSelectAPerformanceChart("Leaders");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName, parentCompanyName);
        dashboardPage.verifyCompanyIsNotClickable(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(parentCompanyName);

        dashboardPage.clickAndSelectAPerformanceChart("Laggards");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName, parentCompanyName);
        dashboardPage.verifyCompanyIsNotClickable(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(parentCompanyName);

    }

    @Test(groups = {"regression", "ui", "smoke", "dashboard"})
    @Xray(test = {11058})
    public void VerifyFileUploadWithInactiveSubsidiaryCompany() {
        DashboardPage dashboardPage = new DashboardPage();

        BrowserUtils.wait(5);
        dashboardPage.clickUploadPortfolioButton();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(2);
        String inputFile = PortfolioFilePaths.portfolioWithInactiveSubsidiaryCompany();
        RobotRunner.selectFileToUpload(inputFile);
        BrowserUtils.wait(4);
        dashboardPage.clickUploadButton();
        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        assertTestCase.assertTrue(dashboardPage.AlertMessage.getText().contains("the system was unable to match"), "Error message verified");
        dashboardPage.closePopUp();
        dashboardPage.waitForDataLoadCompletion();

        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbSubsidiaryCompanyInfo = dashboardQueries.getSubsidiaryCompany("XS0054748412");

        assertTestCase.assertTrue(dbSubsidiaryCompanyInfo.size()==0, "Verifying subsidiary company");

    }

    @Test(groups = {"regression", "ui", "search_entity"})
    @Xray(test = {11059})
    public void searchWithInactiveSubsidiaryCompanyName() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");

        String subsidiaryCompanyName = "XTO ENERGY INC";
        ResearchLinePage researchLinePage = new ResearchLinePage();

        // Search with subsidiary company name
        researchLinePage.searchIconPortfolioPage.click();
        assertTestCase.assertTrue(researchLinePage.checkWarningWhenNoMatchEntry(subsidiaryCompanyName), "No results found message appeared as warning");

    }

    }
