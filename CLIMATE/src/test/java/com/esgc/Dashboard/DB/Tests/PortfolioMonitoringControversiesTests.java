package com.esgc.Dashboard.DB.Tests;

import com.esgc.APIModels.EntityPage.EntityControversy;
import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Utilities.APIUtilities;
import com.esgc.Utilities.Xray;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.esgc.Utilities.Groups.*;

public class PortfolioMonitoringControversiesTests extends DataValidationTestBase {
//TODO check queries
    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = 4060)
    public void verifyControversiesOrder() throws ParseException {
        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        Response response = dashboardAPIController.getControversies(portfolioId, "all", "all", "latest", "latest");
        List<EntityControversy> apiResultsList = new JsonPath(response.asPrettyString().replace("\u0002", " ")).getList("", EntityControversy.class);

        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < (apiResultsList.size() - 2); i++) {
            Date eventDate1 = sdformat.parse(apiResultsList.get(i + 1).getControversyEvents());
            Date eventDate2 = sdformat.parse(apiResultsList.get(i + 2).getControversyEvents());
            test.info("Comparing " + eventDate1 + " " + eventDate2);
            assertTestCase.assertTrue(eventDate1.compareTo(eventDate2) >= 0, "Event dates are not in chronological sorting order");
        }
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = 4062)
    public void verifyControversiesOfSameCompany() {

        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        String companyAndCount = entitypagequeries.getCompanyWithMultipleControversies(portfolioId);
        String dbCompany = companyAndCount.split(";")[0];
        int dbControversiesCount = Integer.parseInt(companyAndCount.split(";")[1]);


        Response response = dashboardAPIController.getControversies(portfolioId, "all", "all", "latest", "latest");
        List<EntityControversy> apiResultsList = new JsonPath(response.asPrettyString().replace("\u0002", " ")).getList("", EntityControversy.class);

        int apiControversiesCount = 0;

        for (int i = 0; i < (apiResultsList.size() - 1); i++) {
            if (apiResultsList.get(i + 1).getTitle().equals(dbCompany)) {
                apiControversiesCount++;
            }
        }
        assertTestCase.assertTrue(apiControversiesCount == dbControversiesCount, "Verify multiple controversies of same company");

    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = {6816, 6955, 4058, 7823})
    public void verifyControversiesFromLast60Days() {

        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        List<EntityControversy> dbResults = entitypagequeries.getControversies(portfolioId, 60);

        Response response = dashboardAPIController.getControversies(portfolioId, "all", "all", "latest", "latest");
        List<EntityControversy> apiResultsList = new JsonPath(response.asPrettyString().replace("\u0002", " ")).getList("", EntityControversy.class);

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
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = {4058, 7823})
    public void verifyControversiesFromLastOneMonth() {

        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        LocalDate now = LocalDate.now();
        LocalDate earlier = now.minusMonths(1);

        int month = earlier.getMonth().getValue();
        int year = earlier.getYear();

        List<EntityControversy> dbResults = entitypagequeries.getControversies(portfolioId, year, month);

        Response response = dashboardAPIController.getControversies(portfolioId, "all", "all", String.valueOf(year), "0" + String.valueOf(month));
        List<EntityControversy> apiResultsList = new JsonPath(response.asPrettyString().replace("\u0002", " ")).getList("", EntityControversy.class);

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
    }

    @Test(groups = {REGRESSION, DATA_VALIDATION, DASHBOARD})
    @Xray(test = {11050})
    public void verifySubsidiaryControversies() {

        Response portfoliosResponse = APIUtilities.getAvailablePortfoliosForUser();
        JsonPath jsonPathEvaluator = portfoliosResponse.jsonPath();
        List<String> portfolioIds = jsonPathEvaluator.getList("portfolios.portfolio_id");
        String portfolioId = portfolioIds.get(portfolioIds.size() - 1).toString();

        LocalDate now = LocalDate.now();
        LocalDate earlier = now.minusMonths(1);

        int month = earlier.getMonth().getValue();
        int year = earlier.getYear();

        List<EntityControversy> dbResults = entitypagequeries.getControversies(portfolioId, year, month);

        Response response = dashboardAPIController.getControversies(portfolioId, "all", "all", String.valueOf(year), "0" + String.valueOf(month));
        List<EntityControversy> apiResultsList = new JsonPath(response.asPrettyString().replace("\u0002", " ")).getList("", EntityControversy.class);

        long countOfSubs = apiResultsList.stream().filter(x -> x.getManaged_type().equals("subsidiary")).count();

        if (countOfSubs == 0) {
            throw new SkipException("There is no subsidiary controversies to check");
        }

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
    }

}
