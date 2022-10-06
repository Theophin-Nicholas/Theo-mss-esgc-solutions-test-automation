package com.esgc.APIModels.EntityUnderlyingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GreenShareProduct{
    String investment_in_category_msg;
    String title;
    int investment_in_category;

    @JsonProperty("investment_in_category_msg")
    public String getInvestment_in_category_msg() { 
		 return this.investment_in_category_msg; } 
    public void setInvestment_in_category_msg(String investment_in_category_msg) { 
		 this.investment_in_category_msg = investment_in_category_msg; } 

    @JsonProperty("title") 
    public String getTitle() { 
		 return this.title; } 
    public void setTitle(String title) { 
		 this.title = title; } 

    @JsonProperty("investment_in_category") 
    public int getInvestment_in_category() { 
		 return this.investment_in_category; } 
    public void setInvestment_in_category(int investment_in_category) { 
		 this.investment_in_category = investment_in_category; } 

}
