/*
 * Created by Mason Shuler on 2018.04.25  * 
 * Copyright © 2018 Mason Shuler. All rights reserved. * 
 */
package com.mycompany.managers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mason
 */
public class SHAHelper {

    public static String getHash(String regPassword, String email) {
        MessageDigest digest;
        String toHash = regPassword + email; //Salt!
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(toHash.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
