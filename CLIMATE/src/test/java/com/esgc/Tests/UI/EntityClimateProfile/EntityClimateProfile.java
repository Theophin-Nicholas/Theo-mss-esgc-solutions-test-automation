package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

public class EntityClimateProfile extends UITestBase {



    @Xray(test = {5875, 5879})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class,     dataProvider = "CompanyNames")
    public void testCompanyHeader(String company) {
      ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        Assert.assertTrue(entityProfilePage.validateGlobalHeader(companyName));
        entityProfilePage.validateCompanyHeader(companyName);
 }

    @Xray(test = 6019)
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class,    dataProvider = "CompanyNames")
    public void testGreenShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(Company);
        Assert.assertTrue(entityProfilePage.checkIfGreenShareCardISAvailable());
        Assert.assertTrue(entityProfilePage.listOfGreenShareCardlabels.get(0).getText().equals("Green Share"));
        System.out.println("entityProfilePage.listOfGreenShareCardlabels.get(1).getText() = " + entityProfilePage.listOfGreenShareCardlabels.get(1).getText());
        Assert.assertTrue(entityProfilePage.listOfGreenShareCardlabels.get(1).getText().equals("% of Commercial activities linked to green solutions"));
        Assert.assertTrue(entityProfilePage.validateGreenCardValueBoxIsAvailable());
        Assert.assertTrue(entityProfilePage.validateifPieChartIsAvailable("greenshare"));
        int greenShareValue = Integer.valueOf(entityProfilePage.GreenShareWidgetValue.getText().split("[^\\d]")[0]);
        entityProfilePage.validatColorOfValueBoxAndPieChart("Green Share", greenShareValue);
    }

    @Xray(test = 6019)
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},
            description = "Verify if Company headers are Displayed as Expected",
            dataProviderClass = DataProviderClass.class,dataProvider = "CompanyNames")
    public void testBrownShareCard(@Optional String Company) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(5);
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(Company);
        Assert.assertTrue(entityProfilePage.checkIfBrownShareCardISAvailable());
        Assert.assertTrue(entityProfilePage.listOfBrownShareCardlabels.get(0).getText().equals("Brown Share"));
        Assert.assertTrue(entityProfilePage.listOfBrownShareCardlabels.get(1).getText().equals("Overall Fossil Fuels Industry Revenues"));
        Assert.assertTrue(entityProfilePage.validateBrownCardValueBoxIsAvailable());
        Assert.assertTrue(entityProfilePage.validateifPieChartIsAvailable("brownshare"));
        int brownShareValue = Integer.valueOf(entityProfilePage.BrownShareWidgetValue.getText().split("[^\\d]")[0]);
        entityProfilePage.validatColorOfValueBoxAndPieChart("Brown Share", brownShareValue);
    }

    @Xray(test = 6066)
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},
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
        entityProfilePage.validateTempratureAlignmentImpliedTemperatureRise();
        entityProfilePage.validateTempratureAlignmentEmissionsReductionTargetYear();
        entityProfilePage.validateTempratureAlignmentTargetDescription();
        entityProfilePage.validateTempratureAlignmentUpdatedOn();
    }



    @Xray(test = {6111,6113})
    @Test(groups = {"regression", "ui", "entity_climate_profile"},
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
                "Company name is compared with the sector"  );
        assertTestCase.assertTrue(entityProfilePage.verifyCompanyDisplayedNextToSectorName(CompanyName),
                "company name is displayed next to sector name");

    }

    @Xray(test = {6189,6190})
    @Test(groups = {"regression", "ui", "entity_climate_profile"},
            description = "Entity Climate Profile Page-Sector Comparison Chart for Physical risk",
            dataProviderClass = DataProviderClass.class, dataProvider = "CompanyNames")
    public void testSectorComparisonChartForPhysicalRisk(String CompanyName) {
       // ResearchLinePage researchLinePage = new ResearchLinePage();
      //  researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        entityProfilePage.navigateToPhysicalRisk();
        assertTestCase.assertTrue(entityProfilePage.checkIfPhysicalClimateRiskChartAndtextsAREVisible(),
                "Physical Climate Risk: Operations Risk chart is displayed");
        assertTestCase.assertTrue(entityProfilePage.isCompanyComparedWithItsSector(CompanyName),
                "Company name is compared with the sector"  );
        assertTestCase.assertTrue(entityProfilePage.verifyCompanyDisplayedNextToSectorName(CompanyName),
                "company name is displayed next to sector name");

    }
    @Xray(test = {8981,8983})
    @Test(groups = {"regression", "ui", "entity_climate_profile"},
            description = "Entity Climate Profile Page-Physical risk management",
            dataProviderClass = DataProviderClass.class,  dataProvider = "CompanyNames")
    public void testPhysicalRiskManagement (String CompanyName) {
      //  ResearchLinePage researchLinePage = new ResearchLinePage();
       // researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(CompanyName);
        entityProfilePage.navigateToPhysicalRisk();

        assertTestCase.assertTrue(entityProfilePage.validatePhysicalRiskMananagementTableIsAvailable(),
                "Validate Physical Risk Management table is displayed");
        System.out.println("Assertion Successful");
        entityProfilePage.validatePhysicalRiskManagementTable();

    }

}
