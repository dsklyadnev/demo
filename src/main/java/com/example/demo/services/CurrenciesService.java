package com.example.demo.services;

import com.example.demo.exceptions.UnsuportedCurrenciesException;
import com.example.demo.models.api.CurrenciesResponse;
import com.lucadev.coinmarketcap.CoinMarketCap;
import com.lucadev.coinmarketcap.Currency;
import com.lucadev.coinmarketcap.model.CoinMarket;
import com.lucadev.coinmarketcap.model.CoinMarketList;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CurrenciesService {

    private final Set<String> supportedCurrencies = new HashSet<String>() {{
        add("USD"); add("BTC"); add("ETH"); add("LTC");
    }};

    private Map <String, Map<String, Double>> prices = new HashMap<>();

    public CurrenciesResponse getCurrencies(@NonNull List<String> from, @NonNull List<String> to) {
        List <String> notSupportedFrom = getNotSupportedCurrencies(from);
        List <String> notSupportedTo = getNotSupportedCurrencies(to);
        if (!notSupportedFrom.isEmpty() || !notSupportedTo.isEmpty()) {
            throw new UnsuportedCurrenciesException(notSupportedFrom, notSupportedTo);
        }

        CurrenciesResponse resp = new CurrenciesResponse();

        for (String fromCurrency : from) {
            resp.getPrices().put(fromCurrency, new HashMap<>());
            for (String toCurrency : to) {
                if (!fromCurrency.equals(toCurrency)) {
                    if (fromCurrency.equals("USD")) {
                        resp.getPrices().get(fromCurrency).put(toCurrency, 1/prices.get(toCurrency).get(fromCurrency));
                    } else {
                        resp.getPrices().get(fromCurrency).put(toCurrency, prices.get(fromCurrency).get(toCurrency));
                    }
                }
            }
        }

        return resp;
    }

    private List <String> getNotSupportedCurrencies(@NonNull List<String> currencies) {
        return currencies.stream().filter(currency -> !isCurrencySupported(currency))
                .collect(Collectors.toList());
    }

    private boolean isCurrencySupported(@NonNull String currency) {
        return supportedCurrencies.contains(currency);
    }

    @Scheduled(fixedDelay = 60000)
    private void updateTickers() {
        Set <String> supportedCryptoCurrencies = new HashSet<String>(){{
            add("BTC"); add("ETH"); add("LTC");
        }};
        CoinMarketList coinMarkets  = CoinMarketCap.ticker().get();

        for (String fromCurrency : supportedCryptoCurrencies) {
            Set<String> toCurrencies = new HashSet<>(supportedCryptoCurrencies);
            toCurrencies.remove(fromCurrency);

            prices.put(fromCurrency, new HashMap<>());
            CoinMarket fromCoinMarket = coinMarkets.getBySymbol(fromCurrency);

            prices.get(fromCurrency).put("USD", fromCoinMarket.getUSDPriceQuote().getPrice());

            for (String toCurrency : toCurrencies) {
                Currency toCoinCurrency = Currency.valueOf(toCurrency);
                CoinMarket toCoinMarket = CoinMarketCap.ticker(fromCoinMarket.getId()).convert(toCoinCurrency).get();
                prices.get(fromCurrency).put(toCurrency, toCoinMarket.getPriceQuote(toCoinCurrency).getPrice());
            }
        }
        log.info("Prices updated");
    }

}
