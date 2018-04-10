/*
 * Created by Jordan Kuhn on 2018.02.15 * 
 * Copyright © 2018 Jordan Kuhn. All rights reserved. * 
 */
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.UserVideo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Jordan
 */
@Stateless
public class UserVideoFacade extends AbstractFacade<UserVideo> {
  
    @PersistenceContext(unitName = "Videos-KuhnPU")
    private EntityManager em;

    // @Override annotation indicates that the super class AbstractFacade's getEntityManager() method is overridden.
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /* 
    This constructor method invokes the parent abstract class AbstractFacade.java's
    constructor method AbstractFacade, which in turn initializes its entityClass instance
    variable with the Photo class object reference returned by the Video.class.
     */
    public UserVideoFacade() {
        super(UserVideo.class);
    }
    
    public UserVideo getUserVideo(int id) {
        return (UserVideo)em.find(UserVideo.class, Integer.valueOf(id));
    }

    /**
     * @param userID is the Primary Key of the User entity in a table row in the UsersVideosDB database.
     * @return a list of photos associated with the User whose primary key is userID
     */
    public List<UserVideo> findUsersVideosByUserID(Integer userID)
    {
        List<UserVideo> UsersVideos = em.createNamedQuery("UserVideo.findUsersVideosByUserId").setParameter("userId", userID).getResultList();
        return UsersVideos;
    }
}