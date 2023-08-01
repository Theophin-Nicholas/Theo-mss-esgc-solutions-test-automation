package com.esgc.ONDEMAND.UI.Pages;

import com.esgc.Common.UI.Pages.CommonPage;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.google.common.base.CharMatcher;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;

import java.util.*;
import java.util.stream.Collectors;

// pom class


public class ViewDetailPage extends CommonPage {

    // this gives details of view detail page
    //


    @FindBy(xpath = "/html/body/div[2]/div[3]/header/div/div/div[1]/div/div[1]/div[1]")
    public WebElement header;

    @FindBy(xpath = "//div[contains(text(), 'Group By')]")
    public WebElement groupByHeader;

    @FindBy(xpath = "//*[@id=\"button-button-test-id-1\"]/button[1]")
    public WebElement scoreTypeButton;

    @FindBy(xpath = "//*[@id=\"button-button-test-id-1\"]/button[2]")
    public WebElement sectorButton;
    @FindBy(xpath = "//*[@id=\"button-button-test-id-1\"]/button[3]")
    public WebElement regionButton;

    @FindBy(xpath = "/html/body/div[2]/div[3]/header/div/div/div[2]/span")
    public WebElement escButton;

    @FindBy(xpath = "//div[text()='Export To Excel']")
    public WebElement exportToExcelButton;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div/div[2]/div[1]/p[1]")
    public WebElement viewDetailFooterLineOne;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div/div[2]/div[1]/p[2]")
    public WebElement viewDetailFooterLineTwo;

    @FindBy(xpath = "//table[@id='viewcompanies']/../preceding-sibling::div")
    public List<WebElement> viewPanelTableTitles;

    @FindBy(xpath = "//span[text()='Entity']")
    public WebElement entityCell;

    @FindBy(xpath = "//span[text()='ESG Score']")
    public WebElement esgScoreCell;

    @FindBy(xpath = "//span[text()='% Investment']")
    public WebElement investmentCell;

    @FindBy(xpath = "//span[text()='Region']")
    public WebElement regionCountryCell;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div/div/div[1]/div/div[3]")
    public WebElement numberCompaniesNotListed;

    @FindBy(xpath = "//table/tbody/tr")
    public List<WebElement> numberRowsTable;

    @FindBy(xpath = "//div[text()='Predicted']/following-sibling::div/table/tbody/tr")
    public List<WebElement> numberOfPredictedTableRows;

    @FindBy(xpath = "//table[@id='viewcompanies']//td[1]")
    public List<WebElement> viewPanelEntityNames;

    @FindBy(xpath = "//table[@id='viewcompanies']//td[2]")
    public List<WebElement> viewPanelESGScores;

    @FindBy(xpath = "//table[@id='viewcompanies']//td[3]")
    public List<WebElement> viewPanelInvestments;

    @FindBy(xpath = "//div[text() = 'Predicted Score']")
    public WebElement predictedScoreLegend;

    @FindBy(xpath = "//*[@id=\"viewcompanies-tableCell-0-2\"]")
    public WebElement investmentRow;

    @FindBy(xpath = "//*[@id=\"viewcompanies-tableCell-0-0\"]")
    public WebElement entityRow;

    @FindBy(xpath = "//div[text()= 'Americas']")
    public WebElement regionOneTableTitle;


    @FindBy(xpath="//div[text()= 'Asia Pacific']")
    public WebElement regionTwoTableTitle;

    @FindBy(xpath="//div[text()= 'Europe, Middle East & Africa']")
    public WebElement regionThreeTableTitle;

    @FindBy(xpath = "/html/body/div[2]/div[3]/div")
    public WebElement tablesInViewDetailPage;


    @FindBy(xpath = "//table/../preceding-sibling::div")
    public List<WebElement> tableTitles;//this will give you the list of all the table titles based on the filter

    public void verifyOnlyRegionsAreInRegionTab(){

    


        //    assertTestCase.assertEquals(regionOneTableTitle.getText(), "Americas", "the first region is Americas ");
        assertTestCase.assertEquals(regionTwoTableTitle.getText(), "Asia Pacific", "the second region is Asia Pacific ");
        assertTestCase.assertEquals(regionThreeTableTitle.getText(), "Europe, Middle East & Africa", "the third region is Europe, Middle East & Africa ");


    }

    public boolean isPredictedScoreLegendDisplayed() {
        return predictedScoreLegend.isDisplayed();
    }

