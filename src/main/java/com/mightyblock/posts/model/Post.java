package com.mightyblock.posts.model;

import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Document(collection = "posts")
public class Post implements Serializable {

    @Id
    private String id;
    private String userId;
    private String description;//TODO check size!
    private String imagePath;
    private Date uploadTime;
    @Setter
    private int likeCounter;
    private List<Like> likes;

    public Post(String userId, String description, String imagePath, Clock now){
        this.userId = userId;
        this.description = description;
        this.imagePath = imagePath;
        this.uploadTime = new Date(now.millis());
        this.likeCounter = 0;
        this.likes = new ArrayList<>();
    }
}
