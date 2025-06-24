package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@JsonPropertyOrder({
        "fullName",
        "description",
        "cloneUrl",
        "watchersCount",
        "createdAt"
})
public class GitHubRepoDto {

    private String fullName;

    private String description;

    private String cloneUrl;

    private Integer watchersCount;

    private String createdAt;

}

