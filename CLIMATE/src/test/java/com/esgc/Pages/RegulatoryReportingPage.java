package com.esgc.Pages;

import com.esgc.Utilities.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class RegulatoryReportingPage extends UploadPage {
    //LOCATORS
    @FindBy(xpath = "//li[@heap_menu='Reporting Service']")
    public WebElement pageTitle;

    @FindBy(xpath = "//div[.='Select Reporting']")
    public WebElement reportingTitle;

    @FindBy(xpath = "//div[.='Reporting']")
    public WebElement reportingSubtitle;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//span[2]")
    public List<WebElement> reportingNamesList;

    @FindBy(xpath = "//div[.='Reporting']/following-sibling::div//input")
    public List<WebElement> reportingRadioButtonList;

    @FindBy(xpath = "//div[.='Select Portfolios']/following-sibling::div//span[2]")
    public List<WebElement> portfolioNamesList;

    @FindBy(xpath = "//div[.='Select Portfolios']/following-sibling::div//label//input")
    public List<WebElement> portfolioRadioButtonList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[2]/span")
    public List<WebElement> lastUploadedList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[3]/span")
    public List<WebElement> coverageList;

    @FindBy(xpath = "//div[.='Reporting for']/../following-sibling::div/div[4]")
    public List<WebElement> reportingForList;

    @FindBy(xpath = "//ul[@role='listbox']//li")
    public List<WebElement> reportingForDropdownOptionsList;

    @FindBy(id = "link-upload")
    public WebElement uploadAnotherPortfolioLink;

    @FindBy(id = "button-download-test-id-1")
    public WebElement createReportsButton;

    @FindBy(id = "interim")
    public WebElement interimReportsToggle;

    @FindBy(id = "annual")
    public WebElement annualReportsToggle;

    @FindBy(id = "latest")
    public WebElement useLatestDataToggle;

    //METHODS
    public List<String> getReportingList() {
        return BrowserUtils.getElementsText(reportingNamesList);
    }

    public List<String> getPortfolioList() {
        return BrowserUtils.getElementsText(portfolioNamesList);
    }

    //return number of selected reporting options
    public int numberOfSelectedReportingOptions() {
        return (int) reportingRadioButtonList.stream().filter(WebElement::isSelected).count();
    }

    //return seleted reporting option's name
    public String getSelectedReportingOption() {
        for (int i = 0; i < reportingRadioButtonList.size(); i++) {
            if (reportingRadioButtonList.get(i).isSelected()) {
                return reportingNamesList.get(i).getText();
            }

        }
        System.out.println("No reporting option is selected");
        return "";
    }

    //select reporting option by index enter 1 for 1st option
    public void selectReportingOptionByIndex(int index) {
        reportingRadioButtonList.get(index - 1).click();
    }

    //select reporting option by name
    public void selectReportingOptionByName(String name) {
        reportingRadioButtonList.get(getReportingList().indexOf(name)).click();
    }

    //select portfolio option by index enter 1 for 1st option
    public void selectPortfolioOptionByIndex(int index) {
        if (portfolioRadioButtonList.get(index - 1).isSelected()) {
            System.out.println("Portfolio option is already selected");
        } else if (numberOfSelectedPortfolioOptions() == 4) {
            System.out.println("You can select up to 4 portfolios. De-select one of the portfolios first");
        } else {
            System.out.println("Selecting portfolio option by index: " + index);
            portfolioRadioButtonList.get(index - 1).click();
        }
    }

    //select portfolio option by name
    public void selectPortfolioOptionByName(String name) {
        portfolioRadioButtonList.get(getPortfolioList().indexOf(name)).click();
    }

    //select all portfolio options
    public void selectAllPortfolioOptions() {
        //select all buttons if not selected
        portfolioRadioButtonList.stream().filter(button -> !button.isSelected()).forEach(WebElement::click);
    }

    //verify selcted reporting option by name
    public boolean isSelectedReportingOptionByName(String name) {
        return reportingRadioButtonList.get(getReportingList().indexOf(name)).isSelected();
    }

    public int numberOfSelectedPortfolioOptions() {
        return (int) portfolioRadioButtonList.stream().filter(WebElement::isSelected).count();
    }

    public boolean uploadPortfolio(String fileName) {
        openPortfolioUploadModal();
        try {
            BrowserUtils.waitForVisibility(uploadButton, 2);
            clickBrowseFile();
            BrowserUtils.wait(2);
            String inputFile = System.getProperty("user.dir") + ConfigurationReader.getProperty(fileName);
            RobotRunner.selectFileToUpload(inputFile);
            BrowserUtils.wait(4);
            clickUploadButton();
            while (!checkifSuccessPopUpIsDisplyed()) {
                BrowserUtils.wait(1);
            }
            closePopUp();
            pressESCKey();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void openPortfolioUploadModal() {
        //if(checkIfUploadingMaskIsDisplayed()) return;
        BrowserUtils.waitForClickablility(uploadAnotherPortfolioLink, 10).click();
    }

    public void deselectAllPortfolioOptions() {
        //select all buttons if not selected
        System.out.println("Deselecting all portfolio options");
        portfolioRadioButtonList.stream().filter(WebElement::isSelected).forEach(WebElement::click);
    }

    //select interim reports
    public void selectInterimReports() {
        if (!isInterimReportsSelected()) {
            interimReportsToggle.click();
        }
    }

    //select annual reports
    public void selectAnnualReports() {
        if (!isAnnualReportsSelected()) {
            annualReportsToggle.click();
        }
    }

    //select use latest data
    public void selectUseLatestData() {
        if (!isUseLatestDataSelected()) {
            useLatestDataToggle.click();
        }
    }

    //deselect interim reports
    public void deselectInterimReports() {
        if (isInterimReportsSelected()) {
            interimReportsToggle.click();
        }
    }

    //deselect annual reports
    public void deselectAnnualReports() {
        if (isAnnualReportsSelected()) {
            annualReportsToggle.click();
        }
    }

    //deselect use latest data
    public void deselectUseLatestData() {
        if (isUseLatestDataSelected()) {
            useLatestDataToggle.click();
        }
    }

    public boolean isInterimReportsSelected() {
        WebElement interimReportsToggle = Driver.getDriver().findElement(By.xpath("//*[@id='interim']//input"));
        System.out.println(interimReportsToggle.getAttribute("value"));
        return interimReportsToggle.isSelected();
    }

    public boolean isAnnualReportsSelected() {
        WebElement annualReportsToggle = Driver.getDriver().findElement(By.xpath("//*[@id='annual']//input"));
        return annualReportsToggle.isSelected();
    }

    public boolean isUseLatestDataSelected() {
        WebElement useLatestDataToggle = Driver.getDriver().findElement(By.xpath("//*[@id='latest']//input"));
        return useLatestDataToggle.isSelected();
    }

    public boolean isNewTabOpened() {
        BrowserUtils.wait(2);
        String currentTab = Driver.getDriver().getWindowHandle();
        for (String tab : Driver.getDriver().getWindowHandles()) {
            if (!tab.equals(currentTab)) {
                Driver.getDriver().switchTo().window(tab);
                WebElement message = Driver.getDriver().findElement(By.xpath("//h3"));
                System.out.println(message.getText());
                return message.isDisplayed();
            }
        }
        System.out.println("Number of Windows = " + Driver.getDriver().getWindowHandles().size());
        return false;
    }

    public List<String> getSelectedPortfolioOptions() {
        List<String> selectedPortfolioOptions = new ArrayList<>();
        for (int i = 0; i < portfolioRadioButtonList.size(); i++) {
            if (portfolioRadioButtonList.get(i).isSelected()) {
                selectedPortfolioOptions.add(portfolioNamesList.get(i).getText());
            }
        }
        return selectedPortfolioOptions;
    }

    public boolean verifyReportsReadyToDownload(List<String> selectedPortfolios) {
        List<WebElement> portfolioList = Driver.getDriver().findElements(By.xpath("//button/preceding-sibling::div//div"));
        for (int i = 0; i < 10; i++) {
            if (portfolioList.size() == 0) BrowserUtils.wait(1);
            else break;
        }
        for (String element : selectedPortfolios) {
            if (!isHave(element, portfolioList)) {
                return false;
            }
        }
        WebElement downloadButton = Driver.getDriver().findElement(By.xpath("//button[.='Download']"));
        return downloadButton.isDisplayed();
    }

    public boolean isHave(String element, List<WebElement> list) {
        for (WebElement webElement : list) {
            if (webElement.getText().contains(element)) {
                System.out.println("Element is present");
                return true;
            }
        }
        System.out.println("Element is not present");
        return false;
    }

    public boolean verifyIfReportsDownloaded() {
        WebElement downloadButton = Driver.getDriver().findElement(By.xpath("//button[.='Download']"));
        downloadButton.click();
        BrowserUtils.wait(10);
        File dir = new File(BrowserUtils.downloadPath());
        File[] dir_contents = dir.listFiles();
        return Arrays.asList(dir_contents).stream().filter(e -> (e.getName().startsWith("SFDR")&&e.getName().contains(DateTimeUtilities.getCurrentDate("MM_dd_yyyy")))).findAny().isPresent();
    }

    public boolean verifyPortfolio(String portfolioName) {
        return getPortfolioList().contains(portfolioName);
    }

    public boolean isPortfolioSelected(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isSelected();
    }

    public boolean isPortfolioSelectionEnabled(String portfolioName) {
        return portfolioRadioButtonList.get(getPortfolioList().indexOf(portfolioName)).isEnabled();
    }
}
