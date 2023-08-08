package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class RegionsSectors extends UITestBase {
    //Subtasks: 4730-drilldown
    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Region and Sector Tables are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4226, 2118, 4624, 4502, 4335, 3887, 4412, 3923, 4038, 4575, 4777})
    public void verifyRegionSectorChartsUI(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");
        if (page.equals("ESG Assessments")) {
            throw new SkipException("not ready to test in " + page);
        }
        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");
        assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed("Regions"), "Region & Sector Title Verified", 4469, 4874);
        assertTestCase.assertTrue(researchLinePage.verifyRegionSectorsTables(page), "Region & Sector UI Verified for " + page, 4515, 3839, 1964);

        test.pass("Regions & Sectors Table rows columns and content verified");
    }

    //Test cases 4973,4865,4656,4942,4610,5069,4983,1704,1705,1748,1920,4619,4910,4914.3473
    //main 4973
    @Test(groups = {REGRESSION, UI},
            description = "Verify if Region and Sector Drill Downs are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4973, 4865, 4656, 4942, 4610, 5069, 4983, 4619, 4910, 4914, 3473, 4722, 4403, 4352, 4870, 4429, 4861, 2074})
    public void verifyRegionSectorDrillDowns(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        if (page.equals("ESG Assessments")) {
            throw new SkipException("not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");
        BrowserUtils.wait(5);
        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(5);
        System.out.println("Random Portfolio Selected");
        assertTestCase.assertTrue(researchLinePage.verifyRegionSectorDrillDowns(page), "Region & Sector drill downs verified", 4860, 4645, 4973, 4969, 5050, 4865, 4656, 4942, 4610, 5069, 4983, 4619, 4910, 4914, 3473);
        test.pass("Regions & Sectors Table rows columns and content verified");
    }

    //Test case 4951
    @Test(groups = {REGRESSION, UI},
            description = "Verify if Region and Sector Drill Downs are Closed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4951, 4721})
    public void verifyRegionSectorDrillDownsClosing(String page) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfDrillDownPanelsAreClosed(), "Regions & Sectors Drill Downs are closed", 4951);
        test.pass("Regions & Sectors Drill Down Panels closed by clicking out of panel");
    }


    //Test cases: 4860,4645, 4279,1746,1918,4919
    //Main: 4860, 4645
    @Test(groups = {REGRESSION, UI},
            description = "Verify if Region and Sector Drill Downs are Clickable and Highlighted with Hover",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4634, 4919, 3474})
    public void verifyRegionSectorChartsHighlightedWithHover(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        researchLinePage.verifyRegionComesFirstThenSectors();

        assertTestCase.assertTrue(researchLinePage.checkIfRegionSectorChartClickableAndHighlightedWithHover(),
                "Regions & Sectors charts highlighted with hover", 4860, 4645, 4279, 4634, 4919);
        test.pass("Regions & Sectors Charts highlighted with hover action");
    }


    //Descoped in story https://esjira/browse/ESGCA-9398
    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4768, 4979, 4767, 4762, 3599})
    public void verify_MoM_QoQ_TextIsAvailable(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.Is_MoM_QoQ_TextAvailable(page);

    }

    @Test(groups = {REGRESSION, UI},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4643, 5083, 3504})
    public void verify_RegionCardsOrder(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Navigated to " + page + " Page");
        researchLinePage.validateRegionCardOrder();
        System.out.println("Region Card Order validated");
    }

    @Test(groups = {REGRESSION, UI},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4506, 4280})
    public void verify_SectorCardsOrder(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Navigated to " + page + " Page");
        researchLinePage.validateSectorCardOrder();
        System.out.println("Region Card Order validated");
    }
}
