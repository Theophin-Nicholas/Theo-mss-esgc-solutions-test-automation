package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryAPIWrapper;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryDetails;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverWeights;
import com.esgc.DBModels.EntityIssuerPageDBModels.ESGMaterlityDBModel;
import com.esgc.Test.TestBases.EntityIssuerPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityIssuerQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.esgc.Pages.LoginPageIssuer.OrbisID;
import static com.esgc.Utilities.Groups.ISSUER;
import static com.esgc.Utilities.Groups.REGRESSION;

public class P3ESGMateriality extends EntityIssuerPageDataValidationTestBase {

    @Test(groups = {REGRESSION, ISSUER})
    @Xray(test = {9953,12446})
    public void validateESGMaterialitySummaryData() {

        // DB Data
        List<ESGMaterlityDBModel> DBData = EntityIssuerQueries.getESGMaterlityData(OrbisID);

        // API Data
        ESGMaterlityDriverSummaryAPIWrapper APIData = controller.getDriverSummary().as(ESGMaterlityDriverSummaryAPIWrapper.class);


        // filter distinct Criterias
        List<ESGMaterlityDBModel> eachCriteria = DBData.stream().filter(distinctByKey(ESGMaterlityDBModel::getCriteria_code)).
                collect(Collectors.toList());

        // Assertion
        for(ESGMaterlityDBModel dbRow :eachCriteria){

            ESGMaterlityDriverSummaryDetails driver = APIData.getDrivers().stream().
                    filter(f-> f.getCriteria_id().equals(dbRow.getCriteria_code()) && f.getIndicator().equals(dbRow.getIndicator())).findFirst().get();

            assertTestCase.assertTrue(driver.getCriteria_name().equals(dbRow.getCriteria_name()), "Validating Criteria name");
            assertTestCase.assertTrue((driver.getCriteria_score()*100.00)/100.00== dbRow.getCriteria_score(), "Validating Criteria Score");
            assertTestCase.assertTrue(driver.getCritical_controversy_exists_flag().equals(dbRow.getCritical_controversy_exists_flag()), "Validating Critical controversy Flag");
            assertTestCase.assertTrue(driver.getDisclosure_ratio()==(dbRow.getDisclosure_ratio()), "Validating Disclosure Ratio");
            assertTestCase.assertTrue(driver.getDomain().equals(dbRow.getDomain()), "Validating Domain");
            assertTestCase.assertEquals(driver.getIndicator(),(dbRow.getIndicator()), "Validating Indicator");
            assertTestCase.assertTrue(driver.getScore_category().equals(dbRow.getScore_category()));
            assertTestCase.assertTrue(driver.getSub_category_detailed_description().equals(dbRow.getSub_catg_desc()));

            for(ESGMaterlityDriverWeights weights : driver.getDriver_weights()){
                ESGMaterlityDBModel dbWeight =  DBData.stream().filter(f-> f.getCriteria_code().equals(dbRow.getCriteria_code())&& f.getData_type().equals(weights.getMateriality_type())).findFirst().get();
                assertTestCase.assertTrue(dbWeight.getValue()==weights.getMateriality_value(),"Validate Materiallity value");
                assertTestCase.assertTrue(dbWeight.getWeight().equals(weights.getMateriality_weight()),"Validate Materiallity Weight");
            }
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }


    @DataProvider(name = "orbisID")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"000387077"}


                };
    }}