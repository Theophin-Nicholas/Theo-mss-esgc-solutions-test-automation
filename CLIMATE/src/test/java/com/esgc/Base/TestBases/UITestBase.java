package com.esgc.Base.TestBases;


import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

import static com.esgc.Utilities.Groups.*;

public abstract class UITestBase extends TestBase {
    String accessToken;

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public synchronized void setupUIForTests(@Optional String browser) {
        System.out.println("Before Class Called");
        String URL = Environment.URL;
        String OktaURL = ConfigurationReader.getProperty("Oktaurl");

        if (browser != null) {
            Driver.getDriver(browser).get(URL);
        } else {
            Driver.getDriver().get(URL);
        }

        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));


        isUITest = true;

        DatabaseDriver.createDBConnection();

    }

    @BeforeMethod(onlyForGroups = {ENTITLEMENTS}, groups = {SMOKE, REGRESSION, ENTITLEMENTS}, alwaysRun = true)
    public synchronized void setupEntitlementsForUITesting(@Optional String browser) {
        System.out.println("Before method called");
        String URL = Environment.URL;
        System.out.println("browser = " + browser);
        if (browser != null) {
            Driver.getDriver(browser).get(URL);
            System.out.println("Inside if");
        } else {
            Driver.getDriver().get(URL);
            System.out.println("inside else");
        }

        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        isUITest = true;

        if (!Driver.getDriver().getCurrentUrl().endsWith("login")) {
            LoginPage loginPage = new LoginPage();
            loginPage.clickOnLogout();
            BrowserUtils.waitForVisibility(loginPage.usernameBox);
        }
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void loginForTestsIfUserLoggedOut(Method method) {
        boolean isPampaTest = this.getClass().getName().contains("Pampa");
        boolean isEntitlementsTest = this.getClass().getName().toLowerCase().contains("bundle") || this.getClass().getName().toLowerCase().contains("entitlements") || method.getName().toLowerCase().contains("entitlements");
        boolean isUserOnLoginPage = Driver.getDriver().getCurrentUrl().endsWith("login") || Driver.getDriver().getCurrentUrl().endsWith("signin");

        LoginPage loginPage = new LoginPage();
        if (isUserOnLoginPage && (!isPampaTest && !isEntitlementsTest)) {
            loginPage.login();
        }
    }
/*
    @BeforeMethod(onlyForGroups = {API}, groups = {SMOKE, REGRESSION, ENTITLEMENTS}, alwaysRun = true)
    public synchronized void setupForAPITesting(@Optional String browser) {
        BrowserUtils.wait(10);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
    }
    */

    @AfterMethod(onlyForGroups = {UI}, groups = {SMOKE, REGRESSION, UI})
    public void refreshPageToContinueUITesting(ITestResult result) {
        getScreenshot(result);
        Driver.getDriver().navigate().refresh();
    }

    @AfterMethod(onlyForGroups = {ENTITLEMENTS}, groups = {SMOKE, REGRESSION, ENTITLEMENTS})
    public synchronized void teardownBrowserAfterUITesting() {
        boolean isUserOnLoginPage = Driver.getDriver().getCurrentUrl().endsWith("login") || Driver.getDriver().getCurrentUrl().endsWith("signin");

        if (!isUserOnLoginPage) {
            LoginPage loginPage = new LoginPage();
            loginPage.clickOnLogout();
            BrowserUtils.waitForVisibility(loginPage.usernameBox);
        }
    }

//    @BeforeClass(alwaysRun = true)
//    public void deleteImportedPortfoliosAfterTest() {
//        APIUtilities.deleteImportedPortfoliosAfterTest();
//    }


}
