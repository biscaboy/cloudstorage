package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
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
@RequestMapping("/credentials")
public class CredentialController {

    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService,
                                CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/delete")
    public String delete(Authentication authentication, @ModelAttribute Credential credential, Model model){
        boolean result;

        User currentUser = userService.getUser(authentication.getName());

        result = credentialService.deleteCrediential(credential);

        model.addAttribute("success", result);
        model.addAttribute("credentials", credentialService.getCredentials(currentUser));
        model.addAttribute("nav", "/home#nav-credentials");
        return "result";
    }

    @PostMapping()
    public String editCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {

        boolean result;

        User currentUser = userService.getUser(authentication.getName());
        credential.setUserId(currentUser.getUserId());

        // is this a new credential or existing credential?
        // new credentials don't have an id yet.
        if (credential.getCredentialId() == null) {
            result = credentialService.createCredential(credential);
        } else {
            result = credentialService.updateCredential(credential);
        }

        model.addAttribute("success", result);
        model.addAttribute("credentials", credentialService.getCredentials(currentUser));
        model.addAttribute("nav", "/home#nav-credentials");
        return "result";
    }
}
