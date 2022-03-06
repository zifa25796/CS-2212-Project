package main;

/**
 * This class represents the trading results performed by broker
 */
public class TradeResult {

    /**
     * A variable containing the name of the broker that performed this trade
     */
    public String name;
    /**
     * A variable containing the strategy used by this broker
     */
    public String strategy;
    /**
     * A variable containing the crypto coin this trade used
     */
    public String cryptoCoin;
    /**
     * A variable containing the action(Buy/Sell/Failed) of this trade
     */
    public String action;
    /**
     * A variable containing the current date in the format of dd-MM-YYYY
     * @see java.text.SimpleDateFormat
     */
    public String date;
    /**
     * A variable containing the quantity of coin in this trade
     */
    public int quantity;
    /**
     * A variable containing the price of the interested coin
     */
    public Double price;


    /**
     * {@link TradeResult} class initializer
     * @param name
     * @param strategy
     * @param cryptoCoin
     * @param action
     * @param quantity
     * @param price
     * @param date
     */
    public TradeResult(String name, String strategy, String cryptoCoin, String action, int quantity, double price, String date) {
        this.name = name;
        this.strategy = strategy;
        this.cryptoCoin = cryptoCoin;
        this.action = action;
        this.quantity = quantity;
        this.price = price;
        this.date = date;
    }
}
