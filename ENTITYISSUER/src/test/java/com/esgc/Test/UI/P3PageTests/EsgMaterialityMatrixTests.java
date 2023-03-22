package com.esgc.Test.UI.P3PageTests;

import com.esgc.APIModels.EntityIssuerPage.ESGCategories;
import com.esgc.APIModels.EntityIssuerPage.SubCategories;
import com.esgc.Controllers.EntityIssuerPageController.EntityIssuerPageAPIController;
import com.esgc.Pages.EntityIssuerPage;
import com.esgc.Pages.LoginPageIssuer;
import com.esgc.Test.TestBases.EntityPageTestBase;
import com.esgc.Test.TestBases.IssuerDataProviderClass;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.esgc.Utilities.Groups.*;

public class EsgMaterialityMatrixTests extends EntityPageTestBase {


    @Test(groups = {"entity_Issuer_profile", SMOKE, REGRESSION, UI}, dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = {9884, 9921})
    public void validateESGMaterialityMatrixHeader(String... dataProvider) {
        EntityIssuerPage entityIssuerPage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0].toString();
            String password = dataProvider[1].toString();
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);

            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(userId, password);

            BrowserUtils.wait(5);
            assertTestCase.assertTrue(EntityIssuerPage.esgMaterialityTab.isDisplayed(), "Validate ESG Materiality Tab availability");
            entityIssuerPage.validateEsgMaterialityLegends();
        } catch (Exception e) {
            e.printStackTrace();
            entityIssuerPage.logout.click();
        }


    }

    @Test(groups = {ENTITY_PROFILE, SMOKE, REGRESSION, UI}, dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = {9924})
    public void validateEsgMaterialityMatrixColumns(String... dataProvider) {
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0].toString();
            String password = dataProvider[1].toString();
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(userId, password);


            entityIssuerpage.selectEsgMaterialityTab();
            List<String> expMaterialityMatrixColumns = Arrays.asList(
                    "Materiality: Very High",
                    "Materiality: High",
                    "Materiality: Moderate",
                    "Materiality: Low"
            );
            List<String> actualMaterialityMatrixColumns = entityIssuerpage.readEsgMaterialityColumns();
            System.out.println("Expected: " + expMaterialityMatrixColumns);
            System.out.println("Actual  : " + actualMaterialityMatrixColumns);
            assertTestCase.assertTrue(actualMaterialityMatrixColumns.equals(expMaterialityMatrixColumns), "Verification of Materiality Matrix Columns");

            assertTestCase.assertTrue(entityIssuerpage.verifyMaterialityMatrixYaxisLabels(), "Verification of Materiality Matrix Y-Axis Labels");
            entityIssuerpage.logout.click();
        } catch (Exception e) {
            e.printStackTrace();
            entityIssuerpage.logout.click();

        }

    }


    @Test(groups = {"esg_materiality", SMOKE, REGRESSION, UI},  dataProviderClass = IssuerDataProviderClass.class)
    @Xray(test = {9925})
    public void validateEsgMaterialityFooter(String... dataProvider) {
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0].toString();
            String password = dataProvider[1].toString();
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(1);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(userId, password);

            BrowserUtils.scrollTo(entityIssuerpage.esgMaterialityTab);
            entityIssuerpage.selectEsgMaterialityTab();

            List<String> methodologies = Arrays.asList(new String[]{"Double Materiality", "Business Materiality", "Stakeholder Materiality", "Environmental", "Social", "Governance"});
            for (String e : methodologies) {
                assertTestCase.assertTrue(entityIssuerpage.isProvidedFilterClickableInMaterialityMatrixFooter(e), "Verify " + e + "filter is clickable in Materiality Matrix Footer");
                if (e.equals("Double Materiality")) {
                    assertTestCase.assertTrue(entityIssuerpage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(e), "Verify" + e + "filter is selected as default in Materiality Matrix Footer");
                } else {
                    assertTestCase.assertTrue(!entityIssuerpage.isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(e), "Verify" + e + "filter is not selected in Materiality Matrix Footer");
                }

            }

            //validate Sub Categories based on methodology filters
            EntityIssuerPageAPIController controller = new EntityIssuerPageAPIController();
            List<ESGCategories> ExpectedCategories = new ArrayList<>();
            for (String e : methodologies) {
                if (e.equals(e.equals("Environmental") || e.equals("Social") || e.equals("Governance"))) {
                    entityIssuerpage.selectMaterialityMatrixFilter(e);
                    List<String> uiEnvironmentalCategories = entityIssuerpage.readEsgMaterialityCategories();
                    List<String> expectedValues = controller.getESGSubCategories(e).get(0).getSubcategories().stream().map(SubCategories::getCategory).collect(Collectors.toList());
                    assertTestCase.assertTrue(expectedValues.stream().filter(element -> uiEnvironmentalCategories.contains(element)).findFirst().isPresent(), "Validating Sub category filter for " + e + " Methodology");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            entityIssuerpage.logout.click();

        }


    }

    @Test(groups = {"esg_materiality", REGRESSION, UI,"Test"}, dataProviderClass = IssuerDataProviderClass.class)

    @Xray(test = {9926})
    public void validateEsgMaterialitySubCategoryModel(String... dataProvider) {
        EntityIssuerPage entityIssuerpage = new EntityIssuerPage();
        try {
            String userId = dataProvider[0].toString();
            String password = dataProvider[1].toString();
            LoginPageIssuer LoginPageIssuer = new LoginPageIssuer();
            BrowserUtils.wait(2);
            if (Driver.getDriver().getCurrentUrl().contains("login"))
                LoginPageIssuer.loginWithParams(userId, password);
            BrowserUtils.scrollTo(entityIssuerpage.esgMaterialityTab);
            entityIssuerpage.selectEsgMaterialityTab();
            entityIssuerpage.validateSubCategoryModal();
        } catch (Exception e) {
            e.printStackTrace();
            entityIssuerpage.logout.click();
        }

    }
}