package com.wire.bots.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitResponse {
    @JsonProperty("object_kind")
    public String action;

    @JsonProperty
    public PullRequest pull_request;

    @JsonProperty
    public Comment comment;

    @JsonProperty
    public Issue issue;

    @JsonProperty
    public List<Commit> commits;

    @JsonProperty
    public User sender;

    @JsonProperty
    public String compare;

    @JsonProperty
    public Review review;

    @JsonProperty
    public Repository repository;

    @JsonProperty
    public boolean created;

    @JsonProperty
    public boolean deleted;

    @JsonProperty("user_username")
    public String username;

    @JsonProperty("user_name")
    public String user;
}
