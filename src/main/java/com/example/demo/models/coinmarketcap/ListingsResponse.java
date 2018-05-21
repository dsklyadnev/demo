package com.example.demo.models.coinmarketcap;

import lombok.Data;

import java.util.List;

@Data
public class ListingsResponse {

    List<Listing> data;

}
