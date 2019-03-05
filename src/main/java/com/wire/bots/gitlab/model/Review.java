package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Review {
    @JsonProperty("body")
    public String body;

    @JsonProperty("user")
    public User user;

    @JsonProperty("state")
    public String state;
}
