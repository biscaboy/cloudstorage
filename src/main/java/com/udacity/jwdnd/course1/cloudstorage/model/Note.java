package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Note {

    @Getter @Setter private Integer noteId;
    @Getter @Setter private String noteTitle;
    @Getter @Setter private String noteDescription;
    @Getter @Setter private Integer userId;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }
}
