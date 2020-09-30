package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class NoteService implements CloudStorageService {

    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public boolean updateNote(Note note){
        return this.noteMapper.update(note) == 1;
    }

    public boolean createNote(Note note) {
        return noteMapper.insert(note) == 1;
    }

    public ArrayList<Note> getNotes(User user){
        return noteMapper.getNotes(user);
    }

    public Note getNote(Integer noteId) { return noteMapper.getNote(noteId); }

    public boolean deleteNote(Note note){
        return noteMapper.delete(note) == 1;
    }

    @Override
    public String getCollectionName() {
        return "notes";
    }
}
