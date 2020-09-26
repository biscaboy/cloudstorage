package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public boolean updateCredential(Credential credential){
        credential = encryptPassword(credential);
        return this.credentialMapper.update(credential) == 1;
    }

    public boolean createCredential(Credential credential) {
        credential = encryptPassword(credential);
        return credentialMapper.insert(credential) == 1;
    }

    public ArrayList<Credential> getCredentials(User user){
        ArrayList<Credential> decryptedCredentials = new ArrayList<>();
        // decript the passwords.
        List<Credential> encryptedCredentials = credentialMapper.getCredentials(user);
        for (Credential c : encryptedCredentials) {
            decryptedCredentials.add(decryptPassword(c));
        }
        return decryptedCredentials;
    }

    public boolean deleteCrediential(Credential crediential){
        return credentialMapper.delete(crediential) == 1;
    }

    private Credential encryptPassword(Credential c) {
        if (c.getKey() == null) {
            c.setKey(encryptionService.generateSecureKey());
        }
        String encryptedPassword = encryptionService.encryptValue(c.getPassword(), c.getKey());
        c.setPassword(encryptedPassword);
        return c;
    }

    private Credential decryptPassword(Credential c) {
        String decryptedPassword = encryptionService.decryptValue(c.getPassword(),c.getKey());
        c.setPassword(decryptedPassword);
        return c;
    }

}
