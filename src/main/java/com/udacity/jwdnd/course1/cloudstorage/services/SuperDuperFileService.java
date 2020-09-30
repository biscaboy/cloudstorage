package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.SuperDuperFileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Service
public class SuperDuperFileService implements CloudStorageService {

    private SuperDuperFileMapper fileMapper;

    public SuperDuperFileService(SuperDuperFileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public boolean updateFile(SuperDuperFile file){
        return this.fileMapper.update(file) == 1;
    }

    public boolean createFile(SuperDuperFile file) throws InvalidFileNameException {
        if ( this.fileMapper.countFilesByFilename(file) > 0){
            throw new InvalidFileNameException(file.getName(), "The filename already exists.");
        }
        return this.fileMapper.insert(file) == 1;
    }

    public SuperDuperFile getFile(Integer fileId) { return this.fileMapper.getFile(fileId); }

    public ArrayList<SuperDuperFile> getFiles(User user){
        return this.fileMapper.getFiles(user);
    }

    public boolean deleteFile(SuperDuperFile file) {
        return this.fileMapper.deleteFile(file.getFileId()) == 1;
    }

    public int deleteAllNotes() {
        return fileMapper.deleteAll();
    }

    @Override
    public String getCollectionName() {
        return "files";
    }

    /**
     * Special thanks to the CodeJava.net for the explanation on MultiPartFile uplaods
     * https://www.codejava.net/coding/upload-files-to-database-with-spring-mvc-and-hibernate
     * Special thanks also to Atul Rai at Websparrow.org for a discussion about saving upload files
     * https://www.websparrow.org/spring/spring-boot-rest-api-file-upload-save-example
     * @param file the uploaded file
     * @return a meta-file with all attributes updated with the original uploaded file's metadata.
     */
    public SuperDuperFile upload(MultipartFile file, User user) {

        SuperDuperFile superDuperFile = null;

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            superDuperFile = new SuperDuperFile(
                    null,
                    file.getOriginalFilename(),
                    file.getContentType(),
                    String.valueOf(file.getSize()),
                    user.getUserId(),
                    file.getBytes());
        } catch (IOException e) {
            // @TODO log an error and throw a new exception (?)
            System.out.println(e.getMessage());
        }
        return superDuperFile;

    }
}
