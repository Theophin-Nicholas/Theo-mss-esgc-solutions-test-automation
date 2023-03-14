package com.esgc.Test.UI.P3PageTests;

import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class EntityIssuerP3PageSummaryWidget extends EntityPageTestBase {

    @Xray(test = {7438,7442,7443,8993,9721})
    @Test(groups = {REGRESSION, UI, SMOKE,ISSUER},
            dataProvider = "credentialsP3",dataProviderClass = IssuerDataProviderClass.class,
            description = "Verify Summary Widget")
    public void ValidateSummaryWidget(String... dataProvider) {
        EntityIssuerPage entitypage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0];
            String password = dataProvider[1];
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
            LoginPageIssuer.loginWithParams(userId, password);

            entitypage.validateSummaryWidgetISVAvailable();
            //TODO : Need to update as per new header chnages
          //  assertTestCase.assertTrue(entitypage.verifyOverallEsgScoreWidget(), "Verify overall ESG Score widget");
            List<String> summarySections = Arrays.asList(new String[]{"ESG Score", "Environment", "Social", "Governance"});
            for (String ss : summarySections) entitypage.validateESGScoresAsNumericalValues(ss);

        /*Removing as the functionality has changed
        entitypage.validateESGScoreRatingList() ;*/
            entitypage.validateEsgScoredateFormat();
            assertTestCase.assertTrue(entitypage.NoSectorComparisionChart.isDisplayed(), "No Comparision Chart is available");
            entitypage.logout.click();
        }catch(Exception e){
            e.printStackTrace();
            entitypage.logout.click();
        }

    }
}
