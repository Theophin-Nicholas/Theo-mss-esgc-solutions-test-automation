package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.PortfolioFilePaths;
import com.esgc.Utilities.RobotRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UploadPage extends ClimatePageBase {

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

    @FindBy(xpath = "//*[@role='dialog']")
    public WebElement uploadModal;

    @FindBy(tagName = "h2")
    public WebElement modalTitle;

    @FindBy(xpath = "//*[@role='dialog']//a/../div")
    public List<WebElement> modalDescription;

    @FindBy(xpath = "//a/following-sibling::span")
    public WebElement uploadAreaInModal;


    public void clickDownloadTemplate() {
        wait.until(ExpectedConditions.elementToBeClickable(downloadTemplateLink));
        BrowserUtils.clickWithJS(downloadTemplateLink);

    }

    public void clickBrowseFile() {
        wait.until(ExpectedConditions.elementToBeClickable(browseFileButton));
        browseFileButton.click();

    }

    public boolean isBrowseButtonPresent() {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(browseFileButton)).isDisplayed();
        } catch (Exception e) {
            System.out.println("Browse File button is not displayed");
            return false;
        }
    }

    public void clickUploadButton() {

        wait.until(ExpectedConditions.elementToBeClickable(uploadButton));
        uploadButton.click();
    }

    public String getSelectedFileName() {

        return selectedFileName.getText().split("\"")[1];
    }


    public boolean IsLinkAvailablewithText(String label) {

        return Driver.getDriver().findElements(By.linkText(label)).size() > 0;
    }

    public boolean isSaveButtonPresent() {
        try {
            return BrowserUtils.isElementVisible(saveButton, 2);
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isRemoveButtonPresent() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(removeLink)).isDisplayed();
        } catch (NoSuchElementException e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void clickRemoveButton() {
        removeLink.click();
    }


    /**
     * Checks if Uploading Mask is Displayed
     *
     * @return true - if not displayed returns false
     */

    public boolean checkIfUploadingMaskIsDisplayed() {
        try {
            System.out.println("Uploading Mask Check");
            boolean loadMaskIsDisplayed = wait.until(ExpectedConditions.visibilityOf(uploadingLoadMask)).isDisplayed();
            //wait.until(ExpectedConditions.invisibilityOf(uploadingLoadMask));
            System.out.println("Uploading Completed!");
            return loadMaskIsDisplayed;
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }


    /**
     * The method returns true if error popup is displayed otherwise returns false
     *
     * @return true if error popup is displayed or false
     */
    public boolean CheckifErrorPopUpIsDisplyed() {
        try {
            return errorPopUP.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }

    }

    /**
     * The method returns true if unknown identifier popup is displayed otherwise returns false
     *
     * @return true if unknown identifier popup is displayed or false
     */
    public boolean checkIfUnknownIdentifierPopUpDisplayed() {
        try {
            return unknownIdentifiersPopUp.isDisplayed();
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
     * The method returns unknown identifier message if unknown identifier popup is displayed
     * otherwise returns pop up not displayed
     *
     * @return unknown identifier message if unknown identifier popup is displayed or "pop up not displayed"
     */
    public String getUnknownIdentifierPopUpMessage() {
        try {
            String message = unknownIdentifiersPopUp.getText().split("\n")[0] + " " + unknownIdentifiersPopUp.getText().split("\n")[1];
            System.out.println("Error Message:" + message);
            return message;
        } catch (NoSuchElementException e) {
            return "Pop up not displayed";
        }

    }

    /**
     * The method returns unknown identifiers if unknown identifier popup is displayed
     * otherwise returns pop up not displayed
     *
     * @return unknown identifiers if unknown identifier popup is displayed or "pop up not displayed"
     */
    public List<String> getUnknownIdentifiersFromPopUpMessage() {
        try {
            List<String> identifiers = new ArrayList<>(Arrays.asList(unknownIdentifiersPopUp.getText().split("\n")));
            System.out.println(identifiers);
            identifiers.remove(0);
            System.out.println("Unknown Identifiers:");
            identifiers.forEach(System.out::println);
            return identifiers;
        } catch (NoSuchElementException e) {
            return Collections.singletonList("Pop up not displayed");
        }

    }

    /**
     * The method returns true if Successful upload popup is displayed otherwise returns false
     *
     * @return true if success popup is displayed or false
     */
    public boolean checkifSuccessPopUpIsDisplyed() {
        try {
            BrowserUtils.waitForVisibility(successMessagePopUP, 15);
            return successMessagePopUP.isDisplayed();
        } catch (NoSuchElementException e) {
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
    public String uploadGoodPortfolio() {
        try {
            clickPortfolioSelectionButton();
            wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton));
            BrowserUtils.clickWithJS(uploadPortfolioButton);
            BrowserUtils.waitForVisibility(uploadButton, 2);
            clickBrowseFile();
            BrowserUtils.wait(2);
            RobotRunner.selectFileToUpload(PortfolioFilePaths.goodPortfolioPath());
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
            return uploadModal.isDisplayed();
        } catch (NoSuchElementException e) {
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


    public void uploadARandomPortfolio() {
        wait.until(ExpectedConditions.elementToBeClickable(uploadPortfolioButton)).click();
        UploadPage uploadPage = new UploadPage();
        uploadPage.clickBrowseFile();
        RobotRunner.selectFileToUpload(PortfolioFilePaths.goodPortfolioPath());
        uploadPage.clickUploadButton();

    }

}