package com.esgc.APIModels.EntityPage;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DriverDetails {
    @JsonProperty("count_not_indicated")
    public Integer countNotIndicated;
    @JsonProperty("count_total")
    public Integer countTotal;
    @JsonProperty("item")
    public List<Item> item = null;
}
