package com.mycompany.EntityBeans;

import com.mycompany.EntityBeans.ItemPhoto;
import com.mycompany.EntityBeans.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-22T18:57:24")
@StaticMetamodel(Item.class)
public class Item_ { 

    public static volatile SingularAttribute<Item, Date> datePublished;
    public static volatile SingularAttribute<Item, Float> price;
    public static volatile SingularAttribute<Item, Float> rating;
    public static volatile SingularAttribute<Item, String> description;
    public static volatile CollectionAttribute<Item, ItemPhoto> itemPhotoCollection;
    public static volatile SingularAttribute<Item, Integer> id;
    public static volatile SingularAttribute<Item, String> title;
    public static volatile SingularAttribute<Item, String> category;
    public static volatile SingularAttribute<Item, User> reservedUser;

}