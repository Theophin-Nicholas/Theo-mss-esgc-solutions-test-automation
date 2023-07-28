package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Environment;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EMCAccountDetailsPage extends EMCBasePage {

    String mesgApp = Environment.MESG_APPLICATION_NAME;
    String product = "ESG On-Demand Assessment";


    @FindBy(tagName = "h4")
    public WebElement pageTitle;

    //DETAILS TAB

    @FindBy(xpath = "//button[.='Details']")
    public WebElement detailsTab;

    @FindBy(name = "name")
    public WebElement accountNameInput;

    @FindBy(name = "key")
    public WebElement accountKeyInput;

    @FindBy(tagName = "label")
    public WebElement statusLabel;

    @FindBy(xpath = "//label//input")
    public WebElement statusCheckBox;

    @FindBy(xpath = "//div[@name='subscriberType']//input")
    public WebElement subscriberInput;

    @FindBy(xpath = "//ul/li")
    public List<WebElement> subscriberTypeList;

    @FindBy(name = "contractStartDate")
    public WebElement startDateInput;

    @FindBy(name = "contractEndDate")
    public WebElement endDateInput;

    @FindBy(xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy(xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy(xpath = "//button[.='Edit']")
    public WebElement editButton;

    @FindBy(xpath = "//span[starts-with(.,'Created')]")
    public WebElement creationInfo;

    @FindBy(xpath = "//span[starts-with(.,'Modified')]")
    public WebElement modificationInfo;

    @FindBy(xpath = "//button[starts-with(.,'Back')]")
    public WebElement backToAccountsButton;

    @FindBy(name = "contractStartDate")
    public WebElement contractStartDateInput;

    @FindBy(name = "contractEndDate")
    public WebElement contractEndDateInput;

    //APPLICATIONS TAB

    @FindBy(xpath = "//button[.='Applications']")
    public WebElement applicationsTab;

    @FindBy(xpath = "//h6[.='Applications']")
    public WebElement applicationsTabTitle;

    @FindBy(xpath = "//button[.='Assign applications']")
    public WebElement assignApplicationsButton;

    @FindBy(xpath = "//h6[.='No Apps assigned to this account']")
    public WebElement noApplicationMessage;

    @FindBy(xpath = "//li/div[1]//span")
    public List<WebElement> applicationsNamesList;

    @FindBy(xpath = "//li//button")
    public List<WebElement> applicationsDeleteButtons;

    @FindBy(xpath = "//h2/../..//li//button")
    public List<WebElement> applicationsAssignButtonList;

    @FindBy(xpath = "//li/div//button")
    public List<WebElement> deleteApplicationsButtons;

    @FindBy(xpath = "//button[.='Proceed']")
    public WebElement deleteApplicationsProceedButton;

    //ASSIGN APPLICATIONS Modal

    @FindBy(tagName = "h2")
    public WebElement assignApplicationsModalTitle;

    @FindBy(tagName = "input")
    public WebElement assignApplicationsModalSearchInput;

    @FindBy(xpath = "//input/../../button")
    public WebElement assignApplicationsModalSearchButton;

    @FindBy(xpath = "//button[.='Assign']/../..//div[1]//span")
    public List<WebElement> assignApplicationsModalApplicationsList;

    @FindBy(xpath = "//button[.='Assign']")
    public List<WebElement> assignApplicationsModalAssignButtonsList;

    @FindBy(xpath = "//button[.='Done']")
    public WebElement assignApplicationModalDoneButton;

    @FindBy(xpath = "//div[@id='notistack-snackbar']")
    public WebElement applicationAddedMessage;

    //PRODUCTS TAB

    @FindBy(xpath = "//button[.='Products']")
    public WebElement productsTab;

    @FindBy(xpath = "//h6[.='No Products assigned to this account']")
    public WebElement noProductsMessage;

    @FindBy(xpath = "//h6[.='Products']")
    public WebElement productsTabTitle;

    @FindBy(xpath = "//button[.='Assign products']")
    public WebElement assignProductsButton;

    @FindBy(xpath = "//ul//p")
    public List<WebElement> currentProductsList;

    @FindBy(xpath = "//li//div[1]//span")
    public List<WebElement> currentProductFeaturesList;

    @FindBy(xpath = "//div[@aria-expanded='true']//following-sibling::div//button[last()]")
    public List<WebElement> currentProductFeaturesDeleteButtons;

    @FindBy(xpath = "//button[.='Proceed']")
    public WebElement proceedButton;

    @FindBy(xpath = "//button[.='Close']")
    public WebElement closeButton;

    @FindBy(xpath = "//input[@name='PurchasedAssessments']")
    public WebElement smePurchasedAssessmentInput;

    @FindBy(xpath = "//li//button[last()]/preceding-sibling::button")
    public WebElement esgOnDemandAssessmentEditButton;

    @FindBy(xpath = "//input[@name='UsedAssessments']")
    public WebElement smeUsedAssessmentInput;

    //after add product button clicked
    @FindBy(xpath = "//h2/../..//ul/div")
    public List<WebElement> availableProductsNamesList;

    @FindBy(xpath = "//button[.='Assign']/../../../../span")
    public List<WebElement> availableFeaturesNamesList;

    @FindBy(xpath = "//button[.='Assign']")
    public List<WebElement> availableFeaturesAssignButtonsList;

    @FindBy(xpath = "//button[.='Done']")
    public WebElement doneButton;

    @FindBy(xpath = "//p[.='PurchasedAssessments is a required field']")
    public WebElement purchasedAssessmentsRequiredMessage;

    //USERS TAB

    @FindBy(xpath = "//button//span[.='Users']")
    public WebElement usersTab;

    @FindBy(xpath = "//button[.='Add User']")
    public WebElement addUserButton;

    @FindBy(xpath = "//button[.='Add User']/../div/button")
    public WebElement userOptionsButton;

    @FindBy(xpath = "//div[@id='long-menu']//li")
    public List<WebElement> userOptionsList;

    @FindBy(xpath = "//input[@type='text']")
    public WebElement searchInput;

    @FindBy(xpath = "//th")
    public List<WebElement> tableHeaders;

    @FindBy(xpath = "//th")
    public List<WebElement> firstRow;

    @FindBy(xpath = "//th/a")
    public List<WebElement> userNamesList;

    @FindBy(xpath = "//main//h6")
    public WebElement noUsersMessage;

    @FindBy(xpath = "//td/a")
    public List<WebElement> userEmailsList;

    @FindBy(xpath = "//td/div/span")
    public List<WebElement> providerList;

    @FindBy(xpath = "//td//input")
    public List<WebElement> userCheckboxList;

    @FindBy(xpath = "//h2")
    public WebElement addUserPanelTitle;

    @FindBy(name = "firstName")
    public WebElement firstNameInput;

    @FindBy(name = "lastName")
    public WebElement lastNameInput;

    @FindBy(name = "email")
    public WebElement emailInput;

    @FindBy(name = "userName")
    public WebElement userNameInput;

    @FindBy(name = "activate")
    public WebElement sendActivationEmailCheckbox;

    @FindBy(xpath = "//p[.='First Name is a required field']")
    public WebElement firstNameInputWarning;

    @FindBy(xpath = "//p[.='Last Name is a required field']")
    public WebElement lastNameInputWarning;
    @FindBy(xpath = "//p[.='User Name is a required field']")
    public WebElement userNameInputWarning;
    @FindBy(xpath = "//p[.='Must be a valid email Address']")
    public WebElement validEmailWarning;
    @FindBy(xpath = "//p[.='Email is a required field']")
    public WebElement emailInputWarning;

    @FindBy(xpath = "//button[.='Back to Users']")
    public WebElement backToUsersButton;

    @FindBy(xpath = "//button[@title='Delete']")
    public WebElement deleteButton;

    @FindBy(xpath = "//h2[.='Delete users']")
    public WebElement deleteConfirmationPopup;

    @FindBy(xpath = "//button[.='Delete']")
    public WebElement popupDeleteButton;

    @FindBy(xpath = "//button[.='Cancel']")
    public WebElement popupCancelButton;

    @FindBy(xpath = "//div[@aria-haspopup='listbox']")
    public WebElement rowsPerPageIndicator;

    @FindBy(xpath = "//div[.='10']")
    public WebElement numberOfUsers10;

    @FindBy(xpath = "//li[.='50']")
    public WebElement numberOfUsers50;

    //create user under accounts with required fields
    @FindBy(xpath = "//input[@value='ma']")
    public WebElement maCheckbox;

    @FindBy(xpath = "//input[@value='mss']")
    public WebElement mssCheckbox;

    //create user under accounts with required fields
    public boolean createUser(String firstName, String lastName, String userName, boolean sendActivationEmail, boolean MAUser) {
        BrowserUtils.waitAndClick(addUserButton, 5);
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        //emailInput.sendKeys(userName);
        userNameInput.sendKeys(userName, Keys.TAB);
        if (MAUser) {
            System.out.println("Selecting MA Option");
            maCheckbox.click();
        } else {
            System.out.println("Selecting MSS Option");
            if (!mssCheckbox.isSelected()) mssCheckbox.click();
            //assertTestCase.assertTrue(BrowserUtils.scrollTo(sendActivationEmailCheckbox).isDisplayed(), "Send Activation Email checkbox is displayed");
            if (!sendActivationEmail) sendActivationEmailCheckbox.click();
        }
        BrowserUtils.waitForClickablility(saveButton, 5).click();
        System.out.println("save button clicked");
        BrowserUtils.waitForClickablility(backToUsersButton, 5).click();
        return true;
    }

    public boolean createUser() {
        Faker faker = new Faker();
        addUserButton.click();
        String firstName = "QATESTUSER";
        String lastName = faker.name().lastName();
        String userName = faker.internet().emailAddress();
        return createUser(firstName, lastName, userName, true);
    }

    public boolean createUser(String firstName, String lastName, String userName, boolean activationEmail) {
        return createUser(firstName, lastName, userName, activationEmail, false);
    }

    public boolean createUser(boolean sendActivationEmail) {
        Faker faker = new Faker();
        BrowserUtils.waitForClickablility(addUserButton, 5).click();
        String firstName = "QATESTUSER";
        String lastName = faker.name().lastName();
        String userName = faker.internet().emailAddress();
        return createUser(firstName, lastName, userName, sendActivationEmail);
    }

    public void searchUser(String userName) {
        searchUser(userName, true);
    }

    public void searchUser(String userName, boolean gotoUser) {
        wait(searchInput, 20);
        clear(searchInput);
        System.out.println("Searching for the user : " + userName);
        searchInput.sendKeys(userName);
        if (userNamesList.size() == 0) {
            System.out.println("user not found");
            return;
        }
        if (gotoUser) userNamesList.get(0).click();
    }

    public boolean isSortedByName() {
        List<String> names = userNamesList.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
        System.out.println("names are: " + names);
        return names.stream().sorted().collect(Collectors.toList()).equals(names);
    }

    public boolean assignApplication(String applicationName) {
        clickOnApplicationsTab();
        System.out.println("assigning application: " + applicationName);
        BrowserUtils.waitForClickablility(assignApplicationsButton, 5).click();
        BrowserUtils.waitForClickablility(assignApplicationsModalSearchInput, 5).sendKeys(applicationName);
        BrowserUtils.wait(5);
        if (applicationsAssignButtonList.isEmpty()) {
            System.out.println("application not found");
            doneButton.click();
            return false;
        } else {
            BrowserUtils.waitForClickablility(applicationsAssignButtonList.get(0), 15).click();
            System.out.println(applicationName + " application assigned");
            doneButton.click();
            BrowserUtils.waitForVisibility(notification, 5);
            assertTestCase.assertTrue(notification.isDisplayed(), "Notification is displayed");
            clickOnDetailsTab();
            BrowserUtils.wait(2);
            clickOnApplicationsTab();
            BrowserUtils.wait(5);
            return true;
        }
    }

    public boolean verifyUser(String name) {
        return verifyUser(name, true);
    }

    public boolean verifyUser(String name, boolean expand) {
        wait(userNamesList, 200);
        if (expand) expandList();
        for (WebElement user : userNamesList) {
            if (user.getText().equals(name)) {
                System.out.println("user " + name + " found");
                return true;
            }
        }
        System.out.println("user " + name + " not found");
        return false;
    }

    public void expandList() {
        wait(rowsPerPageIndicator, 200);
        if (!rowsPerPageIndicator.getText().equals("50")) {
            BrowserUtils.waitForClickablility(rowsPerPageIndicator, 5).click();
            BrowserUtils.waitForClickablility(numberOfUsers50, 5).click();
        }
        BrowserUtils.scrollTo(pageTitle);
    }

    public boolean deleteUser(String name) {
        BrowserUtils.scrollTo(usersTab);
        BrowserUtils.waitForClickablility(usersTab, 5).click();
        for (WebElement user : userNamesList) {
            BrowserUtils.scrollTo(user);
            if (user.getText().equals(name)) {
                System.out.println("user " + name + " found");
                int index = userNamesList.indexOf(user);
                System.out.println("index = " + index);
                BrowserUtils.scrollTo(userCheckboxList.get(index));
                userCheckboxList.get(index).click();
                BrowserUtils.scrollTo(deleteButton);
                BrowserUtils.waitForClickablility(deleteButton, 5).click();
                BrowserUtils.waitForClickablility(popupDeleteButton, 5).click();
                System.out.println("user " + name + " deleted");
                clickOnDetailsTab();
                clickOnUsersTab();
                return true;
            }
        }
        System.out.println(name + " user not found");
        return false;
    }

    public boolean verifyApplication(String applicationName) {
        //System.out.println("Number of current applications = " + applicationsNamesList.size());
        wait(applicationsNamesList, 10);
        for (WebElement application : applicationsNamesList) {
            if (application.getText().equals(applicationName)) {
                System.out.println(applicationName + " application found");
                return true;
            }
        }
        System.out.println(applicationName + " application not found");
        return false;
    }

    public boolean deleteApplication(String applicationName) {
        System.out.println("Deleting Application: " + applicationName);
        BrowserUtils.wait(2);
        while (verifyApplication(applicationName)) {

            for (int i = 0; i < applicationsNamesList.size(); i++) {
                if (applicationsNamesList.get(i).getText().equals(applicationName)) {
                    System.out.println("Application Found");
                    applicationsDeleteButtons.get(i).click();
                    BrowserUtils.waitForClickablility(deleteApplicationsProceedButton, 5).click();
                    clickOnDetailsTab();
                    clickOnApplicationsTab();
                    break;
                }
            }
            System.out.println("refreshing list");
        }
        return !verifyApplication(applicationName);
    }

    public void clickOnAddUserButton() {
        BrowserUtils.scrollTo(addUserButton);
        BrowserUtils.waitForClickablility(addUserButton, 15).click();
    }

    public void clickOnUsersTab() {
        System.out.println("clicking on users tab");
        BrowserUtils.waitAndClick(usersTab, 20);
    }

    public void clickOnDetailsTab() {
        System.out.println("clicking on details tab");
        BrowserUtils.waitAndClick(detailsTab, 20);
    }

    public void clickOnApplicationsTab() {
        System.out.println("clicking on applications tab");
        BrowserUtils.waitAndClick(applicationsTab, 200);
    }

    public void clickOnProductsTab() {
        System.out.println("clicking on products tab");
        BrowserUtils.waitAndClick(productsTab, 20);
    }

    public void deleteRandomUsers() {
        expandList();
        List<String> keepUsers = new ArrayList<>(Arrays.asList("Efrain June2022", "QA Test", "Ferhat Test", "MA Test Account", "Active User"));
        int waitTime = userNamesList.size();
        if (userNamesList.size() > keepUsers.size()) {
            for (int i = 0; i < userNamesList.size(); i++) {
                if (!keepUsers.contains(userNamesList.get(i).getText())) {
                    BrowserUtils.scrollTo(userCheckboxList.get(i));
                    userCheckboxList.get(i).click();
                }
            }
            BrowserUtils.scrollTo(deleteButton);
            deleteButton.click();
            popupDeleteButton.click();
            System.out.println("All random users deleted");
        }
        BrowserUtils.wait(waitTime);
        BrowserUtils.scrollTo(pageTitle);
    }

    public boolean addTestApplications(String applicationName) {
        try {
            if (verifyApplication(applicationName)) {
                System.out.println(applicationName + " application already exists");
                deleteApplication(applicationName);
                System.out.println(applicationName + " application deleted");
            }
            assignApplication(applicationName);
            BrowserUtils.waitForVisibility(applicationAddedMessage, 5);
            System.out.println(applicationName + " application added");

            BrowserUtils.waitForInvisibility(applicationAddedMessage, 15);
            return true;
        } catch (Exception e) {
            System.out.println("error in adding applications");
            System.out.println("e = " + e);
            return false;
        }
    }

    public void printCurrentApplications() {
        System.out.println("Current applications are: ");
        for (WebElement application : applicationsNamesList) {
            System.out.println(application.getText());
        }
        System.out.println("-----------------------------------------------------");
    }

    public void printCurrentUsers() {
        System.out.println("Current Users are: ");
        for (WebElement user : userNamesList) {
            System.out.println(user.getText());
        }
        System.out.println("-----------------------------------------------------");
    }

    public void clickOnBackToAccountsButton() {
        System.out.println("clicking on back to accounts button");
        BrowserUtils.scrollTo(backToAccountsButton);
        BrowserUtils.waitForClickablility(backToAccountsButton, 5).click();
    }

    public void addAllProducts(String applicationName) {
        System.out.println("Adding all features of " + applicationName + " application to account");
        BrowserUtils.waitForClickablility(assignProductsButton, 5).click();
        System.out.println("Number of available products: " + availableProductsNamesList.size());
        for (WebElement product : availableProductsNamesList) {
            System.out.println("Product Name = " + product.getText());
            BrowserUtils.scrollTo(product);
            if (product.getText().equals(applicationName)) {
                System.out.println(applicationName + " product found");
                product.click();
                System.out.println("Number of Available Features = " + availableFeaturesNamesList.size());
                if (availableFeaturesNamesList.size() > 0) {
                    System.out.println("Adding " + availableFeaturesNamesList.get(0).getText() + " feature product");
                    BrowserUtils.waitForClickablility(availableFeaturesAssignButtonsList.get(0), 5).click();
                } else {
                    System.out.println("No features found");
                }
                break;
            }
        }
        BrowserUtils.waitForClickablility(doneButton, 5).click();
        clickOnDetailsTab();
        BrowserUtils.wait(2);
        clickOnProductsTab();
        BrowserUtils.wait(5);
    }

    public boolean verifyProduct(String applicationName) {
        BrowserUtils.wait(2);
        for (WebElement product : currentProductsList) {
            if (product.getText().equals(applicationName)) {
                System.out.println(applicationName + " product found");
                return true;
            }
        }
        return false;
    }

    public boolean verifyProduct(String applicationName, String productName) {
        if (!verifyProduct(applicationName)) {
            System.out.println(applicationName + " application not found");
            return false;
        }
        System.out.println("Application Found. Now verifying product: " + productName);
        selectApplication(applicationName);
        BrowserUtils.wait(2);
        System.out.println("Available products:" + BrowserUtils.getElementsText(currentProductFeaturesList));
        for (WebElement product : currentProductFeaturesList) {
            if (product.getText().equals(productName)) {
                //System.out.println(productName + " product found");
                return true;
            }
        }
        System.out.println(productName + " product not found");
        return false;
    }

    public void selectApplication(String applicationName) {
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnProductsTab();
        BrowserUtils.wait(2);
        for (WebElement application : currentProductsList) {
            if (application.getText().equals(applicationName)) {
                application.click();
                System.out.println(applicationName + " application selected");
                break;
            }
        }
    }

    public void deleteProduct(String applicationName) {
        System.out.println("Deleting product: " + applicationName);
        BrowserUtils.wait(2);
        for (WebElement product : currentProductsList) {
            if (product.getText().equals(applicationName)) {
                System.out.println(applicationName + " product found");
                product.click();
                wait(currentProductFeaturesDeleteButtons, 5);
                System.out.println("Number of Features will be deleted = " + currentProductFeaturesDeleteButtons.size());
                while (currentProductFeaturesDeleteButtons.size() > 0) {
                    currentProductFeaturesDeleteButtons.get(0).click();
                    BrowserUtils.wait(1);
                    assertTestCase.assertTrue(deleteApplicationsProceedButton.isDisplayed(), "Delete Product Confirmation Popup is displayed");
                    BrowserUtils.waitForClickablility(deleteApplicationsProceedButton, 5).click();
                    BrowserUtils.wait(1);
                }
                System.out.println("all features of " + applicationName + " product deleted");
                break;
            }
        }
        clickOnDetailsTab();
        BrowserUtils.wait(2);
        clickOnProductsTab();
        BrowserUtils.wait(3);
    }

    public void deleteProduct(String applicationName, String productName) {
        expandAllApplications();
        BrowserUtils.wait(2);
        System.out.println("Current Features: "+BrowserUtils.getElementsText(currentProductFeaturesList));
        System.out.println("Number of Delete buttons: "+currentProductFeaturesDeleteButtons.size());
        for (WebElement product : currentProductFeaturesList) {
            if (product.getText().equals(productName)) {
                int index = currentProductFeaturesList.indexOf(product);
                System.out.println("index = " + index);
                currentProductFeaturesDeleteButtons.get(index).click();
                proceedButton.click();
                System.out.println(productName + " product deleted");
            }
        }
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnProductsTab();
        BrowserUtils.wait(3);
    }

    private void expandAllApplications() {
        System.out.println("Expanding all applications");
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnProductsTab();
        BrowserUtils.wait(3);
        for (WebElement application : currentProductsList) {
            System.out.println("application = " + application.getText());
            application.click();
            BrowserUtils.wait(1);
        }
    }

    public void clickOnBackToUsersButton() {
        System.out.println("clicking on back to users button");
        BrowserUtils.waitForClickablility(backToUsersButton, 5).click();
    }

    public void clickOnAssignApplicationButton() {
        System.out.println("clicking on assign application button");
        BrowserUtils.waitAndClick(assignApplicationsButton, 10);
    }

    public void clickOnEditButton() {
        System.out.println("clicking on edit button");
        BrowserUtils.waitAndClick(editButton, 10);
    }

    public void clickOnSaveButton() {
        System.out.println("clicking on save button");
        BrowserUtils.waitAndClick(saveButton, 10);
    }

    public void clickOnCancelButton() {
        System.out.println("clicking on cancel button");
        BrowserUtils.waitAndClick(cancelButton, 10);
    }

    public boolean verifyAccountDetails() {
        wait(accountKeyInput, 10);
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Accounts Page - Details Tab is title displayed");
        assertTestCase.assertTrue(detailsTab.isDisplayed(), "Accounts Page - Details Tab is displayed");
        assertTestCase.assertTrue(applicationsTab.isDisplayed(), "Accounts Page - Applications Tab is displayed");
        assertTestCase.assertTrue(productsTab.isDisplayed(), "Accounts Page - Products Tab is displayed");
        assertTestCase.assertTrue(usersTab.isDisplayed(), "Accounts Page - Users Tab is displayed");
        assertTestCase.assertTrue(accountKeyInput.isDisplayed(), "Accounts Page - Account Key Input is displayed");
        assertTestCase.assertTrue(accountNameInput.isDisplayed(), "Accounts Page - Account Name Input is displayed");
        assertTestCase.assertTrue(statusLabel.isDisplayed(), "Accounts Page - Status Check Box is displayed");
        assertTestCase.assertTrue(subscriberInput.isDisplayed(), "Accounts Page - Subscriber Input is displayed");
        assertTestCase.assertTrue(contractStartDateInput.isDisplayed(), "Accounts Page - Contract Start Date Input is displayed");
        assertTestCase.assertTrue(contractEndDateInput.isDisplayed(), "Accounts Page - Contract End Date Input is displayed");
        assertTestCase.assertTrue(creationInfo.isDisplayed(), "Accounts Page - Creation Info is displayed");
        assertTestCase.assertTrue(modificationInfo.isDisplayed(), "Accounts Page - Modification Info is displayed");
        return true;
    }

    public void assignProduct(String applicationName, String productName) {
        clickOnProductsTab();
        BrowserUtils.wait(3);
        if (!verifyProduct(applicationName)) {
            System.out.println("Application is not assigned to account. Assigning application: " + applicationName);
            assignApplication(applicationName);
            clickOnProductsTab();
        }
        System.out.println("Application Found. Now verifying product: " + productName);
        if (verifyProduct(applicationName, productName)) {
            //System.out.println(productName + " product already assigned to account");
            return;
        }
        System.out.println("Application is assigned but product is not assigned. Assigning product: " + productName);
        clickOnAssignProductsButton();
        BrowserUtils.wait(2);
        System.out.println("Available products:" + BrowserUtils.getElementsText(availableProductsNamesList));
        availableProductsNamesList.stream().filter(application -> application.getText().equals(applicationName)).findFirst().get().click();
        BrowserUtils.wait(5);
        wait(availableFeaturesNamesList, 15);
        System.out.println("Available features:" + BrowserUtils.getElementsText(availableFeaturesNamesList));
        for (WebElement product : availableFeaturesNamesList) {
            if (product.getText().equals(productName)) {
                System.out.println(productName + " product found in available features");
                BrowserUtils.waitForClickablility(availableFeaturesAssignButtonsList.get(availableFeaturesNamesList.indexOf(product)), 5).click();
                System.out.println("Assign button clicked.");
                break;
            }
        }
        BrowserUtils.waitForClickablility(doneButton, 5).click();
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnProductsTab();
        BrowserUtils.wait(3);
    }

    public void clickOnAssignProductsButton() {
        System.out.println("clicking on assign products button");
        BrowserUtils.waitAndClick(assignProductsButton, 10);
    }

    public void addAndVerifySMEAssessmentLimit(String limit) {
        clickOnAssignProductsButton();
        BrowserUtils.wait(2);
        System.out.println("Available products:" + BrowserUtils.getElementsText(availableProductsNamesList));
        availableProductsNamesList.stream().filter(application -> application.getText().equals(mesgApp)).findFirst().get().click();
        wait(availableFeaturesNamesList, 15);
        BrowserUtils.wait(5);
        System.out.println("Available features:" + BrowserUtils.getElementsText(availableFeaturesNamesList));
        WebElement productElement = availableFeaturesNamesList.stream().filter(feature -> feature.getText().equals(product)).findFirst().get();
        BrowserUtils.scrollTo(productElement);
        assertTestCase.assertTrue(smePurchasedAssessmentInput.isDisplayed(), "SME Purchased Assessments Limit is present");
        assertTestCase.assertTrue(smeUsedAssessmentInput.isDisplayed(), "SME Used Assessments Limit is present");
        assertTestCase.assertFalse(smeUsedAssessmentInput.isEnabled(), "SME Used Assessments Limit is not enabled");
        assertTestCase.assertTrue(smePurchasedAssessmentInput.isEnabled(), "SME Purchased Assessments Limit is enabled");
        assertTestCase.assertEquals(smePurchasedAssessmentInput.getAttribute("value"), "0", "SME Purchased Assessments Limit is set to 0 by default");
        smePurchasedAssessmentInput.sendKeys(Keys.BACK_SPACE, Keys.TAB);
        assertTestCase.assertTrue(purchasedAssessmentsRequiredMessage.isDisplayed(), "Purchased Assessments Required Message is displayed");
        smePurchasedAssessmentInput.sendKeys(limit);
        BrowserUtils.scrollTo(availableFeaturesAssignButtonsList.get(availableFeaturesNamesList.indexOf(productElement))).click();
        assertTestCase.assertTrue(doneButton.isEnabled(), "Save button is enabled");
        BrowserUtils.scrollTo(doneButton).click();
        clickOnDetailsTab();
        BrowserUtils.wait(2);
        clickOnProductsTab();
        BrowserUtils.wait(3);
        assertTestCase.assertTrue(verifyProduct(mesgApp, product), "ESG On-Demand Assessment product is assigned");
        esgOnDemandAssessmentEditButton.click();
        assertTestCase.assertEquals(smePurchasedAssessmentInput.getAttribute("value"), limit, "SME Purchased Assessments Limit is set to 10");
        closeButton.click();
    }

    public void verifyDetailsPage() {
        assertTestCase.assertTrue(detailsTab.isDisplayed(), "Details tab is displayed");
        assertTestCase.assertTrue(applicationsTab.isDisplayed(), "Applications tab is displayed");
        assertTestCase.assertTrue(productsTab.isDisplayed(), "Products tab is displayed");
        assertTestCase.assertTrue(usersTab.isDisplayed(), "Users tab is displayed");
        assertTestCase.assertTrue(backToAccountsButton.isDisplayed(), "Edit button is displayed");
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(accountKeyInput.isDisplayed(), "Account Key input is displayed");
        assertTestCase.assertTrue(accountNameInput.isDisplayed(), "Account Name input is displayed");
        assertTestCase.assertTrue(statusLabel.isDisplayed(), "Status label is displayed");
        assertTestCase.assertTrue(subscriberInput.isDisplayed(), "Subscriber input is displayed");
        assertTestCase.assertTrue(contractStartDateInput.isDisplayed(), "Contract Start Date input is displayed");
        assertTestCase.assertTrue(contractEndDateInput.isDisplayed(), "Contract End Date input is displayed");
        assertTestCase.assertTrue(creationInfo.isDisplayed(), "Creation Info is displayed");
        assertTestCase.assertTrue(modificationInfo.isDisplayed(), "Modification Info is displayed");
    }

    public void verifyDetailsPage(String accountType) {
        verifyDetailsPage();
        if (accountType.equals("Admin")) {
            assertTestCase.assertTrue(editButton.isDisplayed(), "Edit button is displayed");
        }
        //Account is editable mode
        editButton.click();
        assertTestCase.assertTrue(cancelButton.isEnabled(), "Accounts Page - Users Details - Cancel button is enabled for editing");
        assertTestCase.assertTrue(saveButton.isDisplayed(), "Accounts Page - Users Details - Save button is enabled for editing");
        assertTestCase.assertTrue(accountNameInput.isEnabled(), "Accounts Page - Users Details - Account Name input is enabled for editing");
        assertTestCase.assertFalse(accountKeyInput.isEnabled(), "Accounts Page - Users Details - Account Key input is disabled for editing");
        assertTestCase.assertTrue(statusCheckBox.isEnabled(), "Accounts Page - Users Details - Account status checkbox is enabled for editing");
        assertTestCase.assertTrue(subscriberInput.isEnabled(), "Accounts Page - Users Details - Account Subscriber Input is enabled for editing");
        assertTestCase.assertTrue(startDateInput.isEnabled(), "Accounts Page - Users Details - Account start date input is enabled for editing");
        assertTestCase.assertTrue(endDateInput.isEnabled(), "Accounts Page - Users Details - Account end date input is enabled for editing");

        //Click Cancel button on Account Details editable
        //Cancel button should take the user back to the Accounts view page
        cancelButton.click();
    }

    public void clickOnUserOptionsMenu() {
        System.out.println("clicking on user options menu");
        assertTestCase.assertTrue(userOptionsButton.isDisplayed(), "User Options button is displayed");
        BrowserUtils.waitAndClick(userOptionsButton, 10);
    }

    public void clickOnMenuOption(String optionName) {
        clickAwayInBlankArea();
        clickOnUserOptionsMenu();
        System.out.println("clicking on bulk import option");
        wait(userOptionsList, 10);
        userOptionsList.stream().filter(option -> option.getText().equals(optionName)).findFirst().get().click();
    }

    public void verifyUsersMenuOption(String optionName) {
        clickOnUserOptionsMenu();
        System.out.println("verifying bulk import option");
        wait(userOptionsList, 10);
        assertTestCase.assertTrue(userOptionsList.stream().anyMatch(option -> option.getText().equals(optionName)), optionName + " option is displayed");
        clickAwayInBlankArea();
    }

    public void verifyUsersPageDetails() {
        if (userNamesList.size() == 0) createUser();
        assertTestCase.assertTrue(usersTab.isDisplayed(), "Users tab is displayed");
        assertTestCase.assertTrue(pageTitle.isDisplayed(), "Page title is displayed");
        assertTestCase.assertTrue(addUserButton.isDisplayed(), "Add User button is displayed");
        assertTestCase.assertTrue(userOptionsButton.isDisplayed(), "User Options button is displayed");
        assertTestCase.assertTrue(searchInput.isDisplayed(), "Search input is displayed");
        assertTestCase.assertTrue(userCheckboxList.size() > 0, "Users checkboxes are displayed");
        assertTestCase.assertTrue(userNamesList.size() > 0, "Users names are displayed");
        assertTestCase.assertTrue(userEmailsList.size() > 0, "Users emails are displayed");
        assertTestCase.assertTrue(providerList.size() > 0, "Users providers are displayed");
    }

    public String getUserEmail(String userName) {
        wait(userNamesList, 10);
        WebElement userElement = userNamesList.stream().filter(user -> user.getText().equals(userName)).findFirst().get();
        int index = userNamesList.indexOf(userElement);
        return userEmailsList.get(index).getText();
    }
}
