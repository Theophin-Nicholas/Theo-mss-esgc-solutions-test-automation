package com.esgc.APIModels.EntityIssuerPage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Item {
    @JsonProperty("item_details")
    public List<ItemDetail> itemDetails = null;
    @JsonProperty("item_value")
    public String itemValue;
}
