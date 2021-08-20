package com.mightyblock.posts.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDto {

    private String id;
    private String userId;
    private String description;
    private String imagePath;
    private int likeCounter;
}
