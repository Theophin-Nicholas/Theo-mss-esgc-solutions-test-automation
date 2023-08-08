package com.esgc.ONDEMAND.API.Tests;

import com.esgc.Common.API.TestBase.CommonTestBase;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.ONDEMAND.API.APIModels.Request;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.RegulatoryReporting.API.APIModels.FilteredCompanies;
import com.esgc.ONDEMAND.API.APIModels.OnDemandRequests;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.RegulatoryReporting.API.APIModels.FilteredCompanies;
import com.esgc.Utilities.Xray;
import com.google.common.collect.Ordering;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class OnDemandFilterAPITest extends CommonTestBase {

    @Test(groups = {API, REGRESSION})
    @Xray(test = {4187,4143})
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
    @Xray(test = {4187, 3276})
    public void validateOnDemandStatusApiResponse() {
        OnDemandFilterAPIController onDemandcontroller = new OnDemandFilterAPIController();
        System.clearProperty("token");
        String portfolioId = onDemandcontroller.getPortfolioId("500 predicted portfolio");
        Response response = onDemandcontroller.getOnDemandsStatus(portfolioId).prettyPeek();
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        OnDemandRequests statusAPI = response.as(OnDemandRequests.class);
        List<String> createDateTime = statusAPI.getRequests().stream().map(e-> e.getCreate_datetime()).collect(Collectors.toList());
        assertTestCase.assertTrue(Ordering.<String> natural().reverse().isOrdered(createDateTime),"Validate if Created date is in chronological order");
    }
}
