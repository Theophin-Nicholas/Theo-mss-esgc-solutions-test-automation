package com.esgc.Tests.TestBases;

import com.esgc.Pages.LoginPage;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ConfigurationReader;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.apache.commons.lang.time.StopWatch;
import org.openqa.selenium.JavascriptExecutor;

import javax.swing.*;
import java.time.Duration;

public class TestBaseClimate extends TestBase {
    String accessToken;

    public void getNoExportBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver("chromeheadless").get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        loginPage.userLoginWithNoExportBundle();
        //loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
//        loginPage.userLoginWithNoControversiesBundle();
//        loginPage.entitlementsLogin(EntitlementsBundles.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT);
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
    }

    public void getNoEsgBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver("chromeheadless").get(URL);
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

    public void getNoControversiesBundleAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.closeDriver();
        Driver.getDriver("chromeheadless").get(URL);
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

    public void getAccessTokenDataValidation() {
        System.out.println("getting token");
        String URL = Environment.URL;
        BrowserUtils.wait(1);
        Driver.getDriver("chrome").get(URL);
        //Driver.getDriver().get(URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPage loginPage = new LoginPage();
        loginPage.dataValidationLogin();
        BrowserUtils.wait(20);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
        try {
            stopWatch.start();
        } catch (Exception e) {

        }
    }

    public void refreshToken() {
        if (stopWatch.getTime() > 3600000) {
            LoginPage loginPage = new LoginPage();
            if(loginPage.isSearchIconDisplayed()){
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
        Driver.getDriver(ConfigurationReader.getProperty("browser")).get(URL);
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


}
