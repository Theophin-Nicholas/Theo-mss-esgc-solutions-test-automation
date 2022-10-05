package com.esgc.APIModels.EntityUnderlyingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CarbonFootprint{
    @JsonProperty("bvd9_number")
    public String getBvd9_number() {
        return this.bvd9_number; }
    public void setBvd9_number(String bvd9_number) {
        this.bvd9_number = bvd9_number; }
    String bvd9_number;
    @JsonProperty("carbon_footprint_reporting_year")
    public String getCarbon_footprint_reporting_year() {
        return this.carbon_footprint_reporting_year; }
    public void setCarbon_footprint_reporting_year(String carbon_footprint_reporting_year) {
        this.carbon_footprint_reporting_year = carbon_footprint_reporting_year; }
    String carbon_footprint_reporting_year;
    @JsonProperty("carbon_footprint_value_total")
    public int getCarbon_footprint_value_total() {
        return this.carbon_footprint_value_total; }
    public void setCarbon_footprint_value_total(int carbon_footprint_value_total) {
        this.carbon_footprint_value_total = carbon_footprint_value_total; }
    int carbon_footprint_value_total;
    @JsonProperty("carbon_intensity")
    public double getCarbon_intensity() {
        return this.carbon_intensity; }
    public void setCarbon_intensity(double carbon_intensity) {
        this.carbon_intensity = carbon_intensity; }
    double carbon_intensity;
    @JsonProperty("emissions_scope_1")
    public int getEmissions_scope_1() {
        return this.emissions_scope_1; }
    public void setEmissions_scope_1(int emissions_scope_1) {
        this.emissions_scope_1 = emissions_scope_1; }
    int emissions_scope_1;
    @JsonProperty("emissions_scope_2")
    public int getEmissions_scope_2() {
        return this.emissions_scope_2; }
    public void setEmissions_scope_2(int emissions_scope_2) {
        this.emissions_scope_2 = emissions_scope_2; }
    int emissions_scope_2;
    @JsonProperty("emissions_scope_3")
    public int getEmissions_scope_3() {
        return this.emissions_scope_3; }
    public void setEmissions_scope_3(int emissions_scope_3) {
        this.emissions_scope_3 = emissions_scope_3; }
    int emissions_scope_3;
    @JsonProperty("estimated")
    public String getEstimated() {
        return this.estimated; }
    public void setEstimated(String estimated) {
        this.estimated = estimated; }
    String estimated;
    @JsonProperty("last_updated_date")
    public String getLast_updated_date() {
        return this.last_updated_date; }
    public void setLast_updated_date(String last_updated_date) {
        this.last_updated_date = last_updated_date; }
    String last_updated_date;
    @JsonProperty("sales")
    public int getSales() {
        return this.sales; }
    public void setSales(int sales) {
        this.sales = sales; }
    int sales;
    @JsonProperty("score_category")
    public String getScore_category() {
        return this.score_category; }
    public void setScore_category(String score_category) {
        this.score_category = score_category; }
    String score_category;
    @JsonProperty("source")
    public String getSource() {
        return this.source; }
    public void setSource(String source) {
        this.source = source; }
    String source;
    @JsonProperty("third_party_verification")
    public String getThird_party_verification() {
        return this.third_party_verification; }
    public void setThird_party_verification(String third_party_verification) {
        this.third_party_verification = third_party_verification; }
    String third_party_verification;
}

