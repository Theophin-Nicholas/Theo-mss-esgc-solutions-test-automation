package com.esgc.Tests.UI.PampaLoginPageTest;

import com.esgc.Pages.LoginPage;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by ChaudhS2 on 10/14/2021.
 */
public class LoginPageTest extends DashboardUITestBase {


    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {182})
    public void verifyValidCredentialsLoginPampa() {
        try {
            LoginPage login = new LoginPage();

            test.info("Logging into using valid username and password");
            login.login();
            assertTestCase.assertTrue(login.checkIfUserLoggedInSuccessfully(), "User Login.");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA -1778 : Verify User is able to Click Forgot Password on Pampa Login page")
    @Xray(test = 1778)
    public void verifyForgotPasswordLinkIsClickablePampa() {
        try {
            test.info("Clicking on the need help sigining in link.");
            LoginPage login = new LoginPage();
            login.clickNeedHelpSignIn();

            test.info("User clicked on Need help sigining in? link");
            Assert.assertTrue(login.checkIfForgotPasswordIsClickable(), "Forgot Password link was not clickable.");

            test.info("Click on Forgot Password link");
            login.clickForgetPassword();

            Assert.assertTrue(login.resetViaEmailButtonIsDisplayed(), "User was able to click on the Forgot password link.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA -1427 : Verify Remember Me checkbox is Unselected by Default")
    @Xray(test = 1427)
    public void verifyRememberMeCheckboxDefaultStatusPampa() {
        try {
            test = report.createTest("Verify Remember Me checkbox is unchecked by default on Pampa Login Page.");
            LoginPage login = new LoginPage();

            test.info("Checking Remember Me checkbox is present");
            Assert.assertTrue(login.checkIfRememberMeCheckboxIsPresent(), "Remember Me Checkbox was not present on Login Page.");

            test.info("Verify that Remember Me checkbox was unchecked by default.");
            Assert.assertFalse(login.checkIfRememberMeCheckBoxIsUncheckByDefault(), "User was able to click on the Forgot password link.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA -1606/1508 : Verify Unauthorised user gets 'Unable to Sign in' Message.")
    @Xray(test = {1508, 1603})
    public void verifyTheValidationForInvalidUserPampa() {
        try {
            test = report.createTest("Verify the validation for the unauthorised User.");
            LoginPage login = new LoginPage();

            test.info("Logging into using invalid username and password");
            login.loginWithInvalidCredentials();
            Assert.assertTrue(login.checkIfCorrectValidationErrorMsgDisplayed(), "Unauthorised user was able to Login without any error.");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA -1506 : Verify the Next button functionality.")
    @Xray(test = {1506})
    public void verifyTheNextButtonFunctionalityPampa() {
        try {
            test.info("Verify Next button is present and clickable on Login page.");
            test.info("Checking if Next button is present on Login page.");
            LoginPage login = new LoginPage();

            Assert.assertTrue(login.checkIfNextButtonIsPresent(), "Next Button was not present.");

            test.info("Checking if Next button is clickable.");
            Assert.assertTrue(login.checkIfNextButtonIsClickable(), "User was not able to click on the Next button.");

            test.info("Providing valid username at Login page and then click Next button.");
            login.EnterUserName();

            test.info("Click on Next button...");
            test.info("Checking Next button click redirects user to Okta Login page");
            Assert.assertTrue(login.passwordBox.isDisplayed(), "Next button is not working as expected.");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA -1428 : Verify all the fields on Login page has Placeholder.")
    public void verifyThePlaceholderForLoginPageFieldsPampa() {
        try {
            test.info("Verify the Place folder for different fields on Login page.");
            test.info("Checking the Username placeholder for Username field");
            LoginPage login = new LoginPage();

            Assert.assertEquals(login.usernameBox.getAttribute("name"), "username", "Username field has username placeholder.");

            test.info("Checking the Remember me placeholder for Remember me checkbox field");
            Assert.assertEquals(login.rememberMeCheckboxLabel.getText(), "Remember me", "Remember Me checkbox has Remember me placeholder.");

            test.info("Providing valid username at Login page and then click Next button.");
            login.EnterUserName();

            test.info("Click on Next button...");
            test.info("Checking Next button click redirects user to Okta Login page");

            test.info("Checking the Password placeholder for password textbox field");
            Assert.assertEquals(login.passwordBox.getAttribute("name"), "password", "Password field has password placeholder.");

            test.info("Checking the Remember me placeholder for Remember me checkbox field");
            Assert.assertEquals(login.rememberMeCheckboxLabel.getText(), "Remember me", "Remember Me checkbox has Remember me placeholder.");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA -1483 : User is not Able to Login with a Blank Username and Password")
    @Xray(test = 1483)
    public void verifyUserIsNotableToLoginWithBlankUserNamePampa() {
        try {
            test = report.createTest("Verify Next button is present and clickable on Login page.");
            test.info("Checking if Next button is present on Login page.");
            LoginPage login = new LoginPage();

            Assert.assertTrue(login.checkIfNextButtonIsPresent(), "Next Button was not present.");

            test.info("Checkign if Next button is clickable.");
            Assert.assertTrue(login.checkIfNextButtonIsClickable(), "User was not able to click on the Next button.");

            test.info("Providing valid username at Login page and then click Next button.");
            login.EnterUserNameAsBlank();

            test.info("Click on Next button...");
            Assert.assertTrue(login.checkIfCorrectValidationErrorMsgForBlankUserNameDisplayed(), "No Error message was displayed for the blank UserName.");

            login.EnterUserName();
            login.EnterPasswordAsBlank();
            Assert.assertTrue(login.checkIfCorrectValidationErrorMsgForBlankPasswordDisplayed(), "No Error message was displayed for the blank Password.");


            //  test.info("Checking Next button click redirects user to Okta Login page");
            //  Assert.assertTrue(login.passwordBox.isDisplayed(), "Next button is not working as expected.");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "ESGCA -1648 : User is able to access the MESGC application without providing credentials if already logged in other moodys application.")
    @Xray(test = 1648)
    public void verifyUserCanUseLoginCredentialsFromOtherMoodysToMESGCPampa() {
        try {
            test.info("Verify Next button is present and clickable on Login page.");
            test.info("Checking if Next button is present on Login page.");
            LoginPage login = new LoginPage();

            Assert.assertTrue(login.checkIfNextButtonIsPresent(), "Next Button was not present.");

            test.info("Checking if Next button is clickable.");
            Assert.assertTrue(login.checkIfNextButtonIsClickable(), "User was not able to click on the Next button.");

            test.info("Providing valid username at Login page and then click Next button.");
            login.loginWithParams(Environment.DATA_USERNAME, Environment.DATA_PASSWORD);

            assertTestCase.assertTrue(login.checkIfUserLoggedInOKTASuccessfully(),"User is on Okta Page");

            login.navigateToESGPlatform();
            BrowserUtils.wait(5);

            Assert.assertTrue(login.checkIfUserLoggedInSuccessfully(), "User was able to access the MESGC application with already provided credentials.");


        } catch (Exception e) {
            throw e;
        }
    }

}