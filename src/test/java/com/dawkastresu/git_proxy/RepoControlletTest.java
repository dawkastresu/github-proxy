package com.dawkastresu.git_proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class RepoControlletTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private GitHubRepoService service;

    @MockitoBean
    private RepoRepository repository;

    @Test
    public void shouldFetchRepo() throws Exception {
        String owner = "owner";
        String repo = "repo";
        GitHubRepoDto dto = new GitHubRepoDto("fullName", "description", "cloneUrl", 10, "createdAt");

        when(service.getRepo(owner, repo)).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.get("/repos/{owner}/{repo}", "owner", "repo")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("fullName"))
                .andExpect(jsonPath("$.description").value("description"));
    }
}
