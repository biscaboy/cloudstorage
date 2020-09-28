package com.udacity.jwdnd.course1.cloudstorage.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EncryptionServiceTest {

    @Autowired
    EncryptionService encryptionService;

    @Test
    @DisplayName("Generate a random key")
    public void testGenerateKey() {
        String key = encryptionService.generateSecureKey();
        assertNotNull(key, "Generated key is null");
        assertFalse(key.isBlank());
    }
}
