package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.ResponsePackingService;
import com.udacity.jwdnd.course1.cloudstorage.services.ValidationService;
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
    private ValidationService validationService;
    private ResponsePackingService packingService;

    public CredentialController(UserService userService,
                                CredentialService credentialService,
                                ValidationService validationService,
                                ResponsePackingService packingService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.validationService = validationService;
        this.packingService = packingService;
    }

    @GetMapping("/delete")
    public String delete(Authentication authentication, @ModelAttribute Credential credential, Model model){

        User currentUser = userService.getUser(authentication.getName());
        credential = credentialService.getCredential(credential.getCredentialId());

        boolean result = validationService.validate(credential, currentUser, model, "delete");

        if (result) {
            result = credentialService.deleteCrediential(credential);
        }

        return packingService.packResultResponse(result, model, currentUser, credentialService);
    }

    @GetMapping("/decrypt")
    public void decryptPassword(HttpServletResponse response, Authentication authentication,
                                @ModelAttribute Credential credential) throws IOException {
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

        boolean result = false;
        User currentUser = userService.getUser(authentication.getName());

        if (credential.getCredentialId() == null) {
            credential.setUserId(currentUser.getUserId());
            result = credentialService.createCredential(credential);
        } else {
            Credential existing = credentialService.getCredential(credential.getCredentialId());
            result = validationService.validate(existing, currentUser, model, "edit");
            if (result) {
                credential.setUserId(currentUser.getUserId());
                result = credentialService.updateCredential(credential);
            }
        }
        return packingService.packResultResponse(result, model, currentUser, credentialService);
    }
}
