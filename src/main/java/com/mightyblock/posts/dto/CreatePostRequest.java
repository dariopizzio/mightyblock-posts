package com.mightyblock.posts.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Request object to create a post")
@AllArgsConstructor
@Data
public class CreatePostRequest {

    @ApiModelProperty(value = "Post description")
    @NotNull(message = "description cannot be null")//TODO check size!
    private String description;
    @ApiModelProperty(value = "Picture path of the post")
    @NotNull(message = "imagePath cannot be null")
    private String imagePath;
}
