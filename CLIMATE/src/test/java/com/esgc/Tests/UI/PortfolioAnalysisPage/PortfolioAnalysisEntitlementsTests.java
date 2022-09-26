package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

//import edu.emory.mathcs.backport.java.util.Arrays;

public class PortfolioAnalysisEntitlementsTests extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4286, 4296})
    public void validatePhysicalRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);
        System.out.println("actualAvailableResearchLines = " + actualAvailableResearchLines);
        System.out.println("researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK) = " + researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK));
        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK),
                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4287, 4297})
    public void validateTransitionRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.TRANSITION_RISK),
                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/temperaturealignment");
    }

    @Test(groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4291, 4298})
    public void validateClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

       /* Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/esgassessments");

    }

    @Test(groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4293, 4320})
    public void validatePhysicalAndTransitionRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK),
                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test (groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4295, 4322})
    public void validateTransitionRiskAndClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK_CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

       /* Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.TRANSITION_RISK_CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test( groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {4294, 4321})
    public void validatePhysicalRiskClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines " + actualAvailableResearchLines);

        /*Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK_CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(groups = {"regression", "ui", "smoke", "entitlements"})
    @Xray(test = {1936, 1937, 6093, 6084})
    public void validateExternalUserHasAllResearchLinesBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();
        login.login();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines " + actualAvailableResearchLines);
        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.ALL),
                "Validating list of accessible research lines");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));
        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
        login.clickOnLogout();
    }


    @Test(groups = {"regression", "ui", "smoke", "entitlements","esg"}, description = "Verify research line navigation")
    @Xray(test = {8214, 8215})
    public void verifyESGAssessmentRL() {
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithParams("esg-test1+no-controversy@outlook.com","Moodys123");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        List<String> actualResearchLines = researchLinePage.getAvailableResearchLines();
        assertTestCase.assertEquals(actualResearchLines.size(), 7, "Existing Research Lines Validation ");
        assertTestCase.assertEquals(actualResearchLines.get(6), "ESG Assessments", "ESG Assessments Research Line is available");
        researchLinePage.validateExportButtonIsNotAvailable() ;
    }

    @Xray(test = {8219})
    @Test(groups = {"regression", "ui", "smoke", "esg","entitlements"},
            description = "Verify research line navigation")
    public void verifyESGAssessmentRLIsNotVailable() {
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().navigate().refresh();
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithParams("esg-test4@outlook.com ","Helloworld24");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);

        List<String> actualResearchLines = researchLinePage.getAvailableResearchLines();
        System.out.println("actualResearchLines = " + actualResearchLines);
        assertTestCase.assertEquals(actualResearchLines.size(), 6, "Existing Research Lines Validation ");
       // assertTestCase.assertTrue(actualResearchLines.stream().filter(e -> e.equals("ESG Assessments")).count()==0);

    }

}
