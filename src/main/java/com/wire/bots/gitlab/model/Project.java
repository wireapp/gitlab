package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    @JsonProperty
    public String path_with_namespace;

    @JsonProperty
    public String homepage;

    @JsonProperty
    public String web_url;
}
