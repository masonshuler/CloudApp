package com.mycompany.controllers;

import com.mycompany.EntityBeans.PublicVideo;
import com.mycompany.EntityBeans.UserVideo;
import com.mycompany.FacadeBeans.PublicVideoFacade;
import com.mycompany.FacadeBeans.UserVideoFacade;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.managers.AccountManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

@Named("publicVideoController")
@SessionScoped
public class PublicVideoController
  implements Serializable
{
    // searchField refers to Video Title, Desc, Cat, or All
    private String searchField;

    // searchString contains the character string the user entered for searching the selected searchField
    private String searchString;
    
    private static final String[] CATEGORIES = { "Cars & Vehicles", "Comedy", "Education", "Entertainment", "Film & Animation", "Gaming", "How-to & Style", "Music", "News & Politics", "Non-profits & Activism", "People & Blogs", "Pets & Animals", "Science & Technology", "Sports", "Travel & Events" };

    @Inject
    private AccountManager accountManager;

    @EJB
    private UserVideoFacade userVideoFacade;

    @Inject
    private UserVideoController userVideoController;

    @EJB
    private PublicVideoFacade ejbFacade;

    private List<PublicVideo> items = null;
    // 'searchItems' is a List containing the object references of Videos found in the search
    private List<PublicVideo> searchItems = null;
    
    private PublicVideo selected;
    public PublicVideoController() {}
    
    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }
    
    public PublicVideo getSelected()
    {
        return selected;
    }

    public void setSelected(PublicVideo selected) {
        this.selected = selected;
    }

    public String[] getCategories() {
        return CATEGORIES;
    }
  
    protected void setEmbeddableKeys() {}

    protected void initializeEmbeddableKey() {}

    private PublicVideoFacade getFacade()
    {
        return ejbFacade;
    }

    public PublicVideo prepareCreate() {
        selected = new PublicVideo();
        initializeEmbeddableKey();
        return selected;
    }

    public void cancelCreate() {
        selected = null;
    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PublicVideoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;
        }
    }

    public void update() {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PublicVideoUpdated"));
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PublicVideoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            items = null;
        }
    }
  
    public void share() {
        if (accountManager.isLoggedIn()) {
            UserVideo userVideo = new UserVideo(null, selected.getTitle(), selected.getDescription(), selected.getYoutubeVideoId(), selected.getDuration(), selected.getDatePublished(), selected.getCategory());
            userVideoController.setSelected(userVideo);
            userVideoController.create();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ResourceBundle.getBundle("/Bundle").getString("ShareNotSignedIn")));
        }
    }

    public List<PublicVideo> getItems() {
        if (items == null) {
            items = getFacade().findAll();
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
  
    public PublicVideo getPublicVideo(Integer id) {
        return (PublicVideo)getFacade().find(id);
    }

    public List<PublicVideo> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<PublicVideo> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    /*
     *************************************************************************
     *   Search searchString in searchField and Return the Search Results    *
     *************************************************************************
     Return the list of object references of all those videos where the search
     string 'searchString' entered by the user is contained in the searchField.
     */
    public List<PublicVideo> getSearchItems() {
        if (searchItems == null) {
            switch (searchField) {
                case "Video Title":
                    // Return the list of object references of all those videos where 
                    // video title contains the search string 'searchString' entered by the user.
                    searchItems = getFacade().titleQuery(searchString);
                    break;
                case "Video Description":
                    // Return the list of object references of all those videos where 
                    // video description contains the search string 'searchString' entered by the user.
                    searchItems = getFacade().descriptionQuery(searchString);
                    break;
                case "Video Category":
                    // Return the list of object references of all those videos where 
                    // video category contains the search string 'searchString' entered by the user.
                    searchItems = getFacade().categoryQuery(searchString);break;
                default:
                    // Return the list of object references of all those videos where video title,
                    // video description, or video category contains the search string 'searchString' entered by the user.
                    searchItems = getFacade().allQuery(searchString);
                    break;
            }
        }
        return searchItems;
    }

    /*
     ********************************************
     *   Display the SearchResults.xhtml Page   *
     ********************************************
     */
    /**
     * @SessionScoped enables to preserve the values of the instance variables for the SearchResults.xhtml page to access.
     *
     * @param actionEvent refers to clicking the Submit button
     * @throws IOException if the page to be redirected to cannot be found
     */
    public void search(ActionEvent actionEvent) throws IOException {
        // Unselect previously selected company if any before showing the search results
        selected = null;
        searchItems = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("/Videos-Kuhn/publicVideo/SearchResults.xhtml");
    }
}