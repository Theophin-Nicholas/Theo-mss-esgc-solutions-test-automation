package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.EntityFilterPayload;
import com.esgc.APIModels.EntityPage.EntityControversy;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ControversiesTestsP3 extends EntityIssuerPageDataValidationTestBase {

    @Test(enabled = false, groups = {"entity_issuer"})
    @Xray(test = {4354, 4355})
    public void verifyControversies() {
        List<EntityControversy> dbResults = entityIssuerQueries.getControversies("000002959", null);

        EntityFilterPayload entityAPIFilterPayload = new EntityFilterPayload(null);
        Response response = controller.getControversies(entityAPIFilterPayload);
        response.prettyPeek();
        List<EntityControversy> apiResultsList = Arrays.asList(response.getBody().as(EntityControversy[].class));

        Assert.assertEquals(apiResultsList.size(), dbResults.size());

        for (int i = 0; i < apiResultsList.size(); i++) {
            boolean found = false;
            for (int j = 0; j < dbResults.size(); j++) {
                if (apiResultsList.get(i).equals(dbResults.get(j))) {
                    found = true;
                    break;
                }
            }
            System.out.println("Found element" + i);
            Assert.assertTrue(found);
        }
        test.info("Entity Page Controversies test passed");
    }

    @Test(enabled = false, groups = {"entity_issuer"})
    @Xray(test = {4461, 4469})
    public void verifyRelatedControversies() {
        //EntityFilterPayload entityFilterPayload = new EntityFilterPayload("C&S1.2");
        EntityFilterPayload entityFilterPayload = new EntityFilterPayload("CIN2.1");
        List<EntityControversy> dbResults = entityIssuerQueries.getControversies("000002959", entityFilterPayload);

        Response response = controller.getControversies(entityFilterPayload);
        List<EntityControversy> apiResultsList = Arrays.asList(response.getBody().as(EntityControversy[].class));

        for (int i = 0; i < apiResultsList.size(); i++) {
            boolean found = false;
            for (int j = 0; j < dbResults.size(); j++) {
                if (apiResultsList.get(i).equals(dbResults.get(j))) {
                    found = true;
                    break;
                }
            }
            System.out.println("Found element" + i);
            Assert.assertTrue(found);
        }
        test.info("Related controversies test passed");
    }

}
