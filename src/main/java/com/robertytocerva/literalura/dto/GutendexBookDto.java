package com.robertytocerva.literalura.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class GutendexBookDto {
    public Long id;
    public String title;
    public List<GutendexAuthorDto> authors;
    public List<String> languages;
    @JsonProperty("download_count")
    public Integer downloadCount;
}