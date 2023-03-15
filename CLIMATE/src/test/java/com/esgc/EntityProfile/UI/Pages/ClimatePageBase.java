package com.esgc.EntityProfile.UI.Pages;

import com.esgc.Pages.PageBase;
import com.esgc.TestBase.TestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.RemoteUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public abstract class ClimatePageBase extends PageBase {

    //============= Portfolio Selection Modal Elements


    @FindBy(id = "ExportDropdown-test-id-1")
    public WebElement exportDropdown;

    @FindBy(xpath = "//input[@type='radio']")
    public List<WebElement> portfolioRadioButtonsList;

    @FindBy(xpath = "//div[@heap_id='portfolio-selection']//div[1]/span")
    public List<WebElement> portfolioNameList;

    @FindBy(xpath = "//div[@title='']")
    public List<WebElement> portfoliosUpdatesList;

    @FindBy(xpath = "//div[starts-with(@id,'mini')]")
    public List<WebElement> portfolioCards;

    @FindBy(xpath = "//div[starts-with(@id,'mini')]//input")
    public List<WebElement> portfolioSelectionRadioButtons;

    @FindBy(xpath = "//*[@id=\"portfolio-search-test-id\"]//div[@role='dialog']")
    public WebElement SelectionModalPopup;

    @FindBy(xpath = "//header[@id=\"prop-search\"]")
    public WebElement SelectionModalPopupSearchBar;

    @FindBy(xpath = "//header[@id=\"prop-search\"]//input ")
    public WebElement SelectionModalPopupSearchBarInputField;

    @FindBy(xpath = "//*[@id='table-id']")
    public List<WebElement> tableId;

    @FindBy(xpath = "//a[contains(text(),'more companies ranked in')]")
    public List<WebElement> moreCompaniesRankedIn;

    //================ Select Options from Filters dropdown ========================================

    /**
     * User can see portfolio count in search bar in portfolio selection modal
     *
     * @return that many portfolios imported into user's account
     */
    public int getPortfolioCountForUser() {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        int count = portfolioCards.size();
        portfolioCards.get(0).click();
        return count;
    }

    /**
     * User can see portfolio names in portfolio selection modal
     *
     * @return name of all portfolios imported into user's account
     */
    public List<String> getPortfolioNames() {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<String> portfolioNameList =
                portfolioCards.stream().map(e -> e.getText().trim().substring(0, e.getText().length() - 10))
                        .filter(e -> !e.equals(""))
                        .collect(Collectors.toList());
        portfolioCards.get(0).click();
        BrowserUtils.wait(3);
        return portfolioNameList;
    }

    /**
     * Selects the first portfolio which is already selected to close modal without trigger loading data
     */
    public void selectFirstPortfolioFromPortfolioSelectionModal() {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards)).get(0).click();
    }

    /**
     * Selects random portfolio from portfolio selection modal
     */
    public void selectRandomPortfolioFromPortfolioSelectionModal() {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        List<WebElement> list =
                wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards))
                        .stream().skip(1).collect(Collectors.toList());
        Collections.shuffle(list);
        list.get(0).click();
    }

    /**
     * Selects random portfolio from portfolio selection modal
     */
    public String selectRandomPortfolioFromPortfolioSelectionModalAndGetPortfolioName() {
        clickPortfolioSelectionButton();
        List<WebElement> list =
                wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards))
                        .stream().skip(1).collect(Collectors.toList());
        Collections.shuffle(list);
        String name = new String(list.get(0).getText());
        list.get(0).click();
        return name;
    }

    /**
     * Selects portfolio by name from portfolio selection modal
     */
    public void selectPortfolioByNameFromPortfolioSelectionModal(String portfolioName) {
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<WebElement> list =
                portfolioCards.stream().filter(e -> e.getText().substring(0, e.getText().length() - 11).equals(portfolioName))
                        .collect(Collectors.toList());
        if (list.size() == 0) {
            System.out.println("Portfolio with name " + portfolioName + " is not found");
            clickAwayinBlankArea();
            return;
        }
        list.get(list.size() - 1).click();
    }

    /**
     * Selects sample portfolio (default portfolio for all users) from portfolio selection modal
     */
    public void selectSamplePortfolioFromPortfolioSelectionModal() {
        System.out.println("Selecting Sample Portfolio");
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioSelectionButton));
        if (portfolioSelectionButton.getAttribute("title").equals("Sample Portfolio")) return;
        clickPortfolioSelectionButton();
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<WebElement> list =
                portfolioCards.stream().filter(e -> e.getText().substring(0, e.getText().length() - 11).equals("Sample Portfolio"))
                        .collect(Collectors.toList());
        list.get(list.size() - 1).click();
    }

    /**
     * This method selects random option under one dropdown.
     * Dropdown name should be provided to use it
     * ex: Select random portfolio
     * dropdown = portfolio_name
     * ex2: Select random date
     * dropdown = as_of_date
     *
     * @param dropdown - Dropdown Name should be one of these:
     *                 -portfolio_name
     *                 -regions
     *                 -sectors
     *                 -as_of_date
     *                 -benchmark
     */
    public String selectRandomOptionFromFiltersDropdown(String dropdown) {
        BrowserUtils.wait(4);
        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                return selectRandomPortfolioFromPortfolioSelectionModalAndGetPortfolioName();
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sector":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "Select Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectRandomOptionFromDropdown()");
        }
        //click on menu element
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        if (elementTitle.equals("Select Benchmark")) {
            options = Driver.getDriver().findElements(By.xpath("//ul[@role='listbox']//..//li[text()]"));
        }
        //Generate random number
        Random random = new Random();

        //Should not select first element because it is already selected option
        //that's why we add 1
        int randomIndex = random.nextInt(options.size() - 1) + 1;

        //Should not select last element for portfolio name
        // because it is upload portfolio option
        //that's why subtract 1
        if (elementTitle.equals("Select Benchmark")) {
            randomIndex = randomIndex - 1;
        }

        //select random option from picked dropdown
        try {
            actions.moveToElement(options.get(randomIndex)).pause(1000).click(options.get(randomIndex)).pause(3000).build().perform();
            // return options.get(randomIndex).getText();
            return "Success";
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
        return null;

    }

    /**
     * This method selects first option in one dropdown.
     * Dropdown name should be provided to use it
     * ex: Select first option under benchmark to deactivate benchmark
     * dropdown = benchmark
     *
     * @param dropdown - Dropdown Name should be one of these - Filters Options:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     */
    public void selectFirstOptionFromFiltersDropdown(String dropdown) {

        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                selectFirstPortfolioFromPortfolioSelectionModal();
                return;
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "No Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectFirstOptionFromDropdown()");
        }
        //click on menu element
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        //select first option from picked dropdown
        try {
            actions.moveToElement(options.get(0)).pause(1000).click(options.get(0)).sendKeys(Keys.ESCAPE).build().perform();

        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    /**
     * This method selects provided option in one dropdown.
     * Dropdown name and option should be provided to use it
     * ex: select March 2021 in as_of_date dropdown
     * dropdown = as_of_date
     * option = March 2021
     *
     * @param dropdown - Dropdown Name should be one of these - Filters Options:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     * @param option   - should be a presented option under dropdown
     */
    public void selectOptionFromFiltersDropdown(String dropdown, String option) {
        System.out.println("Selecting " + option + " from " + dropdown + " dropdown");
        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                selectPortfolioByNameFromPortfolioSelectionModal(option);
                return;
            case "regions":
                element = regionsDropdown;
                elementTitle = "list-region";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "list-sector";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "list-asOfDate";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "Select Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: selectOptionFromDropdown()");
        }
        //click on menu element
        // actions.click(element).pause(4000).perform();

        //Get all options under dropdown
        // List<WebElement> options = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'" + elementTitle + "')]/../li"));
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//div[contains(@id,'" + elementTitle + "')]//span[text()]"));

        //select provided option from picked dropdown
        try {
            WebElement filterOptionToSelect = options.stream().filter(e -> e.getText().equals(option)).findFirst().get();
            actions.moveToElement(filterOptionToSelect).pause(1000).click(filterOptionToSelect).pause(3000).build().perform();
        } catch (Exception e) {
            System.out.println("Could not click option under dropdown");
            e.printStackTrace();
        }
    }

    /**
     * This method selects provided option in one dropdown.
     * Dropdown name and option should be provided to use it
     * ex: select March 2021 in as_of_date dropdown
     * dropdown = as_of_date
     * option = March 2021
     *
     * @param dropdown - Dropdown Name should be one of these - Filters:
     *                 portfolio_name
     *                 regions
     *                 sectors
     *                 as_of_date
     *                 benchmark
     */
    public List<String> getOptionsAsStringListFromFiltersDropdown(String dropdown) {
        WebElement element = null;
        String elementTitle = null;
        switch (dropdown) {
            case "portfolio_name":
                return getPortfolioNames();
            case "regions":
                element = regionsDropdown;
                elementTitle = "All Regions";
                break;
            case "sectors":
                element = sectorsDropdown;
                elementTitle = "All Sectors";
                break;
            case "as_of_date":
                element = asOfDateDropdown;
                elementTitle = "March 2021";
                break;
            case "benchmark":
                element = benchmarkDropdown;
                elementTitle = "No Benchmark";
                break;
            default:
                System.out.println("You provided wrong dropdown name for this method: getOptionsAsStringListFromDropdown()");
        }
        //click on menu element
        actions.click(element).pause(4000).perform();

        //Get all options under dropdown
        List<WebElement> options = Driver.getDriver().findElements(By.xpath("//li[contains(text(),'" + elementTitle + "')]/../li"));
        List<String> result = new ArrayList<>();

        //get options as string from picked dropdown
        options.forEach(each -> result.add(each.getText()));

        //click on page to close dropdown
        actions.click().pause(4000).perform();
        return result;
    }


    //====================  Download file methods
    /*
    method takes the download directory and the file name, which will check for the file name mentioned
    in the directory and will return 'True' if the document is
    available in the folder else 'false'. When we are sure of the file name, we can
    make use of this method to verify.
    --- Used for download template verification ESGCA-192
     */
    public String getDownloadedFileName() {

        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        Arrays.asList(dir_contents).stream().filter(e -> e.getName().startsWith("ESG portfolio import"));
        return dir_contents[0].getName(); //0 - only 1 folder
        //template name
    }

    public boolean isTemplateDownloaded() {

        String path = "";
        boolean isRemote = TestBase.isRemote;
        if (isRemote) {
            String fileName = RemoteUtils.getDownloadedDocumentFileName();
            return fileName.startsWith("ESG portfolio import");
        } else {
            File dir = new File(BrowserUtils.downloadPath());
            File[] dir_contents = dir.listFiles();
            return Arrays.asList(dir_contents).stream().filter(e -> e.getName().startsWith("ESG portfolio import")).findAny().isPresent();
        }
    }

    public void openSelectionModalPopUp() {
        clickPortfolioSelectionButton();
        BrowserUtils.wait(3);
        wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton));

    }


    public boolean checkIfSelectionModalPopupIsDisplayed() {
        try {
            return BrowserUtils.isElementVisible(SelectionModalPopupSearchBar, 3);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean validateTextOfPortfolioSearchField() {
        String placeholderText = SelectionModalPopupSearchBarInputField.getAttribute("placeholder");
        return placeholderText.startsWith("Search in ")
                && placeholderText.endsWith(" results");
    }

    public boolean checkIfSearchBarIsDisplayed() {
        try {
            return SelectionModalPopup.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void enterPortfolioNameInSearchBar(String portfolioName) {
        //wait.until(ExpectedConditions.visibilityOf(SelectionModalPopupSearchBarInputField));
        SelectionModalPopupSearchBarInputField.click();
        SelectionModalPopupSearchBarInputField.sendKeys(portfolioName);

        // SelectionModalPopupSearchBarInputField.sendKeys(portfolioName);
        BrowserUtils.wait(1);

    }

    public void selectAPortfolio(int index) {
        if (!checkIfSelectionModalPopupIsDisplayed()) clickPortfolioSelectionButton();
        portfolioCards.get(index).click();
        BrowserUtils.wait(2);

    }

    public boolean checkIfUploadPortfolioIsPrsent() {
        try {
            wait.until(ExpectedConditions.visibilityOf(uploadPortfolioButton));
            System.out.println("TEST PASSED");
            return uploadPortfolioButton.isDisplayed();
        } catch (Exception e) {
            System.out.println("FAILED");
            e.printStackTrace();
            return false;
        }

    }

    public String getaPortfolioNameandSelectthePortfolio() {
        String PortfolioName = portfolioCards.get(1).getText();
        portfolioSelectionRadioButtons.get(1).click();
        BrowserUtils.wait(2);
        return PortfolioName;
    }

    public void validatePortfolioNameUpdatedInAllLocations(String OriginalPortFolioName) {
        String newPortfolioName = "Automation Change in All Locations";
        updatePortfolioNameInPortfolioManagementDrawer(newPortfolioName);
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(successMessageForNameSaved)).isDisplayed(), "Validate Succee message is displayd after save");
        assertTestCase.assertTrue(getPortfolioDrawerHeader(newPortfolioName).isDisplayed(), "Validate that chnaged portfolio name is displayed in header ");
        closeMenuByClickingOutSide();
        navigateToPageFromMenu("Dashboard");
        assertTestCase.assertTrue(getPortfolioNames().contains(newPortfolioName + "\n"), "Validate updated Portfolio name has appreared on Dashboard page, Portfolio selection modal");

        navigateToPageFromMenu("Portfolio Analysis");
        assertTestCase.assertTrue(getPortfolioNames().contains(newPortfolioName + "\n"), "Validate updated Portfolio name has appreared on Dashboard page, Portfolio selection modal");

        clickMenu();
        portfolioSettings.click();
        selectPortfolio(newPortfolioName);
        undoPortfolioNameChange(OriginalPortFolioName);
    }


    public List<String> getPortfolioUploadDates() {
        wait.until(ExpectedConditions.visibilityOfAllElements(portfolioCards));
        List<String> portfolioNameList =
                portfolioCards.stream().map(e -> e.getText().substring(e.getText().length() - 10, e.getText().length()))
                        .collect(Collectors.toList());
        portfolioCards.get(0).click();
        return portfolioNameList;
    }

    public void goToEntity(String entityName) {
        this.entityName = entityName;
        navigateToPageFromMenu("Portfolio Analysis");
        searchIconPortfolioPage.click();
        enterPortfolioNameInSearchBar(entityName);
        String xpath = "//span[.='" + entityName + "']";
        Driver.getDriver().findElement(By.xpath(xpath)).click();


    }
}
