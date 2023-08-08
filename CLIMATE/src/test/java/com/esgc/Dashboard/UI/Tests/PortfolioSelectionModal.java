package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.DashboardUITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.RobotRunner;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class PortfolioSelectionModal extends DashboardUITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -1713/4997/1696/4706 : Verify the search Function on Portfolio Selection Modal PopUP/Sample Portfolio is present in Portfolio list.")
    @Xray(test = {1713, 4997, 1696, 4706, 4662})
    public void verifySearchFunctionOnSelectionModalPopup() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio Analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);
            dashboardPage.openSelectionModalPopUp();
            Assert.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
            Assert.assertTrue(dashboardPage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

            dashboardPage.enterPortfolioNameInSearchBar("Sample Portfolio");
            dashboardPage.getPortfolioNames()
                    .forEach(e -> {
                        e = e.replaceAll("\\n", "");
                        Assert.assertTrue(e.contains("Sample") && e.contains("Portfolio"), "User was not able to search the portfolio by providing portfolio Name in search bar/Sample Portfolio is not present");
                    });
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -4731 : Verify user cannot select multiple portfolios at a time")
    @Xray(test = {4731, 3463})
    public void verifyUserCanSelectSinglePortfolioOnSelectionModalPopup() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);

            test.info("Open the selection Modal Popup");
            dashboardPage.openSelectionModalPopUp();
            Assert.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");
            Assert.assertTrue(dashboardPage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was not opened");

            test.info("Select a Portfolio from the list.");
            dashboardPage.selectAPortfolio(1);

            Assert.assertFalse(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed after selection of one Portfolio, restricting user for multiselct");

        } catch (Exception e) {
            throw e;
        }
    }


    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -1766/1279 : Verify Click on Portfolio Selection Opens Portfolio Selection Modal and closes on clicking away from the popup")
    @Xray(test = {1766, 1279})
    public void verifySelectionModalPopupBehaviour() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio Analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);
            dashboardPage.openSelectionModalPopUp();
            Assert.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

            test.info("Click on the portfolio selection button");
            dashboardPage.clickAwayinBlankArea();

            test.info("Verify that Selection Popup Modal is closed ");
            Assert.assertFalse(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed on clicking away from the modal Popup.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -1945 : Verify Upload Portfolio Link is Placed Right Above the List in Portfolio Selection Modal as per Design")
    @Xray(test = {1945})
    public void verifyUploadPortfolioLinkOnSelectionModalPopup() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio Analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);
            dashboardPage.openSelectionModalPopUp();
            Assert.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

            test.info("Check if Upload Portfolio link is present");
            Assert.assertTrue(dashboardPage.checkIfUploadPortfolioIsPrsent(), "Upload Portfolio link was not present on Selection Modal Popup.");
        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -1392/3463 : Verify Selected Portfolio is Displayed on Top of the List")
    @Xray(test = {1392, 3463})
    public void verifyPortfolioSelectionOnSelectionModalPopup() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);

            test.info("Open the selection Modal Popup");
            dashboardPage.openSelectionModalPopUp();
            assertTestCase.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup is opened");
            assertTestCase.assertTrue(dashboardPage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup is opened");

            test.info("Select a Portfolio from the list and save the name of the selected Portfolio.");
            String selectedPortfolio = dashboardPage.getPortfolioNames().get(1);
            dashboardPage.selectAPortfolio(1);

            test.info("Verify that Loading is triggered");

            test.info("Verify that after portfolio selection , Modal Popup got closed.");
            assertTestCase.assertFalse(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was closed after selection of one Portfolio, restricting user for multiselct");

            test.info("Open the Modal popup again");
            dashboardPage.openSelectionModalPopUp();
            Assert.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was not opened");

            test.info("Get the Top portfolio name in the list on Selection Modal Popup.");
            String portfolioName = dashboardPage.getPortfolioNames().get(0);
            System.out.println("portfolioName = " + portfolioName);
            test.info("Verify that Selected portfolio is on top on the Selection Modal Popup");
            Assert.assertEquals(portfolioName.replaceAll("\\n", ""), selectedPortfolio.replaceAll("\\n", ""), "User was not able to search the portfolio by providing portfolio Name in search bar/Sample Portfolio is not present");

        } catch (Exception e) {
            throw e;
        }
    }

    @Test(groups = {REGRESSION, UI, SMOKE, ROBOT_DEPENDENCY, DASHBOARD},
            description = "ESGT -2051 : Verify that Region and Sector updated after Portfolio change")
    @Xray(test = {2051})
    public void verifyRegionAndSectorSetAsperSelectedPortfolio() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");

            dashboardPage.clickUploadPortfolioButton();
            test.info("Navigated to Upload Page");

            test.info("Clicked on the Browse File button");
            dashboardPage.clickBrowseFile();
            BrowserUtils.wait(2);

            String inputFile = PortfolioFilePaths.portfolioForRegionSectorFilterValidation();
            RobotRunner.selectFileToUpload(inputFile);

            BrowserUtils.wait(4);
            test.info("Import portfolio file was selected");
            dashboardPage.clickUploadButton();
            test.info("Clicked on the Upload button");
            BrowserUtils.wait(20);

            dashboardPage.refreshCurrentWindow();
            dashboardPage.clickFiltersDropdown();

            List<String> regionListForTheUploadedPortfolio = dashboardPage.getListOfRegion();
            List<String> sectorListForTheUploadedPortfolio = dashboardPage.getListOfSector();

            dashboardPage.refreshCurrentWindow();

            dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
            test.info("Select a Portfolio from the list and save the name of the selected Portfolio.");

            dashboardPage.clickFiltersDropdown();

            List<String> regionListForTheSelectedPortfolio = dashboardPage.getListOfRegion();
            List<String> sectorListForTheSelectedPortfolio = dashboardPage.getListOfSector();

            assertTestCase.assertFalse(regionListForTheUploadedPortfolio.size() == regionListForTheSelectedPortfolio.size(), "Regions dropdown was updated as per the selected portfolio.");
            test.pass("Region dropdown values were updated as per the selected portfolio");

            assertTestCase.assertFalse(sectorListForTheUploadedPortfolio.size() == sectorListForTheSelectedPortfolio.size(), "Sector dropdown was updated as per the selected portfolio.");
            test.pass("Sector dropdown values were updated as per the selected portfolio");


        } catch (Exception e) {
            throw e;
        }

    }

    @Test(groups = {REGRESSION, UI, SMOKE, DASHBOARD},
            description = "ESGT -2054 : Verify the Sorting logic for Portfolios on Selection modal popup.")
    @Xray(test = {5003, 2054})
    public void verifyPortfolioSortingOnSelectionModalPopup() {
        try {
            DashboardPage dashboardPage = new DashboardPage();
            test.info("Navigate to Portfolio Analysis page");
            dashboardPage.navigateToPageFromMenu("Climate Portfolio Analysis");
            BrowserUtils.wait(5);
            dashboardPage.openSelectionModalPopUp();
            assertTestCase.assertTrue(dashboardPage.checkIfSelectionModalPopupIsDisplayed(), "Selection Modal Popup was opened");
            assertTestCase.assertTrue(dashboardPage.checkIfSearchBarIsDisplayed(), "Selection Modal Popup was opened");

            List<String> portfolioUploadDates = dashboardPage.getPortfolioUploadDates();
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

            assertTestCase.assertEquals(portfolioUploadDates, portfolioUploadDatesSorted, "Portfolios sorted by date", 2054);
        } catch (Exception e) {
            throw e;
        }
    }
}
