package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityIssuerAddMissingDocumentPopUpTestRouting extends EntityPageTestBase {


    @Xray(test = {6430, 6471, 6468,8795, 9821})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Add missing document functionlity")
    public void testMissingDocumentPopUp(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        BrowserUtils.wait(1);
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        entitypage.addURL("Google.com", false);
        entitypage.validateAssignedCategories();
        entitypage.validateWrongURL();
        entitypage.validatePageNoBox();
        entitypage.saveMissingDocuments();
        entitypage.CloseAddMissingDocumentMessage.click();
        entitypage.logout.click();
    }

    @Xray(test = {6470})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify cancel missing Document")
    public void verifyCancelOnMissingDocumentPopUp(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        entitypage.addURL("Google.com", false);
        BrowserUtils.wait(2);
        entitypage.validatePopupClose();
        entitypage.logout.click();
    }

    @Xray(test = {6426})
    @Test(groups = {"regression", "ui", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify click out of missing Document popup")
    public void verifyClickOutOfMissingDocumentPopUp(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        entitypage.addURL("Google.com",false);
        BrowserUtils.wait(2);
        entitypage.pressESCKey();
        entitypage.buttonContinueWithoutSaving.click();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {6431})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify delete url functionality")
    public void verifyDeleteURLFunctionality(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        String url = "Google.com";
        entitypage.addURL(url,false);
        BrowserUtils.wait(2);
        entitypage.validateURLDelete(url);
        entitypage.pressESCKey();
        BrowserUtils.wait(1);
        entitypage.pressESCKey();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {6466})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify duplicate url functionlity")
    public void verifyDuplicateURL(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        String url = "Google.com";
        entitypage.addURL(url,false);
        BrowserUtils.wait(2);
        entitypage.PopUpWindowURL.sendKeys(url);
        assertTestCase.assertTrue(entitypage.errorMessage.getText().equals("URL already added"), "Validate duplicate url error message");
        entitypage.pressESCKey();
        entitypage.buttonContinueWithoutSaving.click();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {6469})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify save without adding categories")
    public void verifySaveWithoutAssignedCategories(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.ClickOnaddMissingDocuments();
        entitypage.validateopupWindowOpenStatus();
        String url = "Google.com";
        entitypage.addURL(url,false);
        BrowserUtils.wait(2);
        entitypage.validateSaveWithoutAssignedCategories();
        BrowserUtils.wait(1);
        entitypage.pressESCKey();
        entitypage.buttonContinueWithoutSaving.click();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

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
        BrowserUtils.wait(2);
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
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {6270})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Weight is Numeric")
    public void verifyWeightIsNumeric(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        List<String> weightList = new ArrayList(Arrays.asList("Low", "High", "Moderate", "Very High"));
        for (WebElement e : entitypage.weightColumn) {
            assertTestCase.assertTrue(weightList.contains(e.getText()), "Validate Weight is in " + weightList);
        }
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {6356})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Banner")
    public void verifybanner(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.validateIssuerPageBanner();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {7298, 7902})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Grade and Scores")
    public void verifyGradesAndScores(String UserID, String Password) {
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(UserID, Password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.validateScoringMethodologyStaticText();
        BrowserUtils.wait(2);
        entitypage.logout.click();
    }

    @Xray(test = {8087, 8088,10250})
    @Test(groups = {"regression", "ui", "smoke", "entity_issuer"},
            dataProvider = "credentialsP2", dataProviderClass = DataProviderClass.class,
            description = "Verify Methodology Links")
    public void verifyMethodologyLinks(String UserID, String Password) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            LoginPageIssuer.loginWithParams(UserID, Password);

            entitypage.validateLinksOpenedInNewTab(entitypage.linkSeeMethodologyGuide, "Methodology%202.0%20ESG%20Assessment");
            entitypage.validateLinksOpenedInNewTab(entitypage.linkSeeSeeControversyMethodology, "Controversy%20Risk%20Assessment%20-%20Methodology");
            entitypage.validateLinksOpenedInNewTab(entitypage.linkSeeSubcategoryDefinitions, "ESG%20Assessment_Subcategory%20Definitions_FINAL");
            entitypage.validateLinksOpenedInNewTab(entitypage.linkSeeSeeESGMetricDefinitions, "ESGAssessmentMetrics_DefinitionsHandbook");
            entitypage.validateLinksOpenedInNewTab(entitypage.linkSeeFAQ, "FAQ_Moodys%20ESG%20Assessment");
            BrowserUtils.wait(2);
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

}
