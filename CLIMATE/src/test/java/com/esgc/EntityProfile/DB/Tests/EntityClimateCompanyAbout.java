package com.esgc.EntityProfile.DB.Tests;

import com.esgc.EntityProfile.API.APIModels.EntityHeader;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.getEntityHeaderDetails;


public class EntityClimateCompanyAbout extends DataValidationTestBase {


    @Xray(test = {10045,10046})
    @Test(groups = {"regression", "ui", "smoke", "entity_climate_profile"},dataProvider = "Company With Orbis ID",
            dataProviderClass = DataProviderClass.class)
    public void entityClimateCompanyAbout(String... dataprovider) {

        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();

        List<EntityHeader> companyHeaderAPIResponse = Arrays.asList(entityClimateProfileApiController
                .geCompanyHeaderAPIResponse(dataprovider[1]).as(EntityHeader[].class));
        System.out.println("dataprovider[1] = " + dataprovider[1]);
        Map<String,Object> dbResults = getEntityHeaderDetails(dataprovider[1]);
        System.out.println("dbResults.get(\"SECTOR\").toString() = " + dbResults.get("SECTOR").toString());
        System.out.println("companyHeaderAPIResponse.get(0).getGeneric_sector() = " + companyHeaderAPIResponse.get(0).getGeneric_sector());
        System.out.println("companyHeaderAPIResponse.get(0).getModel_version() = " + companyHeaderAPIResponse.get(0).getModel_version());
        assertTestCase.assertTrue(dbResults.get("ORBIS_ID").toString().equals(companyHeaderAPIResponse.get(0).getOrbis_id()));
        if(dbResults.get("RESEARCH_LINE_ID").toString().equals("1008")) {
            String modelVersion= String.valueOf(Double.parseDouble(companyHeaderAPIResponse.get(0).getModel_version()));
            assertTestCase.assertTrue(dbResults.get("SECTOR").toString().equals(companyHeaderAPIResponse.get(0).getGeneric_sector()));
            assertTestCase.assertTrue(modelVersion.equals("1.0"));
            assertTestCase.assertTrue(companyHeaderAPIResponse.get(0).getMethodology().equals("VE"));
        }else if (dbResults.get("RESEARCH_LINE_ID").toString().equals("1015")){
            assertTestCase.assertTrue(dbResults.get("SECTOR").toString().equals(companyHeaderAPIResponse.get(0).getSector_l1()));
            assertTestCase.assertTrue(companyHeaderAPIResponse.get(0).getModel_version().equals("2.0"));
            assertTestCase.assertTrue(companyHeaderAPIResponse.get(0).getMethodology().equals("MESG"));
        }
        if(dbResults.get("LEI")!=null)
            assertTestCase.assertTrue(dbResults.get("LEI").toString().equals(companyHeaderAPIResponse.get(0).getLei()));
        if(dbResults.get("ISIN")!=null)
            assertTestCase.assertTrue(dbResults.get("ISIN").toString().equals(companyHeaderAPIResponse.get(0).getPrimary_isin()));
    }
}
