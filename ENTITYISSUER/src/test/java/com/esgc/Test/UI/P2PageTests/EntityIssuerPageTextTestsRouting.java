package com.esgc.Test.UI.P2PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityIssuerPageTextTestsRouting extends EntityPageTestBase {


    @Xray(test = {3959})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Assessment Framework")
    public void verifyAssessmentFramework(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(data[0].toString(),data[1].toString());

            BrowserUtils.wait(10);
            String AssementData = "Our " + entitypage.header.getText().split("for ")[1].toString() + " industry framework is composed of categories and sub-categories. " +
                    "Each sub-category has been assigned a weight from low to very high depending on their level of materiality. " +
                    "Sub-categories identified as Very High will contribute most significantly to the overall score. " +
                    "Sub-categories identified as moderate have a lower level of materiality but still, " +
                    "contribute to the overall score of a company. More information on Moody’s ESG materiality " +
                    "methodology can be found in the methodology guide.";
            assertTestCase.assertTrue(AssementData.equals(entitypage.paraAssessmentFramewok.getText()), "Validate Assessment Data paragragh");
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }


    @Xray(test = {3368})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Scoring Methodology")
    public void verifyScoringMethodology(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(data[0].toString(),data[1].toString());

            BrowserUtils.wait(10);
            String AssementData = "Our " + entitypage.header.getText().split("for ")[1].toString() + " industry framework is composed of categories and sub-categories. " +
                    "Each sub-category has been assigned a weight from low to very high depending on their level of materiality. " +
                    "Sub-categories identified as Very High will contribute most significantly to the overall score. " +
                    "Sub-categories identified as moderate have a lower level of materiality but still, " +
                    "contribute to the overall score of a company. More information on Moody’s ESG materiality " +
                    "methodology can be found in the methodology guide.";
            assertTestCase.assertTrue(AssementData.equals(entitypage.paraAssessmentFramewok.getText()), "Validate Assessment Data paragragh");
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {6356})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Banner")
    public void verifybanner(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(data[0].toString(),data[1].toString());
            BrowserUtils.wait(10);
            entitypage.validateIssuerPageBanner();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }

    @Xray(test = {3361, 3368, 4087})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Grade and Scores")
    public void verifyGradesAndScores(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(data[0].toString(),data[1].toString());
            BrowserUtils.wait(10);
            entitypage.validateScoringMethodologyStaticText();
            entitypage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entitypage.logout.click();
        }
    }


}
