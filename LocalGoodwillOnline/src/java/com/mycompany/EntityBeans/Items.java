/*
 * Created by Mason Shuler on 2018.03.29  * 
 * Copyright © 2018 Mason Shuler. All rights reserved. * 
 */
package com.mycompany.EntityBeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mason
 */
@Entity
@Table(name = "Items")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Items.findAll", query = "SELECT i FROM Items i")
    , @NamedQuery(name = "Items.findById", query = "SELECT i FROM Items i WHERE i.id = :id")
    , @NamedQuery(name = "Items.findByTitle", query = "SELECT i FROM Items i WHERE i.title = :title")
    , @NamedQuery(name = "Items.findByPrice", query = "SELECT i FROM Items i WHERE i.price = :price")
    , @NamedQuery(name = "Items.findByRating", query = "SELECT i FROM Items i WHERE i.rating = :rating")
    , @NamedQuery(name = "Items.findByCategory", query = "SELECT i FROM Items i WHERE i.category = :category")
    , @NamedQuery(name = "Items.findByDescription", query = "SELECT i FROM Items i WHERE i.description = :description")
    , @NamedQuery(name = "Items.findByDatePublished", query = "SELECT i FROM Items i WHERE i.datePublished = :datePublished")})
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private float price;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private float rating;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category")
    private String category;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Column(name = "image")
    private byte[] image;
    @Size(max = 512)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_published")
    @Temporal(TemporalType.DATE)
    private Date datePublished;
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User userId;

    public Items() {
    }

    public Items(Integer id) {
        this.id = id;
    }

    public Items(Integer id, String title, float price, float rating, String category, byte[] image, Date datePublished) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.category = category;
        this.image = image;
        this.datePublished = datePublished;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public void setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Items)) {
            return false;
        }
        Items other = (Items) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.EntityBeans.Items[ id=" + id + " ]";
    }
    
}
