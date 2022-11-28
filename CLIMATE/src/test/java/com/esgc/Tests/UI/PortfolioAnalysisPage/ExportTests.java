package com.esgc.Tests.UI.PortfolioAnalysisPage;

import com.esgc.APIModels.APIFilterPayload;
import com.esgc.Controllers.APIController;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison.BrownShareAssessment;
import com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison.CarbonFootprint;
import com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison.EsgAssessment;
import com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison.GreenShareAssessment;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.Xray;
import org.apache.commons.io.FileUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;


public class ExportTests extends UITestBase {
//TODO Needs attention. Tests are failing.
    @Test(groups = {"regression", "smoke", "export"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2092, 2093, 2108, 2110, 2112, 2113, 2625, 3004, 3396, 3403, 3405, 3406, 4648, 5169, 3621, 8953, 9656, 9657, 9658, 10679 })
    public void verifyExportFunctionalityandExcelFieldsWithUI(String researchLine) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        if (researchLine.equals("Physical Risk Hazards") || researchLine.equals("Temperature Alignment")
            || researchLine.equals("Physical Risk Management")) {
            throw new SkipException("Physical Risk Hazards - Export is not ready to test in " + researchLine);
        }

        researchLinePage.navigateToResearchLine(researchLine);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "May 2022");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.clickExportDropdown();

        researchLinePage.selectExportData("Data", researchLine);

        assertTestCase.assertTrue(researchLinePage.checkIfExportingLoadingMaskIsDisplayed(),
                "Exporting... Load Mask is displayed", 2620);

        researchLine = researchLine.replaceAll("Management", "Mgmt");
        String expectedTitle = String.format("Portfolio Analysis - %s", researchLine);
        BrowserUtils.wait(5);
        String sheetName = String.format("Summary %s", researchLine);

        if(researchLine.equals("ESG Assessments")) {
            sheetName = "Data - ESG";
            researchLine = "ESG Assessment";
            expectedTitle = "Entity Name";
        }
        Assert.assertEquals(researchLinePage.getDataFromExportedFile(0, 0, researchLine), expectedTitle,
                "Exported Document verified by title for " + researchLinePage + " page");

        ExcelUtil exportedDocument = new ExcelUtil(BrowserUtils.exportPath(researchLine), sheetName);

        getExistingUsersAccessTokenFromUI();

        APIController controller = new APIController();
        String portfolio_id = "00000000-0000-0000-0000-000000000000";
        APIFilterPayload payload = new APIFilterPayload("all", "all", "05", "2022", "");

        switch (researchLine) {
            case "Carbon Footprint":
                System.out.println("CARBON FOOTPRINT TESTING STARTED");
                test.info("CARBON FOOTPRINT TESTING STARTED");
                new CarbonFootprint().verifyCarbonFootPrint(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            case "Green Share Assessment":
                System.out.println("GREEN SHARE ASSESSMENT TESTING STARTED");
                test.info("GREEN SHARE ASSESSMENT TESTING STARTED");
                new GreenShareAssessment().verifyGreenShareAssessment(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            case "Brown Share Assessment":
                System.out.println("BROWN SHARE ASSESSMENT TESTING STARTED");
                test.info("BROWN SHARE ASSESSMENT TESTING STARTED");
                new BrownShareAssessment().verifyBrownShareAssessment(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            case "ESG Assessment":
                System.out.println("ESG Assessment TESTING STARTED");
                test.info("ESG Assessment TESTING STARTED");
                new EsgAssessment().verifyEsgAssessment(researchLine, portfolio_id, "2022","05");
                break;
            default:
                test.fail("Failed to get any Research line");
                System.out.println("Failed to get any Research line");
                break;
        }
    }

    @Test(groups = {"regression", "export"}, dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {7254})
    public void verifyUserIsAbleToDownloadPDFTest(String researchLine) {
        System.out.println("PDF Download test for " + researchLine);
        ResearchLinePage researchLinePage = new ResearchLinePage();

        if (researchLine.equals("ESG Assessments") || researchLine.equals("Physical Risk Hazards") || researchLine.equals("Temperature Alignment")) {
            throw new SkipException("Physical Risk Hazards - Export is not ready to test in " + researchLine);
        }

        researchLinePage.navigateToResearchLine(researchLine);

        researchLinePage.clickExportDropdown();

        researchLinePage.selectExportData("pdf", researchLine);

        try {
            while (researchLinePage.exportingLoadMask.isDisplayed()) {
                BrowserUtils.wait(1);
            }
        } catch (Exception e) {
            System.out.println("File download is complete");
        }

        String timeStamp = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        System.out.println("timeStamp = " + timeStamp);

        researchLine = researchLine + "_" + timeStamp;
        System.out.println("researchLine = " + researchLine);
        System.out.println("BrowserUtils.downloadPath() = " + BrowserUtils.downloadPath());
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        for (File file : dir_contents) {
            if (file.getName().startsWith(researchLine)) {
                System.out.println("File found");
                System.out.println("file.getName() = " + file.getName());
                assertTestCase.assertTrue(file.getName().startsWith(researchLine),
                        "PDF file is downloaded", 7254);
                //file.delete();//Already deleted automatically by after-method
            }
        }
    }

    @Test(groups = {"regression", "smoke", "export"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {2883, 2962})
    public void verifyBenchMarkExportFunctionalityandExcelFieldsWithUI(String researchLine) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        if (researchLine.equals("ESG Assessments") || researchLine.equals("Physical Risk Hazards") || researchLine.equals("Temperature Alignment")
                || researchLine.equals("Physical Risk Management")) {
            throw new SkipException("Export is not ready to test in " + researchLine);
        }

        researchLinePage.navigateToResearchLine(researchLine);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();
        researchLinePage.clickFiltersDropdown();
        researchLinePage.selectOptionFromFiltersDropdown("as_of_date", "May 2022");
        researchLinePage.clickOnBenchmarkDropdown()
                .SelectAPortfolioFromBenchmark("Sample Portfolio");
        researchLinePage.waitForDataLoadCompletion();
        researchLinePage.clickExportDropdown();

        researchLinePage.selectExportData("Data", researchLine);

        assertTestCase.assertTrue(researchLinePage.checkIfExportingLoadingMaskIsDisplayed(),
                "Exporting... Load Mask is displayed", 2620);

        researchLine = researchLine.replaceAll("Management", "Mgmt");
        String expectedTitle = String.format("Portfolio Analysis - %s", researchLine);
        BrowserUtils.wait(5);
        Assert.assertEquals(researchLinePage.getDataFromExportedFile(0, 0, researchLine), expectedTitle,
                "Exported Document verified by title for " + researchLinePage + " page");

        String sheetName = String.format("Summary %s", researchLine);

        ExcelUtil exportedDocument = new ExcelUtil(BrowserUtils.exportPath(researchLine), sheetName);

        getExistingUsersAccessTokenFromUI();

        APIController controller = new APIController();
        String portfolio_id = "00000000-0000-0000-0000-000000000000";
        APIFilterPayload payload = new APIFilterPayload("all", "all", "05", "2022", "");

        switch (researchLine) {
            case "Carbon Footprint":
                System.out.println("CARBON FOOTPRINT TESTING STARTED");
                test.info("CARBON FOOTPRINT TESTING STARTED");
                new CarbonFootprint().verifyCarbonFootPrintBenchMarkSectoins(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            case "Green Share Assessment":
                System.out.println("GREEN SHARE ASSESSMENT TESTING STARTED");
                test.info("GREEN SHARE ASSESSMENT TESTING STARTED");
                new GreenShareAssessment().verifyGreenShareAssessmentBenchMarkSections(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            case "Brown Share Assessment":
                System.out.println("BROWN SHARE ASSESSMENT TESTING STARTED");
                test.info("BROWN SHARE ASSESSMENT TESTING STARTED");
                new BrownShareAssessment().verifyBrownShareAssessmentBenchMarkSections(researchLine, portfolio_id, controller, payload, exportedDocument);
                break;
            default:
                test.fail("Failed to get any Research line");
                System.out.println("Failed to get any Research line");
                break;
        }
    }

    @AfterClass
    public void deleteDownloadFolder() {
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        if (dir_contents != null) {
            for (int i = 0; i < dir_contents.length; i++) {
                if (dir_contents[i].isDirectory()) {
                    try {
                        FileUtils.deleteDirectory(dir_contents[i]);
                    } catch (IOException e) {
                        System.out.println("Folder is empty");
                        System.out.println("Failed");
                        e.printStackTrace();
                    }
                } else {
                    dir_contents[i].delete();
                }
            }
            dir.delete();
        }else
            System.out.println("There is no Directory to DELETE");
    }

    @Test(groups = {"regression", "export"},
            dataProviderClass = DataProviderClass.class, dataProvider = "Research Lines")
    @Xray(test = {7253})
    public void verifyResearchLinesExportOptions(String researchLine) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        if (researchLine.equals("Physical Risk Hazards") || researchLine.equals("Temperature Alignment")
                || researchLine.equals("Physical Risk Management")) {
            throw new SkipException("Export is not ready to test in " + researchLine);
        }

        researchLinePage.navigateToResearchLine(researchLine);
        researchLinePage.selectSamplePortfolioFromPortfolioSelectionModal();

        researchLinePage.clickExportDropdown();
        researchLinePage.verifyExportOptions(researchLine);
        researchLinePage.clickOutsideOfDrillDownPanel();
    }
}