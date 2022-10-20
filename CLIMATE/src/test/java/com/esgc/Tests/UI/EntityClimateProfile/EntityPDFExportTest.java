package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.PDFTestMethods;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

public class EntityPDFExportTest extends UITestBase {
    @Test(groups = {"PDF Export", "regression", "ui"}, dataProviderClass = DataProviderClass.class, dataProvider = "Entity")
    @Xray(test = {9401, 9402, 9403, 9404, 9417,9475, 9476, 9755, 9465, 9929, 9930, 10139})
    public void validatePDFExportedfile(String entity) {

        PDFTestMethods pdfTest = new PDFTestMethods();
        pdfTest.DownloadPDFPAndGetFileContent(entity);

        pdfTest.ValidateUnderlineTransitionRisk_CarbonFootprintWidget();
        pdfTest.ValidatePhysicalClimateHazard();
        pdfTest.ValidatePhysicalRiskManagement();
        pdfTest.ValidateTempratureAlignmentSummaryScetion();
        pdfTest.ValidateGreenShareWidgetSummarySection();
        pdfTest.ValidateBrownShareWidgetSummarySection();
        pdfTest.ValidateStaticTextUnderTemperatureAlignmentChart();
        pdfTest.ValidatePhysicalRiskOperationRisk();
        pdfTest.ValidatePhysicalRiskMarketRisk();
        pdfTest.ValidatePhysicalRiskSupplyChainRisk();
        pdfTest.ValidateUnderlyingDataMetricsGreenShareAssessment();
        pdfTest.ValidateHeader(entity);
        pdfTest.ValidateFooter();
        pdfTest.ValidateESGSummaryData();


    }

}
