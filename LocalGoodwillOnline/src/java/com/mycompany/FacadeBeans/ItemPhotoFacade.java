/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.ItemPhoto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author emcghee
 */
@Stateless
public class ItemPhotoFacade extends AbstractFacade<ItemPhoto> {

    @PersistenceContext(unitName = "LocalGoodwillOnlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemPhotoFacade() {
        super(ItemPhoto.class);
    }
    
    public List<ItemPhoto> findPhotosByItemID(Integer itemID) {

        return (List<ItemPhoto>) em.createNamedQuery("ItemPhoto.findPhotosByItemID")
                .setParameter("itemId", itemID)
                .getResultList();
    }
    
}
