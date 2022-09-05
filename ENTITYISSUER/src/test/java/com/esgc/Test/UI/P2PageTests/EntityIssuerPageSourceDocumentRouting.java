package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerPageSourceDocumentRouting extends EntityPageTestBase {


    @Xray(test = {6988})
    @Test(groups = {"regression", "ui", "smoke","entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Source Documents")
    public void testMissingDocumentPopUp(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.validateSourceDocumentWidgetIsAvailable();
        entitypage.validateSourceDocumentlinkOpenInNewTab(".pdf");
        entitypage.logout.click();
    }



}
