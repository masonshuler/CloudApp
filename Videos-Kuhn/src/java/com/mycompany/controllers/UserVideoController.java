package com.mycompany.controllers;

import com.mycompany.EntityBeans.PublicVideo;
import com.mycompany.EntityBeans.UserVideo;
import com.mycompany.FacadeBeans.PublicVideoFacade;
import com.mycompany.FacadeBeans.UserVideoFacade;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.managers.AccountManager;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named("userVideoController")
@SessionScoped
public class UserVideoController
  implements Serializable
{
  private static final String[] CATEGORIES = { "Cars & Vehicles", "Comedy", "Education", "Entertainment", "Film & Animation", "Gaming", "How-to & Style", "Music", "News & Politics", "Non-profits & Activism", "People & Blogs", "Pets & Animals", "Science & Technology", "Sports", "Travel & Events" };
  
  @Inject
  private AccountManager accountManager;
  
  @EJB
  private PublicVideoFacade publicVideoFacade;
  
  @Inject
  private PublicVideoController publicVideoController;
  
  @EJB
  private UserVideoFacade ejbFacade;
  
  private List<UserVideo> items = null;
  private UserVideo selected;
  
  public UserVideoController() {}
  
  public UserVideo getSelected()
  {
    return selected;
  }
  
  public void setSelected(UserVideo selected) {
    this.selected = selected;
  }
  
  public String[] getCategories() {
    return CATEGORIES;
  }
  
  protected void setEmbeddableKeys() {}
  
  protected void initializeEmbeddableKey() {}
  
  private UserVideoFacade getFacade()
  {
    return ejbFacade;
  }
  
  public UserVideo prepareCreate() {
    selected = new UserVideo();
    initializeEmbeddableKey();
    return selected;
  }
  
  public void cancelCreate() {
    selected = null;
  }
  
  public void create() {
    selected.setUserId(accountManager.getSelected());
    persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UserVideoCreated"));
    if (!JsfUtil.isValidationFailed()) {
      items = null;
    }
  }
  
  public void update() {
    persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UserVideoUpdated"));
  }
  
  public void destroy() {
    persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UserVideoDeleted"));
    if (!JsfUtil.isValidationFailed()) {
      selected = null;
      items = null;
    }
  }
  
  public void share() {
    PublicVideo video = new PublicVideo(selected.getId(), selected.getTitle(), selected.getDescription(), selected.getYoutubeVideoId(), selected.getDuration(), selected.getDatePublished(), selected.getCategory());
    video.setId(null);
    publicVideoController.setSelected(video);
    publicVideoController.create();
  }
  
  public List<UserVideo> getItems() {
    if (items == null) {
      items = getFacade().findUsersVideosByUserID(accountManager.getSelected().getId());
    }
    return items;
  }
  
  public void invalidateItems() {
    items = null;
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
  
  public UserVideo getUserVideo(Integer id) {
    return (UserVideo)getFacade().find(id);
  }
  
  public List<UserVideo> getItemsAvailableSelectMany() {
    return getFacade().findAll();
  }
  
  public List<UserVideo> getItemsAvailableSelectOne() {
    return getFacade().findAll();
  }
}