package com.deskoh.entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.io.IOException;

import java.util.List;

public class AssetDeserializer extends JsonDeserializer<List<Asset>> {
    private final ObjectMapper mapper = new JsonMapper();

    @Override
    public List<Asset> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonNode node = p.readValueAsTree();
        Project project = (Project) p.currentValue();
        // Do some processing logic
        System.out.println(project);
        return mapper.readerForListOf(Asset.class).readValue(node);
    }
}