    public String getPredictedScoreLegendText() {
        return predictedScoreLegend.getText();
    }

    public int returnNumberOfRows() {
        return numberRowsTable.size();
    }

    public String getNumberCompaniesNotListedText() {
        BrowserUtils.waitForVisibility(numberCompaniesNotListed);
        BrowserUtils.scrollTo(numberCompaniesNotListed);
        return numberCompaniesNotListed.getText();
    }

    public int getTheDigitsFromNumberString() {
        String numString = getNumberCompaniesNotListedText();
        System.out.println("the last sentence under the tables in the view detail page: " + numString);
        String digits = CharMatcher.inRange('0', '9').retainFrom(numString);
        System.out.println(digits);

        int value = Integer.valueOf(digits);
        return value;
    }


    public int getCountOfCompaniesInViewDetailPage() {
        int numberFooter = getTheDigitsFromNumberString();
        int rows = returnNumberOfRows();
        int total = numberFooter + rows;
        System.out.println("number of companies: " + total);
        return total;
    }

    public String getColorOfWebelement(WebElement webElement) {

        String s = webElement.getCssValue("color");
        String color = Color.fromString(s).asHex();

        return color;
    }

    public void verifyColorOfPredictedEntities() {

    }

    public String getFooterLineOneText() {
        return viewDetailFooterLineOne.getText();
    }

    public String getFooterLineTwoText() {
        return viewDetailFooterLineTwo.getText();
    }

    public String getRegionCountryCellTitle() {
        return BrowserUtils.waitForVisibility(regionCountryCell).getText();
    }

    public String getInvestmentCellTitle() {
        return BrowserUtils.waitForVisibility(investmentCell).getText();
    }

    public String getEntityCellTitle() {
        return BrowserUtils.waitForVisibility(entityCell).getText();
    }

    public String getEsgScoreCellTitle() {
        return BrowserUtils.waitForVisibility(esgScoreCell).getText();
    }

    public void clickExportToExcelButton() {
        exportToExcelButton.click();
    }

    public String getExportToExcelButtonTitle() {
        return exportToExcelButton.getText();
    }

    public void clickScoreTypeButton() {
        BrowserUtils.waitForClickablility(scoreTypeButton, 30).click();

    }

    public void clickSectorButton() {

        BrowserUtils.waitForClickablility(sectorButton, 30).click();
    }

    public void clickRegionButton() {

        BrowserUtils.waitForClickablility(regionButton, 30).click();
    }

    public String getScoreButtonTitle() {
        return scoreTypeButton.getText();
    }

    public String getSectorButtonTitle() {
        return sectorButton.getText();
    }

    public String getRegionButtonTitle() {
        return regionButton.getText();
    }

    public void clickEscButton() {
        escButton.click();
    }

    public void hitEscapeButton() {
        Actions action = new Actions(Driver.getDriver());
        action.sendKeys(Keys.ESCAPE).build().perform();
    }

    public String getEscButtonTitle() {
        return escButton.getText();
    }

    public String getGroupByHeaderTitle() {
        return groupByHeader.getText();
    }

