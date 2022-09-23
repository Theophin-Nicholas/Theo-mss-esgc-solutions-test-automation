package com.esgc.APIModels.PortfolioSettings;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class PortfolioDetails{
    int entities;
    ArrayList<Investment> investments;
    String portfolio_id;
    int portfolio_percent;
    int total_matched_companies;
    int total_unique_companies;
    int total_unmatched_companies;
    int unmatched_portfolio_percent;

    @JsonProperty("entities")
    public int getEntities() { 
		 return this.entities; } 
    public void setEntities(int entities) { 
		 this.entities = entities; } 

    @JsonProperty("investments") 
    public ArrayList<Investment> getInvestments() {
		 return this.investments; } 
    public void setInvestments(ArrayList<Investment> investments) { 
		 this.investments = investments; } 

    @JsonProperty("portfolio_id") 
    public String getPortfolio_id() { 
		 return this.portfolio_id; } 
    public void setPortfolio_id(String portfolio_id) { 
		 this.portfolio_id = portfolio_id; } 

    @JsonProperty("portfolio_percent") 
    public int getPortfolio_percent() { 
		 return this.portfolio_percent; } 
    public void setPortfolio_percent(int portfolio_percent) { 
		 this.portfolio_percent = portfolio_percent; } 

    @JsonProperty("total_matched_companies") 
    public int getTotal_matched_companies() { 
		 return this.total_matched_companies; } 
    public void setTotal_matched_companies(int total_matched_companies) { 
		 this.total_matched_companies = total_matched_companies; } 

    @JsonProperty("total_unique_companies") 
    public int getTotal_unique_companies() { 
		 return this.total_unique_companies; } 
    public void setTotal_unique_companies(int total_unique_companies) { 
		 this.total_unique_companies = total_unique_companies; } 

    @JsonProperty("total_unmatched_companies") 
    public int getTotal_unmatched_companies() { 
		 return this.total_unmatched_companies; } 
    public void setTotal_unmatched_companies(int total_unmatched_companies) { 
		 this.total_unmatched_companies = total_unmatched_companies; } 

    @JsonProperty("unmatched_portfolio_percent") 
    public int getUnmatched_portfolio_percent() { 
		 return this.unmatched_portfolio_percent; } 
    public void setUnmatched_portfolio_percent(int unmatched_portfolio_percent) { 
		 this.unmatched_portfolio_percent = unmatched_portfolio_percent; } 

}
