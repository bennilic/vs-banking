package net.froihofer.service;

import api.BankService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Remote;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import net.froihofer.dao.BankDao;
import net.froihofer.entity.Bank;

@Remote
@Stateless(name = "BankService")
public class BankServiceImpl implements BankService {

    @Inject
    private BankDao bankDAO;

    @Override
    @RolesAllowed("employee")
    public Long getInvestableVolume() {
        Bank bank = bankDAO.findById(1);
        return bank.getInvestableVolume();
    }
}
