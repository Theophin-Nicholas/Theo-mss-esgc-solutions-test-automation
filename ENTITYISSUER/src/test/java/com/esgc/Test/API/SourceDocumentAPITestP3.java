package com.esgc.Test.API;

import com.esgc.APIModels.EntityIssuerPage.SourceDocument;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class SourceDocumentAPITestP3 extends EntityIssuerPageTestBase {
    @Test(groups = {"api", "regression", "entity_issuer"},
           // dataProvider = "credentialsWithOrbisId"
            dataProvider = "credentialsP3"
            ,dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = 6967)
    public void validateSourceDocumentAPIResponse(String userId, String password,String orbisId) {
        getEntityPageAccessTokenLoginWithParameter(userId,password);
        //String orbis_id = Environment.OrbisId;
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        test.info("Validating Source Document API response");
        Response response = entityIssuerPageAPIController
                .getSourceDocument(orbisId);
        response.as(SourceDocument[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));
    }
}