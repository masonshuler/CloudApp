/*
 * Created by Osman Balci on 2018.03.26
 * Copyright © 2018 Osman Balci. All rights reserved.
 */
package com.mycompany.managers;

import com.mycompany.EntityBeans.Item;
import com.mycompany.controllers.ItemController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Balci
 */
// The SessionScoped annotation preserves the values of the
// instance variables accessed by some facelets pages.
@SessionScoped
@Named(value = "priceManager")

public class PriceManager implements Serializable {

    /*
    ===============================
    Instance Variables (Properties)
    ===============================
     */
    private final List<Item> orderList = new ArrayList<>();
    private List<Item> removed = new ArrayList<>();

    private double totalCost;
    private final double salesTax = 0.053;

    /*
    The PostConstruct annotation designates the init() method to be executed after
    dependency injection is done to perform any initialization. Think about it as a
    class constructor that dresses up the PriceManager class object in this init()
    method right after it is instantiated.
     */
    @PostConstruct
    public void init() {
        orderList.clear();
        setDefaultValues();
    }

    // Set some instance variables to their default values
    private void setDefaultValues() {
        totalCost = 0;
    }

    // Provide the sales tax to ConfirmOrder.xhtml
    public Double getSalesTax() {
        return salesTax;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    // Reset all selected values upon clicking Clear Order
    public String clearOrder(AccountManager accountManager) {
        orderList.clear();
        setDefaultValues();
        accountManager.setCcNumberLast4("");
        return "/PrepareOrder?faces-redirect=true";
    }

    /*
    Update instance variable totalCost's value every time the user selects a
    topping name by clicking its check box.
     */
    public void onUserSelection() {
        totalCost = 0;
        
        for (int i = 0; i < orderList.size(); i++) {
            Item current = orderList.get(i);
            totalCost += current.getPrice();
        }

        // Convert totalCost to have only 2 decimal points by rounding
        // e.g., totalCost = 12.768956 --> x100 --> 1276.8956 --> Math.round --> 1277
        // totalCostRounded = 1277 --> 1277 / 100 = 12.77 = totalCost 
        double totalCostRounded = Math.round(totalCost * 100);
        totalCost = totalCostRounded / 100;
    }

    // Process the submitted pizza order
    public String orderSubmitted(ItemController itemController) throws CloneNotSupportedException {
        removed = cloneList(orderList);
        List<Item> reserved = itemController.getReservedItems();
        for (int i = 0; i < orderList.size(); i++) {
            Item current = orderList.get(i);
            System.out.println("Item: " + current.getTitle());
            itemController.setSelected(current);
            itemController.destroy();
            reserved.remove(current);
        }
        orderList.clear();

        // Add sales tax to totalCost
        totalCost = totalCost + totalCost * salesTax;

        // Convert totalCost to have only 2 decimal points by rounding
        double totalCostRounded = Math.round(totalCost * 100);
        totalCost = totalCostRounded / 100;

        // Redirect to show the ConfirmOrder page
        return "/ConfirmOrder?faces-redirect=true";
    }
    
    public List<Item> cloneList(List<Item> list) throws CloneNotSupportedException {
        List<Item> clone = new ArrayList<>(orderList.size());
        for (Item item : orderList) {
            clone.add(item.clone());
        }
        return clone;
    }
    
    public void setBought(Item item) {
        System.out.println("Set Bought: " + item.getTitle());
        if (orderList.contains(item)) {
            orderList.remove(item);
        }
        else {
            orderList.add(item);
        }
        onUserSelection();
    }
    
    public List<Item> getRemoved() {
        return removed;
    }
}