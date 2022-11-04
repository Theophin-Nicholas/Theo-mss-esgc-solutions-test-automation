package com.esgc.Tests.DataValidation.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.APIModels.PortoflioAnalysisModels.LeadersAndLaggards;
import com.esgc.APIModels.PortoflioAnalysisModels.LeadersAndLaggardsWrapper;
import com.esgc.DBModels.ESGLeaderANDLaggers;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Reporting.CustomAssertion;
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

    @Test(groups = {"regression", "heatmap"}, dataProvider = "Research Lines")
    @Xray(test = {10030})
    public void verifyUpdatesTableForAllRLs(String portfolioId, String researchLine, String year, String month) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        PortfolioQueries portfolioQueries = new PortfolioQueries();
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        researchLinePage.selectResearchLineFromDropdown(researchLine);
        int companiesCountInUpdatesTableUI = researchLinePage.getCompaniesCountFromUpdatesTable();
        int companiesCountInUpdatesTableDB = portfolioQueries.getCompanyUpdatesCount(portfolioId, researchLine, year, month);
        assertTestCase.assertEquals(companiesCountInUpdatesTableDB,companiesCountInUpdatesTableUI,"Verify "+researchLine+" updates count");

    }

    @DataProvider(name = "Research Lines")
    public Object[][] availableResearchLinesForDashboard() {

        return new Object[][]{
                {"00000000-0000-0000-0000-000000000000","Physical Risk Hazards","2022","10"},
                {"00000000-0000-0000-0000-000000000000","Physical Risk Management","2022","10"},
                {"00000000-0000-0000-0000-000000000000","Carbon Footprint","2022","10"},
                {"00000000-0000-0000-0000-000000000000","Brown Share Assessment","2022","10"},
                {"00000000-0000-0000-0000-000000000000","Green Share Assessment","2022","10"},
        };
    }


}
