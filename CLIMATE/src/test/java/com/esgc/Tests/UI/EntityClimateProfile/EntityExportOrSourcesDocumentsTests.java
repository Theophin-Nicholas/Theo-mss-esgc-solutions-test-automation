package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EntityExportOrSourcesDocumentsTests extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui", "robot_dependency"})
    @Xray(test = {9209, 9212})
    public void validateExportSourceDocumentsPopup(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        assertTestCase.assertTrue(entityProfilePage.verifyExportPopupSubTitle("Source Documents"), "Verify Export Popup Subtitle");
        assertTestCase.assertTrue(entityProfilePage.verifySourceDocuments(), "Verify Export Source Documents");
        assertTestCase.assertTrue(entityProfilePage.downloadSourceDocument(), "Verify Source Document is opened in new tab and download");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui", "robot_dependency"})
    @Xray(test = {9211})
    public void validateExportSourceDocumentsPopupClosure(){

        String company = "UFP Technologies, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        entityProfilePage.closePopup();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup is closed");

        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        entityProfilePage.pressESCKey();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup is closed");

        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        entityProfilePage.clickOutsideOfPopup();
        assertTestCase.assertTrue(!entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup is closed");
    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {9381})
    public void validateExportSourceDocumentsPopupWithNoDocsMessage(){

        String company = "Apple, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        assertTestCase.assertTrue(entityProfilePage.verifyExportPopupNoDocsMessage("No source documents available."), "Verify Export Popup No Source Documents Message");
        entityProfilePage.closePopup();
    }

}
