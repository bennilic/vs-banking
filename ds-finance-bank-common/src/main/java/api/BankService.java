package api;

import jakarta.ejb.Remote;

import java.math.BigDecimal;

public interface BankService {
    BigDecimal getInvestableVolume();
}
