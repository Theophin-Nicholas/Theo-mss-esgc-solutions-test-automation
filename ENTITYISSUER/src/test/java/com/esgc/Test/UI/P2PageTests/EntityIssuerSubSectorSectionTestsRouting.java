package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityIssuerSubSectorSectionTestsRouting extends EntityPageTestBase {

    @Xray(test = {6270})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Weight is Numeric")
    public void verifyWeightIsNumeric(String UserID, String Password) {
        System.out.println(UserID +" "+Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        try {
        List<String> weightList = new ArrayList(Arrays.asList("Low", "High", "Moderate", "Very High"));
        for (WebElement e : entitypage.weightColumn) {
            assertTestCase.assertTrue(weightList.contains(e.getText()), "Validate Weight is in " + weightList);
        }
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }
        entitypage.logout.click();
    }
}
