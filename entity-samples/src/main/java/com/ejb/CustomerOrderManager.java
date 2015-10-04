package com.ejb;

import com.entities.Address;
import com.entities.Customer;
import com.entities.CustomerOrder;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 21/03/15
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class CustomerOrderManager {

    @PersistenceContext(name = "primary")
    private EntityManager entityManager;

    public <T> T persistEntity(T entity)
    {
        entityManager.persist(entity);

        return entity;
    }

    public <T> T mergeEntity(T entity)
    {
        return entityManager.merge(entity);
    }

    public void removeCustomer(Customer customer)
    {
        Customer localCustomer = entityManager.find(Customer.class, customer.getId());

        entityManager.remove(localCustomer);
    }

    public List<Customer> getCustomerFindAll()
    {
        return entityManager.createNamedQuery("Customer.findAll", Customer.class).getResultList();
    }

    public List<Customer> getCustomerFindByEmail(String email)
    {
        return entityManager.createNamedQuery("Customer.findByEmail", Customer.class).setParameter("email", email).getResultList();
    }

    public void removeAddress(Address address)
    {
        Address localAddress = entityManager.find(Address.class, address.getId());

        entityManager.remove(localAddress);
    }

    public List<Address> getAddressFindAll()
    {
        return entityManager.createNamedQuery("Address.findAll", Address.class).getResultList();
    }

    public void removeCustomerOrder(CustomerOrder customerOrder)
    {
        CustomerOrder localCustomerOrder = entityManager.find(CustomerOrder.class, customerOrder.getId());

        entityManager.remove(localCustomerOrder);
    }

    public List<CustomerOrder> getCustomerOrderFindAll()
    {
        return entityManager.createNamedQuery("CustomerOrder.findAll", CustomerOrder.class).getResultList();
    }
}
