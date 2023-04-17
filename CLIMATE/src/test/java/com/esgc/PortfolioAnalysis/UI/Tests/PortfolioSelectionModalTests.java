package com.esgc.PortfolioAnalysis.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.RobotRunner;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by ChaudhS2 on 10/20/2021.
 */
public class PortfolioSelectionModalTests extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3179/3250/3274/3253 : Verify the search Funtion on Portfolio Selection Modal PopUP/Sample Portfolio is present in Portfolio list.")
    @Xray(test = {3179, 3250, 3274, 3253})
    public void verifySearchFunctionOnSelectionModalPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

        researchLinePage.enterPortfolioNameInSearchBar("Sample Portfolio");
        researchLinePage.getPortfolioNames()
                .forEach(e ->
                        assertTestCase.assertTrue(e.contains("Sample") && e.contains("Portfolio"), "User was not able to search the portfolio by providing portfolio Name in search bar/Sample Portfolio is not present")
                );
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3252 : Verify user cannot select multiple portfolios at a time")
    @Xray(test = 3252)
    public void verifyUserCanSelectSinglePortfolioOnSelectionModalPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        test.info("Open the selection Modal Popup");
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

        test.info("Select a Portfolio from the list.");
        researchLinePage.selectAPortfolio(1);

        assertTestCase.assertFalse(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed after selection of one Portfolio, restricting user for multiselct");
    }


    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3259/3260 : Verify Click on Portfolio Selection Opens Portfolio Selection Modal and closes on clicking away from the popup")
    @Xray(test = {3259, 3260})
    public void verifySelectionModalPopupBehaviour() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

        test.info("Click on the portfolio selection button");
        researchLinePage.clickAwayinBlankArea();

        test.info("Verify that Selection Popup Modal is closed ");
        assertTestCase.assertFalse(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed on clicking away from the modal Popup.");
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3432 : Verify Upload Portfolio Link is Placed Right Above the List in Portfolio Selection Modal as per Design")
    @Xray(test = {3432})
    public void verifyUploadPortfolioLinkOnSelectionModalPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

        test.info("Check if Upload Portfolio link is present");
        assertTestCase.assertTrue(researchLinePage.checkIfUploadPortfolioIsPrsent(), "Upload Portfolio link was not present on Selection Modal Popup.");
    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3435/3436 : Verify Selected Portfolio is Displayed on Top of the List")
    @Xray(test = {3254, 3435, 3436})
    public void verifyPortfolioSelectionOnSelectionModalPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.waitForDataLoadCompletion();

        test.info("Open the selection Modal Popup");
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

        test.info("Select a Portfolio from the list and save the name of the selected Portfolio.");
        String selectedPortfolio = researchLinePage.getPortfolioNames().get(1);
        researchLinePage.selectAPortfolio(1);
        researchLinePage.getSelectedPortfolioNameFromDropdown();
        test.info("Verify that Loading is triggered");

        test.info("Verify that after portfolio selection , Modal Popup got closed.");
        assertTestCase.assertFalse(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed after selection of one Portfolio, restricting user for multiselct");

        test.info("Open the Modal popup again");
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

        test.info("Get the Top portfolio name in the list on Selection Modal Popup.");
        String portfolioName = researchLinePage.getPortfolioNames().get(0);
        System.out.println("portfolioName = " + portfolioName);
        test.info("Verify that Selected portfolio is on top on the Selection Modal Popup");
        assertTestCase.assertEquals(portfolioName, selectedPortfolio, "User was not able to search the portfolio by providing portfolio Name in search bar/Sample Portfolio is not present");
    }

    @Test(groups = {REGRESSION, UI, SMOKE, ROBOT_DEPENDENCY},
            description = "ESGCA -3269 : Verify that Region and Sector updated after Portfolio change")
    @Xray(test = {3269})
    public void verifyRegionAndSectorSetAsperSelectedPortfolio() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.portfolioForRegionSectorFilterValidation();
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(20);

        researchLinePage.refreshCurrentWindow();
        researchLinePage.clickFiltersDropdown();

        List<String> regionListForTheUploadedPortfolio = researchLinePage.getListOfRegion();
        List<String> sectorListForTheUploadedPortfolio = researchLinePage.getListOfSector();

        researchLinePage.refreshCurrentWindow();
        test.info("Open the selection Modal Popup");
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Select a Portfolio from the list and save the name of the selected Portfolio.");

        test.info("Verify that after portfolio selection , Modal Popup got closed.");
        assertTestCase.assertFalse(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed after selection of one Portfolio, restricting user for multiselct");

        researchLinePage.clickFiltersDropdown();

        List<String> regionListForTheSelectedPortfolio = researchLinePage.getListOfRegion();
        List<String> sectorListForTheSelectedPortfolio = researchLinePage.getListOfSector();

        assertTestCase.assertFalse(regionListForTheUploadedPortfolio.size() == regionListForTheSelectedPortfolio.size(), "Regions dropdown was updated as per the selected portfolio.");
        test.pass("Region dropdown values were updated as per the selected portfolio");

        assertTestCase.assertFalse(sectorListForTheUploadedPortfolio.size() == sectorListForTheSelectedPortfolio.size(), "Sector dropdown was updated as per the selected portfolio.");
        test.pass("Sector dropdown values were updated as per the selected portfolio");

    }

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "ESGCA -3430 : Verify the Sorting logic for Portfolios on Selection modal popup.")
    @Xray(test = {1299, 3430})
    public void verifyPortfolioSortingOnSelectionModalPopup() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);
        researchLinePage.openSelectionModalPopUp();
        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

        List<String> portfolioUploadDates = researchLinePage.getPortfolioUploadDates();
        portfolioUploadDates.remove(0);

        List<String> portfolioUploadDatesSorted = new ArrayList<>(portfolioUploadDates);

        Collections.sort(portfolioUploadDatesSorted, new Comparator<String>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd");

            @Override
            public int compare(String o1, String o2) {
                try {
                    return f.parse(o2).compareTo(f.parse(o1));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        });

        assertTestCase.assertEquals(portfolioUploadDates, portfolioUploadDatesSorted, "Portfolios sorted by date", 3430);
    }


    @Test(groups = {REGRESSION, UI},
            description = "ESGCA -5313 : UI | Portfolio Name Search bar | Verify the default text in the search bar on selection Modal popup")
    @Xray(test = {5313})
    public void verifySearchDefaultMessages() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio Analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.openSelectionModalPopUp();

        assertTestCase.assertTrue(researchLinePage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");
        assertTestCase.assertTrue(researchLinePage.validateTextOfPortfolioSearchField(), "Search field - Placeholder text is incorrect");

    }

    @Test(groups = {REGRESSION, UI, ROBOT_DEPENDENCY},
            description = "ESGCA-5315 - UI | Portfolio Name Search bar | Verify that ellipsis are displayed for a company having too long name")
    @Xray(test = {5315})
    public void verifyEllipsesForPortfolioWithLongName() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigate to Portfolio analysis page");
        researchLinePage.navigateToPageFromMenu("Climate Portfolio Analysis");
        BrowserUtils.wait(5);

        researchLinePage.clickUploadPortfolioButton();
        test.info("Navigated to Upload Page");

        test.info("Clicked on the Browse File button");
        researchLinePage.clickBrowseFile();
        BrowserUtils.wait(2);

        String inputFile = PortfolioFilePaths.portfolioWithLongNamePath();
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(4);
        test.info("Import portfolio file was selected");
        researchLinePage.clickUploadButton();
        test.info("Clicked on the Upload button");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.closePopUp();

        String portfolioName = "Portfolio with very long name to check ellipses after limit";

        researchLinePage.openSelectionModalPopUp();
        researchLinePage.enterPortfolioNameInSearchBar(portfolioName);

        assertTestCase.assertTrue(researchLinePage.checkIfSearchResultsContainsEllipses(portfolioName), "Search result contains keyword");
    }

}
