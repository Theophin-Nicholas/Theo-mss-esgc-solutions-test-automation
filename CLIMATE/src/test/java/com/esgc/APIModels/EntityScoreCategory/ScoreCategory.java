package com.esgc.APIModels.EntityScoreCategory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ScoreCategory{
    Object score;
    String score_category;
    String score_range;
    String sub_category;
    String qualifier;
    String scale;

    @JsonProperty("score")
    public Object getScore() { 
		 return this.score; } 
    public void setScore(Object score) { 
		 this.score = score; } 

    @JsonProperty("score_category") 
    public String getScore_category() { 
		 return this.score_category; } 
    public void setScore_category(String score_category) { 
		 this.score_category = score_category; } 

    @JsonProperty("score_range") 
    public String getScore_range() { 
		 return this.score_range; } 
    public void setScore_range(String score_range) { 
		 this.score_range = score_range; } 

    @JsonProperty("sub_category") 
    public String getSub_category() { 
		 return this.sub_category; } 
    public void setSub_category(String sub_category) { 
		 this.sub_category = sub_category; } 

    @JsonProperty("qualifier") 
    public String getQualifier() { 
		 return this.qualifier; } 
    public void setQualifier(String qualifier) { 
		 this.qualifier = qualifier; } 

    @JsonProperty("scale") 
    public String getScale() { 
		 return this.scale; } 
    public void setScale(String scale) { 
		 this.scale = scale; } 

}
