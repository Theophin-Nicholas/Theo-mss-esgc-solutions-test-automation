package com.esgc.APIModels.EntityScoreCategory;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ESGScores {
    EsgAssessment esg_assessment;

    @JsonProperty("esg_assessment")
    public EsgAssessment getEsg_assessment() { 
		 return this.esg_assessment; }

    public void setEsg_assessment(EsgAssessment esg_assessment) { 
		 this.esg_assessment = esg_assessment; } 

}
