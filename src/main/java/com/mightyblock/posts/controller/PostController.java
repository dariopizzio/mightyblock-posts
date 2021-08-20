package com.mightyblock.posts.controller;

import com.mightyblock.posts.config.authentication.TokenProvider;
import com.mightyblock.posts.dto.CreatePostRequest;
import com.mightyblock.posts.model.exceptions.ApiException;
import com.mightyblock.posts.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for every endpoint related with posts
 */
@Controller
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    TokenProvider tokenProvider;

    /**
     * @param post post to create
     * @return HttpStatus ok = 200 if the user was created
     */
    @PostMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> createPost(@RequestHeader(value="Authorization") String authorizationHeader,
                                             @RequestBody CreatePostRequest post) {
        String userId = tokenProvider.getUserId(authorizationHeader);
        postService.createPost(post, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * @param postId postId to delete
     * @return HttpStatus ok = 200 if the operation was ok
     */
    @DeleteMapping(value = "/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> deletePost(@PathVariable String postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    /**
     * Get all the posts arranged chronologically and supporting pagination of content
     * @param page current page, default = 0
     * @param size size of each page, default = 10
     * @return List of posts
     */
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getPosts(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("uploadTime").descending());
        return ResponseEntity.ok(postService.findAll(paging));
    }//TODO return if the user liked the post, to check if he could like it after

    /**
     * Function to like/dislike posts
     * @param postId postId to like
     * @return HttpStatus ok = 200 if the post was liked ok
     */
    @PostMapping(value = "/like", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> likePost(@RequestHeader(value="Authorization") String authorizationHeader,
                                           @RequestParam String postId) throws ApiException {
        String userId = tokenProvider.getUserId(authorizationHeader);
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