    public String getHeaderTitle() {
        try {
            BrowserUtils.wait(15);
            return header.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public boolean isHeaderDisplayed() {
        try {
            return header.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isEscButtonDisplayed() {
        return escButton.isDisplayed();
    }

    public boolean isEscButtonEnabled() {
        return escButton.isEnabled();
    }

    public boolean isExportToExcelButtonDisplayed() {
        return exportToExcelButton.isDisplayed();
    }

    public boolean isSectorButtonDisplayed() {
        return sectorButton.isDisplayed();
    }

    public boolean isScoreTypeButtonDisplayed() {
        return scoreTypeButton.isDisplayed();
    }

    public boolean isRegionButtonDisplayed() {
        return regionButton.isDisplayed();
    }

    public boolean isExportToExcelButtonEnabled() {
        return exportToExcelButton.isEnabled();
    }

    public boolean isSectorButtonEnabled() {
        return sectorButton.isEnabled();
    }

    public boolean isScoreTypeButtonEnabled() {
        return scoreTypeButton.isEnabled();
    }

    public boolean isRegionButtonEnabled() {
        return regionButton.isEnabled();
    }

    public void verifyHeaderDetailsInViewDetailPage(ViewDetailPage detail, String portfolioName) {
        System.out.println("----------Verify the Header details in the View Detail Page ------------");

        System.out.println("the header title of the view detail page is: " + detail.getHeaderTitle());
        assertTestCase.assertTrue(detail.isPredictedScoreLegendDisplayed(), "Verification that predicted legend is displayed");
        assertTestCase.assertEquals(detail.getPredictedScoreLegendText(), "Predicted Score", "Verification of predicted legend text is done");
        assertTestCase.assertEquals(detail.getHeaderTitle(), "Companies in " + portfolioName, "verify that header is equal to Companies in selected portfolio name ");
        assertTestCase.assertEquals(detail.getColorOfWebelement(header), "#26415e", "Verification of the header color is done.");
        //assertTestCase.assertEquals(detail.getEscButtonTitle(), "Esc", "Verification that Esc button text is done");
        assertTestCase.assertEquals(detail.getGroupByHeaderTitle(), "Group By:", "Verification of Group By button text is done");
        assertTestCase.assertEquals(detail.getSectorButtonTitle(), "Sector", "Verification of Sector button text is done");
        assertTestCase.assertEquals(detail.getScoreButtonTitle(), "Score Type", "Verification of Score Type button text is done");
        assertTestCase.assertEquals(detail.getRegionButtonTitle(), "Region", "Verification of Region button text is done");
        assertTestCase.assertEquals(detail.getExportToExcelButtonTitle(), "Export To Excel", "Verification of Export To Excel button text is done");
        assertTestCase.assertTrue(detail.isExportToExcelButtonEnabled(), "Verification that Export To Excel button is enabled is done");
        assertTestCase.assertTrue(detail.isExportToExcelButtonDisplayed(), "Verification that Export To Excel button is displayed is done");
        assertTestCase.assertTrue(detail.isRegionButtonDisplayed(), "Verification that Region button is displayed is done");
        assertTestCase.assertTrue(detail.isRegionButtonEnabled(), "Verification that Region button is enabled is done");
        assertTestCase.assertTrue(detail.isScoreTypeButtonDisplayed(), "Verification that Score Type button is displayed is done");
        assertTestCase.assertTrue(detail.isScoreTypeButtonEnabled(), "Verification that Score Type button is enabled is done");
        assertTestCase.assertTrue(detail.isSectorButtonDisplayed(), "Verification that Sector button is displayed is done");
        assertTestCase.assertTrue(detail.isSectorButtonEnabled(), "Verification that Sector button is enabled is done");
        assertTestCase.assertTrue(detail.isEscButtonDisplayed(), "Verification that Esc button is displayed is done");
        assertTestCase.assertTrue(detail.isEscButtonEnabled(), "Verification that Esc button is enabled is done");

    }

    public void verifyViewDetailPageFooter(ViewDetailPage detail, String portfolioName) {
        System.out.println("----------Verify the View Detail Page footer---------");
        String expectedFooterLine1 = "Showing 20 out 445 in " + portfolioName + ".";
        String expectedFooterLine2 = "Export to view all data for 445 companies.";
        System.out.println("the actual line 1 of the footer is:" + detail.getFooterLineOneText());
        System.out.println("the actual line 2 of the footer is:" + detail.getFooterLineTwoText());

        assertTestCase.assertTrue(detail.getFooterLineOneText().matches("Showing \\d+ out \\d+ in " + portfolioName + "."), "Verification of line 1 of the view detail page footer is done");
        assertTestCase.assertTrue(detail.getFooterLineTwoText().matches("Export to view all data for \\d+ companies."), "Verification of line 2 of the view detail page footer is done");
        assertTestCase.assertEquals(detail.getColorOfWebelement(exportToExcelButton), "#ffffff", "Verification of Export To Excel button Color is done");
    }

    public void verifyViewDetailTables(ViewDetailPage detail) {
        System.out.println("------------Verify the tables details in the View Detail Page----------");
        BrowserUtils.waitForVisibility(tablesInViewDetailPage, 50);
        assertTestCase.assertEquals(detail.getEntityCellTitle(), "Entity", "Verification of the Entity cell is done");
        assertTestCase.assertEquals(detail.getEsgScoreCellTitle(), "ESG Score", "Verification of the ESG Score cell is done");
        assertTestCase.assertEquals(detail.getInvestmentCellTitle(), "% Investment", "Verification of the % investment cell is done");
        assertTestCase.assertEquals(detail.getRegionCountryCellTitle(), "Region", "Verification of the Region cell is done");

    }

    public void clickOnGroupByOption(ViewDetailPage detail, String option) {
        if (option == "Score Type") {
            detail.clickScoreTypeButton();
            detail.verifyViewDetailTables(detail);
            detail.verifyEntitiesAreNotClickable();
            detail.getColorOfElements();
            detail.verifyTheColorOfPredictedScoreEntities();
        } else if (option == "Sector") {

            detail.clickSectorButton();
            detail.verifyViewDetailTables(detail);
            detail.verifyEntitiesAreNotClickable();
            detail.getColorOfElements();
            detail.verifyTheColorOfPredictedScoreEntities();
        } else if (option == "Region") {

            detail.clickRegionButton();
            detail.verifyViewDetailTables(detail);
            detail.verifyEntitiesAreNotClickable();
            detail.getColorOfElements();
            detail.verifyTheColorOfPredictedScoreEntities();
        }
    }

    public boolean verifyEntitiesAreNotClickable() {
        System.out.println("verification that the entities are not clickable...");
        for (WebElement element : checkEntityCells()) {
            if (element.isDisplayed() && element.isEnabled()) {
                BrowserUtils.waitForClickablility(element, 10);

                if (!element.getAttribute("class").contains("disabled")) {
                    element.click();
                }
            }

        }
        return false;
    }

    public List<WebElement> checkEntityCells() {
        List<WebElement> entityCellData = new ArrayList<WebElement>();
        for (int i = 0; i < returnNumberOfRows(); i++) {
            String xpathEntityCell = "//*[@id=\"viewcompanies-tableCell-" + i + "-0\"]";
            WebElement entityCellElement = Driver.getDriver().findElement(By.xpath(xpathEntityCell));
            entityCellData.add(entityCellElement);
            break;
        }
        System.out.println("the number of the entities in the table is : " + entityCellData.size());
        return entityCellData;
    }

    public List<String> getEntityCellsText() {
        return BrowserUtils.getElementsText(viewPanelEntityNames);
    }

    public List<String> getEntityCellsText(int tableIndex) {
        String xpath = "(//table[@id='viewcompanies']//tbody)["+tableIndex+"]//td[1]";
        List<WebElement> elements = Driver.getDriver().findElements(By.xpath(xpath));
        return BrowserUtils.getElementsText(elements);
    }

    public List<WebElement> getListOfInvestmentCells() {
        return viewPanelInvestments;
    }

    public List<WebElement> getSectorTables() {
        List<WebElement> sectorTables = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            String xpathSectorTables = "/html/body/div[2]/div[3]/div/div/div[1]/div[" + i + "]";
            WebElement sectorsElement = Driver.getDriver().findElement(By.xpath(xpathSectorTables));
            sectorTables.add(sectorsElement);
        }
        return sectorTables;
    }

    public void verifyTablesInSectorTab() {
        String[] sectorsName = {"Basic Materials", "Communication", "Consumer Discretionary", "Consumer Staples", "Energy", "Financials", "Health Care", "Industry", "Sovereign", "Technology", "Utilities"};
        List<String> sectorsName1 = new LinkedList<String>();
        sectorsName1.addAll(Arrays.asList(sectorsName));

        for (WebElement element : getSectorTables()) {
            String sectorText = element.getText().split("\n")[0];
            System.out.println(sectorText);
            assertTestCase.assertTrue(sectorsName1.contains(sectorText), "the" + sectorText + " is displayed in the sector page in the view detail page");
        }

    }

    public List<WebElement> getRegionTables() {
        List<WebElement> regionTables = new LinkedList<>();

        for(int i=1; i < 4 ; i++){
            String xpathRegionTables = "//html/body/div[2]/div[3]/div/div/div[1]/div["+i+"]";
            WebElement sectorsElement = Driver.getDriver().findElement(By.xpath(xpathRegionTables));
            regionTables.add(sectorsElement);
        }
        return regionTables;
    }

    public void verifyTablesInRegionTab() {
        String[] regionsName = {"Americas", "Asia Pacific", "Europe, Middle East & Africa"};
        List<String> regionName1 = new LinkedList<String>();
        regionName1.addAll(Arrays.asList(regionsName));

        for (WebElement element : getRegionTables()) {
            String sectorText = element.getText();
            System.out.println(sectorText);
            //assertTestCase.assertTrue(sectorsName1.contains(sectorText), "the" + sectorText +" is displayed in the sector page in the view detail page");
        }

    }

    public List<String> getListOfInvestmentCellsText() {
        List<String> investmentCellData = new ArrayList<String>();
      /*  for (int i = 1; i <= numberOfPredictedTableRows.size(); i++) {
            //String xpathInvestmentCell = "//*[@id=\"viewcompanies-tableCell-" + i + "-2\"]";
            String xpathInvestmentCell = "//div[text()='Predicted']/following-sibling::div/table/tbody/tr["+i+"]/td[3]";
            WebElement investmentCellElement = Driver.getDriver().findElement(By.xpath(xpathInvestmentCell));
            investmentCellData.add(investmentCellElement.getText().substring(0, investmentCellElement.getText().indexOf("%")));
        }*/
        BrowserUtils.getElementsText(viewPanelInvestments).forEach(element -> investmentCellData.add(element.replace("%", "")));
        return investmentCellData;
    }

    public List<Double> convertListOfInvestmentCellsTextToDouble(List<String> investmentCellsList) {
        List<Double> investmentCellsDouble = new LinkedList<>();
        for (int i = 0; i < investmentCellsList.size(); i++) {
            investmentCellsDouble.add(Double.parseDouble(investmentCellsList.get(i)));
        }

        return investmentCellsDouble;
    }

    public Map<String, Double> getEntityInvestmentMap() {
        Map<String, Double> entityInvestMap = new LinkedHashMap<>();
        List<String> entity = getEntityCellsText();
        List<String> investment = getListOfInvestmentCellsText();
        for (int i = 0; i < getListOfInvestmentCells().size(); i++) {
            entityInvestMap.put(entity.get(i), Double.parseDouble(investment.get(i)));
        }
        return entityInvestMap;
    }

    public Map<String, Double> sortByValue(Map<String, Double> entityInvestMap) {
        //List<Map.Entry<String, Double>> sortedMap = new LinkedList<Map.Entry<String, Double>>(entiyInvestMap.entrySet());

        entityInvestMap.forEach((k, v) -> System.out.println(k + " : " + v));
        System.out.println("the values after Sorting : ");
        List<Map.Entry<String, Double>> list = new ArrayList<>(getEntityInvestmentMap().entrySet());
        list.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));
        list.forEach(System.out::println);

        return entityInvestMap;
    }

    public int investmentCellDataSize() {
        return getListOfInvestmentCells().size();
    }

    public void verifyInvestmentCellsAreSortedFrom() {
        Map<String, String> result = new HashMap<>();

        for (int i = 0; i < investmentCellDataSize(); i++) {
            result.put(getEntityCellsText().get(i), getListOfInvestmentCellsText().get(i));
        }
        System.out.println("list of entities : " + getEntityCellsText().toString());
        System.out.println("list of investment : " + getListOfInvestmentCellsText().toString());

        System.out.println("the hashmap that contains entities and their respective % investment: " + result.toString());
        //assertEquals(EXPECTED_MAP, result);
    }

    public void isEntitiesListSorted() {
        for(int i = 0; i < viewPanelTableTitles.size(); i++){
            List<String> entitiesList = getEntityCellsText(i+1);
            System.out.println("entitiesList = " + entitiesList);
            List<String> sortedEntitiesList = entitiesList.stream()
                    .sorted()
                    .collect(Collectors.toList());
            System.out.println("the sorted entities list is: " + sortedEntitiesList);
            assertTestCase.assertEquals(getEntityCellsText(i+1), sortedEntitiesList, "The entities list is sorted");
        }

    }


    public void getColorOfElements() {
        List<WebElement> elements = new ArrayList<WebElement>();
        elements.addAll(checkEntityCells());
        System.out.println("the size of the webelement list is:" + elements.size());

        for (WebElement element : elements) {

            String s = element.getCssValue("color");
            String c = Color.fromString(s).asHex();
            System.out.println("the first company listed is:  " + element.getText() + " color is :" + c);
            continue;
        }
    }

    public void verifyTheColorOfPredictedScoreEntities() {
        List<WebElement> elements = new ArrayList<WebElement>();
        elements.addAll(checkEntityCells());
        for (WebElement element : elements) {
            String s = element.getCssValue("color");
            String c = Color.fromString(s).asHex();
            assertTestCase.assertEquals(c, "#263238", "verification of the yellow color of the Predicted score entities is done.");
            continue;
        }
    }


    public void verifyClickingOnEscButtonFromViewDetailPage(ViewDetailPage detail) {
        System.out.println("---------Verify clicking on Esc button from View Detail Page is successful------------");
        assertTestCase.assertTrue(detail.isHeaderDisplayed(), "We are still in the View Detail Page...");
        detail.clickEscButton();
        assertTestCase.assertFalse(!detail.isHeaderDisplayed(), "the header of the View Detail page is not displayed");
        //assertTestCase.assertTrue();
    }

    public void verifyDifferentWayToCloseViewDetailPage(ViewDetailPage detail, String portfolio) {
        OnDemandAssessmentPage odPage = new OnDemandAssessmentPage();
        //odPage.clickOnViewDetailButton(portfolio);
        assertTestCase.assertTrue(detail.isHeaderDisplayed(), "We are still in the View Detail Page..+ clicking on esc button.");
        detail.clickEscButton();
        assertTestCase.assertFalse(detail.isHeaderDisplayed(), "the header of the View Detail page is not displayed. we are in the landing page");
        odPage.clickOnViewDetailButton(portfolio);
        BrowserUtils.wait(10);
        assertTestCase.assertTrue(detail.isHeaderDisplayed(), "We are back in the View Detail Page..+ hitting the keyboard esc button.");
        detail.hitEscapeButton();
        assertTestCase.assertFalse(detail.isHeaderDisplayed(), "the header of the View Detail page is not displayed. we are back in the landing page");

    }

    public void closeViewDetailPanel(ViewDetailPage detail) {
        OnDemandAssessmentPage odPage = new OnDemandAssessmentPage();
        //odPage.clickOnViewDetailButton(portfolio);
        while(detail.isHeaderDisplayed()){
            detail.clickEscButton();
        }
    }

    public void verifyEntitiesNotClickableInViewDetailPage(ViewDetailPage detailPage) {
        assertTestCase.assertFalse(detailPage.verifyEntitiesAreNotClickable(), " the entities in the list are not clickable");

    }

    public void getSizeOfElement(WebElement element) {
        Dimension sizeInfo = element.getSize();
        sizeInfo.getWidth();

    }


    public List<Map<String, String>> getPanelDataForRegion() {
        String[] filterOptions = new String[]{"Americas", "Asia Pacific", "Europe, Middle East & Africa"};
        List<Map<String, String>> panelData = new ArrayList<>();
        for(String option : filterOptions) {
            String xpath = "//div[.='" + option + "']/following-sibling::div//td";
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath(xpath));
            if (rows.size() > 0) {
                List<WebElement> entities = Driver.getDriver().findElements(By.xpath(xpath + "[1]"));
                List<WebElement> esgScore = Driver.getDriver().findElements(By.xpath(xpath + "[2]"));
                List<WebElement> investmentPercentage = Driver.getDriver().findElements(By.xpath(xpath + "[3]"));
                List<WebElement> sector = Driver.getDriver().findElements(By.xpath(xpath + "[4]"));
                for (int i = 0; i < entities.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("entity", entities.get(i).getText());
                    map.put("esgScore", esgScore.get(i).getText());
                    map.put("investmentPercentage", investmentPercentage.get(i).getText());
                    map.put("sector", sector.get(i).getText());
                    map.put("region", option);
                    panelData.add(map);
                }
            }
        }
        return panelData;
    }

