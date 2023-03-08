package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

/**
 * Created by Tarun
 */
public class UnderLyingDataMetrics extends UITestBase {

    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if underlying data metrics is available",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {416, 417, 1323, 1324, 3413, 2934, 2939,8943,8950,5662})
    public void verifyIfUnderlyingDataMetricsIsAvailable(String page) {
        if (page.equals("Physical Risk Management") || page.equals("Temperature Alignment")) {
            throw new SkipException(page+" doesn't have Underlying Data Metrics");
        } else {
            ResearchLinePage researchLinePage = new ResearchLinePage();
            researchLinePage.navigateToResearchLine(page);
            BrowserUtils.wait(5);
            researchLinePage.waitForDataLoadCompletion();
            researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();


            test.info("Navigated to " + page + " Page");
            researchLinePage.checkIfUnderLyingDataMetricsIsAvailable(page);
            assertTestCase.assertTrue(researchLinePage.checkIfUnderLyingDataMetricsIsAvailable(page), "Underlying Data Metrics Verified");
            test.pass("User is on " + page + " Page");
        }

    }

    @Test(groups = {REGRESSION, UI},
            description = "Verify if underlying data metrics is available for Physical Risk Hazards")
    @Xray(test = {5954})
    public void verifyTooltipIsDisplayedOnHoverForUnderlyingDataMetrics_PhysicalRiskHazard() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("Physical Risk Hazards");
        researchLinePage.waitForDataLoadCompletion();
        assertTestCase.assertTrue(researchLinePage.checkIfUnderLyingDataMetricsIsAvailable("Physical Risk Hazards"), "Underlying Data Metrics Verified", 5954);

        List<String> underlyingDataList = Arrays.asList("Country of Sales", "Weather Sensitivity", "Country of Origin", "Resource Demand");
        for (String underlyingDataIndicator : underlyingDataList) {
            assertTestCase.assertTrue(researchLinePage.checkIfTooltipSIsDisplayedOnHover(underlyingDataIndicator), "Tool tip was displayed on hovering", 5954);
        }
        test.pass("User is on Physical Risk Hazards Page");
    }

    @Test(groups = {REGRESSION, UI},
            description = "Verify if underlying data metrics is available for Green Share Assessment")
    @Xray(test = {2761,5689})
    public void verifyTooltipIsDisplayedOnHoverForUnderlyingDataMetrics_GreenShare() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("Green Share Assessment");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.waitForDataLoadCompletion();
        assertTestCase.assertTrue(researchLinePage.checkIfUnderLyingDataMetricsIsAvailable("Green Share Assessment"), "Underlying Data Metrics Verified");
        assertTestCase.assertTrue(researchLinePage.checkIfTooltipSIsDisplayedForGreenShareDataMetrics(), "Tool tip was displayed on hovering");

    }

    @Test(groups = {REGRESSION, UI},
            description = "Verify if underlying data metrics is available for Brown Share Assessment")
    @Xray(test = {2761,5688,2939})
    public void verifyTooltipIsDisplayedOnHoverForUnderlyingDataMetrics_BrownShare() {
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.selectSamplePortfolioFromPortfolioSelectionModal();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToResearchLine("Brown Share Assessment");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.waitForDataLoadCompletion();
        assertTestCase.assertTrue(researchLinePage.checkIfUnderLyingDataMetricsIsAvailable("Brown Share Assessment"), "Underlying Data Metrics Verified");
        researchLinePage.checkIfTooltipSIsDisplayedForBrownShareDataMetrics();
        assertTestCase.assertTrue(researchLinePage.checkIfTooltipSIsDisplayedForBrownShareDataMetrics(), "Tool tip was displayed on hovering");

    }
}
