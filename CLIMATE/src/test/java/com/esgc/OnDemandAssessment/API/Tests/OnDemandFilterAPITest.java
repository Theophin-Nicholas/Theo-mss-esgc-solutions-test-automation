package com.esgc.OnDemandAssessment.API.Tests;

import com.esgc.Base.TestBases.APITestBase;
import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.OnDemandAssessment.API.APIModels.FilteredCompanies;
import com.esgc.OnDemandAssessment.API.APIModels.OnDemandRequests;
import com.esgc.OnDemandAssessment.API.Controllers.OnDemandFilterAPIController;
import com.esgc.RegulatoryReporting.API.Controllers.RegulatoryReportingAPIController;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class OnDemandFilterAPITest extends APITestBase {

    @Test(groups = {API, REGRESSION})
    @Xray(test = {12065,12444})
    public void validateOnDemandFilterApiResponse() {
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId("EsgWithPredictedScores");

        Response response = controller.getCompaniesWithFilter(portfolioId);
        response.as(FilteredCompanies.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test(groups = {API, REGRESSION})
    @Xray(test = {12065})
    public void validateOnDemandStatusApiResponse() {
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();

        RegulatoryReportingAPIController apiController = new RegulatoryReportingAPIController();
        String portfolioId = apiController.getPortfolioId("EsgWithPredictedScores");

        Response response = controller.getOnDemandsStatus(portfolioId);
        response.as(OnDemandRequests.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }



}