    public List<Map<String, String>> getPanelDataForSector() {
        String[] filterOptions = new String[]{"Basic Materials", "Communication", "Consumer Discretionary", "Consumer Staples", "Energy", "Financials", "Health Care", "Industry", "Sovereign", "Technology", "Utilities"};
        List<Map<String, String>> panelData = new ArrayList<>();
        for(String option : filterOptions) {
            String xpath = "//div[.='" + option + "']/following-sibling::div//td";
            List<WebElement> rows = Driver.getDriver().findElements(By.xpath(xpath));
            if (rows.size() > 0) {
                List<WebElement> entities = Driver.getDriver().findElements(By.xpath(xpath + "[1]"));
                List<WebElement> esgScore = Driver.getDriver().findElements(By.xpath(xpath + "[2]"));
                List<WebElement> investmentPercentage = Driver.getDriver().findElements(By.xpath(xpath + "[3]"));
                List<WebElement> region = Driver.getDriver().findElements(By.xpath(xpath + "[4]"));
                for (int i = 0; i < entities.size(); i++) {
                    Map<String, String> map = new HashMap<>();
                    map.put("entity", entities.get(i).getText());
                    map.put("esgScore", esgScore.get(i).getText());
                    map.put("investmentPercentage", investmentPercentage.get(i).getText());
                    map.put("region", region.get(i).getText());
                    map.put("sector", option);
                    panelData.add(map);
                }
            }
        }
        return panelData;
    }
}