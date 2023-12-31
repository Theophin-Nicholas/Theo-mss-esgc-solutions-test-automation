package com.esgc.Dashboard.UI.Tests;

import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.Descriptions;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.Dashboard.API.APIModels.APIHeatMapResponse;
import com.esgc.Dashboard.API.APIModels.APIHeatMapSinglePayload;
import com.esgc.Dashboard.API.Controllers.DashboardAPIController;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.Utilities.*;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.support.Color;
import org.testng.annotations.Test;

import java.text.DecimalFormat;
import java.util.*;

import static com.esgc.Utilities.Groups.*;

//TODO synchronization issues
public class DashboardHeatMapEntityListTests extends UITestBase {
    @Test(groups = {DASHBOARD, UI, SMOKE})
    @Xray(test = {1705, 1873, 1668, 3793, 4760, 4346, 3947, 4450, 5015})
    public void verifyEntityListTest() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        //Navigate to the heatmap section
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        BrowserUtils.wait(2);
        //Verify the entity list - Entity list should be displaying no records
        assertTestCase.assertFalse(dashboardPage.isHeatMapEntityListDrawerDisplayed(), "Check if Entity List is not displayed");
        //        "Verified the widget doesn't show anything before a cell is selected.");
        System.out.println("heatMapNoEntityWidget displayed..");
        // dashboardPage.heatMapResearchLines.get(0).click();
        //Click on any cell from the heatmap
        /*The entity list is updated and the records (companies) which meet the criteria are now displayed in descending
        order based on percentage of investment on the same.*/
        BrowserUtils.wait(5);
        verifyHeatMapCells();
        System.out.println("verifyCells(); passed");

