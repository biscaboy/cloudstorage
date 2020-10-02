package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * This service prepares responses to the template view layer
 * keeping responses consistent across all CloudStorageService objects.
 *
 * The Model will be packed with collections of objects, messages or errors
 * and returned for display on the Result view.
 */
@NoArgsConstructor
@Service
public class ResponsePackingService {

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
