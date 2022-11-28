package com.esgc.APIModels;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestEvidence {
    private String data;
    private String filename;
    private String contentType = "image/png";

    public TestEvidence(String data) {
        this.data = data;
        this.filename = "Failure Screenshot";
    }
}
