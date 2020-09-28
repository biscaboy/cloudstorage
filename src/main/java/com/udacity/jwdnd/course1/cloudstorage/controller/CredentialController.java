package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        boolean result = false;
        String message = null;

        User currentUser = userService.getUser(authentication.getName());
        credential = credentialService.getCredential(currentUser.getUserId());

        if (credential == null) {
            message = "The credential was not found or doesn't exist.";
        } else if (credential.getUserId().intValue() == currentUser.getUserId().intValue()) {
            result = credentialService.deleteCrediential(credential);
        } else {
            message = "Only the owner of a credential can delete it.";
        }

        if (message != null) {
            model.addAttribute("message", message);
        }
        model.addAttribute("success", result);
        model.addAttribute("credentials", credentialService.getCredentials(currentUser));
        model.addAttribute("nav", "/home#nav-credentials");
        return "result";
    }

    @GetMapping("/decrypt")
    public void decryptPassword(HttpServletResponse response, Authentication authentication, @ModelAttribute Credential credential, Model model) throws IOException {
        User currentUser = userService.getUser(authentication.getName());
        credential = credentialService.getCredential(credential.getCredentialId());

        // only decrypt the password if the present user is the owner of this credential
        if (credential != null && credential.getUserId().intValue() == currentUser.getUserId().intValue()) {
            String decryptedPassword = credentialService.decryptPassword(credential);
            response.getWriter().println(decryptedPassword);
        }
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
