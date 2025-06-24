package com.dawkastresu.git_proxy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/repos")
public class RepoController {

    private final GitProxyClient gitProxyClient;
    private final GitRepoMapper mapper;
    private final GitHubRepoService service;

    @GetMapping("/{owner}/{repo}")
    public GitHubRepoDto fetchRepo(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        return service.getRepo(owner, repo);
    }

    @PostMapping("/{owner}/{repo}")
    public GitHubRepoDto saveDetails(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        return service.saveRepoDetails(owner, repo);
    }

}
