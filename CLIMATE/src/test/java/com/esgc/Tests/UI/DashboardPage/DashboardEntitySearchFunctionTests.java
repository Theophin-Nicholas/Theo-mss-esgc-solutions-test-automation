package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by ChaudhS2 on 1/20/2022.
 */

public class DashboardEntitySearchFunctionTests extends UITestBase {

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5196)
    public void verifySearchBoxappearonSearchIConClickOnDashboard() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Click on Search Icon");
        dashboardPage.waitForDataLoadCompletion();
        dashboardPage.clickSearchIcon();
        assertTestCase.assertTrue(dashboardPage.isSearchBoxDisplayed(), "Search Box Displayed verification on the Dashboard page after clicking Search Icon.");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5195)
    public void verifySearchBoxAppearonSearchIConClickOnPortfolioAnalysisPage() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Click on Search Icon");
        researchLinePage.clickSearchIcon();
        assertTestCase.assertTrue(researchLinePage.isSearchBoxDisplayed(), "Search Box Displayed verification on the Portfolio Analysis page after clicking Search Icon.");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5200)
    public void verifySearchBoxDisappearOnPortfolioAnalysisPageOnClickCloseICon() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.clickSearchIcon();
        assertTestCase.assertTrue(researchLinePage.isSearchBoxDisplayed(), "Search Box is displayed on the Portfolio Analysis page after clicking Search Icon.");
        test.info("Search box was appearing on Portfolio Analysis page after clicking Search Icon");
        researchLinePage.clickCloseIcon();
        BrowserUtils.wait(2);
        assertTestCase.assertFalse(researchLinePage.isSearchBoxDisplayed(), "Search Box disappeared on the Portfolio Analysis page even after clicking close Icon.");
        test.info("Search box was appearing on Portfolio Analysis page even after clicking Close Icon");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5199)
    public void verifySearchBoxDisappearOnDashboardOnClickCloseICon() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Portfolio Analysis page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Click on Search Icon");
        dashboardPage.clickSearchIcon();
        assertTestCase.assertTrue(dashboardPage.isSearchBoxDisplayed(), "Search Box is displayed on the Dashboard page after clicking Search Icon.");
        test.info("Search box was appearing on Dashboard page after clicking Search Icon");
        dashboardPage.clickCloseIcon();
        BrowserUtils.wait(2);
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "Search Box disappeared on the Dashboard page even after clicking close Icon.");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5201)
    public void verifySearchBoxOnDashboardDisappearsOnPressingESCKey() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Portfolio Analysis page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Click on Search Icon");
        dashboardPage.clickSearchIcon();
        assertTestCase.assertTrue(dashboardPage.isSearchBoxDisplayed(), "Search Box is displayed on the Dashboard page after clicking Search Icon.");
        test.info("Search box was appearing on Dashboard page after clicking Search Icon");
        dashboardPage.sendESCkey();
        BrowserUtils.wait(2);
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "Search Box is disappeared on the Dashboard page even after pressing ESC key.");
        test.info("Search box was appearing on Dashboard page even after pressing ESC key");

    }

    @Test(groups = {"dashboard", "regression", "ui", "smoke", "search_entity"})
    @Xray(test = 5202)
    public void verifySearchBoxOnPortfolioAnalysisPageDisappearsOnPressingESCKey() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");

        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.clickSearchIcon();
        Assert.assertTrue(researchLinePage.isSearchBoxDisplayed(), "Search Box was not appearing on the Portfolio Analysis page after clicking Search Icon.");
        test.info("Search box was appearing on Portfolio Analysis page after clicking Search Icon");
        researchLinePage.sendESCkey();
        BrowserUtils.wait(2);
        Assert.assertFalse(researchLinePage.isSearchBoxDisplayed(), "Search Box was appearing on the Portfolio Analysis page even after pressing ESC key.");
        test.info("Search box was appearing on Portfolio Analysis page even after pressing ESC key");

    }

    @Test(groups = {"regression", "ui", "search_entity"},
            description = "ESGCA-5205 - UI | Dashboard Page | Search Function | Verify that user should be able to search an entity by typing in the search box")
    @Xray(test = 5205)
    public void testSearchBox() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        assertTestCase.assertTrue(dashboardPage.isSearchIconDisplayed());
        dashboardPage.searchIconPortfolioPage.click();

        assertTestCase.assertTrue(dashboardPage.isSearchBoxDisplayed());
        assertTestCase.assertTrue(dashboardPage.checkIfSearchResultIsHighlighted("Amazon"), "Search result is highlighted");

    }

    @Test(groups = {"regression", "ui", "search_entity"},
            description = "ESGCA-5208 - UI | Dashboard Page | Search Function | Verify that 10 most words/phrases user typed appear")
    @Xray(test = 5208)
    public void testSearchFunction() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");

        assertTestCase.assertTrue(dashboardPage.isSearchIconDisplayed());
        dashboardPage.searchIconPortfolioPage.click();

        assertTestCase.assertTrue(dashboardPage.checkIfNumberOfSearchResultIsTen("app"), "Number of the search result is 10");

    }

    @Test(groups = {"regression", "ui", "search_entity"},
            description = "ESGCA-5710 - UI | Dashboard Page | Search Function | Verify the message in case No match found for the searched entity")
    @Xray(test = 5710)
    public void testSearchFunctionForNoMatchEntry() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.searchIconPortfolioPage.click();
        assertTestCase.assertTrue(dashboardPage.checkWarningWhenNoMatchEntry("sss"), "No results found message appeared as warning");
    }

    @Test(groups = {"regression", "ui", "search_entity"},
            description = "Verify that clicking on Entity name redirects to Entity page and ESC button is closing the Entity page")
    @Xray(test = {5843,5846})
    public void testClickingOnEntityName() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
        dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.searchIconPortfolioPage.click();
        assertTestCase.assertFalse(dashboardPage.checkClickingOnEntityName("App"), "Clicking on entity name on search bar worked");
        assertTestCase.assertTrue(dashboardPage.checkIfUserIsOnRightPage(), "User is not on expected page");
        dashboardPage.pressESCKey();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");
    }

  /*  @Test(groups = {"regression", "ui", "search_entity"},
            description = "ESGCA-5846 - UI | Dashboard Page | Search Function | Verify that clicking ESC button is closing the Entity page")
    @Xray(test = 5846)
    public void testESCButtonClosingEntityPage() {
        DashboardPage dashboardPage = new DashboardPage();
        test.info("Navigate to Dashboard page");
    //    dashboardPage.navigateToPageFromMenu("Dashboard");
        dashboardPage.searchIconPortfolioPage.click();
        assertTestCase.assertFalse(dashboardPage.checkClickingOnEntityName("App"), "Clicking on entity name on search bar worked");

        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
        assertTestCase.assertFalse(dashboardPage.isSearchBoxDisplayed(), "User is on expected page");
    }
*/

}

