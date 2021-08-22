package com.mightyblock.posts.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@Profile("test")
@Configuration
public class ClockTestConfiguration {
    @Bean
    @Primary
    Clock fixedClock() {
        return Clock.fixed(
                Instant.parse("2020-12-01T10:05:23.653Z"),
                ZoneId.of("America/Argentina/Buenos_Aires"));
    }
}
