package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.github.javafaker.Faker;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EMCAccountDetailsPage extends EMCBasePage {
    @FindBy (tagName = "h4")
    public WebElement pageTitle;

    //DETAILS TAB

    @FindBy (xpath = "//button[.='Details']")
    public WebElement detailsTab;

    @FindBy (name = "name")
    public WebElement accountNameInput;

    @FindBy (tagName = "label")
    public WebElement statusLabel;

    @FindBy (xpath = "//label//input")
    public WebElement statusCheckBox;

    @FindBy (xpath = "//div[@name='subscriberType']")
    public WebElement subscriberInput;

    @FindBy (name="contractStartDate")
    public WebElement startDateInput;

    @FindBy (name="contractEndDate")
    public WebElement endDateInput;

    @FindBy (xpath = "//button[.='Save']")
    public WebElement saveButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement cancelButton;

    @FindBy (xpath = "//button[.='Edit']")
    public WebElement editButton;

    @FindBy (xpath = "//span[starts-with(.,'Created')]")
    public WebElement creationInfo;

    @FindBy (xpath = "//span[starts-with(.,'Modified')]")
    public WebElement modificationInfo;

    @FindBy (xpath = "//button[starts-with(.,'Back')]")
    public WebElement backToAccountsButton;

    //APPLICATIONS TAB

    @FindBy (xpath = "//button[.='Applications']")
    public WebElement applicationsTab;

    @FindBy (xpath = "//h6[.='Applications']")
    public WebElement applicationsTabTitle;

    @FindBy (xpath = "//button[.='Assign applications']")
    public WebElement assignApplicationsButton;

    @FindBy (xpath = "//h6[.='No Apps assigned to this account']")
    public WebElement noApplicationMessage;

    @FindBy (xpath = "//li/div[1]//span")
    public List<WebElement> applicationsNamesList;

    @FindBy (xpath = "//li//button")
    public List<WebElement> applicationsDeleteButtons;

    @FindBy (xpath = "//h2/../..//li//button")
    public List<WebElement> applicationsAssignButtonList;

    @FindBy (xpath = "//li/div//button")
    public List<WebElement> deleteApplicationsButtons;

    @FindBy (xpath = "//button[.='Proceed']")
    public WebElement deleteApplicationsProceedButton;

    //ASSIGN APPLICATIONS Modal

    @FindBy (tagName = "h2")
    public WebElement assignApplicationsModalTitle;

    @FindBy (tagName = "input")
    public WebElement assignApplicationsModalSearchInput;

    @FindBy (xpath = "//input/../../button")
    public WebElement assignApplicationsModalSearchButton;

    @FindBy (xpath = "//button[.='Assign']/../..//div[1]//span")
    public List<WebElement> assignApplicationsModalApplicationsList;

    @FindBy (xpath = "//button[.='Assign']")
    public List<WebElement> assignApplicationsModalAssignButtonsList;

    @FindBy (xpath = "//button[.='Done']")
    public WebElement assignApplicationModalDoneButton;

    @FindBy (xpath = "//div[@id='notistack-snackbar']")
    public WebElement applicationAddedMessage;

    //PRODUCTS TAB

    @FindBy (xpath = "//button[.='Products']")
    public WebElement productsTab;

    @FindBy (xpath = "//h6[.='No Products assigned to this account']")
    public WebElement noProductsMessage;

    @FindBy (xpath = "//h6[.='Products']")
    public WebElement productsTabTitle;

    @FindBy (xpath = "//button[.='Assign products']")
    public WebElement assignProductsButton;

    @FindBy (xpath = "//ul/div")
    public List<WebElement> currentProductsList;

    @FindBy (xpath = "//li/div[1]//span")
    public List<WebElement> currentProductFeaturesList;

    @FindBy (xpath = "//div[.='Products']/following-sibling::div//ul//button")
    public List<WebElement> currentProductFeaturesDeleteButtons;
//after add product button clicked
    @FindBy (xpath = "//h2/../..//ul//p[2]")
    public List<WebElement> availableProductsNamesList;

    @FindBy (xpath = "//li/div[1]//span")
    public List<WebElement> availableFeaturesNamesList;

    @FindBy (xpath = "//button[.='Assign']")
    public List<WebElement> availableFeaturesAssignButtonsList;

    @FindBy (xpath = "//button[.='Done']")
    public WebElement doneButton;



    //USERS TAB

    @FindBy (xpath = "//button//span[.='Users']")
    public WebElement usersTab;

    @FindBy (xpath = "//button[.='Add User']")
    public WebElement addUserButton;

    @FindBy (xpath = "//input[@type='text']")
    public WebElement searchInput;

    @FindBy (xpath = "//th/a")
    public List<WebElement> userNamesList;

    @FindBy (xpath = "//td/a")
    public List<WebElement> userEmailsList;

    @FindBy (xpath = "//td//input")
    public List<WebElement> userCheckboxList;

    @FindBy (xpath = "//h2")
    public WebElement addUserPanelTitle;

    @FindBy (name = "firstName")
    public WebElement firstNameInput;

    @FindBy (name = "lastName")
    public WebElement lastNameInput;

    @FindBy (name = "email")
    public WebElement emailInput;

    @FindBy (name = "userName")
    public WebElement userNameInput;

    @FindBy (name = "activate")
    public WebElement sendActivationEmailCheckbox;

    @FindBy (xpath = "//p[.='First Name is a required field']")
    public WebElement firstNameInputWarning;

    @FindBy (xpath = "//p[.='Last Name is a required field']")
    public WebElement lastNameInputWarning;
    @FindBy (xpath = "//p[.='User Name is a required field']")
    public WebElement userNameInputWarning;
    @FindBy (xpath = "//p[.='Must be a valid email Address']")
    public WebElement validEmailWarning;
    @FindBy (xpath = "//p[.='Email is a required field']")
    public WebElement emailInputWarning;

    @FindBy (xpath = "//button[.='Back to Users']")
    public WebElement backToUsersButton;

    @FindBy (xpath = "//button[@title='Delete']")
    public WebElement deleteButton;

    @FindBy (xpath = "//h2[.='Delete users']")
    public WebElement deleteConfirmationPopup;

    @FindBy (xpath = "//button[.='Delete']")
    public WebElement popupDeleteButton;

    @FindBy (xpath = "//button[.='Cancel']")
    public WebElement popupCancelButton;

    @FindBy (xpath = "//div[@id='notistack-snackbar']")
    public WebElement deleteMessage;

    @FindBy (xpath = "//div[.='10']")
    public WebElement numberOfUsers10;

    @FindBy (xpath = "//li[.='50']")
    public WebElement numberOfUsers50;
    //create user under accounts with required fields
    public boolean createUser(String firstName, String lastName, String userName, boolean sendActivationEmail) {
        try {
            firstNameInput.sendKeys(firstName);
            lastNameInput.sendKeys(lastName);
            //emailInput.sendKeys(userName);
            userNameInput.sendKeys(userName, Keys.TAB);
            if(!sendActivationEmail) sendActivationEmailCheckbox.click();
            BrowserUtils.waitForClickablility(saveButton, 5).click();
            System.out.println("save button clicked");
            BrowserUtils.waitForClickablility(backToUsersButton, 5).click();
            return true;
        }
        catch (Exception e) {
            System.out.println("create user failed");
            return false;
        }
    }
    public boolean createUser() {
        Faker faker = new Faker();
        addUserButton.click();
        String firstName = "QATESTUSER";
        String lastName = faker.name().lastName();
        String userName = faker.internet().emailAddress();
        return createUser(firstName, lastName, userName, true);
    }
    public boolean createUser(boolean sendActivationEmail) {
        Faker faker = new Faker();
        addUserButton.click();
        String firstName = "QATESTUSER";
        String lastName = faker.name().lastName();
        String userName = faker.internet().emailAddress();
        return createUser(firstName, lastName, userName, sendActivationEmail);
    }

    public void searchUser(String test) {

        clear(searchInput);
        System.out.println("Searching for the user : "+test);
        searchInput.sendKeys(test);
        if(userNamesList.size()==0) {
            System.out.println("user not found");
            return;
        }
        userNamesList.get(0).click();
    }

    public boolean isSortedByName() {
        List<String> names = userNamesList.stream().map(name -> name.getText().toLowerCase()).collect(Collectors.toList());
        System.out.println("names are: "+names);
        return names.stream().sorted().collect(Collectors.toList()).equals(names);
    }

    public boolean assignApplication(String applicationName) {
        System.out.println("assigning application: "+applicationName);
        BrowserUtils.waitForClickablility(assignApplicationsButton,5).click();
        BrowserUtils.waitForClickablility(assignApplicationsModalSearchInput, 5).sendKeys(applicationName);
        BrowserUtils.wait(5);
        if (applicationsAssignButtonList.isEmpty()) {
            System.out.println("application not found");
            doneButton.click();

            return false;
        }else {
            BrowserUtils.waitForClickablility(applicationsAssignButtonList.get(0), 15).click();
            System.out.println(applicationName + " application assigned");
            doneButton.click();
            clickOnDetailsTab();
            BrowserUtils.wait(1);
            clickOnApplicationsTab();
            BrowserUtils.wait(3);
            return true;
        }
    }
    public boolean verifyUser(String name) {
        expandList();
        for(WebElement user : userNamesList) {
            if(user.getText().equals(name)) {
                System.out.println("user "+name+" found");
                return true;
            }
        }
        System.out.println("user "+name+" not found");
        return false;
    }
    public void expandList() {
        BrowserUtils.waitForClickablility(numberOfUsers10, 5).click();
        BrowserUtils.waitForClickablility(numberOfUsers50, 5).click();
    }
    public boolean deleteUser(String name) {
        BrowserUtils.scrollTo(usersTab);
        BrowserUtils.waitForClickablility(usersTab,5).click();
        for(WebElement user : userNamesList) {
            BrowserUtils.scrollTo(user);
            if(user.getText().equals(name)) {
                System.out.println("user "+name+" found");
                int index = userNamesList.indexOf(user);
                System.out.println("index = " + index);
                BrowserUtils.scrollTo(userCheckboxList.get(index));
                userCheckboxList.get(index).click();
                BrowserUtils.scrollTo(deleteButton);
                BrowserUtils.waitForClickablility(deleteButton, 5).click();
                BrowserUtils.waitForClickablility(popupDeleteButton, 5).click();
                System.out.println("user "+name+" deleted");
                return true;
            }
        }
        System.out.println(name + " user not found");
        return false;
    }

    public boolean verifyApplication(String applicationName) {
        //System.out.println("Number of current applications = " + applicationsNamesList.size());
        for(WebElement application : applicationsNamesList) {
            if(application.getText().equals(applicationName)) {
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
        while(verifyApplication(applicationName)) {

            for (int i = 0; i < applicationsNamesList.size(); i++) {
                if(applicationsNamesList.get(i).getText().equals(applicationName)) {
                    System.out.println("Application Found");
                    applicationsDeleteButtons.get(i).click();
                    BrowserUtils.waitForClickablility(deleteApplicationsProceedButton,5).click();
                    clickOnDetailsTab();
                    BrowserUtils.wait(1);
                    clickOnApplicationsTab();
                    BrowserUtils.wait(3);
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
        BrowserUtils.waitForClickablility(usersTab, 5).click();
    }
    public void clickOnDetailsTab() {
        System.out.println("clicking on details tab");
        BrowserUtils.waitForClickablility(detailsTab, 5).click();
    }
    public void clickOnApplicationsTab() {
        System.out.println("clicking on applications tab");
        BrowserUtils.scrollTo(applicationsTab);
        BrowserUtils.waitForClickablility(applicationsTab, 15).click();
    }
    public void clickOnProductsTab() {
        System.out.println("clicking on products tab");
        BrowserUtils.waitForClickablility(productsTab, 5).click();
    }

    public void deleteRandomUsers() {
        expandList();
        List<String> keepUsers = new ArrayList<>(Arrays.asList("Efrain June2022", "QA Test", "Ferhat Test","MA Test Account"));
        int waitTime = userNamesList.size();
        if(userNamesList.size()>keepUsers.size()) {
            for (int i = 0; i < userNamesList.size(); i++) {
                if (!keepUsers.contains(userNamesList.get(i).getText())){
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
    }
    public boolean addTestApplications(String applicationName) {
        try{
            if(verifyApplication(applicationName)) {
                System.out.println(applicationName + " application already exists");
                deleteApplication(applicationName);
                System.out.println(applicationName + " application deleted");
            }
            assignApplication(applicationName);
            BrowserUtils.waitForVisibility(applicationAddedMessage, 5);
            System.out.println(applicationName + " application added");

            BrowserUtils.waitForInvisibility(applicationAddedMessage,15);
            return true;
        } catch (Exception e) {
            System.out.println("error in adding applications");
            System.out.println("e = " + e);
            return false;
        }
    }

    public void printCurrentApplications(){
        System.out.println("Current applications are: ");
        for(WebElement application : applicationsNamesList) {
            System.out.println(application.getText());
        }
        System.out.println("-----------------------------------------------------");
    }
    public void printCurrentUsers(){
        System.out.println("Current Users are: ");
        for(WebElement user : userNamesList) {
            System.out.println(user.getText());
        }
        System.out.println("-----------------------------------------------------");
    }

    public void clickOnBackToAccountsButton() {
        System.out.println("clicking on back to accounts button");
        BrowserUtils.scrollTo(backToAccountsButton);
        BrowserUtils.waitForClickablility(backToAccountsButton,5).click();
    }

    public void addApplication(String applicationName) {
        System.out.println("Adding application: " + applicationName);
        BrowserUtils.waitForClickablility(assignApplicationsButton, 5).click();
        BrowserUtils.waitForVisibility(assignApplicationsModalTitle, 5);
        assignApplicationsModalSearchInput.sendKeys(applicationName);
        BrowserUtils.waitForClickablility(assignApplicationsModalAssignButtonsList.get(0),5).click();
        BrowserUtils.waitForClickablility(doneButton,5).click();
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnApplicationsTab();
        BrowserUtils.wait(3);
        System.out.println(applicationName + " application added");
    }

    public void addAllProducts(String applicationName) {
        System.out.println("Adding all features of " + applicationName+" application to account");
        BrowserUtils.waitForClickablility(assignProductsButton, 5).click();
        System.out.println("Number of available products: " + availableProductsNamesList.size());
        for(WebElement product : availableProductsNamesList) {
            System.out.println("Product Name = " + product.getText());
            BrowserUtils.scrollTo(product);
            if (product.getText().equals(applicationName)) {
                System.out.println(applicationName + " product found");
                product.click();
                System.out.println("Number of Available Features = " + availableFeaturesNamesList.size());
                if(availableFeaturesNamesList.size()>0) {
                    System.out.println("Adding " + availableFeaturesNamesList.get(0).getText() + " feature product");
                    BrowserUtils.waitForClickablility(availableFeaturesAssignButtonsList.get(0), 5).click();
                }
                else {
                    System.out.println("No features found");
                }
                break;
            }
        }
        BrowserUtils.waitForClickablility(doneButton,5).click();
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

    public void deleteProduct(String applicationName) {
        System.out.println("Deleting product: " + applicationName);
        BrowserUtils.wait(2);
        for (WebElement product : currentProductsList) {
            if (product.getText().equals(applicationName)) {
                System.out.println(applicationName + " product found");
                product.click();
                System.out.println("Number of Features will be deleted = "+currentProductFeaturesDeleteButtons.size());
                while(currentProductFeaturesDeleteButtons.size()>0) {
                    if(currentProductFeaturesDeleteButtons.get(0).isDisplayed()) {
                        currentProductFeaturesDeleteButtons.get(0).click();
                        System.out.println("Feature deleted");
                    } else {
                        break;
                    }
                }
                System.out.println("all features of " + applicationName + " product deleted");
                break;
            }
        }
        clickOnDetailsTab();
        BrowserUtils.wait(1);
        clickOnProductsTab();
        BrowserUtils.wait(3);
    }

    public void clickOnBackToUsersButton() {
        System.out.println("clicking on back to users button");
        BrowserUtils.waitForClickablility(backToUsersButton,5).click();
    }
}
