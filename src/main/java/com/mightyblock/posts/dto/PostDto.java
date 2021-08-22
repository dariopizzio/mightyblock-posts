package com.mightyblock.posts.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "Class that represents a post")
@Data
@AllArgsConstructor
public class PostDto {

    @ApiModelProperty(value = "Post id")
    private String id;
    @ApiModelProperty(value = "User id of the creator of the post")
    private String userId;
    @ApiModelProperty(value = "Post description")
    private String description;
    @ApiModelProperty(value = "Picture path of the post")
    private String imagePath;
    @ApiModelProperty(value = "Likes counter, by default is 0")
    private int likeCounter;
    @ApiModelProperty(value = "Upload time")
    private Date uploadTime;
}
