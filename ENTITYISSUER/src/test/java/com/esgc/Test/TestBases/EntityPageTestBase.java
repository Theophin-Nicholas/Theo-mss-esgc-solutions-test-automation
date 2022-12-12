package com.esgc.Test.TestBases;

import com.esgc.Pages.LoginPageIssuer;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DataFinder;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Optional;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
           Map<String,String> params = new HashMap<>();
           params.put("Page","P2");
           params.put("Entitlement","Login");

           Object[][] data = getLoginData("P3",params) ;
           data[0][0].toString();
           LoginPageIssuer loginPage = new LoginPageIssuer();
           loginPage.loginWithParams(data[0][0].toString(), data[0][1].toString());
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

    public Object[][] getLoginData(String Page, Map<String,String> params ){
        // Object[][] data = null;
        IssuerDataProviderClass dataprovider = new IssuerDataProviderClass();
        List<String> requiredCols= Arrays.asList(new String[]{"UserName","Password"});
        return dataprovider.getData(params,requiredCols);
    }

}

