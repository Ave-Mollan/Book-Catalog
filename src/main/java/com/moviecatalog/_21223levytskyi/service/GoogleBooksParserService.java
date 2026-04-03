package com.moviecatalog._21223levytskyi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moviecatalog._21223levytskyi.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class GoogleBooksParserService {

    @Value("${openlibrary.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public BookDto fetchByTitle(String title) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(apiUrl)
                .queryParam("title", title)
                .queryParam("limit", 1);

        try {
            String json = restTemplate.getForObject(builder.toUriString(), String.class);
            JsonNode root = objectMapper.readTree(json);
            JsonNode docs = root.path("docs");
            if (!docs.isArray() || docs.isEmpty()) {
                throw new RuntimeException("Book not found in Open Library: " + title);
            }

            JsonNode doc = docs.get(0);
            BookDto dto = new BookDto();
            dto.setTitle(doc.path("title").asText(""));

            JsonNode authors = doc.path("author_name");
            if (authors.isArray() && !authors.isEmpty()) {
                dto.setAuthor(authors.get(0).asText(""));
            }

            if (doc.path("first_publish_year").isInt()) {
                dto.setPublishYear(doc.path("first_publish_year").asInt());
            }

            if (doc.path("cover_i").isInt()) {
                int coverId = doc.path("cover_i").asInt();
                dto.setCoverUrl("https://covers.openlibrary.org/b/id/" + coverId + "-L.jpg");
            }

            dto.setDescription("");
            dto.setRating(null);
            return dto;
        } catch (HttpClientErrorException.TooManyRequests e) {
            throw new RuntimeException("Open Library temporarily limits requests. Please try again in a minute.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Open Library response", e);
        }
    }
}
