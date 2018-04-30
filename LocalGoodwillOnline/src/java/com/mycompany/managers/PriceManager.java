/*
 * Created by Osman Balci on 2018.03.26
 * Copyright © 2018 Osman Balci. All rights reserved.
 */
package com.mycompany.managers;

import com.mycompany.EntityBeans.Item;
import com.mycompany.controllers.EmailController;
import com.mycompany.controllers.ItemController;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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

    public String totalCostString() {
        return String.format("%.2f", totalCost);
    }

    // Reset all selected values upon clicking Clear Order
    public void clearOrder() {
        orderList.clear();
        setDefaultValues();
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
    public String orderSubmitted(ItemController itemController, EmailController emailController, 
            AccountManager accountManager) throws CloneNotSupportedException, AddressException, 
            MessagingException {
        removed = cloneList(orderList);

        StringBuilder builder = new StringBuilder();
        builder.append("A new order has just been made for ");
        builder.append(accountManager.getSelected().getFirstName());
        builder.append(" ");
        builder.append(accountManager.getSelected().getLastName());
        builder.append(".<br /><br />");

        builder.append("The ordered items are:<br />");

        List<Item> reserved = itemController.getReservedItems();
        for (int i = 0; i < orderList.size(); i++) {
            Item current = orderList.get(i);
            builder.append(current.getTitle());
            builder.append("<br />");
            itemController.setSelected(current);
            itemController.destroy();
            reserved.remove(current);
        }
        builder.append("<br /><br />");

        builder.append("The order should be delivered to: ");
        builder.append(accountManager.getSelected().getAddress1());
        builder.append(" ");
        builder.append(accountManager.getSelected().getAddress2());
        builder.append(", ");
        builder.append(accountManager.getSelected().getCity());
        builder.append(", ");
        builder.append(accountManager.getSelected().getState());
        builder.append(" ");
        builder.append(accountManager.getSelected().getZipcode());

        orderList.clear();

        // Add sales tax to totalCost
        totalCost = totalCost + totalCost * salesTax;

        // Convert totalCost to have only 2 decimal points by rounding
        double totalCostRounded = Math.round(totalCost * 100);
        totalCost = totalCostRounded / 100;

        emailController.setEmailTo("e.scottmcghee@gmail.com");
        emailController.setEmailSubject("Goodwill Order");
        emailController.setEmailBody(builder.toString());
        
        emailController.sendEmail();

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
        } else {
            orderList.add(item);
        }
        onUserSelection();
    }

    public List<Item> getRemoved() {
        return removed;
    }
}
