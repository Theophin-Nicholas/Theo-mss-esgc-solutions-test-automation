package com.esgc.PortfolioManagement.UI.Pages;

import com.esgc.Base.API.APIModels.PortfolioSettings.Investment;
import com.esgc.Base.API.APIModels.PortfolioSettings.PortfolioDetails;
import com.esgc.Base.API.Controllers.APIController;
import com.esgc.Base.UI.Pages.UploadPage;
import com.esgc.Utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class PortfolioManagementPage extends UploadPage {

    @FindBy(xpath = "//table[@id='table-id']//tbody/tr")
    public List<WebElement> portfolioTableRows;

    public void selectPortfolio(String portfolioName){
        String xpath = "//div[@heap_id='portfolio-selection']//span[text()='"+portfolioName+"']";
        Driver.getDriver().findElement(By.xpath(xpath)).click();
    }

    public ArrayList<String> getPredictedScoredCompanies(){
        ArrayList<String> companyNames = new ArrayList<>();
        for(int i=1; i<=portfolioTableRows.size(); i++){
            companyNames.add(Driver.getDriver().findElement(By.xpath("//table[@id='table-id']//tbody/tr["+i+"]//span[text()]")).getText());
        }
        return companyNames;
    }

    public ArrayList<String> getPredictedScoredCompaniesFromApi(String portfolioId){
        ArrayList<String> companyNames = new ArrayList<>();
        APIController controller = new APIController();
        PortfolioDetails portfolioDetails = controller.getPortfolioSettingsAPIResponse(portfolioId).as(PortfolioDetails.class);
        ArrayList<Investment> investments = portfolioDetails.getInvestments();
        for(Investment investment: investments){
            companyNames.add(investment.getCompany_name());
        }
        return companyNames;
    }

}
