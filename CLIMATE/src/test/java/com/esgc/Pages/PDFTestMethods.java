package com.esgc.Pages;


import com.esgc.APIModels.EntityProfilePageModels.ESGMateriality.ESGMaterlityDriverSummaryAPIWrapper;
import com.esgc.APIModels.EntityProfilePageModels.ESGMateriality.ESGMaterlityDriverSummaryDetails;
import com.esgc.Controllers.EntityPage.EntityProfileClimatePageAPIController;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.FileDownloadUtilities;
import com.esgc.Utilities.PdfUtil;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.JavascriptExecutor;

import java.util.*;


public class PDFTestMethods extends PageBase {
    String pdfFileText = "";
    int documentPageCounts = 0;

    public void DownloadPDFPAndGetFileContent(@NotNull String entity) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String company = entity;
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        System.out.println("companyName = " + companyName);

      /*  // Temp code to remove ------------
        String actualFileName = "";
        if (entity.contains("Apple")) {
            actualFileName = "Apple_ Inc_Profile27-Oct-2022_1666901863015.pdf"; // "Akero Therapeutics_ Inc_Profile3-Oct-2022_1664804963663.pdf";
        } else if (entity.contains("Samsung")) {
            actualFileName = "Samsung Securities Co__ Ltd_Profile27-Oct-2022_1666916628592.pdf";
        } else if (entity.contains("Alibaba")) {
            actualFileName = "Alibaba Health Information Technology Ltd_Profile27-Oct-2022_1666916991607.pdf";
        } else if (entity.contains("Development")) {
            actualFileName = "Development Bank of Japan_ Inc_Profile27-Oct-2022_1666917187351.pdf";
        }else if (entity.contains("NMI")) {
            actualFileName = "NMI Holdings_ Inc_Profile27-Oct-2022_1666881615951.pdf";
        }else if (entity.contains("Lexicon")) {
            actualFileName = "Lexicon Pharmaceuticals_ Inc_Profile27-Oct-2022_1666884413598.pdf";
        }*/

        // Temp remove till here  and Uncomment below code after removal of temp code above ----- ----------

         entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");
        String actualFileName = FileDownloadUtilities.getDownloadedFileName() ;

