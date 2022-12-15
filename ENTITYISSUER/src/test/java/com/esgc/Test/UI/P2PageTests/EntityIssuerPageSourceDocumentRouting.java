package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerPageSourceDocumentRouting extends EntityPageTestBase {


    @Xray(test = {6988})
    @Test(groups = {"regression", "ui", "smoke","entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Source Documents")
    public void testMissingDocumentPopUp(String UserID, String Password) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            LoginPageIssuer.loginWithParams(UserID, Password);

            entitypage.validateSourceDocumentWidgetIsAvailable();
            entitypage.validateSourceDocumentlinkOpenInNewTab(".pdf");
            entitypage.logout.click();
        }catch(Exception e){
            e.printStackTrace();
            entitypage.logout.click();
        }
    }



}
