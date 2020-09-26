package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validate User Model and Data Mappings")
@MybatisTest
public class UserModelTests {

    @Autowired
    private UserMapper userMapper;

    private String username = "junit";
    private String salt = "salty";
    private String password = "password";
    private String firstName = "Jay";
    private String lastName = "Unit";
    private User testUser;

    @BeforeEach
    public void beforeEach() {
        testUser = new User(null, username, salt, password, firstName, lastName);
    }

    @AfterEach
    public void afterEach() {
        // @TODO Figure out if there is a way to reset the database state with an Annotations (@DirtiesContext?).
        userMapper.deleteAll();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {
        @Test
        @DisplayName("Should create a new user")
        public void testUserCreate(){
            int result = userMapper.insert(testUser);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Should get a user.")
        public void testGetUser(){
            int result = userMapper.insert(testUser);
            assertEquals(1, result);
            User user = userMapper.getUser(testUser.getUsername());

            assertAll("user",
                    () -> assertNotNull(testUser.getUserId()),
                    () -> assertEquals(testUser.getUsername(), user.getUsername()),
                    () -> assertEquals(testUser.getSalt(), user.getSalt()),
                    () -> assertEquals(testUser.getPassword(), user.getPassword()),
                    () -> assertEquals(testUser.getFirstName(), user.getFirstName()),
                    () -> assertEquals(testUser.getLastName(), user.getLastName())
            );
        }

        @Test
        @DisplayName("Should update an existing user.")
        public void testUserUpdate(){
            // create a new user in the database
            int result = userMapper.insert(testUser);
            assertEquals(1, result);

            // instantiate a new user object and update all its attributes
            User user = userMapper.getUser(testUser.getUsername());
            user.setUsername("clunit");
            user.setSalt("dirty");
            user.setPassword("newpass");
            user.setFirstName("Clay");
            user.setLastName("Unity");

            // Update teh database and check the new fields.
            result = userMapper.update(user);
            User updatedUser = userMapper.getUser(user.getUsername());
            int finalResult = result;
            assertAll("user",
                    () -> assertEquals(1, finalResult),
                    () -> assertNotNull(updatedUser.getUserId()),
                    () -> assertEquals(user.getUsername(), updatedUser.getUsername()),
                    () -> assertEquals(user.getSalt(), updatedUser.getSalt()),
                    () -> assertEquals(user.getPassword(), updatedUser.getPassword()),
                    () -> assertEquals(user.getFirstName(), updatedUser.getFirstName()),
                    () -> assertEquals(user.getLastName(), updatedUser.getLastName())
            );
        }

        @Test
        @DisplayName("Should delete a user.")
        public void testUserDelete(){
            int result = userMapper.insert(testUser);
            assertEquals(1, result);
            result = userMapper.delete(testUser);
            assertEquals(1, result);
            User nullUser = userMapper.getUser(testUser.getUsername());
            assertNull(nullUser, "User still exists in the database after delete.");
        }
    }

    @Nested
    @DisplayName("Should not allow")
    class ShouldNotAllow {

        @Test
        @DisplayName("Should not create a user with and existing user name")
        public void testCreateExistingUser(){
            int result = userMapper.insert(testUser);
            assertEquals(1, result);
            Exception exception = assertThrows(Exception.class, () -> {int r = userMapper.insert(testUser);});
            String expectedMessage = "Unique index or primary key violation";
            String actualMessage = exception.getMessage();
            assertTrue(actualMessage.contains(expectedMessage));
        }
    }
}
