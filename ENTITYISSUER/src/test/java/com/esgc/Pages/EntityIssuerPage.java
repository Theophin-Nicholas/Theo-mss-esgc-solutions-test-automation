package com.esgc.Pages;


import com.esgc.Reporting.CustomAssertion;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Driver;
import com.esgc.Utilities.EntityIssuerESGSubcategoriesDescriptions;
import org.apache.commons.lang3.math.NumberUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.SkipException;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;

import static org.openqa.selenium.By.xpath;


public class EntityIssuerPage extends PageBase {

    protected CustomAssertion assertTestCase = new CustomAssertion();

    //============ Sector curve
    @FindBy(xpath = "//div[contains(@id,'highchart')]")
    public WebElement sectorCurveChart;

    @FindBy(xpath = "//*[name()='g' and @class='highcharts-axis-labels highcharts-xaxis-labels']")
    public List<WebElement> chartLables;

    @FindBy(xpath = "//*[name()='text'][@class='highcharts-plot-line-label ']")
    public List<WebElement> plotLineLabel;

    @FindBy(xpath = "//*[@id=\"issuer-banner-id\"]/div/div/div/div/div[1]/div[1]")
    public WebElement bodyTestOne;

    @FindBy(xpath = "//*[@id=\"issuer-banner-id\"]/div/div/div/div/div[1]/div[2]")
    public WebElement bodyTestTwo;

    @FindBy(xpath = "//*[@style='color: rgb(31, 140, 255);']")
    public WebElement highlightedText;

    @FindBy(xpath = "//div[@class='MuiGrid-root jss38 MuiGrid-item']")
    public List<WebElement> texts;

    @FindBy(xpath = "//*[@id=\"issuer-banner-id\"]/div/div/div/div/div[1]/div[4]")
    public WebElement text2;

    @FindBy(xpath = "//*[@id=\"issuer-banner-id\"]/div/div/div/div/div[1]/div[5]")
    public WebElement text3;

    @FindBy(xpath = "//span/div[.='Contact Us']")
    public WebElement contactUsButton;

    @FindBy(xpath = "//header/div[@id='issuer-banner-id']")
    public WebElement bannerTop;

    @FindBy(xpath = "//img[@src='/static/media/esg logo.a87fabc9.svg']")
    public WebElement footer;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-root MuiGrid-container MuiGrid-spacing')]/div/div/div[1]")
    public List<WebElement> subHeaders;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-root MuiGrid-container MuiGrid-spacing')]/div")
    public List<WebElement> subHeadersDIVs;

    @FindBy(xpath = "//div/main/div/div/div/div[1]/div/div[1]")
    public WebElement midTopHeader;

    @FindBy(xpath = "//*[@id=\"div-mainlayout\"]/div[2]/div/main/div/div/div/div[3]/div[1]")
    public WebElement assessmentFrameworkHeader;

    @FindBy(xpath = "//div[4]/div/div/div/span/div/table/thead")
    public List<WebElement> subCategories;

    @FindBy(xpath = "//td[@class='MuiTableCell-root MuiTableCell-body jss120 MuiTableCell-alignLeft']")
    public List<WebElement> weights;

    @FindBy(xpath = "//div[@class='MuiGrid-root jss100 MuiGrid-item']")
    public List<WebElement> middleBodyTexts;

    //Subcategories
    @FindBy(xpath = "//div[@role=\"dialog\"]")
    public WebElement modalDialog;

    @FindBy(xpath = "//div[contains(@class,'MuiDialogTitle')]//div/div/div[1]")
    public WebElement modalDialogTitle;

    @FindBy(xpath = "//div[contains(@class,'MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 ')]")
    public List<WebElement> ModalItems;

    @FindBy(xpath = " //div[contains(@class,'MuiDialogTitle')]//div/div/div//following-sibling::div")
    public WebElement sectorDescriptorElement;

    // works for both P2 and P3
    @FindBy(xpath = "//button[@id='missing-docs-button']")
    public WebElement addMissingdDocumentsButton;

    @FindBy(xpath = "//button[@id='contact-button']")
    public WebElement issuerContactUsButton;

    @FindBy(xpath = "//div[@role='dialog']")
    public WebElement addMissingdDocumentsPopUp;

    @FindBy(xpath = "//li[normalize-space()='Document Information']")
    public WebElement addMissingdDocumentsPopUpHeader;

    @FindBy(xpath = "//input[@id='name']")
    public WebElement PopUpWindowURL;

    @FindBy(xpath = "//button//div[contains(text(),'Add a URL')]")
    public WebElement buttonAddURL;

    @FindBy(xpath = "//p[@id='name-helper-text']")
    public WebElement wrongURLMessage;

    @FindBy(xpath = "//div[@role='button']")
    public WebElement DocumentType;

    @FindBy(xpath = "//ul[@role='listbox']")
    public WebElement listofDocumentType;

    @FindBy(xpath = "//div[contains(text(),'Assigned Categories')]/../div/div/div")
    public List<WebElement> assignedCategories;

    @FindBy(xpath = "//div[contains(text(),'Assigned Categories')]/../div/div/div/div/label")
    public List<WebElement> Categories;


    @FindBy(xpath = "//p[@id='name-helper-text']")
    public WebElement errorMessage;

    @FindBy(xpath = "//div[@role='dialog']/div[@class='MuiToolbar-root MuiToolbar-regular']/div/*[name()='svg']")
    public WebElement popupCloseButton;

    @FindBy(xpath = "//div[@role='none presentation']")
    public WebElement areaOutOfModal;

    @FindBy(xpath = "//h2[normalize-space()='Unsaved changes']")
    public WebElement headerUnseavedChanges;

    @FindBy(xpath = "//button[normalize-space()='Continue without saving']")
    public WebElement buttonContinueWithoutSaving;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    public WebElement buttonCancel;

    @FindBy(xpath = "//button[contains(@type,'button')][normalize-space()='Delete'][1]")
    public WebElement buttonUrlDelete;

    @FindBy(xpath = "//h2[normalize-space()='Delete Document']")
    public WebElement headerDeleteDocument;

    @FindBy(xpath = "//button[normalize-space()='No, keep this document']")
    public WebElement buttonNokeepThisDocument;

    @FindBy(xpath = "//button[normalize-space()='Yes, delete this document']")
    public WebElement buttonYesDeleteThisDocument;

    @FindBy(xpath = "//div[text()='No URLs']")
    public WebElement divNoUrl;

    @FindBy(xpath = "//button[@id='upload-button']")
    public WebElement buttonUpload;

    @FindBy(xpath = "//div[@role='dialog'][contains(@class,'MuiPaper-root MuiDialog-paper jss73 MuiDialog-" +
            "paperScrollPaper MuiDialog-paperWidthSm MuiPaper-elevation24 MuiPaper-rounded')]")
    public WebElement dialogBoxtoConfirmSave;

    @FindBy(xpath = "//h2[normalize-space()='Are you sure?']")
    public WebElement headerAreYouSureInDialogBoxtoConfirmSave;

    @FindBy(xpath = "//p[contains(text(),'The following URL needs to')]")
    public WebElement paraInDialogBoxtoConfirmSave;

    @FindBy(xpath = "//button[normalize-space()='OK']")
    public WebElement buttonOkInDialogBoxtoConfirmSave;

    @FindBy(xpath = "//div[@class='MuiAlert-message']")
    public WebElement alertMessage;


    // Source documents

    @FindBy(xpath = "//div[normalize-space()='All Source Documents']")
    public WebElement headingSourceDocuments;

