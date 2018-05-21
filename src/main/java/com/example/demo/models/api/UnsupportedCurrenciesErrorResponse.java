package com.example.demo.models.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class UnsupportedCurrenciesErrorResponse {

    @ApiModelProperty(value = "Error message", required = true)
    private String message;

    @ApiModelProperty(value = "List of from currencies that are not supported")
    private List<String> from;

    @ApiModelProperty(value = "List of to currencies that are not supported")
    private List<String> to;

}
