package strategy;

import main.TradeResult;

import java.util.ArrayList;
import java.util.HashMap;

public interface StrategyADT {
    public TradeResult performRule(String traderName, HashMap coinPriceList);
    public int getCounter();
    public String getName();
}
