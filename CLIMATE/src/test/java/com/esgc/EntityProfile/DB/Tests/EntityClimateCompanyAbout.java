package com.esgc.EntityProfile.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.EntityProfile.API.APIModels.EntityHeader;
import com.esgc.EntityProfile.API.Controllers.EntityProfileClimatePageAPIController;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries.getEntityHeaderDetails;
import static com.esgc.Utilities.Groups.*;

public class EntityClimateCompanyAbout extends DataValidationTestBase {


    @Xray(test = {4091,4728})
    @Test(groups = {REGRESSION, UI, SMOKE, ENTITY_PROFILE},dataProvider = "Company With Orbis ID",
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

    @Xray(test = {3198})
    @Test(groups = {REGRESSION, ENTITY_PROFILE, DATA_VALIDATION},
            description = "Data Validation | Entity Page | Entity Drawer | Verify the Sector, L1&L2 for entity")
    public void verifySectorL1L2ForEntityTest() {
        DashboardQueries queries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        List<Map<String, Object>> dbData = queries.getPortfolioSectorDetail(portfolioId);
        for (int i = 0; i < 5; i++) {
            if(dbData.get(i).get("ENTITY_NAME")==null) continue;
            String companyName = dbData.get(i).get("ENTITY_NAME").toString();
            System.out.println("companyName = " + companyName);
            if(dbData.get(i).get("MESG_SECTOR")==null) continue;
            String sector = dbData.get(i).get("MESG_SECTOR").toString();
            System.out.println("sector = " + sector);

            EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
            entityProfilePage.searchAndLoadClimateProfilePage(companyName);
            entityProfilePage.waitForDataLoadCompletion();
            BrowserUtils.waitForClickablility(entityProfilePage.companyHeaderItems.get(0), 10).click();
            BrowserUtils.waitForVisibility(entityProfilePage.companyDrawerSector, 10);
            String uiSector = entityProfilePage.companyDrawerSector.getText();
            System.out.println("Company Sector = " + uiSector);
            if(!uiSector.equals("Company's Description - Coming Soon")) {
                assertTestCase.assertTrue(uiSector.contains(sector), "Sector is verified");
            }

            BrowserUtils.waitForClickablility(entityProfilePage.hideDrawerButton, 10).click();
            System.out.println("Drawer is closed");
            entityProfilePage.closeEntityProfilePage();
            BrowserUtils.waitFor(2);
            System.out.println("ESC key is pressed");
        }


    }


}
