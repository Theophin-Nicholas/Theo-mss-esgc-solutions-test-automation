package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityIssuerPageTextTestsRouting extends EntityPageTestBase {


    @Xray(test = {6270})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Assessment Framework")
    public void verifyAssessmentFramework(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(10);
        String AssementData = "Our " + entitypage.header.getText().split("for ")[1].toString() + " industry framework is composed of categories and sub-categories. " +
                "Each sub-category has been assigned a weight from low to very high depending on their level of materiality. " +
                "Sub-categories identified as Very High will contribute most significantly to the overall score. " +
                "Sub-categories identified as moderate have a lower level of materiality but still, " +
                "contribute to the overall score of a company. More information on Moody’s ESG materiality " +
                "methodology can be found in the methodology guide.";
        assertTestCase.assertTrue(AssementData.equals(entitypage.paraAssessmentFramewok.getText()), "Validate Assessment Data paragragh");
        entitypage.logout.click();

    }



    @Xray(test = {7902})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Scoring Methodology")
    public void verifyScoringMethodology(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(10);
        String AssementData = "Our " + entitypage.header.getText().split("for ")[1].toString() + " industry framework is composed of categories and sub-categories. " +
                "Each sub-category has been assigned a weight from low to very high depending on their level of materiality. " +
                "Sub-categories identified as Very High will contribute most significantly to the overall score. " +
                "Sub-categories identified as moderate have a lower level of materiality but still, " +
                "contribute to the overall score of a company. More information on Moody’s ESG materiality " +
                "methodology can be found in the methodology guide.";
        assertTestCase.assertTrue(AssementData.equals(entitypage.paraAssessmentFramewok.getText()), "Validate Assessment Data paragragh");

    }

    @Xray(test = {6356})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2_back", dataProviderClass = DataProviderClass.class,
            description = "Verify Banner")
    public void verifybanner(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(10);
        entitypage.validateIssuerPageBanner();
        entitypage.logout.click();
    }

    @Xray(test = {7298, 7902, 9070})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2_back", dataProviderClass = DataProviderClass.class,
            description = "Verify Grade and Scores")
    public void verifyGradesAndScores(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(10);
        entitypage.validateScoringMethodologyStaticText();
        entitypage.logout.click();
    }


}
