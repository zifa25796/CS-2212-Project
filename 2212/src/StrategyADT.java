import java.util.ArrayList;
import java.util.HashMap;

interface StrategyADT {
    public TradeResult performRule(String traderName, ArrayList<String> coinList, HashMap coinPriceList);
}
