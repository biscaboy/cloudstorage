package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private String username = "junit";
    private String password = "password";
    private String firstName = "Jay";
    private String lastName = "Unit";
    private User testUser;

    @BeforeEach
    public void beforeEach() {
        testUser = new User(null, username, null, password, firstName, lastName);
    }

    @AfterEach
    public void afterEach() {
        userService.deleteAllUsers();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {

        @Test
        @DisplayName("Create a user - given user, last and first names.")
        public void testCreateUser() {
            int result = userService.createUser(testUser);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Delete a user.")
        public void testDeleteUser() {
            int result = userService.createUser(testUser);
            assertEquals(1, result);
            result = userService.deleteUser(testUser);
            assertEquals(0, result);

        }

        @Test
        @DisplayName("Get a user with a username")
        public void testGetUserByUsername() {
            int result = userService.createUser(testUser);
            assertEquals(1, result);

            User user = userService.getUser(testUser.getUsername());
            assertAll("user",
                    () -> assertNotNull(user),
                    () -> assertNotNull(user.getUserId()),
                    () -> assertNotNull(user.getSalt()),
                    () -> assertNotEquals(testUser.getPassword(), user.getPassword()),
                    () -> assertEquals(testUser.getUsername(), user.getUsername()),
                    () -> assertEquals(testUser.getFirstName(), user.getFirstName()),
                    () -> assertEquals(testUser.getLastName(), user.getLastName())
            );
        }
    }
}
