package com.esgc.Test.TestBases;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.annotations.BeforeClass;

import java.time.Duration;


public abstract class EntityIssuerPageTestBase extends TestBase {

    @BeforeClass(alwaysRun = true)
    public void setupForIssuerToken() {
        /*getEntityPageAccessTokenLoginWithParameter()*/;
    }


    public void getEntityPageAccessTokenLoginWithParameter(String userId, String password) {
        System.out.println("getting token");

        String URL = Environment.ENTITY_URL;
        BrowserUtils.wait(1);
        Driver.getDriver("chromeheadless").get(URL);
        Driver.getDriver().manage().deleteAllCookies();
        System.out.println("userId = " + userId);
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        LoginPageIssuer loginPage = new LoginPageIssuer();
        loginPage.loginWithParams(userId, password);
        System.out.println("login successful....");
        BrowserUtils.wait(5);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.logout.click();
    }


}

