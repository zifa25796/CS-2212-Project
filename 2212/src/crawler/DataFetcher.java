package crawler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Scanner;

public class DataFetcher {

    public HashMap getCoinPriceList(ArrayList<String> coinList) {
        HashMap<String, Double> map = new HashMap<>();
        for (String coin: coinList) {
            if (!map.containsKey(coin)) {
                map.put(coin, getPriceForCoin(coin, getDate("dd-MM-YYYY")));
            }
        }
        return map;
    }

    private JsonObject getDataForCrypto(String id, String date) {
        String strUrl = String.format("https://api.coingecko.com/api/v3/coins/%s/history?date=%s", id, date);

        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if (responsecode == 200) {
                String inLine = "";
                Scanner sc = new Scanner(url.openStream());
                while (sc.hasNext()) {
                    inLine += sc.nextLine();
                }
                sc.close();
                return new JsonParser().parse(inLine).getAsJsonObject();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getPriceForCoin(String id,String date) {
        double price = 0.0;

        JsonObject jsonObject = getDataForCrypto(id, date);
        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("current_price").getAsJsonObject();
            price = currentPrice.get("cad").getAsDouble();
        }

        return price;
    }

    public double getMarketCapForCoin(String id, String date) {
        double marketCap = 0.0;

        JsonObject jsonObject = getDataForCrypto(id, date);
        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("market_cap").getAsJsonObject();
            marketCap = currentPrice.get("cad").getAsDouble();
        }

        return marketCap;
    }

    public double getVolumeForCoin(String id, String date) {
        double volume = 0.0;

        JsonObject jsonObject = getDataForCrypto(id, date);
        if (jsonObject != null) {
            JsonObject marketData = jsonObject.get("market_data").getAsJsonObject();
            JsonObject currentPrice = marketData.get("total_volume").getAsJsonObject();
            volume = currentPrice.get("cad").getAsDouble();
        }

        return volume;
    }

    public static String getDate(String pattern) {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }
}



