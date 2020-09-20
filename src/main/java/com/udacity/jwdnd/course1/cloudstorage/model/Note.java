package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Note {

    @Getter @Setter private Integer id;
    @Getter @Setter private String title;
    @Getter @Setter private String description;
    @Getter @Setter private Integer userId;

    public Note(Integer id, String title, String description, Integer userId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.userId = userId;
    }
}
