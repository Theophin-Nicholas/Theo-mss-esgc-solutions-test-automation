package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Base.UI.Pages.LoginPage;
import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.*;
import net.snowflake.client.jdbc.internal.apache.tika.metadata.PDF;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class EntityExportOrSourcesDocumentsTests extends UITestBase {

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, ROBOT_DEPENDENCY})
    @Xray(test = {4868, 4830, 4828, 4509, 4048, 4660})
    public void validateExportSourceDocumentsPopup(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        assertTestCase.assertTrue(entityProfilePage.verifyExportPopupSubTitle("Source Documents"), "Verify Export Popup Subtitle");
        assertTestCase.assertTrue(entityProfilePage.verifySourceDocuments(), "Verify Export Source Documents");
        assertTestCase.assertTrue(entityProfilePage.downloadSourceDocument(), "Verify Source Document is opened in new tab and download");

    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, ROBOT_DEPENDENCY})
    @Xray(test = {4763})
    public void validateExportSourceDocumentsPopupClosure(){

        String company = "UFP Technologies, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

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

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, ESG})
    @Xray(test = {4803, 4048})
    public void validateExportSourceDocumentsPopupWithNoDocsMessage(){

        String company = "Apple, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");

        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        assertTestCase.assertTrue(entityProfilePage.verifyExportPopupNoDocsMessage("No source documents available."), "Verify Export Popup No Source Documents Message");
        entityProfilePage.closePopup();
    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {4041, 3975, 4040})
    public void validateExportSourceDocumentsDownload(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
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

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {4501})
    public void validateExportPdfButtonDisabledAfterClick(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadPdfButtonIsDisabled(),"Verify PDF button is disabled");

    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {4413})
    public void validateExportPdfProgressBarClose(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        entityProfilePage.waitForDataLoadCompletion();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");
        entityProfilePage.closeEntityProfilePage();
        assertTestCase.assertTrue(!entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");

        companyName = entityProfilePage.searchAndLoadClimateProfilePage("Apple, Inc");
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");
        entityProfilePage.closeDownloadProgressBar();
        assertTestCase.assertTrue(!entityProfilePage.checkDownloadProgressBarIsPresent(), "Verify download progress message is present");

    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI})
    @Xray(test = {4065})
    public void validatePdfContent(){

        String company = "Rogers Corp.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
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
        //TODO needs to be updated, summary is not first page with esg entitlements
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath,1).contains("Summary"),"Verify first page is summary page");
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath, 2).contains("Materiality"),"Verify first page is summary page");
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath, numberOfPages).contains("Disclaimer and Copyright"),"Verify first page is summary page");

        // Verify page numbers
        for(int i=1; i<=numberOfPages; i++){
            assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath,i).trim().endsWith(String.valueOf(i)),"Verify first page is summary page");
        }

    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "exportEntitlements")
    @Xray(test = {3876, 4804,4882, 5036,4456,4966,4364,5091,4525})
    public void validatePdfContentBasedOnEntitlement(String username, String password, String entitlement, String company){
        LoginPage loginPage = new LoginPage();
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<WebElement> check = Driver.getDriver().findElements(By.xpath("//div[@id='RegSector-test-id-1']"));
        System.out.println("check.size() = " + check.size());
        if (check.size() == 1) {
            entityProfilePage.pressESCKey();
            loginPage.clickOnLogout();
        }
        loginPage.loginWithParams(username, password);

//        String company = "Rogers Corp.";
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
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
        System.out.println("PDF Content: \n"+content);

        if (entitlement.equals("Physical Risk")) {
            /* Verify that User is able to see just Physical Risk Entitlement */
            assertTestCase.assertTrue(content.contains("Physical Risk"));
            assertTestCase.assertTrue(content.contains("OPERATIONS RISK"));
            assertTestCase.assertTrue(content.contains("MARKET RISK"));
            assertTestCase.assertTrue(content.contains("SUPPLY CHAIN RISK"));
            assertTestCase.assertTrue(!content.contains("Transition Risk "));
            assertTestCase.assertTrue(!content.contains("Five-year history of ESG score")); //4650
        } else if (entitlement.equals("Transition Risk")) {
            assertTestCase.assertTrue(content.contains("Transition Risk"));
            assertTestCase.assertTrue(content.contains("Temperature Alignment"));
            assertTestCase.assertTrue(content.contains("Carbon Footprint"));
            assertTestCase.assertTrue(content.contains("Green Share"));
            assertTestCase.assertTrue(content.contains("Brown Share"));
            assertTestCase.assertTrue(!content.contains("Physical Risk "));
        } else if (entitlement.equals("Physical Risk and Transition Risk")) {
            /* Verify that User is able to see just Physical Risk and Transition Risk Entitlements  */
            assertTestCase.assertTrue(content.contains("Physical Risk"));
            assertTestCase.assertTrue(content.contains("OPERATIONS RISK"));
            assertTestCase.assertTrue(content.contains("MARKET RISK"));
            assertTestCase.assertTrue(content.contains("SUPPLY CHAIN RISK"));
            assertTestCase.assertTrue(content.contains("Transition Risk"));
            assertTestCase.assertTrue(content.contains("Temperature Alignment"));
            assertTestCase.assertTrue(content.contains("Carbon Footprint"));
            assertTestCase.assertTrue(content.contains("Green Share"));
            assertTestCase.assertTrue(content.contains("Brown Share"));
            assertTestCase.assertTrue(!content.contains("Materiality: High")); //4456
            assertTestCase.assertTrue(content.contains("Controversies as of ")); //4463
        } else if (entitlement.equals("ESG")) {
            /* Verify that User is able to see just ESG   */
            assertTestCase.assertTrue(!content.contains("Physical Risk "));
            assertTestCase.assertTrue(!content.contains("Transition Risk "));
            assertTestCase.assertTrue(!content.contains("Metrics"));
            assertTestCase.assertTrue(!content.contains("Metrics Materiality: Very High")); //4966
            assertTestCase.assertTrue(!content.contains("Metrics Materiality: High")); //5036
            assertTestCase.assertTrue(!content.contains("Metrics Materiality: Moderate"));
            assertTestCase.assertTrue(!content.contains("Metrics Materiality: Low"));
            assertTestCase.assertTrue(content.contains("Key Drivers")); //4364
            assertTestCase.assertTrue(content.contains("Very high and high materiality criteria that score advanced or weak")); //4364
            assertTestCase.assertTrue(content.contains("STRENGTHS WEAKNESSES")); //4364
            assertTestCase.assertTrue(content.contains("more criteria receive a")); //5091,45858,4523
            assertTestCase.assertTrue(content.contains("Controversies as of ")); //4525
            entityProfilePage.verifyStrengthsAndWeakness(content); //5010,12276
            assertTestCase.assertTrue(content.contains("Five-year history of ESG score")); //4589
        } else if (entitlement.equals("EsgWithMethodology1Entity")) {
            assertTestCase.assertTrue(!content.contains("Key Drivers")); //4929
            assertTestCase.assertTrue(!content.contains("Very high and high materiality criteria that score advanced or weak")); //4929
            assertTestCase.assertTrue(!content.contains("STRENGTHS WEAKNESSES")); //4929
        }else if (entitlement.equals("Controversy entitlement")) {
            assertTestCase.assertTrue(content.contains("Controversies as of ")); //4463
            assertTestCase.assertTrue(content.contains("CRITICAL AND HIGH SEVERITY CONTROVERSIES SEVERITY"));
        } else if (entitlement.equals("No Controversy")) {
            assertTestCase.assertTrue(!content.contains("Controversies as of "));
            assertTestCase.assertTrue(!content.contains("CRITICAL AND HIGH SEVERITY CONTROVERSIES SEVERITY"));
        }
        loginPage.clickOnLogout();

    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, PDF})
    @Xray(test = {3207, 3138})
    public void verifyControversiesChangedToESGIncidentsEntityPDFTest(){

        String company = "Apple, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");

        String actualFileName = FileDownloadUtilities.getDownloadedFileName();

        String filePath = BrowserUtils.downloadPath() + File.separator + actualFileName;
        assertTestCase.assertFalse(PdfUtil.getPdfContent(filePath).contains("Controvers"),"Verify PDF file does not contain Controversies/Controversy");
        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath).contains("ESG Incident"),"Verify PDF file contains ESG Incident/ESG Incidents");
    }

    @Test(groups = {ENTITY_PROFILE, REGRESSION, UI, PDF, ENTITLEMENTS})
    @Xray(test = {3066})
    public void verifyUserWithoutControversiesEntitlementTest(){
        LoginPage loginPage = new LoginPage();
        loginPage.entitlementsLogin(EntitlementsBundles.USER_PR_TR_EXPORT);
        String company = "Apple, Inc.";
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        assertTestCase.assertTrue(entityProfilePage.validateGlobalCompanyNameHeader(companyName),companyName+" Header Verification");
        System.out.println("companyName = " + companyName);
        entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");

        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");

        String actualFileName = FileDownloadUtilities.getDownloadedFileName();

        String filePath = BrowserUtils.downloadPath() + File.separator + actualFileName;
        assertTestCase.assertFalse(PdfUtil.getPdfContent(filePath).contains("Controvers"),"Verify PDF file does not contain Controversies/Controversy");
//        assertTestCase.assertTrue(PdfUtil.getPdfContent(filePath).contains("ESG Incident"),"Verify PDF file contains ESG Incident/ESG Incidents");
    }

}
