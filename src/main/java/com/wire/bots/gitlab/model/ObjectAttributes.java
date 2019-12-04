package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ObjectAttributes {
    @JsonProperty
    public String note;

    @JsonProperty
    public String url;

    @JsonProperty
    public String title;

    @JsonProperty
    public String description;

    @JsonProperty
    public String iid;

    @JsonProperty
    public String status;

    @JsonProperty
    public String id;

}
