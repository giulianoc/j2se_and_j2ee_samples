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
public abstract class Customer extends BusinessContact {

    @Column(length = 4000)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL}, orphanRemoval = true)
    List<CartItem> cartItemList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<CustomerOrder> customerOrderList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    // JoinTable is needed because this is a unidirectional one-to-many association.
    // The unidirectional one-to-many association is needed because you have multiple types of 'Customer'.
    // In that case you cannot have the fk column in the child entity and must go with a join table
    @JoinTable(name = "CUSTOMER_BILLING_ADDRESS",
            joinColumns = @JoinColumn(name = "CUSTOMER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    protected List<Address> billingAddressList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "CUSTOMER_SHIPPING_ADDRESS",
            joinColumns = @JoinColumn(name = "CUSTOMER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ADDRESS_ID"))
    protected List<Address> shippingAddressList = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DEFAULT_SHIPPING_ADDRESS")
    protected Address defaultShippingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "DEFAULT_BILLING_ADDRESS")
    protected Address defaultBillingAddress;

    public Customer() {
    }

    public Customer(String firstName, String lastName, String phone, String email, Address defaultShippingAddress,
                    Address defaultBillingAddress) {
        super(firstName, lastName, phone);
        setEmail(email);
        setDefaultBillingAddress(defaultBillingAddress);
        setDefaultShippingAddress(defaultShippingAddress);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartItem> getCartItemList() {
        return cartItemList;
    }

    public void setCartItemList(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    public CartItem addCartItem(CartItem cartItem)
    {
        getCartItemList().add(cartItem);

        cartItem.setCustomer(this);

        return cartItem;
    }

    public CartItem removeCartItem(CartItem cartItem)
    {
        getCartItemList().remove(cartItem);

        cartItem.setCustomer(null);

        return cartItem;
    }

    public List<CustomerOrder> getCustomerOrderList() {
        return customerOrderList;
    }

    public void setCustomerOrderList(List<CustomerOrder> customerOrderList) {
        this.customerOrderList = customerOrderList;
    }

    public CustomerOrder addCustomerOrder(CustomerOrder customerOrder)
    {
        getCustomerOrderList().add(customerOrder);

        customerOrder.setCustomer(this);

        return customerOrder;
    }

    public CustomerOrder removeCustomerOrder(CustomerOrder customerOrder)
    {
        getCustomerOrderList().remove(customerOrder);

        customerOrder.setCustomer(null);

        return customerOrder;
    }

    public List<Address> getBillingAddressList() {
        return billingAddressList;
    }

    public void setBillingAddressList(List<Address> billingAddressList) {
        this.billingAddressList = billingAddressList;
    }

    public Address addBillingAddress(Address billingAddress)
    {
        getBillingAddressList().add(billingAddress);

        return billingAddress;
    }

    public Address removeBillingAddress(Address billingAddress)
    {
        getBillingAddressList().remove(billingAddress);

        return billingAddress;
    }

    public List<Address> getShippingAddressList() {
        return shippingAddressList;
    }

    public void setShippingAddressList(List<Address> shippingAddressList) {
        this.shippingAddressList = shippingAddressList;
    }

    public Address addShippingAddress(Address shippingAddress) {
        getShippingAddressList().add(shippingAddress);
        return shippingAddress;
    }

    public Address removeShippingAddress(Address shippingAddress) {
        getShippingAddressList().remove(shippingAddress);
        return shippingAddress;
    }

    public Address getDefaultShippingAddress() {
        return defaultShippingAddress;
    }

    public void setDefaultShippingAddress(Address defaultShippingAddress) {
        this.defaultShippingAddress = defaultShippingAddress;
    }

    public Address getDefaultBillingAddress() {
        return defaultBillingAddress;
    }

    public void setDefaultBillingAddress(Address defaultBillingAddress) {
        this.defaultBillingAddress = defaultBillingAddress;
    }
}
