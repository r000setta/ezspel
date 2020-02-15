package com.jqy.ezspel.service;

import com.jqy.ezspel.config.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserConfig userConfig;

    public String encryptRememberMe() {
        return EncryptService.encrypt(userConfig.getEncParam1(), userConfig.getEncParam2(), userConfig.getUsername());
    }

    public String decryptRememberMe(String msg) {
        return EncryptService.decrypt(userConfig.getEncParam1(), userConfig.getEncParam2(), msg);
    }
}