        for (int i = 2; i < dashboardPage.heatMapResearchLines.size(); i++) {
            //De-select a research line from the heatmap section
            dashboardPage.heatMapResearchLines.get(i).click();
            System.out.println("Checking for = " + dashboardPage.heatMapResearchLines.get(i).getText());
            //Click on any cell from the heatmap
            verifyHeatMapCells();
        }
    }

    @Test(groups = {DASHBOARD, UI, SMOKE})
    @Xray(test = {4931})
    public void verifyHeatMapDrawer() {
        //Verify that user is able to close drawer by clicking outside of drawer
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        //Navigate to the heatmap section
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        BrowserUtils.wait(3);
        verifyDrawer();
    }

    @Test(groups = {DASHBOARD, UI, REGRESSION})
    @Xray(test = {1344, 1598, 1396, 1815, 1600, 1623, 1632, 1370, 4191, 4461, 4884, 4452})
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

        dashboardPage.refreshCurrentWindow();
        dashboardPage.waitForDataLoadCompletion();
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        //Verify the chart's selectable research lines
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLineOnHeatMap(i);
            assertTestCase.assertTrue(verifyResearchLineDistributionCategories(
                            dashboardPage.heatMapYAxisIndicatorTitle.getText(),
                            dashboardPage.heatMapYAxisIndicators.size()),
                    dashboardPage.heatMapYAxisIndicatorTitle.getText() + " category name and number is verified");
            System.out.println(dashboardPage.heatMapYAxisIndicatorTitle.getText() + " category name and number is verified");
        }

    }

    @Test(groups = {DASHBOARD, UI, REGRESSION})
    @Xray(test = {3492, 1250, 1957, 1672})
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

    @Test(groups = {DASHBOARD, UI, REGRESSION})
    @Xray(test = {1861, 1677, 1897})
    public void verifyHeatMapCellMatrix() {
        //Verify the percentage displayed in the axes
        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.scrollTo(dashboardPage.heatMapXAxisIndicators.get(0));
        BrowserUtils.wait(8);
        if (dashboardPage.heatMapXAxisIndicators.size() > 0) {
            int x = 0, y = 0;
            x = dashboardPage.heatMapXAxisIndicators.size();
            y = dashboardPage.heatMapYAxisIndicators.size();
            System.out.println("x value: " + x + " and y value: " + y);
            int expMatrixSize = x * y;
            int actMatrixSize = dashboardPage.heatMapCells.size();
            assertTestCase.assertEquals(actMatrixSize, expMatrixSize, "Heat Map Matrix Size verified for X and Y axis");

        } else {
            assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.size(), dashboardPage.heatMapCells.size(),
                    "Heat Map Matrix Size verified for Y axis");
        }

    }

    @Test(groups = {DASHBOARD, UI, REGRESSION, SMOKE})
    @Xray(test = {4801, 4564, 1708})
    public void getSelectedResearchLineNameFromHeatMap() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();

        BrowserUtils.wait(2);
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLineOnHeatMap(i);
            BrowserUtils.wait(2);
            String color = Color.fromString(dashboardPage.heatMapResearchLines.get(i).getCssValue("background-color")).asHex();
            String researchLine = dashboardPage.heatMapResearchLines.get(i).getText();
            String selectedResearchLine = researchLine;
            String expectedAxisTitle = dashboardPage.heatMapYAxisIndicatorTitle.getText();

            if (researchLine.startsWith("Physical Risk: ")) {
                selectedResearchLine = selectedResearchLine.substring(selectedResearchLine.indexOf(":") + 2);
            }
            assertTestCase.assertEquals(selectedResearchLine, expectedAxisTitle, "Heat Map Research Line Title Verification");

            //TODO update method and get categories dynamically
            //APIController controller = new APIController();
            //controller.getResearchLineRangesAndScoreCategories()
            System.out.println("researchLine = " + researchLine);
            switch (researchLine) {
                case "Physical Risk: Operations Risk":
                case "Physical Risk: Market Risk":
                case "Physical Risk: Supply Chain Risk":
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "80-100", "Physical Risk Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "60-79", "Physical Risk Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "40-59", "Physical Risk Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "20-39", "Physical Risk Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(4).getText(), "0-19", "Physical Risk Category Verified");
                    if (researchLine.equals("Physical Risk: Operations Risk"))
                        assertTestCase.assertEquals(dashboardPage.operationRiskDescription.getText(), Descriptions.OPERATIONS_RISK_DESCRIPTION_HEATMAP);
                    else if (researchLine.equals("Physical Risk: Market Risk"))
                        assertTestCase.assertEquals(dashboardPage.marketRiskDescription.getText(), Descriptions.MARKET_RISK_DESCRIPTION_HEATMAP);
                    else
                        assertTestCase.assertEquals(dashboardPage.supplyChainRiskDescription.getText(), Descriptions.SUPPLY_CHAIN_RISK_DESCRIPTION_HEATMAP);
                    break;
                case "Physical Risk Management":
                    BrowserUtils.wait(5);
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Weak", "Physical Risk Management Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "Limited", "Physical Risk Management Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "Robust", "Physical Risk Management Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Advanced", "Physical Risk Management Category Verified");
                    assertTestCase.assertEquals(dashboardPage.physicalRiskManagementDescription.getText(), Descriptions.PHYSICAL_RISK_MANAGEMENT_DESCRIPTION_HEATMAP);
                    break;
                case "Temperature Alignment":
                    BrowserUtils.wait(5);
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Above 2°C", "Temperature Alignment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "2°C", "Temperature Alignment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "Below 2°C", "Temperature Alignment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Well Below 2°C", "Temperature Alignment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(4).getText(), "No Info", "Temperature Alignment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.temperatureAlignmentDescription.getText(), Descriptions.TEMPERATURE_ALIGNMENTS_DESCRIPTION_HEATMAP);
                    break;
                case "Carbon Footprint":
                    BrowserUtils.wait(8);
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Intense", "Carbon Footprint Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "High", "Carbon Footprint Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "Significant", "Carbon Footprint Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Moderate", "Carbon Footprint Category Verified");
                    assertTestCase.assertEquals(dashboardPage.carbonFootprintDescription.getText(), Descriptions.CARBON_FOOTPRINT_DESCRIPTION_HEATMAP);
                    break;
                case "Green Share Assessment":
                    BrowserUtils.wait(8);
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "None", "Green Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "Minor", "Green Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "Significant", "Green Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(3).getText(), "Major", "Green Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.greenShareAssessmentDescription.getText(), Descriptions.GREEN_SHARE_ASSESSMENT_DESCRIPTION_HEATMAP);
                    break;
                case "Brown Share Assessment":
                    BrowserUtils.wait(8);
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(0).getText(), "Major Involvement", "Brown Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(1).getText(), "Minor Involvement", "Brown Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.heatMapYAxisIndicators.get(2).getText(), "No Involvement", "Brown Share Assessment Category Verified");
                    assertTestCase.assertEquals(dashboardPage.brownShareAssessmentDescription.getText(), Descriptions.BROWN_SHARE_ASSESSMENT_DESCRIPTION_HEATMAP);
                    break;
            }
        }
    }

    //Entitlements
    @Test(groups = {DASHBOARD, UI, REGRESSION, ENTITLEMENTS})
    @Xray(test = {3955, 5020})
    public void heatMapAPIUIEntitlementsVerification() {
        APIController apiController = new APIController();
        DashboardPage dashboardPage = new DashboardPage();
        DashboardAPIController dashboardAPIController = new DashboardAPIController();
        LoginPage loginPage = new LoginPage();
        loginPage.loginWithParams(Environment.PHYSICAL_RISK_USERNAME, Environment.PHYSICAL_RISK_PASSWORD);

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        dashboardPage.clickFiltersDropdown();
        dashboardPage.selectOptionFromFiltersDropdown("as_of_date", "June 2022");

        //Getting access token for API connection and setting as token.
        getExistingUsersAccessTokenFromUI();

        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        for (int i = 0; i < dashboardPage.heatMapResearchLines.size(); i++) {
            dashboardPage.selectOneResearchLineOnHeatMap(i);
            BrowserUtils.wait(7);
            String researchLine = dashboardPage.heatMapResearchLines.get(i).getText();
            assertTestCase.assertTrue(dashboardPage.verifySelectedResearchLineForHeatMap(researchLine), researchLine + " is selected alone");
            researchLine = researchLine.substring(researchLine.indexOf(":") + 1).trim();
            System.out.println("researchLine = " + researchLine);

            //Api connection for verification
            APIHeatMapSinglePayload apiHeatMapSinglePayload = new APIHeatMapSinglePayload("all", "all", "06", "2022", apiController.apiResourceMapperWithoutphysicalriskinit(researchLine.trim()));
            String portfolio_id = "00000000-0000-0000-0000-000000000000";
            Response response = dashboardAPIController.getHeatMapResponse(portfolio_id, researchLine, apiHeatMapSinglePayload);
            List<APIHeatMapResponse> list = Arrays.asList(response.getBody().as(APIHeatMapResponse[].class));
            System.out.println("dashboardPage.heatMapYAxisIndicatorsAPI.size() = " + dashboardPage.heatMapYAxisIndicatorsAPI.size());
            DecimalFormat df = new DecimalFormat("#.00");

            List<List<String>> UIValues = new ArrayList<>();
            List<List<String>> APIValues = new ArrayList<>();
            for (int x = 0; x < dashboardPage.heatMapYAxisIndicatorsAPI.size(); x++) {
                String scoreRangeUI = dashboardPage.heatMapYAxisIndicatorsAPI.get(x).getText(); //80-100
                String totalInvestmentUI = dashboardPage.heatMapYAxisIndicatorPercentagesAPI.get(x).getText(); //14.59%
                UIValues.add(Arrays.asList(scoreRangeUI, totalInvestmentUI));

            }
            for (int y = 0; y < list.get(0).getY_axis_total_invct_pct().size(); y++) {
                if (list.get(0).getY_axis_total_invct_pct().get(y).getResearch_line_1_score_range().equals("N/A"))
                    continue;
                String scoreRangeAPI = String.valueOf(list.get(0).getY_axis_total_invct_pct().get(y).getResearch_line_1_score_range());//80-100
                String totalInvestmentAPI = df.format(list.get(0).getY_axis_total_invct_pct().get(y).getTotal_investment()) + "%"; //14.59%
                APIValues.add(Arrays.asList(scoreRangeAPI, totalInvestmentAPI));
            }
            //BrowserUtils.wait(3);
            System.out.println("UIValues = " + UIValues);
            System.out.println("APIValues = " + APIValues);
            assertTestCase.assertTrue(UIValues.containsAll(APIValues));
            assertTestCase.assertTrue(APIValues.containsAll(UIValues));
        }
        loginPage.clickOnLogout();
    }

    public void verifyHeatMapCells() {
        DashboardPage dashboardPage = new DashboardPage();
        int counter = 0;
        for (int i = 0; i < dashboardPage.heatMapYAxisIndicators.size(); i++) {
            for (int j = 0; j < dashboardPage.heatMapXAxisIndicators.size(); j++) {
                System.out.println("Checking cell: " + (i + 1) + ":" + (j + 1));
                BrowserUtils.scrollTo(dashboardPage.heatMapCells.get(counter));
                String expPercentage = dashboardPage.heatMapCells.get(counter).getText();
                BrowserUtils.wait(1);
                dashboardPage.heatMapCells.get(counter).click();
                counter++;
                if (expPercentage.equals("0%")) continue;

                BrowserUtils.waitForVisibility(dashboardPage.heatMapWidgetTitle, 10);
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

    public void verifyDrawer() {
        DashboardPage dashboardPage = new DashboardPage();
        int counter = 0;
        for (int i = 0; i < dashboardPage.heatMapYAxisIndicators.size(); i++) {
            for (int j = 0; j < dashboardPage.heatMapXAxisIndicators.size(); j++) {
                System.out.println(counter);
                System.out.println("j " + j);
                BrowserUtils.scrollTo(dashboardPage.heatMapCells.get(counter));
                System.out.println("#1");
                String expPercentage = dashboardPage.heatMapCells.get(counter).getText();
                System.out.println("#2");
                BrowserUtils.wait(1);
                System.out.println("dashboardPage.heatMapCells.get(counter).getText() = " + dashboardPage.heatMapCells.get(counter).getText());
                dashboardPage.heatMapCells.get(counter).click();
                System.out.println("#3");
                counter++;
                if (expPercentage.equals("0%")) continue;
                BrowserUtils.waitForVisibility(dashboardPage.heatMapWidgetTitle, 10);
                System.out.println("#4");
                Driver.getDriver().findElement(By.xpath("//div[normalize-space()='Analyze Companies by Range']")).click();
                System.out.println("#5");
            }
        }
    }

    public boolean verifyResearchLineDistributionCategories(String cat, int num) {
        Map<String, Integer> expCategories = new HashMap<>();

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

    @Test(groups = {DASHBOARD, UI, REGRESSION})
    @Xray(test = {3360})
    public void verifyHeatMapTitleAndDefaultSelection() {

        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.waitForVisibility(dashboardPage.verifyPortfolioName, 20);

        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        BrowserUtils.wait(5);
        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));

        //Verify Heat Map Title
        assertTestCase.assertTrue(dashboardPage.verifyHeatMapTitle("Analyze Companies by Range"),
                "Verified the widget doesn't show anything before a cell is selected.");

        //Verify the entity list - Entity list should be displaying no records
        assertTestCase.assertFalse(dashboardPage.isHeatMapEntityListDrawerDisplayed(),
                "Verified the widget doesn't show anything before a cell is selected.");

        //verify Operations Risk research line is the first one in row
        assertTestCase.assertEquals(dashboardPage.heatMapResearchLines.get(0).getText(), "Physical Risk: Operations Chain Risk",
                "Verified Operations Risk research line is first in row");

        //verify Operations Risk research line selected by default
        assertTestCase.assertTrue(dashboardPage.verifySelectedResearchLineForHeatMap("Physical Risk: Operations Chain Risk"),
                "Verified Operations Risk research line is selected by default");
    }


    @Test(groups = {DASHBOARD, UI, REGRESSION})
    @Xray(test = {3337})
    public void verifySelectTwoStaticTextInHeatMap() {
        DashboardPage dashboardPage = new DashboardPage();
        BrowserUtils.waitForVisibility(dashboardPage.verifyPortfolioName, 20);

        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        BrowserUtils.scrollTo(dashboardPage.heatMapResearchLines.get(0));
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(dashboardPage.validateSelectTwoStaticText(), "Verify Select Two static text is present");

        LoginPage login = new LoginPage();
        login.clickOnLogout();
        login.entitlementsLogin(EntitlementsBundles.CLIMATE_GOVERNANCE);
        BrowserUtils.waitForVisibility(dashboardPage.verifyPortfolioName, 20);

        if (!dashboardPage.verifyPortfolioName.getText().equalsIgnoreCase("Sample Portfolio"))
            dashboardPage.selectPortfolioByNameFromPortfolioSelectionModal("Sample Portfolio");

        BrowserUtils.wait(5);
        assertTestCase.assertFalse(dashboardPage.validateSelectTwoStaticText(), "Verify Select Two static text is present");


    }

}