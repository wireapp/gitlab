package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("avatar_url")
    public String avatarUrl;

    @JsonProperty("login")
    public String login;
}
