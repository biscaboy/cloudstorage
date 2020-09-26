package com.udacity.jwdnd.course1.cloudstorage.model;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.junit.jupiter.api.*;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Validate Credential Model and Data Mappings")
@MybatisTest
public class CredentialModelTest {

    @Autowired
    private CredentialMapper credentialMapper;

    @Autowired
    private UserMapper userMapper;

    // Credential values
    private String url = "http://paypal.com";
    private String username = "bigspender";
    private String key = "!@#$%^&*()_";
    private String password = "ea*30T59##R";
    private Credential testCredential;

    // User values
    private static String userUsername = "junit";
    private static String salt = "salty";
    private static String userPassword = "password";
    private static String firstName = "Jay";
    private static String lastName = "Unit";
    private static User testUser;

    @BeforeAll
    public static void beforeAll() {
        testUser = new User(null, userUsername, salt, userPassword, firstName, lastName);
    }

    @BeforeEach
    public void beforeEach() {
        int result = userMapper.insert(testUser);
        assertEquals(1, result);
        testCredential = new Credential(null, url, username, key, password, testUser.getUserId());
    }

    @AfterEach
    public void afterEach() {
        credentialMapper.deleteAll();
        userMapper.deleteAll();
    }

    @Nested
    @DisplayName("Should allow")
    class ShouldAllow {
        @Test
        @DisplayName("Create a new credential")
        public void testCreateCredential(){
            int result = credentialMapper.insert(testCredential);
            assertEquals(1, result);
        }

        @Test
        @DisplayName("Get a credential.")
        public void testGetCredential(){
            int result = credentialMapper.insert(testCredential);
            assertEquals(1, result);
            ArrayList<Credential> credentials = credentialMapper.getCredentials(testUser);
            assertAll("credential",
                    () -> assertEquals(1, credentials.size()),
                    () -> assertNotNull(credentials.get(0).getCredentialId()),
                    () -> assertEquals(testCredential.getUrl(), credentials.get(0).getUrl()),
                    () -> assertEquals(testCredential.getUsername(), credentials.get(0).getUsername()),
                    () -> assertEquals(testCredential.getKey(), credentials.get(0).getKey()),
                    () -> assertEquals(testCredential.getPassword(), credentials.get(0).getPassword()),
                    () -> assertEquals(testCredential.getUserId(), credentials.get(0).getUserId())
            );
        }

        @Test
        @DisplayName("Update an existing credential.")
        public void testCredentialUpdate(){
            int result = credentialMapper.insert(testCredential);
            assertEquals(1, result);
            // get the credential and update it with a new uid, key and password
            ArrayList<Credential> credentials = credentialMapper.getCredentials(testUser);
            assertEquals(1, credentials.size());
            Credential credential = credentials.get(0);
            credential.setUrl("https://www.myboguspaypal.com");
            credential.setUsername("kunit");
            credential.setKey("%^&*()&^%$");
            credential.setPassword("&EU3fj9ed23&EU3fj8ed23&EU3f");
            // update the database with the new information
            result = credentialMapper.update(credential);
            assertEquals(1, result);
            ArrayList<Credential> updatedCredentials = credentialMapper.getCredentials(testUser);
            assertEquals(1, updatedCredentials.size());
            Credential updatedCredential = updatedCredentials.get(0);
            assertAll("updatedCredential",
                    () -> assertEquals(credential.getCredentialId(), updatedCredential.getCredentialId()),
                    () -> assertEquals(credential.getUrl(), updatedCredential.getUrl()),
                    () -> assertEquals(credential.getUsername(), updatedCredential.getUsername()),
                    () -> assertEquals(credential.getKey(), updatedCredential.getKey()),
                    () -> assertEquals(credential.getPassword(), updatedCredential.getPassword()),
                    () -> assertEquals(credential.getUserId(), updatedCredential.getUserId())
            );
        }

        @Test
        @DisplayName("Delete a credential.")
        public void testDeleteCredential(){
            int result = credentialMapper.insert(testCredential);
            assertEquals(1, result);

            ArrayList<Credential> credentials = credentialMapper.getCredentials(testUser);

            result = credentialMapper.delete(credentials.get(0));
            assertEquals(1, result);

            ArrayList<Credential> noCredentials = credentialMapper.getCredentials(testUser);
            assertEquals(0, noCredentials.size());
        }
    }
}
