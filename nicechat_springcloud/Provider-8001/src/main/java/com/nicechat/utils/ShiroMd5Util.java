package com.nicechat.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class ShiroMd5Util {

    //添加user的密码加密方法
    public static String EncryptMd5(String accountNum, String password) {
        String hashAlgorithmName = "MD5"; //加密方式
        ByteSource salt = ByteSource.Util.bytes(accountNum); //以账号作为盐值
//        int hashIterations = 1024; //加密1024次
        return new SimpleHash(hashAlgorithmName, password, salt).toHex();
    }
}
