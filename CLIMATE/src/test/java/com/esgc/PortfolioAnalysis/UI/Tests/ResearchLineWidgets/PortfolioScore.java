package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.PortfolioAnalysis.UI.Pages.PhysicalRiskPages.PhysicalRiskManagementPages.PhysicalRiskManagementPage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class PortfolioScore extends UITestBase {

    /*
  US JIRA # - ESGCA-450
  Acceptance Criteria:
  when a user selects a portfolio
  an api call should be made to /api/portfolios/PORTFOLIOIDHERE/physicalriskmgmt/score with that portfolio id

  when a user selects a portfolio
  the score section should reflect the data in the api for that particular portfolio
  (load mask should be present when data is loading)
  (here is the mapping for each column):

      first column -> ranking
      second column -> score
      third column -> name

*/
    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Portfolio Score Table is Displayed as Expected", dataProvider = "3 Research Lines")
    public void verifyScoreWidgetAndRadarGraph(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        String title = "Score";

        test.info("Navigated to " + page + " Page");
        researchLinePage.navigateToResearchLine(page);
        Assert.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed(page));
        test.pass("User is on " + page + " Page");

       // Assert.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title));
        Assert.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed(title));
        Assert.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title));

        test.pass("Portfolio Score Table displayed");
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();
        String score = physicalRiskManagementPage.getPortfolioScoreFromUI();
        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioScoreCategoryColorIsCorrect(page));

        Assert.assertTrue(researchLinePage.checkRadarGraph());
        test.pass("Table rows columns and content verified");
    }

    @DataProvider(name = "3 Research Lines")
    public Object[][] dpMethod() {

        return new Object[][]{
                {"Physical Risk Management"}
                //{"Energy Transition Management"},
                //{"TCFD Strategy"},
        };
    }
    //##################################################################################


    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify Score Widget for risk research lines",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {551, 2506, 2657, 2253, 6712, 2504, 2655})
    public void verifyPortfolioScore(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        test.info("Navigated to " + page + " Page");
        researchLinePage.navigateToResearchLine(page);
        assertTestCase.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed(page), "Portfolio Score Title Verification");
        test.pass("User is on " + page + " Page");

        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioScoreCategoryColorIsCorrect(page), "Portfolio Score Category Color Verification");
        assertTestCase.assertTrue(researchLinePage.checkScoreWidget(page), "Portfolio Score Widget is Displayed");

        switch (page) {
            case "Carbon Footprint":
                //Portfolio Score
                assertTestCase.assertTrue(researchLinePage.portfolioScore.isDisplayed(), "Portfolio Score: Carbon Footprint Title Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioScoreValue.isDisplayed(), "Portfolio Score: Carbon Footprint Value Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioCarbonIntensityText.isDisplayed(), "Portfolio Score: Carbon Intensity Title Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioCarbonIntensityValue.isDisplayed(), "Portfolio Score: Carbon Intensity Value Displayed");

                //Portfolio Coverage
                assertTestCase.assertTrue(researchLinePage.portfolioCoverageCompaniesChartTitle.isDisplayed(), "Portfolio Coverage: Companies Title Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioCoverageCompanies.isDisplayed(), "Portfolio Coverage: Companies Value Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioCoverageInvestmentChartTitle.isDisplayed(), "Portfolio Coverage: Investment Title Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioCoverageInvestment.isDisplayed(), "Portfolio Coverage: Investment Value Displayed");
                test.pass("Carbon Footprint Portfolio Coverage and Score is displayed");
                break;
            case "Green Share Assessment":
                List<String> portfolioPortfolioScoreTexts = new ArrayList<>();
                int sizeofPortfolioDistribution = researchLinePage.greenSharePortfolioScoreTexts.size();
                for (int i = 0; i < sizeofPortfolioDistribution; i++) {
                    portfolioPortfolioScoreTexts.add(researchLinePage.greenSharePortfolioScoreTexts.get(i).getText());
                    assertTestCase.assertTrue(researchLinePage.greenSharePortfolioScoreTexts.get(i).isDisplayed(), "Portfolio Score: Text and Value Displayed");
                }
                assertTestCase.assertTrue(researchLinePage.greenSharePortfolioScoreTexts.get(0).getText().contentEquals("Of Investments In Companies Offering Green Solutions"), "Portfolio Score: Text Displayed");

                assertTestCase.assertTrue(researchLinePage.portfolioScoreGreenShareValue.isDisplayed(), "Portfolio Coverage: Value Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioScoreGreenShareInvestmentValue.isDisplayed(), "Portfolio Coverage: Investment Score Displayed");
                assertTestCase.assertTrue(researchLinePage.portfolioScoreGreenShareCompaniesValue.isDisplayed(), "Portfolio Coverage: Company Score Displayed");
                test.pass("Green Share Assessments Portfolio Coverage and Score is displayed");
                break;
            case "Brown Share Assessment":
                //Portfolio Score
                List<String> portfolioPortfolioScoreTexts1 = new ArrayList<>();
                int sizeofPortfolioDistribution1 = researchLinePage.greenSharePortfolioScoreTexts.size();
                for (int i = 0; i < sizeofPortfolioDistribution1; i++) {
                    assertTestCase.assertTrue(researchLinePage.greenSharePortfolioScoreTexts.get(i).isDisplayed(), "Portfolio Coverage: Value Displayed");
                }

                break;
            default:
                test.fail("Failed to get any Research line");
                System.out.println("Failed to get any Research line");
                break;
        }
    }

    public void portfolioScoreCoverage() {

    }

    @DataProvider(name = "No graph research lines")
    public Object[][] riskResearchLines() {
        return new Object[][]{
                //{"Operations Risk"},
                //{"Market Risk"},
                //{"Supply Chain Risk"},
                {"Physical Risk Hazards"},
                {"Carbon Footprint"},
                {"Brown Share Assessment"},
                {"Green Share Assessment"}
        };
    }


    /*
    US JIRA # - ESGCA-450
    Acceptance Criteria:
    if a user changes pages and goes back to physical risk management page
    without updating the portfolio filter there should be no api call to load data
 */

    @Test(groups = {"regression", "ui"},
            description = "Verify Portfolio Score is not changed when User navigates another page and come back to Physical Management Page without any changes")

    public void verifyPortfolioScoreNotUpdated() {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        String title = "Score";
        researchLinePage.navigateToPageFromMenu("Portfolio Analysis");
        test.info("Navigated to Portfolio Analysis Page");
        researchLinePage.navigateToResearchLine("Carbon Footprint");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "Widget is loading");

        assertTestCase.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed("Carbon Footprint"), "Page Subtitle Verification");
        test.pass("User is on Carbon Footprint Page");

        assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed(title), "Portfolio Score Title Verification");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title), "Portfolio Score Table Verification");

        test.pass("Portfolio Score Table displayed");

        test.info("Navigate to Green Share Assessment Page");

        researchLinePage.navigateToResearchLine("Green Share Assessment");

        test.info("User is on Green Share Assessment page");
        test.info("Navigate back to Carbon Footprint Page");

        researchLinePage.navigateToResearchLine("Carbon Footprint");

        test.info("User navigated back to Carbon Footprint page");

        Assert.assertFalse(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title));
        test.pass("Portfolio Score is not updated as expected");
    }

       /*
    US JIRA # - ESGCA-450
    Acceptance Criteria:
    if a user changes pages
    and updates the portfolio filter
    then goes back to physical risk management page
    there should be a api call made for the new portfolio selected
 */

    @Test(groups = {"regression", "ui"},
            description = "Verify Portfolio Score Table is changed if User change page and select another portfolio")
    @Xray(test = {1257})
    public void verifyPortfolioScoreUpdatedWhenPageIsRefreshed() {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        String title = "Score";

        test.info("Navigated to Green Share Assessment Page");
        researchLinePage.navigateToResearchLine("Green Share Assessment");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "Loading mask displayed on Portfolio Score");
        assertTestCase.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed("Green Share Assessment"), "Navigated to research line");
        test.pass("User is on Green Share Assessment Page");


        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title), "Portfolio Score Table displayed");

        test.pass("Portfolio Score Table displayed");

        test.info("Navigate to Carbon Footprint Page");

        researchLinePage.navigateToResearchLine("Carbon Footprint");

        test.info("User is on Carbon Footprint page");
        test.info("User changes portfolio");

        //select random portfolio
        researchLinePage.selectRandomOptionFromFiltersDropdown("portfolio_name");

        test.info("Navigate back to Green Share Assessment Page");
        researchLinePage.navigateToResearchLine("Green Share Assessment");

        test.info("User navigated back to Green Share Assessment page");

        test.info("Check if load mask is on Portfolio Score");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "After refresh, loading mask is displayed on Portfolio Score");
        test.pass("Portfolio Score is updated as expected");
    }

       /*
    US JIRA # - ESGCA-450
    Acceptance Criteria:
    when a user uploads a portfolio
    that new portfolio should be available in both the portfolio drop down and benchmark dropdown
    and if that new uploaded portfolio is then selected
    the score should change to reflect the portfolio.
 */

    //Temporary test!!!!
   /* @Test(groups = {},
            description = "Verify Portfolio Score Table is changed to N/A if user select a date which is not applicable")
    public void verifyPortfolioScoreNAByDate() {
        UploadPage uploadPage = new UploadPage();
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();
        String title = "Score";

        test.info("Navigated to Physical RiskManagement Page");
        physicalRiskManagementPage.navigateToResearchLine("Physical Risk Management");

        Assert.assertTrue(physicalRiskManagementPage.checkIfPhysicalRiskManagementSubtitleIsDisplayed());
        test.pass("User is on Physical Risk Management Page");

        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioLoadMaskIsDisplayed(title));
        Assert.assertTrue(physicalRiskManagementPage.checkIfWidgetTitleIsDisplayed(title));
        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioTableIsDisplayed(title));
        //Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioScoreCategoryColorIsCorrect());

        test.pass("Portfolio Score Table displayed");

        test.info("User changes as-of-date");

        //select random portfolio
        physicalRiskManagementPage.selectOptionFromFiltersDropdown("as_of_date", "November 2020");

        test.info("Check if load mask is on Portfolio Score");
        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioLoadMaskIsDisplayed(title));
        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioTableIsDisplayed(title));
        //Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioScoreCategoryColorIsCorrect());

     /*   List<Map<String, WebElement>> scoreTable = physicalRiskManagementPage.getPortfolioScoreFromUI();

        for (Map<String, WebElement> elementMap : scoreTable) {
            elementMap.entrySet().forEach(stringWebElementEntry -> System.out.println(stringWebElementEntry.getKey() + "=" + stringWebElementEntry.getValue().getText()) );
            /**
             *      * firstColumn->ranking ex:Weak
             *      * secondColumn->score ex:10
             *      * thirdColumn->name ex:Results


            //check if all ranks are N/A
            Assert.assertTrue(elementMap.get("firstColumn").getText().equals("N/A"));

            //check if all scores are N/A
            Assert.assertTrue(elementMap.get("firstColumn").getText().equals("N/A"));
        }
        test.pass("Portfolio Score is not acceptable for November 2020");
    } */


    /*
    US JIRA # - ESGCA-450
    Acceptance Criteria:
    when a user uploads a portfolio
    that new portfolio should be available in both the portfolio drop down and benchmark dropdown
    and if that new uploaded portfolio is then selected
    the score should change to reflect the portfolio.
 */

    @Test(groups = {"regression", "ui", "robot_dependency"},
            description = "Verify Portfolio Score Table is changed if User uploads a new portfolio and selects that new portfolio from dropdown")
    public void verifyPortfolioScoreUpdated() {
        UploadPage uploadPage = new UploadPage();
        ResearchLinePage researchLinePage = new ResearchLinePage();

        String title = "Score";

        test.info("Navigated to Carbon Footprint Page");
        researchLinePage.navigateToResearchLine("Carbon Footprint");
        //assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "Loading mask displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfResearchLineTitleIsDisplayed("Carbon Footprint"), "Research line displayed");
        test.pass("User is on Carbon Footprint Page");

        assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed(title), "Portfolio Score Title Displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title), "Portfolio Score Table displayed");

        test.pass("Portfolio Score Table displayed");

        //existing lists under dropdowns
        List<String> portfoliosUnderPortfolioFilter = researchLinePage.getPortfolioNames();
        List<String> portfoliosUnderBenchmarkFilter = researchLinePage.getOptionsAsStringListFromFiltersDropdown("benchmark");

        test.info("Upload new portfolio");

        String portfolio_name = uploadPage.uploadGoodPortfolio();
        test.pass(portfolio_name + " uploaded successfully");
        BrowserUtils.wait(3);
        uploadPage.closePopUp();

        //new dropdown list after upload
        List<String> portfoliosUnderPortfolioFilterNew = researchLinePage.getPortfolioNames();

        //remove all existing portfolios from new list
        //in this case we should have only uploaded portfolio's name
        portfoliosUnderPortfolioFilter.forEach(portfoliosUnderPortfolioFilterNew::remove);
        System.out.println(portfoliosUnderPortfolioFilterNew);
        assertTestCase.assertEquals(portfoliosUnderPortfolioFilterNew.size(), 1, "New Portfolio Imported and displayed in portfolio selection modal");
        assertTestCase.assertEquals(portfoliosUnderPortfolioFilterNew.get(0).trim(), portfolio_name, "Imported Portfolio name verification");

        test.pass(portfolio_name + " is under portfolio filter dropdown");

        //new dropdown list after upload
        List<String> portfoliosUnderBenchmarkFilterNew = researchLinePage.getOptionsAsStringListFromFiltersDropdown("benchmark");
        //remove all existing portfolios from new list
        //in this case we should have only uploaded portfolio's name
        portfoliosUnderBenchmarkFilter.forEach(portfoliosUnderBenchmarkFilterNew::remove);
        assertTestCase.assertEquals(portfoliosUnderBenchmarkFilterNew.size(), 1, "Uploaded portfolio is displayed in Benchmark dropdown");
        assertTestCase.assertEquals(portfoliosUnderBenchmarkFilterNew.get(0), portfolio_name, "Uploaded portfolio reflected to Benchmark");
        test.pass(portfolio_name + " is under benchmark filter dropdown");
        test.pass("Portfolio Score is updated as expected");
    }
}
