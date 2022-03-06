package strategy;

import main.TradeResult;
import main.User;

import java.util.HashMap;

import static crawler.DataFetcher.getDate;

public class StrategyB implements StrategyADT{

    private static StrategyB instance;
    private static int counter;
    private static final String[] coinNecessary = {"BTC", "ADA"};

    public static StrategyB getInstance() {
        if (instance == null)
            instance = new StrategyB();

        return instance;
    }

    private StrategyB() {
        counter = 0;
    }

    public static void addCounter() {
        counter++;
    }

    // 实际上只要传入strategy关系的coin就足够输出结果，剩下来的不影响计算，所以并不遍历所有的了
    @Override
    public TradeResult performRule(String traderName, HashMap coinPriceList) {
        if (checkNecessary(coinPriceList)) {
            if (compute(coinPriceList)) {
                addCounter();
                return new TradeResult(traderName, "Strategy-B", "ETH", "Buy", 60, 12.34, getDate("dd-MMM-YYYY"));
            }
        } else {
            User.getInstance().mainUI.showMsg("Trading strategy can not be applied");
            return new TradeResult(traderName, "Strategy-B", "", "Fail", -1, -1, getDate("dd-MMM-YYYY"));
        }
        return null;
    }

    private boolean checkNecessary(HashMap coinPriceList) {
        for (String key: coinNecessary) {
            if (!coinPriceList.containsKey(key)) {
                return false;
            }
        }
        return true;
    }

    private boolean compute(HashMap coinPriceList) {
        for (String key: coinNecessary) {
            switch (key) {
                case "BTC":
                    if ((double)coinPriceList.get(key) >= 55000) {
                        return false;
                    }
                    break;
                case "ADA":
                    if ((double)coinPriceList.get(key) <= 2) {
                        return false;
                    }
                    break;
                default:
                    break;
            }
        }
        return true;
    }

    @Override
    public int getCounter() { return counter; }

    @Override
    public String getName() {
        return "Strategy-B";
    }
}
