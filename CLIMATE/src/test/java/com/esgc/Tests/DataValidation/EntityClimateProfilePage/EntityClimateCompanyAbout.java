package com.esgc.Tests.DataValidation.EntityClimateProfilePage;

import com.esgc.APIModels.EntityClimateProfile.EntityHeader;
import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getEntityHeaderDetails;


public class EntityClimateCompanyAbout extends DataValidationTestBase {


    @Xray(test = {10045,10046})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},dataProvider = "Company With Orbis ID")
    public void entityClimateCompanyAbout(String... dataprovider) {

        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();

        List<EntityHeader> companyheaderAPIResponse = Arrays.asList(entityClimateProfileApiController
                .geCompanyHeaderAPIResponse(dataprovider[1]).as(EntityHeader[].class));

        Map<String,Object> dbResults = getEntityHeaderDetails(dataprovider[1]);

        assertTestCase.assertTrue(dbResults.get("ORBIS_ID").toString().equals(companyheaderAPIResponse.get(0).getOrbis_id()));
        if(dbResults.get("RESEARCH_LINE_ID").toString().equals("1008")) {
            assertTestCase.assertTrue(dbResults.get("SECTOR").toString().equals(companyheaderAPIResponse.get(0).getGeneric_sector()));
            assertTestCase.assertTrue(companyheaderAPIResponse.get(0).getModel_version().equals("1.0"));
            assertTestCase.assertTrue(companyheaderAPIResponse.get(0).getMethodology().equals("VE"));
        }else if (dbResults.get("RESEARCH_LINE_ID").toString().equals("1015")){
            assertTestCase.assertTrue(dbResults.get("SECTOR").toString().equals(companyheaderAPIResponse.get(0).getSector_l1()));
            assertTestCase.assertTrue(companyheaderAPIResponse.get(0).getModel_version().equals("2.0"));
            assertTestCase.assertTrue(companyheaderAPIResponse.get(0).getMethodology().equals("MESG"));
        }
        if(dbResults.get("LEI")!=null)
            assertTestCase.assertTrue(dbResults.get("LEI").toString().equals(companyheaderAPIResponse.get(0).getLei()));
        if(dbResults.get("ISIN")!=null)
            assertTestCase.assertTrue(dbResults.get("ISIN").toString().equals(companyheaderAPIResponse.get(0).getPrimary_isin()));
    }
}
