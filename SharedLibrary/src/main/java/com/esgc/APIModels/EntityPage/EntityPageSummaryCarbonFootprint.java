package com.esgc.APIModels.EntityPage;

public class EntityPageSummaryCarbonFootprint {

    private int carbon_footprint_value_total;
    private int emissions_scope_1;
    private int emissions_scope_2;
    private int emissions_scope_3;
    private String estimated;
    private String last_updated_date;
    private String score_category;
    public int getCarbon_footprint_value_total() {
        return carbon_footprint_value_total;
    }

    public void setCarbon_footprint_value_total(int carbon_footprint_value_total) {
        this.carbon_footprint_value_total = carbon_footprint_value_total;
    }

    public int getEmissions_scope_1() {
        return emissions_scope_1;
    }

    public void setEmissions_scope_1(int emissions_scope_1) {
        this.emissions_scope_1 = emissions_scope_1;
    }

    public int getEmissions_scope_2() {
        return emissions_scope_2;
    }

    public void setEmissions_scope_2(int emissions_scope_2) {
        this.emissions_scope_2 = emissions_scope_2;
    }

    public int getEmissions_scope_3() {
        return emissions_scope_3;
    }

    public void setEmissions_scope_3(int emissions_scope_3) {
        this.emissions_scope_3 = emissions_scope_3;
    }

    public String getEstimated() {
        return estimated;
    }

    public void setEstimated(String estimated) {
        this.estimated = estimated;
    }

    public String getLast_updated_date() {
        return last_updated_date;
    }

    public void setLast_updated_date(String last_updated_date) {
        this.last_updated_date = last_updated_date;
    }

    public String getScore_category() {
        return score_category;
    }

    public void setScore_category(String score_category) {
        this.score_category = score_category;
    }


}

