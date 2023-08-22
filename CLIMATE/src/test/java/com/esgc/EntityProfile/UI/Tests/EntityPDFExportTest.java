package com.esgc.EntityProfile.UI.Tests;

import com.esgc.Base.TestBases.EntityClimateProfileTestBase;
import com.esgc.EntityProfile.UI.Pages.PDFTestMethods;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.REGRESSION;
import static com.esgc.Utilities.Groups.UI;

public class EntityPDFExportTest extends EntityClimateProfileTestBase {
    @Test(groups = {"PDF Export", REGRESSION, UI}, dataProviderClass = DataProviderClass.class, dataProvider = "Company With Orbis ID")
    @Xray(test = {4024, 3980, 3997, 3881, 4507,4789, 4500, 4458, 4395, 4390, 5014, 4508, 4971,4963,4842,4408,4369,4518
    ,3962,4817, 3936, 4105, 4587, 4066, 4895, 4886, 5060, 4984, 4891, 5036 })
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
        pdfTest.ValidateEsgMaterlity(entity[1]); //5036

        pdfTest.ValidateUnderlyingDataTransitionRiskBROWNSHARE();
        pdfTest.ValidateCompanyIntroductionText(entity[0]);
        pdfTest.ValidatePDFShowsRegularFormatForVE(); //4891

        pdfTest.ValidateControversies(entity[1]);



    }

}
