package com.esgc.Tests.UI.DashboardPage.Widgets;

import com.esgc.Pages.DashboardPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DashboardUITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class PerformanceChart extends DashboardUITestBase {
//TODO portfolio selection should be updated since some portfolios do not have enough data
    @Test(groups = {"ui", "dashboard", "smoke", "regression"})
    @Xray(test = {2051, 4273, 4448, 2064, 7838, 8683, 8684, 8690})
    public void verifyPerformanceChartsAreDisplayed() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.selectRandomPortfolioFromPortfolioSelectionModal();
        BrowserUtils.wait(3);
        test.info("Check if Performance Charts are Displayed");

        BrowserUtils.scrollTo(dashboardPage.performanceChart);

        List<String> expectedColumnNames =
                Arrays.asList("Company", "% Investment", "Overall ESG Score", "Total Critical Controversies",
                        "Highest Risk Hazard", "Facilities Exposed to High Risk/Red Flag",
                        "Physical Risk Management", "Temperature Alignment",
                        "Carbon Footprint (tCO2eq)", "Green Share Assessment", "Brown Share Assessment");


        dashboardPage.clickAndSelectAPerformanceChart("Leaders");

        List<String> actualColumnNames = dashboardPage.getPerformanceChartColumnNames();
        int sizeOfTable = dashboardPage.getPerformanceChartRowCount();
        double actualTotalInvestment = dashboardPage.calculateTotalInvestmentFromPerformanceChart();
        double expectedTotalInvestment = dashboardPage.getTotalInvestmentInPerformanceChart();
        System.out.println("actualColumnNames = " + actualColumnNames);
        System.out.println("expectedColumnNames = " + expectedColumnNames);
        test.info("Switched to Leaders");
        assertTestCase.assertTrue(sizeOfTable <= 10, "10 companies are listed");
        System.out.println("expectedTotalInvestment = " + expectedTotalInvestment);
        System.out.println("actualColumnNames = " + actualColumnNames);
        assertTestCase.assertEquals(actualColumnNames, expectedColumnNames, "Performance Chart Verified", 6233, 6232);
        assertTestCase.assertEquals(actualTotalInvestment, expectedTotalInvestment, "Total Investments are matching", 2066);

        dashboardPage.clickAndSelectAPerformanceChart("Laggards");

        actualColumnNames = dashboardPage.getPerformanceChartColumnNames();
        sizeOfTable = dashboardPage.getPerformanceChartRowCount();
        actualTotalInvestment = dashboardPage.calculateTotalInvestmentFromPerformanceChart();
        expectedTotalInvestment = dashboardPage.getTotalInvestmentInPerformanceChart();

        test.info("Switched to Laggards");
        assertTestCase.assertTrue(sizeOfTable <= 10, "max 10 companies are listed");
        assertTestCase.assertEquals(actualColumnNames, expectedColumnNames, "Laggards Performance Chart Verified");
        assertTestCase.assertEquals(actualTotalInvestment, expectedTotalInvestment, "Total Investments are matching", 2066);

        dashboardPage.clickAndSelectAPerformanceChart("Largest Holdings");

        actualColumnNames = dashboardPage.getPerformanceChartColumnNames();
        sizeOfTable = dashboardPage.getPerformanceChartRowCount();
        actualTotalInvestment = dashboardPage.calculateTotalInvestmentFromPerformanceChart();
        expectedTotalInvestment = PortfolioUtilities.round(dashboardPage.getTotalInvestmentInPerformanceChart(), 2);

        test.info("Switched to Largest Holdings");
        assertTestCase.assertTrue(sizeOfTable <= 10, "max 10 companies are listed");
        assertTestCase.assertEquals(actualColumnNames, expectedColumnNames, "Largest Holdings Performance Chart Verified");
        assertTestCase.assertEquals(actualTotalInvestment, expectedTotalInvestment, "Total Investments are matching", 2066);
    }

    @Test(groups = {"ui", "dashboard", "regression"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2041, 2043, 2048, 2052, 2067, 3131, 3132, 3152, 3153, 4375, 4376, 4380, 6235, 7111, 7112, 7114})
    public void verifyPerformanceChartScoreCategoriesUnderColumns(String researchLine) {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");
        dashboardPage.selectRandomPortfolioFromPortfolioSelectionModal();
        test.info("Check Score categories in Performance Charts");

        List<String> headersToClick =
                Arrays.asList("Facilities Exposed to High Risk/Red Flag", "Overall ESG Score", "Physical Risk Management", "Temperature Alignment",
                        "Carbon Footprint (tCO2eq)", "Green Share Assessment", "Brown Share Assessment");

        List<String> expectedCategories = dashboardPage.getScoreCategoriesByResearchLine(researchLine);
        System.out.println("expectedCategories = " + expectedCategories);

        //############### LEADERS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Leaders");
        test.info("Switched to Leaders");

        List<WebElement> categories;
        List<String> actualCategories;
        List<String> redColoredCategories;

        for (String header : headersToClick) {
            dashboardPage.clickHeaderInPerformanceChart(header);
            assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                    "Header Highlighted Verification: " + header);
            assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                    "Cells under header Highlighted Verification: " + header);
            categories = dashboardPage.getPerformanceChartColumnElementsByResearchLine(researchLine)
                    .stream().filter(e -> !e.getText().equals("")).collect(Collectors.toList());
            actualCategories = categories
                    .stream().map(WebElement::getText)
                    .collect(Collectors.toList());

            if (researchLine.equals("Carbon Footprint"))
                actualCategories = actualCategories.stream().map(e -> e.substring(0, e.indexOf("\n"))).collect(Collectors.toList());

            System.out.println("actualCategories = " + actualCategories);
            for (String actualCategory : actualCategories) {
                assertTestCase.assertTrue(expectedCategories.contains(actualCategory), "Verify category is in expected list\n" +
                        "category:" + actualCategory + "\n" +
                        "expected list:" + expectedCategories);
            }

            List<String> sortedCategories = dashboardPage.getPerformanceChartColumnElementsByResearchLine(header)
                    .stream().filter(e -> !e.getText().equals(""))
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            if (header.equals("Carbon Footprint (tCO2eq)"))
                sortedCategories = sortedCategories.stream().map(e -> e.substring(0, e.indexOf("\n"))).collect(Collectors.toList());

            System.out.println("Leaders sortedCategories = " + sortedCategories);
            boolean isOrderedAsExpected = isSortedBestToWorst(sortedCategories, expectedCategories);

            assertTestCase.assertTrue(isOrderedAsExpected, "Verify if Categories are sorted for leaders as expected Best to Worst");

            //RED COLOR CHECK
            //Worst Category should be Red colored
            redColoredCategories = categories
                    .stream().filter(e -> e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("#b31717") ||
                            e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("rgba(179, 23, 23, 1)"))
                    .map(WebElement::getText).collect(Collectors.toList());

            //if % facilities exposed is more than 0 then highest risk hazards is red colored
            if (researchLine.equals("Physical Risk Hazards")) {
                List<Integer> facilitiesExposedPercentages = dashboardPage.getPerformanceChartColumnElementsByColumnIndex(6)
                        .stream().map(WebElement::getText)
                        .filter(e -> !e.equals(""))
                        .map(e -> Integer.parseInt(e.replace("%", "")))
                        .filter(e -> e > 0).collect(Collectors.toList());

                System.out.println("facilitiesExposedPercentages = " + facilitiesExposedPercentages);

                assertTestCase.assertEquals((Integer) redColoredCategories.size(), (Integer) facilitiesExposedPercentages.size(),
                        "Verify if % facilities exposed is more than 0 then highest risk hazards is red colored\n" +
                                "red colored categories size:" + redColoredCategories.size() + "\n" +
                                "% facilities exposed more than 0 size:" + facilitiesExposedPercentages.size());

            } else {
                for (String redColoredCategory : redColoredCategories) {
                    //Latest category is the worst category
                    String worstCategory = expectedCategories.get(expectedCategories.size() - 1);

                    if (researchLine.equals("Carbon Footprint"))
                        redColoredCategory = redColoredCategory.substring(0, redColoredCategory.indexOf("\n"));

                    assertTestCase.assertEquals(redColoredCategory, worstCategory, "Verify worst category is in red color\n" +
                            "red colored category:" + redColoredCategory + "\n" +
                            "worst category:" + worstCategory);
                }
            }

        }

        //############### LAGGARDS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Laggards");
        test.info("Switched to Laggards");
        for (String header : headersToClick) {
            dashboardPage.clickHeaderInPerformanceChart(header);
            assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                    "Header Highlighted Verification: " + header);
            assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                    "Cells under header Highlighted Verification: " + header);
            categories = dashboardPage.getPerformanceChartColumnElementsByResearchLine(researchLine)
                    .stream().filter(e -> !e.getText().equals("")).collect(Collectors.toList());
            actualCategories = categories
                    .stream().map(WebElement::getText)
                    .collect(Collectors.toList());

            if (researchLine.equals("Carbon Footprint"))
                actualCategories = actualCategories.stream().map(e -> e.substring(0, e.indexOf("\n"))).collect(Collectors.toList());
            for (String actualCategory : actualCategories) {
                assertTestCase.assertTrue(expectedCategories.contains(actualCategory), "Verify category is in expected list\n" +
                        "category:" + actualCategory + "\n" +
                        "expected list:" + expectedCategories);
            }
            if (researchLine.equals("Physical Risk Hazards"))
                actualCategories = dashboardPage.getPerformanceChartColumnElementsByColumnIndex(6)
                        .stream().map(WebElement::getText)
                        .filter(e -> !e.equals(""))
                        .map(e -> Integer.parseInt(e.replace("%", "")))
                        .filter(e -> e > 0).map(String::valueOf).collect(Collectors.toList());

            List<String> sortedCategories = dashboardPage.getPerformanceChartColumnElementsByResearchLine(header)
                    .stream().filter(e -> !e.getText().equals(""))
                    .map(WebElement::getText)
                    .collect(Collectors.toList());

            if (header.equals("Carbon Footprint (tCO2eq)"))
                sortedCategories = sortedCategories.stream().map(e -> e.substring(0, e.indexOf("\n"))).collect(Collectors.toList());
            System.out.println("Laggards sortedCategories = " + sortedCategories);
            boolean isOrderedAsExpected = isSortedWorstToBest(sortedCategories, expectedCategories);

            assertTestCase.assertTrue(isOrderedAsExpected, "Verify if Categories are sorted for laggards as expected Worst to Best");

            //RED COLOR CHECK
            redColoredCategories = categories
                    .stream().filter(e -> e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("#b31717") ||
                            e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("rgba(179, 23, 23, 1)"))
                    .map(WebElement::getText).collect(Collectors.toList());

            //if % facilities exposed is more than 0 then highest risk hazards is red colored
            if (researchLine.equals("Physical Risk Hazards")) {
                List<Integer> facilitiesExposedPercentages = dashboardPage.getPerformanceChartColumnElementsByColumnIndex(6)
                        .stream().map(WebElement::getText)
                        .filter(e -> !e.equals(""))
                        .map(e -> Integer.parseInt(e.replace("%", "")))
                        .filter(e -> e > 0).collect(Collectors.toList());

                System.out.println("facilitiesExposedPercentages = " + facilitiesExposedPercentages);

                assertTestCase.assertEquals(redColoredCategories.size(), facilitiesExposedPercentages.size(),
                        "Verify if % facilities exposed is more than 0 then highest risk hazards is red colored\n" +
                                "red colored categories size:" + redColoredCategories.size() + "\n" +
                                "% facilities exposed more than 0 size:" + facilitiesExposedPercentages.size());

            } else {
                for (String redColoredCategory : redColoredCategories) {
                    //Latest category is the worst category
                    String worstCategory = expectedCategories.get(expectedCategories.size() - 1);

                    if (researchLine.equals("Carbon Footprint"))
                        redColoredCategory = redColoredCategory.substring(0, redColoredCategory.indexOf("\n"));

                    assertTestCase.assertEquals(redColoredCategory, worstCategory, "Verify worst category is in red color\n" +
                            "red colored category:" + redColoredCategory + "\n" +
                            "worst category:" + worstCategory);
                }
            }
        }

        //############### LARGEST HOLDINGS TABLE

        dashboardPage.clickAndSelectAPerformanceChart("Largest Holdings");

        test.info("Switched to Largest Holdings");

        assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted("% Investment"),
                "Header Highlighted Verification: % Investment");

        assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted("% Investment"),
                "Cells under header Highlighted Verification: % Investment");

        categories = dashboardPage.getPerformanceChartColumnElementsByResearchLine(researchLine)
                .stream().filter(e -> !e.getText().equals("")).collect(Collectors.toList());

        actualCategories = categories
                .stream().map(WebElement::getText)
                .collect(Collectors.toList());

        for (String actualCategory : actualCategories) {
            if (researchLine.equals("Carbon Footprint"))
                actualCategory = actualCategory.substring(0, actualCategory.indexOf("\n"));
            assertTestCase.assertTrue(expectedCategories.contains(actualCategory), "Verify category is in expected list\n" +
                    "category:" + actualCategory + "\n" +
                    "expected list:" + expectedCategories);
        }

        //RED COLOR CHECK
        redColoredCategories = categories
                .stream().filter(e -> e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("#b31717") ||
                        e.findElement(By.xpath(".//*[text()]")).getCssValue("color").equals("rgba(179, 23, 23, 1)"))
                .map(WebElement::getText).collect(Collectors.toList());

        //if % facilities exposed is more than 0 then highest risk hazards is red colored
        if (researchLine.equals("Physical Risk Hazards")) {
            List<Integer> facilitiesExposedPercentages = dashboardPage.getPerformanceChartColumnElementsByColumnIndex(6)
                    .stream().map(WebElement::getText)
                    .filter(e -> !e.equals(""))
                    .map(e -> Integer.parseInt(e.replace("%", "")))
                    .filter(e -> e > 0).collect(Collectors.toList());

            System.out.println("facilitiesExposedPercentages = " + facilitiesExposedPercentages);

            assertTestCase.assertEquals(redColoredCategories.size(), facilitiesExposedPercentages.size(),
                    "Verify if % facilities exposed is more than 0 then highest risk hazards is red colored\n" +
                            "red colored categories size:" + redColoredCategories.size() + "\n" +
                            "% facilities exposed more than 0 size:" + facilitiesExposedPercentages.size());

        } else {
            for (String redColoredCategory : redColoredCategories) {
                //Latest category is the worst category
                String worstCategory = expectedCategories.get(expectedCategories.size() - 1);

                if (researchLine.equals("Carbon Footprint"))
                    redColoredCategory = redColoredCategory.substring(0, redColoredCategory.indexOf("\n"));

                assertTestCase.assertEquals(redColoredCategory, worstCategory, "Verify worst category is in red color\n" +
                        "red colored category:" + redColoredCategory + "\n" +
                        "worst category:" + worstCategory);
            }
        }
    }


    @Test(groups = {"ui", "dashboard", "regression"})
    @Xray(test = {4459})
    public void verifyPerformanceChartSizes() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        test.info("Views by Dropdown options in Performance Charts");

        String defaultOptionInDropdown = dashboardPage.getSelectedOptionFromPerformanceChartDropdown();

        assertTestCase.assertEquals(defaultOptionInDropdown, "Show Top 10", "Verify Default Size from Dropdown\n" +
                "actual:" + defaultOptionInDropdown + "\n" +
                "expected:10");


        List<String> expectedDropdownOptions = Arrays.asList("10", "25", "50", "75", "100");

        List<String> actualDropdownOptions = dashboardPage.getPerformanceChartDropdownOptions();

        assertTestCase.assertEquals(actualDropdownOptions, expectedDropdownOptions, "Verify Dropdown options:\n" +
                "actual:" + actualDropdownOptions + "\n" +
                "expected:" + expectedDropdownOptions);

        for (String option : expectedDropdownOptions) {
            if (!option.equals("10"))
                dashboardPage.selectPerformanceChartViewSizeFromDropdown(option);
            Integer actualCount = dashboardPage.getPerformanceChartRowCount();
            Integer expectedCount = Integer.valueOf(option);

            assertTestCase.assertEquals(actualCount, expectedCount, "Verify Selected Size vs count of records in the table\n" +
                    "actual:" + actualCount + "\n" +
                    "expected:" + expectedCount);

        }
        dashboardPage.selectPerformanceChartViewSizeFromDropdown("10");


        //############### LEADERS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Leaders");

        test.info("Switched to Leaders");


        for (String option : expectedDropdownOptions) {
            if (!option.equals("10"))
                dashboardPage.selectPerformanceChartViewSizeFromDropdown(option);
            Integer actualCount = dashboardPage.getPerformanceChartRowCount();
            Integer expectedCount = Integer.valueOf(option);

            assertTestCase.assertEquals(actualCount, expectedCount, "Verify Selected Size vs count of records in the table\n" +
                    "actual:" + actualCount + "\n" +
                    "expected:" + expectedCount);

        }
        dashboardPage.selectPerformanceChartViewSizeFromDropdown("10");


        //############### LAGGARDS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Laggards");


        test.info("Switched to Laggards");

        for (String option : expectedDropdownOptions) {
            if (!option.equals("10"))
                dashboardPage.selectPerformanceChartViewSizeFromDropdown(option);
            Integer actualCount = dashboardPage.getPerformanceChartRowCount();
            Integer expectedCount = Integer.valueOf(option);

            assertTestCase.assertEquals(actualCount, expectedCount, "Verify Selected Size vs count of records in the table\n" +
                    "actual:" + actualCount + "\n" +
                    "expected:" + expectedCount);

        }
        dashboardPage.selectPerformanceChartViewSizeFromDropdown("10");

    }


    @Test(groups = {"ui", "dashboard", "regression"})
    @Xray(test = {7115})
    public void verifyPerformanceChartSelectedColumnRemainsSameForOtherTables() {
        DashboardPage dashboardPage = new DashboardPage();

        dashboardPage.navigateToPageFromMenu("Dashboard");
        test.info("Navigated to Dashboard Page");

        test.info("Check Score categories in Performance Charts");

        List<String> headersToClick =
                Arrays.asList("Facilities Exposed to High Risk/Red Flag", "Overall ESG Score", "Physical Risk Management", "Temperature Alignment",
                        "Carbon Footprint (tCO2eq)", "Green Share Assessment", "Brown Share Assessment");


        //############### LEADERS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Leaders");


        test.info("Switched to Leaders");

        //Select Random Column
        Collections.shuffle(headersToClick);
        String header = headersToClick.get(0);


        dashboardPage.clickHeaderInPerformanceChart(header);

        assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                "Header Highlighted Verification: " + header);

        assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                "Cells under header Highlighted Verification: " + header);


        //############### LAGGARDS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Laggards");


        test.info("Switched to Laggards");

        //As default header should be already selected
        assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                "Header Highlighted Verification: " + header);

        assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                "Cells under header Highlighted Verification: " + header);


        //Select Random Column again
        Collections.shuffle(headersToClick);
        header = headersToClick.get(0);


        dashboardPage.clickHeaderInPerformanceChart(header);

        assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                "Header Highlighted Verification: " + header);

        assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                "Cells under header Highlighted Verification: " + header);

        //############### LEADERS TABLE
        dashboardPage.clickAndSelectAPerformanceChart("Leaders");


        test.info("Switched to Leaders");

        //As default header should be already selected
        assertTestCase.assertTrue(dashboardPage.checkIfPerformanceChartHeaderIsHighlighted(header),
                "Header Highlighted Verification: " + header);

        assertTestCase.assertTrue(dashboardPage.checkIfAllCellsUnderSelectedPerformanceChartHeaderColumnAreHighlighted(header),
                "Cells under header Highlighted Verification: " + header);

    }


    public static boolean isSortedBestToWorst(List<String> listToCheck, List<String> listToCompare) {
        if (listToCheck.size() == 1) {
            return true;
        }

        Iterator<String> iter = listToCheck.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (listToCompare.indexOf(previous) > listToCompare.indexOf(current)) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    public static boolean isSortedWorstToBest(List<String> listToCheck, List<String> listToCompare) {
        if (listToCheck.size() == 1) {
            return true;
        }

        Iterator<String> iter = listToCheck.iterator();
        String current, previous = iter.next();
        while (iter.hasNext()) {
            current = iter.next();
            if (listToCompare.indexOf(previous) < listToCompare.indexOf(current)) {
                return false;
            }
            previous = current;
        }
        return true;
    }
}
