package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class EntityBrownShareTests extends UITestBase {

    @Test(groups = {REGRESSION, UI},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithBrownShareScore")
    @Xray(test = {4981})
    public void verifyTooltipOverBrownShareSectorComparisonChart(String orbisID) {
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
        assertTestCase.assertTrue(tooltip.getText().contains("Involvement"), "Verify Involvement in tooltip");
        assertTestCase.assertTrue(tooltip.getText().contains("%"), "Verify Brown Share Score % in tooltip");

    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {4829})
    public void verifyNoDataMessageInBrownShareSection() {
        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        String orbisID = entityClimateProfilepagequeries.getEntityWithNoBrownShareInfo().get(0).get("ORBIS_ID").toString();

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToFirstEntity(orbisID);
        EntityClimateProfilePage profilePage = new EntityClimateProfilePage();
        assertTestCase.assertTrue(profilePage.trBrownShareAssessmentNoInfo.isDisplayed(), "No information available.");
        profilePage.navigateToTransitionRisk();
        assertTestCase.assertTrue(profilePage.trBrownShareAssessmentSectorChartNoInfo.isDisplayed(), "No information available.");

    }

    @Test(groups = {REGRESSION, UI},
            dataProviderClass = DataProviderClass.class, dataProvider = "orbisIDWithBrownShareScore")
    @Xray(test = {4847, 4632, 5051, 4669})
    //TODO check queries
    public void verifyBrownShareSectionInformation(String orbisID) {
        EntityClimateProfilePageQueries entityClimateProfilepagequeries = new EntityClimateProfilePageQueries();
        List<Map<String, Object>> sectorRecords = entityClimateProfilepagequeries.getEntitySectorInfo(orbisID);
        String sectorName = sectorRecords.get(0).get("MESG_SECTOR").toString();
        int sectorCompaniesCount = entityClimateProfilepagequeries.getSectorCompanies(sectorName).size();
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(orbisID);
        entityProfilePage.verifyBrownShareWidget();
        entityProfilePage.verifyBrownShareWidgetOverallRevenue(orbisID);
        entityProfilePage.verifyBrownShareCategory();
        entityProfilePage.verifyBrownShareComparisonChartLegends(sectorName,companyName);
        entityProfilePage.verifyBrownShareComparisonChartAxes();
        entityProfilePage.verifyBrownShareComparisonChartAverageLine();
        entityProfilePage.verifyBrownShareComparisonChartSectorDesc(sectorName,sectorCompaniesCount,companyName);
    }

}