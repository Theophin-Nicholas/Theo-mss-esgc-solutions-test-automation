package com.esgc.PortfolioAnalysis.API.Tests;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.APITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.SectorSummary;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;
import static org.hamcrest.Matchers.*;


/**
 * Created by ChaudhS2 on 11/9/2021.
 */
public class SectorSummaryAPITest extends APITestBase {//2210
    @Test(groups = {API, REGRESSION}, dataProvider = "No ESG API Research Lines")
    @Xray(test = {2210, 1966, 2187, 1299, 2082})
    //1751 Energy transition
    //1923 TCFD
    public void SectorSummaryAPI_Success(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Sector Summary");

        APIFilterPayload apiFilterPayload = new APIFilterPayload("all","all","03","2021", "");

        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(200);
        response.as(SectorSummary[].class);
        response.then().log().all();
        response.then().assertThat()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(".", everyItem(is(notNullValue())));

        test.pass("Sector Summary Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    @Xray(test = {2209, 1274, })
    public void SectorSummary_InvalidPayload(@Optional String researchLine) {
        APIController apiController = new APIController();

        test.info("POST Request sending for Sector Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload(null, null, "03", "2021", "");
        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(APIUtilities.invalidPayloadStatusCode);

        test.pass("Response received for Sector Summary");
        test.pass("Sector Summary Call Completed Successfully");
    }

    @Test(groups = {API, REGRESSION}, dataProvider = "API Research Lines")
    public void SectorSummary_UnauthorisedAccess(@Optional String researchLine) {

        APIController apiController = new APIController();

        String portfolioID = RandomStringUtils.randomAlphanumeric(20);

        test.info("POST Request sending for Sector Summary");
        APIFilterPayload apiFilterPayload = new APIFilterPayload("all", "all", "03", "2021", "");
        Response response = apiController
                .getPortfolioSectorSummaryResponse(portfolioID, researchLine, apiFilterPayload);
        response.then().assertThat().statusCode(403);

        test.pass("Response received for Sector Summary");
        test.pass("Sector Summary Call Completed Successfully");
    }
}
