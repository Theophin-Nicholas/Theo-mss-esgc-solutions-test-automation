package com.esgc.ONDEMAND.API.Tests;

import com.esgc.Common.API.TestBase.CommonTestBase;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.RegulatoryReporting.API.APIModels.FilteredCompanies;
import com.esgc.ONDEMAND.API.APIModels.OnDemandRequests;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class OnDemandFilterAPITest extends CommonTestBase {

    @Test(groups = {API, REGRESSION})
    @Xray(test = {12065,12444})
    public void validateOnDemandFilterApiResponse() {
        OnDemandFilterAPIController onDemandcontroller = new OnDemandFilterAPIController();
        String portfolioId = onDemandcontroller.getPortfolioId("500 predicted portfolio");

        Response response = onDemandcontroller.getCompaniesWithFilter(portfolioId);
        response.as(FilteredCompanies.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

    @Test(groups = {API, REGRESSION})
    @Xray(test = {12065})
    public void validateOnDemandStatusApiResponse() {
        OnDemandFilterAPIController onDemandcontroller = new OnDemandFilterAPIController();
        System.clearProperty("token");
        String portfolioId = onDemandcontroller.getPortfolioId("500 predicted portfolio");
        Response response = onDemandcontroller.getOnDemandsStatus(portfolioId);

        response.as(OnDemandRequests.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }
}
