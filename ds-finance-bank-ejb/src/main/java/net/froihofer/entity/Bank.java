package net.froihofer.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private BigDecimal investableVolume;

    public BigDecimal getInvestableVolume() {
        return investableVolume;
    }

    public void setInvestableVolume(BigDecimal investableVolume) {
        this.investableVolume = investableVolume;
    }
}
