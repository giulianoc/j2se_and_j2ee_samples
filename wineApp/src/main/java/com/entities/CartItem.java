package com.entities;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CartItem.findAll", query = "select o from CartItem o")})
public class CartItem extends WineItem {

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATED_DATED")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    public CartItem() {
    }

    public CartItem(int quantity, Wine wine) {
        super(quantity, wine);
        setCreatedDate(new Date(System.currentTimeMillis()));
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
