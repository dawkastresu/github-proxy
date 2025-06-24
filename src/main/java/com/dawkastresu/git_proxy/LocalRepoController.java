package com.dawkastresu.git_proxy;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/local/repos")
public class LocalRepoController {

    private final GitProxyClient gitProxyClient;
    private final GitRepoMapper mapper;
    private final GitHubRepoService service;

    @GetMapping("/{owner}/{repo}")
    public GitHubRepoDto getDetailsDb(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        return service.getRepoFromDb(owner, repo);
    }

    @DeleteMapping("/{owner}/{repo}")
    public void deleteRepo(@PathVariable("owner") String owner, @PathVariable("repo") String repo) {
        service.deleteRepoFromDb(owner, repo);
    }

}
