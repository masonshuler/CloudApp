/*
 * Created by Jordan Kuhn on 2018.04.02 * 
 * Copyright © 2018 Jordan Kuhn. All rights reserved. * 
 */
package com.mycompany.controllers;

import com.mycompany.EntityBeans.User;
import com.mycompany.FacadeBeans.UserFacade;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.controllers.util.JsfUtil.PersistAction;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("userController")
@SessionScoped
public class UserController
  implements Serializable
{
  @EJB
  private UserFacade ejbFacade;
  private List<User> items = null;
  private User selected;
  
  public UserController() {}
  
  public User getSelected()
  {
    return selected;
  }
  
  public void setSelected(User selected) {
    this.selected = selected;
  }

  protected void setEmbeddableKeys() {}
  
  protected void initializeEmbeddableKey() {}
  
  private UserFacade getFacade()
  {
    return ejbFacade;
  }
  
  public User prepareCreate() {
    selected = new User();
    initializeEmbeddableKey();
    return selected;
  }
  
  public void create() {
    persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
    if (!JsfUtil.isValidationFailed()) {
      items = null;
    }
  }
  
  public void update() {
    persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
  }
  
  public void destroy() {
    persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserDeleted"));
    if (!JsfUtil.isValidationFailed()) {
      selected = null;
      items = null;
    }
  }
  
  public List<User> getItems() {
    if (items == null) {
      items = getFacade().findAll();
    }
    return items;
  }
  
  private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
    if (selected != null) {
      setEmbeddableKeys();
      try {
        if (persistAction != JsfUtil.PersistAction.DELETE) {
          getFacade().edit(selected);
        } else {
          getFacade().remove(selected);
        }
        JsfUtil.addSuccessMessage(successMessage);
      } catch (EJBException ex) {
        String msg = "";
        Throwable cause = ex.getCause();
        if (cause != null) {
          msg = cause.getLocalizedMessage();
        }
        if (msg.length() > 0) {
          JsfUtil.addErrorMessage(msg);
        } else {
          JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
      } catch (Exception ex) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
      }
    }
  }
  
  public User getUser(Integer id) {
    return (User)getFacade().find(id);
  }
  
  public List<User> getItemsAvailableSelectMany() {
    return getFacade().findAll();
  }
  
  public List<User> getItemsAvailableSelectOne() {
    return getFacade().findAll();
  }
}