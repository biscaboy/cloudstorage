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

        boolean result = false;
        String message = null;
        User currentUser = userService.getUser(authentication.getName());
        note = noteService.getNote(note.getNoteId());

        if (note.getUserId().intValue() == currentUser.getUserId().intValue()) {
            result = noteService.deleteNote(note);
        } else {
            message = "Only the note owner can delete this note.";
        }

        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("success", result);
        model.addAttribute("notes", noteService.getNotes(currentUser));
        model.addAttribute("nav", "/home#nav-notes");
        return "result";
    }

    @PostMapping()
    public String editNote(Authentication authentication, @ModelAttribute Note note, Model model) {

        boolean result = false;
        String message = null;

        User currentUser = userService.getUser(authentication.getName());
        note.setUserId(currentUser.getUserId());

        if (note == null) {
            message = "The note was not found or doesn't exist.";
        } else if (note.getUserId().intValue() != currentUser.getUserId().intValue()) {
            message = "Only the note owner can create or edit this note.";
        }
        // is this a new note or existing note? New notes don't have an id yet.
        else if (note.getNoteId() == null) {
            result = noteService.createNote(note);
        } else {
            result = noteService.updateNote(note);
        }

        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("success", result);
        model.addAttribute("notes", noteService.getNotes(currentUser));
        model.addAttribute("nav", "/home#nav-notes");
        return "result";
    }
}
