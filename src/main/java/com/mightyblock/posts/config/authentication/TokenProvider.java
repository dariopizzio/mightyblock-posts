package com.mightyblock.posts.config.authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class TokenProvider {

    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    @Value("${conf.token.secretkey}")
    private String secretKey;

    /**
     * Function to validate the token of a request
     *
     * @param request request to evaluate
     * @return TokenDto generated token
     */
    public Claims validateToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER).replace(PREFIX, "");
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody();
    }

    /**
     * This function validates if the request has an Authorization header with the prefix Bearer
     *
     * @param request request to evaluate
     * @return boolean result of the validation
     */
    public boolean tokenExists(HttpServletRequest request) {
        String authenticationHeader = request.getHeader(HEADER);
        return (authenticationHeader != null && authenticationHeader.startsWith(PREFIX));
    }

    /**
     * returns userId from the token
     * @param authorizationHeader Authorization header content
     * @return userId from the token
     */
    public String getUserId(String authorizationHeader) {
        String token = authorizationHeader.substring(PREFIX.length());
        return (String) Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token).getBody().get("userId");
    }
}
