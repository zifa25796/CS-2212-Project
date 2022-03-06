package strategy;

import main.TradeResult;

import java.util.HashMap;

/**
 * Interface for StrategyA/B/C/D
 */
public interface StrategyADT {
    /**
     * Use the corresponding Strategy to calculate the {@link TradeResult}
     * @param traderName {@link main.TradeBroker} name
     * @param coinPriceList {@link HashMap} (coin name, coin price)
     * @return
     */
    public TradeResult performRule(String traderName, HashMap coinPriceList);

    /**
     * Getter method for static variable counter for each Strategy
     * @return
     */
    public int getCounter();

    /**
     * Getter method for the Strategy Name
     * @return
     */
    public String getName();
}
