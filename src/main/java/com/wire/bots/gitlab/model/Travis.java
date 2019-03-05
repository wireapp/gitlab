package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Travis {
    @JsonProperty
    public String id;

    @JsonProperty
    public String state;

    @JsonProperty
    public String build_url;

    @JsonProperty
    public String message;

    @JsonProperty
    public String compare_url;

    @JsonProperty
    public String author_name;

    @JsonProperty
    public Repository repository;
}
