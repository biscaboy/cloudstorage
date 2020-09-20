package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES(#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insert(User user);

    @Update("UPDATE USERS set username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstName}, lastname= #{lastName} where userid = #{userId}")
    int update(User user);

    @Delete("DELETE USERS WHERE userid = #{userId}")
    int delete(User user);

    /**
     * Utility method for testing.
     * @return number of rows deleted
     */
    @Delete("DELETE USERS")
    int deleteAll();
}
