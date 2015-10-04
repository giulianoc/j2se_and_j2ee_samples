package com.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 21/03/15
 * Time: 12:24
 * To change this template use File | Settings | File Templates.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "CustomerOrder.findAll", query = "select o from CustomerOrder o")
})
@Table(name = "CUSTOMER_ORDER")
public class CustomerOrder implements Serializable {

    @Temporal(TemporalType.DATE)
    @Column(name = "CREATION_DATE")
    private Date creationDate;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String status;

    @Version
    private Integer version;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer = null;

    @OneToMany(mappedBy = "customerOrder", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public CustomerOrder() {
    }

    public CustomerOrder(String status, Customer customer) {
        setCreationDate(new Date(System.currentTimeMillis()));
        setStatus(status);
        if (customer != null) {
            customer.addCustomerOrder(this);
        }
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public OrderItem addOrderItem(OrderItem orderItem)
    {
        getOrderItemList().add(orderItem);

        orderItem.setCustomerOrder(this);

        return orderItem;
    }

    public OrderItem removeOrderItem(OrderItem orderItem)
    {
        getOrderItemList().remove(orderItem);

        orderItem.setCustomerOrder(null);

        return orderItem;
    }
}
