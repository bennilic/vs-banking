package net.froihofer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class Customer extends User{

    @Column
    private String address;

    public Customer(){}

    public Customer(String firstName, String lastName, String username, String password, String address){
        super(firstName, lastName, username, password);
        this.address = address;
    }
}
