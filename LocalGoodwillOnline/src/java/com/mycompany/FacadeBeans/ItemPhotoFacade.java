/*
 * Created by Scott McGhee on 2018.04.22  * 
 * Copyright © 2018 Scott McGhee. All rights reserved. * 
 */
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.ItemPhoto;
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
    
}