    @FindBy(xpath = "//div[contains(@id,'link-document')]")
    public List<WebElement> linkDocuments;

    @FindBy(xpath = "//a[normalize-space()='See Methodology Guide']")
    public WebElement linkSeeMethodologyGuide;

    @FindBy(xpath = "//a[normalize-space()='See Controversy Methodology']")
    public WebElement linkSeeSeeControversyMethodology;

    @FindBy(xpath = "//a[normalize-space()='See Subcategory Definitions']")
    public WebElement linkSeeSubcategoryDefinitions;


    @FindBy(xpath = "//a[normalize-space()='See ESG Metric Definitions']")
    public WebElement linkSeeSeeESGMetricDefinitions;

    @FindBy(xpath = "//a[normalize-space()='See FAQ']")
    public WebElement linkSeeFAQ;

    @FindBy(xpath = "//div[contains(text(),'Proposed ESG Score Methodology for ')]")
    public WebElement header;

    @FindBy(xpath = "//div[normalize-space()='Assessment Framework']//following-sibling::div")
    public WebElement paraAssessmentFramewok;

    @FindBy(xpath = "(//div[@class='MuiToolbar-root MuiToolbar-regular'])[2]/div/div[1]/div")
    public List<WebElement> banner;

    @FindBy(xpath = "//table/tbody/tr/td[2]")
    public List<WebElement> weightColumn;

    @FindBy(xpath = "//ul[@class='MuiList-root MuiList-dense']/li/div")
    public List<WebElement> liItemsinBanner;

    @FindBy(xpath = "//div[normalize-space()='Scoring Methodology']")
    public WebElement ScoringMethodology;


    @FindBy(xpath = "//div[normalize-space()='Scoring Methodology']/following-sibling::div")
    public WebElement ScoringMethodologyText;

    @FindBy(xpath = "//div[@id='sourceDocErrors']")
    public WebElement sourceDocErrors;

    //esgSubCategory
    @FindBy(xpath = "//span[normalize-space()='ESG Materiality']")
    public WebElement mainDiv_ESGSubCategory;

    @FindBy(xpath = "//div/p[text()='ESG Score Subcategories']/../following-sibling::div[2]/div/button")
    public List<WebElement> esgsubCategoryToggleButtons;

    @FindBy(xpath = "//div/p[text()='ESG Score Subcategories']/../../div[4]/div")
    public List<WebElement> esgSubCategoriesTableDivs;

    @FindBy(xpath = "//div[@role='dialog']")
    public WebElement EsgSubCategoriesModel;

    @FindBy(xpath = "//a[@id='logout-header-link-id']")
    public WebElement logout;

    //P3 Page
    @FindBy(xpath = "//span[normalize-space()='Region: United States']")
    public WebElement P3PageHeader;

    @FindBy(xpath = "(//div)[21]")
    public WebElement P3PageHeaderMainDiv;

    @FindBy(xpath = "(//div)[21]/div[2]/span")
    public List<WebElement> P3_HeaderIdentifilerList;

    @FindBy(xpath = "//button[@id='contact-button']")
    public WebElement P3ContactUSButton;

    //Summary widget
    @FindBy(xpath = "(//div[@class='MuiGrid-root MuiGrid-item'])[2]")
    public WebElement summaryWidget;

    @FindBy(xpath = "(//div[@class='MuiGrid-root MuiGrid-item'])[2]//div[text()='ESG Score']")
    public WebElement ESGScore;

 /*   @FindBy(xpath = "(//div[@class='MuiGrid-root MuiGrid-item'])[2]/div/div/div[2]/div")
    public WebElement EsgScoreRange;*/

    @FindBy(xpath = "(//div[@class='MuiGrid-root MuiGrid-item'])[2]//div[contains(text(),'Updated on')]")
    public WebElement EsgScoreRange;
    @FindBy(xpath = "(//div[@id='sector_comparison_chart_box'])")
    public WebElement NoSectorComparisionChart;

    @FindBy(xpath = "//div[normalize-space()='All Source Documents']")
    public WebElement P3headingSourceDocuments;


    @FindBy(xpath = "//button[@id='missing-docs-button']")
    public WebElement missingDocumentButton;

    @FindBy(xpath = "//div[@class='MuiFormControl-root']//div[@role='button']")
    public WebElement SelectDisclosureDiv;

    @FindBy(xpath = "//label[normalize-space()='Select disclosure year']")
    public WebElement SelectDisclosureLabel;

    @FindBy(xpath = "//ul[@role='listbox']/li")
    public List<WebElement> SelectDisclosureList;

    @FindBy(xpath = "(//*[name()='svg'][@class='MuiSvgIcon-root'])")
    public List<WebElement> svgCopyURLbutton;

    @FindBy(xpath = "//div[text()='Controversies']")
    public WebElement Controversies;

    @FindBy(xpath = "//div[text()='Controversies']/..//table")
    public WebElement ControversiesTable;

    @FindBy(xpath = "//div[contains(text(),'RESPONSIVENESS UPDATE')]/../..")
    public WebElement ControversiesPopup;

    @FindBy(xpath = "//span[@class='close']//*[name()='svg']")
    public WebElement ControversiesPopupCloseButton;


    @FindBy(xpath = "//button[@id='close-button']")
    public WebElement P3SectorPageCloseButton;

    @FindBy(xpath = "//div[@class='MuiToolbar-root MuiToolbar-regular']//div[2]//*[name()='svg']")
    public WebElement P3SectorPageCloseAddMissingDocument;

    @FindBy(xpath = "//span[@class='MuiIconButton-label']")
    public WebElement CloseAddMissingDocumentMessage;

    @FindBy(xpath = "//span[contains(text(),'Overall Disclosure Ratio')]")
    public WebElement P3OverallDisclosureRatio;

    @FindBy(xpath = "//button[@heap_perfchart_id='Materiality']")
    public static WebElement esgMaterialityTab;

    @FindBy(xpath = "//section//div/p")
    public List<WebElement> esgMaterialityColumns;

    @FindBy(xpath = "//section//li/section/span[1]")
    public List<WebElement> esgSections;


    @FindBy(xpath = "//div[@id='cardInfo_box']/div[text()='Overall ESG Score']")
    public WebElement overallEsgScoreWidget;


    public boolean verifyFooter() {
        List<WebElement> paragraphs = Driver.getDriver().findElements(xpath("//p"));
        for (WebElement element : paragraphs) {
            if (element.getText().equals("Moody's ESG360 2021"))
                return true;
        }
        return false;
    }

    public boolean verifyHeader() {
        WebElement header = Driver.getDriver().findElement(xpath("//div[contains(text(),'Source Documents')]"));
        return wait.until(ExpectedConditions.visibilityOf(header)).isDisplayed();
    }

