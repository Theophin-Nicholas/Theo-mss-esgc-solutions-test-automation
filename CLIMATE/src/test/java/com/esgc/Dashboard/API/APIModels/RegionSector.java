package com.esgc.Dashboard.API.APIModels;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.List;
@Data
public class RegionSector {
    @JsonProperty("entities")
    private Entity[] entities;
    @JsonProperty("remaining_entities")
    private Integer remainingEntities;
    @JsonProperty("total_entities")
    private Integer totalEntities;
}
