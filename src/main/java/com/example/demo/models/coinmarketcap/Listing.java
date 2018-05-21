package com.example.demo.models.coinmarketcap;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Listing {

    private Long   id;

    private String name;

    private String symbol;

    @JsonProperty("website_slug")
    private String websiteSlug;

}
