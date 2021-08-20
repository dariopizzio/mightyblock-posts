package com.mightyblock.posts.config.web;

import com.mightyblock.posts.config.authentication.TokenProvider;
import com.mightyblock.posts.config.filter.AuthorizationFilter;
import com.mightyblock.posts.utils.PostConstants;
import com.mightyblock.posts.utils.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    UtilsService utils;

    /**
     * Security configuration
     * AuthorizationFilter: Filter to validate every request
     * In dev and test environments the swagger urls are allowed
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .addFilterBefore(new AuthorizationFilter(tokenProvider, utils), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(utils.getProperty(PostConstants.FILTER_EXCLUDED_URLS, String[].class)).permitAll()
                .anyRequest().authenticated();
    }
}
