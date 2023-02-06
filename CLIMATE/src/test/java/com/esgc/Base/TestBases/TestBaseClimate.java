package com.esgc.Base.TestBases;

import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

public class TestBaseClimate extends TestBase {
    String accessToken;

    @BeforeTest(alwaysRun = true)
    public void createDBConnectionForAllMethods(){
        DatabaseDriver.createDBConnection();
    }

    public synchronized void getNoExportBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        //loginPage.userLoginWithNoExportBundle();
        //loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
        loginPage.userLoginWithNoControversiesBundle();
        //loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

    public synchronized void getNoEsgBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        loginPage.userLoginWithNoEsgBundle();
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

    public synchronized void getNoControversiesBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        loginPage.userLoginWithNoControversiesBundle();
        //loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
//        loginPage.userLoginWithNoControversiesBundle();
//        loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

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

    public synchronized void getAccessTokenDataValidations() {
        LoginPage loginPage = new LoginPage();
        System.out.println("getting token");
        if (!loginPage.isSearchIconDisplayed()) {
            String URL = Environment.URL;
            BrowserUtils.wait(1);
            //Driver.getDriver("chromeheadless").get(URL);
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

    public void getAccessToken() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        loginPage.loginEMCInternal();
        BrowserUtils.wait(10);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

    public synchronized void setAccessTokenFromUI() {
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        //System.out.println("token = " + accessToken);
    }

}
