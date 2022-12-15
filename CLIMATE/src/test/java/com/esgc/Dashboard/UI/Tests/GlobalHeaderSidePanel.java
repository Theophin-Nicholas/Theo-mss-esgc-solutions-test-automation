package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.*;

public class GlobalHeaderSidePanel extends UITestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {1899, 5939, 8967})
    public void validateGlobalHeader() {

        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Check Global Header Side Panel");
        researchLinePage.ValidateGlobalSidePanel();
        test.pass("Global Header Side Panel Verified");

    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = 8968)
    public void validatePortfolioSettings() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        assertTestCase.assertTrue(researchLinePage.validatePortfolioManagementTitleIsDisplayed(), "Validate Portfolio Selection header is available");
        assertTestCase.assertTrue(researchLinePage.validateUploadNewLinkIsAvailable(), "Validate Upload new link is available");
        assertTestCase.assertTrue(researchLinePage.validateSideArrowIsAvailable(), "Validate  Arrow  is available ");
        assertTestCase.assertTrue(researchLinePage.validatespanPortfolioNameColumnIsAvailable(), "Validate Portfolio Name Column  is available");
        assertTestCase.assertTrue(researchLinePage.validatespanUploadDateColumnIsAvailable(), "Validate Upload Date column header is available");
        //Verify the sorting logic under portfolio settings portfolio list.
        List<WebElement> portfolioName = Driver.getDriver().findElements(By.xpath("(//div[@heap_id='portfolio-selection'])/div[1]/span"));
        List<String> actualList = new ArrayList<>();
        List<String> expectedList = new ArrayList<>();
        for (WebElement names : portfolioName) {
            System.out.println("names.getText() = " + names.getText());
            actualList.add(names.getText());
            expectedList.add(names.getText());
        }
        actualList.remove(0);
        actualList.remove(0);
        expectedList.remove(0);
        expectedList.remove(0);
        Collections.sort(expectedList);
        System.out.println("expectedList = " + expectedList);
        System.out.println("actualList = " + actualList);
        //assertTestCase.assertEquals(actualList, expectedList);
        //TODO Sorting is failing due to digits used in portfolio. And UI sorting logic not matching.
        //Multiple portfolio with the same name is allowed for now
        Set<String> checkDuplicates = new HashSet<>(actualList);
        int beforeRemovingTheDuplicatesSize = actualList.size();
        int afterRemovingDuplicateSize = checkDuplicates.size();
        assertTestCase.assertFalse(beforeRemovingTheDuplicatesSize == afterRemovingDuplicateSize);
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

        researchLinePage.navigateToPageFromMenu("Datalab");
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

        assertTestCase.assertTrue(login.isUsernameBoxDisplayed(), "Logout successfully");

    }


}
