package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createUser(User user) {
        String encodedSalt = hashService.generateSalt();
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        int result = 0;
        try {
            result = userMapper.insert(new User(null, user.getUsername(), encodedSalt, hashedPassword, user.getFirstName(), user.getLastName()));
        } catch (DuplicateKeyException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }

    public int deleteUser(User user) {
        return userMapper.delete(user);
    }

}
