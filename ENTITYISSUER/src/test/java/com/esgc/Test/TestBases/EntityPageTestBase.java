package com.esgc.Test.TestBases;

import com.esgc.Pages.LoginPageIssuer;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;

import java.time.Duration;


public abstract class EntityPageTestBase extends TestBase {
    public static boolean isP3Test = false;

    @BeforeClass(alwaysRun = true)
    public synchronized void setupForIssuerUITests(@Optional String browser) {
        String URL = Environment.ENTITY_URL;
        BrowserUtils.wait(1);
        if (browser != null) {
            System.out.println("Browser : " + browser);
            Driver.getDriver(browser).get(URL);
        } else {
            System.out.println("Browser is null");
            Driver.getDriver().get(URL);
        }
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        boolean isPRoutingTest = this.getClass().getName().contains("Routing");
        isP3Test = this.getClass().getName().contains("P3");
       if (! isPRoutingTest && ! isP3Test) {

            LoginPageIssuer loginPage = new LoginPageIssuer();
            loginPage.entityIssuerLogin();
        }
        isUITest = true;
    }
    public void pressESCKey() {
        new Actions(Driver.getDriver()).sendKeys(Keys.ESCAPE).pause(2000).build().perform();
    }

    public static void getEntityPageAccessToken() {
        System.out.println("getting token");
        String URL = Environment.ENTITY_URL;
        BrowserUtils.wait(1);
        Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPageIssuer loginPage = new LoginPageIssuer();
        loginPage.entityIssuerLogin();
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

}

