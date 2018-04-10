/*
 * Created by Jordan Kuhn on 2018.02.15 * 
 * Copyright © 2018 Jordan Kuhn. All rights reserved. * 
 */
package com.mycompany.managers;

import com.mycompany.EntityBeans.User;
import com.mycompany.EntityBeans.UserPhoto;

import com.mycompany.FacadeBeans.UserFacade;
import com.mycompany.FacadeBeans.UserPhotoFacade;
import com.mycompany.FacadeBeans.UserVideoFacade;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;

/*
---------------------------------------------------------------------------
JSF Managed Beans annotated with @ManagedBean from javax.faces.bean
is in the process of being deprecated in favor of CDI Beans. 

You should annotate the bean class with @Named from javax.inject.Named 
package to designate the bean as managed by the
Contexts and Dependency Injection (CDI) container.

Beans annotated with @Named is the preferred approach, because CDI 
enables Java EE-wide dependency injection. 

A CDI bean is a bean managed by the CDI container. 

Within JSF facelets XHTML pages, this bean will be referenced by using the
name 'accountManager'. Actually, the default name is the class name starting
with a lower case letter and value = 'accountManager' is optional;
However, we spell it out to make our code more readable and understandable.
---------------------------------------------------------------------------
 */
@Named(value = "accountManager")

/*
The @SessionScoped annotation preserves the values of the AccountManager
object's instance variables across multiple HTTP request-response cycles
as long as the user's established HTTP session is alive.
 */
@SessionScoped

/**
 *
 * @author Jordan
 */

/*
--------------------------------------------------------------------------
Marking the AccountManager class as "implements Serializable" implies that
instances of the class can be automatically serialized and deserialized. 

Serialization is the process of converting a class instance (object)
from memory into a suitable format for storage in a file or memory buffer, 
or for transmission across a network connection link.

Deserialization is the process of recreating a class instance (object)
in memory from the format under which it was stored.
--------------------------------------------------------------------------
 */
public class AccountManager implements Serializable {
    private String username;
    private String password;
    private String newPassword;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipcode;
    private int securityQuestion;
    private String securityAnswer;
    private String email;
    private final String[] listOfStates = Constants.STATES;

    private Map<String, Object> security_questions;

    private String statusMessage;

    private User selected;
  
   /*
    The instance variable 'userFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference
    of the UserFacade object, after it is instantiated at runtime, into the instance variable 'userFacade'.
     */
    @EJB
    private UserFacade userFacade;

    /*
    The instance variable 'userFileFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference 
    of the UserFileFacade object, after it is instantiated at runtime, into the instance variable 'userFileFacade'.
     */
    @EJB
    private UserVideoFacade userVideoFacade;

    /*
    The instance variable 'userPhotoFacade' is annotated with the @EJB annotation.
    The @EJB annotation directs the EJB Container (of the GlassFish AS) to inject (store) the object reference 
    of the UserPhotoFacade object, after it is instantiated at runtime, into the instance variable 'userPhotoFacade'.
     */
    @EJB
    private UserPhotoFacade userPhotoFacade;
  
    public AccountManager() {}

    public String[] getListOfStates()
    {
      return listOfStates;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getNewPassword() {
      return newPassword;
    }

    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }

    public String getFirstName() {
      return firstName;
    }

    public void setFirstName(String firstName) {
      this.firstName = firstName;
    }

    public String getMiddleName() {
      return middleName;
    }

    public void setMiddleName(String middleName) {
      this.middleName = middleName;
    }

    public String getLastName() {
      return lastName;
    }

    public void setLastName(String lastName) {
      this.lastName = lastName;
    }

    public String getAddress1() {
      return address1;
    }

    public void setAddress1(String address1) {
      this.address1 = address1;
    }

    public String getAddress2() {
      return address2;
    }

    public void setAddress2(String address2) {
      this.address2 = address2;
    }

    public String getCity() {
      return city;
    }

    public void setCity(String city) {
      this.city = city;
    }

    public String getState() {
      return state;
    }

    public void setState(String state) {
      this.state = state;
    }

    public String getZipcode() {
      return zipcode;
    }

    public void setZipcode(String zip_code) {
      zipcode = zip_code;
    }

    public int getSecurityQuestion() {
      return securityQuestion;
    }

