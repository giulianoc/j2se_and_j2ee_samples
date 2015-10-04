package com.ejb.sessions;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import java.io.Serializable;

@Singleton(name = "ShopperCount")
@LocalBean
@Startup
public class ShopperCountBean implements Serializable
{

    private static final long serialVersionUID = -1L;

    private int shopperCount;

    public void incrementShopperCount()
    {
        shopperCount++;
    }

    public int getShopperCount()
    {
        return shopperCount;
    }

    @PostConstruct
    public void resetShopperCount()
    {
        System.out.println("resetShopperCount is called");

        shopperCount = 0;
    }

}