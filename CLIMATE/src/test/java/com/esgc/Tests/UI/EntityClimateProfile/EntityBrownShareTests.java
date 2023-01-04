package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.EntityClimateProfilePageQueries;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;


public class EntityBrownShareTests extends UITestBase {

    @Test(groups = {"regression", "ui"},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithBrownShareScore")
    @Xray(test = {7890})
    public void verifyTooltipOverBrownShareSectorComparisionChart(String orbisID) {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(orbisID);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        profilePage.verifyBrownShareWidget();

        WebElement lastBar = profilePage.comparisonChartBars.get(profilePage.comparisonChartBars.size() - 1);
        BrowserUtils.scrollTo(lastBar);
        BrowserUtils.hover(lastBar);
        WebElement tooltip = Driver.getDriver().findElement(By.xpath("//*[local-name()='g' and @visibility='visible']"));
        System.out.println("tooltip.getText() = " + tooltip.getText());
        String companyName = tooltip.getText().substring(0,tooltip.getText().indexOf("Brown Share Score: "));
        assertTestCase.assertTrue(companyName.length()>0, companyName+" - Verify Company Name in tooltip");
        assertTestCase.assertTrue(tooltip.getText().contains("Brown Share Score: "), "Verify Brown Share Score in tooltip");
        assertTestCase.assertTrue(tooltip.getText().contains("Brown Share Assessment: "), "Verify Brown Share Assessment in tooltip");
        assertTestCase.assertTrue(tooltip.getText().contains("%"), "Verify Brown Share Score % in tooltip");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {7955})
    public void verifyNoDataMessageInBrownShareSection() {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        String orbisID = entityClimateProfilepagequeries.getEntityWithNoBrownShareInfo().get(0).get("ORBIS_ID").toString();

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(orbisID);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        BrowserUtils.scrollTo(profilePage.transitionRiskBrownShareWidget);
        assertTestCase.assertTrue(profilePage.trBrownShareAssessmentNoInfo.isDisplayed(), "No information available.");
        assertTestCase.assertTrue(profilePage.trBrownShareAssessmentSectorChartNoInfo.isDisplayed(), "No information available.");

    }

    @Test(groups = {"regression", "ui"},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithBrownShareScore")
    @Xray(test = {7889, 7917})
    public void verifyBrownShareSectionInformation(String orbisID) {
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.TRANSITION_RISK);

        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        String sectorName = entityClimateProfilepagequeries.getEntitySectorInfo(orbisID).get(0).get("MESG_SECTOR").toString();
        int sectorCompaniesCount = entityClimateProfilepagequeries.getSectorCompanies(sectorName).size();
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(orbisID);
        entityProfilePage.verifyBrownShareWidget();
        entityProfilePage.verifyBrownShareComparisonChartLegends(sectorName,companyName);
        entityProfilePage.verifyBrownShareComparisonChartAxes();
        entityProfilePage.verifyBrownShareComparisonChartSectorDesc(sectorName,sectorCompaniesCount,companyName);
    }

}