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

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "SubsidiaryCompanies")
    @Xray(test = {11051, 11052, 11053, 11054, 11237})
    public void searchWithSubsidiaryCompanyName(String subsidiaryCompanyName, String parentCompanyName) {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");

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

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "InactiveSubsidiaryCompanyISIN")
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

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "InactiveSubsidiaryCompanies")
    @Xray(test = {11059, 11242})
    public void searchWithInactiveSubsidiaryCompanyName(String subsidiaryCompanyName) {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithInactiveSubsidiaryCompany");

        ResearchLinePage researchLinePage = new ResearchLinePage();

        // Search with subsidiary company name
        researchLinePage.searchIconPortfolioPage.click();
        assertTestCase.assertTrue(researchLinePage.checkWarningWhenNoMatchEntry(subsidiaryCompanyName), "No results found message appeared as warning");

    }

    @Test(groups = {"regression", "ui"}, dataProviderClass = DataProviderClass.class, dataProvider = "SubsidiaryCompanies")
    @Xray(test = {11541})
    public void verifySubsidiaryCompanyInPortfolioManagement(String subsidiaryCompanyName, String parentCompanyName) {
        String portfolioName = "PortfolioWithSubsidiaryCompany1";

        ResearchLinePage researchLinePage = new ResearchLinePage();
        DashboardPage dashboardPage = new DashboardPage();
        researchLinePage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        researchLinePage.selectPortfolioFromPortfolioSettings(portfolioName);

        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);

        researchLinePage.portfolioDropDownMenu.click();
        researchLinePage.portfolioSettingsLargest20Investment.click();
        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);
        researchLinePage.clickTheCompany(subsidiaryCompanyName);
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalHeader(parentCompanyName),"Verify Parent Company page is displayed");;
        entityClimateProfilePage.closeEntityProfilePage();
        researchLinePage.closeMenuByClickingOutSide();

    }

}
