package com.esgc.APIModels.EntityIssuerPage;

import lombok.Data;

import java.util.ArrayList;

@Data
public class DriverDetail {
    private ArrayList<Driver> drivers;
    private String indicator;
}
