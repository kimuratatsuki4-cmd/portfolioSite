package com.kimura.portfolio.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class GithubRepositoryDto {
    private String name;
    private String description;

    @JsonProperty("html_url")
    private String htmlUrl;

    private String language;

    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    @JsonProperty("stargazers_count")
    private int stargazersCount;

    // GitHub Topics (タグ) を受信するためのフィールド
    private List<String> topics;
}
