package com.esgc.ONDEMAND.DB.Tests;

import com.esgc.Common.DB.TestBases.DataValidationTestBase;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.DB.DBQueries.OnDemandAssessmentQueries;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.ONDEMAND.UI.Pages.ViewDetailPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Database.DatabaseDriver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.DATA_VALIDATION;
import static com.esgc.Utilities.Groups.REGRESSION;

// DB test class

public class OnDemandViewDetailPageDBTests extends DataValidationTestBase {

    // DB test method
    //

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
        List<String>  entityNameList = queries.getEntitieNamesFromResultSet(portfolioId);
        onDemandAssessmentPage.navigateToReportingService("On-Demand Assessment");
        BrowserUtils.wait(2);
        onDemandAssessmentPage.selectPortfolioOptionByName(portfolioName);
        BrowserUtils.wait(5);
        onDemandAssessmentPage.clickOnViewDetailButton(portfolioName);
        BrowserUtils.wait(15);
        List<String> uiEntityList = detail.getEntityCellsText();
        assertTestCase.assertTrue(columnData.containsAll(uiEntityList), "verify entities listed are in the result of the DB query");
    }

}