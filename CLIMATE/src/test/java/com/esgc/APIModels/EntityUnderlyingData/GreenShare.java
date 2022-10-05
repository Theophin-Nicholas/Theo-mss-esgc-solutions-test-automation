package com.esgc.APIModels.EntityUnderlyingData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GreenShare{
    String bvd9_number;
    ArrayList<GreenShareProduct> green_share_products;
    String score_category;
    String score_msg;
    String updated_date;
    @JsonProperty("bvd9_number")
    public String getBvd9_number() { 
		 return this.bvd9_number; } 
    public void setBvd9_number(String bvd9_number) { 
		 this.bvd9_number = bvd9_number; } 

    @JsonProperty("green_share_products") 
    public ArrayList<GreenShareProduct> getGreen_share_products() { 
		 return this.green_share_products; } 
    public void setGreen_share_products(ArrayList<GreenShareProduct> green_share_products) {
		 this.green_share_products = green_share_products; } 

    @JsonProperty("score_category") 
    public String getScore_category() { 
		 return this.score_category; } 
    public void setScore_category(String score_category) { 
		 this.score_category = score_category; } 

    @JsonProperty("score_msg") 
    public String getScore_msg() { 
		 return this.score_msg; } 
    public void setScore_msg(String score_msg) { 
		 this.score_msg = score_msg; } 

    @JsonProperty("updated_date") 
    public String getUpdated_date() { 
		 return this.updated_date; } 
    public void setUpdated_date(String updated_date) { 
		 this.updated_date = updated_date; } 

}
