package com.esgc.Tests;

import com.esgc.Pages.EMCMainPage;
import com.esgc.Pages.LoginPageEMC;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class MainPageTests extends EMCUITestBase {
    @Test(groups = {"EMC", "ui", "smoke","regression","prod"})
    @Xray(test = {2292})
    public void EMCLandingPageTest() {

        EMCMainPage mainPage = new EMCMainPage();
        assertTestCase.assertTrue(mainPage.isEMCTitleIsDisplayed(), "Main Title Verification");
        test.pass("User is on landing page");

        mainPage.openSidePanel();

        assertTestCase.assertTrue(mainPage.isLabelDisplayed("Applications"),"Applications Label is Displayed");
        assertTestCase.assertTrue(mainPage.isLabelDisplayed("Accounts"),"Accounts Label is Displayed");
        assertTestCase.assertTrue(mainPage.isLabelDisplayed("Users"),"Users Label is Displayed");
        assertTestCase.assertTrue(mainPage.isLabelDisplayed("Configuration"),"Configuration Label is Displayed");

        mainPage.clickAccountsButton();

    }

    @Test(groups = {"EMC", "ui", "smoke","regression"})
    @Xray(test = {3520})
    public void wrongUsernameLoginTest() {
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPage = new LoginPageEMC();
        loginPage.loginWithWrongUser();
        BrowserUtils.waitForVisibility(loginPage.UnauthorisedUserErrorMsg, 5);
        assertTestCase.assertTrue(loginPage.UnauthorisedUserErrorMsg.isDisplayed(),
                "Login with invalid username failed and login page loaded again");
        Driver.closeDriver();
    }

    @Test(groups = {"EMC", "ui", "smoke","regression"})
    @Xray(test = {3521})
    public void wrongPasswordLoginTest() {
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.waitForPageToLoad(10);
        LoginPageEMC loginPage = new LoginPageEMC();
        loginPage.loginWithWrongPass();
        BrowserUtils.waitForVisibility(loginPage.UnauthorisedUserErrorMsg, 5);
        assertTestCase.assertTrue(loginPage.UnauthorisedUserErrorMsg.isDisplayed(),
                "Login with invalid username failed and login page loaded again");
        Driver.closeDriver();
    }
}
