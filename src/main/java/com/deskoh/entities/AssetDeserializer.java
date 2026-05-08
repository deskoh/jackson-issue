package com.deskoh.entities;

import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.json.JsonMapper;

import java.util.List;

public class AssetDeserializer extends ValueDeserializer<List<Asset>> {
    private final ObjectMapper mapper = new JsonMapper();

    @Override
    public List<Asset> deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonNode node = p.readValueAsTree();
        Project project = (Project) p.currentValue();
        // Do some processing logic
        System.out.println(project);
        return mapper.readerForListOf(Asset.class).readValue(node);
    }
}
