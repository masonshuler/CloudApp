/*
 * Created by Mason Shuler on 2018.04.25  * 
 * Copyright © 2018 Mason Shuler. All rights reserved. * 
 */
package com.mycompany.managers;

import com.twilio.rest.api.v2010.account.Message;
import java.util.Random;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Mason
 */
public class TwilioManager {

    private static final String ACCOUNT_SID = "AC0f1310df595304c48c1aea364cc51a6a";
    private static final String AUTH_TOKEN = "13c787fa038fe32e8238bb94479983d2";
    private static final int MAX_VERIFICATION_CODE = 100000;
    private static final int MIN_VERIFICATION_CODE = 999999;


    public static String sendSMSAuth(String number) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String verCode = "Your Goodwill authentication code is:" + generateVerificationCode();
        String from = "+18044090066";
        String to = "+1" + number;
        Message message = Message.creator(new PhoneNumber(to),
                new PhoneNumber(from),
                verCode).create();
        System.out.println(message.getSid());
        return verCode;
    }

    static String generateVerificationCode() {
        Random rand = new Random();
        Integer code = rand.nextInt(MIN_VERIFICATION_CODE
                - MAX_VERIFICATION_CODE + 1) + MAX_VERIFICATION_CODE;
        return code.toString();
    }
    
    public static String sendCallAuth(String number) throws URISyntaxException {
        String verCode = generateVerificationCode();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String from = "+18044090066";
        String to = "+1" + number;
        String response = "<Response>" +
            "<Say voice=\"alice\">Your code is " + 
                verCode + " again, your code is " + 
                verCode + "</Say>" +
            "</Response>";
        
        
        Call call = Call.creator(new PhoneNumber(to), new PhoneNumber(from),
                response).create();

        System.out.println(call.getSid());
        return verCode;
    }
}

