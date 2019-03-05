package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {
    @JsonProperty
    public String html_url;

    @JsonProperty
    public String title;

    @JsonProperty
    public String body;

    @JsonProperty
    public User user;

    @JsonProperty
    public Integer number;
}
