package com.esgc.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EntityHeader {

    String company_name;
    String country;
    String lei;
    String generic_sector;
    String mesg_sector;
    String mesg_sector_detail_description;
    String methodology;
    String model_version;
    String orbis_id;
    String primary_isin;
    int overall_disclosure_score;
    String region;
    int research_line_id;
    String sector_l1;
    String sector_l2;

    @JsonProperty("company_name")
    public String getCompany_name() {
        return this.company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @JsonProperty("country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @JsonProperty("lei")
    public String getLei() {
        return this.lei;
    }

    public void setLei(String lei) {
        this.lei = lei;
    }

    public String getGeneric_sector() {
        return generic_sector;
    }

    public void setGeneric_sector(String generic_sector) {
        this.generic_sector = generic_sector;
    }

    @JsonProperty("mesg_sector")
    public String getMesg_sector() {
        return this.mesg_sector;
    }

    public void setMesg_sector(String mesg_sector) {
        this.mesg_sector = mesg_sector;
    }

    @JsonProperty("mesg_sector_detail_description")
    public String getMesg_sector_detail_description() {
        return this.mesg_sector_detail_description;
    }

    public void setMesg_sector_detail_description(String mesg_sector_detail_description) {
        this.mesg_sector_detail_description = mesg_sector_detail_description;
    }

    @JsonProperty("methodology")
    public String getMethodology() {
        return this.methodology;
    }

    public void setMethodology(String methodology) {
        this.methodology = methodology;
    }

    @JsonProperty("model_version")
    public String getModel_version() {
        return this.model_version;
    }

    public void setModel_version(String model_version) {
        this.model_version = model_version;
    }

    @JsonProperty("orbis_id")
    public String getOrbis_id() {
        return this.orbis_id;
    }

    public void setOrbis_id(String orbis_id) {
        this.orbis_id = orbis_id;
    }

    @JsonProperty("overall_disclosure_score")
    public int getOverall_disclosure_score() {
        return this.overall_disclosure_score;
    }

    public void setOverall_disclosure_score(int overall_disclosure_score) {
        this.overall_disclosure_score = overall_disclosure_score;
    }

    @JsonProperty("primary_isin")
    public String getPrimary_isin() {
        return this.primary_isin;
    }

    public void setPrimary_isin(String primary_isin) {
        this.primary_isin = primary_isin;
    }

    @JsonProperty("region")
    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @JsonProperty("research_line_id")
    public int getResearch_line_id() {
        return this.research_line_id;
    }

    public void setResearch_line_id(int research_line_id) {
        this.research_line_id = research_line_id;
    }

    @JsonProperty("sector_l1")
    public String getSector_l1() {
        return this.sector_l1;
    }

    public void setSector_l1(String sector_l1) {
        this.sector_l1 = sector_l1;
    }

    @JsonProperty("sector_l2")
    public String getSector_l2() {
        return this.sector_l2;
    }

    public void setSector_l2(String sector_l2) {
        this.sector_l2 = sector_l2;
    }

}


