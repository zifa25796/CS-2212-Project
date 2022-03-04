package strategy;

import main.TradeResult;

import java.util.ArrayList;
import java.util.HashMap;

public interface StrategyADT {
    public TradeResult performRule(String traderName, ArrayList<String> coinList, HashMap coinPriceList);
}
