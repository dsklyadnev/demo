package com.example.demo.models.coinmarketcap;

import lombok.Data;

import java.util.Map;
import java.util.Queue;

@Data
public class Ticker extends Listing {

    Map<String, Queue> quotes;

}
