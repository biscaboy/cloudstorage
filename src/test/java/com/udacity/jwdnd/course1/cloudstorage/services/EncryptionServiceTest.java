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
        String password = "IceCreamSandwich21";
        String key = encryptionService.generateKey();
        String encrypted = encryptionService.encryptValue(password, key);
        String decrypted = encryptionService.decryptValue(encrypted, key);
        assertFalse(key.isBlank(), "Generated key is empty.");
        assertNotNull(encrypted, "Encryption failed.");
        assertNotNull(decrypted, "Decryption failed.");
        assertNotEquals(encrypted, password, "Nothing was encrypted.");
        assertEquals(decrypted, password, "Decryption did not produce the expected result.");
    }
}
