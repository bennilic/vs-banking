package net.froihofer.dsfinance.bank.client;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import api.BankService;
import api.CustomerService;
import api.PortfolioService;
import api.TradingService;
import dto.CustomerDTO;
import dto.StockDTO;
import net.froihofer.util.AuthCallbackHandler;
import net.froihofer.util.WildflyJndiLookupHelper;
import org.apache.commons.lang3.NotImplementedException;
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

    private PortfolioService portfolioService;

    private BankService bankService;

    /**
     * Skeleton method for performing an RMI lookup
     */
    private void getRmiProxy() {
        //AuthCallbackHandler.setUsername("customer");
        //AuthCallbackHandler.setPassword("customerpass");

        AuthCallbackHandler.setUsername("employee");
        AuthCallbackHandler.setPassword("employeepass");

        Properties props = new Properties();
        props.put(Context.SECURITY_PRINCIPAL, AuthCallbackHandler.getUsername());
        props.put(Context.SECURITY_CREDENTIALS, AuthCallbackHandler.getPassword());
        try {
            WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
            tradingService = jndiHelper.lookup("TradingService", TradingService.class);
            customerService = jndiHelper.lookup("CustomerService", CustomerService.class);
            portfolioService = jndiHelper.lookup("PortfolioService", PortfolioService.class);
            bankService = jndiHelper.lookup("BankService", BankService.class);
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

        boolean isEmployee = customerService.isEmployee();

        if (isEmployee) {
            showEmployeeMenu();
        } else {
            showCustomerMenu();
        }
    }

    private void showCustomerMenu() {
        System.out.println("You are logged in as Customer.");

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("--- Choose one of the following options. ---");
            System.out.println("[0] Close the application.");
            System.out.println("[1] Search for stocks.");
            System.out.println("[2] Buy stocks."); // TODO: Need implementation
            System.out.println("[3] Sell stocks."); // TODO: Need implementation
            System.out.println("[4] List Portfolio."); // TODO: Need implementation

            String input = sc.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Closing the application.");
                    return;
                case "1":
                    System.out.print("Enter a stock name:");
                    String stockName = sc.nextLine();

                    System.out.println("Searching for a stock with the name: " + stockName);
                    search4Stocks(stockName);
                    break;
                case "2":
                    throw new NotImplementedException();
                case "3":
                    throw new NotImplementedException();
                case "4":
                    throw new NotImplementedException();
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void showEmployeeMenu() {
        System.out.println("You are logged in as Employee.");

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("--- Choose one of the following options. ---");
            System.out.println("[0] Close the application.");
            System.out.println("[1] Search for stocks.");
            System.out.println("[2] Create a new customer.");
            System.out.println("[3] Show investable volume of the bank.");
            System.out.println("[4] Buy stocks for Customer."); // TODO: Need implementation
            System.out.println("[5] Sell stocks for Customer."); // TODO: Need implementation
            System.out.println("[6] Search for Customer."); // TODO: Need implementation
            System.out.println("[7] List Customer Portfolio."); // TODO: Need implementation

            String input = sc.nextLine();

            switch (input) {
                case "0":
                    System.out.println("Closing the application.");
                    return;
                case "1":
                    System.out.print("Enter a stock name:");
                    String stockName = sc.nextLine();

                    System.out.println("Searching for a stock with the name: " + stockName);
                    search4Stocks(stockName);
                    break;
                case "2":
                    System.out.println("Create a new customer.");

                    System.out.print("First Name: ");
                    String firstName = sc.nextLine();

                    System.out.print("Last Name: ");
                    String lastName = sc.nextLine();

                    System.out.print("Username: ");
                    String username = sc.nextLine();

                    System.out.print("Password: ");
                    String password = sc.nextLine();

                    System.out.print("Address: ");
                    String address = sc.nextLine();

                    CustomerDTO newCustomer = new CustomerDTO();
                    newCustomer.setFirstName(firstName);
                    newCustomer.setLastName(lastName);
                    newCustomer.setUserName(username);
                    newCustomer.setPassword(password);
                    newCustomer.setAddress(address);

                    customerService.createCustomer(newCustomer);
                    System.out.println("Customer created successfully.");
                    break;
                case "3":
                    long investibleVolume = bankService.getInvestableVolume();

                    System.out.println("The investable volume of the bank is: " + investibleVolume);
                    break;
                case "4":
                    throw new NotImplementedException();
                case "5":
                    throw new NotImplementedException();
                case "6":
                    throw new NotImplementedException();
                case "7":
                    throw new NotImplementedException();
                default:
                    System.out.println("Invalid option. Please try again.");
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
