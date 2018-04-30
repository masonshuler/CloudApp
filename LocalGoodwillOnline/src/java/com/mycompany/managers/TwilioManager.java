/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
package com.mycompany.managers;

import com.mycompany.controllers.EmailController;
import com.twilio.rest.api.v2010.account.Message;
import java.util.Random;
import java.net.URISyntaxException;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

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
        String verCode =  generateVerificationCode();
        String from = "+18044090066";
        String to = "+1" + number;
        String response = "Your Goodwill authentication code is:" + verCode;
        Message message = Message.creator(new PhoneNumber(to),
                new PhoneNumber(from),
                response).create();
        System.out.println(message.getSid());
        return verCode;
    }

    static String generateVerificationCode() {
        Random rand = new Random();
        Integer code = rand.nextInt(MIN_VERIFICATION_CODE
                - MAX_VERIFICATION_CODE + 1) + MAX_VERIFICATION_CODE;
        return code.toString();
    }
    
    public static String sendCallAuth(String number) {
        String verCode = generateVerificationCode();
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String from = "+18044090066";
        String to = "+1" + number;
        String raw = "Your code is " + 
                verCode + " again, your code is " + 
                verCode ;
        Say say = new Say.Builder(raw).build();
        VoiceResponse response = new VoiceResponse.Builder().say(say).build();
        
        Call call;
        try {
            call = Call.creator(new PhoneNumber(to), new PhoneNumber(from),
                    new URI("https://handler.twilio.com/twiml/EHde48b0703d85972206d121a3b8c8b1a0")).create();
        } catch (URISyntaxException ex) {
            Logger.getLogger(TwilioManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "218386";
    }
    
    public static String sendEmailAuth(String email) {
        EmailController emailController = new EmailController();
        String verCode =  generateVerificationCode();
        String response = "Your Goodwill authentication code is:" + verCode;
        emailController.setEmailBody(response);
        emailController.setEmailSubject("Goodwill TwoFactor Automated Message");
        emailController.setEmailTo(email);
        try {
            emailController.sendEmail();
        } catch (MessagingException ex) {
            Logger.getLogger(TwilioManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return verCode;
    }
}

