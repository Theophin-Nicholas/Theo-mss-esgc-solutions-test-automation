package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.RobotRunner;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

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
    @Xray(test = {11051, 11052, 11053, 11054, 11237, 11524})
    public void verifySubsidiaryCompanyNameInDashboard(String subsidiaryCompanyName, String parentCompanyName) {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");

        // Search with subsidiary company name, when clicked, parent company profile page should be opened
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        entityClimateProfilePage.searchAndLoadClimateProfilePage(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");

        // Verify subsidiary company in Coverage popup
        dashboardPage.viewAllCompaniesButton.click();
        BrowserUtils.wait(5);
        dashboardPage.verifyCompanyNameInCoveragePopup(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickableInCoveragePopup(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();
        dashboardPage.pressESCKey();

        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step3 - Can't automate as no subsidiary company controversies
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step4 - Can't automate as geomap is not yet ready
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step5 - Can't automate as no subsidiary under heatmap

        dashboardPage.clickAndSelectAPerformanceChart("Largest Holdings");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();

        dashboardPage.clickAndSelectAPerformanceChart("Leaders");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();

        dashboardPage.clickAndSelectAPerformanceChart("Laggards");
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();

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
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName),"Verify Parent Company page is displayed");;
        entityClimateProfilePage.closeEntityProfilePage();
        researchLinePage.closeMenuByClickingOutSide();

    }

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "SubsidiaryCompanies")
    @Xray(test = {11529, 11237})
    public void verifySubsidiaryCompanyNameInPortfolioAnalysis(String subsidiaryCompanyName, String parentCompanyName) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        DashboardPage dashboardPage = new DashboardPage();
        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();

        researchLinePage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithSubsidiaryCompany1");
        researchLinePage.navigateToResearchLine("ESG Assessments");

        // Search with subsidiary company name, when clicked, parent company profile page should be opened
        entityClimateProfilePage.searchAndLoadClimateProfilePage(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");

        // Verify subsidiary company in Coverage popup
        researchLinePage.selectEsgPortfolioCoverage();
        assertTestCase.assertTrue(researchLinePage.validateCompaniesEsgPopupIsDisplayed(), "Verify ESG Drawer coverage details");
        researchLinePage.verifyCompanyNameInCoveragePopup(subsidiaryCompanyName);
        researchLinePage.verifyCompanyIsClickableInCoveragePopup(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();
        dashboardPage.pressESCKey();

        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step3 - Can't automate as no subsidiary company controversies
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step4 - Can't automate as geomap is not yet ready
        // ToDo - ESGCA-11052, ESGCA-11053, ESGCA-11054: Step5 - Can't automate as no subsidiary under heatmap

        // Leaders & Laggards tables - Verify subsidiary company
        dashboardPage.verifyCompanyNameInTables(subsidiaryCompanyName);
        dashboardPage.verifyCompanyIsClickable(subsidiaryCompanyName);
        dashboardPage.clickTheCompany(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");
        entityClimateProfilePage.closeEntityProfilePage();

    }

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "SubsidiaryCompanies")
    @Xray(test = {11542, 11576})
    public void verifySubsidiaryLinkInEntityProfilePage(String subsidiaryCompanyName, String parentCompanyName) {
        DashboardPage dashboardPage = new DashboardPage();

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        entityClimateProfilePage.searchAndLoadClimateProfilePage(subsidiaryCompanyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(parentCompanyName), "Global header verification");

        entityClimateProfilePage.clickGlobalHeader();
        String  orbisId = entityClimateProfilePage.orbisIdValue.getText();
        dashboardPage.clickHideLink();

        DashboardQueries dashboardQueries = new DashboardQueries();
        List<Map<String, Object>> dbSubsidiaryCompanyInfo = dashboardQueries.getSubsidiaryCompanies(orbisId);

        entityClimateProfilePage.verifySubsidiaryCompaniesCount(dbSubsidiaryCompanyInfo.size());
        entityClimateProfilePage.clickSubsidiaryCompaniesLink();
        entityClimateProfilePage.verifySubsidiaryCompaniesPopup(subsidiaryCompanyName);
        dashboardPage.clickHideLink();

        entityClimateProfilePage.closeEntityProfilePage();
        dashboardPage.pressESCKey();
    }

    @Test(groups = {"regression", "ui", "search_entity"}, dataProviderClass = DataProviderClass.class, dataProvider = "CompaniesWithNoSubsidiaryCompanies")
    @Xray(test = {11575})
    public void verifySubsidiaryLinkWhenNoSubsidiaryCompanies(String companyName) {
        DashboardPage dashboardPage = new DashboardPage();

        EntityClimateProfilePage entityClimateProfilePage = new EntityClimateProfilePage();
        entityClimateProfilePage.searchAndLoadClimateProfilePage(companyName);
        assertTestCase.assertTrue(entityClimateProfilePage.validateGlobalCompanyNameHeader(companyName), "Global header verification");
        assertTestCase.assertFalse(entityClimateProfilePage.verifySubsidiaryCompaniesLink(), "Verify subsidiary link is not available");

        entityClimateProfilePage.clickGlobalHeader();
        assertTestCase.assertFalse(entityClimateProfilePage.verifySubsidiaryCompaniesSectionInCoveragePopup(),"Verify Subsidiary Companies popup header");
        dashboardPage.clickHideLink();

        entityClimateProfilePage.closeEntityProfilePage();
        dashboardPage.pressESCKey();
    }

}
