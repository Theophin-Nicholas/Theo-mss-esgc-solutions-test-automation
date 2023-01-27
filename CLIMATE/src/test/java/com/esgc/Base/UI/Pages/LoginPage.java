package com.esgc.Base.UI.Pages;

import com.esgc.EntityProfile.UI.Pages.ClimatePageBase;
import com.esgc.Utilities.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.Set;

public class LoginPage extends ClimatePageBase {


    @FindBy(id = "")
    public WebElement logo;

    @FindBy(id = "okta-signin-submit")
    public WebElement loginButton;
    @FindBy(id = "idp-discovery-submit")
    public List<WebElement> loginButtons;
    @FindBy(id = "idp-discovery-username")
    public WebElement usernameBox;

    @FindBy(id = "idp-discovery-username")
    public List<WebElement> usernameBoxs;

    @FindBy(id = "okta-signin-username")
    public WebElement PTusernameBox;

    @FindBy(id = "idp-discovery-submit")
    public WebElement nextButton;

    @FindBy(id = "okta-signin-password")
    public WebElement passwordBox;

    @FindBy(id = "okta-signin-password")
    public WebElement PTpasswordBox;

    @FindBy(xpath = "//*[@name=\"remember\"]")
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


    // =================== Failed Login Attempt - Error Message =======================

    @FindBy(xpath = "//div[@data-se='o-form-error-container']")
    public WebElement warningMsg;//We found some errors. Please review the form and make corrections.

    /*
    If you try to log in with blank username this message appears:
    This field cannot be left blank error message
     */
    @FindBy(id = "input-container-error9")
    public WebElement blankUsernameErrorMessage;

    /*
   If you try to log in with blank password this message appears:
    Please enter a password
    */
    @FindBy(id = "input-container-error13")
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

    @FindBy(xpath = "//li[text()=\"Moody's ESG360: Dashboard\"]")
    public WebElement MESGCLeftMenuHeader;

    @FindBy(xpath = "//a[text()='Terms of Use']")
    public WebElement termsOfUse;

    @FindBy(xpath = "//li[text()='Log Out']")
    public WebElement logout;

    //================= OKTA

    @FindBy(xpath = "//div[@data-se='dropdown-menu-button-header']")
    public WebElement userDropdown;
    //===================================================================================

