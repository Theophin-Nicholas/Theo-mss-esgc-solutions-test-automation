package com.esgc.Pages;

import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.Environment;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPageEMC extends PageBase {


    @FindBy(id = "")
    public WebElement logo;

    @FindBy(id = "okta-signin-submit")
    public WebElement loginButton;

    @FindBy(id = "idp-discovery-username")
    public WebElement usernameBox;

    @FindBy(id = "okta-signin-username")
    public WebElement PTusernameBox;

    @FindBy(id = "idp-discovery-submit")
    public WebElement nextButton;

    @FindBy(id = "okta-signin-password")
    public WebElement passwordBox;

    @FindBy(id = "okta-signin-password")
    public WebElement PTpasswordBox;

    @FindBy(xpath = "//*[@name='remember']")
    public WebElement rememberMeCheckBox;

    @FindBy(xpath = "//*[text()='Remember me']")
    public WebElement rememberMeCheckboxLabel;

    //after clicked this, forgot password and help links will appear
    @FindBy(xpath = "//a[text()='Need help signing in?']")
    public WebElement needHelpSignIn;

    @FindBy(xpath = "//a[text()='Forgot password?']")
    public WebElement forgotPassword;

    @FindBy(xpath = "//a[text()='Help']")
    public WebElement help;

    @FindBy(xpath = "//p[text()='Unable to sign in']")
    public WebElement UnauthorisedUserErrorMsg;

    @FindBy(id = "input-termsandconditions")
    public WebElement termsAndConditionsCheckBox;

    @FindBy(xpath = "//label[text()='I have read and accepted the ']")
    public WebElement termsAndConditionsLabel;

    @FindBy(xpath = "//p[text()='This field cannot be left blank']")
    public WebElement BlankUserNameErrorMsg;

    @FindBy(xpath = "//p[text()='Please enter a password']")
    public WebElement BlankPasswordErrorMsg;

    @FindBy(xpath = "//p[text()='User is not assigned to the client application.']")
    public WebElement UserNotAssignedApplicationErrorMsg;


    // =================== Failed Login Attempt - Error Message =======================

    @FindBy(xpath = "//p[starts-with(.,'We found some errors.')]")
    public WebElement warningMsg;//We found some errors. Please review the form and make corrections.

    /*
    If you try to log in with blank username this message appears:
    This field cannot be left blank error message
     */
    @FindBy(xpath = "//p[.='This field cannot be left blank']")
    public WebElement blankUsernameErrorMessage;

    /*
   If you try to log in with blank password this message appears:
    Please enter a password
    */
    @FindBy(xpath = "//p[.='Please enter a password']")
    public WebElement blankPasswordErrorMessage;


    // =================== Forgot Password Page  =======================

    @FindBy(id = "account-recovery-username")
    public WebElement enterEmailOrUsernameBox;

    @FindBy(xpath = "//a[text()='Reset via Email']")
    public WebElement resetViaEmailButton;

    @FindBy(xpath = "//a[text()='← Back to Sign-In Page']")
    public WebElement backToSignInButton;


    // ========================================== MESG Platform

    @FindBy(xpath = "//body")
    public WebElement body;

    @FindBy(xpath = "//li[text()='Moody's ESG360: Dashboard']")
    public WebElement MESGCLeftMenuHeader;

    @FindBy(xpath = "//a[text()='Terms of Use']")
    public WebElement termsOfUse;

    @FindBy(xpath = "//li[text()='Log Out']")
    public WebElement logout;

    //================= OKTA

    @FindBy(xpath = "//div[@data-se='dropdown-menu-button-header']")
    public WebElement userDropdown;
    //===================================================================================

    /**
     * *Method for login - version #1
     * Reusable login method that provides username & password as parameters
     *
     * @param userName
     * @param password
     */
    public void loginWithParams(String userName, String password) {
        Driver.getDriver().manage().window().maximize();

        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        try{
            BrowserUtils.wait(5);
            if(nextButton.isDisplayed()){
                System.out.println("Next button is displayed");
                BrowserUtils.waitForClickablility(nextButton, 3).click();
            }
        }catch (Exception e){
            System.out.println("No need to click next button");
        }
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
        System.out.println("Login with params");
        Driver.getDriver().manage().window().maximize();
    }

    /**
     * *Method for login - version #1.2
     * Login as an internal user
     * Credentials will be retrieved from properties files
     */
    public void loginWithInternalUser() {
        Driver.getDriver().manage().window().maximize();
        System.out.println("Login with internal user");
//        BrowserUtils.clearCache();
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        //check if next button is displayed
//        try{
//            BrowserUtils.wait(5);
//            BrowserUtils.waitAndClick(nextButton, 3);
//        }catch (Exception e){
//            System.out.println("No need to click next button");
//        }

        //check if username is displayed and cleared
        try {
            BrowserUtils.wait(5);
            BrowserUtils.clearCache();
            if (PTusernameBox.getAttribute("value").isEmpty())
                PTusernameBox.sendKeys(Environment.INTERNAL_USER_USERNAME);
        } catch (Exception e) {
            System.out.println("No need to enter username");
        }

        //check if password is displayed
        try {
            //wait.until(ExpectedConditions.visibilityOf(
            passwordBox.sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);
        } catch (Exception e) {
            System.out.println("No need to enter password");
        }
        BrowserUtils.waitForPageToLoad(30);
    }
    public void loginEMCInternal() {
        System.out.println("Log in to Prod with Authorized User");
        Driver.getDriver().manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        BrowserUtils.wait(5);
        try{
            if(PTusernameBox.getAttribute("value").isEmpty())
                wait.until(ExpectedConditions.visibilityOf(PTusernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME);
            wait.until(ExpectedConditions.visibilityOf(PTpasswordBox)).sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);
        } catch (Exception e){
            System.out.println("No need to enter username and password");
        }

        Driver.getDriver().manage().window().maximize();
    }
    public void loginWithWrongPass() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        try{
            BrowserUtils.wait(5);
            BrowserUtils.waitAndClick(nextButton, 3);
        }catch (Exception e){
            System.out.println("No need to click next button");
        }
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys("Wrong_Pass_Word", Keys.ENTER);
    }

    public void loginWithWrongUser() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys("Wrong_User_Name", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);
    }

    public void clickOnLogout() {
        BrowserUtils.wait(10);
        wait.until(ExpectedConditions.visibilityOf(menu));
        BrowserUtils.clickWithJS(menu);
        logout.click();
    }

    public void loginEMCWithParams(String userName, String password) {
        Driver.getDriver().manage().window().maximize();
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        BrowserUtils.waitAndClick(PTusernameBox, 10);
        if(PTusernameBox.getAttribute("value").isEmpty())
            wait.until(ExpectedConditions.visibilityOf(PTusernameBox)).sendKeys(userName);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
        Driver.getDriver().manage().window().maximize();
    }
}



