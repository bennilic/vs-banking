package net.froihofer.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Customer customer;

    @OneToMany
    private List<StockHolding> stockHoldings;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<StockHolding> getStockHoldings() {
        return stockHoldings;
    }

    public void setStockHoldings(List<StockHolding> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }
}
