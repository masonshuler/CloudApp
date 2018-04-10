/*
 * Created by Jordan Kuhn on 2018.02.15 * 
 * Copyright © 2018 Jordan Kuhn. All rights reserved. * 
 */
package com.mycompany.managers;

/**
 *
 * @author Jordan
 */

public final class Constants {
    
    /* =========== Our Design Decision ===========
        We decided to use directories external to our application 
        for the storage and retrieval of user's files.
    
        We do not want to use a database for the following reasons: 
            (a) Database storage and retrieval of large files as 
                BLOB (binary large object) degrades performance.
            (b) BLOBs increase the database complexity.
    
        Therefore, we use the following two external directories 
        for the storage and retrieval of photos.
     */
    public static final String PHOTOS_ABSOLUTE_PATH = "/home/cloudsd/Kuhn/UserPhotoStorage/";
    public static final String PHOTOS_RELATIVE_PATH = "UserPhotoStorage/";
    public static final String DEFAULT_PHOTO_RELATIVE_PATH = "UserPhotoStorage/defaultUserPhoto.png";
    public static final String TEMP_FILE = "tmp_file";
    public static final Integer THUMBNAIL_SIZE = Integer.valueOf(200);
    
    /* United States postal state abbreviations */
    public static final String[] STATES = {"AK", "AL", "AR", "AZ", "CA", "CO", "CT",
        "DC", "DE", "FL", "GA", "GU", "HI", "IA", "ID", "IL", "IN", "KS", "KY", "LA", "MA",
        "MD", "ME", "MH", "MI", "MN", "MO", "MS", "MT", "NC", "ND", "NE", "NH", "NJ", "NM",
        "NV", "NY", "OH", "OK", "OR", "PA", "PR", "PW", "RI", "SC", "SD", "TN", "TX", "UT",
        "VA", "VI", "VT", "WA", "WI", "WV", "WY"};

    /* Security questions to reset password  */
    public static final String[] QUESTIONS = {
        "In what city were you born?",
        "What is your mother's maiden name?",
        "What elementary school did you attend?",
        "What was the make of your first car?",
        "What is your father's middle name?",
        "What is the name of your most favorite pet?",
        "What street did you grow up on?"
    };
}