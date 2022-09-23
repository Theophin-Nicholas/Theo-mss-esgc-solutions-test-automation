package com.esgc.APIModels.EntityScoreCategory;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class EsgAssessment{
    String last_updated;
    ArrayList<ScoreCategory> score_categories;
    String research_line_id;

    @JsonProperty("last_updated")
    public String getLast_updated() { 
		 return this.last_updated; } 
    public void setLast_updated(String last_updated) { 
		 this.last_updated = last_updated; } 

    @JsonProperty("score_categories") 
    public ArrayList<ScoreCategory> getScore_categories() {
		 return this.score_categories; } 
    public void setScore_categories(ArrayList<ScoreCategory> score_categories) { 
		 this.score_categories = score_categories; } 

    @JsonProperty("research_line_id") 
    public String getResearch_line_id() { 
		 return this.research_line_id; } 
    public void setResearch_line_id(String research_line_id) { 
		 this.research_line_id = research_line_id; } 

}
