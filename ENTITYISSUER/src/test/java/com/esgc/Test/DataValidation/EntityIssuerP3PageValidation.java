package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.Controversies;
import com.esgc.APIModels.EntityIssuerPage.Header;
import com.esgc.APIModels.EntityIssuerPage.SourceDocument;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.DBModels.EntityIssuerPageDBModels.P3ControversiesDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.P3HeaderIdentifiersDBModel;
import com.esgc.DBModels.EntityIssuerPageDBModels.SourceDocumentDBModel;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityIssuerQueries;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Pages.LoginPageIssuer.OrbisID;
import static com.esgc.Utilities.Groups.ISSUER;
import static com.esgc.Utilities.Groups.REGRESSION;


public class EntityIssuerP3PageValidation extends EntityIssuerPageDataValidationTestBase {


    @Xray(test = {7352})
    @Test(groups = {REGRESSION, ISSUER})
    public void ValidateP3PageHeaderIdentifiers() {
        String orbisID = OrbisID;
        P3HeaderIdentifiersDBModel HeaderIdentifierDBResult = EntityIssuerQueries.getHeaderIdentifiers(orbisID);

        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getHeaderDetails();
        List<Header> headerAPIResponse = Arrays.asList(response.getBody().as(Header[].class));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getOrbis_id().equals(HeaderIdentifierDBResult.getORBIS_ID()));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getLei().equals(HeaderIdentifierDBResult.getLEI()));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getPrimary_isin().equals(HeaderIdentifierDBResult.getISIN()));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getCountry().equals(HeaderIdentifierDBResult.getCOUNTRY_NAME()));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getMesg_sector().equals(HeaderIdentifierDBResult.getMESG_SECTOR()));
        test.info("Validated all Header identifiers, Region and Sector successfully");

    }

    @Xray(test = 7775)
    @Test(groups = {REGRESSION, ISSUER})
    public void p3sourceDocumentTest() {
        String orbisID = OrbisID;
        List<SourceDocumentDBModel> sourceDocumentDBData = EntityIssuerQueries.getSourceDocumentDBData(orbisID);
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getSourceDocument(orbisID);
        List<SourceDocument> sourceDocumentAPIResponse = Arrays.asList(response.getBody().as(SourceDocument[].class));
        assertTestCase.assertTrue(sourceDocumentAPIResponse.size()==(sourceDocumentDBData.size()));
        for  (SourceDocument item : sourceDocumentAPIResponse) {
            SourceDocumentDBModel dbdata = sourceDocumentDBData.stream().filter(f -> f.getDocument_name().equals(item.getDocument_name())).findFirst().get();
            assertTestCase.assertTrue(dbdata.getDocument_url().equals(item.getDocument_url()), "Validate if Document Url is matched");
        }

        test.info("Validated all Source Document URLs successfully");

    }

    @Xray(test = 7407)
    @Test(groups = {REGRESSION, ISSUER})
    public void p3ControversiesTest() {
        String orbisID = OrbisID;
        List<P3ControversiesDBModel> controversiesDBData = EntityIssuerQueries.getControversiesData(orbisID);
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getControversiesAPI(orbisID);
        List<Controversies> controversiesAPIResponse = Arrays.asList(response.getBody().as(Controversies[].class));
        assertTestCase.assertTrue(controversiesAPIResponse.size()==(controversiesDBData.size()));
        for  (Controversies item : controversiesAPIResponse) {
            P3ControversiesDBModel dbdata = controversiesDBData.stream().filter(f -> f.getControversy_title().equals(item.getControversy_title())
                   && f.getTotal_controversies().equals(String.valueOf(item.getTotal_controversies()))
            ).findFirst().get();

            assertTestCase.assertTrue(dbdata.getControversy_description().equals(item.getControversy_description()), "Validate Contoversies Description");
            assertTestCase.assertTrue(dbdata.getControversy_events().equals(item.getControversy_events()), "Validate Contoversies events");
            assertTestCase.assertTrue(dbdata.getSeverity().equals(item.getSeverity()), "Validate Contoversies Severity");
        }

        test.info("Validated Controversies data successfully");

    }

    //TODO This feature is not available as of now for validation
    @Xray(test = {9727})
    @Test(enabled = false ,groups = {REGRESSION, ISSUER})
    public void ValidateP3PageHeaderOverallDisclosureRatio() {
        String orbisID = OrbisID;
        int OverallDisclosureRatio = EntityIssuerQueries.getOverallDisclosureRatio(orbisID);

        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getHeaderDetails();
        List<Header> headerAPIResponse = Arrays.asList(response.getBody().as(Header[].class));
        assertTestCase.assertTrue(headerAPIResponse.get(0).getOverall_disclosure_score()==OverallDisclosureRatio,"Validate OverAll Disclousure Ratio ");
        test.info("Validated Overal lDisclosure Ratio successfully");

    }


}
