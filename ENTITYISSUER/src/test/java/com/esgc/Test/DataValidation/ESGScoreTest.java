package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityPage.ESGScoreSummary;
import com.esgc.APIModels.EntityPage.ScoreCategory;
import com.esgc.DBModels.EntityPage.ESGScoreSummaryDBModel;
import com.esgc.Test.TestBases.EntityPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.Environment;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;

import static com.esgc.Utilities.DateTimeUtilities.getFormattedDate;


public class ESGScoreTest extends EntityPageDataValidationTestBase {

    @Test(groups = {"entity_page"})
    @Xray(test = {3778, 3779, 3780, 3781, 3782, 3783,8341})
    public void validateScoreRange() {
        String orbisID = Environment.OrbisId;
        SoftAssert softAssert = new SoftAssert();
        List<ESGScoreSummaryDBModel> ESGScoreSummaryDbModel = EntityPageQueries.getEsgScore(orbisID);
        System.out.println(ESGScoreSummaryDbModel.size());
        String lastTimeStamp =  getFormattedDate(ESGScoreSummaryDbModel.stream().max(Comparator.comparing(c -> c.getLasttimestamp())).get().getLasttimestamp(),"MMMM dd, yyyy");

        List<ESGScoreSummary> ESGScoreSummaryAPIResponse = Arrays.asList(
                controller.getIssuerSummary()
                        .as(ESGScoreSummary[].class));


        softAssert.assertEquals(ESGScoreSummaryAPIResponse.get(0).getLast_updated(),lastTimeStamp);
        for (ESGScoreSummaryDBModel dbresulRow : ESGScoreSummaryDbModel){

            String criteria = dbresulRow.getCriteria();
            ScoreCategory scoreCategory = ESGScoreSummaryAPIResponse.get(0).getScore_categories().stream()
                    .filter(p -> p.getCriteria().contains(criteria)).findFirst().get();

            softAssert.assertEquals(scoreCategory.getCriteria(), criteria);

            softAssert.assertEquals(String.valueOf(Math.round(scoreCategory.getScore())),dbresulRow.getScore());

            String decimalPattern = "([0-9]*)";
            boolean match = Pattern.matches(decimalPattern, dbresulRow.getScore());

        }





        softAssert.assertAll();

    }


    @DataProvider(name = "orbisID")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                     {"000002959"}
                };
    }

}
