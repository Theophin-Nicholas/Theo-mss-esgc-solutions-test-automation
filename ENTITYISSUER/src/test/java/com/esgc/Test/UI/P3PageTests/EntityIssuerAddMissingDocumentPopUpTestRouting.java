package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerAddMissingDocumentPopUpTestRouting extends EntityPageTestBase {


    @Xray(test = {7350,8795,9821})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentials", dataProviderClass = DataProviderClass.class,
            description = "Verify Add missing document functionlity")
    public void testMissingDocumentPopUp(String... dataProvider) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(dataProvider[0], dataProvider[1]);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(1);
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        entitypage.addURL("Google.com",true);
        entitypage.validateAssignedCategories();
        entitypage.validateWrongURL();
        entitypage.validatePageNoBox();
        entitypage.saveMissingDocuments();
        entitypage.CloseAddMissingDocumentMessage.click();
        entitypage.logout.click();
    }
}
