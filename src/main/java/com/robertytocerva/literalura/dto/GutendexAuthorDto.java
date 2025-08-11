package com.robertytocerva.literalura.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GutendexAuthorDto {
    public String name;
    @JsonProperty("birth_year")
    public Integer birthYear;
    @JsonProperty("death_year")
    public Integer deathYear;
}