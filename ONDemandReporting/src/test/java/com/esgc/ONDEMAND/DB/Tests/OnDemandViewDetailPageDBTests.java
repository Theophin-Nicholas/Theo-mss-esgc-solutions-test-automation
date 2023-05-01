package com.esgc.ONDEMAND.DB.Tests;

import com.esgc.Common.API.Controllers.CommonAPIController;
import com.esgc.Common.DB.TestBases.DataValidationTestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.ViewDetailPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.ImportPortfolioUtility;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

// DB test class

public class OnDemandViewDetailPageDBTests extends DataValidationTestBase {

    @Test(groups = {REGRESSION, DATA_VALIDATION})
    @Xray(test = {14153, 14170, 14187, 13798, 14157, 13799, 14156})
    public void verifyNoESGEntitiesInOnDemandViewDetailPageTestDbValidation(){

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        ViewDetailPage detail = new ViewDetailPage();

        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId(portfolioName);
        OnDemandAssessmentQueries queries = new OnDemandAssessmentQueries();
        List<Map<String, Object>> dbEntitiesList= queries.getEntitiesWithScoreType(portfolioId);
        List<Object> columnData = queries.getEntitiesNameList(portfolioId);
        System.out.println("number of entities in the portfolio equals the number of rows returned from Database "+dbEntitiesList.size() );
        List<String>  entityNameList = queries.getEntitiesNamesFromResultSet(portfolioId);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        BrowserUtils.wait(2);
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);
        BrowserUtils.wait(15);
        List<String> uiEntityList = detail.getEntityCellsText();
        assertTestCase.assertTrue(columnData.containsAll(uiEntityList), "verify entities listed are in the result of the DB query");
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION}, description = "Verify Exported Document from 'View Details'")
    @Xray(test = {14025, 14026, 14067, 14069, 14070, 14071, 14072, 14074})
    public void verifyExportedDocumentFromViewDetailsTest(){

        String portfolioName = "500 predicted portfolio";
        OnDemandAssessmentPage onDemandAssessmentPage = new OnDemandAssessmentPage();
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        BrowserUtils.waitForVisibility(onDemandAssessmentPage.portfolioNamesList, 20);
        if(!onDemandAssessmentPage.verifyPortfolio(portfolioName)) onDemandAssessmentPage.uploadPortfolio(portfolioName.replaceAll(" ", ""));
        onDemandAssessmentPage.downloadPortfolio(portfolioName, "page");
        System.out.println("Downloaded portfolio: " + portfolioName);
        onDemandAssessmentPage.verifyDataScores(portfolioName);

        onDemandAssessmentPage.downloadPortfolio(portfolioName, "details panel");
        System.out.println("Downloaded portfolio: " + portfolioName);
        onDemandAssessmentPage.verifyDataScores(portfolioName);
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION}, description = "Verify that for Data Alliance flag equal to 'Yes'/'No' scores are displayed in exported excel")
    @Xray(test = {14333, 14334})
    public void verifyDataAllianceFlagForExportedExcelTest(){
        OnDemandAssessmentPage reportingPage = new OnDemandAssessmentPage();
        reportingPage.navigateToReportingService("On-Demand Assessment");
        BrowserUtils.waitForVisibility(reportingPage.portfolioNamesList, 20);

        String portfolioName = "DataAllianceFlagPortfolio";
        String portfolioFilePath = ImportPortfolioUtility.getOnDemandPortfolioFileToUpload(Collections.singletonList("DataAlliance"), "", 50, portfolioName,false);
        reportingPage.uploadPortfolio(portfolioFilePath, "OnDemand");
        BrowserUtils.wait(10);
        Driver.getDriver().navigate().refresh();

        reportingPage.selectReportingOptionByName("On-Demand Assessment");
        reportingPage.verifyPortfolio(portfolioName);
        reportingPage.downloadPortfolio(portfolioName, "page");
        System.out.println("Downloaded portfolio: " + portfolioName);
        reportingPage.verifyDataAllianceFlag(portfolioName);
        CommonAPIController.deletePortfolioThroughAPI(portfolioName);
    }
}