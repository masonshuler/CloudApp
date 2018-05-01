/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  *
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. *
 * */
package com.mycompany.controllers;

import com.mycompany.EntityBeans.Item;
import com.mycompany.EntityBeans.ItemPhoto;
import com.mycompany.EntityBeans.User;
import com.mycompany.controllers.util.JsfUtil;
import com.mycompany.controllers.util.JsfUtil.PersistAction;
import com.mycompany.FacadeBeans.ItemFacade;
import com.mycompany.FacadeBeans.ItemPhotoFacade;
import com.mycompany.FacadeBeans.UserFacade;
import com.mycompany.managers.AccountManager;
import com.mycompany.managers.Constants;
import com.mycompany.managers.PhotoFileManager;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.event.ActionEvent;

@Named("itemController")
@SessionScoped
public class ItemController implements Serializable {

    @EJB
    private ItemFacade itemFacade;
    @EJB
    private UserFacade userFacade;
    @EJB
    private ItemPhotoFacade itemPhotoFacade;
    private List<Item> items = null;
    private List<Item> reservedItems = null;
    private Item selected;
    HashMap<Integer, String> cleanedItemHashMap = null;

    private int minPrice;
    private int maxPrice;

    private String searchString;
    private String searchField;
    private List<Item> searchItems = null;
    
    public ItemController() {
        minPrice = 0;
        maxPrice = 200;
    }

    public Item getSelected() {
        return selected;
    }

    public void setSelected(Item selected) {
        this.selected = selected;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getSearchField() {
        return searchField;
    }

    public void setSearchField(String searchField) {
        this.searchField = searchField;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ItemFacade getItemFacade() {
        return itemFacade;
    }

    private ItemPhotoFacade getItemPhotoFacade() {
        return itemPhotoFacade;
    }

    /*
     ********************************************
     *   Display List.xhtml JSF Facelets Page   *
     ********************************************
     */
    public String goBackToList() {
        // Unselect a videoselected in search results if any before showing the List page
        selected = null;
        searchString = null;
        return "/publicItem/List?faces-redirect=true";
    }

    /*
     *************************************************************************
     *   Search searchString in searchField and Return the Search Results    *
     *************************************************************************
     Return the list of object references of all those videos where the search
     string 'searchString' entered by the user is contained in the searchField.
     */
    public List<Item> getSearchItems() {
        System.out.println("SearchItems");
        switch (searchField) {
            case "Item Title":
                searchItems = getItemFacade().titleQuery(searchString);
                break;
            case "Item Description":
                searchItems = getItemFacade().descriptionQuery(searchString);
                break;
            case "Item Category":
                searchItems = getItemFacade().categoryQuery(searchString);
                break;
            case "All":
                searchItems = getItemFacade().allQuery(searchString);
                break;
            default:
                return searchItems;
        }
        searchField = "";
        return searchItems;
    }

    /*
     ********************************************
     *   Display the SearchResults.xhtml Page   *
     ********************************************
     */
    /**
     * @SessionScoped enables to preserve the values of the instance variables
     * for the SearchResults.xhtml page to access.
     *
     * @param actionEvent refers to clicking the Submit button
     * @throws IOException if the page to be redirected to cannot be found
     */
    public void search(ActionEvent actionEvent) throws IOException {
        // Unselect previously selected video if any before showing the search results
        selected = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("SearchResults.xhtml");
    }

    public void upvote(Item item) {
        float rating = item.getRating();
        item.setRating(rating + 1);
        getItemFacade().edit(item);
    }

    public void downvote(Item item) {
        float rating = item.getRating();
        item.setRating(rating - 1);
        getItemFacade().edit(item);
    }

    public void reserve(AccountManager accountManager) {
        if (!accountManager.isLoggedIn()) {
            persist(PersistAction.SHARE, "Cannot reserve the Item since No User is Signed In!");
        } else {
            items = null;
            reservedItems = null;
            selected.setReservedUser(accountManager.getSelected());
            persist(PersistAction.SHARE, ResourceBundle.getBundle("/Bundle").getString("ItemReserved"));
        }
    }

    public void unreserve() {
        System.out.println("ITEMCONTROLLER: Unreserving");
        items = null;
        reservedItems = null;
        selected.setReservedUser(null);
        System.out.println("ITEMCONTROLLER: User set to null");
        persist(PersistAction.SHARE, ResourceBundle.getBundle("/Bundle").getString("ItemUnreserved"));
    }

    public Item prepareCreate() {
        selected = new Item();
        selected.setRating(0);
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null;
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Item> getItems() {
        if (items == null || items.isEmpty()) {
            items = getItemFacade().findAll();
        }
        List<Item> priceFiltered;
        priceFiltered = new ArrayList<>();
        items.stream().filter((item) -> (item.getPrice() >= minPrice && item.getPrice() <= maxPrice)).forEachOrdered((item) -> {
            priceFiltered.add(item);
        });
        items = priceFiltered;
        return priceFiltered;
    }

    private UserFacade getUserFacade() {
        return userFacade;
    }

    public List<Item> getReservedItems() {
        if (reservedItems == null) {
            String usernameOfSignedInUser = (String) FacesContext.getCurrentInstance()
                    .getExternalContext().getSessionMap().get("username");
            User signedInUser = getUserFacade().findByUsername(usernameOfSignedInUser);
            Integer userId = signedInUser.getId();
            reservedItems = getItemFacade().findReservedItemsByUserID(userId);
            cleanedItemHashMap = new HashMap<>();

            for (int i = 0; i < reservedItems.size(); i++) {

                String storedItemName = reservedItems.get(i).getTitle();

                Integer itemId = reservedItems.get(i).getId();

                cleanedItemHashMap.put(itemId, storedItemName);
            }
        }
        return reservedItems;
    }

    public String getPicture(Item anItem) {
        Integer itemId = anItem.getId();

        List<ItemPhoto> photoList = getItemPhotoFacade().findPhotosByItemID(itemId);

        if (photoList.isEmpty()) {
            /*
            No user photo exists. Return defaultUserPhoto.png 
            in CloudStorage/PhotoStorage.
             */
            return Constants.DEFAULT_PHOTO_RELATIVE_PATH;
        }

        String photoName = photoList.get(0).getPhotoFilename();

        String relativePhotoFilePath = Constants.ITEMS_RELATIVE_PATH + photoName;

        return relativePhotoFilePath;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getItemFacade().edit(selected);
                } else {
                    getItemFacade().remove(selected);
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
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Item getItem(java.lang.Integer id) {
        return getItemFacade().find(id);
    }

    public List<Item> getItemsAvailableSelectMany() {
        return getItemFacade().findAll();
    }

    public List<Item> getItemsAvailableSelectOne() {
        return getItemFacade().findAll();
    }

    @FacesConverter(forClass = Item.class)
    public static class ItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ItemController controller = (ItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "itemController");
            return controller.getItem(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Item) {
                Item o = (Item) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Item.class.getName()});
                return null;
            }
        }

    }

}