        String filePath = BrowserUtils.downloadPath() + "/" + actualFileName;
        pdfFileText = PdfUtil.getPdfContent(filePath);
        documentPageCounts = PdfUtil.getNumberOfPages(filePath);

    }

    public void ValidateUnderlineTransitionRisk_CarbonFootprintWidget() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();

       // List<String> expectedUIValues = entityProfilePage.getUnderlyingCarbonPrintDetails();
        String expectedUIValues = entityProfilePage.getUnderlyingCarbonPrintDetails();
        /*String content = PdfUtil.extractPDFText(pdfFileText, "Underlying Data\nTransition Risk\nCARBON FOOTPRINT",
                new StringBuilder().append(expectedUIValues.get(expectedUIValues.size() - 3))
                        .append("\n").append(expectedUIValues.get(expectedUIValues.size() - 2))
                        .append("\n").append(expectedUIValues.get(expectedUIValues.size() - 1)).toString());*/
        String content = PdfUtil.extractPDFText(pdfFileText, expectedUIValues) ;
       /* System.out.println(content);
        List<String> list = Arrays.asList(content.split("\n"));
        for (int i = 3; i < 7; i++) {
            System.out.println("Validating " + list.get(i));*/
            //assertTestCase.assertTrue(list.get(i).equals(expectedUIValues.get(i - 3)), "Validate " + list.get(i));
        //}

        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Climate Hazard widget");
    }

    public void ValidatePhysicalClimateHazard() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getPhysicalClimateHazards();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Climate Hazard widget");
    }

    public void ValidatePhysicalRiskManagement() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getPhysicalRiskManagement();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Risk Management widget");
    }

    public void ValidateTempratureAlignmentSummaryScetion() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getTempratureAlignmentWidget();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Temprature Alignment Summary Scetion");
    }

    public void ValidateGreenShareWidgetSummarySection() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getGreenShareWidget();
        if (UIValue.contains("-")) {
            UIValue = "Offering Green Solutions";
        }
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating GreenShare Widget Summary Section");
    }

    public void ValidateBrownShareWidgetSummarySection() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getBrownShareWidget().replace("Brown Share " , "");
        if (UIValue.contains("-")) {
            UIValue = "Overall Fossil Fuels Industry Revenues";
        }
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Brown Share Widget Summary Section");
    }

    public void ValidateStaticTextUnderTemperatureAlignmentChart() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        if (entityProfilePage.noInfoElementChart.size() < 1) {
            String UIValue = "This extrapolation is a theoretical decarbonization trend based on the company’s targeted reduction between its stated base year and the target year. This trend, therefore, indicates the emissions reduction that the company aims to achieve and can be used to assess the ambitiousness of a company's target in comparison to the benchmark trajectories shown.";
            String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
            if (!UIValue.contains("No information available."))
                assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating StaticText Under Temperature Alignment Chart");
        }
    }

    public void ValidatePhysicalRiskOperationRisk() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getPhysicalRiskOperationRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating PhysicalRisk OperationRisk");
    }

    public void ValidatePhysicalRiskMarketRisk() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getPhysicalRiskMarketRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Risk MarketRisk");
    }

    public void ValidatePhysicalRiskSupplyChainRisk() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getPhysicalRiskSupplyChainRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating PhysicalRisk SupplyChainRisk");
    }

    public void ValidateUnderlyingDataMetricsGreenShareAssessment() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = entityProfilePage.getunderlyingDataMetricsGreenShareAssessment();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (content.equals("No ParaGraph Found")){
            UIValue = UIValue.replace("GREEN SHARE", "GREENSHARE");
            content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        }
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Underlying DataMetrics GreenShare Assessment");
    }

    public void ValidateHeader(String companyName) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<String> UIValue = entityProfilePage.getHederValues(companyName);
        int count = PdfUtil.getCountofTextOccurrencesInPdf(pdfFileText, UIValue.get(0).toString() + " " + UIValue.get(1).toString());
        assertTestCase.assertTrue(documentPageCounts - 1 == count, "Validating Header");
    }

    public void ValidateFooter() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue = "MOODY'S ESG360TM ENTITY PAGE";
        int count = PdfUtil.getCountofTextOccurrencesInPdf(pdfFileText, UIValue);
        assertTestCase.assertTrue(documentPageCounts == count, "Validating Footer");
    }

    public void ValidateESGSummaryData() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<String> UIValue = entityProfilePage.getESGSummaryDetails();
        for (String v : UIValue) {
            String PDFValue = PdfUtil.extractPDFText(pdfFileText, v);
            assertTestCase.assertTrue(v.equals(PDFValue), "Validating ESGSummary Data");
        }
    }

    public void ValidateEsgMaterlity(String orbis_id) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<String> UIValue = entityProfilePage.readEsgMaterialityCategories();
        String ESGHeaders = "Materiality: Very High Materiality: High Materiality: Moderate Materiality: Low";
        String PDFValue = PdfUtil.extractPDFText(pdfFileText, ESGHeaders);
        assertTestCase.assertTrue(ESGHeaders.equals(PDFValue), "Validating Materlity Data Headers");
        EntityProfileClimatePageAPIController entityClimateProfileApiController = new EntityProfileClimatePageAPIController();
        String getAccessTokenScript = "return JSON.parse(localStorage.getItem('okta-token-storage')).accessToken.accessToken";
        String accessToken = ((JavascriptExecutor) Driver.getDriver()).executeScript(getAccessTokenScript).toString();
        System.setProperty("token", accessToken);
        ESGMaterlityDriverSummaryAPIWrapper APIdata = entityClimateProfileApiController
                .getEntityMaterialityMetrics(orbis_id).as(ESGMaterlityDriverSummaryAPIWrapper.class);
        String Methodology = APIdata.getMethodology();
        List<String> veryHigh = new ArrayList<>();
        List<String> High = new ArrayList<>();
        List<String> Moderate = new ArrayList<>();
        List<String> Low = new ArrayList<>();
        for (String v : UIValue) {
            v = v.replaceAll("\\d+", "");
            PDFValue = PdfUtil.extractPDFText(pdfFileText, v);
            assertTestCase.assertTrue(v.equals(PDFValue), "Validating Materlity Data");
            String CriteriaName = v;
            ESGMaterlityDriverSummaryDetails DriverDetails = APIdata.getDrivers().stream().filter(i -> i.getCriteria_name().trim().equalsIgnoreCase(CriteriaName.trim())).findFirst().get();

            switch (DriverDetails.getDriver_weights().stream().filter(i-> i.getMateriality_type().equals("Dual")).findFirst().get().getMateriality_weight()) {
                case "Very High":
                    veryHigh.add(CriteriaName + "|" + DriverDetails.getScore_category() + "|" + DriverDetails.getIndicator() + "|" + DriverDetails.getDisclosure_ratio());
                    break;
                case "High":
                    High.add(CriteriaName + "|" + DriverDetails.getScore_category() + "|" + DriverDetails.getIndicator() + "|" + DriverDetails.getDisclosure_ratio());
                    break;
                case "Moderate":
                    Moderate.add(CriteriaName + "|" + DriverDetails.getScore_category() + "|" + DriverDetails.getIndicator() + "|" + DriverDetails.getDisclosure_ratio());
                    break;
                case "Low":
                    Low.add(CriteriaName + "|" + DriverDetails.getScore_category() + "|" + DriverDetails.getIndicator() + "|" + DriverDetails.getDisclosure_ratio());
                    break;
            }

        }
        String expectedHeader ="";
        String expectedMatricsTitle ="";
        if (Methodology.equals("ve")){
            expectedHeader= "Criteria Performance ESG Component";
            expectedMatricsTitle = "Criteria Materiality" ;
        } else if (Methodology.equals("mesg")) {
            // TODO  Change expectedHeader in below line according to ESGCA-11528 fix
            expectedHeader= "Subcategories Performance Disclosure Ratio ESG Component Weight:Business Stakeholder";
            expectedMatricsTitle = "Subcategory Materiality" ;
        }

        int headerCount = PdfUtil.getCountofTextOccurrencesInPdf(pdfFileText, expectedHeader);
        int expectedCount = 0;
        expectedCount = (veryHigh.size()>0? expectedCount+1:expectedCount);
        expectedCount = (High.size()>0? expectedCount+1 : expectedCount);
        expectedCount = (Moderate.size()>0? expectedCount+1 : expectedCount);
        assertTestCase.assertTrue(headerCount>=expectedCount, "Validating Materlity Header Count");
        assertTestCase.assertTrue(!PdfUtil.extractPDFText(pdfFileText,expectedMatricsTitle).equals("No ParaGraph Found"), "Validating Materlity Metrics Title");

        for (String e : veryHigh) validateMaterlityMetrics(e,Methodology);
        for (String e : High) validateMaterlityMetrics(e,Methodology);
        for (String e : Moderate) validateMaterlityMetrics(e,Methodology);

    }
    public void validateMaterlityMetrics(@NotNull String metricValue,String methodology){
        String[] splitedList = metricValue.split("\\|");
        String regex = "" ;
        if (methodology.equals("ve")) regex = ".*" + splitedList[0].toString() + ".*" + splitedList[1].toString() + ".*" + splitedList[2].toString() + ".*";
       else if(methodology.equals("mesg")) regex = ".*" + splitedList[0].toString() + ".*" + splitedList[1].toString() + ".*" + splitedList[3].toString() + ".*" + splitedList[2].toString() + ".*";
        assertTestCase.assertTrue(PdfUtil.ifMatchingWithRegex(pdfFileText, regex));
    }



}
