package com.example.weihuagu.utils.AES;

import com.example.weihuagu.utils.AES.AESCipher;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Tester {

    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String string = AESCipher.aesEncryptString("1530322499", "7sj65gyh98i31721");
        System.out.println(string);
        System.out.println(AESCipher.aesDecryptString(string, "16BytesLengthKey"));
    }
}
