package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Results(id = "noteResultMap", value = {
            @Result(property = "id", column = "noteid"),
            @Result(property = "title", column = "notetitle"),
            @Result(property = "description", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    ArrayList<Note> getNotes(User user);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{title}, #{description}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Note note);

    @Update("UPDATE NOTES set notetitle = #{title}, notedescription = #{description}, userid = #{userId} WHERE noteid = #{id}")
    int update(Note note);

    @Delete("DELETE NOTES WHERE userid = #{userId}")
    int deleteNotes(User user);

    @Delete("DELETE NOTES WHERE noteid = #{id}")
    int delete(Note note);

    /**
     * Utility method for testing.
     * @return number of rows deleted
     */
    @Delete("DELETE NOTES")
    int deleteAll();
}
