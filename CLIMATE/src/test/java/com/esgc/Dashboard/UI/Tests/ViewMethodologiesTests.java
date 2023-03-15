package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class ViewMethodologiesTests extends DashboardUITestBase {

    @Test(groups = {DASHBOARD, REGRESSION, UI, SMOKE, ESG})
    @Xray(test = {8325, 8328, 8334,9729})
    public void verifyViewMethodologies() {
        // ESGCA-8334: General UI Checks for Methodology Drawer
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");
        dashboardPage.verifyMethodologiesHeader();
        dashboardPage.verifyMethodologiesLinks();
        dashboardPage.clickHideLink();
    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8304})
    public void verifyViewMethodologiesPhysicalRisk_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        List<String> methodologySectionNames = dashboardPage.getMethodologiesSections();
        dashboardPage.clickHideLink();
        assertTestCase.assertTrue(methodologySectionNames.contains("Physical Risk"), "Verify Methodologies Sections");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8304})
    public void verifyViewMethodologiesTransitionRisk_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        List<String> methodologySectionNames = dashboardPage.getMethodologiesSections();
        dashboardPage.clickHideLink();
        assertTestCase.assertTrue(methodologySectionNames.contains("Transition Risk"), "Verify Methodologies Sections");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI, ESG})
    @Xray(test = {8304})
    public void verifyViewMethodologiesEsg_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ESG_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        List<String> methodologySectionNames = dashboardPage.getMethodologiesSections();
        dashboardPage.clickHideLink();
        assertTestCase.assertTrue(methodologySectionNames.contains("ESG Assessment Framework"), "Verify Methodologies Sections");
        assertTestCase.assertTrue(methodologySectionNames.contains("ESG Categories and Subcategories"), "Verify Methodologies Sections");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8304})
    public void verifyViewMethodologiesControversies_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_CONTROVERSIES_ENTITLEMENT);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");
        assertTestCase.assertTrue(dashboardPage.methodologyPopup_Link_RiskAssessmentts.getText().equals("Read more about Controversy Risk Assessment Methodology"), "Validate link as 'Read more about Controversy Risk Assessment Methodology'");
        dashboardPage.clickHideLink();

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8304})
    public void verifyViewMethodologiesWithPRandTR_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        List<String> methodologySectionNames = dashboardPage.getMethodologiesSections();
        dashboardPage.clickHideLink();
        assertTestCase.assertTrue(methodologySectionNames.contains("Physical Risk"), "Verify Methodologies Section - Physical Risk");
        assertTestCase.assertTrue(methodologySectionNames.contains("Transition Risk"), "Verify Methodologies Sections - Transition Risk");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8304})
    public void verifyViewMethodologiesWithAllEntitlements() {

        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        List<String> methodologySectionNames = dashboardPage.getMethodologiesSections();
        dashboardPage.clickHideLink();
        assertTestCase.assertTrue(methodologySectionNames.contains("Physical Risk"), "Verify Methodologies Section - Physical Risk");
        assertTestCase.assertTrue(methodologySectionNames.contains("Transition Risk"), "Verify Methodologies Sections - Transition Risk");
        assertTestCase.assertTrue(methodologySectionNames.contains("ESG Assessment Framework"), "Verify Methodologies Sections");
        assertTestCase.assertTrue(methodologySectionNames.contains("ESG Categories and Subcategories"), "Verify Methodologies Sections");

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8326})
    public void verifyViewMethodologiesLinksPhysicalRisk_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        assertTestCase.assertTrue(dashboardPage.methodologyPopup_Link_RiskAssessmentts.getText().equals("Read more about Physical Risk"), "Validate link as 'Read more about Physical Risk'");
        dashboardPage.methodologyPopup_Link_RiskAssessmentts.click();
        assertTestCase.assertTrue(researchLinePage.verifyMethodologyLink("Methodology_CorporatePhysicalClimateRiskOperationsRisk(Jan2022)"), "Validate link behavior 'Read more about Physical Risk'");

        dashboardPage.clickHideLink();

    }

    @Test(groups = {DASHBOARD, REGRESSION, UI})
    @Xray(test = {8327})
    public void verifyViewMethodologiesLinksTransitionRisk_Bundle() {
        LoginPage login = new LoginPage();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        BrowserUtils.wait(4);
        dashboardPage.selectViewMethodologies();
        assertTestCase.assertTrue(dashboardPage.verifyMethodologiesPopup(), "Verify Methodologies popup");

        assertTestCase.assertTrue(dashboardPage.methodologyPopup_Links.get(0).getText().equals("Read more about Transition Risk"), "Validate link as 'Read more about Transition Risk'");
        dashboardPage.methodologyPopup_Links.get(0).click();
        assertTestCase.assertTrue(researchLinePage.verifyMethodologyLink("Methodology_Climate_ClimateRiskAssessment"), "Validate link behavior 'Read more about Transition Risk'");

        assertTestCase.assertTrue(dashboardPage.methodologyPopup_Links.get(1).getText().equals("Read more about Temperature Alignment"), "Validate link as 'Read more about Temperature Alignment'");
        dashboardPage.methodologyPopup_Links.get(1).click();
        assertTestCase.assertTrue(researchLinePage.verifyMethodologyLink("Methodology_Climate_TemperatureAlignmentData"), "Validate link behavior 'Read more about Temperature Alignment'");

        dashboardPage.clickHideLink();

    }

}
