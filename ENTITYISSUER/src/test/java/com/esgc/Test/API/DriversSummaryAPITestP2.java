package com.esgc.Test.API;

import com.esgc.APIModels.EntityIssuerPage.*;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass ;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class DriversSummaryAPITestP2 extends EntityIssuerPageTestBase {


    @Test(groups = {"regression", "api"},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGCA-5867-API | ESG Issuer - Entity Page | Verify API Response")
    @Xray(test = {3965, 3973})
    public void validateDriverSummaryAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();

        Response response = controller.getSectorDrivers(dataProvider[2]);
        List<SectorDriversWrapper> SectorDriversWrapper = Arrays.asList(
                response.as(SectorDriversWrapper[].class));
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        for (SectorIndicator criteria : SectorDriversWrapper.get(0).getDriver_details()) {
            for (SectorDrivers driver : criteria.getDrivers()) {
                String scoreCategory = "";
                int score = (int) Math.floor(driver.getCriteria_weight_value());
                if (score == 0 ) {           //0-24
                    scoreCategory = "Low";
                } else if (score == 1) {    //25-44
                    scoreCategory = "Moderate";
                } else if (score == 2) {    //45-59
                    scoreCategory = "High";
                } else if (score == 3) {   //60-100
                    scoreCategory = "Very High";
                }
                assertTestCase.assertEquals(scoreCategory, driver.getCriteria_weight(), "Validate Score Category");
            }
        }
      //  String a = SectorDriversWrapper.get(0).getCriteria().get(0).getDrivers().get(0).getScore_category();
    }

    @Test(groups = {"regression", "api"},
            dataProvider = "credentialsP2", dataProviderClass = IssuerDataProviderClass.class,
            description = "ESGCA-5867-API | ESG Issuer - Entity Page | Verify API Response")
    @Xray(test = {3974})
    public void validateDriverSummaryInvalidTokenAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
        controller.setInvalid();
        Response response = controller.getSectorDrivers(dataProvider[2]);

        response.then().log().all();
        response.then().assertThat()
                .statusCode(403)
                .contentType(ContentType.JSON);
    }


}
