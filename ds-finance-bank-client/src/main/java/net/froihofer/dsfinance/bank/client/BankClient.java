package net.froihofer.dsfinance.bank.client;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import api.CustomerService;
import api.TradingService;
import dto.CustomerDTO;
import dto.StockDTO;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for starting the bank client.
 *
 */
public class BankClient {
    private static Logger log = LoggerFactory.getLogger(BankClient.class);

    private TradingService tradingService;

    private CustomerService customerService;

    /**
     * Skeleton method for performing an RMI lookup
     */
    private void getRmiProxy() {
        // AuthCallbackHandler.setUsername("customer");
        // AuthCallbackHandler.setPassword("customerpass");

        AuthCallbackHandler.setUsername("employee");
        AuthCallbackHandler.setPassword("employeepass");

        Properties props = new Properties();
        props.put(Context.SECURITY_PRINCIPAL, AuthCallbackHandler.getUsername());
        props.put(Context.SECURITY_CREDENTIALS, AuthCallbackHandler.getPassword());
        try {
            WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
            tradingService = jndiHelper.lookup("TradingService", TradingService.class);
            customerService = jndiHelper.lookup("CustomerService", CustomerService.class);
            //TODO: Lookup the proxy and assign it to some variable or return it by changing the
            //      return type of this method
        } catch (NamingException e) {
            log.error("Failed to initialize InitialContext.", e);
        }
    }

    public static void main(String[] args) {
        BankClient client = new BankClient();
        client.run();
    }

    private void run() {
        getRmiProxy();
        menu();
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- Menü ---");
            System.out.println("[1] - Suchen nach verfügbaren Aktien");
            System.out.println("[2] - Erstelle einen Customer (Nur für Employees)");
            System.out.println("[0] - Beenden");
            System.out.print("Option auswählen: ");

            String eingabe = sc.nextLine(); // Nutze String für mehr Stabilität

            switch (eingabe) {
                case "1":
                    System.out.print("Geben Sie den Namen der Aktie ein: ");
                    String aktienName = sc.nextLine(); // Nutze den Scanner 'sc'
                    System.out.println("Suche nach Aktien mit dem Namen: " + aktienName);
                    search4Stocks(aktienName);
                    break;
                case "2":
                    System.out.println("Vorname:");
                    String firstName = sc.nextLine();

                    System.out.println("Nachname:");
                    String lastName = sc.nextLine();

                    System.out.println("Benutzername:");
                    String username = sc.nextLine();

                    System.out.println("Passwort:");
                    String password = sc.nextLine();

                    System.out.println("Adresse:");
                    String address = sc.nextLine();

                    CustomerDTO newCustomer = new CustomerDTO();
                    newCustomer.setFirstName(firstName);
                    newCustomer.setLastName(lastName);
                    newCustomer.setUserName(username);
                    newCustomer.setPassword(password);
                    newCustomer.setAddress(address);

                    customerService.createCustomer(newCustomer);

                    break;
                case "0":
                    System.out.println("Programm beendet.");
                    running = false;
                    break;
                default:
                    System.out.println("Ungültige Option!");
                    break;
            }
        }
    }

    void search4Stocks(String stockName) {
        List<StockDTO> stocks = tradingService.searchStocks(stockName);

        for (StockDTO stock : stocks) {
            System.out.println("Found stock: " + stock.getSymbol());
        }
    }
}
