package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonPropertyOrder({
        "full_name",
        "description",
        "clone_url",
        "watchers_count",
        "created_at"
})
public class GitHubRepo {

    @JsonProperty("full_name")
    private String fullName;

    private String description;

    @JsonProperty("clone_url")
    private String cloneUrl;

    @JsonProperty("watchers_count")
    private Integer watchersCount;

    @JsonProperty("created_at")
    private String createdAt;
}
