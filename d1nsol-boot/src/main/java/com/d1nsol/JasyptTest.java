package com.d1nsol;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");

        // 암호화 키... 중요!
        pbeEnc.setPassword("");

        String encryptedText = pbeEnc.encrypt("");
        String decryptedText = pbeEnc.decrypt(encryptedText);

        System.out.println("@@@@@ encryptedText : " + encryptedText);
        System.out.println("@@@@@ decryptedText : " + decryptedText);
    }
}
