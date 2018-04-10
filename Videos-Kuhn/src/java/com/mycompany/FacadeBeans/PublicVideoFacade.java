package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.PublicVideo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class PublicVideoFacade extends AbstractFacade<PublicVideo>
{
    @PersistenceContext(unitName="Videos-KuhnPU")
    private EntityManager em;

    protected EntityManager getEntityManager()
    {
      return em;
    }

    public PublicVideoFacade() 
    { 
        super(PublicVideo.class); 
    }
    
    // This method is called in the getSearchItems() method in PublicVideoController.java
    public List<PublicVideo> titleQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the video title 
        searchString = "%" + searchString + "%";
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT v FROM PublicVideo v WHERE v.title LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }
    
    // This method is called in the getSearchItems() method in PublicVideoController.java
    public List<PublicVideo> descriptionQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the video description 
        searchString = "%" + searchString + "%";
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT v FROM PublicVideo v WHERE v.description LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }
    
    // This method is called in the getSearchItems() method in PublicVideoController.java
    public List<PublicVideo> categoryQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in the video category 
        searchString = "%" + searchString + "%";
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT v FROM PublicVideo v WHERE v.category LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }
    
    // This method is called in the getSearchItems() method in PublicVideoController.java
    public List<PublicVideo> allQuery(String searchString) {
        // Place the % wildcard before and after the search string to search for it anywhere in video title, description, or category
        searchString = "%" + searchString + "%";
        // Conduct the search in a case-insensitive manner and return the results in a list.
        return getEntityManager().createQuery("SELECT v FROM PublicVideo v WHERE v.title LIKE :searchString OR v.description LIKE :searchString OR v.category LIKE :searchString").setParameter("searchString", searchString).getResultList();
    }
}