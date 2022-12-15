package com.esgc.Base.UI.Tests.PampaLoginPageTest;

import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Utilities.*;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TermsAndConditions extends DashboardUITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {5431, 5437, 5439})
    //Covers ESGCA-5439 and ESGCA-5431 also
    public void externalUserPampaTC() {
        try {
            test.info("Open Login Page");
            LoginPage login = new LoginPage();
            BrowserUtils.waitForVisibility(login.usernameBox, 5).sendKeys(Environment.UI_USERNAME, Keys.ENTER);

            test.info("Click on terms of use link");
            login.clickTermsOfUseLink();

            test.info("Check if back to sign in link is present");
            login.checkBackToSignIn();

            test.info("Verify terms and conditions is not selected. Accept terms ");
            Assert.assertFalse(login.termsAndConditionsCheckBox.isSelected());
            if (!login.termsAndConditionsCheckBox.isSelected())
                BrowserUtils.waitForVisibility(login.termsAndConditionsLabel, 5).click();

            test.info("Login as external user");
            login.passwordBox.sendKeys(Environment.UI_PASSWORD, Keys.ENTER);

            test.info("Logout and verify T&C is checked");
            login.clickOnLogout();

            BrowserUtils.waitForVisibility(login.usernameBox, 5).sendKeys(Environment.UI_USERNAME, Keys.ENTER);
            Assert.assertTrue(login.termsAndConditionsCheckBox.isSelected());
        } catch (Exception e) {
            throw e;
        }
    }


    @Test(groups = {"regression", "ui"})
    @Xray(test = 5432)
    public void internalUserPampaTC() {
        test.info("Open Login Page");
        LoginPage login = new LoginPage();

        test.info("Login as internal user");
        BrowserUtils.waitForVisibility(login.usernameBox, 5).sendKeys(ConfigurationReader.getProperty("internal_user"));
        try {
            Assert.assertFalse(!login.termsAndConditionsCheckBox.isDisplayed());
            test.info("Terms and Conditions not present");
        } catch (Exception e) {
            test.info("Terms and Conditions not present");
        }
    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = 5445)
    public void externalUserClearCookies() {
        test.info("Open Login Page");
        LoginPage login = new LoginPage();

        test.info("Login as external user");
        login.clickOnLogout();

        test.info("Delete all cookies and close window");
        Driver.getDriver().manage().deleteAllCookies();
        Driver.closeDriver();
    }

    @Test(dependsOnMethods = "externalUserClearCookies")
    public void reloginPampa() {
        LoginPage login = new LoginPage();
        BrowserUtils.waitForVisibility(login.usernameBox, 5).sendKeys(ConfigurationReader.getProperty("username"), Keys.ENTER);

        test.info("Verify Terms and conditions checkbox is not present");
        Assert.assertFalse(login.termsAndConditionsCheckBox.isSelected());
    }
}
