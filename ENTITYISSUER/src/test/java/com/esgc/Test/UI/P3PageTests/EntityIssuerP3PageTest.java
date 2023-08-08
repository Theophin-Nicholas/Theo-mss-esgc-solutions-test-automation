package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.text.ParseException;

import static com.esgc.Utilities.Groups.*;


public class EntityIssuerP3PageTest extends EntityPageTestBase {

    @Xray(test = {7279})
    @Test(groups = {REGRESSION, UI, SMOKE},//ISSUER
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify ContactUS Button")
    public void TestContactUsButton(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);
            // EntityIssuerPage entitypage = new EntityIssuerPage();
            entitypage.validateP3ContactUSButton();
            entitypage.IsP3ContactUSButton_Clickable();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {7110, 7349,})
    @Test(groups = {REGRESSION, UI, SMOKE},//, ISSUER
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify P3 Page header ")
    public void TestHeader(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);
            //  EntityIssuerPage entitypage = new EntityIssuerPage();
            entitypage.validateHeaderAvailability();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }


    @Xray(test = {3333, 3358, 3357, 3339})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Source Documents")
    public void testsourceDocumentWidget(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);
            // EntityIssuerPage entitypage = new EntityIssuerPage();
            entitypage.validatP3eSourceDocumentWidgetIsAvailable();
            entitypage.validateCopyFunctionlity();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {3330, 3343, 3341, 3345, 3335, 3331, 4905})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Controversies")
    public void testControversies(String... dataProvider) throws ParseException {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))

            LoginPageIssuer.loginWithParams(userId, password);
            //  EntityIssuerPage entitypage = new EntityIssuerPage();
            entitypage.validateIfContoversiesHeadingAvailable();
            entitypage.validateIfContoversiesTableIsAvailable();
            entitypage.validateContoversiesTable();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {3367, 4139})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify save without adding categories")
    public void verifyScoringMethodology(String... dataProvider) {// you can define String... dataProvider or String[] dataProvider
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);
            //   EntityIssuerPage entitypage = new EntityIssuerPage();
            entitypage.validateScoringMethodology();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }
    @Xray(test = {4001})
    @Test(priority = 5, groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Add Missing Document Functionality ")
    public void TestLogOut(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
        LoginPageIssuer.loginWithParams(userId, password);
      //  EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.validateLogoutButton();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {3899}) //TODO this feature is not available in UI. Test Case failing.
    @Test(enabled = false ,groups = {REGRESSION, UI, ISSUER},
            dataProvider = "CompaniesWithMESGScore", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify save without adding categories")
    public void ValidateOverallDisclosureRatio(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);

            entitypage.ValidateOverallDisclosureRatioIsVailableAndValueisNumeric();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {10250})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            description = "Verify links")
    public void verifyMethodologyLinks() {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer loginPage = new LoginPageIssuer();
            loginPage.entityIssuerLogin();
            entitypage.navigateToSectorPage();

            entitypage.validateP3LinksOpenedInNewTab(entitypage.linkSeeMethodologyGuide, "Methodology%202.0%20ESG%20Assessment");
            entitypage.validateP3LinksOpenedInNewTab(entitypage.linkSeeSeeControversyMethodology, "Controversy%20Risk%20Assessment%20-%20Methodology");
            entitypage.validateP3LinksOpenedInNewTab(entitypage.linkSeeSubcategoryDefinitions, "ESG%20Assessment_Subcategory%20Definitions_FINAL");
            entitypage.validateP3LinksOpenedInNewTab(entitypage.linkSeeSeeESGMetricDefinitions, "ESGAssessmentMetrics_DefinitionsHandbook");
            entitypage.validateP3LinksOpenedInNewTab(entitypage.linkSeeFAQ, "FAQ_Moodys%20ESG%20Assessment");
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

}
