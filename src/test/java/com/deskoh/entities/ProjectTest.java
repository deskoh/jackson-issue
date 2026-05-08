package com.deskoh.entities;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.StreamReadFeature;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProjectTest {

    private final ObjectMapper mapper = JsonMapper.builder()
        .disable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION)
        .build();

    @Test
    public void given_project_can_deserialize() throws IOException {
        // arrange
        var stream = this.getClass().getClassLoader().getResourceAsStream("project.json");

        // act
        var mission = mapper.readValue(stream, Project.class);

        // assert
        assertTrue(mission instanceof Project);
        assertTrue(mission.getAssets().getFirst() != null);
    }
}
