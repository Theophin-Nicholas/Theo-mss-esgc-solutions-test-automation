package com.esgc.API.Tests;

import com.esgc.API.APIModels.FilteredCompanies;
import com.esgc.API.APIModels.OnDemandRequests;
import com.esgc.API.Controllers.OnDemandFilterAPIController;
import com.esgc.API.TestBase.TestBaseOnDemand;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class OnDemandFilterAPITest extends TestBaseOnDemand {

    @Test(groups = {API, REGRESSION})
    @Xray(test = {12065,12444})
    public void validateOnDemandFilterApiResponse() {
        OnDemandFilterAPIController controller = new OnDemandFilterAPIController();
        String portfolioId = controller.getPortfolioId("500 predicted portfolio");

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
        String portfolioId = controller.getPortfolioId("500 predicted portfolio");

        Response response = controller.getOnDemandsStatus(portfolioId);
        response.as(OnDemandRequests.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }



}
