package com.esgc.Pages.PortfolioAnalysisPage.PhysicalRiskPages.PhysicalRiskManagementPages;

import com.esgc.Pages.ResearchLinePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PhysicalRiskManagementPage extends ResearchLinePage {


    @FindBy(xpath = "//div[text()='Physical Risk Management']")
    public WebElement subTitle;

    @FindBy(xpath = "//div[contains(text(),'We evaluate how companies are acting to anticipate, prevent and manage the risks associated with the physical impacts of climate change. The assessment is based on corporate disclosures related to physical risks identification and measures put in place to address exposure to physical risks.')]")
    public WebElement description;

    //================Portfolio Score Elements
/*
    //Ranks should be one of these: Weak, Limited, Robust, Advanced, N/A
    @FindBy(xpath = "(//*[@id='phy-risk-mgm-test-id-from-overview-'])[1]//div[text()]")
    public WebElement portfolioScoreCategory;

    //Scores should be between 0-100
    @FindBy(xpath = "(//*[@id='phy-risk-mgm-test-id-from-overview-'])[2]//div[text()='Score']/following-sibling::div")
    public WebElement portfolioScore;
*/
    //Names order should be: Results, Implementation, Leadership, Total
    @FindBy(xpath = "//table[@id='overview-portfolio-score-minimaltable-test-id']//tr//td[3]")
    public List<WebElement> portfolioScoreNames;


    //===============================

    /**
     * Checks if Physical Risk Management Subtitle Displayed
     *
     * @return true - if not displayed returns false
     */

    public boolean checkIfPhysicalRiskManagementSubtitleIsDisplayed() {
        try {
            System.out.println("Physical risk Management Sub Title Check");
            return subTitle.isDisplayed();
        } catch (Exception e) {
            System.out.println("Failed");
            e.printStackTrace();
            return false;
        }
    }


    //============== Portfolio Score Methods
/*
    public String getPortfolioScoreFromUI() {
        return portfolioScore.getText();
    }

    public String getPortfolioScoreCategoryFromUI() {
        return portfolioScoreCategory.getText();
    }
*/




}
