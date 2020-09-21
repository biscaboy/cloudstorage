package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SuperDuperFile {

    private Integer id;
    private String name;
    private String contentType;
    private String size;
    private Integer userId;
    private byte [] data;

    public SuperDuperFile(Integer id, String name, String contentType, String size, Integer userId, byte [] data) {
        this.id = id;
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.userId = userId;
        this.data = data;
    }

}