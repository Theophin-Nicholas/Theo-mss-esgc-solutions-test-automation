package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortoflioAnalysisModels.LeadersAndLaggards;
import com.esgc.APIModels.PortoflioAnalysisModels.LeadersAndLaggardsWrapper;
import com.esgc.DBModels.ESGLeaderANDLaggers;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Database.PortfolioQueries;
import com.esgc.Utilities.PortfolioUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.esgc.Utilities.PortfolioUtilities.distinctByKey;

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
