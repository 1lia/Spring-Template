package com.app;


import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {
    @Test
    public void JasyptTest(){
        String password = "test";
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDES");
        String content = "ssafy";
        String encryptedContent = encryptor.encrypt(content); //암호화
        String decryptedContent = encryptor.decrypt(encryptedContent);

        System.out.println(encryptedContent + " " + decryptedContent);

    }
}
