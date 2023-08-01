package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class PortfolioAnalysisEntitlementsTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4286, 4296})
    public void validatePhysicalRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(2);
        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);
        System.out.println("actualAvailableResearchLines = " + actualAvailableResearchLines);
//        System.out.println("researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK) = " + researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK));
//        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK),
//                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4287, 4297})
    public void validateTransitionRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

//        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.TRANSITION_RISK),
//                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/temperaturealignment");
    }

    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4291, 4298}) //TODO No climate governance, de-scopped  MAP this ESGCA-11327 with this test case
    public void validateClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

       /* Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/esgassessments");

    }

    @Test(groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4293, 4320})
    public void validatePhysicalAndTransitionRiskBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

//        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK_TRANSITION_RISK),
//                "Validating list of accessible research lines");

        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4295, 4322})
    public void validateTransitionRiskAndClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK_CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines" + actualAvailableResearchLines);

       /* Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.TRANSITION_RISK_CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {4294, 4321})
    public void validatePhysicalRiskClimateGovernanceBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();

        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK_CLIMATE_GOVERNANCE);
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");

        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines " + actualAvailableResearchLines);

        /*Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfResearchLines(EntitlementsBundles.PHYSICAL_RISK_CLIMATE_GOVERNANCE),
                "Validating list of accessible research lines");
*/
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));

        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
    }

    @Test(groups = {REGRESSION, UI, SMOKE, ENTITLEMENTS})
    @Xray(test = {1936, 1937, 6083, 6084})
    public void validateExternalUserHasAllResearchLinesBundleAccess() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        LoginPage login = new LoginPage();
        login.login();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        List<String> actualAvailableResearchLines = researchLinePage.getAvailableResearchLines();
        test.info("Available research lines " + actualAvailableResearchLines);
        Assert.assertEquals(actualAvailableResearchLines, researchLinePage.getExpectedListOfPortfolioAnalysisResearchLines(EntitlementsBundles.ALL),
                "Validating list of accessible research lines");
        String currentUrl = Driver.getDriver().getCurrentUrl();
        currentUrl = currentUrl.substring(currentUrl.indexOf("portfolioanalysis"));
        Assert.assertEquals(currentUrl, "portfolioanalysis/physicalriskhazards");
        login.clickOnLogout();
    }




    @Xray(test = {8219})
    @Test(groups = {REGRESSION, UI, SMOKE, ESG, ENTITLEMENTS},
            description = "Verify research line navigation")
    public void verifyESGAssessmentRLIsNotVailable() {
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().navigate().refresh();
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithParams("esg-test4@outlook.com ", "Helloworld24");
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        List<String> actualResearchLines = researchLinePage.getAvailableResearchLines();
        System.out.println("actualResearchLines = " + actualResearchLines);
        assertTestCase.assertEquals(actualResearchLines.size(), 6, "Existing Research Lines Validation ");
        assertTestCase.assertTrue(actualResearchLines.stream().filter(e -> e.equals("ESG Assessments")).count()==0);

    }

}
