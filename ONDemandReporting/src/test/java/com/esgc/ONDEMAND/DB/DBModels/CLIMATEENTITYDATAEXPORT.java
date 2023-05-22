package com.esgc.ONDEMAND.DB.DBModels;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CLIMATEENTITYDATAEXPORT {
    @JsonProperty("% Investment")
    public String investment;
    @JsonProperty("CGV - Corporate Governance Domain Score")
    public String cgvcorporategovernancedomainscore;
    @JsonProperty("CGV 1.1 - Board of Directors")
    public String cgv11boardofdirectors;
    @JsonProperty("CGV 2.1 - Audit & Internal Controls")
    public String cgv21auditinternalcontrols;
    @JsonProperty("CGV 3.1 - Shareholders")
    public String cgv31shareholders;
    @JsonProperty("CGV 4.1 - Executive Remuneration")
    public String cgv41executiveremuneration;
    @JsonProperty("CIN - Community Involvement Domain Score")
    public String cincommunityinvolvementdomainscore;
    @JsonProperty("CIN 1.1 - Promotion of the social and economic development")
    public String cin11promotionofthesocialandeconomicdevelopment;
    @JsonProperty("CIN 2.1 - Societal impacts of the companys products / services")
    public String cin21societalimpactsofthecompanysproductsservices;
    @JsonProperty("CIN 2.2 - Contribution to general interest causes")
    public String cin22contributiontogeneralinterestcauses;
    @JsonProperty("CS - Business Behaviour Domain Score")
    public String csbusinessbehaviourdomainscore;
    @JsonProperty("CS 1.1 - Product Safety (process and use)")
    public String cs11productsafetyprocessanduse;
    @JsonProperty("CS 1.2 - Information to customers")
    public String cs12informationtocustomers;
    @JsonProperty("CS 1.3 - Responsible Customer Relations")
    public String cs13responsiblecustomerrelations;
    @JsonProperty("CS 2.2 - Sustainable relationships with suppliers")
    public String cs22sustainablerelationshipswithsuppliers;
    @JsonProperty("CS 2.3 - Integration of environmental factors in the supply chain")
    public String cs23integrationofenvironmentalfactorsinthesupplychain;
    @JsonProperty("CS 2.4 - Integration of social factors in the supply chain")
    public String cs24integrationofsocialfactorsinthesupplychain;
    @JsonProperty("CS 3.1 - Prevention of corruption")
    public String cs31preventionofcorruption;
    @JsonProperty("CS 3.2 - Prevention of anti-competitive practices")
    public String cs32preventionofanticompetitivepractices;
    @JsonProperty("CS 3.3 - Transparency and integrity of influence strategies and practices")
    public String cs33transparencyandintegrityofinfluencestrategiesandpractices;
    @JsonProperty("Country (Country ISO code)")
    public String countrycountryisocode;
    @JsonProperty("ENTITY")
    public String entity;
    @JsonProperty("ENV - Environment Domain Score")
    public String envenvironmentdomainscore;
    @JsonProperty("ENV 1.1 - Environmental strategy and eco-design")
    public String env11environmentalstrategyandecodesign;
    @JsonProperty("ENV 1.2 - Pollution prevention and control (soil, accident)")
    public String env12pollutionpreventionandcontrolsoilaccident;
    @JsonProperty("ENV 1.3 - Development of green products and services")
    public String env13developmentofgreenproductsandservices;
    @JsonProperty("ENV 1.4 - Protection of biodiversity")
    public String env14protectionofbiodiversity;
    @JsonProperty("ENV 2.1 - Protection of water resources")
    public String env21protectionofwaterresources;
    @JsonProperty("ENV 2.2 - Minimising environmental impacts from energy use")
    public String env22minimisingenvironmentalimpactsfromenergyuse;
    @JsonProperty("ENV 2.4 - Management of atmospheric emissions")
    public String env24managementofatmosphericemissions;
    @JsonProperty("ENV 2.5 - Waste management")
    public String env25wastemanagement;
    @JsonProperty("ENV 2.6 - Management of local pollution")
    public String env26managementoflocalpollution;
    @JsonProperty("ENV 2.7 - Management of environmental impacts from transportation")
    public String env27managementofenvironmentalimpactsfromtransportation;
    @JsonProperty("ENV 3.1 - Management of environmental impacts from the use and disposal of products/services")
    public String env31managementofenvironmentalimpactsfromtheuseanddisposalofproductsservices;
    @JsonProperty("Evaluation Year")
    public String evaluationyear;
    @JsonProperty("HRS - Human Resources Domain Score")
    public String hrshumanresourcesdomainscore;
    @JsonProperty("HRS 1.1 - Promotion of labour relations")
    public String hrs11promotionoflabourrelations;
    @JsonProperty("HRS 2.3 - Responsible management of restructurings")
    public String hrs23responsiblemanagementofrestructurings;
    @JsonProperty("HRS 2.4 - Career management and promotion of employability")
    public String hrs24careermanagementandpromotionofemployability;
    @JsonProperty("HRS 3.1 - Quality of remuneration systems")
    public String hrs31qualityofremunerationsystems;
    @JsonProperty("HRS 3.2 - Improvement of health and safety conditions")
    public String hrs32improvementofhealthandsafetyconditions;
    @JsonProperty("HRS 3.3 - Respect and management of working hours")
    public String hrs33respectandmanagementofworkinghours;
    @JsonProperty("HRT - Human Rights Domain Score")
    public String hrthumanrightsdomainscore;
    @JsonProperty("HRT 1.1 - Respect for human rights standards and prevention of violations")
    public String hrt11respectforhumanrightsstandardsandpreventionofviolations;
    @JsonProperty("HRT 2.1 - Respect for freedom of association and the right to collective bargaining")
    public String hrt21respectforfreedomofassociationandtherighttocollectivebargaining;
    @JsonProperty("HRT 2.4 - Non-discrimination")
    public String hrt24nondiscrimination;
    @JsonProperty("HRT 2.5 -  Elimination of child labour and forced labour")
    public String hrt25eliminationofchildlabourandforcedlabour;
    @JsonProperty("Input industry")
    public String inputindustry;
    @JsonProperty("Input industry type")
    public String inputindustrytype;
    @JsonProperty("Input location")
    public String inputlocation;
    @JsonProperty("Input location type")
    public String inputlocationtype;
    @JsonProperty("Input size")
    public String inputsize;
    @JsonProperty("Input size type")
    public String inputsizetype;
    @JsonProperty("ORBIS_ID")
    public String orbisid;
    @JsonProperty("Overall ESG Score")
    public String overallesgscore;
    @JsonProperty("Overall ESG Score Qualifier")
    public String overallesgscorequalifier;
    @JsonProperty("Overall Environmental score")
    public String overallenvironmentalscore;
    @JsonProperty("Overall Environmental score Qualifier")
    public String overallenvironmentalscorequalifier;
    @JsonProperty("Overall Governance score")
    public String overallgovernancescore;
    @JsonProperty("Overall Governance score Qualifier")
    public String overallgovernancescorequalifier;
    @JsonProperty("Overall Social score")
    public String overallsocialscore;
    @JsonProperty("Overall Social score Qualifier")
    public String overallsocialscorequalifier;
    @JsonProperty("Portfolio Upload Date")
    public String portfoliouploaddate;
    @JsonProperty("REGION")
    public String region;
    @JsonProperty("SECTOR")
    public String sector;
    @JsonProperty("Score Type")
    public String scoretype;
    @JsonProperty("Scored Date")
    public String scoreddate;
    @JsonProperty("USER INPUT")
    public String userinput;
    @JsonProperty("ISIN(PRIMARY)")
    public String isinprimary;
    public String lei;
    @JsonProperty("Parent Orbis ID")
    public String parentorbisid;
    public String subsidiary;
}

