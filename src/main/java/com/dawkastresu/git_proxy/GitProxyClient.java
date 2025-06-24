package com.dawkastresu.git_proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "gitHubClient", configuration = FeignConfig.class)
public interface GitProxyClient {

    @GetMapping("/repos/{owner}/{repo}")
    GitHubRepo getRepository(@PathVariable("owner") String owner, @PathVariable("repo") String repo);

}
