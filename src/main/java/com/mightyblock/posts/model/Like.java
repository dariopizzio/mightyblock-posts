package com.mightyblock.posts.model;

import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@Getter
public class Like implements Serializable {

    private String userId;
    private Date likeDate;

    public Like(String userId){
        this.userId = userId;
        this.likeDate = new Date();
    }
}
