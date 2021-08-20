package com.mightyblock.posts.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreatePostRequest {

    @NotNull(message = "description cannot be null")//TODO check size!
    private String description;
    @NotNull(message = "imagePath cannot be null")
    private String imagePath;
}
