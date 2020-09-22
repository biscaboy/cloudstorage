package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int updateNote(Note note){
        return this.noteMapper.update(note);
    }

    public int createNote(Note note) {
        return noteMapper.insert(note);
    }

    public ArrayList<Note> getNotes(User user){
        return noteMapper.getNotes(user);
    }

    public boolean deleteNote(Note note){
        return noteMapper.delete(note) == 1;
    }

    public int deleteNotes(User user) {return noteMapper.deleteNotes(user);}

    public int deleteAllNotes() {
        return noteMapper.deleteAll();
    }
}
