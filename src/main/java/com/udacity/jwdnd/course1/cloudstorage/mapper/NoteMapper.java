package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NoteMapper {
    @Results(id = "noteResultMap", value = {
            @Result(property = "noteId", column = "noteid"),
            @Result(property = "noteTitle", column = "notetitle"),
            @Result(property = "noteDescription", column = "notedescription"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM NOTES WHERE userid = #{userId}")
    ArrayList<Note> getNotes(User user);

    @ResultMap("noteResultMap")
    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE NOTES set notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE NOTES WHERE userid = #{userId}")
    int deleteNotes(User user);

    @Delete("DELETE NOTES WHERE noteid = #{noteId}")
    int delete(Note note);

    /**
     * Utility method for testing.
     * @return number of rows deleted
     */
    @Delete("DELETE NOTES")
    int deleteAll();
}
