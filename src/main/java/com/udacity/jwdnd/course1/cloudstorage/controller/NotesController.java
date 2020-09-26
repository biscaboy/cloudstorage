package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private UserService userService;
    private NoteService noteService;

    public NotesController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping("/delete")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note, Model model) {

        boolean _result;

        User _currentUser = userService.getUser(authentication.getName());

        _result = noteService.deleteNote(note);

        model.addAttribute("success", _result);
        model.addAttribute("notes", noteService.getNotes(_currentUser));
        model.addAttribute("nav", "/home#nav-notes");
        return "result";
    }

    @PostMapping()
    public String editNote(Authentication authentication, @ModelAttribute Note note, Model model) {

        boolean _result;

        User _currentUser = userService.getUser(authentication.getName());
        note.setUserId(_currentUser.getUserId());

        // is this a new note or existing note?
        // new notes don't have an id yet.
        if (note.getNoteId() == null) {
            _result = noteService.createNote(note);
        } else {
            _result = noteService.updateNote(note);
        }

        model.addAttribute("success", _result);
        model.addAttribute("notes", noteService.getNotes(_currentUser));
        model.addAttribute("nav", "/home#nav-notes");
        return "result";
    }
}
