package com.dawkastresu.git_proxy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GitRepoMapper {

    GitHubRepoDto toDto(GitHubRepo repo);

    GitRepoEntity toEntity(GitHubRepo repo);

    GitHubRepoDto toDto(GitRepoEntity entity);
}
