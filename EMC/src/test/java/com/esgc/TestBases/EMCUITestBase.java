package com.esgc.TestBases;

import com.esgc.Pages.LoginPageEMC;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

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

    @AfterMethod(onlyForGroups = {UI}, groups = {SMOKE, UI, REGRESSION})
    public void refreshEMCPage(ITestResult result) {
        getScreenshot(result);
        Driver.getDriver().navigate().refresh();
    }

    @AfterClass(alwaysRun = true)
    public void closeDriverAfterEMCTests() {
        Driver.closeDriver();
    }

    public void wait(WebElement element, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                if (element.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        System.out.println("Element is not displayed after " + seconds + " seconds");
    }
    public void wait(List<WebElement> elements, int seconds) {
        for (int i = 0; i < seconds; i++) {
            try {
                if (elements.size() > 0) {
                    System.out.println("elements = " + elements.size());
                    return;
                }
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        System.out.println("Element is not displayed after " + seconds + " seconds");
    }

}
