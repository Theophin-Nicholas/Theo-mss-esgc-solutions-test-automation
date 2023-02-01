package com.esgc.OnDemandAssessment.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.OnDemandAssessment.UI.Pages.OnDemandAssessmentPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class OnDemandAssessmentTests extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12001,12002,12011})
    public void validateOnDemandAssessmentRequest() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        researchLinePage.selectPortfolio("EsgWithPredictedScores");

        researchLinePage.clickMenu();
        onDemandAssessmentPage.onDemandAssessmentRequest.click();
        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

        onDemandAssessmentPage.clickReviewAndSendRequestButton();
        onDemandAssessmentPage.confirmRequest();
        onDemandAssessmentPage.clickProceedOnConfirmRequestPopup();

        assertTestCase.assertEquals(onDemandAssessmentPage.menuOptionPageHeader.getText(), "Moody's ESG360: Request On-Demand Assessment", "Moody's ESG360: Request On-Demand Assessment page verified");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {12010})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(10);

        researchLinePage.clickMenu();
        assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
    }
}
