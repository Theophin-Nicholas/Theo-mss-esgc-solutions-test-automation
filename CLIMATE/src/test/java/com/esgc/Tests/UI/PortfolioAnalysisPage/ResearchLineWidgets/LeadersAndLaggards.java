package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

/**
 * Created by ChaudhS2 on 10/5/2021.
 */
public class LeadersAndLaggards extends UITestBase {

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Leaders and Laggards Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class,dataProvider = "Research Lines")
    @Xray(test = {389})
    public void verifyLeadersAndLaggardsTableUI(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        String title = "Updates as of June 2021 and Current Leaders/Laggards";
        String commonWidgetsID = "updates_and_current_leaders_laggards";

        researchLinePage.navigateToResearchLine(page);
        BrowserUtils.wait(5);
        test.info("Navigated to " + page + " Page");

        Assert.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "check if page title is displayed");
        test.pass("User is on " + page + " Page");

        Assert.assertTrue(researchLinePage.checkifWidgetTitleIsDisplayedWithID(title, commonWidgetsID));
        assertTestCase.assertTrue(researchLinePage.checkIfLeadersAndLaggardsTableDisplayed(commonWidgetsID), "Leaders & Laggards verified", 381, 550, 1256);

        test.pass(title + " Table/Chart displayed");

        assertTestCase.assertTrue(researchLinePage.verifyLeadersHasMax10Companies(), "Leaders has max 10 companies", 383);
        assertTestCase.assertTrue(researchLinePage.verifyLaggardsHasMax10Companies(), "Laggards has max 10 companies", 383);

//        Assert.assertTrue(researchLinePage.checkIfPortfolioCoverageChartTitlesAreDisplayed());
        Assert.assertTrue(researchLinePage.checkifLeadersAndLaggardsTableUIIsperDesign(page));
        test.pass(title + " Table rows columns and content verified");
    }

    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if More companies ranked in link present",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {442, 543, 537, 1275, 2133, 2444, 2880, 3096, 3846, 1263, 1275, 6654 })
    public void verifyMoreRankedCompaniesLink(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        test.info("Navigated to Research Line page");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Selected Sample Portfolio");

        // researchLinePage.clickRegionsSectionAndAsOfDateDropdown();
        test.info("Selecting the As of Date value");

        //researchLinePage.selectAnAsOfDateWhereUpdatesAreAvailable("April 2021");
        if (!page.equals("Carbon Footprint")) {
            Assert.assertTrue(researchLinePage.checkMoreCompaniesExistLink(page));
            Actions action = new Actions(Driver.getDriver());
            action.sendKeys(Keys.ESCAPE);
        }
        test.info("Verified that the More companies ranked in link is working as expected");
    }


    @Test(groups = {"regression", "ui"},
            description = "Verify the ScoreLogic for Leaders And Laggards Section",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2124, 3034, 3159, 2080})
    public void verifyScoreLogicForLeaderAndLaggards(String page){
        ResearchLinePage researchLinePage = new ResearchLinePage();
        if ( page.equals("Temperature Alignment")) {
            throw new SkipException("Leaders & Laggards is not ready to test in " + page);
        }
        researchLinePage.navigateToResearchLine(page);
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "August 2021");
        BrowserUtils.wait(5);
        // researchLinePage.clickAwayinBlankArea();


        assertTestCase.assertTrue(researchLinePage.VerifyIfScoreLogicIsCorrectForLeaders(page), "Entities were not ordered in correct order for Leaders");
        assertTestCase.assertTrue(researchLinePage.VerifyIfScoreLogicIsCorrectForLaggards(page), "Entities were not ordered in correct order for Laggards");

    }
}
