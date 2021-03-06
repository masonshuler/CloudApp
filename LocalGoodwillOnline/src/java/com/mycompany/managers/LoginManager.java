/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
package com.mycompany.managers;

import com.mycompany.EntityBeans.User;
import com.mycompany.FacadeBeans.UserFacade;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named(value = "loginManager")
@SessionScoped
/**
 *
 * @author McGhee
 */
public class LoginManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private String username;
    private String passwordHash;
    private String errorMessage;
    private String answerAuthCode;

    /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private UserFacade userFacade;

    // Constructor method instantiating an instance of LoginManager
    public LoginManager() {
    }

    /*
    =========================
    Getter and Setter Methods
    =========================
     */
    public String getAnswerAuthCode() {
        return answerAuthCode;
    }

    public void setAnswerAuthCode(String answerAuthCode) {
        this.answerAuthCode = answerAuthCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String regPassword) {
        this.passwordHash = SHAHelper.getHash(regPassword, username);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public UserFacade getUserFacade() {
        return userFacade;
    }

    /*
    ================
    Instance Methods
    ================
     */
    public String createUser() {

        // Redirect to show the CreateAccount page
        return "/CreateAccount.xhtml?faces-redirect=true";
    }

    public String resetPassword() {

        // Redirect to show the EnterUsername page
        return "/EnterUsername.xhtml?faces-redirect=true";
    }

    /*
    Sign in the user if the entered username and password are valid
    @return "" if an error occurs; otherwise, redirect to show the Profile page
     */
    public String loginUser() {

        // Obtain the object reference of the User object from the entered username
        User user = getUserFacade().findByUsername(getUsername());

        if (user == null) {
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return "";

        } else {
            String actualUsername = user.getUsername();
            String enteredUsername = getUsername();

            String actualPasswordHash = user.getPasswordHash();
            String enteredPasswordHash = getPasswordHash();

            if (!actualUsername.equals(enteredUsername)) {
                errorMessage = "Invalid Username!";
                return "";
            }

            if (!actualPasswordHash.equals(enteredPasswordHash)) {
                errorMessage = "Invalid Password!";
                return "";
            }

            errorMessage = "";

            // Initialize the session map with user properties of interest
            //initializeSessionMap(user); Need Two-Factor
            // Redirect to show the Profile page
            return "/TwoFactor.xhtml?faces-redirect=true";
        }
    }

    /**
     * Check the user's authentication code
     * @return Nothing or profile page
     */
    public String checkAuthCode() {
        User user = getUserFacade().findByUsername(getUsername());
        if (user == null) { //Should only happen on page error
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return "";
        }

        if (!getAnswerAuthCode().equals(user.getGeneratedAuthCode())) {
            errorMessage = "Invalid Authentication Code!";
            return "";
        }

        initializeSessionMap(user);
        return "/Profile.xhtml?faces-redirect=true";
    }

    /**
     * Send an SMS for 2 factor
     */
    public void sendSMS() {
        User user = getUserFacade().findByUsername(getUsername());
        if (user == null) { //Should only happen on page error
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return;
        }

        user.setGeneratedAuthCode(TwilioManager.sendSMSAuth(user.getPhoneNumber()));
        getUserFacade().edit(user);
    }

    /**
     * Call for 2 factor
     */
    public void call() {
        User user = getUserFacade().findByUsername(getUsername());
        if (user == null) { //Should only happen on page error
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return;
        }
        user.setGeneratedAuthCode(TwilioManager.sendCallAuth(user.getPhoneNumber()));
        getUserFacade().edit(user);
    }

    /**
     * Email for 2 factor
     */
    public void email() {
        User user = getUserFacade().findByUsername(getUsername());
        if (user == null) { //Should only happen on page error
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return;
        }
        user.setGeneratedAuthCode(TwilioManager.sendEmailAuth(user.getEmail()));
        getUserFacade().edit(user);
    }
    
    /**
     * Allow users to bypass 2 factor 
     * @return Profile page
     */
    public String bypass() {
        User user = getUserFacade().findByUsername(getUsername());
        if (user == null) { //Should only happen on page error
            errorMessage = "Entered username " + getUsername() + " does not exist!";
            return "";
        }
        initializeSessionMap(user);
        return "/Profile.xhtml?faces-redirect=true";
    }
    /*
    Initialize the session map with the user properties of interest,
    namely, first_name, last_name, username, and user_id.
    user_id = primary key of the user entity in the database
             */

    public void initializeSessionMap(User user) {
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("first_name", user.getFirstName());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("last_name", user.getLastName());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("username", user.getUsername());
        FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().put("user_id", user.getId());
    }

}
