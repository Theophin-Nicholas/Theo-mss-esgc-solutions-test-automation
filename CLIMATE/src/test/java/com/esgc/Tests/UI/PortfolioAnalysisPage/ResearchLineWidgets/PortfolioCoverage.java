package com.esgc.Tests.UI.PortfolioAnalysisPage.ResearchLineWidgets;

import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class PortfolioCoverage extends UITestBase {


    /*
    US JIRA # - ESGCA-489
    Acceptance Criteria:
    when a user selects a portfolio
    an api call should be made to /api/portfolios/PORTFOLIOIDHERE/physicalrisk/coverage
    with that portfolio id

    when a user selects a portfolio
    the score section should reflect the data in the api for that particular portfolio
    (load mask should be present when data is loading)
    (here is the formulas for the chart):

    Companies

    light green: 100 - (num/denominator x 100)
    dark green: (num/denominator x 100)

    Investments

    light green: 100 - investment
    dark green: investment

 */
    @Test(groups = {"regression", "ui", "smoke"},
            description = "Verify if Portfolio Coverage Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2420, 6729, 1690})
    public void verifyPortfolioCoverage(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        String title = "Coverage";

        researchLinePage.navigateToResearchLine(page);

        test.info("Navigated to " + page + " Page");

       // assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "Portfolio Coverage Loading");
        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "Navigated to Research Line");
        test.pass("User is on " + page + " Page");


       // assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed(title), "Portfolio Coverage is Displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title), "Portfolio Coverage is displayed", 553);

        test.pass(title + " Table/Chart displayed");

        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioCoverageChartTitlesAreDisplayed(), "Portfolio Coverage Labels verified");

        System.out.printf("%10s %10s", "Companies", "Investment");
        String companies = researchLinePage.getCoverageCompanies();

        String investment = researchLinePage.getCoverageInvestment();

        //ex:
        //companies: 123/129
        //companyCount = 123
        //allCompaniesCount = 129
        int companyCount = Integer.parseInt(companies.substring(0, companies.indexOf("/")));
        int allCompaniesCount = Integer.parseInt(companies.substring(companies.indexOf("/") + 1));

        //ex:
        //investment: 95%
        //investmentRate: 95
        assertTestCase.assertTrue(investment.endsWith("%"), "% sign verified");
        int investmentRate = Integer.parseInt(investment.substring(0, investment.indexOf("%")));

        assertTestCase.assertTrue(companyCount >= 0, "Company count displayed");
        assertTestCase.assertTrue(companyCount <= allCompaniesCount, "Company count in range");
        assertTestCase.assertTrue(investmentRate <= 100 && investmentRate >= 0, "% range verified");

        System.out.println();
        System.out.printf("%10s %10s", companyCount + "/" + allCompaniesCount, investmentRate + "%");

        test.pass(title + " Table rows columns and content verified");


    }


    /*
    US JIRA # - ESGCA-487
    Acceptance Criteria:
    when a user uploads a portfolio
    that new portfolio should be available in both the portfolio drop down and benchmark dropdown
    and if that new uploaded portfolio is then selected
    the coverage should change to reflect the portfolio.
 */

    //Temporary test!!!!
   /* @Test(groups = {},
            description = "Verify Portfolio Score Table is changed to N/A if user select a date which is not applicable")
    public void verifyPortfolioScoreNAByDate() {
        UploadPage uploadPage = new UploadPage();
        PhysicalRiskManagementPage physicalRiskManagementPage = new PhysicalRiskManagementPage();

        String title = "Portfolio Score";
        test = report.createTest("Verify Portfolio Score Table is changed to N/A if user select a date which is not applicable");

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
/*        Assert.assertTrue(physicalRiskManagementPage.checkIfPortfolioScoreCategoryColorIsCorrect());

        List<Map<String, WebElement>> scoreTable = physicalRiskManagementPage.getPortfolioScoreFromUI();

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
    }*/
}
