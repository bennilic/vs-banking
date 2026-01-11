package net.froihofer.entity;

import jakarta.persistence.*;

@Entity
@Table
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long investableVolume;

    public Long getInvestableVolume() {
        return investableVolume;
    }

    public void setInvestableVolume(Long investableVolume) {
        this.investableVolume = investableVolume;
    }
}
