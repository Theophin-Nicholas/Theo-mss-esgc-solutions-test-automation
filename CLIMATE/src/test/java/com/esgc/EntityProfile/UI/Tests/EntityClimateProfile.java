package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.API.APIModels.EntityHeader;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class EntityClimateProfile extends UITestBase {


    @Xray(test = {5875, 5879, 11725,12413})
    @Test(enabled = true, groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testCompanyHeader(String company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        Assert.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName));
        //todo: remove if condition once confidence level pushed to prod or uat
        if(entityProfilePage.companyHeaderItems.size() > 4)
        Assert.assertEquals(entityProfilePage.confidenceLevel.getText(),"Confidence Level: Analyst Driven","Validating Confidence Level: Analyst Driven");
        entityProfilePage.validateCompanyHeader(companyName);
    }

    @Xray(test = 6019)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testGreenShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(Company);
        Assert.assertTrue(entityProfilePage.checkIfGreenShareCardISAvailable());
        Assert.assertEquals(entityProfilePage.listOfGreenShareCardlabels.get(0).getText(), "Green Share");
        System.out.println("entityProfilePage.listOfGreenShareCardlabels.get(1).getText() = " + entityProfilePage.listOfGreenShareCardlabels.get(1).getText());
        Assert.assertTrue(entityProfilePage.listOfGreenShareCardlabels.get(1).getText()
                .matches("Company has \\d+%of their investments in companies offering green solutions."));
        Assert.assertTrue(entityProfilePage.validateGreenCardValueBoxIsAvailable());
        //Assert.assertTrue(entityProfilePage.validateifPieChartIsAvailable("greenshare"));
        int greenShareValue = Integer.parseInt(entityProfilePage.GreenShareWidgetValue.getText().split("[^\\d]")[0]);
        //entityProfilePage.validateColorOfValueBoxAndPieChart("Green Share", greenShareValue);
    }

    @Xray(test = 6019)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testBrownShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(Company);
        //Assert.assertTrue(entityProfilePage.checkIfBrownShareCardISAvailable());
        Assert.assertTrue(entityProfilePage.listOfBrownShareCardlabels.get(0).getText().equals("Brown Share"));
        Assert.assertTrue(entityProfilePage.listOfBrownShareCardlabels.get(1).getText().matches("Company has \\d+%of revenue from the fossil fuel industry"));
        Assert.assertTrue(entityProfilePage.validateBrownCardValueBoxIsAvailable());
        //Assert.assertTrue(entityProfilePage.validateifPieChartIsAvailable("brownshare"));
        int brownShareValue = Integer.valueOf(entityProfilePage.BrownShareWidgetValue.getText().split("[^\\d]")[0]);
        //entityProfilePage.validateColorOfValueBoxAndPieChart("Brown Share", brownShareValue);
    }

    @Xray(test = 6066)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected")
    public void testTemperatureAlignmentWidget() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc.");
        Assert.assertTrue(entityProfilePage.checkIfTempratureAlignmentWidgetISAvailable());
        entityProfilePage.validateTemperatureAligmentHeaderText();
        entityProfilePage.validateQualitativeScoreHeader();
        entityProfilePage.validateTemperatureAlignmentImpliedTemperatureRise();
        entityProfilePage.validateTemperatureAlignmentEmissionsReductionTargetYear();
        entityProfilePage.validateTemperatureAlignmentTargetDescription();
        entityProfilePage.validateTemperatureAlignmentUpdatedOn();
    }

    @Xray(test = {6111, 6113})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Entity Climate Profile Page Carbon Footprint Sector Comparison Chart  for Transition Risk",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testSectorComparisonChartForTransitionRisk(String CompanyName) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        entityProfilePage.navigateToTransitionRisk();
        assertTestCase.assertTrue(entityProfilePage.checkIfCarbonFootprintWidgetISAvailable(),
                "Carbon footprint widget is displayed");
        assertTestCase.assertTrue(entityProfilePage.isCompanyComparedWithItsSector(CompanyName),
                "Company name is compared with the sector");
        assertTestCase.assertTrue(entityProfilePage.verifyCompanyDisplayedNextToSectorName(CompanyName),
                "company name is displayed next to sector name");

    }

    @Xray(test = {6189, 6190})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Entity Climate Profile Page-Sector Comparison Chart for Physical risk",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testSectorComparisonChartForPhysicalRisk(String CompanyName) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        entityProfilePage.navigateToPhysicalRisk();
        assertTestCase.assertTrue(entityProfilePage.checkIfPhysicalClimateRiskChartAndtextsAREVisible(),
                "Physical Climate Risk: Operations Risk chart is displayed");
        assertTestCase.assertTrue(entityProfilePage.isCompanyComparedWithItsSector(CompanyName),
                "Company name is compared with the sector");
        assertTestCase.assertTrue(entityProfilePage.verifyCompanyDisplayedNextToSectorName(CompanyName),
                "company name is displayed next to sector name");

    }
