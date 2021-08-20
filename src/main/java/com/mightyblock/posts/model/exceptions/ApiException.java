package com.mightyblock.posts.model.exceptions;

import com.mightyblock.posts.dto.ApiErrorDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

/**
 * Custom Exception
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ApiException extends Exception {

    private Timestamp timestamp;
    private HttpStatus status;
    private String error;
    private String path;

    public ApiException(HttpStatus status, String error, String path){
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.status = status;
        this.error = error;
        this.path = path;
    }

    public ApiErrorDto getApiError(){
        return new ApiErrorDto(timestamp, status.value(), error, path);
    }
}
