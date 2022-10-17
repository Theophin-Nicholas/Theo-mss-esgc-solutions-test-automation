package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.Pages.DashboardPage;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntitySearchTests extends DataValidationTestBase {

    @Test(groups = {"regression", "ui"})
    @Xray(test = {9976})
    public void testSearchBoxIsGivingCorrectLastUpdateDate_DashboardPage() {
        String entityName = "Apple, Inc.";
        DashboardPage dashboardPage = new DashboardPage();
        dashboardPage.searchIconPortfolioPage.click();
        dashboardPage.searchBarOfPortfolio.sendKeys(entityName);
        String uiLastUpdatedDate = dashboardPage.getLastUpdatedDateContainsSearchKeyWord(entityName).replace("Last Update: ", "");
        String dbLastUpdatedDate = portfolioQueries.getLastUpdatedDateOfEntity(entityName);
        assertTestCase.assertEquals(dbLastUpdatedDate, uiLastUpdatedDate, "Verify last updated date from UI to DB");
    }

    @Test(groups = {"regression", "ui"})
    @Xray(test = {9977})
    public void testSearchBoxIsGivingCorrectLastUpdateDate_PortfolioAnalysis() {
        String entityName = "Apple, Inc.";
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.searchIconPortfolioPage.click();
        researchLinePage.searchBarOfPortfolio.sendKeys(entityName);
        String uiLastUpdatedDate = researchLinePage.getLastUpdatedDateContainsSearchKeyWord(entityName).replace("Last Update: ", "");
        String dbLastUpdatedDate = portfolioQueries.getLastUpdatedDateOfEntity(entityName);
        assertTestCase.assertEquals(dbLastUpdatedDate, uiLastUpdatedDate, "Verify last updated date from UI to DB");
    }
}