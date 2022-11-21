package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.EntityClimateProfilePage;
import com.esgc.Pages.LoginPage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityExportOrSourcesDocumentsTests extends UITestBase {

    @Test(groups = {"entity_climate_profile", "regression", "ui", "robot_dependency"})
    @Xray(test = {9206, 9208, 9209, 9212, 10175})
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
    @Xray(test = {9381, 10175})
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

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {10084, 10154, 10176})
    public void validateExportSourceDocumentsDownload(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");
        String actualFileName = FileDownloadUtilities.getDownloadedFileName();
        String expFileName = companyName.replace(".","_").replace(",","_")+
                "Profile";
        System.out.println(actualFileName+"-->"+expFileName);
        assertTestCase.assertTrue(actualFileName.startsWith(expFileName),"Verify download of export file with name format");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {10085})
    public void validateExportPdfButtonDisabledAfterClick(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadPdfButtonIsDisabled(),"Verify PDF button is disabled");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {10086})
    public void validateExportPdfProgressBarClose(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");
        entityProfilePage.closeEntityProfilePage();
        assertTestCase.assertTrue(!entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");

        companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");
        entityProfilePage.closeDownloadProgressBar();
        assertTestCase.assertTrue(!entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"})
    @Xray(test = {10177})
    public void validatePdfContent(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");

        String actualFileName = FileDownloadUtilities.getDownloadedFileName();

        String filePath = BrowserUtils.downloadPath()+"\\"+actualFileName;
        int numberOfPages = PdfUtil.getNumberOfPages(filePath);
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath,1).contains("Summary"),"Verify first page is summary page");
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath, 2).contains("Materiality"),"Verify first page is summary page");
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath, numberOfPages).contains("Disclaimer and Copyright"),"Verify first page is summary page");

        // Verify page numbers
        for(int i=1; i<=numberOfPages; i++){
            assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath,i).trim().endsWith(String.valueOf(i)),"Verify first page is summary page");
        }

    }

    @Test(groups = {"entity_climate_profile", "regression", "ui"}, dataProviderClass = DataProviderClass.class, dataProvider = "exportEntitlements")
    @Xray(test = {10178, 11041,11153})
    public void validatePdfContentBasedOnEntitlement(String username, String password, String entitlement){
        LoginPage loginPage = new LoginPage();
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<WebElement> check = Driver.getDriver().findElements(By.xpath("//div[@id='RegSector-test-id-1']"));
        System.out.println("check.size() = " + check.size());
        if (check.size() == 1) {
            entityProfilePage.pressESCKey();
            loginPage.clickOnLogout();
        }
        loginPage.loginWithParams(username, password);

        String company = "Rogers Corp.";
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");

        String actualFileName = FileDownloadUtilities.getDownloadedFileName();

        String filePath = BrowserUtils.downloadPath()+"\\"+actualFileName;
        String content = PdfUtil.getPdfContent(filePath);

        if (entitlement.equals("Physical Risk")) {
            /* Verify that User is able to see just Physical Risk Entitlement */
            assertTestCase.assertTrue(content.contains("Physical Risk"));
            assertTestCase.assertTrue(content.contains("OPERATIONS RISK"));
            assertTestCase.assertTrue(content.contains("MARKET RISK"));
            assertTestCase.assertTrue(content.contains("SUPPLY CHAIN RISK"));
            assertTestCase.assertTrue(!content.contains("Transition Risk "));
        } else if (entitlement.equals("Transition Risk")) {
            assertTestCase.assertTrue(content.contains("Transition Risk"));
            assertTestCase.assertTrue(content.contains("Temperature Alignment"));
            assertTestCase.assertTrue(content.contains("Carbon Footprint"));
            assertTestCase.assertTrue(content.contains("Green Share"));
            assertTestCase.assertTrue(content.contains("Brown Share"));
            assertTestCase.assertTrue(!content.contains("Physical Risk "));
        } else if (entitlement.equals("Physical Risk and Transition Risk")) {
            /* Verify that User is able to see just Physical Risk and Transition Risk Entitlements  */
            assertTestCase.assertTrue(content.contains("Physical Risk "));
            assertTestCase.assertTrue(content.contains("OPERATIONS RISK"));
            assertTestCase.assertTrue(content.contains("MARKET RISK"));
            assertTestCase.assertTrue(content.contains("SUPPLY CHAIN RISK"));
            assertTestCase.assertTrue(content.contains("Transition Risk "));
            assertTestCase.assertTrue(content.contains("Temperature Alignment"));
            assertTestCase.assertTrue(content.contains("Carbon Footprint"));
            assertTestCase.assertTrue(content.contains("Green Share"));
            assertTestCase.assertTrue(content.contains("Brown Share"));
        } else if (entitlement.equals("ESG")) {
            /* Verify that User is able to see just ESG   */
            assertTestCase.assertTrue(content.contains("Subcategory Materiality"));
            assertTestCase.assertTrue(content.contains("Very High Materiality"));
            assertTestCase.assertTrue(content.contains("High Materiality"));
            assertTestCase.assertTrue(content.contains("Moderate Materiality"));
            assertTestCase.assertTrue(content.contains("Low Materiality"));
            assertTestCase.assertTrue(!content.contains("Physical Risk "));
            assertTestCase.assertTrue(!content.contains("Transition Risk "));

        } else if (entitlement.equals("Controversey entitlement")) {
            assertTestCase.assertTrue(content.contains("Controversies as of "));
            assertTestCase.assertTrue(content.contains("CRITICAL AND HIGH SEVERITY CONTROVERSIES SEVERITY"));
        } else if (entitlement.equals("No Controversey")) {
            assertTestCase.assertTrue(!content.contains("Controversies as of "));
            assertTestCase.assertTrue(!content.contains("CRITICAL AND HIGH SEVERITY CONTROVERSIES SEVERITY"));
        }
        loginPage.clickOnLogout();

    }

}
