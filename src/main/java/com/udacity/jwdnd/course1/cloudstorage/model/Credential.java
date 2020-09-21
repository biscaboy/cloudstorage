package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Credential {

     private Integer id;
     private String url;
     private String username;
     private String key;
     private String password;
     private Integer userId;

    public Credential(Integer id, String url, String username, String key, String password, Integer userId) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }
}
