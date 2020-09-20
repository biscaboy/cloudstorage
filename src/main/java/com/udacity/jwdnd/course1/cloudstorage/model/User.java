package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.*;

@Data
public class User {
    @Getter @Setter private Integer userId;
    @Getter @Setter private String username;
    @Getter @Setter private String salt;
    @Getter @Setter private String password;
    @Getter @Setter private String firstName;
    @Getter @Setter private String lastName;

    public User(Integer userId, String username, String salt, String password, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.salt = salt;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
