package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface SuperDuperFileMapper {

    @Results(id = "superDuperFileResultMap", value = {
            @Result(property = "fileId", column = "fileId"),
            @Result(property = "name", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "size", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "data", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    ArrayList<SuperDuperFile> getFiles(User user);

    @ResultMap("superDuperFileResultMap")
    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    SuperDuperFile getFile(Integer fileId);

    @Select("SELECT COUNT(*) FROM FILES WHERE userid = #{userId} and filename = #{name}")
    int countFilesByFilename(SuperDuperFile file);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{name}, #{contentType}, #{size}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(SuperDuperFile file);

    @Update("UPDATE FILES SET filename = #{name}, contenttype = #{contentType}, filesize = #{size}, filedata = #{data} where fileId = #{fileId}")
    int update(SuperDuperFile file);

    @Delete("DELETE FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);

    @Delete("DELETE FILES WHERE userid = #{userId}")
    int deleteFiles(User user);

    @Delete("DELETE FILES")
    int deleteAll();
}