    public void setSecurityQuestion(int securityQuestion) {
      this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
      return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
      this.securityAnswer = securityAnswer;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public UserFacade getUserFacade() {
      return userFacade;
    }

    public UserVideoFacade getUserVideoFacade() {
      return userVideoFacade;
    }

    public UserPhotoFacade getUserPhotoFacade() {
      return userPhotoFacade;
    }

    public Map<String, Object> getSecurity_questions()
    {
      if (security_questions == null)
      {
        security_questions = new LinkedHashMap();

        for (int i = 0; i < Constants.QUESTIONS.length; i++) {
          security_questions.put(Constants.QUESTIONS[i], Integer.valueOf(i));
        }
      }
      return security_questions;
    }

    public String getStatusMessage() {
      return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
      this.statusMessage = statusMessage;
    }

    public User getSelected()
    {
      if (selected == null)
      {
        int userPrimaryKey = 
          ((Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id")).intValue();

        selected = ((User)getUserFacade().find(Integer.valueOf(userPrimaryKey)));
      }

      return selected;
    }

    public void setSelected(User selectedUser) {
      selected = selectedUser;
    }

    public boolean isLoggedIn()
    {
      return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null;
    }

    public String createAccount()
    {
      User aUser = getUserFacade().findByUsername(username);

      if (aUser != null)
      {
        username = "";
        statusMessage = "Username already exists! Please select a different one!";
        return "";
      }

      if ((statusMessage == null) || (statusMessage.isEmpty()))
      {
        try {
          User newUser = new User();
          newUser.setFirstName(firstName);
          newUser.setMiddleName(middleName);
          newUser.setLastName(lastName);
          newUser.setAddress1(address1);
          newUser.setAddress2(address2);
          newUser.setCity(city);
          newUser.setState(state);
          newUser.setZipcode(zipcode);
          newUser.setSecurityQuestion(securityQuestion);
          newUser.setSecurityAnswer(securityAnswer);
          newUser.setEmail(email);
          newUser.setUsername(username);
          newUser.setPassword(password);

          getUserFacade().create(newUser);
        }
        catch (EJBException e) {
          username = "";
          statusMessage = ("Something went wrong while creating user's account! See: " + e.getMessage());
          return "";
        }

        initializeSessionMap();





        return "/SignIn.xhtml?faces-redirect=true";
      }
      return "";
    }

    public void initializeSessionMap()
    {
      User user = getUserFacade().findByUsername(getUsername());

      FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().put("user", user);

      FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().put("user_id", user.getId());
    }

    public String updateAccount()
    {
      if ((statusMessage == null) || (statusMessage.isEmpty()))
      {

        String user_name = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");

        User editUser = getUserFacade().findByUsername(user_name);

        try
        {
          editUser.setFirstName(selected.getFirstName());
          editUser.setMiddleName(selected.getMiddleName());
          editUser.setLastName(selected.getLastName());

          editUser.setAddress1(selected.getAddress1());
          editUser.setAddress2(selected.getAddress2());
          editUser.setCity(selected.getCity());
          editUser.setState(selected.getState());
          editUser.setZipcode(selected.getZipcode());
          editUser.setEmail(selected.getEmail());

          String new_Password = getNewPassword();

          if ((new_Password != null) && (!new_Password.isEmpty()))
          {

            editUser.setPassword(new_Password);
          }

          getUserFacade().edit(editUser);
        }
        catch (EJBException e) {
          username = "";
          statusMessage = ("Something went wrong while editing user's profile! See: " + e.getMessage());
          return "";
        }

        return "/Profile.xhtml?faces-redirect=true";
      }
      return "";
    }

    public String deleteAccount()
    {
      if ((statusMessage == null) || (statusMessage.isEmpty()))
      {

        int user_id = ((Integer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user_id")).intValue();

        try
        {
          deleteAllUserPhotos(user_id);

          getUserFacade().deleteUser(user_id);

          statusMessage = "Your account is successfully deleted!";
        }
        catch (EJBException e) {
          username = "";
          statusMessage = ("Something went wrong while deleting user's account! See: " + e.getMessage());
          return "";
        }

        logout();
        return "/index.xhtml?faces-redirect=true";
      }
      return "";
    }

    public void validateInformation(ComponentSystemEvent event)
    {
      FacesContext fc = FacesContext.getCurrentInstance();

      UIComponent components = event.getComponent();

      UIInput uiInputPassword = (UIInput)components.findComponent("password");

      String entered_password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();

      UIInput uiInputConfirmPassword = (UIInput)components.findComponent("confirmPassword");

      String entered_confirm_password = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

      if ((entered_password.isEmpty()) || (entered_confirm_password.isEmpty()))
      {
        return;
      }

      if (!entered_password.equals(entered_confirm_password)) {
        statusMessage = "Password and Confirm Password must match!";
      } else {
        statusMessage = "";
      }
    }

    public void validatePasswordChange(ComponentSystemEvent event)
    {
      FacesContext fc = FacesContext.getCurrentInstance();

      UIComponent components = event.getComponent();

      UIInput uiInputPassword = (UIInput)components.findComponent("newPassword");

      String new_Password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();

      UIInput uiInputConfirmPassword = (UIInput)components.findComponent("newConfirmPassword");

      String new_ConfirmPassword = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

      if ((new_Password.isEmpty()) || (new_ConfirmPassword.isEmpty()))
      {
        return;
      }

      if (!new_Password.equals(new_ConfirmPassword)) {
        statusMessage = "New Password and New Confirm Password must match!";
      }
      else
      {
        String regex = "^(?=.{8,31})(?=.*[!@#$%^&*()])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";

        if (!new_Password.matches(regex)) {
          statusMessage = "The password must be minimum 8 characters long, contain at least one special character above the numbers on the keyboard, contain at least one uppercase letter, contain at least one lowercase letter, and contain at least one number 0 to 9.";
        }
        else
        {
          statusMessage = "";
        }
      }
    }

    public void validateUserPassword(ComponentSystemEvent event)
    {
      FacesContext fc = FacesContext.getCurrentInstance();

      UIComponent components = event.getComponent();

      UIInput uiInputPassword = (UIInput)components.findComponent("password");

      String entered_password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();

      UIInput uiInputConfirmPassword = (UIInput)components.findComponent("confirmPassword");

      String entered_confirm_password = uiInputConfirmPassword.getLocalValue() == null ? "" : uiInputConfirmPassword.getLocalValue().toString();

      if ((entered_password.isEmpty()) || (entered_confirm_password.isEmpty()))
      {
        return;
      }

      if (!entered_password.equals(entered_confirm_password)) {
        statusMessage = "Password and Confirm Password must match!";
      }
      else
      {
        String user_name = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");

        User user = getUserFacade().findByUsername(user_name);

        if (entered_password.equals(user.getPassword()))
        {
          statusMessage = "";
        } else {
          statusMessage = "Incorrect Password!";
        }
      }
    }

    private boolean correctPasswordEntered(UIComponent components)
    {
      UIInput uiInputVerifyPassword = (UIInput)components.findComponent("verifyPassword");

      String verifyPassword = uiInputVerifyPassword.getLocalValue() == null ? "" : uiInputVerifyPassword.getLocalValue().toString();

      if (verifyPassword.isEmpty()) {
        statusMessage = "Please enter a password!";
        return false;
      }
      if (verifyPassword.equals(password))
      {
        return true;
      }

      statusMessage = "Invalid password entered!";
      return false;
    }

    public String showHomePage()
    {
      return "/index?faces-redirect=true";
    }

    public String showProfile()
    {
      return "/Profile?faces-redirect=true";
    }

    public String logout()
    {
      FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

      username = (this.password = "");
      firstName = (this.middleName = this.lastName = "");
      address1 = (this.address2 = this.city = this.state = this.zipcode = "");
      securityQuestion = 0;
      securityAnswer = "";
      email = (this.statusMessage = "");

      FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

      return "/index.xhtml?faces-redirect=true";
    }

    public String userPhoto()
    {
      String usernameOfSignedInUser = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");

      User signedInUser = getUserFacade().findByUsername(usernameOfSignedInUser);

      Integer userId = signedInUser.getId();

      List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserID(userId);

      if (photoList.isEmpty())
      {
        return "UserPhotoStorage/defaultUserPhoto.png";
      }

      String thumbnailFileName = ((UserPhoto)photoList.get(0)).getThumbnailFileName();

      String relativePhotoFilePath = "UserPhotoStorage/" + thumbnailFileName;

      return relativePhotoFilePath;
    }

    public void deleteAllUserPhotos(int userId)
    {
      List<UserPhoto> photoList = getUserPhotoFacade().findPhotosByUserID(Integer.valueOf(userId));

      if (!photoList.isEmpty())
      {
        UserPhoto photo = (UserPhoto)photoList.get(0);

        try
        {
          Files.deleteIfExists(Paths.get(photo.getPhotoFilePath(), new String[0]));

          Files.deleteIfExists(Paths.get(photo.getThumbnailFilePath(), new String[0]));

          Files.deleteIfExists(Paths.get("/home/cloudsd/Kuhn/UserPhotoStorage/tmp_file", new String[0]));

          getUserPhotoFacade().remove(photo);
        }
        catch (IOException e) {
          statusMessage = ("Something went wrong while deleting user's photo! See: " + e.getMessage());
        }
      }
    }
}