package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.ScoreCategory;
import com.esgc.APIModels.EntityIssuerPage.Summary;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.DBModels.EntityIssuerPageDBModels.SummaryWidgetDBModel;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityIssuerQueries;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.esgc.Pages.LoginPageIssuer.OrbisID;
import static com.esgc.Utilities.DateTimeUtilities.getFormattedDate;

public class ESGScoreSummaryWidgetDBValidationP3 extends EntityIssuerPageDataValidationTestBase {

    @Xray(test = {7439,7441,7444})
    @Test(groups = {"regression", "entity_issuer"})
    public void SummaryWidgetTest() {
        String orbisID = OrbisID;
        List<SummaryWidgetDBModel> summaryWidgetDBResult = EntityIssuerQueries.getESGScoreSummaryWidgetData(orbisID);
        EntityIssuerPageAPIController entityIssuerPageAPIController = new EntityIssuerPageAPIController();
        Response response = entityIssuerPageAPIController.getSummaryWidget(orbisID);
        List<Summary> summaryWidgetAPIResponse = Arrays.asList(response.getBody().as(Summary[].class));

        assertTestCase.assertTrue(getFormattedDate(summaryWidgetDBResult.get(0).getSCORED_TIMESTAMP().
                substring(0,10),"MMMM dd, yyyy").equals(summaryWidgetAPIResponse.get(0)
                .getLast_updated()),"Validate updated Date");

        for  (ScoreCategory item : summaryWidgetAPIResponse.get(0).getScore_categories()) {
            SummaryWidgetDBModel dbdata = summaryWidgetDBResult.stream().filter(f -> f.getCATEGORY().equals(item.getCriteria())).findFirst().get();
            if (item.getCriteria().equals("ESG Rating"))
                assertTestCase.assertTrue(dbdata.getVALUE().equals(item.getScore_msg()), "Validate if Summary rank is matched");
            else
                assertTestCase.assertTrue(Math.round(Double.valueOf(dbdata.getVALUE()))==Math.round(item.getScore()), "Validate if Summary score is matched");
        }

        test.info("Validated all ESG Summary Widget successfully");

    }




}
