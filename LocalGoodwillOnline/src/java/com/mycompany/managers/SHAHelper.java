/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
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

    /**
     * Get the hash for the password and email
     * @param regPassword The password
     * @param username The username
     * @return The hash
     */
    public static String getHash(String regPassword, String username) {
        MessageDigest digest;
        String toHash = regPassword + username; //Salt!
        try {
            digest = MessageDigest.getInstance("SHA-256");
            return bytesToHex(digest.digest(toHash.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Convert bytes to hex string
     * @param bytes Bytes to convert
     * @return Hex string
     */
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }
}
