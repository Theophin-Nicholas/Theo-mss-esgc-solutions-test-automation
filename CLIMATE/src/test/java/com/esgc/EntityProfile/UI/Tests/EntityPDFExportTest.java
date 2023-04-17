package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.EntityProfile.UI.Pages.PDFTestMethods;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class EntityPDFExportTest extends EntityClimateProfileTestBase {
    @Test(groups = {"PDF Export", REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Company With Orbis ID")
    @Xray(test = {9401, 9402, 9403, 9404, 9417,9475, 9476, 9755, 9465, 9929, 9930, 10139, 9907,9908,10146,10155,10157,10206
    ,10535,10536, 10671, 10678, 10681, 10191, 11152, 11154, 11442, 11157, 11885, 12015 })
    public void validatePDFExportedfile(String... entity) {

        PDFTestMethods pdfTest = new PDFTestMethods();
        pdfTest.DownloadPDFPAndGetFileContent(entity[0]);

        pdfTest.ValidateUnderlineTransitionRisk_CarbonFootprintWidget();
        pdfTest.ValidatePhysicalClimateHazard();
        pdfTest.ValidatePhysicalRiskManagement();
        pdfTest.ValidateTemperatureAlignmentSummarySection();
        pdfTest.ValidateGreenShareWidgetSummarySection();
        pdfTest.ValidateBrownShareWidgetSummarySection();
        pdfTest.ValidateStaticTextUnderTemperatureAlignmentChart();
        pdfTest.ValidatePhysicalRiskOperationRisk();
        pdfTest.ValidatePhysicalRiskMarketRisk();
        pdfTest.ValidatePhysicalRiskSupplyChainRisk();
        pdfTest.ValidateUnderlyingDataMetricsGreenShareAssessment();
        pdfTest.ValidateHeader(entity[0]);
        pdfTest.ValidateFooter();
       // pdfTest.ValidateESGSummaryData();
        pdfTest.ValidateEsgMaterlity(entity[1]); //12015

        pdfTest.ValidateUnderlyingDataTransitionRiskBROWNSHARE();
        pdfTest.ValidateCompanyIntroductionText(entity[0]);
        pdfTest.ValidatePDFShowsRegularFormatForVE(); //11885

        pdfTest.ValidateControversies(entity[1]);



    }

}
