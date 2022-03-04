package main;

import gui.LoginForm;
import gui.MainUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class User {

    private static User instance;
    private ArrayList<TradeResult> tradeLog;
    private ArrayList<TradeBroker> brokerList;
    private ArrayList<String> coinList;

    public User() {
        this.tradeLog = new ArrayList<>();
        this.brokerList = new ArrayList<>();
        this.coinList = new ArrayList<>();
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
        MainUI mainUI = new MainUI();
        mainUI.display();
    }

    public void addTradeLog(TradeResult result) {
        this.tradeLog.add(result);
    }

    public void addBroker(TradeBroker broker) {
        this.brokerList.add(broker);
    }

    public void setCoinList() {

    }

    public static User getInstance() {
        if (instance == null){
            instance = new User();
        }
        return instance;
    }

    public class MainUI_Listener extends JFrame implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
