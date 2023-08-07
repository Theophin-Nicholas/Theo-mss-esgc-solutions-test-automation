package com.esgc.ONDEMAND.API.Tests;

import com.esgc.Common.API.TestBase.CommonTestBase;
import com.esgc.Common.UI.Pages.LoginPage;
import com.esgc.ONDEMAND.API.Controllers.OnDemandFilterAPIController;
import com.esgc.ONDEMAND.UI.Pages.OnDemandAssessmentPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class OnDemandAPIEntitlementBundleTest extends CommonTestBase {


    // api test for on demand
    @Test(groups= {API, REGRESSION})
    @Xray(test = {2846})
    public void validateOnDemandRemainingAssessmentsApiResponse(){
        OnDemandAssessmentPage oDPage = new OnDemandAssessmentPage();
        oDPage.navigateToReportingService("On-Demand Assessment");
        LoginPage login = new LoginPage();

        String portfolioName = "PortfolioWithZeroCoverageEntities";
        oDPage.clickOnMenuButton();
        oDPage.clickOnLogOutButton();

        login.entitlementsLogin(EntitlementsBundles.USER_WITH_ZERO_ASSESSMENT_AVAILABLE);
        System.out.println("Logged in to Check 0 Remaining Assessment....");
        BrowserUtils.wait(5);
        Driver.getDriver().manage().deleteAllCookies();
        BrowserUtils.refresh();
        BrowserUtils.wait(5);
        getExistingUsersAccessTokenFromUI();

        OnDemandFilterAPIController onDemandController = new OnDemandFilterAPIController();
        Response response = onDemandController.getOnDemandInfo();
        // response.as(FilteredCompanies.class);
        response.then().log().all();
        response.getBody().asString();

        System.out.println("Response Body of Info API in On Demand Page is :" + response.getBody().asString());
        JsonPath jsonPathEvaluator = response.jsonPath();
        String remainingAssessments = jsonPathEvaluator.get("remaining_assessments").toString();
        System.out.println("remaining assessments are:" + remainingAssessments);
        assertTestCase.assertEquals(remainingAssessments, "0", "Verification that remaining assessments are equal to 0");
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);
    }

}
