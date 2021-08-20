package com.mightyblock.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

/**
 * This Dto is sent to the requester if there was an error
 */
@Data
@AllArgsConstructor
public class ApiErrorDto{

    private Timestamp timestamp;
    private int status;
    private String error;
    private String path;
}
