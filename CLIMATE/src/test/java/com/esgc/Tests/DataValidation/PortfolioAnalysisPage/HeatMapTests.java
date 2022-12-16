package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class HeatMapTests extends DataValidationTestBase {

    @Test(groups = {"regression", "heatmap"}, dataProviderClass = DataProviderClass.class, dataProvider = "Heat Map Research Lines")
    @Xray(test = {10030})
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
