package com.esgc.Pages;



import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.FileDownloadUtilities;
import com.esgc.Utilities.PdfUtil;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;





public class PDFTestMethods extends PageBase {
    String pdfFileText = "";
    int documentPageCounts = 0;

    public void DownloadPDFPAndGetFileContent(String entity) {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
       String company = entity;
        String companyName = entityProfilePage.searchAndLoadClimateProfilePage(company);
        System.out.println("companyName = " + companyName);

     /*   // Temp code to remove ------------
        String actualFileName = "";
        if (entity.contains("Apple")){
            actualFileName = "Apple_ Inc_Profile18-Oct-2022_1666110287474.pdf";
        } else if (entity.contains("Samsung")){
            actualFileName = "Samsung Securities Co__ Ltd_Profile17-Oct-2022_1666021188001.pdf";
        }else if (entity.contains("Alibaba")){
            actualFileName = "Alibaba Health Information Technology Ltd_Profile18-Oct-2022_1666115181989.pdf";
        }else if (entity.contains("Development")) {
            actualFileName = "Development Bank of Japan_ Inc_Profile18-Oct-2022_1666116348194.pdf";
        }

        // Temp remove till here  and Uncomment below code after removal of temp code above ----- ----------*/

         entityProfilePage.selectExportSourcesDocuments();
        assertTestCase.assertTrue(entityProfilePage.verifyPopup(), "Verify Export Sources Documents popup");
        assertTestCase.assertTrue(entityProfilePage.verifyPopupTitle(companyName), "Verify Export Popup Title");
        FileDownloadUtilities.deleteDownloadFolder();
        entityProfilePage.selectPdfDownload();
        assertTestCase.assertTrue(entityProfilePage.verifyDownloadProgressMessage(), "Verify download progress message");
        assertTestCase.assertTrue(FileDownloadUtilities.waitUntilFileIsDownloaded(),"Verify download of export file");
        String actualFileName = FileDownloadUtilities.getDownloadedFileName() ;

        String filePath = BrowserUtils.downloadPath()+"/"+actualFileName;
        pdfFileText=  PdfUtil.getPdfContent(filePath);
        documentPageCounts = PdfUtil.getNumberOfPages(filePath);

    }

    public void ValidateUnderlineTransitionRisk_CarbonFootprintWidget(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();

        List<String> expectedUIList = entityProfilePage.getUnderlyingCarbonPrintDetails();
        String content = PdfUtil.extractPDFText(pdfFileText, "Underlying Data\nTransition Risk\nCARBON FOOTPRINT",
                new StringBuilder().append(expectedUIList.get(expectedUIList.size() - 3))
                        .append("\n").append(expectedUIList.get(expectedUIList.size() - 2))
                        .append("\n").append(expectedUIList.get(expectedUIList.size() - 1)).toString());
        System.out.println(content);
       //String[] sourceList = expectedUIList.stream().filter(f-> f.startsWith("Source")).findFirst().get().split(" ");
        List<String> list = Arrays.asList(content.split("\n"));
        for (int i = 3; i < 7; i++) {
            System.out.println("Validating " + list.get(i));
            assertTestCase.assertTrue(list.get(i).equals(expectedUIList.get(i - 3)), "Validate " + list.get(i));
        }
    }

    public void ValidatePhysicalClimateHazard(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getPhysicalClimateHazards();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Climate Hazard widget" );
    }
    public void ValidatePhysicalRiskManagement(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getPhysicalRiskManagement();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Risk Management widget" );
    }

    public void ValidateTempratureAlignmentSummaryScetion(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getTempratureAlignmentWidget();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Temprature Alignment Summary Scetion" );
    }
    public void ValidateGreenShareWidgetSummarySection(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getGreenShareWidget();
        if (UIValue.contains("-")){
            UIValue = "Offering Green Solutions" ;
        }
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating GreenShare Widget Summary Section" );
    }

    public void ValidateBrownShareWidgetSummarySection(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getBrownShareWidget();
        if (UIValue.contains("-")){
            UIValue = "Brown Share Overall Fossil Fuels Industry Revenues" ;
        }
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Brown Share Widget Summary Section" );
    }
    public void ValidateStaticTextUnderTemperatureAlignmentChart(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        if (entityProfilePage.noInfoElementChart.size()<1) {
            String UIValue = "This extrapolation is a theoretical decarbonization trend based on the company’s targeted reduction between its stated base year and the target year. This trend, therefore, indicates the emissions reduction that the company aims to achieve and can be used to assess the ambitiousness of a company's target in comparison to the benchmark trajectories shown.";
            String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
            if (!UIValue.contains("No information available."))
                assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating StaticText Under Temperature Alignment Chart");
        }
    }

    public void ValidatePhysicalRiskOperationRisk(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getPhysicalRiskOperationRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating PhysicalRisk OperationRisk" );
    }
    public void ValidatePhysicalRiskMarketRisk(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getPhysicalRiskMarketRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
        assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Physical Risk MarketRisk" );
    }

    public void ValidatePhysicalRiskSupplyChainRisk(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getPhysicalRiskSupplyChainRisk();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating PhysicalRisk SupplyChainRisk" );
    }
    public void ValidateUnderlyingDataMetricsGreenShareAssessment(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  entityProfilePage.getunderlyingDataMetricsGreenShareAssessment();
        String content = PdfUtil.extractPDFText(pdfFileText, UIValue);
        if (!UIValue.contains("No information available."))
            assertTestCase.assertTrue(!content.equals("No ParaGraph Found"), "Validating Underlying DataMetrics GreenShare Assessment" );
    }
    public void ValidateHeader(String companyName){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<String> UIValue =  entityProfilePage.getHederValues(companyName);
        int count = PdfUtil.getCountofTextOccurrencesInPdf(pdfFileText, UIValue.get(0).toString() + " " +UIValue.get(1).toString());
        assertTestCase.assertTrue(documentPageCounts-1==count, "Validating Header" );
    }

    public void ValidateFooter(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        String UIValue =  "MOODY'S ESG360TM ENTITY PAGE" ;
        int count = PdfUtil.getCountofTextOccurrencesInPdf(pdfFileText, UIValue);
        assertTestCase.assertTrue(documentPageCounts==count, "Validating Footer" );
    }
    public void ValidateESGSummaryData(){
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        List<String> UIValue =  entityProfilePage.getESGSummaryDetails();
        for(String v : UIValue) {
            String PDFValue = PdfUtil.extractPDFText(pdfFileText, v);
            assertTestCase.assertTrue(v.equals(PDFValue), "Validating ESGSummary Data");
        }
    }
}
