package com.esgc.Test.API;

import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.*;

public class AddMissingDcumentsURL extends EntityIssuerPageTestBase {
    @Test(groups = {API, REGRESSION, ISSUER},dataProvider = "loginP2", dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = 11715)
    public void ValidateUrlApiResponseP2(String... data) {
        getEntityPageAccessTokenLoginWithParameter(data[0].toString(),data[1].toString());
        validateResponse("googl.zxd","Invalid URL or the domain doesn't exists");
    }

    @Test(groups = {API, REGRESSION, ISSUER},dataProvider = "loginP3", dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = 11523)
    public void ValidateUrlApiResponseP3(String... data) {
        getEntityPageAccessTokenLoginWithParameter(data[0].toString(),data[1].toString());
        validateResponse("googl.zxd","Invalid URL or the domain doesn't exists");
    }

    public void validateResponse(String URL, String message) {

        //String orbis_id = Environment.OrbisId;
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        test.info("Validating Source Document API response");
        Response response = entityIssuerPageAPIController
                .getVAlidateURLApiResponse(URL);

        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON);

        assertTestCase.assertEquals(response.body().jsonPath().getString("msg"),message,"Validate wrong URL message");
    }
}