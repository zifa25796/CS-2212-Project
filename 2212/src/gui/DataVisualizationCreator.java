package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import main.TradeBroker;
import main.TradeResult;
import main.User;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This class supports the MainUI to display the tables
 */
public class DataVisualizationCreator {

    /**
     * Create the tables
     */
    public void createCharts() {
//		createTextualOutput();
        createTableOutput();
//		createTimeSeries();
//		createScatter();
        createBar();
    }

    private void createTextualOutput() {
//		DefaultTableModel dtm = new  DefaultTableModel(new Object[] {"Broker Name", "Ticker List", "Strategy Name"}, 1);
//		JTable table = new JTable(dtm);
//		//table.setPreferredSize(new Dimension(600, 300));
//		dtm.e
//		JScrollPane scrollPane = new JScrollPane(table);
//		scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
//                "Broker Actions",
//                TitledBorder.CENTER,
//                TitledBorder.TOP));
//
//
//
//		scrollPane.setPreferredSize(new Dimension(800, 300));
//		table.setFillsViewportHeight(true);;
//
//		MainUI.getInstance().updateStats(scrollPane);
    }

    /**
     * Create the chart for {@link TradeResult}
     */
    private void createTableOutput() {
        // Column Name
        Object[] columnNames = {"Trader","Strategy","CryptoCoin","Action","Quantity","Price","Date"};

        // Row Data
        Object[][] data = new Object[User.getInstance().getTradeLog().size()][7];
        int resultCounter = 0;
        for (TradeResult result: User.getInstance().getTradeLog()) {
            data[resultCounter][0] = result.name;
            data[resultCounter][1] = result.strategy;
            data[resultCounter][2] = (result.cryptoCoin == "") ? "Null" : result.cryptoCoin;
            data[resultCounter][3] = result.action;
            data[resultCounter][4] = (result.quantity == -1) ? "Null" : result.quantity;
            data[resultCounter][5] = (result.price == -1) ? "Null" : result.price;
            data[resultCounter][6] = result.date;
            resultCounter++;
        }

        // Create JTable Object, and set properties
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder (BorderFactory.createTitledBorder (BorderFactory.createEtchedBorder (),
                "Trader Actions",
                TitledBorder.CENTER,
                TitledBorder.TOP));

        scrollPane.setPreferredSize(new Dimension(User.getInstance().mainUI.WindowWidth / 5 * 2, User.getInstance().mainUI.WindowHeight / 2));
        scrollPane.setLocation(0, 0);
        table.setFillsViewportHeight(true);

        // Added to main frame
        User.getInstance().mainUI.setChartPanel(scrollPane);
    }

