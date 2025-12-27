package net.froihofer.dsfinance.bank.client;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import api.TradingService;
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

  /**
   * Skeleton method for performing an RMI lookup
   */
  private void getRmiProxy() {

    //AuthCallbackHandler.setUsername("csdc26bb_03");

    AuthCallbackHandler.setUsername("customer");
    //AuthCallbackHandler.setUsername("csdc26bb_03");
    AuthCallbackHandler.setPassword("customerpass");
    //AuthCallbackHandler.setPassword("Voak5eesei");
    Properties props = new Properties();
    props.put(Context.SECURITY_PRINCIPAL,AuthCallbackHandler.getUsername());
    props.put(Context.SECURITY_CREDENTIALS,AuthCallbackHandler.getPassword());
    try {
      WildflyJndiLookupHelper jndiHelper = new WildflyJndiLookupHelper(new InitialContext(props), "ds-finance-bank-ear", "ds-finance-bank-ejb", "");
        tradingService = jndiHelper.lookup("TradingService", TradingService.class);
      //TODO: Lookup the proxy and assign it to some variable or return it by changing the
      //      return type of this method
    }
    catch (NamingException e) {
      log.error("Failed to initialize InitialContext.",e);
    }
  }

  private void run() {
    //TODO implement the client part
      getRmiProxy();
      menu();
  }

  public static void main(String[] args) {
    BankClient client = new BankClient();
    client.run();
  }

  void search4Stocks(String stockName){
    List<StockDTO> stocks = tradingService.searchStocks(stockName);

    for (StockDTO stock : stocks) {
      System.out.println("Found stock: " + stock.getSymbol());
    }
  }

  public void menu() {
    Scanner sc = new Scanner(System.in);
    boolean running = true;

    while (running) {
      System.out.println("\n--- Menü ---");
      System.out.println("[1] - Suchen nach verfügbaren Aktien");
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


}
