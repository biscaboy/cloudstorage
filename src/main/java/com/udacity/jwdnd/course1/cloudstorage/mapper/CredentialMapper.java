package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {
    @Results(id = "credentialResultMap", value = {
            @Result(property = "id", column = "credentialid"),
            @Result(property = "url", column = "url"),
            @Result(property = "username", column = "username"),
            @Result(property = "key", column = "key"),
            @Result(property = "password", column = "password"),
            @Result(property = "userId", column = "userid")
    })
    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
    ArrayList<Credential> getCredentials(User user);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Credential credential);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password = #{password} where credentialid = #{id}")
    int update(Credential credential);

    @Delete("DELETE CREDENTIALS WHERE credentialid = #{id}")
    int delete(Credential credential);

    /**
     * Utility method for testing.
     * @return number of rows deleted
     */
    @Delete("DELETE CREDENTIALS")
    int deleteAll();

}