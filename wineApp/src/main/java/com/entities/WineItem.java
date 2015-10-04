package com.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 17:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(name = "WineItem.findAll", query = "select o from WineItem o")})
@Table(name = "WINE_ITEM")
public abstract class WineItem implements Serializable{

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int quantity;

    private Integer version;

    @ManyToOne
    @JoinColumn(name = "WINE_ID")
    private Wine wine;

    public WineItem() {
    }

    public WineItem(int quantity, Wine wine) {
        this.setQuantity(quantity);
        this.setWine(wine);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }
}