//TODO needs to be handled
    @Xray(test = {8981, 8983})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Entity Climate Profile Page-Physical risk management",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testPhysicalRiskManagement(String CompanyName) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        entityProfilePage.navigateToPhysicalRisk();

        assertTestCase.assertTrue(entityProfilePage.validatePhysicalRiskMananagementTableIsAvailable(),
                "Validate Physical Risk Management table is displayed");
        System.out.println("Assertion Successful");
        entityProfilePage.validatePhysicalRiskManagementTable();

    }
    //todo: Check if functionality is available
    @Xray(test = {11207, 11209})
    @Test(enabled = false, groups = {REGRESSION,SMOKE, UI, ENTITY_PROFILE},
            description = "Entity Climate Profile Page-Physical risk management",
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIdWithCompanyName")
    public void verifyL3SectorInEntityHeader(String companyName,String orbisId) {
        //Get the api response for L3 field mesg_sector
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(orbisId);
        getExistingUsersAccessTokenFromUI();
        BrowserUtils.wait(2);
        EntityProfileClimatePageAPIController apiController = new EntityProfileClimatePageAPIController();
        List<EntityHeader> list = Arrays.asList(apiController.geCompanyHeaderAPIResponse(orbisId).as(EntityHeader[].class));
        String l3ApiValue = list.get(0).getMesg_sector();
        l3ApiValue="Sector: "+l3ApiValue;
        System.out.println("l3ApiValue = " + l3ApiValue);
        // Get the header Sector details
        //String sectorHeaderUI = entityProfilePage.sectorInHeader.getText();
        //System.out.println("sectorHeaderUI = " + sectorHeaderUI);

        // Get the Company drawer Sector detail.
        WebElement companyDrawerButton= Driver.getDriver().findElement(By.xpath("//span[contains(text(),'"+companyName+"')]"));
        companyDrawerButton.click();
        BrowserUtils.wait(1);
        //String sectorDrawerUI = entityProfilePage.sectorInHeader.getText();
        //System.out.println("sectorDrawerUI = " + sectorDrawerUI);

        //Compare all 3 fields against each other
        //assertTestCase.assertEquals(sectorHeaderUI,l3ApiValue);
        //assertTestCase.assertEquals(sectorDrawerUI,l3ApiValue);
        //assertTestCase.assertEquals(sectorDrawerUI,sectorHeaderUI);

    }

    @Xray(test = {10044})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Verify Entity page header of the Company Name' About Drawer",
            dataProviderClass = DataProviderClass.class,  dataProvider = "CompanyNames")
    public void validateCompanyNameAndAboutDrawer (String CompanyName) {

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        //Validate Hover on Company name :
        entityProfilePage.validateCompanyNameHoverFunctionality(CompanyName);
        //Validate About Icon next to name :
        assertTestCase.assertTrue(entityProfilePage.validateInfoIcon(CompanyName),"Validate About Icon next to company Header");
        entityProfilePage.openAboutDrawer(CompanyName);
        //Validate if Drawer has opened upon click
        assertTestCase.assertTrue(entityProfilePage.isAboutDrawerAvailable(),
                "Validate About Drawer is displayed");
        // Validate Header text in Drawer
        assertTestCase.assertTrue(entityProfilePage.validateAboutHeader(CompanyName),"Validate Header Text");
        // Validate validate Drawer Details
        entityProfilePage.validateAboutDrawerDetails(CompanyName);
        // Click on escape to close the drawer
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);

    }

    @Xray(test = {10275})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            dataProviderClass = DataProviderClass.class,  dataProvider = "Company With Orbis ID")
    public void validatePhysicalClimateHazardDate (String... Company) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(Company[0]);
        entityProfilePage.validatePhysicalClimateHazardUpdatedDate(Company[1]);
    }

    @Xray(test = {10282})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            dataProviderClass = DataProviderClass.class,  dataProvider = "Company With Orbis ID")
    public void validatePhysicalRiskManagementUpdatedDate (String... Company) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(Company[0]);
        entityProfilePage.validatePhysicalRiskManagementUpdatedDate(Company[1]);
    }


}
