package com.esgc.Tests.UI.DashboardPage;

import com.esgc.Pages.LoginPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class GlobalHeaderSidePanel extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {1899, 5939, 8967})
    public void validateGlobalHeader() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Check Global Header Side Panel");
        researchLinePage.ValidateGlobalSidePanel();
        test.pass("Global Header Side Panel Verified");

    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = 1905)
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

        researchLinePage.navigateToPageFromMenu("Climate on Demand");

        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
        }

        currentURL = Driver.getDriver().getCurrentUrl();

        assertTestCase.assertEquals(currentURL, "https://climate-on-demand.moodys.com/AppsServices/", "Climate on Demand page verified");

        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(firstPage);


        researchLinePage.navigateToPageFromMenu("Company Portal");

        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
        }

        currentURL = Driver.getDriver().getCurrentUrl();

        assertTestCase.assertEquals(currentURL, "https://veconnect.vigeo.com/login.html", "Company Portal page verified");

        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(firstPage);

        researchLinePage.navigateToPageFromMenu("DataLab");
        BrowserUtils.wait(10);

        for (String windowHandle : Driver.getDriver().getWindowHandles()) {
            Driver.getDriver().switchTo().window(windowHandle);
        }

        currentURL = Driver.getDriver().getCurrentUrl();

        assertTestCase.assertEquals(currentURL, "https://www.ve-datalab.com/Account/EGPlogin.aspx?src=/Features/Home/Landing.aspx", "Data Lab page verified");

        Driver.getDriver().close();
        Driver.getDriver().switchTo().window(firstPage);

        researchLinePage.navigateToPageFromMenu("Log Out");
        BrowserUtils.wait(5);
        LoginPage login = new LoginPage();

        assertTestCase.assertTrue(login.usernameBox.isDisplayed(), "Logout successfully");

    }

    @Test(groups = {"regression", "ui", "smoke"})
    public void validatePortfolioSettings() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        assertTestCase.assertTrue(researchLinePage.validatePOrtfolioManagementHeaderIsAvailable(), "Validate Portfolio Management header is available");
        assertTestCase.assertTrue(researchLinePage.validateUploadNewLinkIsAvailable(), "Validate Upload new link is available");
        assertTestCase.assertTrue(researchLinePage.validateSideArrowIsAvailable(), "Validate  Arrow  is available ");
        assertTestCase.assertTrue(researchLinePage.validatespanPortfolioNameColumnIsAvailable(), "Validate Portfolio Name Column  is available");
        assertTestCase.assertTrue(researchLinePage.validatespanUploadDateColumnIsAvailable(), "Validate Upload Date column header is available");


    }
}
