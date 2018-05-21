package com.example.demo.models.api;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class CurrenciesResponse {

    private Map <String, Map<String, Double>> prices = new HashMap<>();

}
