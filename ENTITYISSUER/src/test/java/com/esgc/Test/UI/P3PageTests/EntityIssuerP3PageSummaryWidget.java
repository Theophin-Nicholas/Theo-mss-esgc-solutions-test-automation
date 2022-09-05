package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.TestBase.DataProviderClass;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class EntityIssuerP3PageSummaryWidget extends EntityPageTestBase {

    @Xray(test = {7438,7442,7443,8993})
    @Test(groups = {"regression", "ui", "smoke","entity_issuer"},
            dataProvider = "credentials",dataProviderClass = DataProviderClass.class,
            description = "Verify Summary Widget")
    public void ValidateSummaryWidget(String... dataProvider) {
        String userId=dataProvider[0];
        String password=dataProvider[1];
        LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
        LoginPageIssuer.loginWithParams(userId,password);
        EntityIssuerPage entitypage = new EntityIssuerPage();
        entitypage.validateSummaryWidgetISVAvailable();
        List<String> summarySections = Arrays.asList(new String[]{"ESG Score", "Environment", "Social", "Governance"});
        for (String ss : summarySections) entitypage.validateESGScoresAsNumericalValues(ss);

        /*Removing as the functionality has changed
        entitypage.validateESGScoreRatingList() ;*/
        entitypage.validateEsgScoredateFormat();

        entitypage.logout.click();
    }
}
