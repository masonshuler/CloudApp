/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.Item;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ItemFacade extends AbstractFacade<Item> {

    @PersistenceContext(unitName = "LocalGoodwillOnlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemFacade() {
        super(Item.class);
    }
    
    /**
     * Searches GoodwillOnlineDB for videos where video name contains the searchString entered by the user.
     *
     * @param searchString contains the search string the user entered for searching video names
     * @return A list of Public Video object references as the search results
     */
    // This method is called in the getSearchItems() method in ItemController.java
    public List<Item> titleQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the video name 
        searchString = "%" + searchString + "%";
        System.out.println("Title");

        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Item c WHERE c.title LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }

    /*
    ------------------------------------
    Search Category: STOCK TICKER SYMBOL
    ------------------------------------
     */
    /**
     * Searches GoodwillOnlineDB for videos where video's stock ticker name contains the searchString entered by the user.
     *
     * @param searchString contains the search string the user entered for searching stock tickers
     * @return A list of Public Video object references as the search results
     */
    // This method is called in the getSearchItems() method in ItemController.java
    public List<Item> descriptionQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the stock ticker name 
        searchString = "%" + searchString + "%";
                        System.out.println("Description");

        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Item c WHERE c.description LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }

    /*
    -------------------------------------
    Search Category: BUSINESS SECTOR NAME
    -------------------------------------
     */
    /**
     * Searches GoodwillOnlineDB for videos where business Sector name contains the searchString entered by the user.
     *
     * @param searchString contains the search string the user entered for searching business Sector names
     * @return A list of Public Video object references as the search results
     */
    // This method is called in the getSearchItems() method in ItemController.java
    public List<Item> categoryQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the business Sector name 
        searchString = "%" + searchString + "%";
                        System.out.println("Category");

        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Item c WHERE c.category LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }

    /*
    --------------------
    Search Category: ALL
    --------------------
     */
    /**
     * Searches GoodwillOnlineDB for videos where video name, ticker, and sector name contains the searchString entered by the user.
     *
     * @param searchString contains the search string the user entered for searching video name, ticker, and sector name
     * @return A list of Public Video object references as the search results
     */
    // This method is called in the getSearchItems() method in ItemController.java
    public List<Item> allQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in video name, ticker, or sector name 
        searchString = "%" + searchString + "%";
                        System.out.println("All");

        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT c FROM Item c WHERE c.title LIKE :searchString OR c.description LIKE :searchString OR c.category LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }
    
    /**
     *
     * @param userID is the Primary Key of the user entity in the database
     * @return a list of object references of userFiles that belong to the user whose DB Primary Key = userID
     */
    public List<Item> findReservedItemsByUserID(Integer userID) {
        /*
        The following @NamedQuery definition is given in UserFile.java entity class file:
        @NamedQuery(name = "UserFile.findUserFilesByUserId", query = "SELECT u FROM UserFile u WHERE u.userId.id = :userId")
        
        The following statement obtaines the results from the named database query.
         */
        List<Item> reservedItems = em.createNamedQuery("Item.findReservedItemByUserId")
                .setParameter("userId", userID)
                .getResultList();

        return reservedItems;
    }
    
}
