package com.esgc.Test.API;

import com.esgc.APIModels.EntityPage.SectorDriversWrapper;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Test.TestBases.EntityIssuerPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class SectorDriversAPITestP3 extends EntityIssuerPageTestBase {

    @Test (groups ={"regression", "api"},
            dataProvider = "credentials",dataProviderClass = DataProviderClass.class,
            description = "ESGCA-5867-API | ESG Issuer - Entity Page | Verify API Response")
    @Xray(test = {5867})
    public void validateSectorDriversAPIResponse(String userId, String password,String orbisId){
        getEntityPageAccessTokenLoginWithParameter(userId,password);
        EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
        Response response = controller.getSectorDrivers(orbisId);
        response.as(SectorDriversWrapper[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));
        test.pass("Entity Issuer Page Sector Drivers API Validation");
    }
}
