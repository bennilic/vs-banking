package net.froihofer.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<StockHolding> stockHoldings;

    public List<StockHolding> getStockHoldings() {
        return stockHoldings;
    }

    public void setStockHoldings(List<StockHolding> stockHoldings) {
        this.stockHoldings = stockHoldings;
    }

    public void addStockHolding(StockHolding stockHolding) {
        this.stockHoldings.add(stockHolding);
    }
}
