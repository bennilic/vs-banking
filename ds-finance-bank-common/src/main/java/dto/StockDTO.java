package dto;
import java.io.Serializable;
import java.math.BigDecimal;

public class StockDTO implements Serializable {
    private String symbol;

    private BigDecimal purchasePrice;

    private int quantity;

    public StockDTO () {}

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
