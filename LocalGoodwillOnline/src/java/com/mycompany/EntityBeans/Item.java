/**
 * Created by Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman on 2018.04.22  * 
 * Copyright © 2018 Jordan Kuhn, Scott McGhee, Shuvo Rahman, Mason Shuler, Matt Tuckman. All rights reserved. * 
 **/

package com.mycompany.EntityBeans;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "Item")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i")
    , @NamedQuery(name = "Item.findById", query = "SELECT i FROM Item i WHERE i.id = :id")
    , @NamedQuery(name = "Item.findByTitle", query = "SELECT i FROM Item i WHERE i.title = :title")
    , @NamedQuery(name = "Item.findByPrice", query = "SELECT i FROM Item i WHERE i.price = :price")
    , @NamedQuery(name = "Item.findByRating", query = "SELECT i FROM Item i WHERE i.rating = :rating")
    , @NamedQuery(name = "Item.findBySize", query = "SELECT i FROM Item i WHERE i.size = :size")
    , @NamedQuery(name = "Item.findByCategory", query = "SELECT i FROM Item i WHERE i.category = :category")
    , @NamedQuery(name = "Item.findByDescription", query = "SELECT i FROM Item i WHERE i.description = :description")
    , @NamedQuery(name = "Item.findByDatePublished", query = "SELECT i FROM Item i WHERE i.datePublished = :datePublished")
    , @NamedQuery(name = "Item.findReservedItemByUserId", query = "SELECT u FROM Item u WHERE u.reservedUser.id = :userId")})
public class Item implements Serializable, Cloneable {

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
    @Size(min = 1, max = 32)
    @Column(name = "size")
    private String size;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rating")
    private float rating;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "category")
    private String category;
    @Size(max = 512)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date_published")
    @Temporal(TemporalType.DATE)
    private Date datePublished;
    @JoinColumn(name = "reserved_user", referencedColumnName = "id")
    @ManyToOne
    private User reservedUser;
    @OneToMany(mappedBy = "itemId")
    private Collection<ItemPhoto> itemPhotoCollection;

    public Item() {
    }

    public Item(Integer id) {
        this.id = id;
    }

    public Item(Integer id, String title, float price, float rating, String size, String category, Date datePublished) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.rating = rating;
        this.size = size;
        this.category = category;
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
    
    /**
     * Display price in a nice format.
     * @return Formatted price.
     */
    public String priceString() {
        return String.format("%.2f", price);
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
    
    /**
     * Display rating in nice format.
     * @return Formatted rating.
     */
    public String ratingString() {
        return String.format("%.0f", rating);
    }
    
    public String getSize() {
        return size;
    }
    
    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public User getReservedUser() {
        return reservedUser;
    }

    public void setReservedUser(User reservedUser) {
        this.reservedUser = reservedUser;
    }
    
    /**
     * Display reserved status
     * @return Reserved status
     */
    public String reservedString() {
        if (reservedUser == null) 
            return "Available";
        else
            return "Reserved";
    }
    
    @XmlTransient
    public Collection<ItemPhoto> getItemPhotoCollection() {
        return itemPhotoCollection;
    }

    public void setItemPhotoCollection(Collection<ItemPhoto> itemPhotoCollection) {
        this.itemPhotoCollection = itemPhotoCollection;
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
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }
    
    @Override
    public Item clone() throws CloneNotSupportedException {
        return (Item) super.clone();
    }
    
}
