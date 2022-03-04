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
    }

    public TradeResult trade(HashMap coinPriceList) {
        return this.strategy.performRule(this.name, this.coinList, coinPriceList);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCoinList(String coin) {
        this.coinList.add(coin);
    }

    public void setStrategy(StrategyADT strategy) {
        this.strategy = strategy;
    }
}
