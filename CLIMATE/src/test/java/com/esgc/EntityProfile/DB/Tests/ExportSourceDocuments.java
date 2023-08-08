package com.esgc.EntityProfile.DB.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileDataValidationTestBase;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.List;

import static com.esgc.EntityProfile.DB.DBQueries.EntityClimateProfilePageQueries.getExportOrSourceDocumentsFromDB;
import static com.esgc.Utilities.Groups.*;

public class ExportSourceDocuments extends EntityClimateProfileDataValidationTestBase {

    @Xray(test = {5019})
    @Test(groups = {REGRESSION, UI, ENTITY_PROFILE})
    public void validateExportSourceDocuments() {

        String company = "Rogers Corp.";
        String orbisId = "000001484";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        assertTestCase.assertTrue(entityProfilePage.verifyExportPopupSubTitle("Source Documents"), "Verify Export Popup Subtitle");
        List<String> dbSourceDocuments = getExportOrSourceDocumentsFromDB(orbisId);

        assertTestCase.assertEquals(dbSourceDocuments.size(), entityProfilePage.listSourceDocuments.size(), "Verification of number of export or source documents from DB");
        for(WebElement sourceDoc:entityProfilePage.listSourceDocuments){
            assertTestCase.assertTrue(dbSourceDocuments.contains(sourceDoc.getText()), "Verification of export or source document from DB");
        }
    }

}
