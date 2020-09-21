package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface SuperDuperFileMapper {

    @Results(id = "superDuperFileResultMap", value = {
            @Result(property = "id", column = "fileId"),
            @Result(property = "name", column = "filename"),
            @Result(property = "contentType", column = "contenttype"),
            @Result(property = "size", column = "filesize"),
            @Result(property = "userId", column = "userid"),
            @Result(property = "data", column = "filedata")
    })
    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    ArrayList<SuperDuperFile> getFiles(User user);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{name}, #{contentType}, #{size}, #{userId}, #{data})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SuperDuperFile file);

    @Update("UPDATE FILES SET filename = #{name}, contenttype = #{contentType}, filesize = #{size}, filedata = #{data} where fileId = #{id}")
    int update(SuperDuperFile file);

    @Delete("DELETE FILES WHERE fileid = #{id}")
    int deleteFile(Integer id);

    @Delete("DELETE FILES WHERE userid = #{userId}")
    int deleteFiles(User user);

    @Delete("DELETE FILES")
    int deleteAll();
}
