package com.esgc.RegulatoryReporting.UI.Pages;

import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SFDRPage extends RegulatoryReportingPage {

    public void verifySFDRHeaders() {
        List<String> actualHeaders = tableHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> expectedHeaders = Arrays.asList("Portfolio", "Last Uploaded", "Coverage % Investment", "Reporting for");
        assertTestCase.assertEquals(actualHeaders, expectedHeaders, "SFDR Header verification", 3809);
    }

    public void verifyDefaultReportingOptionsForSFDR() {
        assertTestCase.assertTrue(isInterimReportsOptionDisplayed(), "Interim reports toggle is displayed", 3809);
        assertTestCase.assertTrue(isInterimReportsSelected(), "Interim reports toggle disabled", 3809);

        assertTestCase.assertTrue(isAnnualReportsOptionDisplayed(), "Annual reports toggle is displayed", 3809);
        assertTestCase.assertFalse(isAnnualReportsSelected(), "Annual reports toggle is disabled", 3809);

        assertTestCase.assertTrue(isUseLatestDataOptionDisplayed(), "Use latest data toggle is displayed", 3809);
        assertTestCase.assertFalse(isUseLatestDataSelected(), "Use latest data toggle is enabled", 3809);
    }
}
