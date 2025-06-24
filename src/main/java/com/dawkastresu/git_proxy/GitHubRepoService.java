package com.dawkastresu.git_proxy;

import com.github.tomakehurst.wiremock.admin.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GitHubRepoService {

    private final GitProxyClient gitProxyClient;
    private final GitRepoMapper mapper;
    private final RepoRepository repository;

    public GitHubRepoDto getRepo(String owner, String repo) {
        GitHubRepo raw =  gitProxyClient.getRepository(owner, repo);
        return mapper.toDto(raw);
    }

    @Transactional
    public GitHubRepoDto saveRepoDetails(String owner, String repo) {
        GitRepoEntity raw =  mapper.toEntity(gitProxyClient.getRepository(owner, repo));
        repository.save(raw);
        return mapper.toDto(raw);
    }

    @Transactional
    public GitHubRepoDto getRepoFromDb(String owner, String repo) {
        String fullName = owner + "/" + repo;
        return repository.findAll().stream()
                .filter(gitHubRepo -> gitHubRepo.getFullName().equalsIgnoreCase(fullName))
                .map(mapper::toDto)
                .findFirst()
                .orElseThrow();

    }

    public void deleteRepoFromDb(String owner, String repo) {
        String fullName = owner + "/" + repo;
        var gitRepo = repository.findAll().stream()
                .filter(gitHubRepo -> gitHubRepo.getFullName().equalsIgnoreCase(fullName))
                .findFirst()
                .orElseThrow();
        repository.delete(gitRepo);
    }

}
