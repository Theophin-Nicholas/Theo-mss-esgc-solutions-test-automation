package com.esgc.RegulatoryReporting.UI.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.DateTimeUtilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EUTaxonomyPage extends RegulatoryReportingPage {

    @FindBy(xpath = "//label[.//*[text()='EU Taxonomy'] and .//input[@type='radio']]")
    public WebElement EUTaxonomyOption;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]")
    public List<WebElement> EUTaxonomyTableRows;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//input[@type='checkbox']")
    public List<WebElement> EUTaxonomyTablePortfolioCheckBoxes;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//span[@title]")
    public List<WebElement> EUTaxonomyTablePortfolioNames;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//div[2]//span")
    public List<WebElement> EUTaxonomyTablePortfolioUpdateDates;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//div[3]//span")
    public List<WebElement> EUTaxonomyTableCoverages;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//div[@class='MuiFormControl-root'][1]//input[@type='text']")
    public List<WebElement> EUTaxonomyTableDerivatives;

    @FindBy(xpath = "//*[text()='Select Portfolios']/following-sibling::div[.//input]//div[@class='MuiFormControl-root'][2]//input[@type='text']")
    public List<WebElement> EUTaxonomyTableCash;


    public boolean isEUTaxonomyOptionIsAvailable() {
        return isElementDisplayed(EUTaxonomyOption);
    }

    public void verifyEUTaxonomyHeaders() {
        BrowserUtils.waitForVisibility(tableHeaders, 15);
        List<String> actualHeaders = tableHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
        System.out.println("actualHeaders = " + actualHeaders);
        List<String> expectedHeaders = Arrays.asList("Portfolio", "Last Uploaded", "Coverage % Investment", "Non-Sovereign Derivatives", "Cash and liquidities");
        System.out.println("expectedHeaders = " + expectedHeaders);
        assertTestCase.assertEquals(actualHeaders, expectedHeaders, "EU Taxonomy Header verification", 3835);
    }

    public void verifyEUTaxonomyTableContent() {
        System.out.println("EUTaxonomyTableRows.size() = " + EUTaxonomyTableRows.size());
        for (int i = 0; i < EUTaxonomyTableRows.size(); i++) {
            WebElement checkBox = EUTaxonomyTablePortfolioCheckBoxes.get(i);
            String portfolioName = EUTaxonomyTablePortfolioNames.get(i).getText();
            String portfolioUpdateDate = EUTaxonomyTablePortfolioUpdateDates.get(i).getText();
            String coverage = EUTaxonomyTableCoverages.get(i).getText();
            if (coverage.equals("NA")) coverage = "0%";
            coverage = coverage.substring(0, coverage.indexOf("%"));
            double coverageValue = Double.parseDouble(coverage);
            double derivativeValue = Double.parseDouble(EUTaxonomyTableDerivatives.get(i).getAttribute("value"));
            double cashValue = Double.parseDouble(EUTaxonomyTableCash.get(i).getAttribute("value"));

            assertTestCase.assertTrue(portfolioName.length() > 0, "Portfolio Name validation");
            assertTestCase.assertTrue(DateTimeUtilities.isValidDate(portfolioUpdateDate), "Portfolio Update Date validation");
            assertTestCase.assertTrue(coverageValue >= 0d && coverageValue <= 100d, "Coverage Validation");
            if (coverageValue == 0d) {
                assertTestCase.assertTrue(!checkBox.isEnabled(), "If coverage is 0, check box should be disabled", 3846);
            }
            assertTestCase.assertTrue(derivativeValue >= 0, "Non-Sovereign Derivatives value validation", 3814);
            assertTestCase.assertTrue(cashValue >= 0, "Cash and liquidities value validation", 3814);
        }
    }

    public void verifyDefaultReportingOptionsForEUTaxonomy() {
        assertTestCase.assertTrue(isInterimReportsOptionDisplayed(), "Interim reports toggle is displayed", 3849);
        assertTestCase.assertFalse(isInterimReportsSelected(), "Interim reports toggle disabled", 3849);

        assertTestCase.assertTrue(isAnnualReportsOptionDisplayed(), "Annual reports toggle is displayed", 3849);
        assertTestCase.assertFalse(isAnnualReportsSelected(), "Annual reports toggle is disabled", 3849);

        assertTestCase.assertTrue(isUseLatestDataOptionDisplayed(), "Use latest data toggle is displayed", 3849);
        assertTestCase.assertTrue(isUseLatestDataSelected(), "Use latest data toggle is enabled", 3849);
    }

    public void verifyLatestDataOptionCannotTurnedOffForEUTaxonomy() {
        clickOnUseLatestData();
        assertTestCase.assertTrue(isUseLatestDataSelected(), "Use latest data toggle should not be disabled", 3816);
    }

    public double getNonSovereignDerivativesValueByPortfolioName(String name) {
        int index = getPortfolioList().indexOf(name.trim());
        System.out.println("Index = " + index);
        return Double.parseDouble(EUTaxonomyTableDerivatives.get(index).getAttribute("value"));
    }

    public double getCashAndLiquiditiesValueByPortfolioName(String name) {
        int index = getPortfolioList().indexOf(name.trim());
        System.out.println("Index = " + index);
        return Double.parseDouble(EUTaxonomyTableCash.get(index).getAttribute("value"));
    }
}
