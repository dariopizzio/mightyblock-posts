package com.mightyblock.posts.unit.service;

import com.mightyblock.posts.config.ClockTestConfiguration;
import com.mightyblock.posts.config.PostRepositoryTestConfiguration;
import com.mightyblock.posts.dto.CreatePostRequest;
import com.mightyblock.posts.model.Like;
import com.mightyblock.posts.model.Post;
import com.mightyblock.posts.model.exceptions.ApiException;
import com.mightyblock.posts.repository.PostRepository;
import com.mightyblock.posts.service.PostService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.Clock;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {PostRepository.class, PostRepositoryTestConfiguration.class, ClockTestConfiguration.class})
public class PostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock
    private Clock clock;

    @Mock
    private PostRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPost_shouldReturnAnUserId() {
        Post newPostWithId = new Post("userId", "description", "path", new Date(clock.millis()));
        newPostWithId.setId("id1");
        when(repository.save(Mockito.any())).thenReturn(newPostWithId);

        CreatePostRequest postToCreate = new CreatePostRequest("description", "path");
        String id = postService.createPost(postToCreate, "userId");
        Assertions.assertEquals("id1", id);
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
        when(repository.findById("postId")).thenReturn(Optional.empty());
        ApiException exception = Assert.assertThrows(ApiException.class,
                () -> postService.likePost("postId", "userId"));

        ApiException expectedException = new ApiException(HttpStatus.NOT_FOUND, "Post not found", "/posts/like", new Timestamp(clock.millis()));
        Assertions.assertEquals(expectedException, exception);
    }

    @Test
    void like_shouldReturnAPostWithoutLikes() throws ApiException {
        Post postToLike = getNewPost(null, "userId");
        when(repository.findById("postId")).thenReturn(Optional.of(postToLike));
        Post postToLikeWithId = getNewPost("postId", "userId");
        when(repository.save(postToLike)).thenReturn(postToLikeWithId);

        Post post = postService.likePost("postId", "userId");
        Assertions.assertEquals("postId", post.getId());
        Assertions.assertEquals(0, post.getLikeCounter());
        Assertions.assertEquals(0, post.getLikes().size());
    }

    @Test
    void like_shouldReturnAPostWith1Like() throws ApiException {
        Post postToLike = getNewPost("postId2", "userId2");
        when(repository.findById("postId2")).thenReturn(Optional.of(postToLike));
        Post postToLikeWithId = getNewPost("postId2", "userId2");
        Like like = new Like("userId2", new Date(clock.millis()));
        postToLikeWithId.setLikeCounter(1);
        postToLikeWithId.getLikes().add(like);
        when(repository.save(postToLike)).thenReturn(postToLikeWithId);

        Post post = postService.likePost("postId2", "userId2");
        Assertions.assertEquals("postId2", post.getId());
        Assertions.assertEquals(1, post.getLikeCounter());
        Assertions.assertEquals(1, post.getLikes().size());
    }

    @Test
    void like_shouldDislikePost() throws ApiException {
        Post postToLike = getNewPost("postId3", "userId3");
        Like like = new Like("userId3", new Date(clock.millis()));
        postToLike.getLikes().add(like);
        when(repository.findById("postId3")).thenReturn(Optional.of(postToLike));
        Post postToLikeWithId = getNewPost("postId3", "userId3");
        when(repository.save(postToLike)).thenReturn(postToLikeWithId);

        Post post = postService.likePost("postId3", "userId3");
        Assertions.assertEquals("postId3", post.getId());
        Assertions.assertEquals(0, post.getLikeCounter());
        Assertions.assertEquals(0, post.getLikes().size());
    }

    private Post getNewPost(String postId, String userId){
        Post newPost = new Post(userId, "description", "imagePath", new Date(clock.millis()));
        if(postId!=null){ newPost.setId(postId);}
        return newPost;
    }
}
