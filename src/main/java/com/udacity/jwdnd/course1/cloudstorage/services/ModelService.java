package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageModel;
import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@NoArgsConstructor
@Service
public class ModelService {

    public boolean validate(CloudStorageModel obj, Authentication authentication,
                                      User user, Model model, String crudAction){
        String message = null;
        boolean result = true;

        // not sure if Spring will ever supply a null object, but just to be sure...
        if (obj == null) {
            message = "The " + obj.printName() + " was not found.";
        } else if (user.getUserId().intValue() != obj.getUserId().intValue()) {
            message = "Only the owner of a " + obj.printName() + " can " + crudAction + " it.";
        }

        if (message != null) {
            model.addAttribute("message", message);
            result = false;
        }

        return result;

    }

    public String packResultResponse(boolean result, Model model, User user,
                                SuperDuperFileService service) {
        model.addAttribute(service.getCollectionName(), service.getFiles(user));
        return this.packResultResponse(result, model, service.getCollectionName());
    }

    public String packResultResponse(boolean result, Model model, User user,
                                     NoteService service) {
        model.addAttribute(service.getCollectionName(), service.getNotes(user));
        return this.packResultResponse(result, model, service.getCollectionName());
    }

    public String packResultResponse(boolean result, Model model, User user,
                                     CredentialService service) {
        model.addAttribute(service.getCollectionName(), service.getCredentials(user));
        return this.packResultResponse(result, model, service.getCollectionName());
    }

    private String packResultResponse(boolean result, Model model, String serviceName) {
        model.addAttribute("success", result);
        model.addAttribute("nav", "/home#nav-" + serviceName);
        return "result";

    }
}
