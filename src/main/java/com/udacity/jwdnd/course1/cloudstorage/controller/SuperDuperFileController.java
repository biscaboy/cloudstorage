package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.ResponsePackingService;
import com.udacity.jwdnd.course1.cloudstorage.services.ValidationService;
import com.udacity.jwdnd.course1.cloudstorage.services.SuperDuperFileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.commons.text.StringEscapeUtils;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("files")
public class SuperDuperFileController {

    private UserService userService;
    private SuperDuperFileService fileService;
    private ValidationService validationService;
    private ResponsePackingService packingService;

    public SuperDuperFileController(UserService userService,
                                    SuperDuperFileService fileService,
                                    ValidationService validationService,
                                    ResponsePackingService packingService) {
        this.userService = userService;
        this.fileService = fileService;
        this.validationService = validationService;
        this.packingService = packingService;
    }

    @GetMapping("/delete")
    public String deleteFile(Authentication authentication, @ModelAttribute SuperDuperFile file, Model model) {

        User currentUser = userService.getUser(authentication.getName());
        file = fileService.getFile(file.getFileId());

        boolean result = validationService.validate(file, currentUser, model,"delete");

        if (result) {
            result = fileService.deleteFile(file);
        }

        return packingService.packResultResponse(result, model, currentUser, fileService);
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response, Authentication authentication,
                             @ModelAttribute SuperDuperFile file, Model model) throws IOException {

        User currentUser = userService.getUser(authentication.getName());
        file = fileService.getFile(file.getFileId());

        boolean result = validationService.validate(file, currentUser, model,"download");

        if (result) {
            // modify response
            response.setContentType(file.getContentType());
            response.setContentLength(Integer.valueOf(file.getSize()));
            // force download
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + StringEscapeUtils.escapeHtml4(file.getName());
            response.setHeader(headerKey, headerValue);
            response.getOutputStream().write(file.getData());
        }
    }


    @PostMapping()
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multiPartFile, Model model) {

        User currentUser = userService.getUser(authentication.getName());
        SuperDuperFile file = fileService.upload(multiPartFile, currentUser);

        boolean result = validationService.validate(file, currentUser, model,"create/edit");

        if (result) {
            try {
                fileService.createFile(file);
            } catch (InvalidFileNameException e) {
                result = false;
                model.addAttribute("message", e.getMessage());
            }
        }

        return packingService.packResultResponse(result, model, currentUser, fileService);
    }
}
