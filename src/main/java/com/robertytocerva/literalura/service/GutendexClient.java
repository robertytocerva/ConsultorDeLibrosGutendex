package com.robertytocerva.literalura.service;

import com.robertytocerva.literalura.dto.GutendexBookDto;
import com.robertytocerva.literalura.dto.GutendexResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class GutendexClient {

    private final RestTemplate restTemplate;

    public GutendexClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<GutendexBookDto> searchFirstByTitle(String title) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl("https://gutendex.com/books/")
                .queryParam("search", title)
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUri();

        GutendexResponse r = restTemplate.getForObject(uri, GutendexResponse.class);
        if (r == null || r.results == null || r.results.isEmpty()) return Optional.empty();
        return Optional.of(r.results.get(0));
    }
}
