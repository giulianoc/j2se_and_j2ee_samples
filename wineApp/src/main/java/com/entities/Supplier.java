package com.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Supplier.findAll", query = "select o from Supplier o")})
public class Supplier extends BusinessContact {

    @ManyToOne
    @JoinColumn(name = "PAYMENT_ADDRESS")
    private Address paymentAddress;

    @ManyToMany(mappedBy = "supplierList")
    protected List<Wine> wineList = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String firstName, String lastName, String phone, Address paymentAddress) {
        super(firstName, lastName, phone);
        setPaymentAddress(paymentAddress);
    }

    public Address getPaymentAddress() {
        return paymentAddress;
    }

    public void setPaymentAddress(Address paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public List<Wine> getWineList() {
        return wineList;
    }

    public void setWineList(List<Wine> wineList) {
        this.wineList = wineList;
    }
}
