package main;

public class TradeResult {

    public String name, strategy,cryptoCoin, action, date;
    public int quantity;
    public Double price;


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
