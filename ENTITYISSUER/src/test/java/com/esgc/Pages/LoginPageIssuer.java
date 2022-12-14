package com.esgc.Pages;

import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static String OrbisID = "";

    public void entityIssuerLogin() {
        IssuerDataProviderClass credentials = new IssuerDataProviderClass();
        Map<String,String> params = new HashMap<>();
        System.out.println("entityIssuerLogin started");
        if ( EntityPageTestBase.isP3Test || EntityIssuerPageDataValidationTestBase.isP3Test) {
            params.put("Page","P3");
        } else {
            params.put("Page","P2");
        }
        params.put("Entitlement","Login");
        Object[][] credntial = credentials.getData(params,Arrays.asList(new String[]{"UserName","Password","OrbisID"}));
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(credntial[0][0].toString(), Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(credntial[0][1].toString());
        OrbisID = credntial[0][2].toString() ;
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    public void loginWithParams(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
        String env = ConfigurationReader.getProperty("environment");
        /*if (env.equals("prod")) {
            if (!termsAndConditionsCheckBox.isSelected())
                wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();
        }*/
      /*  if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }


}
