package com.esgc.EntityProfile.UI.Tests;

import com.esgc.EntityProfile.UI.Pages.EntityClimateProfilePage;
import com.esgc.PortfolioAnalysis.UI.Pages.ResearchLinePage;
import com.esgc.Base.TestBases.UITestBase;
import com.esgc.Utilities.BrowserUtils;
import com.esgc.Utilities.Xray;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EntityClimateProfileControversies extends UITestBase {


    @Test(groups = {"entity_climate_profile", "regression", "ui", "smoke"})
    @Xray(test = {8402, 8403, 8404, 8405, 8406,8407,8408,8409,8411})
    public void validateEntityControversiesWidget() {
        EntityClimateProfilePage entityProfilePage = new EntityClimateProfilePage();
        ResearchLinePage researchLinePage = new ResearchLinePage();
        BrowserUtils.wait(3);
        researchLinePage.navigateToFirstEntity("000411117");//apple
        BrowserUtils.wait(5);
        try {
            BrowserUtils.scrollTo(entityProfilePage.controversiesTitle.get(0));
        }catch (Exception e){
            System.out.println("Didn't find the Controversies");
        }
        //check if the entity has controversies
        if (entityProfilePage.controversiesTitle.size() > 0) {
            //Verify the title - Controversies
            BrowserUtils.scrollTo(entityProfilePage.controversiesTitle.get(0));
            assertTestCase.assertEquals(entityProfilePage.controversiesTitle.get(0).getText(), "Controversies");
            //Verify the Label - Filter by most impacted categories of ESG:
            assertTestCase.assertEquals(entityProfilePage.controversiesStaticText.getText(), "Filter by most impacted categories of ESG:");
            //Verify the subcategory names and the numbers in it. ex: Environmental Strategy (3)
            //TODO get values from database instead hardcoded values
            List<String> categoryExpectedList = new ArrayList<>(
                    Arrays.asList(
                            "Community Involvement (8)",
                            "Business Behaviour (65)",
                            "Human Rights (14)",
                            "Human Resources (5)",
                            "Environment (3)",
                            "Corporate Governance (4)"
                    )
            );
            //number of controversies in sub category
            //Verify that selected subcategory controversies are visible after clicking the subcategory

            List<String> controversyList=new ArrayList<>();
            for (WebElement subCategoryActual : entityProfilePage.subCategoryList) {

                System.out.println("subCategoryList = " + subCategoryActual.getText());
                System.out.println("categoryExpectedList = " + categoryExpectedList);
                System.out.println("subCategoryActual.getText() = " + subCategoryActual.getText());
                assertTestCase.assertTrue(categoryExpectedList.contains(subCategoryActual.getText()));
                subCategoryActual.click();
                int numberOfSubCategoryControversies= Integer.parseInt(subCategoryActual.getText().substring( subCategoryActual.getText().indexOf("(")+1 ,subCategoryActual.getText().indexOf(")")));
                System.out.println("Number of Controversies in Sub Category = " + numberOfSubCategoryControversies);
                BrowserUtils.wait(2);
                //Verify that there is vertical scrollbar is displayed for the widget if number of Controversies for the entity is too many
                if(!(entityProfilePage.controversiesTableRow.size() ==50)) {
                    assertTestCase.assertEquals(entityProfilePage.controversiesTableRow.size(), numberOfSubCategoryControversies);
                }else {//if the number of controversies are more than 50, then need to scroll to the 50th item to get the remaining.
                    BrowserUtils.scrollTo(entityProfilePage.controversiesTableRow.get(49));
                    BrowserUtils.wait(3);
                    assertTestCase.assertEquals(entityProfilePage.controversiesTableRow.size(), numberOfSubCategoryControversies);
                    BrowserUtils.scrollTo(entityProfilePage.controversiesTableRow.get(0));
                }
                //Verify the sorting for the Controversy list in the widget. 8407
                // Controversy list should be sorted by the most recent one on top followed by less recent
                for(WebElement controversiesDetails: entityProfilePage.controversiesTableRow){
                    // Verify that clicking on a controversy row in the widget ,is opening up a Controversy modal popup
                    controversyList.add(controversiesDetails.findElement(By.xpath("td[1]")).getText());
                    controversiesDetails.click();
                    System.out.println("entityProfilePage.controversiesPopUpVerify.getText() = " + entityProfilePage.controversiesPopUpVerify.getText());
                    assertTestCase.assertEquals(entityProfilePage.controversiesPopUpVerify.getText(),"Controversies");
                    BrowserUtils.wait(1);
                    entityProfilePage.controversiesPopUpClose.click();
                }
                System.out.println("controversyList = " + controversyList);
                List<String> actualList=controversyList;

                SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
               // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM d',' yyyy");
                Collections.sort(controversyList, (s1, s2) -> {
                    try {
                        return formatter.parse(s2).
                                compareTo(formatter.parse(s1));
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                });
                System.out.println("controversyList = " + controversyList);
                assertTestCase.assertEquals(actualList,controversyList);

                BrowserUtils.scrollTo(entityProfilePage.controversiesStaticText);
                subCategoryActual.click();//uncheck the sub category
                BrowserUtils.wait(3);
                controversyList.clear();
            }
        } else {
            if (entityProfilePage.noControversies.getText().equals("No controversies to display.")) {
                System.out.println("No controversies to display.");
            } else {
                throw new RuntimeException("Issue with Controversies table");
            }
        }
        researchLinePage.pressESCKey();
    }

}


//   8419
// DB VALIDATION : Verify the Controversies data for an entity is correct for an entity
