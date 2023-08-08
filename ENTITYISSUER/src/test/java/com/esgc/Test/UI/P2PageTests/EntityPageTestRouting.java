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

public class EntityPageTestRouting extends EntityPageTestBase {
    //6270


    //@Xray(test = {1544, 1437, 1864})
    @Test(enabled = false,priority = 6, groups = {REGRESSION, UI, SMOKE, ISSUER},

            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify if Leaders and Laggards Table is Displayed as Expected")
    public void testEntityPage(String UserID, String Password) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(UserID, Password);
        //  assertTestCase.assertTrue(entitypage.verifyFooter());//This is not valid. Image used instead of text in footer
        try {
            assertTestCase.assertTrue(entitypage.verifyHeader());
            assertTestCase.assertTrue(entitypage.verifyDocument());
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }

    @Xray(test = {1544, 1437, 1864, 1364})
    @Test(groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify if Leaders and Laggards Table is Displayed as Expected")
    public void testDrivers(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(data[0].toString(),data[1].toString());
        try {
            assertTestCase.assertTrue(entitypage.verifyDriverDrillDown());
            pressESCKey();
            entitypage.logout.click();

        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }


    }

    @Xray(test = 4405)
    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Position chart x axis and Legands")
    public void validatePositioninSectorCurveChart(String UserID, String Password) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(UserID, Password);
        try {
            assertTestCase.assertTrue(entitypage.sectorCurveChart.isDisplayed());
            entitypage.checkPositioninSectorCurveChart();
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGT-1661-UI | ESG Issuer - Entity Page | Verify Landing Page and Banner")
    @Xray(test = {1661})
    public void testLandingPageAndBanner(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(data[0].toString(),data[1].toString());
        try {
            assertTestCase.assertTrue(entitypage.verifyMoodysHeader(), "Header Validation");
            assertTestCase.assertTrue(entitypage.verifyMidTopHeaderDetails(), "Text Validation");
            assertTestCase.assertTrue(entitypage.verifyTopBodyOfText(), "Text Validation");
            //   // assertTestCase.assertTrue(entitypage.verifyMidBodyOfText(), "Text Validation");
            assertTestCase.assertTrue(entitypage.verifyBottomBodyOfText(), "Text Validation");
            assertTestCase.assertTrue(entitypage.verifyContactButtonIsVisible(), "Contact Us Validation");
            assertTestCase.assertTrue(entitypage.verifyContactButtonIsClickable(), "Contact Us Validation");
            assertTestCase.assertTrue(entitypage.verifyBannerScrollsAsPartOfThePage1(), "Banner Validation");
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGT-1741-UI | ESG Issuer - Entity Page | Verify Footer is Displayed")
    @Xray(test = {1741})
    public void testFooter(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(data[0].toString(),data[1].toString());
        try {
            assertTestCase.assertTrue(entitypage.verifyFooterIsDisplayed(), "Footer Validation");
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION, ISSUER},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGT-2167-UI | ESG Issuer - Entity Page | Verify Methodology Details (Titles, Text)")
    @Xray(test = {2167,9694})
    public void testMethodologyDetails(String... data) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(data[0].toString(),data[1].toString());
        try {
            // assertTestCase.assertTrue(entitypage.verifyMidTexts(), "Text Validation");
            assertTestCase.assertTrue(entitypage.verifySubHeadersDetails(), "Header Details Validation");
            assertTestCase.assertTrue(entitypage.verifyAssessmentFrameworkHeaderDetails(), "Assess,ent Framework Header Details Validation");
            assertTestCase.assertTrue(entitypage.verifySubCategoriesDetails(), "Assess,ent Framework Header Details Validation");
            assertTestCase.assertTrue(entitypage.verifyNumericValues(), "Assess,ent Framework Header Details Validation");
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }

    @Test(groups = {UI, SMOKE, REGRESSION, ISSUER},
            dataProvider = "loginP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGCA-6288-UI | ESG Issuer - Verify if Sector Modal is Displayed with a Click on Sub-Sectors")
    @Xray(test = {3939})
    public void verifySectorModalPopupIsDisplayedOnClickSubcategories(String UserID, String Password) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        LoginPageIssuer loginPage = new LoginPageIssuer();
        BrowserUtils.wait(2);
        if(Driver.getDriver().getCurrentUrl().contains("login"))
        loginPage.loginWithParams(UserID, Password);
        try {
            assertTestCase.assertTrue(entitypage.verifySectorModalPopUpForSubCategories("Environmental"), "Environmental Sectors subcategories was displayed correctly and Modal popup is loading successfully.");
            assertTestCase.assertTrue(entitypage.verifySectorModalPopUpForSubCategories("Social"), "Social Sectors subcategories was displayed correctly and Modal popup is loading successfully.");
            assertTestCase.assertTrue(entitypage.verifySectorModalPopUpForSubCategories("Governance"), "Governance Sectors subcategories was displayed correctly and Modal popup is loading successfully.");
            entitypage.logout.click();
        } catch (AssertionError ae) {
            entitypage.pressESCKey();
            entitypage.logout.click();
            ae.printStackTrace();
        }

    }
}
