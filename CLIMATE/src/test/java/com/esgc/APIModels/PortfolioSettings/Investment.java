package com.esgc.APIModels.PortfolioSettings;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Investment{
    String company_name;
    double investment_pct;
    String orbis_id;
    int order;
    int rank;

    @JsonProperty("company_name") 
    public String getCompany_name() { 
		 return this.company_name; } 
    public void setCompany_name(String company_name) { 
		 this.company_name = company_name; } 

    @JsonProperty("investment_pct")
    public double getInvestment_pct() { 
		 return this.investment_pct; } 
    public void setInvestment_pct(double investment_pct) { 
		 this.investment_pct = investment_pct; } 

    @JsonProperty("orbis_id") 
    public String getOrbis_id() { 
		 return this.orbis_id; } 
    public void setOrbis_id(String orbis_id) { 
		 this.orbis_id = orbis_id; } 

    @JsonProperty("order") 
    public int getOrder() { 
		 return this.order; } 
    public void setOrder(int order) { 
		 this.order = order; } 

    @JsonProperty("rank") 
    public int getRank() { 
		 return this.rank; } 
    public void setRank(int rank) { 
		 this.rank = rank; } 

}
