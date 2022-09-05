package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.Header;
import com.esgc.Test.TestBases.EntityPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectorAllocation extends EntityPageDataValidationTestBase {
    List<String> criteria = new ArrayList<>();

    @Xray(test = 6267)
    @Test(groups = {"entity_issuer"})
    public void verifySectorHeaderDescription() {

        Response response = controller.getHeaderAPI();
        List<Header> headerAPIResponse = Arrays.asList(response.getBody().as(Header[].class));

        String SectorDescription = EntityPageQueries.getSectorDescription(headerAPIResponse.get(0).getOrbis_id());

        assertTestCase.assertTrue(SectorDescription.equals(headerAPIResponse.get(0).getMesg_sector_detail_description()));

        test.info("Verified Sector Header Description successfully");
    }

}
