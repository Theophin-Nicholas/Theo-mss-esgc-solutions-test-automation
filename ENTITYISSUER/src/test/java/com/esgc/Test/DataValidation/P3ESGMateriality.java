package com.esgc.Test.DataValidation;

import com.esgc.APIModels.EntityIssuerPage.DriverDetail;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryAPIWrapper;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverSummaryDetails;
import com.esgc.APIModels.EntityIssuerPage.ESGMaterlityDriverWeights;
import com.esgc.APIModels.EntityPage.SectorDrivers;
import com.esgc.APIModels.EntityPage.SectorDriversWrapper;
import com.esgc.APIModels.EntityPage.SectorIndicator;
import com.esgc.DBModels.EntityIssuerPageDBModels.ESGMaterlityDBModel;
import com.esgc.DBModels.EntityPage.SectorDriverDBModel;
import com.esgc.DBModels.ResearchLineIdentifier;
import com.esgc.Test.TestBases.EntityPageDataValidationTestBase;
import com.esgc.Utilities.Database.EntityPageQueries;
import com.esgc.Utilities.Xray;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.PortfolioUtilities.distinctByKey;


public class P3ESGMateriality extends EntityPageDataValidationTestBase {

    @Test(dataProvider = "orbisID", groups = {"regression", "entity_issuer"})
    @Xray(test = {9953})
    public void validateESGMaterialitySummaryData(@Optional String orbisID) {

        // DB Data
        List<ESGMaterlityDBModel> DBData = EntityPageQueries.getESGMaterlityData(orbisID);

        // API Data
        ESGMaterlityDriverSummaryAPIWrapper APIData = controller.getDriverSummary().as(ESGMaterlityDriverSummaryAPIWrapper.class);


        // filter distinct Criterias
        List<ESGMaterlityDBModel> eachCriteria = DBData.stream().filter(distinctByKey(ESGMaterlityDBModel::getCriteria_code)).
                collect(Collectors.toList());

        // Assertion
        for(ESGMaterlityDBModel dbRow :eachCriteria){

            ESGMaterlityDriverSummaryDetails driver = APIData.getDrivers().stream().
                    filter(f-> f.getCriteria_id().equals(dbRow.getCriteria_code())).findFirst().get();

            assertTestCase.assertTrue(driver.getCriteria_name().equals(dbRow.getCriteria_name()), "Validating Criteria name");
            assertTestCase.assertTrue((driver.getCriteria_score()*100.00)/100.00== dbRow.getCriteria_score(), "Validating Criteria Score");
            assertTestCase.assertTrue(driver.getCritical_controversy_exists_flag().equals(dbRow.getCritical_controversy_exists_flag()), "Validating Critical controversy Flag");
            assertTestCase.assertTrue(driver.getDisclosure_ratio()==(dbRow.getDisclosure_ratio()), "Validating Disclosure Ratio");
            assertTestCase.assertTrue(driver.getDomain().equals(dbRow.getDomain()), "Validating Domain");
            assertTestCase.assertTrue(driver.getIndicator().equals(dbRow.getIndicator()), "Validating Indicator");
            assertTestCase.assertTrue(driver.getScore_category().equals(dbRow.getScore_category()));
            assertTestCase.assertTrue(driver.getSub_category_detailed_description().equals(dbRow.getSub_catg_desc()));

            for(ESGMaterlityDriverWeights weights : driver.getDriver_weights()){
                ESGMaterlityDBModel dbWeight =  DBData.stream().filter(f-> f.getCriteria_code().equals(dbRow.getCriteria_code())&& f.getData_type().equals(weights.getMateriality_type())).findFirst().get();
                assertTestCase.assertTrue(dbWeight.getValue()==weights.getMateriality_value(),"Validate Materiallity value");
                assertTestCase.assertTrue(dbWeight.getWeight().equals(weights.getMateriality_weight()),"Validate Materiallity Weight");
            }
        }
    }


    @DataProvider(name = "orbisID")
    public Object[][] provideFilterParameters() {

        return new Object[][]
                {
                        {"000387077"}


                };
    }}