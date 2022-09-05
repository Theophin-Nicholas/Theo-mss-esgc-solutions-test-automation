package com.esgc.APIModels;

import lombok.Data;

import java.util.List;

@Data
public class TestCase {
    public List<String> testCaseNumber;
    public Boolean passOrFail;
    public String failedScreenShot;
}
