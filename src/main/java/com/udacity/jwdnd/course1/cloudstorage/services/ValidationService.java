package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * Service for common validation of objects that are passed to the server before
 * passing object to the model layer for saving, editing or deleting.
 */
@NoArgsConstructor
@Service
public class ValidationService {

    public boolean validate(CloudStorageModel obj, User user, Model model, String crudAction){
        String message = null;
        boolean result = true;

        if (obj == null) {
            message = "That's fishy.  What you're looking for couldn't be found.";
        } else if (user.getUserId().intValue() != obj.getUserId().intValue()) {
            message = "Only the owner of a " + obj.printName() +
                    " can " + crudAction + " it. ";
        }

        if (message != null) {
            model.addAttribute("message", message);
            result = false;
        }

        return result;
    }
}
