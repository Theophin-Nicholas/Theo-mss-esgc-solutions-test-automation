package com.esgc.Test.UI;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

import static com.esgc.Utilities.Groups.*;

public class EntityIssuerPageRoutingTest extends EntityPageTestBase {

    @Xray(test = {7634, 7331})
    @Test(groups = {REGRESSION, UI, ISSUER},
            dataProvider = "RoutingCheck", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify if Landing page is routing correctly")
    public void validateRouting(String UserID, String Password, String coverage_status, String expectedPage ) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        System.out.println("**** Login Page Setting up ****");
        LoginPageIssuer loginPage = new LoginPageIssuer();
        loginPage.loginWithParams(UserID,Password);
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        SoftAssert softAssert = new SoftAssert();
        if (expectedPage.equals("P2")){
            BrowserUtils.wait(5);
            System.out.println("P2 Banner "+entitypage.banner.get(0).getText());
           softAssert.assertTrue(wait.until(ExpectedConditions.visibilityOf(entitypage.banner.get(0))).getText().contains("Welcome to Moody’s ESG360: the portal connecting you to the Moody’s ESG Assessment process."));
        }
        else{
            BrowserUtils.wait(5);
            System.out.println("HEADER : "+entitypage.P3PageHeader.getText());
            softAssert.assertTrue(wait.until(ExpectedConditions.visibilityOf(entitypage.P3PageHeader)).getText().contains("Region:"));
        }
           entitypage.logout.click();
        softAssert.assertAll();
    }


}