package main;

import strategy.StrategyADT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>This class represents the trade broker created by the user</p>
 */
public class TradeBroker {
    /**
     * A variable containing the Broker's name
     */
    private String name;
    /**
     * A variable containing the list of coin this Broker is interested
     */
    private ArrayList<String> coinList;
    /**
     * A variable containing the strategy this Broker is using
     */
    private StrategyADT strategy;
    /**
     * A variable containing the row number of this broker in the table
     */
    private int rowNum;

    /**
     * {@link TradeBroker} class initializer
     * @param name Broker name
     * @param coinList Interested coin list
     * @param strategy Strategy Used
     */
    public TradeBroker(String name, String[] coinList, StrategyADT strategy, int rowNum) {
        this.name = name;
        this.coinList = new ArrayList<>();
        for (String coin: coinList) {
            this.coinList.add(coin);
        }
        this.strategy = strategy;
        this.rowNum = rowNum;
    }

    /**
     * Performs the strategy
     * @param coinPriceList coin price list
     * @return The trading result
     */
    public TradeResult trade(HashMap coinPriceList) {
        return this.strategy.performRule(this.name, coinPriceList);
    }

    /**
     * Setter method for name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for name
     * @return
     */
    public String getName() { return this.name; }

    /**
     * Adder method for coin list
     * @param coin
     */
    public void addCoinList(String coin) {
        this.coinList.add(coin);
    }

    /**
     * Setter method for strategy
     * @param strategy
     */
    public void setStrategy(StrategyADT strategy) {
        this.strategy = strategy;
    }

    /**
     * Getter method for strategy
     * @return
     */
    public StrategyADT getStrategy() { return this.strategy; }

    /**
     * Setter method for coin list
     * @param coinList
     */
    public void setCoinList(String coinList) {
        for (String coin: coinList.toString().split(",")) {
            this.coinList.add(coin);
        }
    }

    /**
     * Getter method for coin list
     * @return
     */
    public ArrayList<String> getCoinList() {
        return coinList;
    }

    /**
     * Getter method for rowNum
     * @return
     */
    public int getRowNum() {
        return rowNum;
    }

    /**
     * Setter methof for rowNum
     * @param rowNum
     */
    public void setRowNum(int rowNum) { this.rowNum = rowNum; }
}
