package com.esgc.TestBases;

import com.esgc.Pages.LoginPageEMC;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;

public abstract class EMCUITestBase extends TestBase {

    @BeforeClass(alwaysRun = true)
    @Parameters("browser")
    public synchronized void setupUIForEMCTests(@Optional String browser) {
        //getAccessToken();
        String URL = Environment.EMC_URL;
        BrowserUtils.wait(1);

        if (browser != null) {
            Driver.getDriver(browser).get(URL);
        } else {
            Driver.getDriver().get(URL);
        }

        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPageEMC loginPage = new LoginPageEMC();
        System.out.println(System.getProperty("env"));
        if (ConfigurationReader.getProperty("environment").equals("prod")) {
            loginPage.loginEMCInternal();
        } else {
            loginPage.loginWithInternalUser();
        }
        isUITest = true;
        Driver.getDriver().manage().window().maximize();

    }

    @AfterMethod(onlyForGroups = {"ui"}, groups = {"smoke", "ui", "regression"})
    public void refreshEMCPage(ITestResult result) {
        getScreenshot(result);
        Driver.getDriver().navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void closeDriverAfterEMCTests() {
        Driver.closeDriver();
    }

}
