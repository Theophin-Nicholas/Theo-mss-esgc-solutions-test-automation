package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.SourceDocument;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.DBModels.EntityIssuerPageDBModels.SourceDocumentDBModel;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityIssuerQueries;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Pages.LoginPageIssuer.*;

public class SourceDocumentDBValidation extends EntityIssuerPageDataValidationTestBase {
    List<String> criteria = new ArrayList<>();

    @Xray(test = 6968)
    @Test(groups = {"regression", "entity_issuer"})
    public void sourceDocumentTest() {
        String orbisID = OrbisID;
        List<SourceDocumentDBModel> sourceDocumentDBData = EntityIssuerQueries.getSourceDocumentDBData(orbisID);
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getSourceDocument(orbisID);
        List<SourceDocument> sourceDocumentAPIResponse = Arrays.asList(response.getBody().as(SourceDocument[].class));
        assertTestCase.assertTrue(sourceDocumentAPIResponse.size()==(sourceDocumentDBData.size()));
        for  (SourceDocument item : sourceDocumentAPIResponse) {
                SourceDocumentDBModel dbdata = sourceDocumentDBData.stream()
                        .filter(f -> f.getDocument_name().equals(item.getDocument_name()))
                        .filter(x -> x.getDocument_url().equals(item.getDocument_url())).findFirst().get();
                assertTestCase.assertEquals(dbdata.getDocument_url(), item.getDocument_url(),
                        "Validate if Document Url is matched");
        }

        test.info("Validated all Source Document URLs successfully");

    }




}
