package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.test.context.TestPropertySource;


import static com.github.tomakehurst.wiremock.client.WireMock.*;

@SpringBootTest
@AutoConfigureWireMock(port = 8083)
@TestPropertySource("classpath:application-test.properties")
public class GitRepoFeinClientTest {

    @Autowired
    WireMockServer wireMockServer;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GitProxyClient gitProxyClient;

    @BeforeEach
    void setup() {
        wireMockServer.start();
    }

    @AfterEach
    void shutdown() {
        wireMockServer.stop();
    }

    @Test
    void shouldReturnResponse() throws JsonProcessingException {
        GitHubRepoDto gitHubRepoDto = GitHubRepoDto.builder()
                        .fullName("/name/myrepo")
                            .build();

        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        wireMockServer.stubFor(
                get("/repos/name/myrepo")
                        .willReturn(okJson(objectMapper.writeValueAsString(gitHubRepoDto)))
        );

        GitHubRepo result = gitProxyClient.getRepository("name", "myrepo");

        Assertions.assertAll(
                () -> Assertions.assertEquals("/name/myrepo", result.getFullName())
        );

    }
}
