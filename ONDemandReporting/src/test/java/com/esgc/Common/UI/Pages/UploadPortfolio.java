package com.esgc.Common.UI.Pages;


import com.esgc.Pages.PageBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;

import com.esgc.Utilities.RobotRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.stream.Collectors;

public class UploadPortfolio extends PageBase {

    @FindBy(id = "upload-button-upload-test-id")
    public WebElement uploadButton;

    @FindBy(id = "upload-button-browsefile-test-id")
    public WebElement browseFileButton;

    @FindBy(id = "upload-link-test-id")
    public WebElement downloadTemplateLink;

    @FindBy(id = "upload-button-test-id-2")
    public WebElement viewMyPortfoliosButton;

    @FindBy(id = "upload-button-test-id-3")
    public WebElement addAnotherPortfolioButton;

    @FindBy(xpath = "//*[@id='upload-modal-test-id']//span/span")
    public WebElement selectedFileName;

    @FindBy(xpath = "//*[text()='Remove']")
    public WebElement removeLink;

    @FindBy(id = "upload-popover-error-upload-test-id")
    public WebElement errorPopUP;

    @FindBy(xpath = "//*[@class='MuiAlert-message']")
    public WebElement successMessagePopUP;

    @FindBy(xpath = "//*[@class='MuiAlert-message']/div[1]")
    public WebElement AlertMessage;

    @FindBy(xpath = "//*[@class='MuiAlert-message']//b")
    public List<WebElement> AlertMessageMultipleTickersLists;

    @FindBy(xpath = "//*[text()='Portfolio Upload Successfully Saved']/following-sibling::div")
    public WebElement closeButtonIcon;

    @FindBy(id = "upload-button-test-id-1")
    public WebElement saveButton;

    @FindBy(id = "upload-input-test-id-success")
    public WebElement NameInputField;

    @FindBy(id = "upload-popover-unidentified-error-test-id")
    public WebElement unknownIdentifiersPopUp;

    @FindBy(xpath = "//span[@class='MuiIconButton-label']//*[name()='svg']")
    public WebElement closeAlert;

    @FindBy(xpath = "//h2/button")
    public WebElement closeUploadModalButton;

    @FindBy(id = "upload-input-test-id-success")
    public WebElement updatePortfolioNameFromPopUpInput;

    @FindBy(xpath = "//span[text()='Uploading']/../..")
    public WebElement uploadingLoadMask;

    @FindBy(xpath = "//*[@role='dialog' and .//h2[text()='Import Portfolio']]")
    public WebElement uploadModal;

    @FindBy(tagName = "h2")
    public WebElement modalTitle;

    @FindBy(xpath = "//*[@role='dialog']//a/../div")
    public List<WebElement> modalDescription;

    @FindBy(xpath = "//a/following-sibling::span")
    public WebElement uploadAreaInModal;

    @FindBy(xpath = "//a[@id='link-upload']")
    public WebElement uploadPortfolioLinkOnDemandPage;


    public void clickBrowseFile() {
        wait.until(ExpectedConditions.elementToBeClickable(browseFileButton));
        browseFileButton.click();

    }

    public void clickUploadButton() {
        //wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
        BrowserUtils.waitForClickablility(uploadButton, 30);
        uploadButton.click();
    }

