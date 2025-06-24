package com.dawkastresu.git_proxy;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoRepository extends JpaRepository<GitRepoEntity, Long> {

}
