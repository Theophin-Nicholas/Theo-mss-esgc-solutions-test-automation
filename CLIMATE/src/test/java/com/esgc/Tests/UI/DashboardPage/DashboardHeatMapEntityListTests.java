package com.esgc.Tests.UI.DashboardPage;

import com.esgc.APIModels.Dashboard.APIHeatMapResponse;
import com.esgc.APIModels.Dashboard.APIHeatMapSinglePayload;
import com.esgc.Controllers.APIController;
import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.LoginPage;
import com.esgc.Tests.TestBases.Descriptions;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.util.*;

public class DashboardHeatMapEntityListTests extends UITestBase {
    @Test(groups = {"dashboard", "ui", "smoke"})
    @Xray(test = {4843, 4844, 4829, 7475, 7943, 9268, 9269, 9270, 6221})
    public void verifyEntityListTest() {
        DashboardPage dashboardPage = new DashboardPage();
        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        //Navigate to the heatmap section
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        //Verify the entity list - Entity list should be displaying no records
        assertTestCase.assertTrue(dashboardPage.heatMapNoEntityWidget.isDisplayed(),
                "Verified the widget doesn't show anything before a cell is selected.");
        System.out.println("heatMapNoEntityWidget displayed..");
        //Click on any cell from the heatmap
        /*The entity list is updated and the records (companies) which meet the criteria are now displayed in descending
        order based on percentage of investment on the same.*/
        verifyCells();
        System.out.println("verifyCells(); passed");
        for (int i = 2; i < dashboardPage.heatMapResearchLines.size(); i++) {
            //De-select a research line from the heatmap section
            dashboardPage.heatMapResearchLines.get(i).click();
            //Click on any cell from the heatmap
            verifyCells();
        }
    }

