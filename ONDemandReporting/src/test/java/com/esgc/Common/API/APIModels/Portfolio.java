package com.esgc.Common.API.APIModels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data @AllArgsConstructor @NoArgsConstructor
public class Portfolio {
    private ArrayList<PortfolioDetails> portfolios;
}




