package com.esgc.Tests.UI.PortfolioAnalysisPage.ExcelComparison;

import com.esgc.APIModels.*;
import com.esgc.Controllers.APIController;
import com.esgc.Pages.ResearchLinePage;
import com.esgc.Reporting.CustomAssertion;
import com.esgc.Tests.TestBases.UITestBase;
import com.esgc.Utilities.ExcelUtil;
import com.esgc.Utilities.PortfolioUtilities;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.WebElement;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BrownShareAssessment extends UITestBase {
    public CustomAssertion assertTestCase = new CustomAssertion();

    public void verifyBrownShareAssessment(String researchLine, String portfolio_id, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        /**
         * Getting UI values
         */
        //Portfolio Score
        List<String> portfolioPortfolioScoreTexts = new ArrayList<>();
        int sizeofPortfolioDistribution = researchLinePage.greenSharePortfolioScoreTexts.size();
        for (int i = 0; i < sizeofPortfolioDistribution; i++) {
            System.out.println(researchLinePage.greenSharePortfolioScoreTexts.get(i).getText());
            portfolioPortfolioScoreTexts.add(researchLinePage.greenSharePortfolioScoreTexts.get(i).getText());
        }
        String brownShareUI = portfolioPortfolioScoreTexts.get(0); //Of Investments In Companies Offering Green Solutions
        String brownshareScoreUI = researchLinePage.getUIValues(researchLinePage.portfolioScoreGreenShareValue);


        //Portfolio Coverage
        String brownShareInvestmentUI = portfolioPortfolioScoreTexts.get(2);
        String brownShareInvestmentScoreUI = researchLinePage.getUIValues(researchLinePage.portfolioScoreGreenShareInvestmentValue);
        String brownShareCompaniesUI = portfolioPortfolioScoreTexts.get(1);
        String brownShareCompaniesScoreUI = researchLinePage.getUIValues(researchLinePage.portfolioScoreGreenShareCompaniesValue);


        List<List<String>> portfolioDistributionList = new ArrayList<>();
        int sizeofPortfolioDistribution1 = researchLinePage.portfolioDistributionAllTable.size();
        for (int i = 0; i < sizeofPortfolioDistribution1; i++) {
            // System.out.println(Arrays.toString(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
            portfolioDistributionList.add(Arrays.asList(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
        }
       /* String result=researchLinePage.getFirstCellValueFromExportedFile("Emissions",researchLine);
        System.out.println("=================");
        System.out.println(result);
        System.out.println("=================");*/
        //Portfolio Score Excel readings
        String portfolioScore = researchLinePage.getDataFromExportedFile(4, 0, researchLine);

        String brownSharePortfolioScoreText = researchLinePage.getDataFromExportedFile(6, 0, researchLine).split("-")[1];
        String brownSharePortfolioScoreValue = researchLinePage.getDataFromExportedFile(6, 0, researchLine).split("-")[0];
        brownSharePortfolioScoreValue = Math.round(Double.parseDouble(brownSharePortfolioScoreValue.substring(0, brownSharePortfolioScoreValue.trim().length() - 1))) + "%";
        //Portfolio Coverage
        String portfolioCoverage = researchLinePage.getDataFromExportedFile(14, 0, researchLine);
        String brownShareCompaniesCompanies = researchLinePage.getDataFromExportedFile(15, 0, researchLine);
        String brownShareCompaniesCompaniesValue = researchLinePage.getDataFromExportedFile(16, 0, researchLine);
        String brownShareInvestment = researchLinePage.getDataFromExportedFile(15, 1, researchLine);
        String brownShareInvestmentValue = researchLinePage.getDataFromExportedFile(16, 1, researchLine);

        //Portfolio Distribution
        String portfolioDistribution = researchLinePage.getDataFromExportedFile(8, 0, researchLine);
        String portfolioDistributionCategoryName = researchLinePage.getDataFromExportedFile(9, 0, researchLine);
        String portfolioDistributionCategoryInvestmentName = researchLinePage.getDataFromExportedFile(9, 1, researchLine);
        String portfolioDistributionCategoryCompanyName = researchLinePage.getDataFromExportedFile(9, 2, researchLine).split("\\.")[0];


        String portfolioDistributionCategory1 = researchLinePage.getDataFromExportedFile(10, 0, researchLine);
        String portfolioDistributionCategory1Investment = researchLinePage.getDataFromExportedFile(10, 1, researchLine);
        String portfolioDistributionCategory1Company = researchLinePage.getDataFromExportedFile(10, 2, researchLine).split("\\.")[0];

        String portfolioDistributionCategory2 = researchLinePage.getDataFromExportedFile(11, 0, researchLine);
        String portfolioDistributionCategory2Investment = researchLinePage.getDataFromExportedFile(11, 1, researchLine);
        String portfolioDistributionCategory2Company = researchLinePage.getDataFromExportedFile(11, 2, researchLine).split("\\.")[0];

        String portfolioDistributionCategory3 = researchLinePage.getDataFromExportedFile(12, 0, researchLine);
        String portfolioDistributionCategory3Investment = researchLinePage.getDataFromExportedFile(12, 1, researchLine);
        String portfolioDistributionCategory3Company = researchLinePage.getDataFromExportedFile(12, 2, researchLine).split("\\.")[0];


        /**
         * Assertion starting for Excel data vs UI Data
         */
        //Portfolio Score
        try { //Portfolio Score Text: Overall Fossil Fuels Industry Revenues
            assertTestCase.assertEquals(brownShareUI, brownSharePortfolioScoreText.trim(),
                    "Brown Share Assessments Portfolio Score and value verified as " + brownShareUI + " : " + brownSharePortfolioScoreText);
        } catch (AssertionError e) {
            test.fail("Brown Share Assessments Portfolio Score and value verified as " + brownShareUI + " : " + brownSharePortfolioScoreText);
            e.printStackTrace();
        }
        try {// Portfolio Score value :4%
            assertTestCase.assertEquals(brownshareScoreUI, brownSharePortfolioScoreValue,
                    "Brown Share Assessments Portfolio Score and value verified as " + brownshareScoreUI + " : " + brownSharePortfolioScoreValue);
            System.out.println("Brown Share Assessments Portfolio Score and value verified as " + brownSharePortfolioScoreText + " : " + brownSharePortfolioScoreValue);
        } catch (AssertionError e) {
            test.fail("Brown Share Assessments Portfolio Score and value verified as " + brownshareScoreUI + " : " + brownSharePortfolioScoreValue);
            e.printStackTrace();
        }

        //Portfolio Coverage
        try { //Portfolio Coverage text: Companies
            assertTestCase.assertEquals(brownShareCompaniesUI, brownShareCompaniesCompanies,
                    "Portfolio Coverage verified as " + brownShareCompaniesUI + " : " + brownShareCompaniesCompanies);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + brownShareCompaniesUI + " : " + brownShareCompaniesCompanies);
            e.printStackTrace();
        }
        try { //Portfolio Coverage Companies Score : 310/400
            assertTestCase.assertEquals(brownShareCompaniesScoreUI, brownShareCompaniesCompaniesValue,
                    "Portfolio Coverage verified as " + brownShareCompaniesScoreUI + " : " + brownShareCompaniesCompaniesValue);
            System.out.println("Portfolio Coverage verified as " + brownShareCompaniesCompanies + " : " + brownShareCompaniesCompaniesValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + brownShareCompaniesScoreUI + " : " + brownShareCompaniesCompaniesValue);
            e.printStackTrace();
        }
        try { //Portfolio Coverage Investment Text : % Investment
            assertTestCase.assertEquals("% " + brownShareInvestmentUI, brownShareInvestment,
                    "Portfolio Coverage verified as " + brownShareInvestmentUI + " : " + brownShareInvestment);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + brownShareInvestmentUI + " : " + brownShareInvestment);
            e.printStackTrace();
        }
        try {//Portfolio Coverage Investment value : 8.26%
            assertTestCase.assertEquals(brownShareInvestmentScoreUI,
                    Math.round(Float.parseFloat(brownShareInvestmentValue.substring(0, brownShareInvestmentValue.length() - 1))) + "%",
                    "Portfolio Coverage verified as " + brownShareInvestmentScoreUI + " : " + brownShareInvestmentValue);
            System.out.println("Portfolio Coverage verified as " + brownShareInvestment + " : " + brownShareInvestmentValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + brownShareInvestmentScoreUI + " : " + brownShareInvestmentValue);
            e.printStackTrace();
        }
        //Portfolio Distribution
        List<String> portfolioDistributionCategoryColumnNames = Arrays.asList(researchLinePage.portfolioDistributionAllTableHeaders.get(0).getText().split(" "));
        String portfolioDistributionCategoryNameUI = portfolioDistributionCategoryColumnNames.get(0) + " " + portfolioDistributionCategoryColumnNames.get(1);
        String portfolioDistributionCategoryInvestmentNameUI = "% " + portfolioDistributionCategoryColumnNames.get(3);
        String portfolioDistributionCategoryCompanyNameUI = portfolioDistributionCategoryColumnNames.get(4);

        String portfolioDistributionCategoryUI1 = portfolioDistributionList.get(0).get(0);
        String portfolioDistributionCategory1InvestmentUI = portfolioDistributionList.get(0).get(1);
        String portfolioDistributionCategory1CompanyUI = portfolioDistributionList.get(0).get(2);
        String portfolioDistributionCategory2UI = portfolioDistributionList.get(1).get(0);
        String portfolioDistributionCategory2InvestmentUI = portfolioDistributionList.get(1).get(1);
        String portfolioDistributionCategory2CompanyUI = portfolioDistributionList.get(1).get(2);
        String portfolioDistributionCategory3UI = portfolioDistributionList.get(2).get(0);
        String portfolioDistributionCategory3InvestmentUI = portfolioDistributionList.get(2).get(1);
        String portfolioDistributionCategory3CompanyUI = portfolioDistributionList.get(2).get(2);

        System.out.println("==> Portfolio Distribution <==");
        //Verifying "Score Range" header
        try { //Portfolio Distribution : Score Range
            assertTestCase.assertEquals(portfolioDistributionCategoryNameUI, portfolioDistributionCategoryName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            e.printStackTrace();
        }
        try {//Portfolio Distribution : Score Range
            //Verifying "% Investment" header
            assertTestCase.assertEquals(portfolioDistributionCategoryInvestmentNameUI, portfolioDistributionCategoryInvestmentName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            e.printStackTrace();
        }
        try {//Verifying "Company" header
            assertTestCase.assertEquals(portfolioDistributionCategoryCompanyNameUI, portfolioDistributionCategoryCompanyName,
                    "Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + portfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 1 value :0%
            assertTestCase.assertEquals(portfolioDistributionCategoryUI1, portfolioDistributionCategory1,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 2 value :65.46%
            assertTestCase.assertEquals(portfolioDistributionCategory1InvestmentUI, portfolioDistributionCategory1Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 3 value :260
            assertTestCase.assertEquals(portfolioDistributionCategory1CompanyUI, portfolioDistributionCategory1Company,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 2, Cell 1 value :
            assertTestCase.assertEquals(portfolioDistributionCategory2UI, portfolioDistributionCategory2,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 2, Cell 2 value :
            assertTestCase.assertEquals(portfolioDistributionCategory2InvestmentUI, portfolioDistributionCategory2Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 3, Cell 3 value :
            assertTestCase.assertEquals(portfolioDistributionCategory2CompanyUI, portfolioDistributionCategory2Company,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 4, Cell 1 value :
            assertTestCase.assertEquals(portfolioDistributionCategory3UI, portfolioDistributionCategory3,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 4, Cell 2 value :
            assertTestCase.assertEquals(portfolioDistributionCategory3InvestmentUI, portfolioDistributionCategory3Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 4, Cell 3 value :
            assertTestCase.assertEquals(portfolioDistributionCategory3CompanyUI, portfolioDistributionCategory3Company,
                    "Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + portfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            e.printStackTrace();
        }

        //Portfolio Updates

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
        List<String> updatesUIHeaders = researchLinePage.brownAndGreenShareHeaders.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> excelHeaders = new ArrayList<>();
        for (int i = updatesHeadersRow, j = 0; j < 9; j++) {
            excelHeaders.add(exportedDocument.getCellData(i, j));
        }
        //Asserting the headers
        System.out.println("Excel Headers " + excelHeaders);
        System.out.println("UI Headers " + updatesUIHeaders);
        assertTestCase.assertTrue(CollectionUtils.isEqualCollection(updatesUIHeaders, excelHeaders));

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
        //Comparing both API and Excel data for update.
        assertTestCase.assertTrue(CollectionUtils.isEqualCollection(excelUpdateList, myList));
        System.out.println("Assertion Successful");

        //Leaders & Laggards
        List<String> leadersLaggardsTableHeaders = researchLinePage.LeadersAndLaggardsTableColumns.stream().map(WebElement::getText).collect(Collectors.toList());
        List<String> leadersTableHeaders = leadersLaggardsTableHeaders.subList(0, 4);

        List<String> laggardsTableHeaders = leadersLaggardsTableHeaders.subList(4, 8);

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
            String scoreCategoryInUI = company.getScoreCategory();


            int rankInExcel = Double.valueOf(exportedDocument.getCellData(i, 0)).intValue();
            String companyInExcel = exportedDocument.getCellData(i, 1);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 2).replace("%", ""));
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 3);


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
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Leaders Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                System.out.println("Leaders Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                test.fail("Leaders Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
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
            String scoreCategoryInUI = company.getScoreCategory();


            int rankInExcel = Double.valueOf(exportedDocument.getCellData(i, 0)).intValue();
            String companyInExcel = exportedDocument.getCellData(i, 1);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 2).replace("%", ""));
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 3);


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
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Laggards Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                System.out.println("Laggards Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                test.fail("Laggards Score vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
                e.printStackTrace();
            }


        }
        //verify the company summary info in the Excel document for region
        verifyBSARegions(researchLine, portfolio_id, controller, payload, exportedDocument);
        //verify the company summary info in the Excel document for region
        verifyBSARegionDetails(researchLine, portfolio_id, controller, payload, exportedDocument);

        //verify the company summary info in the Excel document for sectors
        verifyBSASectors(researchLine, portfolio_id, controller, payload, exportedDocument);
        //verify the company summary info in the Excel document for Sector
        verifyBSASectorDetails(researchLine, portfolio_id, controller, payload, exportedDocument);


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


        //There are 3 Headers in the table
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
            String scoreCategoryInUI = company.getScore_category();

            String companyInExcel = exportedDocument.getCellData(i, 0);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 1).replace("%", ""));
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 2);

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
            String scoreCategoryInUI = company.getScore_category();

            String companyInExcel = exportedDocument.getCellData(i, 0);
            double investmentInExcel = Double.parseDouble(exportedDocument.getCellData(i, 1).replace("%", ""));
            String scoreCategoryInExcel = exportedDocument.getCellData(i, 2);

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
                assertTestCase.assertEquals(scoreCategoryInUI, scoreCategoryInExcel,
                        "Negative Impact Score Category vs Excel field verified " + scoreCategoryInUI + " : " + scoreCategoryInExcel);
            } catch (AssertionError e) {
                e.printStackTrace();
            }

        }

        //Underlying Data Metrics
        List<String> underlyingDataHeaders = researchLinePage.getUnderlyingDataMetricHeaders();
        System.out.println("underlyingDataHeaders = " + underlyingDataHeaders);

        List<UnderlyingDataMetricsWrapperNew> dataMetricsWrapperList = Arrays.asList(controller
                .getPortfolioUnderlyingDataMetricsResponse(portfolio_id, researchLine, payload).as(UnderlyingDataMetricsWrapperNew[].class));

        Cell underlyingDataMetricsCell = exportedDocument.searchCellData("Fossil Fuel Disclosures");

        int underlyingDataTitleRow = underlyingDataMetricsCell.getRowIndex();
        int underlyingDataHeadersRow = underlyingDataTitleRow + 1;
        int underlyingDataStartsFrom = underlyingDataHeadersRow + 1;
        int underlyingDataEndsAt = underlyingDataHeadersRow + 14;


        String underlyingDataTitleInExportedDocument = exportedDocument.getCellData(underlyingDataTitleRow, 0);
        assertTestCase.assertEquals(underlyingDataTitleInExportedDocument, "Fossil Fuel Disclosures",
                "Underlying Data Title in Excel verified");

        //Loop Through each metric
        for (int i = 0; i < underlyingDataHeaders.size(); i++) {

            String headerInUI = underlyingDataHeaders.get(i);
            String headerInExcel = exportedDocument.getCellData(underlyingDataHeadersRow, (i * 4));

            assertTestCase.assertEquals(headerInExcel, headerInUI,
                    "Underlying Data Metric vs Excel field verified " + headerInExcel + " : " + headerInUI);

            assertTestCase.assertEquals(dataMetricsWrapperList.get(i).getTitle(), headerInUI,
                    "Underlying Data Metric vs UI field verified " + dataMetricsWrapperList.get(i).getTitle() + " : " + headerInUI);

            String dataType = dataMetricsWrapperList.get(i).getType();

            if (dataType.equals("range")) {
                underlyingDataEndsAt = underlyingDataStartsFrom + 14;
            } else if (dataType.equals("boolean")) {
                underlyingDataEndsAt = underlyingDataStartsFrom + 12;
            }

            //check each metric's distribution in Excel document

            String subHeader1InExcel = exportedDocument.getCellData(underlyingDataStartsFrom, (i * 4) + 1);
            String subHeader1InUI = "Investment in Category";

            assertTestCase.assertEquals(subHeader1InUI, subHeader1InExcel,
                    "Investment in Category Title in UI vs Excel field verified " + subHeader1InUI + " : " + subHeader1InExcel);

            String categoryTitleInExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 1, (i * 4) + 1);
            String categoryTitleInUI = "Category";

            assertTestCase.assertEquals(categoryTitleInUI, categoryTitleInExcel,
                    "Category Title in UI vs Excel field verified " + categoryTitleInUI + " : " + categoryTitleInExcel);

            String investmentTitleInExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 1, (i * 4) + 2);
            String investmentTitleInUI = "% Investment";

            assertTestCase.assertEquals(investmentTitleInUI, investmentTitleInExcel,
                    "Investment Title in UI vs Excel field verified " + investmentTitleInUI + " : " + investmentTitleInExcel);

            int coverage = Integer.parseInt(brownShareCompaniesCompaniesValue.substring(brownShareCompaniesCompaniesValue.indexOf("/") + 1));


            if (dataType.equals("range")) {
                String category1InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 2, (i * 4) + 1);
                String category1InUI = "0%";

                assertTestCase.assertEquals(category1InUI, category1InExcel,
                        "1st Category in UI vs Excel field verified " + category1InUI + " : " + category1InExcel);

                Integer category1ValueInExcel = Integer.parseInt(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 2, (i * 4) + 2).replace("%", ""));
                Integer category1ValueInUI = (int) PortfolioUtilities.round(dataMetricsWrapperList.get(i).getData().get(1).get(2).getData().get(0), 0);

                assertTestCase.assertEquals(category1ValueInUI, category1ValueInExcel,
                        "1st Category Value in UI vs Excel field verified " + category1ValueInUI + " : " + category1ValueInExcel);

                String category2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 3, (i * 4) + 1);
                String category2InUI = "0-20%";

                assertTestCase.assertEquals(category2InUI, category2InExcel,
                        "2nd Category in UI vs Excel field verified " + category2InUI + " : " + category2InExcel);

                Integer category2ValueInExcel = Integer.parseInt(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 3, (i * 4) + 2).replace("%", ""));
                Integer category2ValueInUI = (int) PortfolioUtilities.round(dataMetricsWrapperList.get(i).getData().get(1).get(1).getData().get(0), 0);

                assertTestCase.assertEquals(category2ValueInUI, category2ValueInExcel,
                        "2nd Category Value in UI vs Excel field verified " + category2ValueInUI + " : " + category2ValueInExcel);

                String category3InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 4, (i * 4) + 1);
                String category3InUI = "20-100%";

                assertTestCase.assertEquals(category3InUI, category3InExcel,
                        "3rd Category in UI vs Excel field verified " + category3InUI + " : " + category3InExcel);

                Integer category3ValueInExcel = Integer.parseInt(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 4, (i * 4) + 2).replace("%", ""));
                Integer category3ValueInUI = (int) PortfolioUtilities.round(dataMetricsWrapperList.get(i).getData().get(1).get(0).getData().get(0), 0);

                assertTestCase.assertEquals(category3ValueInUI, category3ValueInExcel,
                        "3rd Category Value in UI vs Excel field verified " + category3ValueInUI + " : " + category3ValueInExcel);

                String category4InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 5, (i * 4) + 1);
                String category4InUI = "Not Covered";

                assertTestCase.assertEquals(category4InUI, category4InExcel,
                        "4th Category in UI vs Excel field verified " + category4InUI + " : " + category4InExcel);

                Integer category4ValueInExcel = Integer.parseInt(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 5, (i * 4) + 2).replace("%", ""));
                Integer category4ValueInUI = 100 - (category1ValueInUI + category2ValueInUI + category3ValueInUI);

                assertTestCase.assertEquals(category4ValueInUI, category4ValueInExcel,
                        "4th Category Value in UI vs Excel field verified " + category4ValueInUI + " : " + category4ValueInExcel);

                String subHeader2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 7, (i * 4) + 1);
                String subHeader2InUI = "Companies in Category";

                assertTestCase.assertEquals(subHeader2InUI, subHeader2InExcel,
                        "Companies in Category Title in UI vs Excel field verified " + subHeader2InUI + " : " + subHeader2InExcel);

                String categoryTitle2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 8, (i * 4) + 1);
                String categoryTitle2InUI = "Category";

                assertTestCase.assertEquals(categoryTitle2InUI, categoryTitle2InExcel,
                        "Category Title in UI vs Excel field verified " + categoryTitle2InUI + " : " + categoryTitle2InExcel);

                String companiesTitleInExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 8, (i * 4) + 2);
                String companiesTitleInUI = "Companies";

                assertTestCase.assertEquals(companiesTitleInUI, companiesTitleInExcel,
                        "Companies Title in UI vs Excel field verified " + companiesTitleInUI + " : " + companiesTitleInExcel);

                String companiesCategory1InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 9, (i * 4) + 1);
                String companiesCategory1InUI = "0%";

                assertTestCase.assertEquals(companiesCategory1InUI, companiesCategory1InExcel,
                        "1st Companies Category in UI vs Excel field verified " + companiesCategory1InUI + " : " + companiesCategory1InExcel);

                Integer companiesCategory1ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 9, (i * 4) + 2));
                Integer companiesCategory1ValueInUI = dataMetricsWrapperList.get(i).getData().get(0).get(2).getCount();

                assertTestCase.assertEquals(companiesCategory1ValueInUI, companiesCategory1ValueInExcel,
                        "1st Companies Category Value in UI vs Excel field verified " + companiesCategory1ValueInUI + " : " + companiesCategory1ValueInExcel);

                String companiesCategory2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 10, (i * 4) + 1);
                String companiesCategory2InUI = "0-20%";

                assertTestCase.assertEquals(companiesCategory2InUI, companiesCategory2InExcel,
                        "2nd companiesCategory in UI vs Excel field verified " + companiesCategory2InUI + " : " + companiesCategory2InExcel);

                Integer companiesCategory2ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 10, (i * 4) + 2));
                Integer companiesCategory2ValueInUI = dataMetricsWrapperList.get(i).getData().get(0).get(1).getCount();

                assertTestCase.assertEquals(companiesCategory2ValueInUI, companiesCategory2ValueInExcel,
                        "2nd companiesCategory Value in UI vs Excel field verified " + companiesCategory2ValueInUI + " : " + companiesCategory2ValueInExcel);

                String companiesCategory3InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 11, (i * 4) + 1);
                String companiesCategory3InUI = "20-100%";

                assertTestCase.assertEquals(companiesCategory3InUI, companiesCategory3InExcel,
                        "3rd companiesCategory in UI vs Excel field verified " + companiesCategory3InUI + " : " + companiesCategory3InExcel);

                Integer companiesCategory3ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 11, (i * 4) + 2));
                Integer companiesCategory3ValueInUI = dataMetricsWrapperList.get(i).getData().get(0).get(0).getCount();

                assertTestCase.assertEquals(companiesCategory3ValueInUI, companiesCategory3ValueInExcel,
                        "3rd companiesCategory Value in UI vs Excel field verified " + companiesCategory3ValueInUI + " : " + companiesCategory3ValueInExcel);

                String companiesCategory4InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 12, (i * 4) + 1);
                String companiesCategory4InUI = "Not Covered";

                assertTestCase.assertEquals(companiesCategory4InUI, companiesCategory4InExcel,
                        "4th companiesCategory in UI vs Excel field verified " + companiesCategory4InUI + " : " + companiesCategory4InExcel);

                Integer companiesCategory4ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 12, (i * 4) + 2));
                Integer companiesCategory4ValueInUI = coverage - (companiesCategory1ValueInUI + companiesCategory2ValueInUI + companiesCategory3ValueInUI);

                assertTestCase.assertEquals(companiesCategory4ValueInUI, companiesCategory4ValueInExcel,
                        "4th companiesCategory Value in UI vs Excel field verified " + companiesCategory4ValueInUI + " : " + companiesCategory4ValueInExcel);

                //YES NO DATA METRICS:
            } else {

                String category1InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 2, (i * 4) + 1);
                String category1InUI = "Yes";

                assertTestCase.assertEquals(category1InUI, category1InExcel,
                        "1st Category in UI vs Excel field verified " + category1InUI + " : " + category1InExcel);

                Integer category1ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 2, (i * 4) + 2).replace("%", ""));
                Integer category1ValueInUI = (int) PortfolioUtilities.round(dataMetricsWrapperList.get(i).getData().get(1).get(1).getData().get(0), 0);

                assertTestCase.assertEquals(category1ValueInUI, category1ValueInExcel,
                        "1st Category Value in UI vs Excel field verified " + category1ValueInUI + " : " + category1ValueInExcel);

                String category2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 3, (i * 4) + 1);
                String category2InUI = "No";

                assertTestCase.assertEquals(category2InUI, category2InExcel,
                        "2nd Category in UI vs Excel field verified " + category2InUI + " : " + category2InExcel);

                Integer category2ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 3, (i * 4) + 2).replace("%", ""));
                Integer category2ValueInUI = (int) PortfolioUtilities.round(dataMetricsWrapperList.get(i).getData().get(1).get(0).getData().get(0), 0);

                assertTestCase.assertEquals(category2ValueInUI, category2ValueInExcel,
                        "2nd Category Value in UI vs Excel field verified " + category2ValueInUI + " : " + category2ValueInExcel);

                String category3InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 4, (i * 4) + 1);
                String category3InUI = "Not Covered";

                assertTestCase.assertEquals(category3InUI, category3InExcel,
                        "3rd Category in UI vs Excel field verified " + category3InUI + " : " + category3InExcel);

                Integer category3ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 4, (i * 4) + 2).replace("%", ""));
                Integer category3ValueInUI = 100 - (category1ValueInUI + category2ValueInUI);

                assertTestCase.assertEquals(category3ValueInUI, category3ValueInExcel,
                        "3rd Category Value in UI vs Excel field verified " + category3ValueInUI + " : " + category3ValueInExcel);

                String subHeader2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 6, (i * 4) + 1);
                String subHeader2InUI = "Companies in Category";

                assertTestCase.assertEquals(subHeader2InUI, subHeader2InExcel,
                        "Companies in Category Title in UI vs Excel field verified " + subHeader2InUI + " : " + subHeader2InExcel);

                String categoryTitle2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 7, (i * 4) + 1);
                String categoryTitle2InUI = "Category";

                assertTestCase.assertEquals(categoryTitle2InUI, categoryTitle2InExcel,
                        "Category Title in UI vs Excel field verified " + categoryTitle2InUI + " : " + categoryTitle2InExcel);

                String companiesTitleInExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 7, (i * 4) + 2);
                String companiesTitleInUI = "Companies";

                assertTestCase.assertEquals(companiesTitleInUI, companiesTitleInExcel,
                        "Companies Title in UI vs Excel field verified " + companiesTitleInUI + " : " + companiesTitleInExcel);

                String companiesCategory1InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 8, (i * 4) + 1);
                String companiesCategory1InUI = "Yes";

                assertTestCase.assertEquals(companiesCategory1InUI, companiesCategory1InExcel,
                        "1st companiesCategory in UI vs Excel field verified " + companiesCategory1InUI + " : " + companiesCategory1InExcel);

                Integer companiesCategory1ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 8, (i * 4) + 2));
                Integer companiesCategory1ValueInUI = dataMetricsWrapperList.get(i).getData().get(0).get(1).getCount();

                assertTestCase.assertEquals(companiesCategory1ValueInUI, companiesCategory1ValueInExcel,
                        "1st companiesCategory Value in UI vs Excel field verified " + companiesCategory1ValueInUI + " : " + companiesCategory1ValueInExcel);

                String companiesCategory2InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 9, (i * 4) + 1);
                String companiesCategory2InUI = "No";

                assertTestCase.assertEquals(companiesCategory2InUI, companiesCategory2InExcel,
                        "2nd companiesCategory in UI vs Excel field verified " + companiesCategory2InUI + " : " + companiesCategory2InExcel);

                Integer companiesCategory2ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 9, (i * 4) + 2));
                Integer companiesCategory2ValueInUI = dataMetricsWrapperList.get(i).getData().get(0).get(0).getCount();

                assertTestCase.assertEquals(companiesCategory2ValueInUI, companiesCategory2ValueInExcel,
                        "2nd companiesCategory Value in UI vs Excel field verified " + companiesCategory2ValueInUI + " : " + companiesCategory2ValueInExcel);

                String companiesCategory3InExcel = exportedDocument.getCellData(underlyingDataStartsFrom + 10, (i * 4) + 1);
                String companiesCategory3InUI = "Not Covered";

                assertTestCase.assertEquals(companiesCategory3InUI, companiesCategory3InExcel,
                        "3rd companiesCategory in UI vs Excel field verified " + companiesCategory3InUI + " : " + companiesCategory3InExcel);

                Integer companiesCategory3ValueInExcel = (int) Double.parseDouble(
                        exportedDocument.getCellData(underlyingDataStartsFrom + 10, (i * 4) + 2));
                Integer companiesCategory3ValueInUI = coverage - (companiesCategory1ValueInUI + companiesCategory2ValueInUI);

                assertTestCase.assertEquals(companiesCategory3ValueInUI, companiesCategory3ValueInExcel,
                        "3rd companiesCategory Value in UI vs Excel field verified " + companiesCategory3ValueInUI + " : " + companiesCategory3ValueInExcel);
            }
        }
    }

    /**
     * Verify the Brown Share Assesment for the region in the Excel document
     *
     * @param researchLine
     * @param portfolioId
     * @param controller
     * @param payload
     * @param exportedDocument
     */
    private void verifyBSARegionDetails(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Brown Share Assessment Region Details from API");

        //Get all Region Details
        List<RegionSectorDetail> rsAPI = Arrays.asList(
                controller.getPortfolioRegionDetailsResponse(portfolioId, researchLine, payload)
                        .as(RegionSectorDetail[].class));

        List<Company> companiesAPI = new ArrayList<>();
        for (RegionSectorDetail rs : rsAPI) {
            System.out.println("Region: " + rs.getName());
            if (rs.getCategory1() != null) {
                for (Company o : rs.getCategory1()) {
                    Company company = new Company();
                    company.setCompany_name(o.getCompany_name());
                    company.setInvestment_pct(o.getInvestment_pct());
                    company.setScore(o.getScore());
                    company.setScore_category("0%");
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
                    company.setScore_category("0-20%");
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
                    company.setScore_category("20-100%");
                    companiesAPI.add(company);
                }
                System.out.println("Category 3 completed");
            }
        }
        System.out.println("companiesAPI.size() = " + companiesAPI.size());
        //get all company details in a list


        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Brown Share Assessment Region Details from Excel");
        Cell regionsCell = exportedDocument.searchCellData("Regions");
        int regionsTitleRow = regionsCell.getRowIndex();
//        System.out.println("regionsTitleRow = " + regionsTitleRow);
        int regionsHeadersRow = regionsTitleRow + 1;
//        System.out.println("regionsHeadersRow = " + regionsHeadersRow);
        int regionsListStartsFrom = regionsHeadersRow + 10;
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
//                softAssert(actCompany.getScore(), expCompany.getScore(), "Score");
                softAssert(actCompany.getScore_category(), expCompany.getScore_category(), "Score Category");
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }

    public void verifyBSARegions(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Brown Share Assessment Regions from API");
        List<RegionSummary> rsAPI = Arrays.asList(
                controller.getPortfolioRegionSummaryResponse(portfolioId, researchLine, payload)
                        .as(RegionSummary[].class));

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Brown Share Assessment Region from Excel");
        Cell regionsCell = exportedDocument.searchCellData("Regions");
        int regionsTitleRow = regionsCell.getRowIndex();
        System.out.println("regionsTitleRow = " + regionsTitleRow);
        int regionsHeadersRow = regionsTitleRow + 1;
        int regionsListStartsFrom = regionsHeadersRow + 1;
        for (int i = 0; i < 3; i++) {
            String headerInExcel = exportedDocument.getCellData(regionsHeadersRow, i * 5);
            System.out.println("\n==========================");
            System.out.println("Getting Summary Info for = " + headerInExcel);
            RegionSummary rsExcel = getRegionSummary(regionsListStartsFrom, i * 5 + 1, exportedDocument);
            System.out.println("Summary data loaded from Excel to regionSummary object. Time to compare with API");
            for (int j = 0; j < rsAPI.size(); j++) {
                if (rsAPI.get(j).getRegionCode().equals(headerInExcel)) {
                    //softAssert(rsAPI.get(j).getCategory(),rsExcel.getCategory(), "Region Summary - Category Type");
                    //softAssert(rsAPI.get(j).getWeighted_average_score(),rsExcel.getWeighted_average_score(), "Region Summary - Weighted Average Score");
                    softAssert(rsAPI.get(j).getWeighted_average_score(), rsExcel.getWeighted_average_score(), "Region Summary - Average Score");
                    softAssert(rsAPI.get(j).getCountOfCompanies(), rsExcel.getCountOfCompanies(), "Region Summary - Count of Companies");
                    softAssert(Math.round(rsAPI.get(j).getInvestment_pct()) + ".0", rsExcel.getInvestment_pct() + "", "Region Summary - Investment %");
                    for (int k = 0; k < rsExcel.getPortfolioDistributionList().size(); k++) {
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getScore_range(), rsExcel.getPortfolioDistributionList().get(k).getScore_range(), "Region Summary - Portfolio Distribution table - Score Range");
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
        //regionSummary.setCategory(exportedDocument.getCellData(row++, ++column));
        regionSummary.setWeighted_average_score(Integer.parseInt(exportedDocument.getCellData(row++, ++column).replaceAll("\\D", "")));
        regionSummary.setCountOfCompanies(Integer.parseInt(exportedDocument.getCellData(row++, column).replaceAll("\\D", "")) / 10);
        regionSummary.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row++, column).replaceAll("%", "")));
        System.out.println("regionSummary is done");
        regionSummary.setPortfolioDistributionList(getPortfolioDistributionList(row + 1, column, exportedDocument));
        System.out.println("portfolioDistributionList is done");
        return regionSummary;
    }

    public List<Company> getRegionDetails(int row, int column, ExcelUtil exportedDocument) {
        System.out.println("Getting Region Details from Excel");
        List<Company> companyList = new ArrayList<>();
        try {
            while (exportedDocument.getCellData(row, column) != null) {
                Company company = new Company();
                //System.out.println("exportedDocument.getCellData(row++, column) = " + exportedDocument.getCellData(row, column));
                company.setCompany_name(exportedDocument.getCellData(row, column));
                company.setScore_category(exportedDocument.getCellData(row, column + 1));
                company.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row, column + 2).replaceAll("%", "")));
                row++;
                companyList.add(company);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();

        }
        System.out.println("End of Region Details");
        System.out.println("companyList.size() = " + companyList.size());
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
        for (int i = 0; i < 3; i++) {
            PortfolioDistribution portfolioDistribution = new PortfolioDistribution();
            portfolioDistribution.setScore_range(exportedDocument.getCellData(row, column - 1));
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
            //System.out.println(fieldName + " vs Excel field verified " + exp+ " : " + act);
            assertTestCase.assertEquals(exp, act, fieldName + " vs Excel field verified " + exp + " : " + act);
            return true;
        } catch (AssertionError e) {
            test.fail(fieldName + " vs Excel field verified " + exp + " : " + act);
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verify the Brown Share Assessment for the sectors in the Excel document
     *
     * @param researchLine
     * @param portfolioId
     * @param controller
     * @param payload
     * @param exportedDocument
     */
    private void verifyBSASectorDetails(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {
        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Brown Share Assessment Sector Details from API");

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
                    company.setScore_category("0%");
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
                    company.setScore_category("0-20%");
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
                    company.setScore_category("20-100%");
                    companiesAPI.add(company);
                }
                System.out.println("Category 3 completed");
            }
        }

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Brown Share Assessment Sector Details from Excel");
        Cell sectorsCell = exportedDocument.searchCellData("Sectors");
        int sectorsTitleRow = sectorsCell.getRowIndex();
        int sectorsHeadersRow = sectorsTitleRow + 1;
        int sectorsListStartsFrom = sectorsHeadersRow + 10;

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
                //softAssert(actCompany.getScore(), expCompany.getScore(), "Score");
                softAssert(actCompany.getScore_category(), expCompany.getScore_category(), "Score Category");
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }
    }

    public void verifyBSASectors(String researchLine, String portfolioId, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        /**
         * Getting Summary values from API instead of UI
         */
        System.out.println("Getting Brown Share Assessment Sectors from API");
        List<SectorSummary> rsAPI = Arrays.asList(
                controller.getPortfolioSectorSummaryResponse(portfolioId, researchLine, payload)
                        .as(SectorSummary[].class));

        /**
         * Getting Summary values from Excel
         */
        System.out.println("Getting Brown Share Assessment Sector from Excel");
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
                    //softAssert(rsAPI.get(j).getCategory(),rsExcel.getCategory(), "Sector Summary - Category Type");
                    softAssert(Math.round(rsAPI.get(j).getWeighted_average_score()), Math.round(rsExcel.getWeighted_average_score()), "Sector Summary - Weighted Average Score");
                    softAssert(rsAPI.get(j).getCountOfCompanies(), rsExcel.getCountOfCompanies(), "Sector Summary - Count of Companies");
                    softAssert(Math.round(rsAPI.get(j).getInvestment_pct()) + ".0", rsExcel.getInvestment_pct() + "", "Sector Summary - Investment %");
                    for (int k = 0; k < rsExcel.getPortfolioDistributionList().size(); k++) {
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getScore_range(), rsExcel.getPortfolioDistributionList().get(k).getScore_range(),
                                "Sector Summary - Portfolio Distribution table - Score Range");
                        softAssert(rsAPI.get(j).getPortfolioDistributionList().get(k).getCompanies(), rsExcel.getPortfolioDistributionList().get(k).getCompanies(),
                                "Sector Summary - Portfolio Distribution table - Number of Companies");
                        softAssert(Math.round(rsAPI.get(j).getPortfolioDistributionList().get(k).getInvestment_pct() * 100) / 100D, rsExcel.getPortfolioDistributionList().get(k).getInvestment_pct(),
                                "Sector Summary - Portfolio Distribution table - Investment %");
                    }
                }
            }
            System.out.println("++++++++++++++++++++++++++++\n");
        }


    }

    public SectorSummary getSectorSummary(int row, int column, ExcelUtil exportedDocument) {
        SectorSummary sectorSummary = new SectorSummary();
        //sectorSummary.setCategory(exportedDocument.getCellData(row++, ++column));
        sectorSummary.setWeighted_average_score(Double.parseDouble(exportedDocument.getCellData(row++, ++column).replaceAll(",", "")));
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
                //company.setScore(Double.parseDouble(exportedDocument.getCellData(row, column+1).replaceAll(",","")));
                company.setScore_category(exportedDocument.getCellData(row, column + 1));
                company.setInvestment_pct(Double.parseDouble(exportedDocument.getCellData(row, column + 2).replaceAll("%", "")));
                row++;
                companyList.add(company);
            }
        } catch (NullPointerException e) {
            System.out.println("End of Sector Details");
            System.out.println("companyList.size() = " + companyList.size());
        }
        return companyList;
    }

    public void verifyBrownShareAssessmentBenchMarkSections(String researchLine, String portfolio_id, APIController controller, APIFilterPayload payload, ExcelUtil exportedDocument) {

        ResearchLinePage researchLinePage = new ResearchLinePage();

        /**
         * Getting UI values
         */
        //Portfolio Score

        String brownShareUI = researchLinePage.getUIValues(researchLinePage.BrownShareBenchmarkScoreText);
        String brownshareScoreUI = researchLinePage.getUIValues(researchLinePage.BrownShareBenchmarkScoreValue);


        //Portfolio Coverage
        String benchmarkBrownShareInvestmentUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageInvestmentSection);
        String benchmarkGrownShareInvestmentScoreUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageSectionInvestmentValue);
        String benchmarkGrownShareCompaniesUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageCompaniesSection);
        String benchmarkGrownShareCompaniesScoreUI = researchLinePage.getUIValues(researchLinePage.BenchMarkCoverageSectionCompaniesValue);

        List<List<String>> benchmarkPortfolioDistributionList = new ArrayList<>();
        int sizeofbenchmarkPortfolioDistribution = researchLinePage.BenchMarkDistributionTableRow.size();
        for (int i = 0; i < sizeofbenchmarkPortfolioDistribution; i++) {
            // System.out.println(Arrays.toString(researchLinePage.portfolioDistributionAllTable.get(i).getText().split(" ")));
            benchmarkPortfolioDistributionList.add(Arrays.asList(researchLinePage.BenchMarkDistributionTableRow.get(i).getText().split(" ")));
        }

        //----- Excel Reading For BenchMark
        //BenchMark Portfolio Score

        String portfolioScore = researchLinePage.getDataFromExportedFile(4, 1, researchLine);

        String brownSharePortfolioScoreText = researchLinePage.getDataFromExportedFile(6, 1, researchLine).split("-")[1];
        String brownSharePortfolioScoreValue = researchLinePage.getDataFromExportedFile(6, 1, researchLine).split("-")[0];
        brownSharePortfolioScoreValue = Math.round(Double.parseDouble(brownSharePortfolioScoreValue.substring(0, brownSharePortfolioScoreValue.trim().length() - 1))) + "%";
        //Portfolio Coverage
        String portfolioCoverage = researchLinePage.getDataFromExportedFile(14, 2, researchLine);
        String brownShareCompaniesCompanies = researchLinePage.getDataFromExportedFile(15, 2, researchLine);
        String brownShareCompaniesCompaniesValue = researchLinePage.getDataFromExportedFile(16, 2, researchLine);
        String brownShareInvestment = researchLinePage.getDataFromExportedFile(15, 3, researchLine);
        String brownShareInvestmentValue = researchLinePage.getDataFromExportedFile(16, 3, researchLine);

        //Portfolio Distribution

        String portfolioDistributionCategoryName = researchLinePage.getDataFromExportedFile(9, 0, researchLine);
        String portfolioDistributionCategoryInvestmentName = researchLinePage.getDataFromExportedFile(9, 3, researchLine);
        String portfolioDistributionCategoryCompanyName = researchLinePage.getDataFromExportedFile(9, 4, researchLine).split("\\.")[0];


        String portfolioDistributionCategory1 = researchLinePage.getDataFromExportedFile(10, 0, researchLine);
        String portfolioDistributionCategory1Investment = researchLinePage.getDataFromExportedFile(10, 3, researchLine);
        String portfolioDistributionCategory1Company = researchLinePage.getDataFromExportedFile(10, 4, researchLine).split("\\.")[0];

        String portfolioDistributionCategory2 = researchLinePage.getDataFromExportedFile(11, 0, researchLine);
        String portfolioDistributionCategory2Investment = researchLinePage.getDataFromExportedFile(11, 3, researchLine);
        String portfolioDistributionCategory2Company = researchLinePage.getDataFromExportedFile(11, 4, researchLine).split("\\.")[0];

        String portfolioDistributionCategory3 = researchLinePage.getDataFromExportedFile(12, 0, researchLine);
        String portfolioDistributionCategory3Investment = researchLinePage.getDataFromExportedFile(12, 3, researchLine);
        String portfolioDistributionCategory3Company = researchLinePage.getDataFromExportedFile(12, 4, researchLine).split("\\.")[0];


        /**
         * Assertion starting for Excel data vs UI Data
         */
        //Portfolio Score
        try { //Portfolio Score Text: Overall Fossil Fuels Industry Revenues
            assertTestCase.assertEquals(brownShareUI, brownSharePortfolioScoreText.trim(),
                    "Brown Share Assessments Portfolio Score and value verified as " + brownShareUI + " : " + brownSharePortfolioScoreText);
        } catch (AssertionError e) {
            test.fail("Brown Share Assessments Portfolio Score and value verified as " + brownShareUI + " : " + brownSharePortfolioScoreText);
            e.printStackTrace();
        }
        try {// Portfolio Score value :4%
            assertTestCase.assertEquals(brownshareScoreUI, brownSharePortfolioScoreValue,
                    "Brown Share Assessments Portfolio Score and value verified as " + brownshareScoreUI + " : " + brownSharePortfolioScoreValue);
            System.out.println("Brown Share Assessments Portfolio Score and value verified as " + brownSharePortfolioScoreText + " : " + brownSharePortfolioScoreValue);
        } catch (AssertionError e) {
            test.fail("Brown Share Assessments Portfolio Score and value verified as " + brownshareScoreUI + " : " + brownSharePortfolioScoreValue);
            e.printStackTrace();
        }

        //Portfolio Coverage
        try { //Portfolio Coverage text: Companies
            assertTestCase.assertEquals(benchmarkGrownShareCompaniesUI, brownShareCompaniesCompanies,
                    "Portfolio Coverage verified as " + benchmarkGrownShareCompaniesUI + " : " + brownShareCompaniesCompanies);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkGrownShareCompaniesUI + " : " + brownShareCompaniesCompanies);
            e.printStackTrace();
        }
        try { //Portfolio Coverage Companies Score : 310/400
            assertTestCase.assertEquals(benchmarkGrownShareCompaniesScoreUI, brownShareCompaniesCompaniesValue,
                    "Portfolio Coverage verified as " + benchmarkGrownShareCompaniesScoreUI + " : " + brownShareCompaniesCompaniesValue);
            System.out.println("Portfolio Coverage verified as " + brownShareCompaniesCompanies + " : " + brownShareCompaniesCompaniesValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkGrownShareCompaniesScoreUI + " : " + brownShareCompaniesCompaniesValue);
            e.printStackTrace();
        }
        try { //Portfolio Coverage Investment Text : % Investment
            assertTestCase.assertEquals("% " + benchmarkBrownShareInvestmentUI, brownShareInvestment,
                    "Portfolio Coverage verified as " + benchmarkBrownShareInvestmentUI + " : " + brownShareInvestment);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkBrownShareInvestmentUI + " : " + brownShareInvestment);
            e.printStackTrace();
        }
        try {//Portfolio Coverage Investment value : 8.26%
            assertTestCase.assertEquals(benchmarkGrownShareInvestmentScoreUI,
                    Math.round(Float.parseFloat(brownShareInvestmentValue.substring(0, brownShareInvestmentValue.length() - 1))) + "%",
                    "Portfolio Coverage verified as " + benchmarkGrownShareInvestmentScoreUI + " : " + brownShareInvestmentValue);
            System.out.println("Portfolio Coverage verified as " + brownShareInvestment + " : " + brownShareInvestmentValue);
        } catch (AssertionError e) {
            test.fail("Portfolio Coverage verified as " + benchmarkGrownShareInvestmentScoreUI + " : " + brownShareInvestmentValue);
            e.printStackTrace();
        }
        //Portfolio Distribution
        List<String> benchmarkPrtfolioDistributionCategoryColumnNames = Arrays.asList(researchLinePage.portfolioDistributionAllTableHeaders.get(0).getText().split(" "));
        String benchmarkPortfolioDistributionCategoryNameUI = benchmarkPrtfolioDistributionCategoryColumnNames.get(0) + " " + benchmarkPrtfolioDistributionCategoryColumnNames.get(1);
        String benchmarkPortfolioDistributionCategoryInvestmentNameUI = "% " + benchmarkPrtfolioDistributionCategoryColumnNames.get(3);
        String benchmarkPortfolioDistributionCategoryCompanyNameUI = benchmarkPrtfolioDistributionCategoryColumnNames.get(4);

        String benchmarkPortfolioDistributionCategoryUI1 = benchmarkPortfolioDistributionList.get(0).get(0);
        String benchmarkPortfolioDistributionCategory1InvestmentUI = benchmarkPortfolioDistributionList.get(0).get(1);
        String benchmarkPortfolioDistributionCategory1CompanyUI = benchmarkPortfolioDistributionList.get(0).get(2);
        String benchmarkPortfolioDistributionCategory2UI = benchmarkPortfolioDistributionList.get(1).get(0);
        String benchmarkPortfolioDistributionCategory2InvestmentUI = benchmarkPortfolioDistributionList.get(1).get(1);
        String benchmarkPortfolioDistributionCategory2CompanyUI = benchmarkPortfolioDistributionList.get(1).get(2);
        String benchmarkPortfolioDistributionCategory3UI = benchmarkPortfolioDistributionList.get(2).get(0);
        String benchmarkPortfolioDistributionCategory3InvestmentUI = benchmarkPortfolioDistributionList.get(2).get(1);
        String benchmarkPortfolioDistributionCategory3CompanyUI = benchmarkPortfolioDistributionList.get(2).get(2);

        System.out.println("==> Portfolio Distribution <==");
        //Verifying "Score Range" header
        try { //Portfolio Distribution : Score Range
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategoryNameUI, portfolioDistributionCategoryName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryNameUI + " : " + portfolioDistributionCategoryName);
            e.printStackTrace();
        }
        try {//Portfolio Distribution : Score Range
            //Verifying "% Investment" header
            assertTestCase.assertEquals("Benchmark Investment", portfolioDistributionCategoryInvestmentName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryInvestmentNameUI + " : " + portfolioDistributionCategoryInvestmentName);
            e.printStackTrace();
        }
        try {//Verifying "Company" header
            assertTestCase.assertEquals("Benchmark Companies", portfolioDistributionCategoryCompanyName,
                    "Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            System.out.println("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution names UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryCompanyNameUI + " : " + portfolioDistributionCategoryCompanyName);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 1 value :0%
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategoryUI1, portfolioDistributionCategory1,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategoryUI1 + " : " + portfolioDistributionCategory1);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 2 value :65.46%
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory1InvestmentUI, portfolioDistributionCategory1Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1InvestmentUI + " : " + portfolioDistributionCategory1Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 1, Cell 3 value :260
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory1CompanyUI, portfolioDistributionCategory1Company,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory1CompanyUI + " : " + portfolioDistributionCategory1Company);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 2, Cell 1 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2UI, portfolioDistributionCategory2,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2UI + " : " + portfolioDistributionCategory2);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 2, Cell 2 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2InvestmentUI, portfolioDistributionCategory2Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2InvestmentUI + " : " + portfolioDistributionCategory2Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 2, Cell 3 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory2CompanyUI, portfolioDistributionCategory2Company,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory2CompanyUI + " : " + portfolioDistributionCategory2Company);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 3, Cell 1 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3UI, portfolioDistributionCategory3,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3UI + " : " + portfolioDistributionCategory3);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 3, Cell 2 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3InvestmentUI, portfolioDistributionCategory3Investment,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3InvestmentUI + " : " + portfolioDistributionCategory3Investment);
            e.printStackTrace();
        }
        try {//Verifying Score Range Row 3, Cell 3 value :
            assertTestCase.assertEquals(benchmarkPortfolioDistributionCategory3CompanyUI, portfolioDistributionCategory3Company,
                    "Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            System.out.println("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
        } catch (AssertionError e) {
            test.fail("Portfolio Distribution values UI vs Excel field verified " + benchmarkPortfolioDistributionCategory3CompanyUI + " : " + portfolioDistributionCategory3Company);
            e.printStackTrace();
        }


    }

}
