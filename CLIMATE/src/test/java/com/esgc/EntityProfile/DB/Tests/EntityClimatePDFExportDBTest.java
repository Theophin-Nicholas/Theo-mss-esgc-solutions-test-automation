package com.esgc.EntityProfile.DB.Tests;

import com.esgc.Base.TestBases.DataValidationTestBase;
import com.esgc.Dashboard.DB.DBQueries.DashboardQueries;
import com.esgc.EntityProfile.UI.Pages.PDFTestMethods;
import com.esgc.Utilities.Xray;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static com.esgc.Utilities.Groups.*;

public class EntityClimatePDFExportDBTest extends DataValidationTestBase {

    @Test(groups = {"PDF Export", REGRESSION, UI, DATA_VALIDATION}, description = "Data Validation | Entity Page | Export Button | Verify the Sector for entity in downloaded PDF")
    @Xray(test = {3118})
    public void verifySectorForEntityTest() {
        DashboardQueries queries = new DashboardQueries();
        String portfolioId = "00000000-0000-0000-0000-000000000000";
        List<Map<String, Object>> dbData = queries.getPortfolioSectorDetail(portfolioId);
        for (int i = 0; i < 5; i++) {
            if (dbData.get(i).get("ENTITY_NAME") == null) continue;
            String companyName = dbData.get(i).get("ENTITY_NAME").toString();
            System.out.println("companyName = " + companyName);
            if (dbData.get(i).get("MESG_SECTOR") == null) continue;
            String sector = dbData.get(i).get("MESG_SECTOR").toString();
            System.out.println("sector = " + sector);
            PDFTestMethods pdfTest = new PDFTestMethods();
            pdfTest.DownloadPDFPAndGetFileContent(companyName);
            pdfTest.verifySectorForEntity(sector);
            break;
        }
    }
}
