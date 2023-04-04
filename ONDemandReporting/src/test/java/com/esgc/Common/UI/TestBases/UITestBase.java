package com.esgc.Common.UI.TestBases;


import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import com.github.javafaker.Faker;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

import static com.esgc.Utilities.Groups.*;

public abstract class UITestBase extends TestBase implements ITestListener {
    String accessToken;
    public String portfolioName ;
    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public synchronized void setupUIForTests(@Optional String browser) {
        System.out.println("Before Class Called");
        String URL = Environment.URL;
        BrowserUtils.wait(1);

        if (browser != null) {
            Driver.getDriver(browser).get(URL);
        } else {
            Driver.getDriver().get(URL);
        }

        Driver.getDriver().manage().window().maximize();
       // Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(80));

        isUITest = true;

        DatabaseDriver.createDBConnection();

    }

    @BeforeMethod(onlyForGroups = {ENTITLEMENTS}, groups = {SMOKE, REGRESSION, ENTITLEMENTS}, alwaysRun = true)
    public synchronized void setupEntitlementsForUITesting(@Optional String browser) {
        System.out.println("Before method called");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
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
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void loginForTestsIfUserLoggedOut() {
        boolean isEntitlementsTest = this.getClass().getName().contains("Bundle") || this.getClass().getName().contains("Entitlements");
        LoginPage loginPage = new LoginPage();
        if (Driver.getDriver().getCurrentUrl().endsWith("login")) {
            if (!isEntitlementsTest) {
                loginPage.login();
                BrowserUtils.wait(20);
            }
            setAccessTokenFromUI();
        }

    }


    public synchronized void setAccessTokenFromUI() {
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
    }

    @AfterMethod(onlyForGroups = {UI}, groups = {SMOKE, REGRESSION, UI})
    public void refreshPageToContinueUITesting(ITestResult result) {
        getScreenshot(result);
        Driver.getDriver().navigate().refresh();
    }

  /*  @AfterMethod(onlyForGroups = {ENTITLEMENTS}, groups = {SMOKE, REGRESSION, ENTITLEMENTS})
    public synchronized void teardownBrowserAfterUITesting() {
        Driver.closeDriver();
    }*/

   /* @BeforeMethod(onlyForGroups = {COMMON} )
    public void commonStarttup() {
        portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        //onDemandAssessmentPage.selectPortfolioByNameFromPortfolioSelectionModal(portfolioName);
        onDemandAssessmentPage.clickMenu();
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickonOnRequestAssessmentButton();
        onDemandAssessmentPage.validateOnDemandPageHeader();
    }*/

}
