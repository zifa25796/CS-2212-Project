package main;

import crawler.DataFetcher;
import gui.LoginForm;
import gui.MainUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a user of the system.
 * It controls the main thread.
 */
public class User {

    /**
     * A static variable representing the one and only User instance.
     */
    private static User instance;
    /**
     * A variable representing the MainUI created by User.
     */
    public MainUI mainUI;
    /**
     * A variable containing the list of trade log
     */
    private ArrayList<TradeResult> tradeLog;
    /**
     * A variable containing the list of broker
     */
    private ArrayList<TradeBroker> brokerList;

    /**
     * User class initializer
     */
    public User() {
        this.tradeLog = new ArrayList<>();
        this.brokerList = new ArrayList<>();
    }

    /**
     * Main thread of User class, and the Main entry of the program
     * @param args Default param
     * @throws InterruptedException Exception from User::performLogin
     */
    public static void main(String[] args) throws InterruptedException {
        // Get the static variable User instance
        User instance = User.getInstance();
        // Create Login process
        instance.performLogin();
        // Create MainUI process
        instance.performMainUI();
    }

    /**
     * Creates Login Window and waits for the user login verification
     * @throws InterruptedException Exception from TimeUnit.MILLISECONDS.sleep()
     * @see LoginForm
     */
    private void performLogin() throws InterruptedException {
        // Create LoginForm Object, the entire login operation will start in LoginForm initializer
        LoginForm hWndLogin = new LoginForm();
        // Monitor LoginFlag variable in LoginForm Object
        // Flag default is false, once the login validation passed, the flag will equal to true
        while (!hWndLogin.getLoginFlag()) {
            // Check flag every 0.5 of a second
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }

    /**
     * Creates {@link MainUI} Window
     */
    private void performMainUI() {
        // Set mainUI variable equal to a MainUI object, the entire MainUI operation will start in MainUI initializer
        mainUI = new MainUI();
        // Call MainUI's display function to display the window
        mainUI.display();
    }

    /**
     * Adder method for {@link ArrayList<TradeResult>} tradeLog
     * @param result {@link TradeResult} that need to be added
     * @deprecated This function was never Used
     */
    public void addTradeLog(TradeResult result) {
        this.tradeLog.add(result);
    }

    /**
     * Getter method for {@link ArrayList<TradeResult>} tradeLog
     * @return private variable tradeLog
     */
    public ArrayList<TradeResult> getTradeLog() { return this.tradeLog; }

    /**
     * Adder method for {@link ArrayList<TradeBroker>} brokerList
     * @param broker {@link TradeBroker} that need to be added
     */
    public void addBroker(TradeBroker broker) {
        this.brokerList.add(broker);
    }

    /**
     * Getter method for {@link ArrayList<TradeBroker>} brokerList
     * @return private variable brokerList
     */
    public ArrayList<TradeBroker> getBrokerList() { return this.brokerList; }

    /**
     * Setter method for {@link ArrayList<TradeBroker>} brokerList
     * It creates a new ArrayList to clear the old data
     */
    public void setBrokerListNull() {
        brokerList = new ArrayList<TradeBroker>();
    }

    /**
     * The trading process
     * @param coinList a list of coin that need to fetch their price
     * @see DataFetcher
     */
    public void trade(ArrayList<String> coinList) {
        // Create a DataFetcher Object
        DataFetcher fetcher = new DataFetcher();
        // Gets the price for all the coins in the list
        HashMap<String, Double> coinPriceList = fetcher.getCoinPriceList(coinList);

        // Iter through all the broker in broker list
        for (TradeBroker broker: brokerList) {
            // Create a temp HashMap to store the proper coin and price for current broker
            HashMap<String, Double> currPriceList = new HashMap<>();
            for (String coin: broker.getCoinList()) {
                currPriceList.put(coin, coinPriceList.get(coin));
            }
            // broker.trade() Starts the broker trading process
            // tradeLog.add() add the return value, TradeResult
            tradeLog.add(broker.trade(currPriceList));
        }

    }

    /**
     * Getter method for User's instance
     * @return instance
     */
    public static User getInstance() {
        // If instance was not created, create one
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

}
