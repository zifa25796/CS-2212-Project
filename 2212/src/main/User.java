package main;

import crawler.AvailableCryptoList;
import crawler.DataFetcher;
import gui.LoginForm;
import gui.MainUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class User {

    private static User instance;
    public MainUI mainUI;
    private ArrayList<TradeResult> tradeLog;
    private ArrayList<TradeBroker> brokerList;

    public User() {
        this.tradeLog = new ArrayList<>();
        this.brokerList = new ArrayList<>();
    }

    public static void main(String[] args) throws InterruptedException {
        User instance = User.getInstance();
        instance.performLogin();
        instance.performMainUI();
    }

    private void performLogin() throws InterruptedException {
        LoginForm hWndLogin = new LoginForm();
        while (!hWndLogin.getLoginFlag()) {
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    private void performMainUI() {
        mainUI = new MainUI();
        mainUI.display();
    }

    public void addTradeLog(TradeResult result) {
        this.tradeLog.add(result);
    }

    public ArrayList<TradeResult> getTradeLog() { return this.tradeLog; }

    public void addBroker(TradeBroker broker) {
        this.brokerList.add(broker);
    }

    public ArrayList<TradeBroker> getBrokerList() { return this.brokerList; }

    public void setBrokerListNull() {
        brokerList = new ArrayList<TradeBroker>();
    }

    public void trade(ArrayList<String> coinList) {
        DataFetcher fetcher = new DataFetcher();
        HashMap<String, Double> coinPriceList = fetcher.getCoinPriceList(coinList);
        System.out.println(coinPriceList.toString());

        for (TradeBroker broker: brokerList) {
            HashMap<String, Double> currPriceList = new HashMap<>();
            for (String coin: broker.getCoinList()) {
                currPriceList.put(coin, coinPriceList.get(coin));
            }
            tradeLog.add(broker.trade(currPriceList));
        }

    }

    public static User getInstance() {
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

}
