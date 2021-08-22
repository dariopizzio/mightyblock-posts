package com.mightyblock.posts.controller;

import com.mightyblock.posts.config.authentication.TokenProvider;
import com.mightyblock.posts.dto.CreatePostRequest;
import com.mightyblock.posts.model.exceptions.ApiException;
import com.mightyblock.posts.service.PostService;
import io.swagger.annotations.*;
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
@Api(value = "Posts", tags = {"Posts"})
@Controller
@RequestMapping(value = "/posts")
public class PostController {

    final private String getResponseExample = "{\"content\": [{\"id\": \"id\",\"userId\": \"userId\",\"description\": \"description\",\"imagePath\": \"imagePath\",\"likeCounter\": 0}],\"pageable\": {\"sort\": {\"sorted\": true,\"unsorted\": false,\"empty\": false},\"pageNumber\": 0,\"pageSize\": 10,\"offset\": 0,\"paged\": true,\"unpaged\": false},\"totalPages\": 1,\"totalElements\": 1,\"last\": true,\"sort\": {\"sorted\": true,\"unsorted\": false,\"empty\": false},\"numberOfElements\": 1,\"first\": true,\"size\": 10,\"number\": 0,\"empty\": false}";

    @Autowired
    PostService postService;

    @Autowired
    TokenProvider tokenProvider;

    /**
     * @param post post to create
     * @return HttpStatus ok = 200 if the user was created
     */
    @ApiOperation(value = "Add a new post into the database")
    @PostMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> createPost(
            @ApiParam(value = "Header authorization that contains a jwt token", required = true) @RequestHeader(value="Authorization") String authorizationHeader,
            @ApiParam(value = "Post to add", required = true) @RequestBody CreatePostRequest post) {
        String userId = tokenProvider.getUserId(authorizationHeader);
        postService.createPost(post, userId);
        return ResponseEntity.ok().build();
    }

    /**
     * @param postId postId to delete
     * @return HttpStatus ok = 200 if the operation was ok
     */
    @ApiOperation(value = "Deletes an existing post")
    @DeleteMapping(value = "/{postId}", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> deletePost(
            @ApiParam(value = "PostId of the post to delete", required = true) @PathVariable String postId){
        postService.deletePost(postId);
        return ResponseEntity.ok().build();
    }

    /**
     * @param page current page, default = 0
     * @param size size of each page, default = 10
     * @return List of posts
     */
    @ApiOperation(value = "Get all the posts arranged chronologically and supporting pagination of content")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK",
                    examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = getResponseExample))
            )})
    @GetMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> getPosts(
            @ApiParam(value = "Current page") @RequestParam(defaultValue = "0") int page,
            @ApiParam(value = "Size of each page") @RequestParam(defaultValue = "10") int size){
        Pageable paging = PageRequest.of(page, size, Sort.by("uploadTime").descending());
        return ResponseEntity.ok(postService.findAll(paging));
    }

    /**
     * Function to like/dislike posts
     * @param postId postId to like
     * @return HttpStatus ok = 200 if the post was liked ok
     */
    @ApiOperation(value = "Action to like/dislike a post ")
    @PostMapping(value = "/like", produces = { MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<Object> likePost(
            @ApiParam(value = "Header authorization that contains a jwt token", required = true) @RequestHeader(value="Authorization") String authorizationHeader,
            @ApiParam(value = "PostId of the post to like/dislike", required = true) @RequestParam String postId) throws ApiException {
        String userId = tokenProvider.getUserId(authorizationHeader);
        postService.likePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