    public boolean CheckifErrorPopUpIsDisplyed() {
        try {
            return errorPopUP.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    /**
     * The method returns error message if error popup is displayed otherwise returns pop up not displayed
     *
     * @return error message if error popup is displayed or "pop up not displayed"
     */
    public String getErrorMessage() {
        try {
            System.out.println("Error Message:" + errorPopUP.getText());
            return errorPopUP.getText();
        } catch (NoSuchElementException e) {
            return "Pop up not displayed";
        }

    }


    /**
     * The method returns true if Successful upload popup is displayed otherwise returns false
     *
     * @return true if success popup is displayed or false
     */
    public boolean checkifSuccessPopUpIsDisplyed() {
        try {
            BrowserUtils.waitForVisibility(successMessagePopUP, 20);
            return successMessagePopUP.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkifSuccessPopUpDisappearedAfterACertainTime() {
        try {
            BrowserUtils.waitForInvisibility(successMessagePopUP, 60);
            return successMessagePopUP.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * The method should return portfolio name if Successful upload popup is displayed otherwise returns null
     *
     * @return portfolio name if success popup is displayed or null
     */
    public String getPlaceholderInSuccessPopUp() {
        try {
            return updatePortfolioNameFromPopUpInput.getAttribute("placeholder");
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * The method returns true if a 'X' button icon on Successful upload popup is displayed otherwise returns false
     *
     * @return true if CloseIcon 'X' is displayed or false
     */
    public boolean CheckifClosebuttonIsDisplayed() {

        try {
            return closeButtonIcon.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * The method returns true if a input field for renaming the portfolio was present on Successful upload popup otherwise returns false
     *
     * @return true if input field to rename the portfolio is displayed or false
     */
    public boolean CheckifNameInputFielIsPresent() {
        try {
            return NameInputField.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    /**
     * The method clicked on a button with the label
     *
     * @param buttonLabel
     */
    public void clickOnButtonWithLabel(String buttonLabel) {
        Driver.getDriver().findElement((By.xpath("//*[text()='" + buttonLabel + "']"))).click();
    }

    /**
     * This method automatically uploads a good portfolio and return portfolio name
     *
     * @return name of uploaded portfolio
     */
    public String uploadPortfolio(String filePath, String fromPage) {
        try {
            if (fromPage.equals("Dashboard")) {
                clickPortfolioSelectionButton();
                wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton));
                BrowserUtils.clickWithJS(uploadPortfolioButton);
                BrowserUtils.waitForVisibility(uploadButton, 2);
            } else if (fromPage.equals("OnDemand")) {
                wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioLinkOnDemandPage));
                BrowserUtils.clickWithJS(uploadPortfolioLinkOnDemandPage);
            }
            clickBrowseFile();
            BrowserUtils.wait(2);
            RobotRunner.selectFileToUpload(filePath);
            BrowserUtils.wait(4);
            clickUploadButton();
            BrowserUtils.wait(2);
        } catch (Exception e) {
            System.out.println("###Portfolio Upload Failed###");
            System.out.println("Failed");
            e.printStackTrace();
        }
        return "Portfolio Upload updated_good";
    }

    public boolean validateIfUploadPortfolioButtonIsAvailable(){
        return BrowserUtils.waitForVisibility(uploadPortfolioLinkOnDemandPage,10).isDisplayed();
    }

    /**
     * This method closes pop up after successful or unsuccessful portfolio uploads.
     */
    public void closePopUp() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(closeAlert));
            actions.click(closeAlert).pause(3000).build().perform();
            BrowserUtils.wait(2);
            System.out.println("Pop up closed");
        } catch (Exception e) {
            System.out.println("###POP UP Failed###");
            System.out.println("Failed");
            e.printStackTrace();
        }
    }

    /**
     * This method allows you to update portfolio name after successful update.
     * This function only works when successful upload pop-up appeared
     *
     * @param newName - new name of portfolio that you want to update with it
     * @return - updated portfolio name
     */
    public String updatePortfolioName(String newName) {
        wait.until(ExpectedConditions.visibilityOf(updatePortfolioNameFromPopUpInput));
        actions.moveToElement(updatePortfolioNameFromPopUpInput).pause(2000)
                .click(updatePortfolioNameFromPopUpInput).pause(1000)
                .sendKeys(newName).pause(1000).build().perform();
        actions.moveToElement(saveButton).pause(1000).click(saveButton).build().perform();
        System.out.println("Portfolio Name updated to:" + newName);
        return newName;
    }

    public boolean checkIfPortfolioUploadModalIsDisplayed() {
        try {
            return BrowserUtils.waitForVisibility(uploadModal).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfPortfolioUploadModalSubTitleIsDisplayedAsExpected() {
        try {
            return modalTitle.getText().equals("Import Portfolio");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean checkIfPortfolioUploadModalDescriptionIsDisplayedAsExpected() {
        try {
            String description = modalDescription.stream().map(WebElement::getText).collect(Collectors.joining(" "));
            return description.equals("Upload a portfolio in CSV format. Acceptable identifiers include: <ISIN, Bloomberg Ticker, ORBIS_ID>");
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean checkIfUploadButtonIsDisabled() {
        try {
            return !uploadButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean checkIfCloseUploadModalButtonIsDisabled() {
        try {
            return closeUploadModalButton.isEnabled();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void closeUploadModal() {
        closeUploadModalButton.click();
    }

    public void clickCloseButton() {
        wait.until(ExpectedConditions.visibilityOf(closeButtonIcon)).click();
    }

    public boolean isFileRemovedFromModal() {
        return !uploadAreaInModal.getText().contains("\"") && !uploadAreaInModal.getText().contains("Remove");
    }


    /*public void uploadARandomPortfolio() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton)).click();
        UploadPage uploadPage = new UploadPage();
        uploadPage.clickBrowseFile();
        RobotRunner.selectFileToUpload(PortfolioFilePaths.goodPortfolioPath());
        uploadPage.clickUploadButton();

    }*/
}