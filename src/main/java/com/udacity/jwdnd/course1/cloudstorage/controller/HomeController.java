package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.SuperDuperFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private UserService userService;
    private NoteService noteService;
    private SuperDuperFileService fileService;
    private CredentialService credentialService;

    public HomeController(UserService userService,
                          NoteService noteService,
                          CredentialService credentialService,
                          SuperDuperFileService fileService) {
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping
    public String displayHome(Authentication authentication, Note note, Credential credential,
                              SuperDuperFile file, Model model) {
        // populate model objects
        User currentUser = userService.getUser(authentication.getName());
        model.addAttribute(currentUser);
        model.addAttribute("files", fileService.getFiles(currentUser));
        model.addAttribute("notes", noteService.getNotes(currentUser));
        model.addAttribute("credentials", credentialService.getCredentials(currentUser));
        return "home";
    }
}
