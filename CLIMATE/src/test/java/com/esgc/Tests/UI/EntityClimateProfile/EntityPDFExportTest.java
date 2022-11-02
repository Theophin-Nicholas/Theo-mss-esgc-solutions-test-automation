package com.esgc.Tests.UI.EntityClimateProfile;

import com.esgc.Pages.PDFTestMethods;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.*;
import org.testng.annotations.Test;

public class EntityPDFExportTest extends UITestBase {
    @Test(groups = {"PDF Export", "regression", "ui"}, dataProviderClass = DataProviderClass.class, dataProvider = "Company With Orbis ID")
    @Xray(test = {9401, 9402, 9403, 9404, 9417,9475, 9476, 9755, 9465, 9929, 9930, 10139, 9907,9908,10146,10155,10157,10206})
    public void validatePDFExportedfile(String... entity) {

        PDFTestMethods pdfTest = new PDFTestMethods();
        pdfTest.DownloadPDFPAndGetFileContent(entity[0]);

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
        pdfTest.ValidateHeader(entity[0]);
        pdfTest.ValidateFooter();
        pdfTest.ValidateESGSummaryData();
        pdfTest.ValidateEsgMaterlity(entity[1]);
    }

}