    @Test(groups = {"dashboard", "ui", "regression"})
    @Xray(test = {4784, 4785, 4786, 4787, 4788, 4789, 4798, 4799, 6208, 7899, 7900, 9266})
    public void DashboardUIHeatMapTest() {
        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.wait(3);
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        //Navigate to the heatmap section
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        System.out.println("Scrolling to Heatmap Section");

        //Verify the Default / Pressed / Hover states of the research lines
        dashboardPage.heatMapResearchLines.forEach(i -> {
            String actBackgroundColor = Color.fromString(i.getCssValue("background-color")).asHex();
            assertTestCase.assertTrue(dashboardPage.verifyResearchLineColor(actBackgroundColor), i.getText() + " background color verified");
            actBackgroundColor = Color.fromString(i.getCssValue("color")).asHex();
            assertTestCase.assertTrue(dashboardPage.verifyResearchLineColor(actBackgroundColor), i.getText() + " text color verified");
            BrowserUtils.hover(i);
            BrowserUtils.wait(2);
            actBackgroundColor = Color.fromString(i.getCssValue("background-color")).asHex();
            assertTestCase.assertTrue(dashboardPage.verifyResearchLineColor(actBackgroundColor), i.getText() + " hover over color verified");
        });

        //Verify the interaction and display state of the selected research lines
        for (int i = 0; i < 2; i++) {
            String color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("background-color")).asHex();
            assertTestCase.assertEquals(color, "#355b85", "Selected research line background color verified");
            color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("color")).asHex();
            if (!color.equals("#ffffff")) {
                System.out.println("Selected Research Element = " + dashboardPage.heatMapResearchLines.get(i).getText());
                System.out.println("Text color = " + color);
            }
            assertTestCase.assertEquals(color, "#ffffff", "Selected research line text color verified");
        }
        //Verify the interaction and display state of the un-selected research lines
        for (int i = 2; i < dashboardPage.heatMapResearchLines.size(); i++) {
            BrowserUtils.hover(dashboardPage.heatMapResearchLines.get(i - 1));
            String color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("background-color")).asHex();

            if (!color.equals("#f5f7f7")) {
                System.out.println("Unselected Research Element = " + dashboardPage.heatMapResearchLines.get(i).getText());
                System.out.println("Text color = " + color);
            }
            assertTestCase.assertEquals(color, "#f5f7f7", "Unselected research line background color verified");
            color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("color")).asHex();
            if (!color.equals("#333333")) {
                System.out.println("Selected Research Element = " + dashboardPage.heatMapResearchLines.get(i).getText());
                System.out.println("Text color = " + color);
            }
            assertTestCase.assertEquals(color, "#333333", "Unselected research line text color verified");
        }
        //dashboardPage.heatMapResearchLines.forEach(i-> System.out.println(i.getText()));
        //When displaying the research lines within the heatmap table, the first research line will always be the Y axis,
        // and the second research line will be the X axis.
        assertTestCase.assertTrue(dashboardPage.heatMapResearchLines.get(0).getText().contains(dashboardPage.heatMapYAxisIndicatorTitle.getText()),
                "First selected research line is displayed on Y axis");
        assertTestCase.assertTrue(dashboardPage.heatMapResearchLines.get(1).getText().contains(dashboardPage.heatMapXAxisIndicatorTitle.getText()),
                "Second selected research line is displayed on X axis");
        //Verify the percentage displayed in the axes

        //Deselect 1 research line
        dashboardPage.waitForHeatMap();
        dashboardPage.heatMapResearchLines.get(0).click();
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(1),
                "map is updated and reflects the information for 1st research line");

        //Deselect the last research line
        dashboardPage.waitForHeatMap();
        dashboardPage.heatMapResearchLines.get(1).click();
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(1),
                "User is unable to deselect the current research line");

        //Select a third research line
        dashboardPage.waitForHeatMap();
        dashboardPage.heatMapResearchLines.get(2).click();
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(1),
                "map is updated and reflects the information for 1st research line");
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(2),
                "map is updated and reflects the information for 2nd research line");

        //Repeat step 4 with a different research line
        dashboardPage.waitForHeatMap();
        dashboardPage.heatMapResearchLines.get(3).click();
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(2),
                "map is updated and reflects the information for 1st research line");
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapResearchLineTitle(3),
                "map is updated and reflects the information for 2nd research line");
        //Verify the applicable research lines
        assertTestCase.assertTrue(dashboardPage.verifyResearchLines(), "Applicable research lines verified");

        //Verify the chart's selectable research lines
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLine(i);
            assertTestCase.assertTrue(verifyResearchLineDistributionCategories(
                            dashboardPage.heatMapYAxisIndicatorTitle.getText(),
                            dashboardPage.heatMapYAxisIndicators.size()),
                    dashboardPage.heatMapYAxisIndicatorTitle.getText() + " category name and number is verified");
            System.out.println(dashboardPage.heatMapYAxisIndicatorTitle.getText() + " category name and number is verified");
        }

    }

    @Test(groups = {"dashboard", "ui", "regression"})
    @Xray(test = {4880, 4885, 4825, 4824})
    public void verifyHeatMapPercentages() {
        //Verify the percentage displayed in the axes
        DashboardPage dashboardPage = new DashboardPage();
        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");
        BrowserUtils.wait(5);
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        int counter = 0;
        double[][] percentages = new double[dashboardPage.heatMapYAxisIndicators.size()][dashboardPage.heatMapXAxisIndicators.size()];
        double sumOfPercentages = 0;
        for (int i = 0; i < dashboardPage.heatMapYAxisIndicatorPercentages.size(); i++) {
            double expXPercent = 0;
            for (int j = 0; j < dashboardPage.heatMapXAxisIndicatorPercentages.size(); j++) {
                String expPercentage = dashboardPage.heatMapCells.get(counter).getText();
                percentages[i][j] = Double.parseDouble(expPercentage.replaceAll("%", ""));
                counter++;
                expXPercent += percentages[i][j];
            }
            double expYPercent = Arrays.stream(percentages[i]).sum();
            //Distribution of entities should be represented in the xx.xx% format with up to 2 decimal points.
            String actPercentString = dashboardPage.heatMapYAxisIndicatorPercentages.get(i).getText();
            assertTestCase.assertTrue(actPercentString.matches("^\\d{0,4}(\\.\\d{2})? *%?$"),
                    "Actual percentage is in the correct format");
            actPercentString = actPercentString.trim().replaceAll("%", "");
            double actYPercent = Double.parseDouble(actPercentString);

            //Distribution of entities should be represented in the xx.xx% format with up to 2 decimal points.
            assertTestCase.assertTrue(actPercentString.matches("^\\d{0,4}(\\.\\d{2})? *%?$"),
                    "Actual percentage is in the correct format");
            actPercentString = actPercentString.trim().replaceAll("%", "");
            double actXPercent = Double.parseDouble(actPercentString);
            if (expYPercent != actYPercent) {
                System.out.println("Row Num = " + (i + 1));
                System.out.println("Percent on Y Indicator = " + actYPercent);
                System.out.println("Percent of Totals from cells = " + expYPercent);
            }
            sumOfPercentages += actXPercent;
        }
        assertTestCase.assertTrue(sumOfPercentages <= 100, "Percentages sum is as expected less than or equal to 100%");

    }

    @Test(groups = {"dashboard", "ui", "regression"})
    @Xray(test = {4800, 4801, 4802})
    public void verifyHeatMapCellMatrix() {
        //Verify the percentage displayed in the axes
        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.scrollTo(dashboardPage.heatMapXAxisIndicators.get(0));
        BrowserUtils.wait(8);
        if (dashboardPage.heatMapXAxisIndicators.size() > 0) {
            int x = 0, y = 0;
            x = dashboardPage.heatMapXAxisIndicators.size();
            y = dashboardPage.heatMapYAxisIndicators.size();
            System.out.println("x value: "+x+ " and y value: "+y);
            int expMatrixSize = x * y;
            int actMatrixSize = dashboardPage.heatMapCells.size();
            assertTestCase.assertEquals(actMatrixSize, expMatrixSize, "Heat Map Matrix Size verified for X and Y axis");

        } else {
            assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.size(), dashboardPage.heatMapCells.size(),
                    "Heat Map Matrix Size verified for Y axis");
        }

    }

    @Test(groups = {"dashboard", "ui", "regression", "smoke"})
    @Xray(test = {7942, 7944, 5115})
    public void getSelectedResearchLineNameFromHeatMap() {
        DashboardPage dashboardPage = new DashboardPage();
        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        BrowserUtils.wait(2);
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLine(i);

            String color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("background-color")).asHex();
            String researchLine = dashboardPage.heatMapResearchLines.get(i).getText();
            switch (researchLine) {
                case "Overall ESG Score":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Weak", "Overall ESG Score Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Advanced", "Overall ESG Score Verified");
                    assertTestCase.assertEquals(dashboardPage.overallESGDescription.getText(), Descriptions.OVERALL_ESG_SCORE_HEATMAP);
                    break;
                case "Physical Risk: Operations Risk":
                case "Physical Risk: Market Risk":
                case "Physical Risk: Supply Chain Risk":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "80-100", "Physical Risk verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(4).getText(), "0-19", "Physical Risk verified");
                    if (researchLine.equals("Physical Risk: Operations Risk"))
                        assertTestCase.assertEquals(dashboardPage.operationRiskDescription.getText(), Descriptions.OPERATIONS_RISK_DESCRIPTION_HEATMAP);
                    else if (researchLine.equals("Physical Risk: Market Risk"))
                        assertTestCase.assertEquals(dashboardPage.marketRiskDescription.getText(), Descriptions.MARKET_RISK_DESCRIPTION_HEATMAP);
                    else
                        assertTestCase.assertEquals(dashboardPage.supplyChainRiskDescription.getText(), Descriptions.SUPPLY_CHAIN_RISK_DESCRIPTION_HEATMAP);
                    break;
                case "Physical Risk Management":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Weak", "Physical Risk Management verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Advanced", "Physical Risk Management verified");
                    assertTestCase.assertEquals(dashboardPage.physicalRiskManagementDescription.getText(), Descriptions.PHYSICAL_RISK_MANAGEMENT_DESCRIPTION_HEATMAP);
                    break;
                case "Temperature Alignment":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Above 2°C", "Temperature Alignment verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(4).getText(), "No Info", "Temperature Alignment verfied");
                    assertTestCase.assertEquals(dashboardPage.temperatureAlignmentDescription.getText(), Descriptions.TEMPERATURE_ALIGNMENTS_DESCRIPTION_HEATMAP);
                    break;
                case "Carbon Footprint":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Intense", "Carbon Footprint verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Moderate", "Carbon Footprint verified");
                    assertTestCase.assertEquals(dashboardPage.carbonFootprintDescription.getText(), Descriptions.CARBON_FOOTPRINT_DESCRIPTION_HEATMAP);
                    break;
                case "Green Share Assessment":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "None", "Green Share Assessment verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Major", "Green Share Assessment verified");
                    assertTestCase.assertEquals(dashboardPage.greenShareAssessmentDescription.getText(), Descriptions.GREEN_SHARE_ASSESSMENT_DESCRIPTION_HEATMAP);
                    break;
                case "Brown Share Assessment":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "20-100%", "Brown Share Assessment verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "0%", "Brown Share Assessment verified");
                    assertTestCase.assertEquals(dashboardPage.brownShareAssessmentDescription.getText(), Descriptions.BROWN_SHARE_ASSESSMENT_DESCRIPTION_HEATMAP);
                    break;
            }
        }
    }

    @Test(groups = {"dashboard", "ui", "regression", "smoke"})
    @Xray(test = {8185, 7973,})
    public void heatMapAPIUIVerification() {
        LoginPage loginPage = new LoginPage();
        //Trying to login with only Physical Risk Entitilment User
        WebElement portfolioSelectionButton = Driver.getDriver().findElement(By.id("button-holdings"));
        BrowserUtils.wait(3);
        if (portfolioSelectionButton.getAttribute("title").equals("Sample Portfolio"))
            loginPage.clickOnLogout();
        Driver.getDriver().manage().deleteAllCookies();
        Driver.getDriver().navigate().refresh();

        loginPage.loginWithParams("esg-test1@outlook.com", "Helloworld21");
        BrowserUtils.wait(2);
        Driver.getDriver().findElement(By.id("RegSector-test-id-1")).click();
        selectOptionFromFiltersDropdown("as_of_date", "June 2022");


        APIController apiController = new APIController();
        DashboardPage dashboardPage = new DashboardPage();
        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        //Getting access token for API connection and setting as token.
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);

        BrowserUtils.wait(2);
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLine(i);
            BrowserUtils.wait(1);
            String color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("background-color")).asHex();
            String researchLine = dashboardPage.heatMapResearchLines.get(i).getText();
            researchLine = researchLine.substring(researchLine.indexOf(":") + 1).trim();
            System.out.println("researchLine = " + researchLine);

            //Api connection for verification
            APIHeatMapSinglePayload apiHeatMapSinglePayload = new APIHeatMapSinglePayload("all", "all", "06", "2022", apiController.apiResourceMapperWithoutphysicalriskinit(researchLine.trim()));
            String portfolio_id = "00000000-0000-0000-0000-000000000000";
            Response response = apiController.getHeatMapResponse(portfolio_id, researchLine, apiHeatMapSinglePayload);
            List<APIHeatMapResponse> list = Arrays.asList(response.getBody().as(APIHeatMapResponse[].class));
            System.out.println("dashboardPage.heatMapYAxisIndicatorsAPI.size() = " + dashboardPage.heatMapYAxisIndicatorsAPI.size());
            switch (researchLine) {
                case "Operations Risk":
                case "Supply Chain Risk":
                case "Market Risk":
                case "Physical Risk Management":
                    List<List<String>> UIValues = new ArrayList<>();
                    List<List<String>> APIValues = new ArrayList<>();
                    for (int x = 0; x < dashboardPage.heatMapYAxisIndicatorsAPI.size(); x++) {
                        String scoreRangeUI = dashboardPage.heatMapYAxisIndicatorsAPI.get(x).getText(); //80-100
                        String totalInvestmentUI = dashboardPage.heatMapYAxisIndicatorPercentagesAPI.get(x).getText(); //14.59%
                        UIValues.add(Arrays.asList(scoreRangeUI, totalInvestmentUI));

                    }
                    for (int y = 0; y < list.get(0).getY_axis_total_invct_pct().size(); y++) {
                        String scoreRangeAPI = String.valueOf(list.get(0).getY_axis_total_invct_pct().get(y).getResearch_line_1_score_range());//80-100
                        String totalInvestmentAPI = String.valueOf(list.get(0).getY_axis_total_invct_pct().get(y).getTotal_investment()) + "%"; //14.59%
                        APIValues.add(Arrays.asList(scoreRangeAPI, totalInvestmentAPI));
                    }
                    BrowserUtils.wait(3);
                    System.out.println("UIValues = " + UIValues);
                    System.out.println("APIValues = " + APIValues);
                    assertTestCase.assertTrue(UIValues.containsAll(APIValues));
                    assertTestCase.assertTrue(APIValues.containsAll(UIValues));
                    break;
            }
        }
    }

    public void selectOptionFromFiltersDropdown(String dropdown, String option) {

        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "regions":
                elementTitle = "list-region";
                break;
            case "sectors":
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                elementTitle = "list-asOfDate";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectOptionFromDropdown()");
        }
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));
        Actions actions = new Actions(Driver.getDriver());
        //select provided option from picked dropdown
        try {
            for (WebElement each : options) {
                if (each.getText().equals(option)) {
                    each.click();
                    //actions.moveToElement(each).pause(1000).click(each).sendKeys(Keys.ESCAPE).build().perform();

                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    public void verifyCells() {
        DashboardPage dashboardPage = new DashboardPage();
        int counter = 0;
        for (int i = 0; i < dashboardPage.heatMapYAxisIndicators.size(); i++) {
            for (int j = 0; j < dashboardPage.heatMapXAxisIndicators.size(); j++) {
                System.out.println(counter);
                System.out.println("j " + j);
                BrowserUtils.scrollTo(dashboardPage.heatMapCells.get(counter));
                String expPercentage = dashboardPage.heatMapCells.get(counter).getText();
                dashboardPage.heatMapCells.get(counter).click();
                counter++;
                if (expPercentage.equals("0%")) continue;
                String actPercentage = dashboardPage.heatMapWidgetTitle.getText();
                actPercentage = actPercentage.substring(actPercentage.indexOf("\n") + 1);
                actPercentage = actPercentage.substring(0, actPercentage.indexOf(" "));
                assertTestCase.assertEquals(actPercentage, expPercentage,
                        "Cell percentage vs Widget percentage verified");
                assertTestCase.assertEquals(dashboardPage.heatMapWidgetYIndicator.getText()
                        , dashboardPage.heatMapYAxisIndicators.get(i).getText(),
                        "Cell Y Axis indicator vs Widget Y axis indicator verified");
                assertTestCase.assertEquals(dashboardPage.heatMapWidgetXIndicator.getText()
                        , dashboardPage.heatMapXAxisIndicators.get(j).getText(),
                        "Cell X Axis indicator vs Widget X axis indicator verified");
            }
        }
    }

    public boolean verifyResearchLineDistributionCategories(String cat, int num) {
        Map<String, Integer> expCategories = new HashMap<>();
        expCategories.put("Overall ESG Score", 4);
        expCategories.put("Operations Risk", 5);
        expCategories.put("Market Risk", 5);
        expCategories.put("Supply Chain Risk", 5);
        expCategories.put("Physical Risk Management", 4);
        expCategories.put("Temperature Alignment", 5);
        expCategories.put("Carbon Footprint", 4);
        expCategories.put("Green Share Assessment", 4);
        expCategories.put("Brown Share Assessment", 3);


        if (expCategories.get(cat) == num) return true;
        else {
            System.out.println("Actual Category Number for " + cat + " = " + num);
            System.out.println("Expected Category Number for " + cat + " = " + expCategories.get(cat));
            return false;
        }
    }
}