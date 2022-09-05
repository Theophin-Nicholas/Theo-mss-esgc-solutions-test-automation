package com.esgc.Tests.API;

import com.esgc.Tests.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

public class ExportSourceDocumentsTests extends DataValidationTestBase {

    @Test(groups = {"api", "regression"})
    @Xray(test=9229)
    public void VerifyExportSourceDocumentsAvailability() {
        dashboardAPIController.getExportSourceDocuments("000001484").then().assertThat().statusCode(200);
    }

    @Test(groups = {"api", "regression"})
    @Xray(test=9228)
    public void VerifyExportSourceDocumentsNotAvailableBundle() {
        getNoExportBundleAccessTokenDataValidation();
        dashboardAPIController.getExportSourceDocuments("000001484").then().assertThat().statusCode(403);
    }
}
