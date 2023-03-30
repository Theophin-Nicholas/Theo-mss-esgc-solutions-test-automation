package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityIssuerAddMissingDocumentPopUpTestRouting extends EntityPageTestBase {


    @Xray(test = {7350, 8795, 9821,10809})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "loginP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Add missing document functionlity")
    public void testMissingDocumentPopUp(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(dataProvider[0], dataProvider[1]);

            BrowserUtils.wait(1);
            entitypage.ClickOnaddMissingDocuments();
            entitypage.validateopupWindowOpenStatus();
            entitypage.addURL("Google.com", true);
            entitypage.validateAssignedCategories();
            entitypage.validateWrongURL();
            entitypage.validatePageNoBox();
            entitypage.saveMissingDocuments();
            entitypage.CloseAddMissingDocumentMessage.click();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {11522})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "loginP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Add missing document functionlity with wrong URL")
    public void testMissingDocumentPopUpWrongURL(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(dataProvider[0], dataProvider[1]);

            BrowserUtils.wait(1);
            entitypage.ClickOnaddMissingDocuments();
            entitypage.validateopupWindowOpenStatus();
            entitypage.addURL("Googl.com", true);
            entitypage.validateURL();
            System.out.println("Wrong URL validated");
            BrowserUtils.ActionKeyPress(Keys.ESCAPE);
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }


    }
}
