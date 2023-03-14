package com.esgc.API.TestBase;

import com.esgc.TestBase.TestBase;
import com.esgc.UI.Pages.LoginPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class TestBaseOnDemand extends TestBase {
    String accessToken;

    @BeforeTest(alwaysRun = true)
    public void createDBConnectionForAllMethods(){
        DatabaseDriver.createDBConnection();
    }

    @BeforeClass(alwaysRun = true)
    public synchronized void getAccessTokenDataValidation() {

        LoginPage loginPage = new LoginPage();
        System.out.println("getting token");
        if (!loginPage.isSearchIconDisplayed()) {
            String URL = Environment.URL;
            BrowserUtils.wait(1);
            //Driver.getDriver("chrome").get(URL);
            Driver.getDriver().get(URL);
            Driver.getDriver().manage().window().maximize();
            Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            loginPage.dataValidationLogin();
            BrowserUtils.wait(20);
        }
        setAccessTokenFromUI();
        try {
            stopWatch.start();
        } catch (Exception e) {

        }
    }

    public synchronized void refreshToken() {
        if (stopWatch.getTime() > 3600000) {
            LoginPage loginPage = new LoginPage();
            if (loginPage.isSearchIconDisplayed()) {
                loginPage.clickOnLogout();
                BrowserUtils.wait(5);
            }
            loginPage.dataValidationLogin();
            BrowserUtils.wait(20);
            String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
            accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
            System.setProperty("token", accessToken);
            System.out.println("token = " + accessToken);
            stopWatch.stop();
            stopWatch.reset();
            stopWatch.start();
        }
    }

    public synchronized void setAccessTokenFromUI() {
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        //System.out.println("token = " + accessToken);
    }

}
