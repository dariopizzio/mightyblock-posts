package com.mightyblock.posts.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * This Dto is sent to the requester if there was an error
 */
@ApiModel(description = "Class that wraps all the errors sent to the client")
@Data
@AllArgsConstructor
public class ApiErrorDto{

    @ApiModelProperty(value = "Timestamp when the error was thrown")
    private Timestamp timestamp;
    @ApiModelProperty(value = "Status that indicates what kind of error is")
    private int status;
    @ApiModelProperty(value = "Description about the error")
    private String error;
    @ApiModelProperty(value = "Path that was invoked")
    private String path;
}
