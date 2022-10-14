package com.esgc.Test.API;

import com.esgc.APIModels.EntityIssuerPage.DriverSummaryAPIWrapper;
import com.esgc.APIModels.EntityIssuerPage.DriverSummaryCriterion;
import com.esgc.APIModels.EntityIssuerPage.DriverSummaryDrivers;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class DriversSummaryAPITestP3 extends EntityIssuerPageTestBase {


    @Test(groups = {"regression", "api"},
            dataProvider = "credentials", dataProviderClass = DataProviderClass.class,
            description = "ESGCA-5867-API | ESG Issuer - Entity Page | Verify API Response")
    @Xray(test = {3965, 3973})
    public void validateDriverSummaryAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();

        Response response = controller.getDriverSummary();
        List<DriverSummaryAPIWrapper> driverSummaryPIResponse = Arrays.asList(
                response.as(DriverSummaryAPIWrapper[].class));
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        for (DriverSummaryCriterion criteria : driverSummaryPIResponse.get(0).getCriteria()) {
            for (DriverSummaryDrivers driver : criteria.getDrivers()) {
                String scoreCategory = "";
                int score = (int) Math.floor(driver.getCriteria_score());
                if (score >= 0 && score <= 24) {           //0-24
                    scoreCategory = "Weak";
                } else if (score >= 25 && score <= 44) {    //25-44
                    scoreCategory = "Limited";
                } else if (score >= 45 && score <= 59) {    //45-59
                    scoreCategory = "Robust";
                } else if (score >= 60 && score <= 100) {   //60-100
                    scoreCategory = "Advanced";
                }
                assertTestCase.assertEquals(scoreCategory, driver.getScore_category(), "Validate Score Category");
            }
        }
        String a = driverSummaryPIResponse.get(0).getCriteria().get(0).getDrivers().get(0).getScore_category();
    }

    @Test(groups = {"regression", "api"},
            dataProvider = "credentials", dataProviderClass = DataProviderClass.class,
            description = "ESGCA-5867-API | ESG Issuer - Entity Page | Verify API Response")
    @Xray(test = {3965, 3973})
    public void validateDriverSummaryInvalidTokenAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
        controller.setInvalid();
        Response response = controller.getDriverSummary();

        response.then().log().all();
        response.then().assertThat()
                .statusCode(403)
                .contentType(ContentType.JSON);
    }

}
