package com.mightyblock.posts.config;

import com.mightyblock.posts.repository.PostRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class PostRepositoryTestConfiguration {
    @Bean
    @Primary
    public PostRepository postRepository() {
        return Mockito.mock(PostRepository.class);
    }
}
