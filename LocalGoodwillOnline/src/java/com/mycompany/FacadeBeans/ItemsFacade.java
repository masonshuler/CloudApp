/*
 * Created by Mason Shuler on 2018.03.29  * 
 * Copyright © 2018 Mason Shuler. All rights reserved. * 
 */
package com.mycompany.FacadeBeans;

import com.mycompany.EntityBeans.Items;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Mason
 */
@Stateless
public class ItemsFacade extends AbstractFacade<Items> {

    @PersistenceContext(unitName = "LocalGoodwillOnlinePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ItemsFacade() {
        super(Items.class);
    }
    
}
