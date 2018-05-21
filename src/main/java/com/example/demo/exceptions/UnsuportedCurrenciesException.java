package com.example.demo.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class UnsuportedCurrenciesException extends RuntimeException {

    List<String> unsupportedCurrenciesFrom;
    List<String> unsupportedCurrenciesTo;

    public UnsuportedCurrenciesException(List<String> unsupportedCurrenciesFrom, List<String> unsupportedCurrenciesTo) {
        super("Not supported currencies");
        this.unsupportedCurrenciesFrom = unsupportedCurrenciesFrom;
        this.unsupportedCurrenciesTo = unsupportedCurrenciesTo;
    }

}
