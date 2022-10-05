package com.esgc.APIModels.EntityUnderlyingData;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class BrownShare{
    ArrayList<BrownShareProduct> brown_share_products;
    String bvd9_number;
    int score;
    String score_category;
    String updated_date;

    @JsonProperty("brown_share_products")
    public ArrayList<BrownShareProduct> getBrown_share_products() {
		 return this.brown_share_products; } 
    public void setBrown_share_products(ArrayList<BrownShareProduct> brown_share_products) { 
		 this.brown_share_products = brown_share_products; } 

    @JsonProperty("bvd9_number") 
    public String getBvd9_number() { 
		 return this.bvd9_number; } 
    public void setBvd9_number(String bvd9_number) { 
		 this.bvd9_number = bvd9_number; } 

    @JsonProperty("score") 
    public int getScore() { 
		 return this.score; } 
    public void setScore(int score) { 
		 this.score = score; } 

    @JsonProperty("score_category") 
    public String getScore_category() { 
		 return this.score_category; } 
    public void setScore_category(String score_category) { 
		 this.score_category = score_category; } 

    @JsonProperty("updated_date") 
    public String getUpdated_date() { 
		 return this.updated_date; } 
    public void setUpdated_date(String updated_date) { 
		 this.updated_date = updated_date; } 

}
