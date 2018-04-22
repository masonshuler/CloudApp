package com.mycompany.EntityBeans;

import com.mycompany.EntityBeans.Item;
import com.mycompany.EntityBeans.UserPhoto;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-22T18:57:24")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile SingularAttribute<User, String> address2;
    public static volatile SingularAttribute<User, String> city;
    public static volatile SingularAttribute<User, Integer> securityQuestion;
    public static volatile SingularAttribute<User, String> securityAnswer;
    public static volatile CollectionAttribute<User, Item> itemCollection;
    public static volatile SingularAttribute<User, String> address1;
    public static volatile SingularAttribute<User, Boolean> isAdmin;
    public static volatile SingularAttribute<User, String> passwordHash;
    public static volatile CollectionAttribute<User, UserPhoto> userPhotoCollection;
    public static volatile SingularAttribute<User, String> zipcode;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> phoneNumber;
    public static volatile SingularAttribute<User, String> middleName;
    public static volatile SingularAttribute<User, Integer> id;
    public static volatile SingularAttribute<User, String> state;
    public static volatile SingularAttribute<User, String> email;

}