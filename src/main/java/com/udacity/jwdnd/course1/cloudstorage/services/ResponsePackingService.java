package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

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
