package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by ChaudhS2 on 10/5/2021.
 */
public class LeadersAndLaggards extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Leaders and Laggards Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class,dataProvider = "Research Lines")
    @Xray(test = {389, 8433,9868})
    public void verifyLeadersAndLaggardsTableUI(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        String title = "Updates as of June 2021 and Current Leaders/Laggards";
        String commonWidgetsID = "updates_and_current_leaders_laggards";
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(10);
        test.info("Navigated to " + page + " Page");

        Assert.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");
        if (!page.equals("ESG Assessments"))
            assertTestCase.assertTrue(researchLinePage.checkifWidgetTitleIsDisplayedWithID(title, commonWidgetsID));

        assertTestCase.assertTrue(researchLinePage.checkIfLeadersAndLaggardsTableDisplayed(commonWidgetsID), "Leaders & Laggards verified", 381, 550, 1256);

        test.pass(title + " Table/Chart displayed");

        assertTestCase.assertTrue(researchLinePage.verifyLeadersHasMax10Companies(), "Leaders has max 10 companies", 383);
        assertTestCase.assertTrue(researchLinePage.verifyLaggardsHasMax10Companies(), "Laggards has max 10 companies", 383);

//        Assert.assertTrue(researchLinePage.checkIfPortfolioCoverageChartTitlesAreDisplayed());
        Assert.assertTrue(researchLinePage.checkifLeadersAndLaggardsTableUIIsperDesign(page));
        test.pass(title + " Table rows columns and content verified");
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if More companies ranked in link present",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {442, 543, 537, 1275, 2133, 2444, 2880, 3096, 3846, 1263, 1264, 1275, 6654, 8435 })
    public void verifyMoreRankedCompaniesLink(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to Research Line page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");
        test.info("Selecting the As of Date value");
        researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        if (!page.equals("Carbon Footprint")) {
            Assert.assertTrue(researchLinePage.checkMoreCompaniesExistLink(page));
            researchLinePage.clickHideButtonInDrillDownPanel();
        }
        test.info("Verified that the More companies ranked in link is working as expected");
    }


    @Test(groups = {REGRESSION, UI},
            description = "Verify the ScoreLogic for Leaders And Laggards Section",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2124, 3034, 3159, 2080, 3848, 3849, 11077})
    public void verifyScoreLogicForLeaderAndLaggards(String page){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "August 2021");
        BrowserUtils.wait(5);

        assertTestCase.assertTrue(researchLinePage.VerifyIfScoreLogicIsCorrectForLeaders(page), "Entities were not ordered in correct order for Leaders");
        assertTestCase.assertTrue(researchLinePage.VerifyIfScoreLogicIsCorrectForLaggards(page), "Entities were not ordered in correct order for Laggards");

        assertTestCase.assertTrue(researchLinePage.VerifySortingOrderForLeaders(), "Sorting order for Leaders");
        assertTestCase.assertTrue(researchLinePage.VerifySortingOrderForLaggards(), "Sorting order for Laggards");

    }


    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if More companies ranked in link present",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {8434})
    public void verifyClickOnEntitesandVerifyEntityPage(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to Research Line page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");

        //researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        if (!page.equals("Carbon Footprint")) {
            Assert.assertTrue(researchLinePage.checkLLeadersAndLaggardsEntityLinks(page));

        }
        test.info("Verified that the More companies ranked in link is working as expected");
    }
    //TODO De-Scoped as of now
    @Test(enabled = false,groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {9871})
    public void VerifyESGLeadersAndLaggersTable() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("ESG Assessments");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");
        BrowserUtils.scrollTo(researchLinePage.LeadersAndLaggardsTable);
        researchLinePage.validateEsgLeadersANDlaggersScorValuese();
        researchLinePage.validateEsgLeadersANDlaggersModelvalues();
    }

}
