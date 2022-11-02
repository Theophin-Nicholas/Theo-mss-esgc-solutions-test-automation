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
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SubsidiaryTests extends UITestBase {


    @Test(groups = {"regression", "ui", "search_entity"})
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
    @Xray(test = {11051, 11052, 11053, 11054, 11237})
    public void searchWithSubsidiaryCompanyName() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");

        String subsidiaryCompanyName = "National Grid North America, Inc.";
        String parentCompanyName = "National Grid Plc";

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();

        // Search with subsidiary company name
        entityProfilePage.searchAndLoadClimateProfilePage(subsidiaryCompanyName);

        // Parent company should be opened
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(parentCompanyName), "Global header verification");

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        entityClimateProfilePage.closeEntityProfilePage();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");

        // Verify subsidiary company name along with parent company
        dashboardPage.clickCoverageLink();
        BrowserUtils.wait(5);
        dashboardPage.verifyCompanyNameInCoveragePopup(subsidiaryCompanyName, parentCompanyName);
        dashboardPage.verifyCompanyIsNotClickableInCoveragePopup(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickableInCoveragePopup(parentCompanyName);
        dashboardPage.pressESCKey();

        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step3 - Can't automate as no subsidiary company controversies
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step4 - Can't automate as geomap is not yet ready
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step5 - Can't automate as no subsidiary under heatmap

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

    @Test(groups = {"regression", "ui", "search_entity"}, dataProvider = "InactiveSubsidiaryCompany")
    @Xray(test = {11058, 11242})
    public void VerifyFileUploadWithInactiveSubsidiaryCompany(String inactiveSubsidiaryCompany) {
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
        List<Map<String, Object>> dbSubsidiaryCompanyInfo = dashboardQueries.getSubsidiaryCompany(inactiveSubsidiaryCompany);

        assertTestCase.assertTrue(dbSubsidiaryCompanyInfo.size()==0, "Verifying subsidiary company");

    }

    @Test(groups = {"regression", "ui", "search_entity"})
    @Xray(test = {11059})
    public void searchWithInactiveSubsidiaryCompanyName() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithInactiveSubsidiaryCompany");

        String subsidiaryCompanyName = "XTO ENERGY INC";
        ResearchLinePage researchLinePage = new ResearchLinePage();

        // Search with subsidiary company name
        researchLinePage.searchIconPortfolioPage.click();
        assertTestCase.assertTrue(researchLinePage.checkWarningWhenNoMatchEntry(subsidiaryCompanyName), "No results found message appeared as warning");

    }

    @DataProvider(name = "InactiveSubsidiaryCompany")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"XS0054748412"},
        };
    }

}
