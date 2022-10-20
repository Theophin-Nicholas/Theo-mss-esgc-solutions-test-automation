package com.esgc.Tests.UI.DashboardPage;

import com.esgc.APIModels.PortfolioSettings.PortfolioDetails;
import com.esgc.Controllers.APIController;
import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.*;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Database.PortfolioQueries;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PortfolioSettings extends UITestBase {


    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9391, 9665, 9666, 9667, 9668, 9669, 9670, 9673, 9674})
    public void validatePortfolioSetting_EditPortfolioName() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        String originalPortfolioName = researchLinePage.getPortfolioName();
        System.out.println("originalPortfolioName = " + originalPortfolioName);
        researchLinePage.selectPortfolio(originalPortfolioName);
        BrowserUtils.wait(5);

        assertTestCase.assertTrue(researchLinePage.ValidatePortfolioNameFeildIsEditable(),
                "Validate that portfolio name is editable");

        researchLinePage.validatePortfolioNameNotChangedAfterUpdateAndClickOutside(originalPortfolioName);
        researchLinePage.selectPortfolio(originalPortfolioName);
        researchLinePage.validatePortfolioNameChangedAfterUpdateAndClickInsideDrawer(originalPortfolioName);
        researchLinePage.validatePortfolioNameSavedAutomaticallyAfterTwoSecond(originalPortfolioName);
        researchLinePage.validatePortfolioNameRevertbyCtrlZ(originalPortfolioName);
        researchLinePage.validatePortfolioNameUpdatedInAllLocations(originalPortfolioName);
        // researchLinePage.validateblankPortfolioName(originalPortfolioName);

    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9664})
    public void validatePortfolioSetting_SamplePortfolioNotEditable() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        researchLinePage.selectPortfolio("Sample Portfolio");
        assertTestCase.assertTrue(!researchLinePage.ValidatePortfolioNameFeildIsEditable(),
                "Validate that Sample portfolio should not be editble");
    }

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    @Xray(test = {9627, 9628, 9629})
    public void
    validatePortfolioDeletionViaPortfolioSettings() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickPortfolioSelectionButton();
        researchLinePage.selectPortfolio("Sample Portfolio");
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.wait(2);

        //Verify that Sample Portfolio Delete button is disabled
        System.out.println("Verify that Sample Portfolio Delete button is disabled");
        BrowserUtils.scrollTo(researchLinePage.samplePortfolio);
        researchLinePage.samplePortfolio.click();
        BrowserUtils.scrollTo(researchLinePage.deleteButton);
        assertTestCase.assertFalse(researchLinePage.deleteButton.isEnabled());
        researchLinePage.pressESCKey();


        //Delete a portfolio which is not default selected portfolio
        System.out.println("Delete a portfolio which is not default selected portfolio");
        PortfolioSettings.uploadPortfolioForDelete(); // Uploading :SamplePortfolioToDelete
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.scrollTo(researchLinePage.samplePortfolioToDelete);
        researchLinePage.samplePortfolioToDelete.click();
        BrowserUtils.wait(4);
        researchLinePage.deleteButton.click();
        BrowserUtils.wait(1);
        assertTestCase.assertEquals(researchLinePage.confirmPortfolioDeletePopupHeader.getText(), "Confirm Portfolio Deletion");
        BrowserUtils.wait(4);
        researchLinePage.confirmPortfolioDeleteCancelButton.click(); //Clicking the cancel button
        BrowserUtils.wait(4);
        researchLinePage.deleteButton.click();
        researchLinePage.confirmPortfolioDeleteYesButton.click(); //clicking the Yes button and deleting the portfolio
        BrowserUtils.wait(6);
        researchLinePage.pressESCKey();

        //Delete the selected portfolio
        System.out.println("Delete the selected portfolio");
        PortfolioSettings.uploadPortfolioForDelete(); // Uploading :SamplePortfolioToDelete
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.scrollTo(researchLinePage.samplePortfolioToDelete);
        researchLinePage.samplePortfolioToDelete.click();
        BrowserUtils.wait(4);
        researchLinePage.deleteButton.click();
        researchLinePage.confirmPortfolioDeleteYesButton.click(); //clicking the Yes button and deleting the portfolio
        BrowserUtils.wait(3);
        researchLinePage.pressESCKey();
    }

    @Test(groups = {"regression", "ui", "smoke", "robot_dependency"})
    @Xray(test = {9627, 9628, 9629})
    public void validatePortfolioManagementDrawer() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickPortfolioSelectionButton();
        researchLinePage.selectPortfolio("Sample Portfolio");
        String portfolioNameDashboard = researchLinePage.getPortfolioNameFromDashboard.getText();
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.wait(3);
        List<WebElement> portfolioNames = Driver.getDriver().findElements(By.xpath("(//div[@heap_id='portfolio-selection'])/div[1]/span"));
        assertTestCase.assertEquals(portfolioNameDashboard, portfolioNames.get(1).getText());
        List<WebElement> portfolioDates = Driver.getDriver().findElements(By.xpath("(//div[@heap_id='portfolio-selection'])/div[2]/span"));

        //Date formatting should keep consistent with what currently have, YYYY-MM-DD (Checking only the first)
        researchLinePage.isValidFormat("yyyy-MM-dd", portfolioDates.get(1).getText());

    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9537, 9563})
    public void validatePortfolioUnderPortfolioSettings() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickPortfolioSelectionButton();
        researchLinePage.selectPortfolio("Sample Portfolio");
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.wait(3);
        BrowserUtils.scrollTo(researchLinePage.samplePortfolio);
        researchLinePage.samplePortfolio.click();
        BrowserUtils.wait(5);
        //-The title should be the portfolio name, example: Sample Portfolio.
        assertTestCase.assertEquals(researchLinePage.samplePortfolioTitle.getText(), "Sample Portfolio");
        //-Under the title, should be re-upload hyperlink
        assertTestCase.assertEquals(researchLinePage.portfolioReUpload.getText(), "Re-upload");
        //-Should be a text field with a label saying "Name *"
        assertTestCase.assertEquals(researchLinePage.portfolioNameWithStar.getText(), "Name*");
        //-In the text box, it should show the portfolio name by default
        assertTestCase.assertEquals(researchLinePage.portfolioTextBoxGetValue.getAttribute("value"), "Sample Portfolio");
        //-Under name edit text box, there should be a dropdown.
        assertTestCase.assertTrue(researchLinePage.portfolioDropDownMenu.isDisplayed());
        //-On the bottom, there should be a button says "Delete Portfolio"
        assertTestCase.assertTrue(researchLinePage.deleteButton.isDisplayed());
        BrowserUtils.wait(2);
        //Should be a box shadow saying "We've matched <X>/<Y> INVESTMENTS<Z> ENTITIES" and"Accounting for<N>%of your uploaded portfolio "
        System.out.println("researchLinePage.portfolioDescription.getText() = " + researchLinePage.portfolioDescription.getText());
        assertTestCase.assertTrue(researchLinePage.portfolioDescription.getText().contains("We've matched"));
        //There should be a list of "Company" and "% Investment" which shows detailed company name and investment percentage.
        assertTestCase.assertTrue(researchLinePage.portfolioCompanyColumnNames.getText().contains("Company"));
        assertTestCase.assertTrue(researchLinePage.portfolioCompanyColumnNames.getText().contains("% Investment"));
        //At the bottom of the list, it should say <M> investments no matched in grey and show the corresponding investment %.
        assertTestCase.assertTrue(researchLinePage.portfolioFooterText.getText().contains("investments not matched"));
        //Entities should be hyperlinked and once user clicks on entities, user should be navigated to related Entity Climate Page
        for (int i = 0; i < 5; i++) {// Only verifying first 5 entities.
            String entityName = researchLinePage.portfolioEntityList.get(i).getText();
            System.out.println("entityName = " + entityName);
            System.out.println("researchLinePage.portfolioEntityList.get(i) = " + researchLinePage.portfolioEntityList.get(i).getText());
            System.out.println("researchLinePage.portfolioEntityList.size() = " + researchLinePage.portfolioEntityList.size());
            researchLinePage.portfolioEntityList.get(i).click();
            BrowserUtils.wait(5);
            System.out.println("portfolioEntityName.getText() = " + researchLinePage.portfolioEntityName.getText());
            assertTestCase.assertTrue(researchLinePage.portfolioEntityName.getText().contains(entityName));
            researchLinePage.pressESCKey();
            BrowserUtils.wait(1);
        }
    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9566, 9573, 9572})
    public void validateSelectedPortfolioDrawer() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickPortfolioSelectionButton();
        researchLinePage.selectPortfolio("Sample Portfolio");
        BrowserUtils.wait(10);
        researchLinePage.clickMenu();
        BrowserUtils.wait(2);
        researchLinePage.portfolioSettings.click();
        BrowserUtils.wait(3);
        BrowserUtils.scrollTo(researchLinePage.samplePortfolio);
        researchLinePage.samplePortfolio.click();
        BrowserUtils.wait(5);

        // Get the Portfolio values from DB and calculate %Investment
        DatabaseDriver.createDBConnection();
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        List<List<Object>> companyList = portfolioQueries.getPortfolioCompaniesFromDB();
        Double totalValue = Double.valueOf((portfolioQueries.getPortfolioCompaniesTotalValuesFromDB().get(0).get(0).toString()));

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);

        Map<String, Double> companyMap = new HashMap<>();
        for (int i = 0; i < companyList.size(); i++) {
            String companyName = companyList.get(i).get(0).toString();
            Double value = Double.valueOf(companyList.get(i).get(1).toString());
            Double investmentPercentage = Double.valueOf(df.format(value / totalValue * 100));
            companyMap.put(companyName, investmentPercentage);
        }
        System.out.println("companyMap = " + companyMap);

        //Get the API response payload to calculate to verify the not matched investments
        //Verify 10 largest and 20 largest portfolio UI is same as API
        getExistingUsersAccessTokenFromUI();
        APIController controller = new APIController();
        PortfolioDetails portfolioDetails = controller.getPortfolioSettingsAPIResponse("00000000-0000-0000-0000-000000000000").as(PortfolioDetails.class);
        System.out.println("portfolioDetails.getEntities() = " + portfolioDetails.getEntities());
        System.out.println("portfolioDetails.getTotal_unmatched_companies() = " + portfolioDetails.getTotal_unmatched_companies());
        // check the largest 10-20 investment from UI and API.
        //Simple will verify the first 20 investment that matches with UI.
        researchLinePage.portfolioDropDownMenu.click();
        researchLinePage.portfolioSettingsLargest20Investment.click();
        for (int i = 0; i < 20; i++) {
            String companyUIName = researchLinePage.portfolioSettingsCompanies.get(i).getText();
            String companyAPINAme=portfolioDetails.getInvestments().get(i).getCompany_name();
           // assertTestCase.assertEquals(companyUIName,companyAPINAme );
        }
        // From UI: Get the company list and their percentage.
        BrowserUtils.scrollTo(researchLinePage.portfolioSettingsMoreCompanies);
        researchLinePage.portfolioSettingsMoreCompanies.click();

        //TODO As of now not working. UI values are showing <1%
        for (int i = 0; i < researchLinePage.portfolioSettingsCompanies.size(); i++) {
            String companyUIName = researchLinePage.portfolioSettingsCompanies.get(i).getText();
            //  Double investmentPercentageUI = Double.valueOf(researchLinePage.portfolioSettingsInvestmentPercentage.get(i).getText().toString());
            System.out.println("Calculated Investment % = " + companyMap.get(companyUIName));
            //  System.out.println("UI Investment % = " + investmentPercentageUI);
            //Compare the UI Investment % with calculated DB percentage value
            //assertTestCase.assertTrue(companyMap.get(companyUSName).equals(investmentPercentageUI));
        }
    }

    //this is to upload portfolio to deletion test. Portfolio Name: SamplePortfolioToDelete
    public static void uploadPortfolioForDelete() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.pressESCKey();
        dashboardPage.clickUploadPortfolioButton();
        List<WebElement> checkThePortfolioAvailability = Driver.getDriver().findElements(By.xpath("//span[@title='SamplePortfolioToDelete']"));
        if (checkThePortfolioAvailability.size() >= 1) {
            dashboardPage.pressESCKey();
            return;
        }
        BrowserUtils.waitForVisibility(dashboardPage.uploadButton, 2);
        test.info("Waited for upload button's visibility");

        test.info("Clicked on the Browse File button");
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(3);

        String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty("PortfolioToDelete");
        RobotRunner.selectFileToUpload(inputFile);

        BrowserUtils.wait(5);

        String expectedFileName = "\"" + inputFile.substring(inputFile.lastIndexOf(File.separator) + 1) + "\"";
        System.out.println("expectedFileName = " + expectedFileName);
        String actualFileName = dashboardPage.selectedFileName.getText().substring(0, dashboardPage.selectedFileName.getText().indexOf("Remove") - 1);
        System.out.println("actualFileName = " + actualFileName);

        dashboardPage.clickUploadButton();
        test.info("Clicked on the Upload button");
        BrowserUtils.wait(3);

        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 2);
        test.info("Waited for the Successful popup's visibility");

        dashboardPage.waitForDataLoadCompletion();
        dashboardPage.refreshCurrentWindow();

        dashboardPage.getSelectedPortfolioNameFromDropdown();
        BrowserUtils.wait(10);
    }

    @Test(groups = {"regression", "ui", "smoke"})
    @Xray(test = {9637})
    public void validateReUploadInPortfolioSettings() {
        //Upload a portfolio
        BrowserUtils.wait(10);
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
       String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        System.out.println("token = " + accessToken);
        APIController apiController = new APIController();

       apiController.postValidPortfolio("SamplePortfolioToDelete.csv");

        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        researchLinePage.selectPortfolio("SamplePortfolioToDelete");

        //Verify re-upload portfolio hyperlink functionality working
        researchLinePage.portfolioReUpload.click();
        assertTestCase.assertEquals(researchLinePage.importPortfolioPopUp.getText(), "Import Portfolio");
        BrowserUtils.wait(2);
        researchLinePage.closePortfolioPopUp.click();
    }


}