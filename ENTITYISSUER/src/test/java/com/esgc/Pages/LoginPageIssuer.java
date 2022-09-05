package com.esgc.Pages;

import com.esgc.Test.TestBases.EntityPageDataValidationTestBase;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPageIssuer extends PageBase{
    @FindBy(id = "input-termsandconditions")
    public WebElement termsAndConditionsCheckBox;

    @FindBy(id = "idp-discovery-username")
    public WebElement usernameBox;

    @FindBy(id = "okta-signin-password")
    public WebElement passwordBox;

    @FindBy(xpath = "//label[text()='I have read and accepted the ']")
    public WebElement termsAndConditionsLabel;

    @FindBy(id = "okta-signin-submit")
    public WebElement loginButton;

    public void entityIssuerLogin() {
        System.out.println("entityIssuerLogin started");
        if ( EntityPageTestBase.isP3Test || EntityPageDataValidationTestBase.isP3Test) {
            System.out.println("Environment.ISSUER_USERNAMEP3 = " + Environment.ISSUER_USERNAMEP3);
            wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.ISSUER_USERNAMEP3, Keys.ENTER);
        } else {
            System.out.println("Environment.ISSUER_USERNAME = " + Environment.ISSUER_USERNAME);
            wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.ISSUER_USERNAME, Keys.ENTER);
        }

        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.ISSUER_PASSWORD);
        if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    public void loginWithParams(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
        if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();

    }
}