    public boolean verifyDocument() {
        boolean noDocument = Driver.getDriver().findElements(xpath("//*[contains(text(),'No source documents available.')]")).size() > 0;
        if (noDocument) {
            logout.click();
            throw new SkipException("No source documents available");
        }
        try {

            WebDriver driver = Driver.getDriver();
            List<WebElement> documentLink = Driver.getDriver().findElements(xpath("//*[contains(@id,\"link-document\")]"));
            System.out.println("documentLink.size() = " + documentLink.size());
            BrowserUtils.scrollTo(documentLink.get(0));
            System.out.println("Scrooled");
            Actions actions = new Actions(Driver.getDriver());
            for (WebElement link : documentLink) {
                System.out.println("link.getText() = " + link.getText());
                actions.keyDown(Keys.COMMAND).click(link).keyUp(Keys.COMMAND).build().perform();
                System.out.println("Clicked performed");
                Set<String> handles = Driver.getDriver().getWindowHandles();
                Iterator<String> it = handles.iterator();
                String parent = it.next();
                String child = it.next();
                Driver.getDriver().switchTo().window(child);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String url = Driver.getDriver().getCurrentUrl();
                driver.close();
                driver.switchTo().window(parent);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyDriverDrillDown() {
        BrowserUtils.wait(10);
        List<WebElement> links = Driver.getDriver().findElements(xpath("//tr"));
        JavascriptExecutor executor = (JavascriptExecutor) Driver.getDriver();
        executor.executeScript("arguments[0].click();", links.get(10));
        BrowserUtils.wait(5);
        WebElement popup = Driver.getDriver().findElement(By.cssSelector("div[class='MuiDialog-container MuiDialog-scrollPaper"));
        wait.until(ExpectedConditions.visibilityOf(popup)).isDisplayed();
        return true;
    }

    public void checkPositioninSectorCurveChart() {
        List<String> gradePositions = Arrays.asList(new String[]{"0", "30", "50", "60"});
        List<String> xAxisLables = Arrays.asList(new String[]{"Weak", "Limited", "Robust", "A2"});
        List<String> Legends = Arrays.asList(new String[]{"I Sector", "I Entity"});

        for (int i = 0; i < xAxisLables.size(); i++) {
            Assert.assertTrue(chartLables.get(0).findElements(By.xpath("*[local-name()='text']")).get(i).getText().equals(xAxisLables.get(i).toString()));
        }

        for (int i = 0; i < gradePositions.size(); i++) {
            Assert.assertTrue(chartLables.get(1).findElements(By.xpath("*[local-name()='text']")).get(i).getText().equals(gradePositions.get(i).toString()));
        }

        Assert.assertTrue(plotLineLabel.stream().filter(e -> e.getText().equals(Legends.get(0).toString())).count() > 0);
        Assert.assertTrue(plotLineLabel.stream().filter(e -> e.getText().equals(Legends.get(1).toString())).count() > 0);

        WebElement sectorLine = Driver.getDriver().findElement(By.xpath("//*[name()='tspan'][contains(text(),'Sector')]//*"));
        WebElement entityLine = Driver.getDriver().findElement(By.xpath("//*[name()='tspan'][contains(text(),'Entity')]//*"));
        Assert.assertTrue(Color.fromString(sectorLine.getCssValue("stroke")).asHex().equalsIgnoreCase("#FFA463"));
        Assert.assertTrue(Color.fromString(entityLine.getCssValue("stroke")).asHex().equalsIgnoreCase("#1F8CFF"));

    }

    // ESG content to be de-scoped , remove. check with furkan ???
    public boolean verifyMidTexts() {
        String[] expectedTexts = {"Community Bank System, Inc. is assessed as part of the Banks industry.", "Our Banks industry framework is composed of categories and sub-categories listed below. " +
                "Each sub-category has been assigned a weight from 0-3 depending on their level of materiality. " +
                "Sub-categories identified as 3 will contribute most significantly to the overall score. " +
                "Sub-categories identified as 1 have a lower level of materiality but still, " +
                "contribute to the overall score of a company. More information on " +
                "Moody’s ESG materiality methodology can be found in the methodology guide.",
                "To assess a company on the different ESG sub-categories, we assess three types of pillars. This approach provides a 360-degree view on how companies are managing associated risks and opportunities: \n" +
                        "\n" +
                        " 1. Leadership pillars focus on the policy commitments and quantitative targets that have been set by an organization \n" +
                        "\n" +
                        " 2. Implementation pillars examine the activities that are being undertaken by an organization to make good on those commitments \n" +
                        "\n" +
                        " 3. Result pillars look at defined key performance pillars and their trends as well as the exposure of a company to controversies on the driver in question",
                "Only public information is used in the development Moody’s ESG Scores. " +
                        "This will include self-reported information from the companies that are" +
                        " assessed as well as information from third party specialist sources. " +
                        "All sources of information that have been used to develop a score are visible in an ESG Scorecard."};
//        EntityPageAPIController controller = new EntityPageAPIController();
//        Header header = controller.getHeader("000646014").as(Header[].class)[0];


        String[] actualTexts = new String[middleBodyTexts.size()];
        BrowserUtils.wait(1);
        //BrowserUtils.waitForVisibility(middleBodyTexts.get(3), 3);
        for (int i = 0; i < middleBodyTexts.size(); i++) {
            //BrowserUtils.waitForVisibility(middleBodyTexts.get(i), 3);
            // BrowserUtils.wait(1);
            actualTexts[i] = middleBodyTexts.get(i).getText();
        }

        return Arrays.equals(actualTexts, expectedTexts);

    }

    public boolean verifySubHeadersDetails() {
        String[] expectedSubHeaders = {"Sector Allocation", "Assessment Framework", "Questioning Framework", "Sources of Information", "Scoring Methodology"};
        String[] actualSubHeaders = new String[subHeaders.size()];

        for (int i = 0; i < subHeaders.size(); i++) {
            System.out.println("#### " + subHeaders.get(i).getText());
            BrowserUtils.waitForVisibility(subHeaders.get(i), 1);
            actualSubHeaders[i] = subHeaders.get(i).getText();
            // BrowserUtils.wait(1);
        }
        for (int j = 0; j < subHeadersDIVs.size(); j++) {
            List<WebElement> a = subHeadersDIVs.get(j).findElements(By.xpath("div"));
            if (j == 0) {
                assertTestCase.assertTrue(a.size() == 3);
            } else {
                assertTestCase.assertTrue(a.size() == 2);
            }

        }


        return Arrays.equals(expectedSubHeaders, actualSubHeaders);
    }
// ESG content to be de-scoped , remove. check with furkan ???

    public boolean verifyMidTopHeaderDetails() {
        String expectedPartOfHeader = "Proposed ESG Score Methodology";
        String actualPartOfHeaderHeader = midTopHeader.getText();
        return actualPartOfHeaderHeader.contains(expectedPartOfHeader);
    }

    public boolean verifyAssessmentFrameworkHeaderDetails() {
        String actualAssessmentFrameworkHeader = "Assessment Framework";
        wait.until(ExpectedConditions.visibilityOf(assessmentFrameworkHeader));
        String expectedAssessmentFrameworkHeader = assessmentFrameworkHeader.getText();
        System.out.println("$$$$ " + expectedAssessmentFrameworkHeader);
        return expectedAssessmentFrameworkHeader.contains(actualAssessmentFrameworkHeader);
    }

    public boolean verifySubCategoriesDetails() {
        String[] expectedSubCategories = {"Environmental", "Social", "Governance"};
        String[] actualSubHeadersubCategories = new String[subCategories.size()];

        for (int i = 0; i < subCategories.size(); i++) {
            System.out.println("%%%%%% " + subCategories.get(i).getText().split(" ")[0]);
            actualSubHeadersubCategories[i] = subCategories.get(i).getText().split(" ")[0];
        }
        System.out.println(Arrays.toString(expectedSubCategories));
        System.out.println(Arrays.toString(actualSubHeadersubCategories));
        return Arrays.equals(expectedSubCategories, actualSubHeadersubCategories);
    }

    public boolean verifyNumericValues() {
        List<String> weightsList = Arrays.asList("Low", "Moderate", "High", "Very High");
        for (WebElement weight : weights) {
            System.out.println("weight.getText() = " + weight.getText());
            assertTestCase.assertTrue(weightsList.contains(weight.getText()), "Weight Validation");
        }
        return true;
    }

    public boolean verifyFooterIsDisplayed() {
        BrowserUtils.scrollTo(footer);
        System.out.println(footer.getLocation());
        return footer.isDisplayed();
    }

    public boolean verifyBannerScrollsAsPartOfThePage1() {

        BrowserUtils.scrollTo(footer);
        return bannerTop.isDisplayed();
    }

    public boolean verifyMoodysHeader() {
        String expectedHeader = "Moody's ESG360";
        String actualHeader = Driver.getDriver().getTitle();
        return expectedHeader.equals(actualHeader);
    }

    public boolean verifyTopBodyOfText() {
        // ESG content to be de-scoped , remove. check with furkan ???

        String bannerHeader = "Welcome to Moody’s ESG360: the portal connecting you to the Moody’s ESG Assessment process.";
        String bannerText = "We will be conducting an ESG Assessment";
        String styleColor = highlightedText.getAttribute("style");
        String expectedColor = "color: rgb(31, 140, 255);";
       /* List<String> midLines = java.util.Arrays.asList(
                "• Understand the methodology used to assess your company",
                "• Review the information we have collected that informs our assessment",
                "• Upload URL links when your company updates its policies",
                "• View your company’s ESG Assessment once completed");

        for (int i = 0; i < midLines.size(); i++) {
            Assert.assertEquals(texts.get(i).getText(), midLines.get(i));
        }*/
        return bodyTestOne.getText().contains(bannerHeader) &&
                bodyTestTwo.getText().contains(bannerText) &&
                styleColor.equals(expectedColor);
    }

    // ESG content to be de-scoped , remove. check with furkan ???
    public boolean verifyMidBodyOfText() {
        boolean result = false;
        List<String> expectedMidLines = Arrays.asList(
                "• Understand the methodology used to assess your company",
                "• Review the information we have collected that informs our assessment",
                "• Upload URL links when your company updates its policies",
                "• View your company’s ESG Assessment once completed");
        List<String> actualMidLines = new ArrayList<>();
        for (WebElement text : texts) {
            actualMidLines.add(text.getText());
        }
        return Objects.equals(expectedMidLines, actualMidLines);
    }

    public boolean verifyBottomBodyOfText() {
        String expected1 = "You will receive an email to review information when the assessment proceeds to the next stage.";
        String expected2 = "Please contact us if you have questions.";
        return text2.getText().equals(expected1) && text3.getText().equals(expected2);
    }

    public boolean verifyContactButtonIsVisible() {
        return contactUsButton.isDisplayed();
    }

    public boolean verifyContactButtonIsClickable() {
        try {
            BrowserUtils.waitForClickablility(contactUsButton, 5);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public boolean verifySectorModalPopUpForSubCategories(String Subcategories) {
        boolean ModalPopUpCheck = false;
        List<WebElement> SubcategoriesSectors = Driver.getDriver().findElements(By.xpath("//table/thead//th[text()='" + Subcategories + "']//..//..//../tbody//tr"));
        Actions action = new Actions(Driver.getDriver());
        //boolean checkData = Driver.getDriver().findElements(By.xpath("//table/thead//th[text()='" + Subcategories + "']//..//..//../tbody//tr[0]//td[1]")).size() > 0;
        //System.out.println("checkData = " + checkData);

        for (int i = 1; i <= SubcategoriesSectors.size(); i++) {
            WebElement SubcategoriesSector = Driver.getDriver().findElement(By.xpath("//table/thead//th[text()='" + Subcategories + "']//..//..//../tbody//tr[" + i + "]//td[1]"));
            String ModalPopupTitle = SubcategoriesSector.getText().split("/")[1].trim();
            wait.until(ExpectedConditions.visibilityOf(SubcategoriesSector));
            action.moveToElement(SubcategoriesSector).click().perform();
            BrowserUtils.wait(2);
            ModalPopUpCheck = (modalDialog.isDisplayed() && modalDialogTitle.getText().equals(ModalPopupTitle))? true:false ;
            BrowserUtils.ActionKeyPress(Keys.ESCAPE);
            Driver.getDriver().navigate().refresh();
            if (ModalPopUpCheck == false) break;
        }
        return ModalPopUpCheck;

}

    public void ClickOnaddMissingDocuments() {
        System.out.println("addMissingdDocumentsButton.isDisplayed() = " + addMissingdDocumentsButton.isDisplayed());
        assertTestCase.assertTrue(addMissingdDocumentsButton.getText().equals("Add Information"), "Validating Add Information Button text");
        BrowserUtils.waitForVisibility(addMissingdDocumentsButton, 5).click();
    }

    public void validateopupWindowOpenStatus() {
        wait.until(ExpectedConditions.visibilityOf(addMissingdDocumentsPopUp)).isDisplayed();
        assertTestCase.assertTrue(addMissingdDocumentsPopUp.isDisplayed(), "Verify if Add missing pop up is displayed");
        assertTestCase.assertTrue(addMissingdDocumentsPopUpHeader.isDisplayed(), "Verify if Header in add missing pop up is displayed");

    }


    public void addURL(String url, Boolean disclosureRatioAvailable) {
        PopUpWindowURL.sendKeys(url);
        System.out.println(DocumentType.getText());
        BrowserUtils.wait(5);
        DocumentType.click();
        List<WebElement> options = listofDocumentType.findElements(By.tagName("li"));
        for (WebElement option : options) {
            if (option.getText().equals("Environmental Technical Report")) {
                option.click();
                break;
            }
        }
        Boolean selectorDiv = false;
        try {
            if (disclosureRatioAvailable) selectorDiv = SelectDisclosureDiv.isDisplayed();
            else selectorDiv = disclosureRatioAvailable;
        } catch (Exception e) {
            selectorDiv = false;

        }
        if (selectorDiv) {
            SelectDisclosureDiv.click();
            SelectDisclosureList.get(1).click();
        }
        BrowserUtils.wait(1);
        buttonAddURL.click();


    }


    public void validateAssignedCategories() {
        List<String> listofAssignedcategories = new ArrayList(Arrays.asList(
                "Environmental",
                "Waste and Pollution / Air Emissions",
                "Natural Capital / Water Management",
                "Environmental Strategy / Environmental Protection",
                "Natural Capital / Biodiversity",
                "Climate / Physical Risks",
                "Waste and Pollution / Material Flows",
                "Climate / Transition Risks",
                "Social",
                "Communities / Societal Impacts",
                "Customers / Product Safety",
                "Human Capital / Wages & Hours",
                "Human Capital / Health & Safety",
                "Human Rights / Fundamental Human Rights",
                "Human Rights / Modern Slavery",
                "Human Capital / Labour Rights & Relations",
                "Human Capital / Career Development",
                "Customers / Customer Engagement",
                "Communities / Social & Economic Development",
                "Communities / Responsible Tax",
                "Human Capital / Reorganizations",
                "Human Rights / Diversity & Inclusion",
                "Governance",
                "Business Conduct / Lobbying",
                "Business Conduct / Business Ethics",
                "Corporate Governance / Stakeholder Relations",
                "Supply Chain / Supplier Relations",
                "Business Conduct / Competition",
                "Corporate Governance / Internal Controls & Risk Management",
                "Corporate Governance / Executive Remuneration",
                "Innovation / Cyber & Technological Risks",
                "Corporate Governance / Board",
                "Supply Chain / Sustainable Sourcing"
        ));


        wait.until(ExpectedConditions.visibilityOf(assignedCategories.get(0))).isDisplayed();

        List<String> data = new ArrayList<String>();
        for (WebElement e : assignedCategories) {
            System.out.println(e.getText());
            List<WebElement> labels = e.findElements(By.tagName("label"));
            for (WebElement label : labels) {
                data.add(label.getText());
            }
        }
        System.out.println("data = " + data);
        assertTestCase.assertEquals(listofAssignedcategories.size(), data.size(), "Validate if all categories are available and displayed");


    }

    public void validateWrongURL() {
        String wrongURL = "xyz";
        PopUpWindowURL.sendKeys(wrongURL);
        assertTestCase.assertTrue(wrongURLMessage.getText().equals("URL invalid"), "Validate the URL");
        for (int i = 0; i < wrongURL.length(); i++) {
            PopUpWindowURL.sendKeys(Keys.BACK_SPACE);
        }
    }

    public void validateURL() {
        assertTestCase.assertTrue(wrongURLMessage.getText().equals("URL should be valid and publicly accessible"), "Validate the URL");
    }

    public void validatePopupClose() {
        popupCloseButton.click();
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(headerUnseavedChanges)).isDisplayed(), "Validate if Header is displayed in Unsaved error dialog box ");
        buttonCancel.click();
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(addMissingdDocumentsPopUp)).isDisplayed(), "Validate if add missing document popup window is displayed");
        popupCloseButton.click();
        buttonContinueWithoutSaving.click();
        assertTestCase.assertTrue(addMissingdDocumentsButton.isDisplayed(), "Validate if Addmissing button is avaialble");
    }

    public void validatePopupCloseByESC() {
        pressESCKey();
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(headerUnseavedChanges)).isDisplayed(), "Validate if Header is displayed in Unsaved error dialog box ");
        buttonCancel.click();
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(addMissingdDocumentsPopUp)).isDisplayed(), "Validate if add missing document popup window is displayed");
        BrowserUtils.wait(2);
        clickCoordination(10, 10);
        buttonContinueWithoutSaving.click();
        BrowserUtils.wait(2);
        assertTestCase.assertTrue(addMissingdDocumentsButton.isDisplayed(), "Validate if Addmissing button is avaialble");
    }

    public void validateURLDelete(String url) {
        buttonUrlDelete.click();
        assertTestCase.assertTrue(BrowserUtils.waitForVisibility(headerDeleteDocument, 5).isDisplayed(), "Validate if Header is available in Delete document window");
        buttonNokeepThisDocument.click();
        WebElement gridItem = Driver.getDriver().findElement(By.xpath("//div[normalize-space()='" + url + "']"));
        assertTestCase.assertTrue(gridItem.isDisplayed(), "Validate if added URL is still available in list");
        buttonUrlDelete.click();
        buttonYesDeleteThisDocument.click();
        assertTestCase.assertTrue(divNoUrl.isDisplayed(), "Validate that added url is successfully deleted");
    }

    public void validateSaveWithoutAssignedCategories() {
        buttonUpload.click();
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(buttonOkInDialogBoxtoConfirmSave)).isDisplayed(), "if save confirmation dialog has opened");
        assertTestCase.assertTrue(headerAreYouSureInDialogBoxtoConfirmSave.isDisplayed(), "Validae if 'Are You Sure' header message is displayed in save confirmation dialog box");
        assertTestCase.assertTrue(paraInDialogBoxtoConfirmSave.isDisplayed(), "Validae if text message is displayed in save confirmation dialog box");
        buttonOkInDialogBoxtoConfirmSave.click();
        assertTestCase.assertTrue(addMissingdDocumentsPopUp.isDisplayed(), "Validate if add missing document popup window is displayed");

    }

    public void validatePageNoBox() {
        for (WebElement e : Categories) {
            e.click();
            assertTestCase.assertTrue(e.getText().contains("Page #"), "Validating if Page # lable is available");
            WebElement inputBox = Driver.getDriver().findElement(By.xpath("//input[@id='" + e.getText().split("\n")[0] + "']"));
            assertTestCase.assertTrue(inputBox.isDisplayed(), "Validating if Page# input box is visible");
            Random rand = new Random();
            inputBox.sendKeys(String.valueOf(rand.nextInt((10 - 1) + 1) + 1));
        }
    }

    public void saveMissingDocuments() {
        // WebElement category = assignedCategories.get(0).findElement(By.xpath("//span[text()='Waste and Pollution / Material Flows']"));
        //  category.click();
        buttonUpload.click();
        wait.until(ExpectedConditions.visibilityOf(alertMessage)).isDisplayed();
        assertTestCase.assertTrue(alertMessage.getText().equals("Documents saved"), "Validate if document is saved sucessfully");
    }

    public void validateSourceDocumentWidgetIsAvailable() {
        boolean noDocument = Driver.getDriver().findElements(xpath("//*[contains(text(),'No source documents available.')]")).size() > 0;
        if (noDocument) {
            logout.click();
            throw new SkipException("No source documents available.");
        }
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(headingSourceDocuments)).isDisplayed(),
                "Validate if 'header 2020 Source Documents' is displayed");
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(linkDocuments.get(0))).isDisplayed(), "Validate if document links are available");

    }

    public void validateSourceDocumentlinkOpenInNewTab(String whatToValidate) {
        wait.until(ExpectedConditions.visibilityOf(linkDocuments.get(0))).isDisplayed();
        for (WebElement e : linkDocuments) {
            validateLinksOpenedInNewTab(e, whatToValidate);
        }
    }

    public void validateLinksOpenedInNewTab(WebElement element, String whatToValidate) {
        WebDriver driver = Driver.getDriver();
        wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        String mainWindowHandler = driver.getWindowHandle();
        BrowserUtils.waitForVisibility(element, 5).click();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandler.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String url = driver.getCurrentUrl();
                System.out.println("actual url   = " + url);
                System.out.println("Expected url = " + whatToValidate);
                assertTestCase.assertTrue(driver.getCurrentUrl().contains(whatToValidate));
                driver.close();
                BrowserUtils.wait(2);
                driver.switchTo().window(mainWindowHandler);
            }
        }
    }

    public void validateP3LinksOpenedInNewTab(WebElement element, String whatToValidate) {
        WebDriver driver = Driver.getDriver();

        String mainWindowHandler = driver.getWindowHandle();

        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandler.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                String url = driver.getCurrentUrl();
                if (url.contains("sector")) {
                    driver = Driver.getDriver();
                    wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
                    String mainWindowHandler1 = driver.getWindowHandle();
                    element.click();
                    allWindowHandles = driver.getWindowHandles();
                    Iterator<String> iterator1 = allWindowHandles.iterator();
                    while (iterator1.hasNext()) {
                        ChildWindow = iterator1.next();
                        if (!mainWindowHandler.equalsIgnoreCase(ChildWindow) && !mainWindowHandler1.equalsIgnoreCase(ChildWindow)) {
                            driver.switchTo().window(ChildWindow);
                            url = driver.getCurrentUrl();
                            System.out.println("actual url   = " + url);
                            System.out.println("Expected url = " + whatToValidate);
                            assertTestCase.assertTrue(driver.getCurrentUrl().contains(whatToValidate));
                            driver.close();
                            BrowserUtils.wait(2);
                            driver.switchTo().window(mainWindowHandler1);
                            driver.close();
                            BrowserUtils.wait(2);
                            driver.switchTo().window(mainWindowHandler);
                        }
                    }
                }

            }
        }
    }


    public void validateIssuerPageBanner() {
        assertTestCase.assertTrue(banner.get(0).getText().equals("Welcome to Moody’s ESG360: the portal connecting you to the Moody’s ESG Assessment process."), "Verify the Welcome Text in Banner");
        //Validating if Add missing button is next to ContactUS
        int ContactusYLocation = issuerContactUsButton.getLocation().y;
        int ContactusXLocation = issuerContactUsButton.getLocation().x;
        int addMissingdDocumentsButtonYLocation = addMissingdDocumentsButton.getLocation().y;
        int addMissingdDocumentsButtonXLocation = addMissingdDocumentsButton.getLocation().x;
        int ContactUSButtonWidth = issuerContactUsButton.getSize().getWidth();
        int marginBetweenButtons = 10;
        assertTestCase.assertTrue(ContactusYLocation == addMissingdDocumentsButtonYLocation, "Validating if Add missing button is next to ContactUS");
        assertTestCase.assertTrue(ContactusXLocation + marginBetweenButtons + ContactUSButtonWidth == addMissingdDocumentsButtonXLocation, "Validating if Add missing button is next to ContactUS");

        assertTestCase.assertTrue(Color.fromString(addMissingdDocumentsButton.getCssValue("background-color")).asHex().equals("#1f8cff"),
                "Validating add missing button background color");


        assertTestCase.assertTrue(Color.fromString(issuerContactUsButton.getCssValue("background-color")).asHex().equals("#ffffff"),
                "Validating Contact Us button background color");

        assertTestCase.assertTrue(liItemsinBanner.get(0).getText().equals("• Understand the proposed methodology to access your company"), "Validated Bullet points in Banner");
        assertTestCase.assertTrue(liItemsinBanner.get(1).getText().equals("• View data collected and provide missed documents"), "Validated Bullet points in Banner");
        assertTestCase.assertTrue(liItemsinBanner.get(2).getText().equals("• View your company's scorecard"), "Validated Bullet points in Banner");
    }

    public void validateScoringMethodologyStaticText() {
        assertTestCase.assertTrue(ScoringMethodology.getText().equals("Scoring Methodology"), "Validate Grade and Score header");
        assertTestCase.assertEquals(ScoringMethodologyText.getText(), "The scoring outputs of an ESG Assessment comprise two types of scores: \n" +
                "\n" +
                " 1. An Overall ESG Score from 0-100 based on the maturity of the entity’s approach against common global standards and industry practices. \n" +
                "\n" +
                " 2. Our assessment of an entity's managerial system to address defined responsibilities across different subcategories expressed, highest to lowest, as four levels: Advanced, Robust, Limited and Weak.");

    }

    public void validateESGSubcategorieToggleButtons() {

        List<String> toggleButtons = Arrays.asList(new String[]{"All Subcategories", "Environment", "Social", "Governance"});
        assertTestCase.assertTrue(esgsubCategoryToggleButtons.size() == toggleButtons.size(), "Validate toggle button counts");
        for (int i = 0; i < toggleButtons.size(); i++) {
            assertTestCase.assertTrue(esgsubCategoryToggleButtons.get(i).getText().equals(toggleButtons.get(i)), "Validate Toggle button text and order");
            esgsubCategoryToggleButtons.get(i).click();
            BrowserUtils.wait(2);
            assertTestCase.assertTrue(Color.fromString(esgsubCategoryToggleButtons.get(i).getCssValue("background-color")).asHex().equals("#26415e")
                    , "Validate Toggle buttons are clickable and when selected, it is highlighted in dark blue.");

            validateEsgSubCategoriesTableItems(esgsubCategoryToggleButtons.get(i).getText());


        }

    }

    public void validateSummaryWidgetISVAvailable() {
        assertTestCase.assertTrue(ESGScore.isDisplayed(), "Validate if ESG score widget is displayed");


    }
    // ESG content to be de-scoped , remove. check with furkan ???
    public void validateEsgSubCategoriesTableItems(String tab) {
        List<String> sections = Arrays.asList("Very High", "High", "Moderate", "Low");
        List<String> sectors = Arrays.asList("Environmental", "Social", "Governance");
        assertTestCase.assertTrue(esgSubCategoriesTableDivs.size() == sections.size(), "Validate table section counts");
        for (int i = 0; i < sections.size(); i++) {

            WebElement div = esgSubCategoriesTableDivs.get(i);
            if (!div.getText().contains("No information available.")) {
                assertTestCase.assertTrue(div.findElement(By.xpath("div/div[1]")).getText().equals("Weight: " + sections.get(i)), "Validate Table Heading");
                for (WebElement li : div.findElements(By.xpath("div/div[2]/ul/li"))) {
                    WebElement category = li.findElement(By.xpath("div"));
                    WebElement title = li.findElement(By.xpath("div/span[1]"));
                    WebElement subCategory = li.findElement(By.xpath("div/span[2]"));

                    String subCategoryText = subCategory.getText();
                    switch (tab) {
                        case "All Subcategories": {
                            assertTestCase.assertTrue(getMatchingSubstring(subCategoryText.split(" /")[0].trim(), sectors), "Validate sector name");
                            break;
                        }
                        default: {
                            String sector = "";
                            if (tab.equals("Environment")) sector = "Environmental";
                            else sector = tab;
                            assertTestCase.assertTrue(subCategoryText.split(" /")[0].equals(sector), "Validate sector name");
                            break;
                        }
                    }
                    System.out.println("category.getAttribute(\"title\") = " + category.getAttribute("title"));

                    Actions actions = new Actions(Driver.getDriver());
                    actions.moveToElement(subCategory);
                    actions.build().perform();
                    System.out.println("title.getText() = " + title.getText().replaceAll(" ", "_").replaceAll("&", "and"));
                    EntityIssuerESGSubcategoriesDescriptions descriptionClass = new EntityIssuerESGSubcategoriesDescriptions();
                    String expectedDescription = descriptionClass.getDescriptionMap().get(title.getText().replaceAll(" ", "_").replaceAll("&", "and"));
                    System.out.println("expectedDescription = " + expectedDescription);
                    assertTestCase.assertTrue(expectedDescription.trim().equals(category.getAttribute("title").trim()), "Validate mouse hover text");
                }
            }
        }
    }

    private static Boolean getMatchingSubstring(String str, List<String> substrings) {
        if (substrings.stream().filter(str::contains).count() == 1)
            return true;
        else
            return false;

    }


    public void validateP3ContactUSButton() {
        assertTestCase.assertTrue(P3ContactUSButton.isDisplayed(), "Validate if contact us button is available");
        assertTestCase.assertTrue(P3ContactUSButton.getText().equals("Contact Us"), "Validate contact us button text");

    }

    public void IsP3ContactUSButton_Clickable() {
        assertTestCase.assertTrue(BrowserUtils.waitForClickablility(P3ContactUSButton, 2).isEnabled(), "Validate if contact us button is clicakable");
    }


    public void validateESGScoreRatingList() {
        List<String> expectedList = Arrays.asList("a1", "a2", "a3", "b1", "b2", "b3", "c1", "c2", "c3", "e");
        List<WebElement> scoreRanges = EsgScoreRange.findElements(By.xpath("div"));
        assertTestCase.assertTrue(scoreRanges.size() == expectedList.size(), "Validate if range size is as expected");

        for (int i = 0; i < scoreRanges.size(); i++) {
            assertTestCase.assertTrue(scoreRanges.get(i).getText().equals(expectedList.get(i)), "Validate if Score ranges are as expected");
        }

    }
    public void validateEsgScoredateFormat() {
        // String esgScoreDate = EsgScoreRange.findElement(By.xpath("../following-sibling::div")).getText();
        assertTestCase.assertTrue(EsgScoreRange.getText().startsWith("Updated on"));
        assertTestCase.assertTrue((isValidFormat("MMMM dd, yyyy", EsgScoreRange.getText().split("on ")[1].toString())), "Validate date format");
        assertTestCase.assertTrue(Color.fromString(EsgScoreRange.getCssValue("color")).asHex().equals("#ffffff"), "Validate Date text color");
        assertTestCase.assertTrue(Color.fromString(EsgScoreRange.findElement(By.xpath("..")).getCssValue("background-color")).asHex().equals("#26415e"), "Validate Date text background color");
    }

    public void validateHeaderAvailability() {
        BrowserUtils.wait(10);
        WebElement mainDivHeader = wait.until(ExpectedConditions.visibilityOf(P3PageHeaderMainDiv.findElement(By.xpath("div[1]"))));
        assertTestCase.assertTrue(!mainDivHeader.getText().equals(""));

        List<WebElement> IdentifierSpans = P3PageHeaderMainDiv.findElements(By.xpath("div[2]/span/span"));

        System.out.println("IdentifierSpans.size() = " + IdentifierSpans.size());
        List<String> expectedList = Arrays.asList("ISIN", "LEI", "Orbis ID");
        //Size of the identifier changes, If condition applied. In some entities LEI is not available.
        if (IdentifierSpans.size() == 9) {
            for (int i = 0; i < IdentifierSpans.size(); i++) {
                switch (i) {
                    case 0:
                        List<WebElement> innerSpan = IdentifierSpans.get(i).findElements(By.xpath("span"));
                        for (WebElement e : innerSpan) {
                            if (!e.getText().equals("|")) {
                                assertTestCase.assertTrue(expectedList.contains(e.getText().split(":")[0].trim()));
                            }
                        }
                        break;
                    case 6:
                        System.out.println("IdentifierSpans.get(i).getText().split(\":\")[0] " + IdentifierSpans.get(i).getText().split(":")[0]);
                        assertTestCase.assertEquals(IdentifierSpans.get(i).getText().split(":")[0], "Region");
                        break;
                    case 8:
                        System.out.println("IdentifierSpans.get(i).getText().split(\":\")[0] " + IdentifierSpans.get(i).getText());
                        assertTestCase.assertEquals(IdentifierSpans.get(i).getText().split(":")[0], "Industry");
                        validateLinksOpenedInNewTab(IdentifierSpans.get(i), "sector");
                        break;
                }
            }
        } else {
            for (int i = 0; i < IdentifierSpans.size(); i++) {
                switch (i) {
                    case 0:
                        List<WebElement> innerSpan = IdentifierSpans.get(i).findElements(By.xpath("span"));
                        for (WebElement e : innerSpan) {
                            if (!e.getText().equals("|")) {
                                assertTestCase.assertTrue(expectedList.contains(e.getText().split(":")[0].trim()));
                            }
                        }
                        break;
                    case 5:
                        System.out.println("IdentifierSpans.get(i).getText().split(\":\")[0] " + IdentifierSpans.get(i).getText().split(":")[0]);
                        assertTestCase.assertEquals(IdentifierSpans.get(i).getText().split(":")[0], "Region");
                        break;
                    case 7:
                        System.out.println("IdentifierSpans.get(i).getText().split(\":\")[0] " + IdentifierSpans.get(i).getText().split(":")[0]);
                        assertTestCase.assertEquals(IdentifierSpans.get(i).getText().split(":")[0], "Industry");
                        validateLinksOpenedInNewTab(IdentifierSpans.get(i), "sector");
                        break;
                }
            }
        }
    }

    public void validateMissingDocument() {
        WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
        assertTestCase.assertTrue(missingDocumentButton.isDisplayed());
        assertTestCase.assertTrue(missingDocumentButton.getText().equals("Add Missing Documents"), "Validate button text as Add Information");
        missingDocumentButton.click();
        assertTestCase.assertTrue(SelectDisclosureLabel.isDisplayed());
        SelectDisclosureDiv.click();
        assertTestCase.assertTrue(SelectDisclosureList.get(0).getText().equals("2019"));
        String year = SelectDisclosureList.get(1).getText();
        SelectDisclosureList.get(1).click();
        wait.until(ExpectedConditions.visibilityOf(P3SectorPageCloseAddMissingDocument));
        P3SectorPageCloseAddMissingDocument.click();
    }

    public void validatP3eSourceDocumentWidgetIsAvailable() {
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(P3headingSourceDocuments)).isDisplayed(),
                "Validate if 'All Source Documents' is displayed");
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(linkDocuments.get(0))).isDisplayed(), "Validate if document links are available");
    }

    public void validateCopyFunctionlity() {
        wait.until(ExpectedConditions.visibilityOf(linkDocuments.get(0))).isDisplayed();
        for (int i = 0; i < 2; i++) {//linkDocuments.size()
            assertTestCase.assertTrue(svgCopyURLbutton.get(i).isDisplayed(), "Validate if copy button is available");
            svgCopyURLbutton.get(i + 1).click();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            try {
                String data = (String) clipboard.getData(DataFlavor.stringFlavor);
                System.out.println("Data     url =" + data);
                validateLinksOpenedInNewTab(linkDocuments.get(i), data);
            } catch (UnsupportedFlavorException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void validateIfContoversiesHeadingAvailable() {
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(Controversies)).isDisplayed(), "If Controversies heading is available");
    }

    public void validateIfContoversiesTableIsAvailable() {
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(ControversiesTable)).isDisplayed(), "If Controversies Table is available");
    }

    public void validateContoversiesTable() throws ParseException {
        List<WebElement> rows = ControversiesTable.findElements(By.xpath("tbody/tr"));
        Date previousRowDate = null;
        for (WebElement row : rows) {
            String date = row.findElement(By.xpath("td[1]")).getText();
            assertTestCase.assertTrue(isValidFormat("MMMM d, yyyy", date), "Validating if Date format is correct for : " + date);
            Date currentRowDate = new SimpleDateFormat("MMMM d, yyyy").parse(date);
            if (!(previousRowDate == null) && !(currentRowDate.equals(previousRowDate)))
                assertTestCase.assertTrue(currentRowDate.before(previousRowDate), "Validating if latest controversies are on top");
            previousRowDate = currentRowDate;
            //Removing as functionality has changed
           /* WebElement criticaLable = row.findElement(By.xpath("td[2]"));
            if (!criticaLable.getText().equals("")) {
                assertTestCase.assertTrue(criticaLable.getText().equals("Critical"), "Validating if Criticical lable text is correct");
                assertTestCase.assertTrue(Color.fromString(criticaLable.getCssValue("background")).asHex().equals("#b31717"), "Validating Controversy display color when Severity Status is Critical");
            }
            criticaLable.click();*/
            row.click();
            WebElement description = row.findElement(By.xpath("td[3]"));
            WebElement ControversiesPopup = Driver.getDriver().findElement(By.xpath("//div[contains(text(),\"" + description.getText() + "\")]/../.."));

            assertTestCase.assertTrue(ControversiesPopup.isDisplayed(), "Validating if Controvesies popup is opened");
            List<WebElement> paragraphs = ControversiesPopup.findElements(By.xpath("div[2]/p"));
            paragraphs.get(paragraphs.size() - 1).getText().equals("Please contact veconnect@vigeo-eiris.com for any log-in queries.");
            paragraphs.get(paragraphs.size() - 2).getText().equals("To respond to an allegation or controversy, please go to: https://veconnect.vigeo-eiris.com.");
            String firstParagraghText = paragraphs.get(0).getText();
            int startindex = firstParagraghText.indexOf("considered") + 11;
            int endIndex = firstParagraghText.indexOf("based");
            String severity = firstParagraghText.substring(startindex, endIndex).trim();
            ControversiesPopupCloseButton.click();

            if (severity.equals("Critical")) {
                WebElement criticalIcon = row.findElement(By.xpath("td[2]/span/div[@data-testid='critical']/*[name()='svg']"));
                assertTestCase.assertTrue(criticalIcon.isDisplayed());
            }

        }


    }

    public void validateLogoutButton() {
        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(logout)).isDisplayed(), "If Logout button is available");
        logout.click();
