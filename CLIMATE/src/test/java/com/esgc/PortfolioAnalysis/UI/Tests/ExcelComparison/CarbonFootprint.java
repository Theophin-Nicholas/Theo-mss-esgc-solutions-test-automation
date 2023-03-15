package com.esgc.PortfolioAnalysis.UI.Tests.ExcelComparison;

import com.esgc.Base.API.APIModels.APIFilterPayload;
import com.esgc.Base.API.APIModels.APIFilterPayloadWithImpactFilter;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.PortfolioAnalysis.API.APIModels.*;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.ExcelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarbonFootprint extends UITestBase {
    public CustomAssertion assertTestCase = new CustomAssertion();

    /**
     * This Method verifies the Portfolio Score, Portfolio Distribution
     * Portfolio Coverage and Emissions part in UI vs Excel
     */

    public void verifyCarbonFootPrint(String researchLine, String portfolio_id, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        /**
         * Getting UI values
         */
        //Portfolio Score
        String carbonFootprintUI = researchLinePage.getUIValues(researchLinePage.portfolioScore).substring(17, 33);
        String carbonFootprintScoreUI = researchLinePage.getUIValues(researchLinePage.portfolioScoreValue);

        String carbonIntensityUI = researchLinePage.getUIValues(researchLinePage.portfolioCarbonIntensityText).substring(17, 33);
        String carbonIntensityScoreUI = researchLinePage.getUIValues(researchLinePage.portfolioCarbonIntensityValue);

        //Portfolio Coverage
        String portfolioCoverageCompaniesUI = researchLinePage.getUIValues(researchLinePage.portfolioCoverageCompaniesChartTitle);
        String portfolioCoverageCompaniesValueUI = researchLinePage.getUIValues(researchLinePage.portfolioCoverageCompanies);
        String portfolioCoverageInvestmentUI = researchLinePage.getUIValues(researchLinePage.portfolioCoverageInvestmentChartTitle);
        String portfolioCoverageInvestmentValueUI = researchLinePage.getUIValues(researchLinePage.portfolioCoverageInvestment);

        List<List<String>> portfolioDistributionList = new ArrayList<>();
        int sizeofPortfolioDistribution = researchLinePage.portfolioDistributionAllTable.size();
        for (int i = 0; i < sizeofPortfolioDistribution; i++) {
            //System.out.println(Arrays.toString(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
            portfolioDistributionList.add(Arrays.asList(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
        }
        //emmission UI
        String emissionUI = researchLinePage.getUIValues(researchLinePage.EmissionsSectionWidgetTitle);
        String emissionsTotalUI = "";
        String emissionsTotalAssetsUI = "";
        String emissionsMarketCapitalizationUI = "";

        String emissionFinancedPerMillionInvestedTotalUI = "";
        String emissionFinancedPerMillionInvestedTotalAssetsUI = "";
        String emissionFinancedPerMillionInvestedMarketCapitalizationUI = "";

        String emissionsCarbonIntensityTotalUI = "";
        String emissionsCarbonIntensityTotalAssetsUI = "";
        String emissionsCarbonIntensityMarketCapitalizationUI = "";

        String emissionTotalFinancedUI = researchLinePage.getUIValues(researchLinePage.EmissionsTotalFinanceSectionDetail);
        List<WebElement> emissionTable = researchLinePage.EmissionsTables.get(0).findElements(By.xpath(".//tbody/tr"));
        for (int x = 0; x < emissionTable.size(); x++) {
            emissionsTotalUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[1]")));
            emissionsTotalAssetsUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[2]")));
            emissionsMarketCapitalizationUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[3]")));
        }

        String emissionFinancedPerMillionInvestedUI = researchLinePage.getUIValues(researchLinePage.Financed_Emissions_per_million_Invested_Detail);
        emissionTable = researchLinePage.EmissionsTables.get(1).findElements(By.xpath(".//tbody/tr"));
        for (int x = 0; x < emissionTable.size(); x++) {
            emissionFinancedPerMillionInvestedTotalUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[1]")));
            emissionFinancedPerMillionInvestedTotalAssetsUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[2]")));
            emissionFinancedPerMillionInvestedMarketCapitalizationUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[3]")));
        }

        String emissionsCarbonIntensityUI = researchLinePage.getUIValues(researchLinePage.Carbon_Intensity_per_SalesDetail);
        emissionTable = researchLinePage.EmissionsTables.get(2).findElements(By.xpath(".//tbody/tr"));
        for (int x = 0; x < emissionTable.size(); x++) {
            emissionsCarbonIntensityTotalUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[1]")));
            emissionsCarbonIntensityTotalAssetsUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[2]")));
            emissionsCarbonIntensityMarketCapitalizationUI = researchLinePage.getUIValues(emissionTable.get(x).findElement(By.xpath(".//td[3]")));
        }

        //TODO This part is for next development
       /* String result=researchLinePage.getFirstCellValueFromExportedFile("Emissions",researchLine);
        System.out.println("=================");
        System.out.println(result);
        System.out.println("=================");*/
        //Portfolio Score Excel readings
        String portfolioScore = researchLinePage.getDataFromExportedFile(4, 0, researchLine);
        String carbonFootprint = researchLinePage.getDataFromExportedFile(6, 0, researchLine);
        String carbonFootprintScore = researchLinePage.getDataFromExportedFile(6, 2, researchLine);

        String carbonIntensity = researchLinePage.getDataFromExportedFile(7, 0, researchLine);
        String carbonIntensityScore = researchLinePage.getDataFromExportedFile(7, 2, researchLine).split("\\.")[0];

        //Portfolio Coverage
        String portfolioCoverage = researchLinePage.getDataFromExportedFile(16, 0, researchLine);
        String portfolioCoverageCompanies = researchLinePage.getDataFromExportedFile(17, 0, researchLine);
        String portfolioCoverageCompaniesValue = researchLinePage.getDataFromExportedFile(18, 0, researchLine);
        String portfolioCoverageInvestment = researchLinePage.getDataFromExportedFile(17, 1, researchLine);
        String portfolioCoverageInvestmentValue = researchLinePage.getDataFromExportedFile(18, 1, researchLine);

        //Portfolio Distribution
        String portfolioDistribution = researchLinePage.getDataFromExportedFile(9, 0, researchLine);

        String portfolioDistributionCategoryName = researchLinePage.getDataFromExportedFile(10, 0, researchLine);
        String portfolioDistributionCategoryInvestmentName = researchLinePage.getDataFromExportedFile(10, 1, researchLine);
        String portfolioDistributionCategoryCompanyName = researchLinePage.getDataFromExportedFile(10, 2, researchLine).split("\\.")[0];


        String portfolioDistributionCategory1 = researchLinePage.getDataFromExportedFile(11, 0, researchLine);
        String portfolioDistributionCategory1Investment = researchLinePage.getDataFromExportedFile(11, 1, researchLine);
        String portfolioDistributionCategory1Company = researchLinePage.getDataFromExportedFile(11, 2, researchLine).split("\\.")[0];

        String portfolioDistributionCategory2 = researchLinePage.getDataFromExportedFile(12, 0, researchLine);
        String portfolioDistributionCategory2Investment = researchLinePage.getDataFromExportedFile(12, 1, researchLine);
        String portfolioDistributionCategory2Company = researchLinePage.getDataFromExportedFile(12, 2, researchLine).split("\\.")[0];

        String portfolioDistributionCategory3 = researchLinePage.getDataFromExportedFile(13, 0, researchLine);
        String portfolioDistributionCategory3Investment = researchLinePage.getDataFromExportedFile(13, 1, researchLine);
        String portfolioDistributionCategory3Company = researchLinePage.getDataFromExportedFile(13, 2, researchLine).split("\\.")[0];

        String portfolioDistributionCategory4 = researchLinePage.getDataFromExportedFile(14, 0, researchLine);
        String portfolioDistributionCategory4Investment = researchLinePage.getDataFromExportedFile(14, 1, researchLine);
        String portfolioDistributionCategory4Company = researchLinePage.getDataFromExportedFile(14, 2, researchLine).split("\\.")[0];

        //Emission
        String emission = researchLinePage.getDataFromExportedFile(20, 0, researchLine);
        String emissionTotalFinanced = researchLinePage.getDataFromExportedFile(22, 0, researchLine);
        String emissionsTotal = researchLinePage.getDataFromExportedFile(22, 1, researchLine);
        String emissionsTotalAssets = researchLinePage.getDataFromExportedFile(22, 2, researchLine);
        String emissionsMarketCapitalization = researchLinePage.getDataFromExportedFile(22, 3, researchLine);

        String emissionFinancedPerMillionInvested = researchLinePage.getDataFromExportedFile(22, 5, researchLine);
        String emissionFinancedPerMillionInvestedTotal = researchLinePage.getDataFromExportedFile(22, 6, researchLine);
        String emissionFinancedPerMillionInvestedTotalAssets = researchLinePage.getDataFromExportedFile(22, 7, researchLine);
        String emissionFinancedPerMillionInvestedMarketCapitalization = researchLinePage.getDataFromExportedFile(22, 8, researchLine);

        String emissionsCarbonIntensity = researchLinePage.getDataFromExportedFile(22, 10, researchLine);
        String emissionsCarbonIntensityTotal = researchLinePage.getDataFromExportedFile(22, 11, researchLine);
        String emissionsCarbonIntensityTotalAssets = researchLinePage.getDataFromExportedFile(22, 12, researchLine);
        String emissionsCarbonIntensityMarketCapitalization = researchLinePage.getDataFromExportedFile(22, 13, researchLine);

        /**
         * Assertion starting for Excel data vs UI Data
         */
        //Portfolio Score
        try {
            assertTestCase.assertEquals(carbonFootprintUI, carbonFootprint,
                    "Carbon Foot print Portfolio Score and value verified as " + carbonFootprintUI + " : " + carbonFootprint);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Portfolio Score and value verified as " + carbonFootprintUI + " : " + carbonFootprint);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(carbonFootprintScoreUI, carbonFootprintScore,
                    "Carbon Foot print Portfolio Score and value verified as " + carbonFootprintScoreUI + " : " + carbonFootprintScore);
            System.out.println("Carbon Foot print Portfolio Score and value verified as " + carbonFootprint + " : " + carbonFootprintScore);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Portfolio Score and value verified as " + carbonFootprintScoreUI + " : " + carbonFootprintScore);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(carbonIntensityUI, carbonIntensity,
                    "Carbon Intensity in Portfolio Score and value verified as " + carbonIntensityUI + " : " + carbonIntensity);
        } catch (AssertionError e) {
            test.fail("Carbon Intensity in Portfolio Score and value verified as " + carbonIntensityUI + " : " + carbonIntensity);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(carbonIntensityScoreUI, carbonIntensityScore,
                    "Carbon Intensity in Portfolio Score and value verified as " + carbonIntensityScoreUI + " : " + carbonIntensityScore);
            System.out.println("Carbon Intensity in Portfolio Score and value verified as " + carbonIntensity + " : " + carbonIntensityScore);
        } catch (AssertionError e) {
            test.fail("Carbon Intensity in Portfolio Score and value verified as " + carbonIntensityScoreUI + " : " + carbonIntensityScore);
            e.printStackTrace();
        }
        try {
            //Portfolio Coverage returns 310/400
            assertTestCase.assertEquals(portfolioCoverageCompaniesUI, portfolioCoverageCompanies,
                    "Portfolio Coverage verified as " + portfolioCoverageCompaniesUI + " : " + portfolioCoverageCompanies);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + portfolioCoverageCompaniesUI + " : " + portfolioCoverageCompanies);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioCoverageCompaniesValueUI, portfolioCoverageCompaniesValue,
                    "Portfolio Coverage verified as " + portfolioCoverageCompaniesValueUI + " : " + portfolioCoverageCompaniesValue);
            System.out.println("Portfolio Coverage verified as " + portfolioCoverageCompanies + " : " + portfolioCoverageCompaniesValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + portfolioCoverageCompaniesValueUI + " : " + portfolioCoverageCompaniesValue);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals("% " + portfolioCoverageInvestmentUI, portfolioCoverageInvestment,
                    "Portfolio Coverage verified as " + portfolioCoverageInvestmentUI + " : " + portfolioCoverageInvestment);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + portfolioCoverageInvestmentUI + " : " + portfolioCoverageInvestment);
            e.printStackTrace();
        }
        try {// investment value like %78
            assertTestCase.assertEquals(portfolioCoverageInvestmentValueUI,
                    Math.round(Float.parseFloat(portfolioCoverageInvestmentValue.substring(0, portfolioCoverageInvestmentValue.length() - 1))) + "%",
                    "Portfolio Coverage verified as " + portfolioCoverageInvestmentValueUI + " : " + portfolioCoverageInvestmentValue);
            System.out.println("Portfolio Coverage verified as " + portfolioCoverageInvestment + " : " + portfolioCoverageInvestmentValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + portfolioCoverageInvestmentValueUI + " : " + portfolioCoverageInvestmentValue);
            e.printStackTrace();
        }
        //Portfolio Distribution
        List<String> portfolioDistributionCategoryColumnNames = Arrays.asList(researchLinePage.portfolioDistributionAllTableHeaders.get(0).getText().split(" "));
        String portfolioDistributionCategoryNameUI = portfolioDistributionCategoryColumnNames.get(0);
        String portfolioDistributionCategoryInvestmentNameUI = "% " + portfolioDistributionCategoryColumnNames.get(2);
        String portfolioDistributionCategoryCompanyNameUI = portfolioDistributionCategoryColumnNames.get(3);
        String portfolioDistributionCategoryUI1 = portfolioDistributionList.get(0).get(0);
        String portfolioDistributionCategory1InvestmentUI = portfolioDistributionList.get(0).get(1);
        String portfolioDistributionCategory1CompanyUI = portfolioDistributionList.get(0).get(2);
        String portfolioDistributionCategory2UI = portfolioDistributionList.get(1).get(0);
        String portfolioDistributionCategory2InvestmentUI = portfolioDistributionList.get(1).get(1);
        String portfolioDistributionCategory2CompanyUI = portfolioDistributionList.get(1).get(2);
        String portfolioDistributionCategory3UI = portfolioDistributionList.get(2).get(0);
        String portfolioDistributionCategory3InvestmentUI = portfolioDistributionList.get(2).get(1);
        String portfolioDistributionCategory3CompanyUI = portfolioDistributionList.get(2).get(2);
        String portfolioDistributionCategory4UI = portfolioDistributionList.get(3).get(0);
        String portfolioDistributionCategory4InvestmentUI = portfolioDistributionList.get(3).get(1);
        String portfolioDistributionCategory4CompanyUI = portfolioDistributionList.get(3).get(2);

        System.out.println("==> Portfolio Distribution <==");
        try {
            assertTestCase.assertEquals(portfolioDistributionCategoryNameUI, portfolioDistributionCategoryName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategoryInvestmentNameUI, portfolioDistributionCategoryInvestmentName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategoryCompanyNameUI, portfolioDistributionCategoryCompanyName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategoryUI1, portfolioDistributionCategory1,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory1InvestmentUI, portfolioDistributionCategory1Investment,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory1CompanyUI, portfolioDistributionCategory1Company,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory2UI, portfolioDistributionCategory2,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory2InvestmentUI, portfolioDistributionCategory2Investment,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory2CompanyUI, portfolioDistributionCategory2Company,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory3UI, portfolioDistributionCategory3,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory3InvestmentUI, portfolioDistributionCategory3Investment,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory3CompanyUI, portfolioDistributionCategory3Company,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory4UI, portfolioDistributionCategory4,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4UI + " : " + portfolioDistributionCategory4);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4UI + " : " + portfolioDistributionCategory4);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4UI + " : " + portfolioDistributionCategory4);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory4InvestmentUI, portfolioDistributionCategory4Investment,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4InvestmentUI + " : " + portfolioDistributionCategory4Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4InvestmentUI + " : " + portfolioDistributionCategory4Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4InvestmentUI + " : " + portfolioDistributionCategory4Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(portfolioDistributionCategory4CompanyUI, portfolioDistributionCategory4Company,
                    "Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4CompanyUI + " : " + portfolioDistributionCategory4Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4CompanyUI + " : " + portfolioDistributionCategory4Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + portfolioDistributionCategory4CompanyUI + " : " + portfolioDistributionCategory4Company);
            e.printStackTrace();
        }

        try {
            assertTestCase.assertEquals(emission, emissionUI,
                    "Carbon Foot print Emission title verified as " + emissionUI + " : " + emission);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission title verified as " + emissionUI + " : " + emission);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionTotalFinanced, emissionTotalFinancedUI,
                    "Carbon Foot print Emission emissionTotalFinanced value verified as " + emissionTotalFinancedUI + " : " + emissionTotalFinanced);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionTotalFinanced value verified as " + emissionTotalFinancedUI + " : " + emissionTotalFinanced);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsTotal, emissionsTotalUI,
                    "Carbon Foot print Emission emissionsTotal value verified as " + emissionsTotalUI + " : " + emissionsTotal);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsTotal value verified as " + emissionsTotalUI + " : " + emissionsTotal);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsTotalAssets + " (tCO2eq)", emissionsTotalAssetsUI,
                    "Carbon Foot print Emission emissionsTotalAssets value verified as " + emissionsTotalAssetsUI + " : " + emissionsTotalAssets + " (tCO2eq)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsTotalAssets value verified as " + emissionsTotalAssetsUI + " : " + emissionsTotalAssets + " (tCO2eq)");
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsMarketCapitalization + " (tCO2eq)", emissionsMarketCapitalizationUI,
                    "Carbon Foot print Emission emissionsMarketCapitalization value verified as " + emissionsMarketCapitalizationUI + " : " + emissionsMarketCapitalization + " (tCO2eq)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsMarketCapitalization value verified as " + emissionsMarketCapitalizationUI + " : " + emissionsMarketCapitalization + " (tCO2eq)");
            e.printStackTrace();
        }

        assertTestCase.assertEquals(emissionFinancedPerMillionInvested, emissionFinancedPerMillionInvestedUI,
                "Carbon Foot print Emission emissionFinancedPerMillionInvested value verified as " + emissionFinancedPerMillionInvestedUI + " : " + emissionFinancedPerMillionInvested + " (tCO2eq)");
        try {
            assertTestCase.assertEquals(emissionFinancedPerMillionInvestedTotal, emissionFinancedPerMillionInvestedTotalUI,
                    "Carbon Foot print Emission emissionFinancedPerMillionInvestedTotal value verified as " + emissionFinancedPerMillionInvestedTotalUI + " : " + emissionFinancedPerMillionInvestedTotal);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionFinancedPerMillionInvestedTotal value verified as " + emissionFinancedPerMillionInvestedTotalUI + " : " + emissionFinancedPerMillionInvestedTotal);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionFinancedPerMillionInvestedTotalAssets + " (tCO2eq/M)", emissionFinancedPerMillionInvestedTotalAssetsUI,
                    "Carbon Foot print Emission emissionFinancedPerMillionInvestedTotalAssets value verified as " + emissionFinancedPerMillionInvestedTotalAssetsUI + " : " + emissionFinancedPerMillionInvestedTotalAssets + " (tCO2eq/M)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionFinancedPerMillionInvestedTotalAssets value verified as " + emissionFinancedPerMillionInvestedTotalAssetsUI + " : " + emissionFinancedPerMillionInvestedTotalAssets + " (tCO2eq/M)");
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionFinancedPerMillionInvestedMarketCapitalization + " (tCO2eq/M)", emissionFinancedPerMillionInvestedMarketCapitalizationUI,
                    "Carbon Foot print Emission emissionFinancedPerMillionInvestedMarketCapitalization value verified as " + emissionFinancedPerMillionInvestedMarketCapitalizationUI + " : " + emissionFinancedPerMillionInvestedMarketCapitalization + " (tCO2eq/M)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionFinancedPerMillionInvestedMarketCapitalization value verified as " + emissionFinancedPerMillionInvestedMarketCapitalizationUI + " : " + emissionFinancedPerMillionInvestedMarketCapitalization + " (tCO2eq/M)");
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsCarbonIntensity, emissionsCarbonIntensityUI,
                    "Carbon Foot print Emission emissionsCarbonIntensity value verified as " + emissionsCarbonIntensityUI + " : " + emissionsCarbonIntensity);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsCarbonIntensity value verified as " + emissionsCarbonIntensityUI + " : " + emissionsCarbonIntensity);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsCarbonIntensityTotal, emissionsCarbonIntensityTotalUI,
                    "Carbon Foot print Emission emissionsCarbonIntensityTotal value verified as " + emissionsCarbonIntensityTotalUI + " : " + emissionsCarbonIntensityTotal);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsCarbonIntensityTotal value verified as " + emissionsCarbonIntensityTotalUI + " : " + emissionsCarbonIntensityTotal);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsCarbonIntensityTotalAssets + " (tCO2eq/M)", emissionsCarbonIntensityTotalAssetsUI,
                    "Carbon Foot print Emission emissionsCarbonIntensityTotalAssets value verified as " + emissionsCarbonIntensityTotalAssetsUI + " : " + emissionsCarbonIntensityTotalAssets + " (tCO2eq/M)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission emissionsCarbonIntensityTotalAssets value verified as " + emissionsCarbonIntensityTotalAssetsUI + " : " + emissionsCarbonIntensityTotalAssets + " (tCO2eq/M)");
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(emissionsCarbonIntensityMarketCapitalization + " (tCO2eq/M)", emissionsCarbonIntensityMarketCapitalizationUI,
                    "Carbon Foot print Emission CarbonIntensityMarketCapitalization value verified as " + emissionsCarbonIntensityMarketCapitalizationUI + " : " + emissionsCarbonIntensityMarketCapitalization + " (tCO2eq/M)");
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Emission CarbonIntensityMarketCapitalization value verified as " + emissionsCarbonIntensityMarketCapitalizationUI + " : " + emissionsCarbonIntensityMarketCapitalization + " (tCO2eq/M)");
            e.printStackTrace();
        }
        //Portfolio Updates
        //Verify the headers UI vs Excel

        //Verify the Data API vs Excel
        UpdatesModel[] updatesModel =
                controller.getPortfolioUpdatesResponse(portfolio_id, researchLine, payload).body().prettyPeek()
                        .as(UpdatesModel[].class);
        System.out.println("String.valueOf(updatesModel[0]) = " + updatesModel[0]);
        List<List<Object>> myList = new ArrayList<>();

        for (UpdatesModel each : updatesModel) {
            myList.add(Arrays.asList(
                    each.getCompanyName(),
                    each.getLastUpdateDate(),
                    each.getScoreCategory(),
                    each.getPreviousUpdateDate(),
                    each.getPreviousScoreCategory(),
                    String.valueOf(each.getInvestmentPct()),
                    each.getSectorName(),
                    each.getCountryName(),
                    each.getRegionName()
            ));
        }
        for (Object aaa : myList) {
            System.out.println(aaa.toString());
        }

        System.out.println("============Getting Excel Data & Comparing with API Response===========");
        Cell updatesCell = exportedDocument.searchCellData("Portfolio Updates");
        int updatesTitleRow = updatesCell.getRowIndex();
        int updatesHeadersRow = updatesTitleRow + 1;

        //Verify the headers UI vs Excel
        List<String> updatesUIHeaders = researchLinePage.carbonFootprintHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
        updatesUIHeaders.set(2, updatesUIHeaders.get(2) + " Category");
        updatesUIHeaders.set(4, updatesUIHeaders.get(4) + " Category");
        List<String> excelHeaders = new ArrayList<>();
        for (int i = updatesHeadersRow, j = 0; j < 9; j++) {
            excelHeaders.add(exportedDocument.getCellData(i, j));
        }
        //Asserting the headers
        System.out.println("Excel Headers " + excelHeaders);
        System.out.println("UI Headers " + updatesUIHeaders);
        assertTestCase.assertTrue(CollectionUtils.isEqualCollection(updatesUIHeaders, excelHeaders), "Headers are Matching");

        int updatesListStartsFrom = updatesHeadersRow + 1;
        List<List<Object>> excelUpdateList = new ArrayList<>();
        for (int z = updatesListStartsFrom, a = 0; a < myList.size(); z++, a++) {
            excelUpdateList.add(Arrays.asList(
                    exportedDocument.getCellData(z, 0), //Company
                    exportedDocument.getCellData(z, 1), //Updated
                    exportedDocument.getCellData(z, 2), //Score Category
                    exportedDocument.getCellData(z, 3), //Precious
                    exportedDocument.getCellData(z, 4), //Precious Score Category
                    exportedDocument.getCellData(z, 5).replace("%", ""), //% Investment
                    exportedDocument.getCellData(z, 6), //Sector
                    exportedDocument.getCellData(z, 7), //Country
                    exportedDocument.getCellData(z, 8)  //Region
            ));
        }
        for (int i = 0; i < myList.size(); i++) {
            System.out.println("####################################");
            System.out.println("excelUpdateList.get(i) = " + excelUpdateList.get(i));
            System.out.println("myList.get(i) = " + myList.get(i));
            System.out.println("####################################");

            //empty cells should be corrected since there are some format differences
            for (int j = 0; j < myList.size(); j++) {
                if (excelUpdateList.get(i).get(j).equals("-")) {
                    assertTestCase.assertTrue(myList.get(i).get(j).equals("") || myList.get(i).get(j) == null);
                    myList.get(i).set(j, "-");
                }
            }
            assertTestCase.assertEquals(excelUpdateList.get(i), myList.get(i));
        }
        assertTestCase.assertTrue(CollectionUtils.isEqualCollection(excelUpdateList, myList));
        System.out.println("Assertion Successful");


        //Leaders & Laggards
        List<String> leadersLaggardsTableHeaders = researchLinePage.LeadersAndLaggardsTableColumns.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> leadersTableHeaders = leadersLaggardsTableHeaders.subList(0, 4);

        List<String> laggardsTableHeaders = leadersLaggardsTableHeaders.subList(5, 9);


        LeadersAndLaggardsWrapper leadersAndLaggardsWrapper =
                controller.getPortfolioLeadersAndLaggardsResponse(portfolio_id, researchLine, payload).body().prettyPeek()
                        .as(LeadersAndLaggardsWrapper.class);

        Map<String, LeadersAndLaggards> apiLeaders = leadersAndLaggardsWrapper.getLeaders().stream()
                .collect(Collectors.toMap(LeadersAndLaggards::getCompanyName, x -> x));

        Map<String, LeadersAndLaggards> apiLaggards = leadersAndLaggardsWrapper.getLaggards().stream()
                .collect(Collectors.toMap(LeadersAndLaggards::getCompanyName, x -> x));

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);


        Cell leadersCell = exportedDocument.searchCellData("Leaders");

        int leadersTitleRow = leadersCell.getRowIndex();
        int leadersHeadersRow = leadersTitleRow + 1;
        int leadersListStartsFrom = leadersHeadersRow + 1;

        Cell laggardsCell = exportedDocument.searchCellData("Laggards");

        int laggardsTitleRow = laggardsCell.getRowIndex();
        int laggardsHeadersRow = laggardsTitleRow + 1;
        int laggardsListStartsFrom = laggardsHeadersRow + 1;

        int leadersListEndsAt = laggardsTitleRow - 1;
        int laggardsListEndsAt = exportedDocument.searchCellData("Regions").getRowIndex() - 1;


        String leadersTitleInExportedDocument = exportedDocument.getCellData(leadersTitleRow, 0);

        try {
            assertTestCase.assertEquals(leadersTitleInExportedDocument, "Leaders",
                    "Leaders Title in Excel verified");
            System.out.println("Leaders Title in Excel field verified");
        } catch (AssertionError e) {
            test.fail("Leaders Title in Excel field verified");
            e.printStackTrace();
        }


        //There are 5 Headers in the table
        for (int i = 0; i < 4; i++) {

            String headerInUI = leadersTableHeaders.get(i);
            String headerInExcel = exportedDocument.getCellData(leadersHeadersRow, i);

            try {
                assertTestCase.assertEquals(headerInExcel, headerInUI,
                        "Leaders Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
                System.out.println("Leaders Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
            } catch (AssertionError e) {
                test.fail("Leaders Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
                e.printStackTrace();
            }
        }

        //check companies in leaders in Excel document
        for (int i = leadersListStartsFrom, j = 0; i < leadersListEndsAt && j < apiLeaders.size(); i++, j++) {

            String companyName = exportedDocument.getCellData(i, 1);
            LeadersAndLaggards company = apiLeaders.get(companyName);


            int rankInUI = company.getRank();
            String companyInUI = company.getCompanyName();
            double investmentInUI = Double.parseDouble(df.format(company.getInvestmentPct()));
            int scoreInUI = Double.valueOf(company.getScore()).intValue();
            String scoreCategoryInUI = company.getScoreCategory();

            int rankInExcel = Double.valueOf(exportedDocument.getCellData(i, 0)).intValue();
            String companyInExcel = exportedDocument.getCellData(i, 1);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 2).replace("%", ""));
            int scoreInExcel = Double.valueOf(exportedDocument.getCellData(i, 3).replace(",", "")).intValue();
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 4);

            try {
                assertTestCase.assertEquals(rankInUI, rankInExcel,
                        "Leaders Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
                System.out.println("Leaders Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(companyInUI, companyInExcel,
                        "Leaders Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
                System.out.println("Leaders Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(investmentInUI, investmentInExcel,
                        "Leaders Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
                System.out.println("Leaders Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreInUI, scoreInExcel,
                        "Leaders Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
                System.out.println("Leaders Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Leaders Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                System.out.println("Leaders Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                e.printStackTrace();
            }

        }

        String laggardsTitleInExportedDocument = exportedDocument.getCellData(laggardsTitleRow, 0);

        try {
            assertTestCase.assertEquals(laggardsTitleInExportedDocument, "Laggards",
                    "Laggards Title in Excel verified");
            System.out.println("Laggards Title in Excel field verified");
        } catch (AssertionError e) {
            test.fail("Laggards Title in Excel field verified");
            e.printStackTrace();
        }

        //There are 5 Headers in the table
        for (int i = 0; i < 4; i++) {

            String headerInUI = laggardsTableHeaders.get(i);
            String headerInExcel = exportedDocument.getCellData(laggardsHeadersRow, i);

            try {
                assertTestCase.assertEquals(headerInExcel, headerInUI,
                        "Laggards Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
                System.out.println("Laggards Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
            } catch (AssertionError e) {
                test.fail("Laggards Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
                e.printStackTrace();
            }
        }

        //check companies in laggards in Excel document
        for (int i = laggardsListStartsFrom, j = 0; i < laggardsListEndsAt && j < apiLaggards.size(); i++, j++) {


            String companyName = exportedDocument.getCellData(i, 1);
            LeadersAndLaggards company = apiLaggards.get(companyName);


            int rankInUI = company.getRank();
            String companyInUI = company.getCompanyName();
            double investmentInUI = Double.parseDouble(df.format(company.getInvestmentPct()));
            int scoreInUI = Double.valueOf(company.getScore()).intValue();
            String scoreCategoryInUI = company.getScoreCategory();

            int rankInExcel = Double.valueOf(exportedDocument.getCellData(i, 0)).intValue();
            String companyInExcel = exportedDocument.getCellData(i, 1);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 2).replace("%", ""));
            int scoreInExcel = Double.valueOf(exportedDocument.getCellData(i, 3).replace(",", "")).intValue();
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 4);

            try {
                assertTestCase.assertEquals(rankInUI, rankInExcel,
                        "Laggards Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
                System.out.println("Laggards Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Rank vs Excel field verified " + rankInUI + " : " + rankInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(companyInUI, companyInExcel,
                        "Laggards Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
                System.out.println("Laggards Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(investmentInUI, investmentInExcel,
                        "Laggards Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
                System.out.println("Laggards Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreInUI, scoreInExcel,
                        "Laggards Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
                System.out.println("Laggards Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Laggards Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                System.out.println("Laggards Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                e.printStackTrace();
            }
            //verify the company summary info in the Excel document for region
            verifyCarbonFootPrintRegions(researchLine, portfolio_id, controller, payload, exportedDocument);
            //verify the company summary info in the Excel document for region
            verifyCarbonFootPrintRegionDetails(researchLine, portfolio_id, controller, payload, exportedDocument);

            //verify the company summary info in the Excel document for sectors
            verifyCarbonFootPrintSectors(researchLine, portfolio_id, controller, payload, exportedDocument);
            //verify the company summary info in the Excel document for Sector
            verifyCarbonFootPrintSectorDetails(researchLine, portfolio_id, controller, payload, exportedDocument);
        }

        //Impact Tables
        List<String> impactTableHeaders = researchLinePage.verifyImpactTableColumns();
        System.out.println("impactTableHeaders = " + impactTableHeaders);


        APIFilterPayloadWithImpactFilter payloadWithImpactFilter =
                new APIFilterPayloadWithImpactFilter(payload.getRegion(),
                        payload.getSector(),
                        payload.getMonth(),
                        payload.getYear(),
                        "top5");

        List<ImpactDistributionWrappers> impactDistributionWrapper = Arrays.asList(
                controller.getImpactDistributionResponse(portfolio_id, researchLine, payloadWithImpactFilter)
                        .as(ImpactDistributionWrappers[].class));

        Map<String, InvestmentAndScore> apiPositiveImpact = impactDistributionWrapper.get(0).getPositive_impact().getInvestment_and_score().stream()
                .collect(Collectors.toMap(InvestmentAndScore::getCompany_name, x -> x));

        Map<String, InvestmentAndScore> apiNegativeImpact = impactDistributionWrapper.get(0).getNegative_impact().getInvestment_and_score().stream()
                .collect(Collectors.toMap(InvestmentAndScore::getCompany_name, x -> x));

        Cell positiveImpactCell = exportedDocument.searchCellData("Positive impact based on investment and score");

        int positiveImpactTitleRow = positiveImpactCell.getRowIndex();
        int positiveImpactHeadersRow = positiveImpactTitleRow + 1;
        int positiveImpactListStartsFrom = positiveImpactHeadersRow + 1;

        Cell negativeImpactCell = exportedDocument.searchCellData("Negative impact based on investment and score");

        int negativeImpactTitleRow = negativeImpactCell.getRowIndex();
        int negativeImpactHeadersRow = negativeImpactTitleRow + 1;
        int negativeImpactListStartsFrom = negativeImpactHeadersRow + 1;


        int positiveImpactListEndsAt = negativeImpactTitleRow - 1;
        int negativeImpactListEndsAt = leadersTitleRow - 1;

        String positiveImpactTitleInExportedDocument = exportedDocument.getCellData(positiveImpactTitleRow, 0);

        try {
            assertTestCase.assertEquals(positiveImpactTitleInExportedDocument, "Positive impact based on investment and score",
                    "Positive Impact Title in Excel verified");
        } catch (AssertionError e) {
            e.printStackTrace();
        }


        //There are 4 Headers in the table
        for (int i = 0; i < 3; i++) {

            String headerInUI = impactTableHeaders.get(i);
            String headerInExcel = exportedDocument.getCellData(positiveImpactHeadersRow, i);

            try {
                assertTestCase.assertEquals(headerInExcel, headerInUI,
                        "Positive Impact Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }

        //check companies in positive impact in Excel document
        for (int i = positiveImpactListStartsFrom, j = 0; i < positiveImpactListEndsAt && j < apiPositiveImpact.size(); i++, j++) {

            String companyName = exportedDocument.getCellData(i, 0);
            InvestmentAndScore company = apiPositiveImpact.get(companyName);
            System.out.println(company);

            String companyInUI = company.getCompany_name();
            double investmentInUI = Double.parseDouble(df.format(company.getInvestment_pct()));
            int scoreInUI = Double.valueOf(company.getScore()).intValue();
            String scoreCategoryInUI = company.getScore_category();

            String companyInExcel = exportedDocument.getCellData(i, 0);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 1).replace("%", ""));
            int scoreInExcel = Double.valueOf(exportedDocument.getCellData(i, 2).replace(",", "")).intValue();
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 3);

            try {
                assertTestCase.assertEquals(companyInUI, companyInExcel,
                        "Positive Impact Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(investmentInUI, investmentInExcel,
                        "Positive Impact Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreInUI, scoreInExcel,
                        "Positive Impact Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Positive Impact Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

        }

        String negativeImpactTitleInExportedDocument = exportedDocument.getCellData(negativeImpactTitleRow, 0);

        try {
            assertTestCase.assertEquals(negativeImpactTitleInExportedDocument, "Negative impact based on investment and score",
                    "Negative Impact Title in Excel verified");
        } catch (AssertionError e) {
            e.printStackTrace();
        }


        //There are 4 Headers in the table
        for (int i = 0; i < 3; i++) {

            String headerInUI = impactTableHeaders.get(i);
            String headerInExcel = exportedDocument.getCellData(negativeImpactHeadersRow, i);

            try {
                assertTestCase.assertEquals(headerInExcel, headerInUI,
                        "Negative Impact Headers vs Excel field verified " + headerInExcel + " : " + headerInUI);
            } catch (AssertionError e) {
                e.printStackTrace();
            }
        }

        //check companies in negative impact in Excel document
        for (int i = negativeImpactListStartsFrom, j = 0; i < negativeImpactListEndsAt && j < apiNegativeImpact.size(); i++, j++) {

            String companyName = exportedDocument.getCellData(i, 0);
            InvestmentAndScore company = apiNegativeImpact.get(companyName);

            String companyInUI = company.getCompany_name();
            double investmentInUI = Double.parseDouble(df.format(company.getInvestment_pct()));
            int scoreInUI = Double.valueOf(company.getScore()).intValue();
            String scoreCategoryInUI = company.getScore_category();

            String companyInExcel = exportedDocument.getCellData(i, 0);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 1).replace("%", ""));
            int scoreInExcel = Double.valueOf(exportedDocument.getCellData(i, 2).replace(",", "")).intValue();
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 3);

            try {
                assertTestCase.assertEquals(companyInUI, companyInExcel,
                        "Negative Impact Company Name vs Excel field verified " + companyInUI + " : " + companyInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(investmentInUI, investmentInExcel,
                        "Negative Impact Investment vs Excel field verified " + investmentInUI + " : " + investmentInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreInUI, scoreInExcel,
                        "Negative Impact Score vs Excel field verified " + scoreInUI + " : " + scoreInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

            try {
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Negative Impact Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

        }

        Cell carbonFootPrintScopeCell = exportedDocument.searchCellData("Carbon Footprint Scope");
        int i = carbonFootPrintScopeCell.getRowIndex();
        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeader.getText(), exportedDocument.getCellData(i, 0),
                "Verify Carbon Footprint Scope Header", 532);

        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(0).getText(), exportedDocument.getCellData(i + 1, 0),
                "Verify Scope1 title in UI vs Excel");
        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(1).getText(), exportedDocument.getCellData(i + 1, 1),
                "Verify Total Scope 1 Emissions in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(0).getText().contains(exportedDocument.getCellData(i + 2, 0)),
                "Verify Scope 1 Description in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(1).getText().contains(exportedDocument.getCellData(i + 2, 1)),
                "Verify Total Scope 1 Value in UI vs Excel");

        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(2).getText(), exportedDocument.getCellData(i + 3, 0),
                "Verify Scope2 title in UI vs Excel");
        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(3).getText(), exportedDocument.getCellData(i + 3, 1),
                "Verify Total Scope 2 Emissions in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(2).getText().contains(exportedDocument.getCellData(i + 4, 0)),
                "Verify Scope 2 Description in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(3).getText().contains(exportedDocument.getCellData(i + 4, 1)),
                "Verify Total Scope 2 Value in UI vs Excel");

        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(4).getText(), exportedDocument.getCellData(i + 5, 0),
                "Verify Scope3 title in UI vs Excel");
        assertTestCase.assertEquals(researchLinePage.carbonFootprintScopeHeaders.get(5).getText(), exportedDocument.getCellData(i + 5, 1),
                "Verify Total Scope 3 Emissions in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(4).getText().contains(exportedDocument.getCellData(i + 6, 0)),
                "Verify Scope 3 Description in UI vs Excel");
        assertTestCase.assertTrue(researchLinePage.carbonFootprintScopeDetails.get(5).getText().contains(exportedDocument.getCellData(i + 6, 1)),
                "Verify Total Scope 3 Value in UI vs Excel");


    }

    /**
     * Verify the Carbon Foot Print for the region in the Excel document
     *
     * @param researchLine
     * @param portfolioId
     * @param controller
     * @param payload
     * @param exportedDocument
     */
    private void verifyCarbonFootPrintRegionDetails(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Carbon Foot Print Region Details from API");

        //Get all Region Details
        List<RegionSectorDetail> rsAPI = Arrays.asList(
                controller.getPortfolioRegionDetailsResponse(portfolioId, researchLine, payload)
                        .as(RegionSectorDetail[].class));

        List<Company> companiesAPI = new ArrayList<>();
        for (RegionSectorDetail rs : rsAPI) {
            System.out.println("Region: " + rs.getName());
            for (Company o : rs.getCategory1()) {
                Company company = new Company();
                company.setCompany_name(o.getCompany_name());
                company.setInvestment_pct(o.getInvestment_pct());
                company.setScore(o.getScore());
                company.setScore_category("Moderate");
                companiesAPI.add(company);
            }
            for (Company o : rs.getCategory2()) {
                Company company = new Company();
                company.setCompany_name(o.getCompany_name());
                company.setInvestment_pct(o.getInvestment_pct());
                company.setScore(o.getScore());
                company.setScore_category("Significant");
                companiesAPI.add(company);
            }
            for (Company o : rs.getCategory3()) {
                Company company = new Company();
                company.setCompany_name(o.getCompany_name());
                company.setInvestment_pct(o.getInvestment_pct());
                company.setScore(o.getScore());
                company.setScore_category("High");
                companiesAPI.add(company);
            }
            for (Company o : rs.getCategory4()) {
                Company company = new Company();
                company.setCompany_name(o.getCompany_name());
                company.setInvestment_pct(o.getInvestment_pct());
                company.setScore(o.getScore());
                company.setScore_category("Intense");
                companiesAPI.add(company);
            }
        }
        //get all company details in a list


        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Carbon Foot Print Region Details from Excel");
        Cell regionsCell = exportedDocument.searchCellData("Regions");
        int regionsTitleRow = regionsCell.getRowIndex();
//        System.out.println("regionsTitleRow = " + regionsTitleRow);
        int regionsHeadersRow = regionsTitleRow + 1;
//        System.out.println("regionsHeadersRow = " + regionsHeadersRow);
        int regionsListStartsFrom = regionsHeadersRow + 12;
//        System.out.println("region details List Starts From = " + regionsListStartsFrom);
//        System.out.println(exportedDocument.getCellData(regionsListStartsFrom, 1));

        //Get all Region Details from Excel
        for (int i = 0; i < 3; i++) {
            System.out.println("\n==========================");
            String headerInExcel = exportedDocument.getCellData(regionsHeadersRow, i * 5);
            System.out.println("Getting Summary Details Info for = " + headerInExcel);
            List<Company> rsExcel = getRegionDetails(regionsListStartsFrom, i * 5 + 1, exportedDocument);
            System.out.println("Summary data loaded from Excel for = " + headerInExcel);
            System.out.println("Time to check those details in API");

            for (Company actCompany : rsExcel) {
                Company expCompany = companiesAPI.stream().filter(s -> s.getCompany_name().equals(actCompany.getCompany_name())).findFirst().get();
                softAssert(actCompany.getCompany_name(), expCompany.getCompany_name(), "Company Name");
                softAssert(actCompany.getInvestment_pct(), Math.round(expCompany.getInvestment_pct() * 100.0) / 100.0, "Investment Percentage");
                softAssert(actCompany.getScore(), expCompany.getScore(), "Score");
                softAssert(actCompany.getScore_category(), expCompany.getScore_category(), "Score Category");
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }

    public void verifyCarbonFootPrintRegions(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Carbon Foot Print Regions from API");
        List<RegionSummary> rsAPI = Arrays.asList(
                controller.getPortfolioRegionSummaryResponse(portfolioId, researchLine, payload)
                        .as(RegionSummary[].class));

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Carbon Foot Print Region from Excel");
        Cell regionsCell = exportedDocument.searchCellData("Regions");
        int regionsTitleRow = regionsCell.getRowIndex();
        System.out.println("regionsTitleRow = " + regionsTitleRow);
        int regionsHeadersRow = regionsTitleRow + 1;
        int regionsListStartsFrom = regionsHeadersRow + 1;
        String regionsTitleInExportedDocument = exportedDocument.getCellData(regionsTitleRow, 0);
        for (int i = 0; i < 3; i++) {
            String headerInExcel = exportedDocument.getCellData(regionsHeadersRow, i * 5);
            System.out.println("\n==========================");
            System.out.println("Getting Summary Info for = " + headerInExcel);
            RegionSummary rsExcel = getRegionSummary(regionsListStartsFrom, i * 5 + 1, exportedDocument);
            System.out.println("Summary data loaded from Excel to regionSummary object. Time to compare with API");
            for (int j = 0; j < rsAPI.size(); j++) {
                if (rsAPI.get(j).getRegionCode().equals(headerInExcel)) {
                    softAssert(rsAPI.get(j).getCategory(), rsExcel.getCategory(), "Region Summary - Category Type");
                    softAssert(rsAPI.get(j).getWeighted_average_score(), rsExcel.getWeighted_average_score(), "Region Summary - Weighted Average Score");
                    softAssert(rsAPI.get(j).getCountOfCompanies(), rsExcel.getCountOfCompanies(), "Region Summary - Count of Companies");
                    softAssert(Math.round(rsAPI.get(j).getInvestment_pct()) + ".0", rsExcel.getInvestment_pct() + "", "Region Summary - Investment %");
                    for (int k = 0; k < rsExcel.getPortfolioDistributionList().size(); k++) {
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getCategory(), rsExcel.getPortfolioDistributionList().get(k).getCategory(), "Region Summary - Portfolio Distribution table - Category Type");
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getCompanies(), rsExcel.getPortfolioDistributionList().get(k).getCompanies(), "Region Summary - Portfolio Distribution table - Number of Companies");
                        softAssert(Math.round(rsAPI.get(j).getPortfolioDistributionList().get(k).getInvestment_pct() * 100) / 100D, rsExcel.getPortfolioDistributionList().get(k).getInvestment_pct(), "Region Summary - Portfolio Distribution table - Investment %");

                    }
                }
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }

    public RegionSummary getRegionSummary(int row, int column, ExcelUtil exportedDocument) {
        RegionSummary regionSummary = new RegionSummary();
        regionSummary.setCategory(exportedDocument.getCellData(row++, ++column));
        regionSummary.setWeighted_average_score(Integer.parseInt(exportedDocument.getCellData(row++, column).replaceAll("\\D", "")));
        regionSummary.setCountOfCompanies(Integer.parseInt(exportedDocument.getCellData(row++, column).replaceAll("\\D", "")) / 10);
        regionSummary.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row++, column).replaceAll("%", "")));
        regionSummary.setPortfolioDistributionList(getPortfolioDistributionList(row + 1, column, exportedDocument));
        return regionSummary;
    }

    public List<Company> getRegionDetails(int row, int column, ExcelUtil exportedDocument) {
        System.out.println("Getting Region Details from Excel");
        List<Company> companyList = new ArrayList<>();
        try {
            while (exportedDocument.getCellData(row, column) != null) {
                Company company = new Company();

                company.setCompany_name(exportedDocument.getCellData(row, column));
                company.setScore(Double.parseDouble(exportedDocument.getCellData(row, column + 1).replaceAll(",", "")));
                company.setScore_category(exportedDocument.getCellData(row, column + 2));
                company.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row, column + 3).replaceAll("%", "")));
                row++;
                companyList.add(company);
            }
        } catch (NullPointerException e) {
            System.out.println("End of Region Details");
            System.out.println("companyList.size() = " + companyList.size());
        }

        return companyList;
    }

    /**
     * getPortfolioDistributionList can be used for both Regions and Sectors
     *
     * @param row
     * @param column
     * @param exportedDocument
     * @return
     */
    private List<PortfolioDistribution> getPortfolioDistributionList(int row, int column, ExcelUtil exportedDocument) {
        List<PortfolioDistribution> portfolioDistributionList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            PortfolioDistribution portfolioDistribution = new PortfolioDistribution();
            portfolioDistribution.setCategory(exportedDocument.getCellData(row, column - 1));
            portfolioDistribution.setCompanies(Integer.parseInt(exportedDocument.getCellData(row, column).replaceAll("\\D", "")) / 10);
            portfolioDistribution.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row, column + 1).replaceAll("%", "")));
            portfolioDistributionList.add(portfolioDistribution);
            row++;
        }
        return portfolioDistributionList;
    }

    /**
     * Custom soft assert to compare two values
     *
     * @param exp
     * @param act
     * @param fieldName
     * @return
     */

    public boolean softAssert(Object exp, Object act, String fieldName) {
        try {
            assertTestCase.assertEquals(exp, act, fieldName + " vs Excel field verified " + exp + " : " + act);
            //System.out.println(fieldName + " vs Excel field verified " + exp+ " : " + act);
            return true;
        } catch (AssertionError e) {
            test.fail(fieldName + " vs Excel field verified " + exp + " : " + act);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verify the Carbon Footprint for the sectors in the Excel document
     *
     * @param researchLine
     * @param portfolioId
     * @param controller
     * @param payload
     * @param exportedDocument
     */
    private void verifyCarbonFootPrintSectorDetails(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Carbon Foot Print Sector Details from API");

        //Get all Sector Details
        List<RegionSectorDetail> rsAPI = Arrays.asList(
                controller.getPortfolioSectorDetailsResponse(portfolioId, researchLine, payload)
                        .as(RegionSectorDetail[].class));
        System.out.println("rsAPI.size() = " + rsAPI.size());
        //get all Sector Details in a standard list
        List<Company> companiesAPI = new ArrayList<>();
        for (RegionSectorDetail rs : rsAPI) {
            System.out.println("Sector: " + rs.getName());
            if (rs.getCategory1() != null) {
                for (Company o : rs.getCategory1()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("Moderate");
                    companiesAPI.add(company);
                }
                System.out.println("Category 1 completed");
            }

            if (rs.getCategory2() != null) {
                for (Company o : rs.getCategory2()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("Significant");
                    companiesAPI.add(company);
                }
                System.out.println("Category 2 completed");
            }

            if (rs.getCategory3() != null) {
                for (Company o : rs.getCategory3()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("High");
                    companiesAPI.add(company);
                }
                System.out.println("Category 3 completed");
            }

            if (rs.getCategory4() != null) {
                for (Company o : rs.getCategory4()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("Intense");
                    companiesAPI.add(company);
                }
                System.out.println("Category 4 completed");
            }

            if (rs.getCategory5() != null) {
                for (Company o : rs.getCategory5()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("No Info");
                    companiesAPI.add(company);
                }
                System.out.println("Category 5 completed");
            }
        }

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Carbon Foot Print Sector Details from Excel");
        Cell sectorsCell = exportedDocument.searchCellData("Sectors");
        int sectorsTitleRow = sectorsCell.getRowIndex();
        int sectorsHeadersRow = sectorsTitleRow + 1;
        int sectorsListStartsFrom = sectorsHeadersRow + 12;

        //Get all Sector Details from Excel
        for (int i = 0; i < 12; i++) {
            System.out.println("\n==========================");
            String headerInExcel = exportedDocument.getCellData(sectorsHeadersRow, i * 5);
            System.out.println("Getting Summary Details Info for = " + headerInExcel);
            List<Company> rsExcel = getSectorDetails(sectorsListStartsFrom, i * 5 + 1, exportedDocument);
            System.out.println("Summary data loaded from Excel for = " + headerInExcel);
            System.out.println("Time to check those details in API");

            for (Company actCompany : rsExcel) {
                Company expCompany = companiesAPI.stream().filter(s -> s.getCompany_name().equals(actCompany.getCompany_name())).findFirst().get();
                softAssert(actCompany.getCompany_name(), expCompany.getCompany_name(), "Company Name");
                softAssert(actCompany.getInvestment_pct(), Math.round(expCompany.getInvestment_pct() * 100.0) / 100.0, "Investment Percentage");
                softAssert(actCompany.getScore(), expCompany.getScore(), "Score");
                softAssert(actCompany.getScore_category(), expCompany.getScore_category(), "Score Category");
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }


    public void verifyCarbonFootPrintSectors(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Carbon Foot Print Sectors from API");
        List<SectorSummary> rsAPI = Arrays.asList(
                controller.getPortfolioSectorSummaryResponse(portfolioId, researchLine, payload)
                        .as(SectorSummary[].class));

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Carbon Foot Print Sector from Excel");
        Cell sectorsCell = exportedDocument.searchCellData("Sectors");
        int sectorsTitleRow = sectorsCell.getRowIndex();
        System.out.println("sectorsTitleRow = " + sectorsTitleRow);
        int sectorsHeadersRow = sectorsTitleRow + 1;
        int sectorsListStartsFrom = sectorsHeadersRow + 1;
        for (int i = 0; i < 12; i++) {
            String headerInExcel = exportedDocument.getCellData(sectorsHeadersRow, i * 5);
            System.out.println("\n==========================");
            System.out.println("Getting Summary Info for = " + headerInExcel);
            SectorSummary rsExcel = getSectorSummary(sectorsListStartsFrom, i * 5 + 1, exportedDocument);
            System.out.println("Summary data loaded from Excel to sectorSummary object. Time to compare with API");
            for (int j = 0; j < rsAPI.size(); j++) {
                if (rsAPI.get(j).getSectorName().equals(headerInExcel)) {
                    softAssert(rsAPI.get(j).getCategory(), rsExcel.getCategory(), "Sector Summary - Category Type");
                    softAssert(Math.round(rsAPI.get(j).getWeighted_average_score()), Math.round(rsExcel.getWeighted_average_score()), "Sector Summary - Weighted Average Score");
                    softAssert(rsAPI.get(j).getCountOfCompanies(), rsExcel.getCountOfCompanies(), "Sector Summary - Count of Companies");
                    softAssert(Math.round(rsAPI.get(j).getInvestment_pct()) + ".0", rsExcel.getInvestment_pct() + "", "Sector Summary - Investment %");
                    for (int k = 0; k < rsExcel.getPortfolioDistributionList().size(); k++) {
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getCategory(), rsExcel.getPortfolioDistributionList().get(k).getCategory(), "Sector Summary - Portfolio Distribution table - Category Type");
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getCompanies(), rsExcel.getPortfolioDistributionList().get(k).getCompanies(), "Sector Summary - Portfolio Distribution table - Number of Companies");
                        softAssert(Math.round(rsAPI.get(j).getPortfolioDistributionList().get(k).getInvestment_pct() * 100) / 100D, rsExcel.getPortfolioDistributionList().get(k).getInvestment_pct(), "Sector Summary - Portfolio Distribution table - Investment %");
                    }
                }
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }

    public SectorSummary getSectorSummary(int row, int column, ExcelUtil exportedDocument) {
        SectorSummary sectorSummary = new SectorSummary();
        sectorSummary.setCategory(exportedDocument.getCellData(row++, ++column));
        sectorSummary.setWeighted_average_score(Double.parseDouble(exportedDocument.getCellData(row++, column).replaceAll(",", "")));
        sectorSummary.setCountOfCompanies(Integer.parseInt(exportedDocument.getCellData(row++, column).replaceAll("\\D", "")) / 10);
        sectorSummary.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row++, column).replaceAll("%", "")));
        sectorSummary.setPortfolioDistributionList(getPortfolioDistributionList(row + 1, column, exportedDocument));
        return sectorSummary;
    }

    public List<Company> getSectorDetails(int row, int column, ExcelUtil exportedDocument) {
        System.out.println("Getting Sector Details from Excel");
        List<Company> companyList = new ArrayList<>();
        try {
            while (exportedDocument.getCellData(row, column) != null) {
                Company company = new Company();

                company.setCompany_name(exportedDocument.getCellData(row, column));
                company.setScore(Double.parseDouble(exportedDocument.getCellData(row, column + 1).replaceAll(",", "")));
                company.setScore_category(exportedDocument.getCellData(row, column + 2));
                company.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row, column + 3).replaceAll("%", "")));
                row++;
                companyList.add(company);
            }
        } catch (NullPointerException e) {
            System.out.println("End of Sector Details");
            System.out.println("companyList.size() = " + companyList.size());
        }
        return companyList;
    }

    public void verifyCarbonFootPrintBenchMarkSectoins(String researchLine, String portfolio_id, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        ResearchLinePage researchLinePage = new ResearchLinePage();

        /**
         * Getting UI values
         */
        //BenchMark Portfolio Score
        String benchMarkCarbonFootprintUI = researchLinePage.getUIValues(researchLinePage.CarbonFootPrintBenchmarkScoreFootPrintText).substring(17, 33);
        String benchMarkCarbonFootprintScoreUI = researchLinePage.getUIValues(researchLinePage.CarbonFootPrintBenchmarkScoreFootPrintvalue);

        String benchMarkCarbonIntensityUI = researchLinePage.getUIValues(researchLinePage.CarbonFootPrintBenchmarkScoreFootIntensityText).substring(17, 33);
        String benchMarkCarbonIntensityScoreUI = researchLinePage.getUIValues(researchLinePage.CarbonFootPrintBenchmarkScoreIntensityvalue);

        //BenchMark Portfolio Coverage
        String benchmarkPortfolioCoverageCompaniesUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageCompaniesSection);
        String benchmarkPortfolioCoverageCompaniesValueUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageSectionCompaniesValue);
        String benchmarkPortfolioCoverageInvestmentUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageInvestmentSection);
        String benchmarkPortfolioCoverageInvestmentValueUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageSectionInvestmentValue);

        //BenchMark Portfolio Distribution
        List<List<String>> benchmarkPortfolioDistributionList = new ArrayList<>();
        int sizeofbenchmarkPortfolioDistribution = researchLinePage.BenchMarkDistributionTableRow.size();
        for (int i = 0; i < sizeofbenchmarkPortfolioDistribution; i++) {
            //System.out.println(Arrays.toString(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
            benchmarkPortfolioDistributionList.add(Arrays.asList(researchLinePage.BenchMarkDistributionTableRow.get(i).getText().split(" ")));
        }

        //----- Excel Reading For BenchMark
        //BenchMark Portfolio Score
        String benchmarkPortfolioScore = researchLinePage.getDataFromExportedFile(4, 3, researchLine);
        String benchmarkCarbonFootprint = researchLinePage.getDataFromExportedFile(6, 3, researchLine);
        String benchmarkCarbonFootprintScore = researchLinePage.getDataFromExportedFile(6, 5, researchLine);

        String benchmarkCarbonIntensity = researchLinePage.getDataFromExportedFile(7, 3, researchLine);
        String benchmarkCarbonIntensityScore = researchLinePage.getDataFromExportedFile(7, 5, researchLine).split("\\.")[0];

        ////BenchMark Portfolio Coverage
        String portfolioCoverage = researchLinePage.getDataFromExportedFile(16, 2, researchLine);
        String benchmarkPortfolioCoverageCompanies = researchLinePage.getDataFromExportedFile(17, 2, researchLine);
        String benchmarkPortfolioCoverageCompaniesValue = researchLinePage.getDataFromExportedFile(18, 2, researchLine);
        String benchmarkPortfolioCoverageInvestment = researchLinePage.getDataFromExportedFile(17, 3, researchLine);
        String benchmarkPortfolioCoverageInvestmentValue = researchLinePage.getDataFromExportedFile(18, 3, researchLine);

        //BenchMark Portfolio Distribution
        String benchmarkPortfolioDistributionCategoryName = researchLinePage.getDataFromExportedFile(10, 0, researchLine);
        String benchmarkPortfolioDistributionCategoryInvestmentName = researchLinePage.getDataFromExportedFile(10, 3, researchLine);
        String benchmarkPortfolioDistributionCategoryCompanyName = researchLinePage.getDataFromExportedFile(10, 4, researchLine).split("\\.")[0];


        String benchmarkPortfolioDistributionCategory1 = researchLinePage.getDataFromExportedFile(11, 0, researchLine);
        String benchmarkPortfolioDistributionCategory1Investment = researchLinePage.getDataFromExportedFile(11, 3, researchLine);
        String benchmarkPortfolioDistributionCategory1Company = researchLinePage.getDataFromExportedFile(11, 4, researchLine).split("\\.")[0];

        String benchmarkPortfolioDistributionCategory2 = researchLinePage.getDataFromExportedFile(12, 0, researchLine);
        String benchmarkPortfolioDistributionCategory2Investment = researchLinePage.getDataFromExportedFile(12, 3, researchLine);
        String benchmarkPortfolioDistributionCategory2Company = researchLinePage.getDataFromExportedFile(12, 4, researchLine).split("\\.")[0];

        String benchmarkPortfolioDistributionCategory3 = researchLinePage.getDataFromExportedFile(13, 0, researchLine);
        String benchmarkPortfolioDistributionCategory3Investment = researchLinePage.getDataFromExportedFile(13, 3, researchLine);
        String benchmarkPortfolioDistributionCategory3Company = researchLinePage.getDataFromExportedFile(13, 4, researchLine).split("\\.")[0];

        String benchmarkPortfolioDistributionCategory4 = researchLinePage.getDataFromExportedFile(14, 0, researchLine);
        String benchmarkPortfolioDistributionCategory4Investment = researchLinePage.getDataFromExportedFile(14, 3, researchLine);
        String benchmarkPortfolioDistributionCategory4Company = researchLinePage.getDataFromExportedFile(14, 4, researchLine).split("\\.")[0];


        /**
         * Assertion starting for Excel data vs UI Data
         */
        //BenchMark Portfolio Score
        try {
            assertTestCase.assertEquals(benchMarkCarbonFootprintUI, benchmarkCarbonFootprint,
                    "Carbon Foot print Portfolio Score and value verified as " + benchMarkCarbonFootprintUI + " : " + benchmarkCarbonFootprint);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Portfolio Score and value verified as " + benchMarkCarbonFootprintUI + " : " + benchmarkCarbonFootprint);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchMarkCarbonFootprintScoreUI, benchmarkCarbonFootprintScore,
                    "Carbon Foot print Portfolio Score and value verified as " + benchMarkCarbonFootprintScoreUI + " : " + benchmarkCarbonFootprintScore);
            System.out.println("Carbon Foot print Portfolio Score and value verified as " + benchmarkCarbonFootprint + " : " + benchmarkCarbonFootprintScore);
        } catch (AssertionError e) {
            test.fail("Carbon Foot print Portfolio Score and value verified as " + benchMarkCarbonFootprintScoreUI + " : " + benchmarkCarbonFootprintScore);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchMarkCarbonIntensityUI, benchmarkCarbonIntensity,
                    "Carbon Intensity in Portfolio Score and value verified as " + benchMarkCarbonIntensityUI + " : " + benchmarkCarbonIntensity);
        } catch (AssertionError e) {
            test.fail("Carbon Intensity in Portfolio Score and value verified as " + benchMarkCarbonIntensityUI + " : " + benchmarkCarbonIntensity);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchMarkCarbonIntensityScoreUI, benchmarkCarbonIntensityScore,
                    "Carbon Intensity in Portfolio Score and value verified as " + benchMarkCarbonIntensityScoreUI + " : " + benchmarkCarbonIntensityScore);
            System.out.println("Carbon Intensity in Portfolio Score and value verified as " + benchmarkCarbonIntensity + " : " + benchmarkCarbonIntensityScore);
        } catch (AssertionError e) {
            test.fail("Carbon Intensity in Portfolio Score and value verified as " + benchMarkCarbonIntensityScoreUI + " : " + benchmarkCarbonIntensityScore);
            e.printStackTrace();
        }
        try {
            //BenchMark Portfolio Coverage
            assertTestCase.assertEquals(benchmarkPortfolioCoverageCompaniesUI, benchmarkPortfolioCoverageCompanies,
                    "Portfolio Coverage verified as " + benchmarkPortfolioCoverageCompaniesUI + " : " + benchmarkPortfolioCoverageCompanies);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkPortfolioCoverageCompaniesUI + " : " + benchmarkPortfolioCoverageCompanies);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioCoverageCompaniesValueUI, benchmarkPortfolioCoverageCompaniesValue,
                    "Portfolio Coverage verified as " + benchmarkPortfolioCoverageCompaniesValueUI + " : " + benchmarkPortfolioCoverageCompaniesValue);
            System.out.println("Portfolio Coverage verified as " + benchmarkPortfolioCoverageCompanies + " : " + benchmarkPortfolioCoverageCompaniesValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkPortfolioCoverageCompaniesValueUI + " : " + benchmarkPortfolioCoverageCompaniesValue);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals("% " + benchmarkPortfolioCoverageInvestmentUI, benchmarkPortfolioCoverageInvestment,
                    "Portfolio Coverage verified as " + benchmarkPortfolioCoverageInvestmentUI + " : " + benchmarkPortfolioCoverageInvestment);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkPortfolioCoverageInvestmentUI + " : " + benchmarkPortfolioCoverageInvestment);
            e.printStackTrace();
        }
        try {// investment value like %78
            assertTestCase.assertEquals(benchmarkPortfolioCoverageInvestmentValueUI,
                    Math.round(Float.parseFloat(benchmarkPortfolioCoverageInvestmentValue.substring(0, benchmarkPortfolioCoverageInvestmentValue.length() - 1))) + "%",
                    "Portfolio Coverage verified as " + benchmarkPortfolioCoverageInvestmentValueUI + " : " + benchmarkPortfolioCoverageInvestmentValue);
            System.out.println("Portfolio Coverage verified as " + benchmarkPortfolioCoverageInvestment + " : " + benchmarkPortfolioCoverageInvestmentValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkPortfolioCoverageInvestmentValueUI + " : " + benchmarkPortfolioCoverageInvestmentValue);
            e.printStackTrace();
        }
        //BenchMark Portfolio Distribution
        List<String> benchmarkPortfolioDistributionCategoryColumnNames = Arrays.asList(researchLinePage.portfolioDistributionAllTableHeaders.get(0).getText().split(" "));
        String benchmarkPortfolioDistributionCategoryNameUI = benchmarkPortfolioDistributionCategoryColumnNames.get(0);
        String benchmarkPortfolioDistributionCategoryInvestmentNameUI = "% " + benchmarkPortfolioDistributionCategoryColumnNames.get(2);
        String benchmarkPortfolioDistributionCategoryCompanyNameUI = benchmarkPortfolioDistributionCategoryColumnNames.get(3);
        String benchmarkPortfolioDistributionCategoryUI1 = benchmarkPortfolioDistributionList.get(0).get(0);
        String benchmarkPortfolioDistributionCategory1InvestmentUI = benchmarkPortfolioDistributionList.get(0).get(1);
        String benchmarkPortfolioDistributionCategory1CompanyUI = benchmarkPortfolioDistributionList.get(0).get(2);
        String benchmarkPortfolioDistributionCategory2UI = benchmarkPortfolioDistributionList.get(1).get(0);
        String benchmarkPortfolioDistributionCategory2InvestmentUI = benchmarkPortfolioDistributionList.get(1).get(1);
        String benchmarkPortfolioDistributionCategory2CompanyUI = benchmarkPortfolioDistributionList.get(1).get(2);
        String benchmarkPortfolioDistributionCategory3UI = benchmarkPortfolioDistributionList.get(2).get(0);
        String benchmarkPortfolioDistributionCategory3InvestmentUI = benchmarkPortfolioDistributionList.get(2).get(1);
        String benchmarkPortfolioDistributionCategory3CompanyUI = benchmarkPortfolioDistributionList.get(2).get(2);
        String benchmarkPortfolioDistributionCategory4UI = benchmarkPortfolioDistributionList.get(3).get(0);
        String benchmarkPortfolioDistributionCategory4InvestmentUI = benchmarkPortfolioDistributionList.get(3).get(1);
        String benchmarkPortfolioDistributionCategory4CompanyUI = benchmarkPortfolioDistributionList.get(3).get(2);

        System.out.println("==> Portfolio Distribution <==");
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategoryNameUI, benchmarkPortfolioDistributionCategoryName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + benchmarkPortfolioDistributionCategoryName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + benchmarkPortfolioDistributionCategoryName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + benchmarkPortfolioDistributionCategoryName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals("Benchmark Investment", benchmarkPortfolioDistributionCategoryInvestmentName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + benchmarkPortfolioDistributionCategoryInvestmentName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + benchmarkPortfolioDistributionCategoryInvestmentName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + benchmarkPortfolioDistributionCategoryInvestmentName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals("Benchmark Companies", benchmarkPortfolioDistributionCategoryCompanyName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + benchmarkPortfolioDistributionCategoryCompanyName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + benchmarkPortfolioDistributionCategoryCompanyName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + benchmarkPortfolioDistributionCategoryCompanyName);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategoryUI1, benchmarkPortfolioDistributionCategory1,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + benchmarkPortfolioDistributionCategory1);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + benchmarkPortfolioDistributionCategory1);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + benchmarkPortfolioDistributionCategory1);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory1InvestmentUI, benchmarkPortfolioDistributionCategory1Investment,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + benchmarkPortfolioDistributionCategory1Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + benchmarkPortfolioDistributionCategory1Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + benchmarkPortfolioDistributionCategory1Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory1CompanyUI, benchmarkPortfolioDistributionCategory1Company,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + benchmarkPortfolioDistributionCategory1Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + benchmarkPortfolioDistributionCategory1Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + benchmarkPortfolioDistributionCategory1Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2UI, benchmarkPortfolioDistributionCategory2,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + benchmarkPortfolioDistributionCategory2);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + benchmarkPortfolioDistributionCategory2);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + benchmarkPortfolioDistributionCategory2);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2InvestmentUI, benchmarkPortfolioDistributionCategory2Investment,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + benchmarkPortfolioDistributionCategory2Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + benchmarkPortfolioDistributionCategory2Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + benchmarkPortfolioDistributionCategory2Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2CompanyUI, benchmarkPortfolioDistributionCategory2Company,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + benchmarkPortfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + benchmarkPortfolioDistributionCategory2Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + benchmarkPortfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3UI, benchmarkPortfolioDistributionCategory3,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + benchmarkPortfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3UI + " : " + benchmarkPortfolioDistributionCategory3);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + benchmarkPortfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3InvestmentUI, benchmarkPortfolioDistributionCategory3Investment,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + benchmarkPortfolioDistributionCategory3Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + benchmarkPortfolioDistributionCategory3Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + benchmarkPortfolioDistributionCategory3Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3CompanyUI, benchmarkPortfolioDistributionCategory3Company,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + benchmarkPortfolioDistributionCategory3Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + benchmarkPortfolioDistributionCategory3Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + benchmarkPortfolioDistributionCategory3Company);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory4UI, benchmarkPortfolioDistributionCategory4,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4UI + " : " + benchmarkPortfolioDistributionCategory4);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4UI + " : " + benchmarkPortfolioDistributionCategory4);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4UI + " : " + benchmarkPortfolioDistributionCategory4);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory4InvestmentUI, benchmarkPortfolioDistributionCategory4Investment,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4InvestmentUI + " : " + benchmarkPortfolioDistributionCategory4Investment);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4InvestmentUI + " : " + benchmarkPortfolioDistributionCategory4Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4InvestmentUI + " : " + benchmarkPortfolioDistributionCategory4Investment);
            e.printStackTrace();
        }
        try {
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory4CompanyUI, benchmarkPortfolioDistributionCategory4Company,
                    "Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4CompanyUI + " : " + benchmarkPortfolioDistributionCategory4Company);
            System.out.println("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4CompanyUI + " : " + benchmarkPortfolioDistributionCategory4Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values US vs Excel field verified " + benchmarkPortfolioDistributionCategory4CompanyUI + " : " + benchmarkPortfolioDistributionCategory4Company);
            e.printStackTrace();
        }


    }


}