package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Credential implements CloudStorageModel {

     private Integer credentialId;
     private String url;
     private String username;
     private String key;
     private String password;
     private Integer userId;

    public Credential(Integer credentialId, String url, String username, String key, String password, Integer userId) {
        this.credentialId = credentialId;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    @Override
    public String printName() {
        return "credential";
    }
}
