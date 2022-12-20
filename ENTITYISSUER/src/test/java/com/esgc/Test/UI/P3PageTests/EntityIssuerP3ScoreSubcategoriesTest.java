package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerP3ScoreSubcategoriesTest extends EntityPageTestBase {

    //TODO should be tested both in p2 and p3 accounts
    @Xray(test = {7431, 7445, 7447, 7450, 7451, 7452, 7453, 7454, 7455, 7456, 7458, 7459, 7460, 7465, 7982, 7983, 8337})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Source Documents")
    public void validateESGSubcategories(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer loginPage = new LoginPageIssuer();
            BrowserUtils.wait(1);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                loginPage.loginWithParams(userId, password);

            entitypage.validateESGSubcategoriesavailability();
            //Todo this section needs to be updated based on new design
            // entitypage.validateESGSubcategorieToggleButtons();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }

    }
}
