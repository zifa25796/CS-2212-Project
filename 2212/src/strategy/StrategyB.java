package strategy;

import main.TradeResult;
import main.User;

import java.util.HashMap;

import static crawler.DataFetcher.getDate;

/**
 * This class represents a trading strategy
 */
public class StrategyB implements StrategyADT{

    /**
     * A static variable representing the one and only StrategyB instance
     */
    private static StrategyB instance;
    /**
     * <p>A static variable representing the counter</p>
     * <p>Each time a trade is performed using this strategy it adds one</p>
     */
    private static int counter;
    /**
     * A const variable representing the coin this Strategy will use
     */
    private static final String[] coinNecessary = {"BTC", "ADA"};

    /**
     * Getter method for StrategyB's instance
     * @return instance
     */
    public static StrategyB getInstance() {
        // If instance was not created, create one
        if (instance == null)
            instance = new StrategyB();

        return instance;
    }

    /**
     * {@link StrategyB} class initializer
     */
    private StrategyB() {
        counter = 0;
    }

    /**
     * Adder method for counter
     */
    public static void addCounter() {
        counter++;
    }

    @Override
    public TradeResult performRule(String traderName, HashMap coinPriceList) {
        // First check if all the necessary coin price was in the param coinPriceList to be able to apply strategy
        if (checkNecessary(coinPriceList)) {
            // If yes, compute the result of the strategy
            if (compute(coinPriceList)) {
                // Add counter, since trade success
                addCounter();
                // Return a success trade result
                return new TradeResult(traderName, "Strategy-A", "ETH", "Buy", 60, 12.34, getDate("dd-MMM-YYYY"));
            }
        } else {
            // If no, show an error message, and return a Failed trade result
            User.getInstance().mainUI.showMsg("Trading strategy can not be applied");
            return new TradeResult(traderName, "Strategy-A", "", "Fail", -1, -1, getDate("dd-MMM-YYYY"));
        }
        return null;
    }

    /**
     * Checks if the param coin list matches up with the required coin list
     * @param coinPriceList
     * @return <p>True: All required coin are in the param</p><p>False: Not all required coin are in the param</p>
     */
    private boolean checkNecessary(HashMap coinPriceList) {
//        for (String key: coinNecessary) {
//            if (!coinPriceList.containsKey(key)) {
//                return false;
//            }
//        }
        return true;
    }

    /**
     * Calculate using the given price list, to see weather a trade can be performed
     * @param coinPriceList coin price required
     * @return <p>True: a trade can be done</p><p>False: a trade cannot be done</p>
     */
    private boolean compute(HashMap coinPriceList) {
//        for (String key: coinNecessary) {
//            switch (key) {
//                case "BTC":
//                    if ((double)coinPriceList.get(key) >= 55000) {
//                        return false;
//                    }
//                    break;
//                case "ADA":
//                    if ((double)coinPriceList.get(key) <= 2) {
//                        return false;
//                    }
//                    break;
//                default:
//                    break;
//            }
//        }
        return true;
    }

    @Override
    public int getCounter() { return counter; }

    @Override
    public String getName() {
        return "Strategy-B";
    }
}
