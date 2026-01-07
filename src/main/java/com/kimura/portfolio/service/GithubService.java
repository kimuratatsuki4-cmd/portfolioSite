package com.kimura.portfolio.service;

import com.kimura.portfolio.dto.GithubRepositoryDto;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

@Service
public class GithubService {

    private final RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Value("${portfolio.github.api-url}")
    private String githubApiUrl;

    public GithubService() {
        this.restTemplate = new RestTemplate();
    }

    public List<GithubRepositoryDto> getRepositories() {
        try {
            ResponseEntity<GithubRepositoryDto[]> response = restTemplate.getForEntity(
                    githubApiUrl,
                    GithubRepositoryDto[].class);
            if (response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception e) {
            // エラーハンドリング：ログを出して空リストを返すなど
            // 今回は簡易的に空リストを返却
            System.err.println("GitHub API Error: " + e.getMessage());
        }
        return Collections.emptyList();
    }
}
