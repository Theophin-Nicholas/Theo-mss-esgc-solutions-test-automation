package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class EntityClimateProfile extends UITestBase {


    @Xray(test = {3347, 3352, 4274})
    @Test(enabled = true, groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testCompanyHeader(String company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        entityProfilePage.waitForDataLoadCompletion();
        Assert.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName));
        //todo: remove if condition once confidence level pushed to prod or uat
//        if(entityProfilePage.companyHeaderItems.size() > 4)
//        Assert.assertEquals(entityProfilePage.confidenceLevel.getText(),"Confidence Level: Analyst Driven","Validating Confidence Level: Analyst Driven");
        entityProfilePage.validateCompanyHeader(companyName);
    }

    @Xray(test = 3366)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testGreenShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
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

    @Xray(test = 3366)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testBrownShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
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

    @Xray(test = 4366)
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},
            description = "Verify if Company headers are Displayed as Expected")
    public void testTemperatureAlignmentWidget() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
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

    @Xray(test = {4885, 4365})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Entity Climate Profile Page Carbon Footprint Sector Comparison Chart  for Transition Risk",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testSectorComparisonChartForTransitionRisk(String CompanyName) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
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

    @Xray(test = {4952, 4816})
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
    @Xray(test = {4483, 4795})
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

    @Xray(test = {3970})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            description = "Verify Entity page header of the Company Name' About Drawer",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void validateCompanyNameAndAboutDrawer(String CompanyName) {

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        //Validate Hover on Company name :
        entityProfilePage.validateCompanyNameHoverFunctionality(CompanyName);
        //Validate About Icon next to name :
        assertTestCase.assertTrue(entityProfilePage.validateInfoIcon(CompanyName), "Validate About Icon next to company Header");
        entityProfilePage.openAboutDrawer(CompanyName);
        //Validate if Drawer has opened upon click
        assertTestCase.assertTrue(entityProfilePage.isAboutDrawerAvailable(),
                "Validate About Drawer is displayed");
        // Validate Header text in Drawer
        assertTestCase.assertTrue(entityProfilePage.validateAboutHeader(CompanyName), "Validate Header Text");
        // Validate validate Drawer Details
        entityProfilePage.validateAboutDrawerDetails(CompanyName);
        // Click on escape to close the drawer
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);

    }

    @Xray(test = {4841})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Company With Orbis ID")
    public void validatePhysicalClimateHazardDate(String... Company) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(Company[0]);
        entityProfilePage.validatePhysicalClimateHazardUpdatedDate(Company[1]);
    }

    @Xray(test = {4858})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Company With Orbis ID")
    public void validatePhysicalRiskManagementUpdatedDate(String... Company) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(Company[0]);
        entityProfilePage.validatePhysicalRiskManagementUpdatedDate(Company[1]);
    }


}