    public boolean isUsernameBoxDisplayed() {
        try {
            return usernameBox.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * *Method for login - version #1
     * Reusable login method that provides username & password as parameters
     *
     * @param userName
     * @param password
     */
    public void loginWithParams(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
      /*  if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();

    }

    public void loginWithParamsToOktaPage(String userName, String password) {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(userName, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(password);
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();

    }

    /**
     * *Method for login - version #1.2
     * Login as an internal user
     * Credentials will be retrieved from properties files
     */
    public void loginWithInternalUser() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        if (!Environment.INTERNAL_USER_USERNAME.contains("moodys.com")) {
            wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);
        }
    }

    public void loginWithWrongUser() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys("Wrong_User_Name", Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);
    }
    public void loginWithWrongPass() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys("Wrong_Pass_Word", Keys.ENTER);
    }
    /*
     *Method for login - version #2
     * Login as a default user
     * Credentials will be retrieved from configuration.properties file
     */
    public void login() {
        System.out.println("Login with default user");
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.UI_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.UI_PASSWORD);
//        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.UI_USERNAME, Keys.ENTER);
//        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.UI_PASSWORD);
       /* if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }
    public void userLoginWithNoControversiesBundle() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_PASSWORD);
       /* if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }
    public void userLoginWithNoEsgBundle() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_OUT_ESG_ENTITLEMENT_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_OUT_ESG_ENTITLEMENT_PASSWORD);
       /* if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }
    public void userLoginWithNoExportBundle() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_OUT_EXPORT_ENTITLEMENT_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_OUT_EXPORT_ENTITLEMENT_PASSWORD);
       /* if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    /*
     *Method for login - version #3
     * Login for data validation
     * Credentials will be retrieved from configuration.properties file
     */
    public void dataValidationLogin() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.DATA_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.DATA_PASSWORD);
       // if (!termsAndConditionsCheckBox.isSelected()) //TODO This is removed
         //   wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    /*
     *Method for login - version #2
     * Login as issuer
     * Credentials will be retrieved from configuration.properties file
     */


    public void entitlementsLogin(EntitlementsBundles bundles) {
        switch (bundles) {
            case PHYSICAL_RISK:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PHYSICAL_RISK_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PHYSICAL_RISK_PASSWORD);
                break;
            case TRANSITION_RISK:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.TRANSITION_RISK_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.TRANSITION_RISK_PASSWORD);
                break;
            case CLIMATE_GOVERNANCE:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.CLIMATE_GOVERNANCE_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.CLIMATE_GOVERNANCE_PASSWORD);
                break;
            case PHYSICAL_RISK_TRANSITION_RISK:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PHYSICAL_RISK_TRANSITION_RISK_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PHYSICAL_RISK_TRANSITION_RISK_PASSWORD);
                break;
            case TRANSITION_RISK_CLIMATE_GOVERNANCE:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.TRANSITION_RISK_CLIMATE_GOVERNANCE_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.TRANSITION_RISK_CLIMATE_GOVERNANCE_PASSWORD);
                break;
            case PHYSICAL_RISK_CLIMATE_GOVERNANCE:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PHYSICAL_RISK_CLIMATE_GOVERNANCE_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PHYSICAL_RISK_CLIMATE_GOVERNANCE_PASSWORD);
                break;
            case USER_WITH_CONTROVERSIES_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_CONTROVERSIES_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_CONTROVERSIES_ENTITLEMENT_PASSWORD);
                break;
            case USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_OUT_CONTROVERSIES_ENTITLEMENT_PASSWORD);
                break;
            case USER_WITH_EXPORT_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_EXPORT_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_EXPORT_ENTITLEMENT_PASSWORD);
                break;
            case USER_WITH_ESG_WITHOUT_EXPORT_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_ESG_WITHOUT_EXPORT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_ESG_WITHOUT_EXPORT_PASSWORD);
                break;
            case USER_WITH_OUT_EXPORT_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_OUT_EXPORT_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_OUT_EXPORT_ENTITLEMENT_PASSWORD);
                break;
            case USER_WITH_ESG_PS_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_ESG_PS_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_ESG_PS_ENTITLEMENT_PASSWORD);
                break;
            case USER_WITH_ESG_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.USER_WITH_ESG_ENTITLEMENT_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.USER_WITH_ESG_ENTITLEMENT_PASSWORD);
                break;
            case PDF_EXPORT_BUNDLE_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PDF_EXPORT_BUNDLE_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PDF_EXPORT_BUNDLE_PASSWORD);
                break;
            case PDF_EXPORT_ONLY_PDF_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PDF_EXPORT_ONLY_PDF_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PDF_EXPORT_ONLY_PDF_PASSWORD);
                break;
            case PDF_EXPORT_ONLY_SOURCEDOCUMENTS_ENTITLEMENT:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.PDF_EXPORT_ONLY_SOURCEDOCUMENTS_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.PDF_EXPORT_ONLY_SOURCEDOCUMENTS_PASSWORD);
                break;
            case NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS:
                wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_USERNAME, Keys.ENTER);
                wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.NO_PREVIOUSLY_DOWNLOADED_REGULATORY_REPORTS_PASSWORD);
                break;
            default:
                Assert.fail("Bundle not found!");
        }
     /*   if (!termsAndConditionsCheckBox.isSelected())
            wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();*/
        wait.until(ExpectedConditions.visibilityOf(loginButton)).click();
    }

    /*
     *Method for login - version #2
     * Login as a default user
     * Credentials will be retrieved from configuration.properties file
     */
    public void loginEMCInternal() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.INTERNAL_USER_USERNAME, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(Environment.INTERNAL_USER_PASSWORD, Keys.ENTER);

        System.out.println("Logged in as Internal User");
    }

    /*
    Click method for 'Forget Password'
     */
    public void clickForgetPassword() {
        wait.until(ExpectedConditions.visibilityOf(forgotPassword));
        forgotPassword.click();

    }


    /*
     Method to get the warning message after invalid login attempt
      */
    public String getWarningMsgText() {

        return warningMsg.getText();

    }

    /*
        Method for verifying Moody's logo on Login page
         */
    public boolean verifyLogo() {
        return logo.isDisplayed();

    }


    public void clickNeedHelpSignIn() {
        needHelpSignIn.click();
        wait.until(ExpectedConditions.visibilityOf(forgotPassword));
    }

    public boolean checkIfForgotPasswordIsClickable() {
        try {
            BrowserUtils.waitForClickablility(forgotPassword, 5);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean resetViaEmailButtonIsDisplayed() {
        try {
            return resetViaEmailButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }

    }

    public boolean checkIfRememberMeCheckboxIsPresent() {
        try {
            return rememberMeCheckboxLabel.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfRememberMeCheckBoxIsUncheckByDefault() {
        try {
            return rememberMeCheckBox.isSelected();
        } catch (Exception e) {
            return true;
        }
    }

    /*
     *Method for login - version #2
     * Login as a default user
     * Credentials will be retrieved from configuration.properties file
     */
    public void loginWithInvalidCredentials() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(ConfigurationReader.getProperty("invalidUsername"), Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys(ConfigurationReader.getProperty("invalidPassword"), Keys.ENTER);

    }

    public boolean checkIfCorrectValidationErrorMsgDisplayed() {
        try {
            return UnauthorisedUserErrorMsg.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }


    public boolean checkIfNextButtonIsPresent() {
        try {
            return nextButton.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public boolean checkIfNextButtonIsClickable() {
        try {
            BrowserUtils.waitForClickablility(nextButton, 5);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void EnterUserName() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys(Environment.UI_USERNAME, Keys.ENTER);
    }

    public void EnterUserNameAsBlank() {
        wait.until(ExpectedConditions.visibilityOf(usernameBox)).sendKeys("", Keys.ENTER);
    }

    public boolean checkIfCorrectValidationErrorMsgForBlankUserNameDisplayed() {
        try {
            return BlankUserNameErrorMsg.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }


    public void EnterPasswordAsBlank() {
        wait.until(ExpectedConditions.visibilityOf(passwordBox)).sendKeys("", Keys.ENTER);

    }

    public boolean checkIfCorrectValidationErrorMsgForBlankPasswordDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(BlankPasswordErrorMsg)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void navigateToESGPlatform() {
        Driver.getDriver().get(Environment.URL);
    }

    public boolean checkIfUserLoggedInSuccessfully() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(MESGCLeftMenuHeader)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean checkIfUserLoggedInOKTASuccessfully() {
        try {
            return userDropdown.isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public void clickTermsOfUseLink() {
        BrowserUtils.waitForClickablility(termsOfUse, 5);
        termsOfUse.click();
    }

    public void checkBackToSignIn() {
        Set<String> handles = Driver.getDriver().getWindowHandles();
        if (handles.size() != 2) {
            System.out.println("Terms of conditions did not open a new page");
            Assert.fail("Test failed");
        }
        String currentHandle = Driver.getDriver().getWindowHandle();
        for (String handle : handles) {
            if (!handle.equals(currentHandle)) {
                Driver.getDriver().switchTo().window(handle);
                Assert.assertTrue(backToSignInButton.isDisplayed());
            }
        }
        Driver.getDriver().switchTo().window(currentHandle);
    }

    public void clickOnLogout() {
        BrowserUtils.wait(10);
        wait.until(ExpectedConditions.visibilityOf(menu));
        BrowserUtils.clickWithJS(menu);
        logout.click();
    }

    public void checkTermsAndConditions(){
        wait.until(ExpectedConditions.visibilityOf(termsAndConditionsLabel)).click();
    }
}



