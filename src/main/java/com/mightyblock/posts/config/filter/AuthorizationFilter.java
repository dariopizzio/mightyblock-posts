package com.mightyblock.posts.config.filter;

import com.mightyblock.posts.config.authentication.TokenProvider;
import com.mightyblock.posts.utils.PostConstants;
import com.mightyblock.posts.utils.UtilsService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    private TokenProvider tokenProvider;
    private UtilsService utils;

    public AuthorizationFilter(TokenProvider tokenProvider, UtilsService utils) {
        this.tokenProvider = tokenProvider;
        this.utils = utils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!PatternMatchUtils.simpleMatch(utils.getProperty(PostConstants.FILTER_EXCLUDED_URLS, String[].class), req.getRequestURI())){
                if (tokenProvider.tokenExists(req)) {
                    Claims claims = tokenProvider.validateToken(req);
                    if (claims.get("authorities") != null) {
                        List<String> authorities = (List<String>) claims.get("authorities");
                        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
                                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    } else {
                        SecurityContextHolder.clearContext();
                    }
                } else {
                    SecurityContextHolder.clearContext();
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            SecurityContextHolder.clearContext();
        }finally {
            filterChain.doFilter(req, res);
        }
    }
}
