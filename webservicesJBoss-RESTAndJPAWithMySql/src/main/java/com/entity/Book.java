package com.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * Created with IntelliJ IDEA.
 * User: multi
 * Date: 12/05/15
 * Time: 09:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "Book")
public class Book implements Serializable {

    /*
    CREATE TABLE Book (
         name varchar(100) null,
         isbn VARCHAR(20) null,
         price int null,
         publishedDate date null
       );
     */

    @Id
    @Column(name = "name")
    String name;

    @Column(name = "isbn")
    String isbn;

    @Column(name = "price")
    int price;

    @Column(name = "publishedDate")
    Date publishedDate;

    public Book()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }
}
