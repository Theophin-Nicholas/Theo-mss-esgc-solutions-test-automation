package com.esgc.Test.API;

import com.esgc.APIModels.EntityIssuerPage.DriverDetailPayload;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryAPIWrapper;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryDetails;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class ESGMateralityDriversSummaryAPITest extends EntityIssuerPageTestBase {


    @Test(groups = {"regression", "api"},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify API Response")
    @Xray(test = {9951})
    public void validateDriverSummaryAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();

        Response response = controller.getDriverSummary();

        ESGMaterlityDriverSummaryAPIWrapper driverSummaryPIResponse = response.as(ESGMaterlityDriverSummaryAPIWrapper.class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);


    }

    @Test(groups = {"regression", "api"},
            dataProvider = "credentialsP3", dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify API Response")
    @Xray(test = {9948})
    public void validateDriverSummaryDetailsAPIResponse(String... dataProvider) {
        String userId = dataProvider[0];
        String password = dataProvider[1];
        getEntityPageAccessTokenLoginWithParameter(userId, password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
        ESGMaterlityDriverSummaryAPIWrapper driverSummaryPIResponse = controller.getDriverSummary().as(ESGMaterlityDriverSummaryAPIWrapper.class);

        List<String> eachCriteria = driverSummaryPIResponse.getDrivers().stream().map(ESGMaterlityDriverSummaryDetails::getCriteria_id).
                collect(Collectors.toList());

        DriverDetailPayload driverPayload = new DriverDetailPayload(eachCriteria.get(0), dataProvider[2]);
        Response response = controller.getDriverDetails(driverPayload);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

    }

}
