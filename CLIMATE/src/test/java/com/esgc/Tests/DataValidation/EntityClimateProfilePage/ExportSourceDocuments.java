package com.esgc.Tests.DataValidation.EntityClimateProfilePage;

import com.esgc.APIModels.EntityProfileClimatePage.SummarySection.TemperatureAlignmentSummary;
import com.esgc.APIModels.EntityUnderlyingData.BrownShare;
import com.esgc.APIModels.EntityUnderlyingData.CarbonFootprint;
import com.esgc.APIModels.EntityUnderlyingData.GreenShare;
import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Tests.TestBases.EntityClimateProfileDataValidationTestBase;
import com.esgc.Utilities.API.EntityClimateProfilePageEndpoints;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Database.EntityClimateProfilePageQueries.*;
import static io.restassured.RestAssured.given;

public class ExportSourceDocuments extends EntityClimateProfileDataValidationTestBase {

    @Xray(test = {9222})
    @Test(groups = {"regression", "ui", "entity_climate_profile"})
    public void validateExportSourceDocuments() {

        String company = "Rogers Corp.";
        String orbisId = "000001484";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
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
