package com.esgc.Base.UI.Tests.LoginPageTest;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

/**
 * Created by ChaudhS2 on 10/14/2021.
 */
public class LoginPageTest extends DashboardUITestBase {


    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4388, 1547})
    public void verifyValidCredentialsLoginPampa() {
        LoginPage login = new LoginPage();

        test.info("Logging into using valid username and password");
        login.login();
        assertTestCase.assertTrue(login.checkIfUserLoggedInSuccessfully(), "User Login.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -2070 : Verify User is able to Click Forgot Password on Pampa Login page")
    @Xray(test = 2070)
    public void verifyForgotPasswordLinkIsClickablePampa() {
        test.info("Clicking on the need help sigining in link.");
        LoginPage login = new LoginPage();
        login.clickNeedHelpSignIn();

        test.info("User clicked on Need help sigining in? link");
        assertTestCase.assertTrue(login.checkIfForgotPasswordIsClickable(), "Forgot Password link was not clickable.");

        test.info("Click on Forgot Password link");
        login.clickForgetPassword();

        assertTestCase.assertTrue(login.resetViaEmailButtonIsDisplayed(), "User was able to click on the Forgot password link.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT-4938 : Verify that MESG Users able to Click On Help Page From the MESG Log in Screen")
    @Xray(test = {4415, 4938})
    public void verifyHelpLinkIsClickablePampa() {
        test.info("Clicking on the need help sigining in link.");
        LoginPage login = new LoginPage();
        login.clickNeedHelpSignIn();

        test.info("User clicked on Need help sigining in? link");
        assertTestCase.assertTrue(login.checkIfHelpLinkIsClickable(), "Help link should be clickable.");

        test.info("Click on Forgot Password link");
        login.clickHelpLink();

        login.checkIfUserIsOnHelpPage();
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -4932 : Verify Remember Me checkbox is Unselected by Default")
    @Xray(test = 4932)
    public void verifyRememberMeCheckboxDefaultStatusPampa() {
        test = report.createTest("Verify Remember Me checkbox is unchecked by default on Pampa Login Page.");
        LoginPage login = new LoginPage();

        test.info("Checking Remember Me checkbox is present");
        assertTestCase.assertTrue(login.checkIfRememberMeCheckboxIsPresent(), "Remember Me Checkbox was not present on Login Page.");

        test.info("Verify that Remember Me checkbox was unchecked by default.");
        assertTestCase.assertFalse(login.checkIfRememberMeCheckBoxIsUncheckByDefault(), "User was able to click on the Forgot password link.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -1528/1486 : Verify Unauthorised user gets 'Unable to Sign in' Message.")
    @Xray(test = {1486, 1528})
    public void verifyTheValidationForInvalidUserPampa() {
        test = report.createTest("Verify the validation for the unauthorised User.");
        LoginPage login = new LoginPage();

        test.info("Logging into using invalid username and password");
        login.loginWithInvalidCredentials();
        assertTestCase.assertTrue(login.checkIfCorrectValidationErrorMsgDisplayed(), "Unauthorised user was able to Login without any error.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -1646 : Verify the Next button functionality.")
    @Xray(test = {1646})
    public void verifyTheNextButtonFunctionalityPampa() {

        test.info("Verify Next button is present and clickable on Login page.");
        test.info("Checking if Next button is present on Login page.");
        LoginPage login = new LoginPage();

        assertTestCase.assertTrue(login.checkIfNextButtonIsPresent(), "Next Button was not present.");

        test.info("Checking if Next button is clickable.");
        assertTestCase.assertTrue(login.checkIfNextButtonIsClickable(), "User was not able to click on the Next button.");

        test.info("Providing valid username at Login page and then click Next button.");
        login.EnterUserName();

        test.info("Click on Next button...");
        test.info("Checking Next button click redirects user to Okta Login page");
        assertTestCase.assertTrue(login.passwordBox.isDisplayed(), "Next button is not working as expected.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -1786 : Verify all the fields on Login page has Placeholder.")
    @Xray(test = {1786})
    public void verifyThePlaceholderForLoginPageFieldsPampa() {

        test.info("Verify the Place folder for different fields on Login page.");
        test.info("Checking the Username placeholder for Username field");
        LoginPage login = new LoginPage();

        assertTestCase.assertEquals(login.usernameBox.getAttribute("name"), "username", "Username field has username placeholder.");

        test.info("Checking the Remember me placeholder for Remember me checkbox field");
        assertTestCase.assertEquals(login.rememberMeCheckboxLabel.getText(), "Remember me", "Remember Me checkbox has Remember me placeholder.");

        test.info("Providing valid username at Login page and then click Next button.");
        login.EnterUserName();

        test.info("Click on Next button...");
        test.info("Checking Next button click redirects user to Okta Login page");

        test.info("Checking the Password placeholder for password textbox field");
        assertTestCase.assertEquals(login.passwordBox.getAttribute("name"), "password", "Password field has password placeholder.");

        test.info("Checking the Remember me placeholder for Remember me checkbox field");
        assertTestCase.assertEquals(login.rememberMeCheckboxLabel.getText(), "Remember me", "Remember Me checkbox has Remember me placeholder.");
    }

    @Test(groups = {REGRESSION, UI},
            description = "ESGT -4642 : User is not Able to Login with a Blank Username and Password")
    @Xray(test = 4642)
    public void verifyUserIsNotableToLoginWithBlankUserNamePampa() {

        test = report.createTest("Verify Next button is present and clickable on Login page.");
        test.info("Checking if Next button is present on Login page.");
        LoginPage login = new LoginPage();

        assertTestCase.assertTrue(login.checkIfNextButtonIsPresent(), "Next Button was not present.");

        test.info("Checkign if Next button is clickable.");
        assertTestCase.assertTrue(login.checkIfNextButtonIsClickable(), "User was not able to click on the Next button.");

        test.info("Providing valid username at Login page and then click Next button.");
        login.EnterUserNameAsBlank();
        BrowserUtils.wait(5);
        test.info("Click on Next button...");
        assertTestCase.assertTrue(login.checkIfCorrectValidationErrorMsgForBlankUserNameDisplayed(), "No Error message was displayed for the blank UserName.");

        login.EnterUserName();
        BrowserUtils.wait(5);
        // login.checkTermsAndConditions();
        login.EnterPasswordAsBlank();
        assertTestCase.assertTrue(login.checkIfCorrectValidationErrorMsgForBlankPasswordDisplayed(), "No Error message was displayed for the blank Password.");

        //  test.info("Checking Next button click redirects user to Okta Login page");
        //  assertTestCase.assertTrue(login.passwordBox.isDisplayed(), "Next button is not working as expected.");
    }

}