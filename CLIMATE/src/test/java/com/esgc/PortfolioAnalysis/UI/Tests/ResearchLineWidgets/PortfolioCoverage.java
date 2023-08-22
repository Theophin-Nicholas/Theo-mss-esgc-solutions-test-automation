package com.esgc.PortfolioAnalysis.UI.Tests.ResearchLineWidgets;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

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
    @Test(groups = {REGRESSION, UI, SMOKE},
            description = "Verify if Portfolio Coverage Table is Displayed as Expected",
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {4337, 4033, 4831})
    public void verifyPortfolioCoverage(String page) {
        ResearchLinePage researchLinePage = new ResearchLinePage();
        String title = "Coverage";

        researchLinePage.navigateToResearchLine(page);

        test.info("Navigated to " + page + " Page");

       // assertTestCase.assertTrue(researchLinePage.checkIfPortfolioLoadMaskIsDisplayed(title), "Portfolio Coverage Loading");
        assertTestCase.assertTrue(researchLinePage.checkIfPageTitleIsDisplayed(page), "Navigated to Research Line");
        test.pass("User is on " + page + " Page");


       // assertTestCase.assertTrue(researchLinePage.checkIfWidgetTitleIsDisplayed(title), "Portfolio Coverage is Displayed");
        assertTestCase.assertTrue(researchLinePage.checkIfPortfolioTableIsDisplayed(title), "Portfolio Coverage is displayed", 4557);

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
        int investmentRate = 0;
        if(!investment.contains("<")){
            investmentRate = Integer.parseInt(investment.substring(0, investment.indexOf("%")));
        }

        assertTestCase.assertTrue(companyCount >= 0, "Company count displayed");
        assertTestCase.assertTrue(companyCount <= allCompaniesCount, "Company count in range");
        assertTestCase.assertTrue(investmentRate <= 100 && investmentRate >= 0, "% range verified");

        System.out.println();
        System.out.printf("%10s %10s", companyCount + "/" + allCompaniesCount, investmentRate + "%");

        test.pass(title + " Table rows columns and content verified");


    }
}
