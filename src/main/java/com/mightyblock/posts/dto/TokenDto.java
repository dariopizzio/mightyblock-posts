package com.mightyblock.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Login response for httpStatus = ok
 */
@Data
@AllArgsConstructor
public class TokenDto {

    private String token;
    private Long expireTime;
}
