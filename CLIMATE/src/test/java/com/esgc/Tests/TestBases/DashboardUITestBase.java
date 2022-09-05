package com.esgc.Tests.TestBases;

import com.esgc.Pages.LoginPage;
import com.esgc.Utilities.*;
import com.esgc.TestBase.TestBase;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.time.Duration;

public abstract class DashboardUITestBase extends TestBase {

    @BeforeMethod(onlyForGroups = {"ui", "dashboard"}, groups = {"smoke", "regression", "ui", "dashboard"})
    @Parameters("browser")
    public synchronized void setupDashboardUI(@Optional String browser, Method method) {
        isUITest = true;
        String URL = Environment.URL;
        String OktaURL = ConfigurationReader.getProperty("Oktaurl");
        BrowserUtils.wait(1);
        boolean isCredenRemembered = method.getName().contains("MESGCPampa");

        if (browser != null) {
            Driver.getDriver(browser).get(URL);
        } else if (isCredenRemembered) {
            Driver.getDriver().get(OktaURL);
        } else {
            Driver.getDriver().get(URL);
        }

        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        LoginPage loginPage = new LoginPage();

        boolean isPampaTest = method.getName().contains("Pampa");
        boolean isEntitlementsTest = method.getName().contains("Bundle");
        if (!isPampaTest && !isEntitlementsTest) {
            loginPage.login();
        }
    }


    @AfterMethod(onlyForGroups = {"ui"}, groups = {"smoke", "regression", "ui"})
    public synchronized void teardownDashboard(ITestResult iTestResult) {
        getScreenshot(iTestResult);
        Driver.closeDriver();
    }

    @DataProvider(name = "Research Lines")
    public Object[][] availableResearchLinesForDashboard() {

        return new Object[][]{
                {"Operations Risk"},
                {"Market Risk"},
                {"Supply Chain Risk"},
                {"Temperature Alignment"},
                {"Physical Risk Management"},
                {"Carbon Footprint"},
                {"Brown Share Assessment"},
                {"Green Share Assessment"},
        };
    }
}
