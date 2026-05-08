package com.deskoh.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class Project {
    private SourceSystem source;
    @JsonDeserialize(using = AssetDeserializer.class)
    private List<Asset> assets;

    public Project() {
    }

    public SourceSystem getSource() {
        return source;
    }

    public void setSource(SourceSystem source) {
        this.source = source;
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }
}
