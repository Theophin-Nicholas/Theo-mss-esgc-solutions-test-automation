package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.List;

@Data
public class SectorIndicator {

    private List<SectorDrivers> drivers;
    private String indicator;

}