    private void createTimeSeries() {
        TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
        series1.add(new Day(13, 9, 2021), 50368.67);
        series1.add(new Day(14, 9, 2021), 51552.05);
        series1.add(new Day(15, 9, 2021), 47228.30);
        series1.add(new Day(16, 9, 2021), 45263.90);
        series1.add(new Day(17, 9, 2021), 46955.41);

        TimeSeries series2 = new TimeSeries("Ethereum - Daily");
        series2.add(new Day(13, 9, 2021), 3912.28);
        series2.add(new Day(14, 9, 2021), 3927.27);
        series2.add(new Day(15, 9, 2021), 3460.48);
        series2.add(new Day(16, 9, 2021), 3486.09);
        series2.add(new Day(17, 9, 2021), 3550.29);

        TimeSeries series3 = new TimeSeries("Cardano - Daily");
        series3.add(new Day(13, 9, 2021), 2.87);
        series3.add(new Day(14, 9, 2021), 2.84);
        series3.add(new Day(15, 9, 2021), 2.41);
        series3.add(new Day(16, 9, 2021), 2.43);
        series3.add(new Day(17, 9, 2021), 2.59);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYSplineRenderer splinerenderer1 = new XYSplineRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, splinerenderer1);
        DateAxis domainAxis = new DateAxis("");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new LogAxis("Price(USD)"));

        //plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        //plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis
        //plot.mapDatasetToRangeAxis(2, 2);// 3rd dataset to 3rd y-axis

        JFreeChart chart = new JFreeChart("Daily Price Line Chart", new Font("Serif", java.awt.Font.BOLD, 18), plot,
                true);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);

        User.getInstance().mainUI.updateStats(chartPanel);
    }

    private void createScatter() {
        TimeSeries series1 = new TimeSeries("Bitcoin - Daily");
        series1.add(new Day(13, 9, 2021), 50368.67);
        series1.add(new Day(14, 9, 2021), 51552.05);
        series1.add(new Day(15, 9, 2021), 47228.30);
        series1.add(new Day(16, 9, 2021), 45263.90);
        series1.add(new Day(17, 9, 2021), 46955.41);

        TimeSeries series2 = new TimeSeries("Ethereum - Daily");
        series2.add(new Day(13, 9, 2021), 3912.28);
        series2.add(new Day(14, 9, 2021), 3927.27);
        series2.add(new Day(15, 9, 2021), 3460.48);
        series2.add(new Day(16, 9, 2021), 3486.09);
        series2.add(new Day(17, 9, 2021), 3550.29);

        TimeSeries series3 = new TimeSeries("Cardano - Daily");
        series3.add(new Day(13, 9, 2021), 2.87);
        series3.add(new Day(14, 9, 2021), 2.84);
        series3.add(new Day(15, 9, 2021), 2.41);
        series3.add(new Day(16, 9, 2021), 2.43);
        series3.add(new Day(17, 9, 2021), 2.59);

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series1);
        dataset.addSeries(series2);
        dataset.addSeries(series3);

        XYPlot plot = new XYPlot();
        XYItemRenderer itemrenderer1 = new XYLineAndShapeRenderer(false, true);

        plot.setDataset(0, dataset);
        plot.setRenderer(0, itemrenderer1);
        DateAxis domainAxis = new DateAxis("");
        plot.setDomainAxis(domainAxis);
        plot.setRangeAxis(new LogAxis("Price(USD)"));

        //plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        //plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart scatterChart = new JFreeChart("Daily Price Scatter Chart",
                new Font("Serif", java.awt.Font.BOLD, 18), plot, true);

        ChartPanel chartPanel = new ChartPanel(scatterChart);
        chartPanel.setPreferredSize(new Dimension(600, 300));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);
        User.getInstance().mainUI.updateStats(chartPanel);
    }

    /**
     * Create the bar graph for {@link strategy}
     */
    private void createBar() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // For each log
        for (TradeResult result: User.getInstance().getTradeLog()) {
            // If the double hashmap does not contain current broker
            if (!User.BarChartData.containsKey(result.name)) {
                // Create a new one
                User.BarChartData.put(result.name, new HashMap<>());
            }

            HashMap<String, Integer> temp = User.BarChartData.get(result.name);
            // If the hashmap does contain current broker's strategy
            if (temp.containsKey(result.strategy)) {
                // Add one to the value
                temp.put(result.strategy, temp.get(result.strategy) + 1);
            } else {
                // Create a new one
                temp.put(result.strategy, 1);
            }
        }

        // For each broker and each strategy
        for (String nameKey: User.BarChartData.keySet()) {
            for (String stratKey: User.BarChartData.get(nameKey).keySet()) {
                dataset.setValue((double)User.BarChartData.get(nameKey).get(stratKey), (Comparable)nameKey, (Comparable)stratKey);
            }
        }

        CategoryPlot plot = new CategoryPlot();
        BarRenderer barrenderer1 = new BarRenderer();

        plot.setDataset(0, dataset);
        plot.setRenderer(0, barrenderer1);
        CategoryAxis domainAxis = new CategoryAxis("Strategy");
        plot.setDomainAxis(domainAxis);
        LogAxis rangeAxis = new LogAxis("Actions(Buys or Sells)");
        rangeAxis.setRange(new Range(1, 10000));
        plot.setRangeAxis(rangeAxis);

        //plot.mapDatasetToRangeAxis(0, 0);// 1st dataset to 1st y-axis
        //plot.mapDatasetToRangeAxis(1, 1); // 2nd dataset to 2nd y-axis

        JFreeChart barChart = new JFreeChart("Actions Performed By Traders So Far", new Font("Serif", java.awt.Font.BOLD, 18), plot,
                true);

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new Dimension(User.getInstance().mainUI.WindowWidth / 5 * 2, User.getInstance().mainUI.WindowHeight / 2));
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.white);
        User.getInstance().mainUI.setTablePanel(chartPanel);
    }

}
