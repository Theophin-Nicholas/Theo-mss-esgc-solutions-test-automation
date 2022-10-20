package com.esgc.APIModels.PortoflioAnalysisModels;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//This deserializer used to handle "data" field, sometimes returns integer sometimes returns an object, see line 31 and 35
public class HistoryTableDataResolverForHistoryTableCategoryDeserializer extends StdDeserializer<HistoryTableCategory> {

    public HistoryTableDataResolverForHistoryTableCategoryDeserializer() {
        super(HistoryTableCategory.class);
    }

    @Override
    public HistoryTableCategory deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        HistoryTableCategory historyTableCategory = new HistoryTableCategory();
        historyTableCategory.setName(node.get("name").asText());
        JsonNode historyTableDataNode = node.get("data");
        List<HistoryTableData> dataList = new ArrayList<>();

        for (int i = 0; i < historyTableDataNode.size(); i++) {
            JsonNode eachData = historyTableDataNode.get(i);
            HistoryTableData data = new HistoryTableData();
            if (eachData.isInt()) {
                //if data returns 0
                data.setInv_pct(0d);
                data.setNum_companies(0);
            } else {
                //if data has an object
                data.setInv_pct(eachData.get("inv_pct").asDouble());
                data.setNum_companies(eachData.get("num_companies").asInt());
            }
            dataList.add(data);
        }
        historyTableCategory.setData(dataList);
        return historyTableCategory;
    }
}
