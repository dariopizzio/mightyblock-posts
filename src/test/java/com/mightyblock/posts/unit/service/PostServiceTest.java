package com.mightyblock.posts.unit.service;

import com.mightyblock.posts.config.PostRepositoryTestConfiguration;
import com.mightyblock.posts.dto.CreatePostRequest;
import com.mightyblock.posts.model.Post;
import com.mightyblock.posts.repository.PostRepository;
import com.mightyblock.posts.service.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PostRepository.class, PostRepositoryTestConfiguration.class})
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    private PostRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void initMocks(){
        Post postCreated = new Post("userId", "description", "path");
        Post newPostWithId = new Post("userId", "description", "path");
        newPostWithId.setId("id1");
        Mockito.when(repository.save(postCreated)).thenReturn(newPostWithId);
    }

    @Test
    void createPost_shouldReturnAnUserId() {
        CreatePostRequest postToCreate = new CreatePostRequest("description", "path");
        String id = postService.createPost(postToCreate, "userId");
        Assert.assertEquals("id1", id);
    }

    @Test
    void findAll_shouldReturnAnEmptyList() {
        //TODO implement!
    }

    @Test
    void findAll_shouldReturnANonEmptyList() {
        //TODO implement!
    }

    @Test
    void like_shouldThrowAPostNotFound() {
        //TODO implement!
    }

    @Test
    void like_shouldReturnAPostWith1Likes() {
        //TODO implement!
    }

    @Test
    void like_shouldReturnAPostWithoutLikes() {
        //TODO implement!
    }
}
