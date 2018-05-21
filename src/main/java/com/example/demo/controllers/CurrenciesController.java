package com.example.demo.controllers;

import com.example.demo.exceptions.UnsuportedCurrenciesException;
import com.example.demo.models.api.CurrenciesResponse;
import com.example.demo.models.api.UnsupportedCurrenciesErrorResponse;
import com.example.demo.services.CurrenciesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Slf4j
@Api(
        tags = "Endpoint for exchange common operations",
        value = "Common exchanges operations",
        produces = MediaType.APPLICATION_JSON_VALUE
)
@RestController
@RequestMapping(value = "/api/currencies", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrenciesController {

    private final CurrenciesService currenciesService;

    @Autowired
    public CurrenciesController(CurrenciesService currenciesService) {
        this.currenciesService = currenciesService;
    }

    @ExceptionHandler(UnsuportedCurrenciesException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public UnsupportedCurrenciesErrorResponse unsupportedCurrenciesException(HttpServletRequest req, UnsuportedCurrenciesException ex) {
        return new UnsupportedCurrenciesErrorResponse("Not supported currencies in params", ex.getUnsupportedCurrenciesFrom(), ex.getUnsupportedCurrenciesTo());
    }

    @ApiOperation(
            value = "Currencies endpoint",
            notes = "An endpoint to get currencies tickers",
            response = CurrenciesResponse.class,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public CurrenciesResponse getCurrencies(
                                        @ApiParam(value = "From what currencies to convert")
                                        @RequestParam(value = "from") String from,
                                        @ApiParam(value = "To what currencies to convert")
                                        @RequestParam(value = "to") String to
    ) {
        return currenciesService.getCurrencies(Arrays.asList(from.split(",")), Arrays.asList(to.split(",")));
    }

}
