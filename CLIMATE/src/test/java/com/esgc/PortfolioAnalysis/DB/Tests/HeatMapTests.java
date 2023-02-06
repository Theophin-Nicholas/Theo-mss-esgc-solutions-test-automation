package com.esgc.PortfolioAnalysis.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;

public class HeatMapTests extends DataValidationTestBase {

    @Test(groups = {REGRESSION, "heatmap"}, dataProviderClass = DataProviderClass.class, dataProvider = "Heat Map Research Lines")
    @Xray(test = {10030})
    //TODO this test case is related to Updates Data Validation not Heat Map
    public void verifyUpdatesTableForAllRLs(String portfolioId, String researchLine, String year, String month) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        researchLinePage.selectResearchLineFromDropdown(researchLine);
        int companiesCountInUpdatesTableUI = researchLinePage.getCompaniesCountFromUpdatesTable();
        int companiesCountInUpdatesTableDB = portfolioQueries.getCompanyUpdates(portfolioId, researchLine, year, month).size();
        assertTestCase.assertEquals(companiesCountInUpdatesTableDB,companiesCountInUpdatesTableUI,"Verify "+researchLine+" updates count");

    }


}
