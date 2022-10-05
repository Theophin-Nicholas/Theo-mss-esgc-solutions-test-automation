package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntitySearchFunctionTests extends UITestBase {

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5209 - UI | Portfolio Analysis Page | Search Function | Verify that 10 most words/phrases user typed appear")
    @Xray(test = {5209})
    public void testSearchFunction() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);

        assertTestCase.assertTrue(researchLinePage.checkIfNumberOfSearchResultIsTen("app"), "number of the result in the search bar is 10");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5701 - UI | Portfolio Name Search | Verify the search functionality with wildcard characters")
    @Xray(test = {5701})
    public void testSearchFunctionWildCharacter1() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        researchLinePage.checkSearchResultWithWildChars("app%");
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5701 - UI | Portfolio Name Search | Verify the search functionality with wildcard characters")
    @Xray(test = {5701})
    public void testSearchFunctionWildCharacter2() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        researchLinePage.checkSearchResultWithWildChars("app*");
    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5312 - UI | Portfolio Name Search | Verify the search for portfolio is giving correct result as per the given words")
    @Xray(test = {5312})
    public void testSearchBoxIsGivingCorrectResult() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarOfPortfolioIsDisplayed2());
        assertTestCase.assertTrue(researchLinePage.checkIfSearchResultsContainsSearchKeyWord("Multi"), "Search result contains keyword");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5314 - UI | Portfolio Name Search bar | Verify the message in case of No Result is found for given Portfolio name")
    @Xray(test = {5314})
    public void testSearchFunctionForNoMatchEntry() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);
        assertTestCase.assertTrue(researchLinePage.checkWarningWhenNoMatchEntry("sssss"), "No results found message appeared as warning");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5206 - UI | Portfolio Analysis Page | Search Function | Verify that user should be able to search an entity by typing in the search box")
    @Xray(test = {5206})
    public void testSearchBox() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarOfPortfolioIsDisplayed2(), "Search bar is not displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchResultIsHighlighted("Amazon"), "result in the search bar is displayed");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5708 - UI | Portfolio Analysis Page | Search Function | Verify that a scroll bar is displayed for the search result dropdown on the page")
    @Xray(test = {5708})
    public void testSearchBoxInPortfolioPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        assertTestCase.assertTrue(researchLinePage.isSearchIconDisplayed());
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarOfPortfolioIsDisplayed2(), "Searchbar is displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchResultIsHighlighted("Amazon"), "Search result is highlighted");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5844 - UI | Portfolio Analysis Page | Search Function | Verify that clicking on Entity name redirects to Entity page")
    @Xray(test = {5844})
    public void testClickingOnEntityNameOnPortfolioPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        //BrowserUtils.wait(5);
        researchLinePage.searchIconPortfolioPage.click();
        //BrowserUtils.wait(3);
        assertTestCase.assertFalse(researchLinePage.checkClickingOnEntityName("App"), "Clicking on entity name on search worked");
        assertTestCase.assertTrue(researchLinePage.checkIfUserIsOnRightPage(), "User is on expected page");

    }

    @Test(groups = {"regression", "ui"},
            description = "ESGCA-5845 - UI | Portfolio Analysis Page | Search Function | Verify that clicking ESC button is closing the Entity page")
    @Xray(test = {5845})
    public void testESCbuttonClosingEntityPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        BrowserUtils.wait(3);
        researchLinePage.searchIconPortfolioPage.click();
        assertTestCase.assertFalse(researchLinePage.checkClickingOnEntityName("Apple, Inc."), "Clicking on entity name on search bar worked");

        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.pressESCKey();

        assertTestCase.assertTrue(researchLinePage.userIsOnHomePage(), "User is on expected page");

    }
}
