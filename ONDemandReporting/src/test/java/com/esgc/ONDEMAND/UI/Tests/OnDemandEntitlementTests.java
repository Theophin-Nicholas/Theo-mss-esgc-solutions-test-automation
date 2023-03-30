package com.esgc.ONDEMAND.UI.Tests;

import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.Common.UI.TestBases.UITestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Utilities.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class OnDemandEntitlementTests extends UITestBase {

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {12010, 12827, 13741})
    public void verifyOnDemandAssessmentRequestIsNotAvailable() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();

        BrowserUtils.wait(10);

        onDemandAssessmentPage.clickMenu();
        assertTestCase.assertFalse(onDemandAssessmentPage.isOnDemandAssessmentRequestAvailableInMenu(), "On-Demand Assessment Request option should not be available");
        login.clickOnLogout();
    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {12080})
    public void validateOnDemandEntitilementForPortfolioUpload() {
        String portfolioName = "OnDemandEntities";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload("Self-Assessed", "", 10, portfolioName);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
            onDemandAssessmentPage.closePopUp();
        }
    }


    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {12393})
    public void validatePortfolioUploadWithOnlyPredictedEntitlement() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_PREDICTEDSCORE_AND_CLIMATE);
        String portfolioName = "OnDemandEntitiesPredictedEntitlement";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload("Self-Assessed", "N", 10, portfolioName);
        onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "Dashboard");
        Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
        onDemandAssessmentPage.closePopUp();
    }

    @Test(groups = {REGRESSION, UI, COMMON})
    @Xray(test = {12394})
    public void validatePortfolioUploadWithOnDemandEntitlement() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ONDEMAND_ENTITLEMENT);
        String portfolioName = "OnDemandEntitiesOnDemandEntitlement";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload("Self-Assessed", "N", 10, portfolioName);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        if (onDemandAssessmentPage.IsPortfolioTableLoaded()) {
            onDemandAssessmentPage.uploadPortfolio(portfolioFilePath, "OnDemand");
            Assert.assertTrue(onDemandAssessmentPage.checkifSuccessPopUpIsDisplyed(), "Validating if portfolio updated successfully");
            onDemandAssessmentPage.closePopUp();
        }
    }
}
