package com.ejb.sessions;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;
import java.io.Serializable;
import java.util.ArrayList;

@Stateful(name = "ShoppingCart")
@LocalBean
public class ShoppingCartBean implements Serializable
{

    private static final long serialVersionUID = -1L;

    private ArrayList cartItems;

    public void addWineItem(String wine)
    {
        cartItems.add(wine);
    }

    public void removeWineItem(String wine)
    {
        cartItems.remove(wine);
    }

    public ArrayList getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList cartItems) {
        this.cartItems = cartItems;
    }

    @PostConstruct
    public void initialize()
    {
        cartItems = new ArrayList();
    }

    @PreDestroy
    public void exit()
    {
        System.out.println("Save items list into the database");

        cartItems.clear();
    }

    @Remove
    public void stopSession()
    {
        System.out.println("stopSession called (with @Remove annotation");
    }
}