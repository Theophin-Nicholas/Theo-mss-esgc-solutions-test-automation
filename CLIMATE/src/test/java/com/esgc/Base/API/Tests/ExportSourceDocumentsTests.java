package com.esgc.Base.API.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import static com.esgc.Utilities.Groups.API;
import static com.esgc.Utilities.Groups.REGRESSION;

public class ExportSourceDocumentsTests extends DataValidationTestBase {

    @Test(groups = {API, REGRESSION})
    @Xray(test=9229)
    public void VerifyExportSourceDocumentsAvailability() {
        dashboardAPIController.getExportSourceDocuments("000001484").then().assertThat().statusCode(200);
    }

    @Test(groups = {API, REGRESSION})
    @Xray(test=9228)
    public void VerifyExportSourceDocumentsNotAvailableBundle() {
        getNoExportBundleAccessTokenDataValidation();
        dashboardAPIController.getExportSourceDocuments("000001484").then().assertThat().statusCode(403);
    }
}
