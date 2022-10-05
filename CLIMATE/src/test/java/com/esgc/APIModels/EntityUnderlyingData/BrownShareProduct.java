package com.esgc.APIModels.EntityUnderlyingData;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BrownShareProduct{
    @JsonProperty("criteria")
    public String getCriteria() {
        return this.criteria; }
    public void setCriteria(String criteria) {
        this.criteria = criteria; }
    String criteria;
    @JsonProperty("investment_in_category")
    public double getInvestment_in_category() {
        return this.investment_in_category; }
    public void setInvestment_in_category(double investment_in_category) {
        this.investment_in_category = investment_in_category; }
    double investment_in_category;
    @JsonProperty("title")
    public String getTitle() {
        return this.title; }
    public void setTitle(String title) {
        this.title = title; }
    String title;
    @JsonProperty("type")
    public String getType() {
        return this.type; }
    public void setType(String type) {
        this.type = type; }
    String type;
    @JsonProperty("investment_in_category_msg")
    public String getInvestment_in_category_msg() {
        return this.investment_in_category_msg; }
    public void setInvestment_in_category_msg(String investment_in_category_msg) {
        this.investment_in_category_msg = investment_in_category_msg; }
    String investment_in_category_msg;
    @JsonProperty("unit")
    public String getUnit() {
        return this.unit; }
    public void setUnit(String unit) {
        this.unit = unit; }
    String unit;
}
