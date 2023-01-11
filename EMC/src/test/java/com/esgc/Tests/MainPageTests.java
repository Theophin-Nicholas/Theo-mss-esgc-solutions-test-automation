package com.esgc.Tests;

import com.esgc.Pages.EMCMainPage;
import com.esgc.Pages.LoginPageEMC;
import com.esgc.TestBases.EMCUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class MainPageTests extends EMCUITestBase {
    @Test(groups = {EMC, UI, SMOKE,REGRESSION,PROD})
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

    @Test(groups = {EMC, UI, SMOKE,REGRESSION, PROD})
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

    @Test(groups = {EMC, UI, SMOKE,REGRESSION})
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

    @Test(groups = {EMC, UI,REGRESSION}, description = "Login | EMC | Verify that Users can't Access Using Empty Username or Password")
    @Xray(test = {3530, 3531})
    public void verifyUserCantAccessUsingEmptyCredentialsTest() {
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.wait(5);
        LoginPageEMC loginPage = new LoginPageEMC();

        //Click Sign In button
        //Error message is displayed on the Login Page "We found some errors. Please review the form and make corrections." and a Warning in the Username Field "This field cannot be left blank"
        BrowserUtils.waitForClickablility(loginPage.nextButton,10).click();
        assertTestCase.assertTrue(loginPage.warningMsg.isDisplayed(),"Empty username login attempt warning message is displayed");
        assertTestCase.assertTrue(loginPage.blankUsernameErrorMessage.isDisplayed(),"This field cannot be left blank error message is displayed");


        String username = "erolvera.mx+030@gmail.com";
        String password = "Moodys123";

        //Type a valid External Email
        //Click Next button
        //Click Sign In button
        //Error message is displayed on the Login Page "We found some errors. Please review the form and make corrections." and in Password field error message "Please enter a password"
        BrowserUtils.waitForVisibility(loginPage.usernameBox,10).sendKeys(username);
        BrowserUtils.waitForClickablility(loginPage.nextButton,10).click();
        BrowserUtils.wait(2);
        BrowserUtils.waitForClickablility(loginPage.loginButton,10).click();
        assertTestCase.assertTrue(loginPage.warningMsg.isDisplayed(),"Empty username login attempt warning message is displayed");
        assertTestCase.assertTrue(loginPage.blankPasswordErrorMessage.isDisplayed(),"This field cannot be left blank error message is displayed");

        Driver.closeDriver();
    }

    @Test(groups = {EMC, UI,REGRESSION}, description = "Login | EMC | Verify that Users can't Access Using External Credentials")
    @Xray(test = {3525})
    public void verifyUserCantAccessUsingExternalCredentialsTest() {
        Driver.closeDriver();
        Driver.getDriver().get(Environment.EMC_URL);
        BrowserUtils.wait(5);
        LoginPageEMC loginPage = new LoginPageEMC();

        //Type a valid External Email
        //Login page allows typing Username (external email address) and password
        //Error message is displayed on the Login Page "User is not assigned to the client application"
        String username = "erolvera.mx+030@gmail.com";
        String password = "Moodys123";
        loginPage.loginEMCWithParams(username, password);
        BrowserUtils.wait(2);
        BrowserUtils.waitForVisibility(loginPage.UserNotAssignedApplicationErrorMsg, 5);
        assertTestCase.assertTrue(loginPage.UserNotAssignedApplicationErrorMsg.isDisplayed(),
                "User is not assigned to client application error message is displayed");
        Driver.closeDriver();
    }
}
