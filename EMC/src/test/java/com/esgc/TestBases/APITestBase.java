package com.esgc.TestBases;


import com.esgc.Pages.LoginPageEMC;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;

import java.time.Duration;


public abstract class APITestBase extends TestBase {

    public static String portfolioID = null;
    public static String portfolioName = null;
    public CustomAssertion assertTestCase = new CustomAssertion();

    @BeforeClass(alwaysRun = true)
    public synchronized void setupToGetAPIToken() {
        getAccessToken();
    }

    public void getAccessToken() {
        System.out.println("getting token");
        Driver.getDriver().get(Environment.EMC_URL);
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPageEMC loginPage = new LoginPageEMC();
        loginPage.loginWithInternalUser();
        for (int i = 0; i < 60; i++) {
            try{
                String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
                String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
                System.setProperty("token", accessToken);
                System.out.println("token = " + accessToken);
                break;
            } catch (Exception e) {
                BrowserUtils.wait(1);
            }
        }
        if(System.getProperty("token") == null) {
            throw new RuntimeException("Token is null");
        }
    }
}

