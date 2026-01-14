package net.froihofer.entity;

import jakarta.persistence.*;

@Entity
public class Customer extends User{

    @Column
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    private Portfolio portfolio;

    public Customer(){}

    public Customer(String firstName, String lastName, String username, String password, String address){
        super(firstName, lastName, username, password);
        this.address = address;
        this.portfolio = new Portfolio();
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }
}
