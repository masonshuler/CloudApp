package com.mycompany.EntityBeans;

import com.mycompany.EntityBeans.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-04-03T21:09:24")
@StaticMetamodel(UserVideo.class)
public class UserVideo_ { 

    public static volatile SingularAttribute<UserVideo, String> duration;
    public static volatile SingularAttribute<UserVideo, Date> datePublished;
    public static volatile SingularAttribute<UserVideo, String> youtubeVideoId;
    public static volatile SingularAttribute<UserVideo, String> description;
    public static volatile SingularAttribute<UserVideo, Integer> id;
    public static volatile SingularAttribute<UserVideo, String> title;
    public static volatile SingularAttribute<UserVideo, String> category;
    public static volatile SingularAttribute<UserVideo, User> userId;

}