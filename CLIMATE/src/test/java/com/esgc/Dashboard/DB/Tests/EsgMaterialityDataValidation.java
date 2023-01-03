package com.esgc.Dashboard.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class EsgMaterialityDataValidation extends DataValidationTestBase {

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {9367, 9368, 9375, 8451, 8428, 8869})
    public void validateEsgMaterialityMatrix() {

       /* String company = "Sculptor Capital Management, Inc.";
        String orbisID = "035352536";*/
        String company = "Apple, Inc.";
        String orbisID = "000411117";
        test.info("Searching and Selecting the company");
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName), companyName + " Header Verification");
        assertTestCase.assertTrue(entityProfilePage.verifyOverallEsgScoreWidget(), "Verify overall ESG Score widget");
        assertTestCase.assertTrue(entityProfilePage.verifySectorComparisonChart(), "Verify Sector Comparison widget ");

        entityProfilePage.selectEsgMaterialityTab();
        List<Map<String, Object>> dbEsgMaterialityCategories = EntityPageQueries.getEsgMaterialityCategories(orbisID);
        List<String> uiEsgMaterialityCategories = entityProfilePage.readEsgMaterialityCategories();
        //assertTestCase.assertEquals(dbEsgMaterialityCategories.size(), uiEsgMaterialityCategories.size(), "Verification number of categories");
        for (int i = 1; i < dbEsgMaterialityCategories.size(); i++) {
            System.out.println("Category Record - " + i + ": " + dbEsgMaterialityCategories.get(i).get("criteria_name"));
            System.out.println("uiEsgMaterialityCategories = " + uiEsgMaterialityCategories);
            assertTestCase.assertTrue(uiEsgMaterialityCategories.contains(dbEsgMaterialityCategories.get(i).get("criteria_name").toString().trim()), "Verify Category from DB is matching with UI");
            //TODO Controversies are not available to click as of now. Clickable on Methodologies 2.0
               List<Map<String,Object>> dbEsgMaterialityControversies = EntityPageQueries.getEsgMaterialityControversies(orbisID, dbEsgMaterialityCategories.get(i).get("criteria_id").toString());
             // assertTestCase.assertTrue(entityProfilePage.verifyCategoryControversies(dbEsgMaterialityCategories.get(i), dbEsgMaterialityControversies), "Verify Controversies of Category");
        }
        entityProfilePage.clickCloseIcon();
    }
}
