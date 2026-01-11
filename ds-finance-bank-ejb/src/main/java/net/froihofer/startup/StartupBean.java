package net.froihofer.startup;


import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import net.froihofer.dao.BankDao;
import net.froihofer.entity.Bank;
import org.apache.commons.lang3.NotImplementedException;

@Singleton
@Startup
public class StartupBean {

    @Inject
    private BankDao bankDao;

    @PostConstruct
    private void init() {
        createBankIfNotExists();

        // TODO: Add a default employee at startup.
        // createEmployeeIfNotExists();
    }

    public void createBankIfNotExists() {
        Bank existingBank = bankDao.findById(1);

        if (existingBank == null) {
            Bank bank = new Bank();
            bank.setInvestableVolume(1_000_000_000L);

            bankDao.persist(bank);
        }
    }

    public void createEmployeeIfNotExists() {
        throw new NotImplementedException();
    }
}
