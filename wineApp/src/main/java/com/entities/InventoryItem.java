package com.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "InventoryItem.findAll", query = "select o from InventoryItem o"),
        @NamedQuery(name = "InventoryItem.findItemByWine", query = "select o from InventoryItem o where o.wine = :wine")})
public class InventoryItem extends WineItem {

    @Temporal(TemporalType.DATE)
    @Column(name = "STOCK_DATE")
    private Date stockDate;

    @Column(name = "WHOLESALE_PRICE")
    private Float wholesalePrice;

    public InventoryItem() {
    }

    public InventoryItem(int quantity, Wine wine, Date stockDate, Float wholesalePrice) {
        super(quantity, wine);
        setStockDate(stockDate);
        setWholesalePrice(wholesalePrice);
    }

    public Date getStockDate() {
        return stockDate;
    }

    public void setStockDate(Date stockDate) {
        this.stockDate = stockDate;
    }

    public Float getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(Float wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }
}
