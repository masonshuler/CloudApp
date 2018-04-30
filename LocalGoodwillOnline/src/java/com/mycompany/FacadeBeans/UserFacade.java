/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emcghee
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "LocalGoodwillOnlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(User.class);
    }
    
    /*
    ======================================================
    The following methods are added to the generated code.
    ======================================================
     */
    /**
     * @param id is the Primary Key of the User entity in a table row in the CloudDriveDB database.
     * @return object reference of the User entity whose primary key is id
     */
    public User getUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        return em.find(User.class, id);
    }

    /**
     * @param email is the email attribute (column) value of the user
     * @return object reference of the User entity whose email is email
     */
    public User findByEmail(String email) {
        if (em.createQuery("SELECT c FROM User c WHERE c.email = :email ")
                .setParameter("email", email)
                .getResultList().isEmpty()) {
            return null;
        } else {
            return (User) (em.createQuery("SELECT c FROM User c WHERE c.email = :email")
                    .setParameter("email", email)
                    .getSingleResult());
        }
    }

    /**
     * Deletes the User entity whose primary key is id
     * @param id is the Primary Key of the User entity in a table row in the CloudDriveDB database.
     */
    public void deleteUser(int id) {
        
        // The find method is inherited from the parent AbstractFacade class
        User user = em.find(User.class, id);
        
        // The remove method is inherited from the parent AbstractFacade class
        em.remove(user); 
    }
    
}
