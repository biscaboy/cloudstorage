package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadExceptionAdvice {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException exc, HttpServletRequest request, HttpServletResponse response) {

        // get the max upload files size by reading max size from the error message.
        int lDlm = exc.getCause().getMessage().lastIndexOf('(') + 1;
        int rDlm = exc.getCause().getMessage().length() -1;
        String maxFileSize = exc.getCause().getMessage().substring(lDlm, rDlm);
        int maxfs = Integer.valueOf(maxFileSize); // in bytes
        int maxfsMB = maxfs / 1000000; // in MB

        ModelAndView modelAndView = new ModelAndView("result");
        modelAndView.getModel().put("nav", "home");
        modelAndView.getModel().put("success", false);
        modelAndView.getModel().put("message", "The file is too large to upload (> " + maxfsMB + " MB).");

        return modelAndView;
    }
}
