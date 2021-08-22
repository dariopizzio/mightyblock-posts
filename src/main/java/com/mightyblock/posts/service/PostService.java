package com.mightyblock.posts.service;

import com.mightyblock.posts.dto.CreatePostRequest;
import com.mightyblock.posts.dto.PostDto;
import com.mightyblock.posts.model.Like;
import com.mightyblock.posts.model.Post;
import com.mightyblock.posts.model.exceptions.ApiException;
import com.mightyblock.posts.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository repository;

    @Autowired
    Clock now;

    /**
     * @param createPostRequest to create
     * @param userId
     * @return String postId created
     */
    public String createPost(CreatePostRequest createPostRequest, String userId) {
        Post post = new Post(userId, createPostRequest.getDescription(), createPostRequest.getImagePath(), now);
        Post postSaved = repository.save(post);
        return postSaved.getId();
    }

    /**
     * Deletes a post given an postId
     * @param postId postId to delete
     */
    public void deletePost(String postId) {
        repository.deleteById(postId);//TODO validates if is the owner of the post!
    }

    /**
     * @param paging indicates the current page and the size of each page
     * @return List of posts
     */
    public Page<PostDto> findAll(Pageable paging) {
        Page<Post> postList = repository.findAll(paging);//TODO return if the user liked the post, to check if he could like it after
        return postList.map(it -> convertPostToDto(it));
    }

    /**
     * Function to like/dislike posts
     * Like if you did not like it before and Dislike if you already like it
     * @param postId postId to like
     * @throws ApiException when the post does not exist
     */
    public Post likePost(String postId, String userId) throws ApiException {
        Post postToLike = repository.findById(postId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Post not found", "/posts/like"));
        List<Like> likesList = postToLike.getLikes().stream().filter(like -> like.getUserId().equals(userId)).collect(Collectors.toList());
        //Max size should be 1
        if(likesList.size() == 0){
            postToLike.getLikes().add(new Like(userId, now));
        }else{
            postToLike.getLikes().removeAll(likesList);
        }
        postToLike.setLikeCounter(postToLike.getLikes().size());
        return repository.save(postToLike);
    }

    private PostDto convertPostToDto(Post post){
        return new PostDto(post.getId(), post.getUserId(), post.getDescription(), post.getImagePath(), post.getLikeCounter());
    }
}
