package net.froihofer.service;

import api.BankService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.BankDAO;
import net.froihofer.entity.Bank;

import java.math.BigDecimal;

@Remote
@Stateless(name = "BankService")
public class BankServiceImpl implements BankService {

    @Inject
    private BankDAO bankDAO;

    @Override
    @RolesAllowed("employee")
    public BigDecimal getInvestableVolume() {
        Bank bank = bankDAO.findById(1);
        return bank.getInvestableVolume();
    }
}
