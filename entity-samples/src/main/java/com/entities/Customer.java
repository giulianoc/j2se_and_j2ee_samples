package com.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 21/03/15
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Customer.findAll", query = "select o from Customer o"),
        @NamedQuery(name = "Customer.findByEmail", query = "select o from Customer o where o.email = :email")
})
@Table(name = "CUSTOMER")
public class Customer implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigDecimal id;

    @Version
    private int version;

    @Column(length = 4000)
    private String email;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> customerOrders = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BILLING_ADDRESS")
    private Address billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIPPING_ADDRESS")
    private Address shippingAddress;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CustomerOrder> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder)
    {
        customerOrders.add(customerOrder);

        customerOrder.setCustomer(this);

        return customerOrder;
    }

    public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder)
    {
        customerOrders.remove(customerOrder);

        customerOrder.setCustomer(null);

        return customerOrder;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
