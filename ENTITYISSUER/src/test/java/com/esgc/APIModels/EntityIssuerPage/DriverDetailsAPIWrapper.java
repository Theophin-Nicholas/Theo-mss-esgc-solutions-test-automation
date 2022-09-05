package com.esgc.APIModels.EntityIssuerPage;

import com.esgc.APIModels.EntityPage.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DriverDetailsAPIWrapper {
    @JsonProperty("count_not_indicated")
    public Integer countNotIndicated;
    @JsonProperty("count_total")
    public Integer countTotal;
    @JsonProperty("item")
    public List<Item> item;
    @JsonProperty("sub_category_description")
    public String SubCategoryDescription;
    @JsonProperty("sub_category_detailed_description")
    public String SubCategoryDetailedDescription;
    public List<Trend> trends;

}
