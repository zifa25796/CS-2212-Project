package main;

import strategy.StrategyADT;

import java.util.ArrayList;
import java.util.HashMap;

public class TradeBroker {
    private String name;
    private ArrayList<String> coinList;
    private StrategyADT strategy;

    public TradeBroker() {
        this.name = "";
        this.coinList = new ArrayList<>();
        this.strategy = null;
    }

    public TradeBroker(String name, String[] coinList, StrategyADT strategy) {
        this.name = name;
        this.coinList = new ArrayList<>();
        for (String coin: coinList) {
            this.coinList.add(coin);
        }
        this.strategy = strategy;
    }

    public TradeResult trade(HashMap coinPriceList) {
        return this.strategy.performRule(this.name, coinPriceList);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    public void addCoinList(String coin) {
        this.coinList.add(coin);
    }

    public void setStrategy(StrategyADT strategy) {
        this.strategy = strategy;
    }

    public StrategyADT getStrategy() { return this.strategy; }

    public ArrayList<String> getCoinList() {
        return coinList;
    }
}
