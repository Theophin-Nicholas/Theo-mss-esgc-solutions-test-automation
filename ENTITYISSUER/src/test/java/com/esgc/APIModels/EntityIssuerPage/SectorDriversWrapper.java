package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.List;


@Data
public class SectorDriversWrapper {

    private int driver_count;
    private List<SectorIndicator> driver_details;
/*    private Header header;
    private int sector_peers;*/

}