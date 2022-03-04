import java.util.ArrayList;

public class User {
    public static void main(String[] args) {

    }

    private ArrayList<TradeResult> tradeLog;
    private ArrayList<TradeBroker> brokerList;
    private ArrayList<String> coinList;

    public User() {
        this.tradeLog = new ArrayList<>();
        this.brokerList = new ArrayList<>();
        this.coinList = new ArrayList<>();
    }

    public void addTradeLog(TradeResult result) {
        this.tradeLog.add(result);
    }

    public void addBrokerList(TradeBroker broker) {
        this.brokerList.add(broker);
    }

    public void setCoinList() {

    }
}