/*        LoginPage loginPage = new LoginPage();
        loginPage.entityIssuerLogin();*/
    }

    public void validateScoringMethodology() {
        BrowserUtils.wait(10);
        wait.until(ExpectedConditions.visibilityOf(P3PageHeaderMainDiv)).findElement(By.xpath("div[2]/span[3]")).click();
        BrowserUtils.wait(5);
        WebDriver driver = Driver.getDriver();
        String mainWindowHandler = driver.getWindowHandle();
        Set<String> allWindowHandles = driver.getWindowHandles();
        Iterator<String> iterator = allWindowHandles.iterator();
        while (iterator.hasNext()) {
            String ChildWindow = iterator.next();
            if (!mainWindowHandler.equalsIgnoreCase(ChildWindow)) {
                driver.switchTo().window(ChildWindow);
                validateScoringMethodologyStaticText();
                driver.close();
                driver.switchTo().window(mainWindowHandler);
            }

        }
    }


    public void ValidateOverallDisclosureRatioIsVailableAndValueisNumeric() {

        assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(P3OverallDisclosureRatio)).isDisplayed(), "If Overall Disclosure Ratio is available");
        assertTestCase.assertTrue(NumberUtils.isParsable(P3OverallDisclosureRatio.getText().split(":")[1].split("%")[0].trim()), "Validate if Overall Disclosure Ratio value  is numeric ");

    }

    public void validateESGScoresAsNumericalValues(String score) {
        if (score.equals("ESG Score")) {
            String esgScoreValue = ESGScore.findElement(By.xpath("../following-sibling::div")).getText();
            assertTestCase.assertTrue(Integer.valueOf(esgScoreValue.split("/")[0]) > 0, "Validate that ESG Score is greater than 0");
            assertTestCase.assertTrue(Integer.valueOf(esgScoreValue.split("/")[1]) == 100, "Validate that ESG Score widget has 100");

        } else {

            String esgScoreValue = Driver.getDriver().findElement(By.xpath("(//div[@class='MuiGrid-root MuiGrid-item'])[2]//div[text()='" + score + "']/following-sibling::div")).getText();
            assertTestCase.assertTrue(Integer.valueOf(esgScoreValue.split("/")[0]) > 0, "Validate that ESG Score is greater than 0");
            assertTestCase.assertTrue(Integer.valueOf(esgScoreValue.split("/")[1]) == 100, "Validate that ESG Score widget has 100");
        }


    }
    public void navigateToSectorPage() {
        wait.until(ExpectedConditions.visibilityOf(P3_HeaderIdentifilerList.get(P3_HeaderIdentifilerList.size() - 1))).click();
    }

    public boolean verifyOverallEsgScoreWidget() {
        try {
            wait.until(ExpectedConditions.visibilityOf(overallEsgScoreWidget));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void validateEsgMaterialityLegends() {

        String labelXpath = "//button[@heap_perfchart_id='Materiality']/../../..//div[contains(@class,'MuiPaper-elevation1')]//span";

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[1]")).getText(), "Weak");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[1]")).getAttribute("style"), "background: rgb(221, 88, 29);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[2]")).getText(), "Limited");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[2]")).getAttribute("style"), "background: rgb(232, 149, 28);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[3]")).getText(), "Robust");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[3]")).getAttribute("style"), "background: rgb(234, 197, 80);");

        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + ")[4]")).getText(), "Advanced");
        assertTestCase.assertEquals(Driver.getDriver().findElement(By.xpath("(" + labelXpath + "/div)[4]")).getAttribute("style"), "background: rgb(219, 229, 163);");

        String criticalControversiesXpath = "//button[@heap_perfchart_id='Materiality']/../../..//div[text()='Critical controversies']";
        assertTestCase.assertTrue(Driver.getDriver().findElement(By.xpath(criticalControversiesXpath)).isDisplayed());

    }

    public void selectEsgMaterialityTab() {
        esgMaterialityTab.click();
    }

    public List<String> readEsgMaterialityColumns() {
        List<String> columns = new ArrayList<String>();
        for (WebElement column : esgMaterialityColumns) {
            columns.add(column.getText());
        }
        return columns;
    }

    public boolean verifyMaterialityMatrixYaxisLabels() {
        boolean highScore = Driver.getDriver().findElement(By.xpath("//section/div[text()='Higher Score']")).isDisplayed();
        boolean lowScore = Driver.getDriver().findElement(By.xpath("//section/div[text()='Lower Score']")).isDisplayed();

        return highScore && lowScore;
    }

    public boolean isProvidedFilterClickableInMaterialityMatrixFooter(String filterName) {
        try {
            WebElement element = Driver.getDriver().findElement(By.xpath("//div/a[text()='" + filterName + "']"));
            BrowserUtils.scrollTo(element);
            BrowserUtils.waitForClickablility(element, 30);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isProvidedFilterSelectedByDefaultInMaterialityMatrixFooter(String filterName) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div/a[text()='" + filterName + "']"));
        BrowserUtils.scrollTo(element);
        String classAttribute = element.getAttribute("class");
        System.out.println(classAttribute + " " + classAttribute.split("jss").length);
        return classAttribute.split("jss").length == 4;
    }

    public void selectMaterialityMatrixFilter(String filterName) {
        WebElement element = Driver.getDriver().findElement(By.xpath("//div[@id='driverFilter']//a[text()='" + filterName + "']"));
        wait.until(ExpectedConditions.visibilityOf(element));
        BrowserUtils.scrollTo(element);
        element.click();
        BrowserUtils.wait(3);
        String classAttribute = element.getAttribute("class");
        Assert.assertTrue(classAttribute.split("jss").length == 4, "Verify selected filter is highlites");
    }

    public List<String> readEsgMaterialityCategories() {
        List<String> categories = new ArrayList<String>();
        String sectionName = "";
        for (WebElement section : esgSections) {
            sectionName = section.getText();
            if (!sectionName.equals("None for the sector"))
                categories.add(section.getText());
        }
        return categories;
    }

    public void validateSubCategoryModal() {

        String sectionName = "";
        for (WebElement section : esgSections) {
            sectionName = section.getText();
            section.click();
            assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(modalDialog)).isDisplayed());
            assertTestCase.assertTrue(wait.until(ExpectedConditions.visibilityOf(modalDialogTitle)).getText().equals(sectionName), "Validate Section name");
            List<String> expectedMetricValues = Arrays.asList(new String[]{"Yes", "No Info", "No"});
            for (int i = 2; i < ModalItems.size(); i++) {
                String[] values = ModalItems.get(i).getText().split("\n");
                if (expectedMetricValues.contains(values[0].toString())) {
                    assertTestCase.assertTrue(true, "Validating if item value is in " + expectedMetricValues);
                } else {
                    String decimalPattern = "([0-9]*)\\.([0-9]*)";
                    boolean match = Pattern.matches(decimalPattern, values[0].toString());
                    assertTestCase.assertTrue(match, "Validating if Item value is numeric");
                }
            }
            Actions a = new Actions(Driver.getDriver());
            a.sendKeys(Keys.ESCAPE).build().perform();
        }

    }

}
