package com.esgc.APIModels.EntityIssuerPage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ItemDetail {
    @JsonProperty("angle")
    public String angle;
    @JsonProperty("criteria_name")
    public String criteriaName;
    @JsonProperty("item_descripton")
    public String itemDescripton;
    @JsonProperty("item_value")
    public String itemValue;
   // @JsonProperty("item_weight")
   // public Float itemWeight;
    @JsonProperty("orbis_id")
    public String orbisId;
    @JsonProperty("pillar")
    public String pillar;
    @JsonProperty("possible_unit")
    public String possibleUnit;
}
