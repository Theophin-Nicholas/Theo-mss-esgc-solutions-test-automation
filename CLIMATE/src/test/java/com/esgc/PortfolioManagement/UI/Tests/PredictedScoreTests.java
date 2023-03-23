package com.esgc.PortfolioManagement.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Dashboard.UI.Pages.DashboardPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.PortfolioManagement.DB.DBQueries.PredictedScoreQueries;
import com.esgc.PortfolioManagement.UI.Pages.PortfolioManagementPage;
//import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.esgc.Utilities.Groups.*;

public class PredictedScoreTests extends UITestBase {


    @Test(groups = {REGRESSION, UI, ESG})
    @Xray(test = {11892, 11909, 11910})
    public static void validatePredictedScoredCompanies() {

        String portfolioName = "EsgWithPredictedScores";
        CustomAssertion assertTestCase = new CustomAssertion();
        PortfolioManagementPage pmPage = new PortfolioManagementPage();
        DashboardPage dashboardPage = new DashboardPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        researchLinePage.clickMenu();
        researchLinePage.portfolioSettings.click();
        researchLinePage.link_UploadNew.click();
        dashboardPage.clickBrowseFile();
        BrowserUtils.wait(3);
        RobotRunner.selectFileToUpload(PortfolioFilePaths.portfolioWithPredictedScores());
        BrowserUtils.wait(5);
        dashboardPage.clickUploadButton();
        BrowserUtils.wait(3);
        BrowserUtils.waitForVisibility(dashboardPage.successMessagePopUP, 30);
        dashboardPage.waitForDataLoadCompletion();
        BrowserUtils.wait(10);
        pmPage.selectPortfolio(portfolioName);

        assertTestCase.assertTrue(researchLinePage.verifyEntitiesWithPredictedScoresInYellow_PA(pmPage.portfolioTableRows),"Verify Entities with Predicted Scores are displaying in Yellow color in Portfolio table");
        assertTestCase.assertTrue(researchLinePage.verifyEntitiesWithPredictedScoresAreNotClickable_PA(pmPage.portfolioTableRows),"Verify Entities with Predicted Scores are not clickable in Portfolio table");

       /* RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId(portfolioName);*/

        String portfolioId = APIUtilities.getPortfolioId(portfolioName);
        PredictedScoreQueries queries = new PredictedScoreQueries();
        ArrayList<String> dbPredictedScoredCompanies = queries.getPredictedScoredCompanies(portfolioId);
        ArrayList<String> uiPredictedScoredCompanies = pmPage.getPredictedScoredCompanies();
        ArrayList<String> apiPredictedScoredCompanies = pmPage.getPredictedScoredCompaniesFromApi(portfolioId);

        assertTestCase.assertTrue(dbPredictedScoredCompanies.containsAll(uiPredictedScoredCompanies), "UI Predicted Companies are not in DB");
        assertTestCase.assertTrue(apiPredictedScoredCompanies.containsAll(uiPredictedScoredCompanies), "UI Predicted Companies are not in API");

    }
}
