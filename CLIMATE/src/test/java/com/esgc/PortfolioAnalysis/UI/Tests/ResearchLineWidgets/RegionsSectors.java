package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
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
    @Xray(test = {404, 2712, 2563, 5961, 6933, 6765, 6766, 6767, 6750, 6749, 6697})
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
        assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed("Regions"), "Region & Sector Title Verified", 590, 591);
        assertTestCase.assertTrue(researchLinePage.verifyRegionSectorsTables(page), "Region & Sector UI Verified for " + page, 414, 1158, 1697, 1916, 1744, 2478);

        test.pass("Regions & Sectors Table rows columns and content verified");
    }

    //Test cases 467,822,823,1208,1265,1279,1281,1704,1705,1748,1920,2145,2149,2212.2488
    //main 467
    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Region and Sector Drill Downs are Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {467, 822, 823, 1208, 1265, 1279, 1281, 1704, 1705, 1748, 1920, 2145, 2149, 2212, 2488, 6763, 6762, 6761, 6760, 6759, 6757, 2665})
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
        assertTestCase.assertTrue(researchLinePage.verifyRegionSectorDrillDowns(page), "Region & Sector drill downs verified", 411, 420, 467, 469, 470, 822, 823, 1208, 1265, 1279, 1281, 1704, 1705, 1748, 1920, 2145, 2149, 2212, 2488);
        test.pass("Regions & Sectors Table rows columns and content verified");
    }

    //Test case 468
    @Test(groups = {REGRESSION, UI},
            description = "Verify if Region and Sector Drill Downs are Closed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {468, 6764})
    public void verifyRegionSectorDrillDownsClosing(String page) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");

        researchLinePage.selectRandomPortfolioFromPortfolioSelectionModal();

        assertTestCase.assertTrue(researchLinePage.checkIfDrillDownPanelsAreClosed(), "Regions & Sectors Drill Downs are closed", 468);
        test.pass("Regions & Sectors Drill Down Panels closed by clicking out of panel");
    }


    //Test cases: 411,420, 1199,1746,1918,2209
    //Main: 411, 420
    @Test(groups = {REGRESSION, UI},
            description = "Verify if Region and Sector Drill Downs are Clickable and Highlighted with Hover",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {1278, 2209})
    public void verifyRegionSectorChartsHighlightedWithHover(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        assertTestCase.assertTrue(researchLinePage.checkIfRegionSectorChartClickableAndHighlightedWithHover(),
                "Regions & Sectors charts highlighted with hover", 411, 420, 1199, 1278, 1746, 2209);
        test.pass("Regions & Sectors Charts highlighted with hover action");
    }


    //Descoped in story https://esjira/browse/ESGCA-9398
    @Test(enabled = false, groups = {REGRESSION, UI, SMOKE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4224, 4225, 4227, 4226, 4231})
    public void verify_MoM_QoQ_TextIsAvailable(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Navigated to " + page + " Page");
        researchLinePage.Is_MoM_QoQ_TextAvailable(page);

    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = 412)
    public void verify_RegionCardsOrder(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        test.info("Navigated to " + page + " Page");
        researchLinePage.validateRegionCardOrder();
        System.out.println("Region Card Order validated");
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = 423)
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
