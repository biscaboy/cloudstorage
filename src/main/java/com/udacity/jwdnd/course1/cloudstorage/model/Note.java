package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;

@Data
public class Note implements CloudStorageModel{

    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

    public Note(Integer noteId, String noteTitle, String noteDescription, Integer userId) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userId = userId;
    }

    @Override
    public String printName() {
        return "note";
    }
}
