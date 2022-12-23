package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.EntityProfile.UI.Pages.PDFTestMethods;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.EntitlementsBundles;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class EsgPDFExportEntitlements extends UITestBase {


    @Test(groups = {"regression", "ui", "entitlements"})
    @Xray(test = {9207})
    public void validateExportWithoutEsgScoreEntitlement(){
        LoginPage login = new LoginPage();

        test.info("Login with user is not having ESG Entitlement");
        login.entitlementsLogin(EntitlementsBundles.PHYSICAL_RISK);

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        test.info("Export or Source Documents feature availability");
        EntityClimateProfilePage entityPage = new EntityClimateProfilePage();
        assertTestCase.assertTrue(!entityPage.IsExportSourcesDocumentsButtonAvailable(),"Verify Export or Source Documents button is not available");
    }

    @Test(groups = {"regression", "ui", "entitlements"},dataProviderClass= DataProviderClass.class, dataProvider = "PDFExportButtonEntitlements")
    @Xray(test = {10941})
    public void validatePDFExportButtonEntitlements(EntitlementsBundles entitlements){
        LoginPage login = new LoginPage();

        login.entitlementsLogin(entitlements);
        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.searchAndLoadClimateProfilePage(company);
        boolean ExportSourcesDocumentsButtonAvailable = false;
        if(entityProfilePage.IsExportSourcesDocumentsButtonAvailable()) {
            entityProfilePage.selectExportSourcesDocuments();
            ExportSourcesDocumentsButtonAvailable = true;
            BrowserUtils.wait(3);
        }
        boolean exportPDF = false;
        boolean sourceDocument = false;

        switch (entitlements){
            case PDF_EXPORT_BUNDLE_ENTITLEMENT :
                exportPDF = true;
                sourceDocument = true;
                break;
            case PDF_EXPORT_ONLY_PDF_ENTITLEMENT :
                exportPDF = true;
                sourceDocument = false;
                break;
            case PDF_EXPORT_ONLY_SOURCEDOCUMENTS_ENTITLEMENT :
                exportPDF = false;
                sourceDocument = true;
                break;
        }
        test.info("Validating for " + entitlements);
        if (!entitlements.toString().equals("USER_WITH_OUT_EXPORT_ENTITLEMENT") ) {
            assertTestCase.assertEquals(exportPDF, entityProfilePage.IsPDFButtonAvailable(), "Validating PDF button entitlement for " + entitlements);
            assertTestCase.assertEquals(sourceDocument, entityProfilePage.IsSourceDocumentsDivAvailable(), "Validating Source button entitlement for " + entitlements);
        }else{
            assertTestCase.assertTrue(!ExportSourcesDocumentsButtonAvailable, "Validating that Export/ Source button is not available when + " + entitlements);
        }

    }




    @Test(groups = {"regression", "ui", "entitlements"},dataProviderClass= DataProviderClass.class, dataProvider = "PDFEntitlements")
    @Xray(test = {11765})
    public void validateBrownShareExportEntitlement(String... data){
        LoginPage login = new LoginPage();
        login.loginWithParams(data[0].toString(),data[1].toString());

        PDFTestMethods pdfTest = new PDFTestMethods();
        pdfTest.DownloadPDFPAndGetFileContent("Apple Inc.");
        switch (data[2].toString()){
            case "PR+ESG+Export":
            case "PR+Export":
                assertTestCase.assertEquals(pdfTest.ValidateIfBROWNSHAREIN_UnderlyingDataTransitionRisk_IsAvaialble(),false);

            case "TR+ESG+Export": case "TR+Export":
                assertTestCase.assertEquals(pdfTest.ValidateIfBROWNSHAREIN_UnderlyingDataTransitionRisk_IsAvaialble(),true);
        }
    }

}
