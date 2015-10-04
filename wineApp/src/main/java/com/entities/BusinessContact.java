package com.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 22/03/15
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "BusinessContact.findAll", query = "select o from BusinessContact o")})
@Table(name = "BUSINESS_CONTACT")
public class BusinessContact implements Serializable {

    @Column(name = "FIRST_NAME", length = 4000)
    private String firstName;

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "LAST_NAME", length = 4000)
    private String lastName;

    @Column(length = 15)
    private String phone;

    @Version
    private Integer version;

    public BusinessContact() {
    }

    public BusinessContact(String firstName, String lastName, String phone) {
        setFirstName(firstName);
        setLastName(lastName);
        setPhone(phone);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
