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

import static com.esgc.Utilities.Groups.*;

public class SubsidiaryTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, SEARCH_ENTITY, ESG})
    @Xray(test = {3524})
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

        assertTestCase.assertEquals(dbSubsidiaryCompanyInfo.size(), 1, "Verifying subsidiary company");
    }

    @Test(groups = {REGRESSION, UI, SEARCH_ENTITY, ESG}, dataProviderClass = DataProviderClass.class, dataProvider = "InactiveSubsidiaryCompanyISIN")
    @Xray(test = {3468, 3681})
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

        assertTestCase.assertEquals(dbSubsidiaryCompanyInfo.size(), 0, "Verifying subsidiary company");

    }

    @Test(groups = {REGRESSION, UI, SEARCH_ENTITY, ESG}, dataProviderClass = DataProviderClass.class, dataProvider = "InactiveSubsidiaryCompanies")
    @Xray(test = {3405, 3681})
    public void searchWithInactiveSubsidiaryCompanyName(String subsidiaryCompanyName) {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Climate Dashboard");
        dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("PortfolioWithInactiveSubsidiaryCompany");

        ResearchLinePage researchLinePage = new ResearchLinePage();

        // Search with subsidiary company name
        researchLinePage.searchIconPortfolioPage.click();
        assertTestCase.assertTrue(researchLinePage.checkWarningWhenNoMatchEntry(subsidiaryCompanyName), "No results found message appeared as warning");

    }

    @Test(groups = {REGRESSION, UI, ESG}, dataProviderClass = DataProviderClass.class, dataProvider = "SubsidiaryCompanies")
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

    @Test(groups = {REGRESSION, UI, SEARCH_ENTITY, ESG}, dataProviderClass = DataProviderClass.class, dataProvider = "CompaniesWithNoSubsidiaryCompanies")
    @Xray(test = {3402})
    public void verifySubsidiaryLinkIsNotDisplayedWhenNoSubsidiaryCompanies(String companyName) {
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
