package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerPageSourceDocumentRouting extends EntityPageTestBase {


    @Xray(test = {6988})
    @Test(groups = {"regression", "ui", "smoke","entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Source Documents")
    public void testMissingDocumentPopUp(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(data[0].toString(), data[1].toString());

            entitypage.validateSourceDocumentWidgetIsAvailable();
            entitypage.validateSourceDocumentlinkOpenInNewTab(".pdf");
            entitypage.logout.click();
        }catch(Exception e){
            e.printStackTrace();
            entitypage.logout.click();
        }
    }



}
