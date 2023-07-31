package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.esgc.Utilities.Groups.*;

public class GlobalHeaderSidePanel extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = {1899, 5939, 8967})
    public void validateGlobalHeader() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Check Global Header Side Panel");
        researchLinePage.ValidateGlobalSidePanel();
        test.pass("Global Header Side Panel Verified");
    }

    //TODO orders are different and this method should be moved to a separate class for Portfolio Settings
    //Expected order should be incase sensitive and numbers should come last
    @Test(groups = {REGRESSION, UI, SMOKE})
    @Xray(test = 8968)
    public void validatePortfolioSettings() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(researchLinePage.validatePortfolioManagementTitleIsDisplayed(), "Validate Portfolio Selection header is available");
        researchLinePage.portfolioSettings.click();
        assertTestCase.assertTrue(researchLinePage.validateUploadNewLinkIsAvailable(), "Validate Upload new link is available");
        assertTestCase.assertTrue(researchLinePage.validateSideArrowIsAvailable(), "Validate  Arrow  is available ");
        assertTestCase.assertTrue(researchLinePage.validatespanPortfolioNameColumnIsAvailable(), "Validate Portfolio Name Column  is available");
        assertTestCase.assertTrue(researchLinePage.validatespanUploadDateColumnIsAvailable(), "Validate Upload Date column header is available");
        //Verify the sorting logic under portfolio settings portfolio list.
        List<WebElement> portfolioName = Driver.getDriver().findElements(By.xpath("(//div[@heap_id='portfolio-selection'])/div[1]/span"));
        List<String> actualList = BrowserUtils.getElementsText(portfolioName);
        List<String> expectedList = BrowserUtils.getElementsText(portfolioName);
        actualList.remove(0);
        actualList.remove(0);
        expectedList.remove(0);
        expectedList.remove(0);
        expectedList = BrowserUtils.specialSort(expectedList);
        System.out.println("expectedList = " + expectedList);
        System.out.println("actualList = " + actualList);
        assertTestCase.assertEquals(actualList, expectedList);
        //Multiple portfolio with the same name is allowed for now
        Set<String> checkDuplicates = new HashSet<>(actualList);
        int beforeRemovingTheDuplicatesSize = actualList.size();
        int afterRemovingDuplicateSize = checkDuplicates.size();
        assertTestCase.assertTrue(beforeRemovingTheDuplicatesSize >= afterRemovingDuplicateSize);
    }

    @Test(groups = {REGRESSION, UI})
    @Xray(test = {1905, 11828})
    public void validateGlobalHeaderActions() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Check Global Header Side Panel");
        BrowserUtils.wait(10);
        String firstPage = Driver.getDriver().getWindowHandle();

        researchLinePage.navigateToPageFromMenu("Contact Us");

        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
        }

        String currentURL = Driver.getDriver().getCurrentUrl();

        assertTestCase.assertEquals(currentURL, "https://www.moodys.com/Pages/contactus.aspx", "Contact Us page verified");

        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(firstPage);

        researchLinePage.navigateToPageFromMenu("Terms & Conditions");

        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
        }

        currentURL = Driver.getDriver().getCurrentUrl();

        assertTestCase.assertEquals(currentURL, Environment.URL + "terms", "Terms & Conditions page verified", 11833);
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(//header)[3]//../following-sibling::div")).getText(),
                TermsConditionsUtilities.termsAndConditionText());

        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(firstPage);

        researchLinePage.navigateToPageFromMenu("Log Out");
        BrowserUtils.wait(5);
        LoginPage login = new LoginPage();

        assertTestCase.assertTrue(login.isUsernameBoxDisplayed(), "Logout successfully");

    }

    @Test(groups = {UI, REGRESSION})
    @Xray(test = {12840, 12841, 12916, 12850, 12855, 12852})
    public void ValidateCalculationsOptionsFromGlobalMenu() {
        DashboardPage dashboardPage = new DashboardPage();
        if(!Environment.environment.equalsIgnoreCase("qa"))
            new SkipException("Calculations option is only available for QA environment");
        dashboardPage.clickOnMenuButton();
        assertTestCase.assertTrue(dashboardPage.menuItems.stream().filter(e -> e.getText().equals("Calculations")).count() > 0, "Verify \"Calculation\" option is shown under global settings menu", 12840);
        assertTestCase.assertTrue(dashboardPage.menuItems.stream().filter(e -> e.getText().equals("Portfolio Selection/Upload")).count() > 0, "Verify option of \"Portfolio selection\" is shown under global settings menu", 12841);
        dashboardPage.navigateToPageFromMenu("Calculations");
        dashboardPage.verifyCalculationDrawer();
        dashboardPage.selectOtherOptionAndValidateSaveMessage(dashboardPage.getSelectedOption());

        String before_SelectedOption = dashboardPage.getSelectedOption();
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);
        dashboardPage.selectRandomPortfolioFromPortfolioSelectionModal();

        dashboardPage.clickOnMenuButton();
        dashboardPage.navigateToPageFromMenu("Calculations");
        assertTestCase.assertEquals(before_SelectedOption, dashboardPage.getSelectedOption(), "Verify changed made on \"Calculation\" drawer are retained on switching portfolios", 12855);
        BrowserUtils.ActionKeyPress(Keys.ESCAPE);
        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.login();
        dashboardPage.clickOnMenuButton();
        dashboardPage.navigateToPageFromMenu("Calculations");
        assertTestCase.assertEquals(before_SelectedOption, dashboardPage.getSelectedOption(), "Verify changed made on \"Calculation\" drawer are retained on switching portfolios", 12852);


    }


}
