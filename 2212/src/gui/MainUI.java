package gui;

import crawler.DataFetcher;
import main.TradeBroker;
import main.User;
import org.jfree.chart.ChartPanel;
import strategy.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.List;

public class MainUI {

    private JFrame frame;
    private JPanel stats;
    private JPanel north, south, east, west, buttons;
    private DefaultTableModel dtm;
    private JTable table;
    private JScrollPane scrollPane, chartPanel;
    private ChartPanel tablePanel;

    public int WindowWidth = 900;
    public int WindowHeight = 600;
    private int ScrollWidth, ScrollHeight, WestWidth, WestHeight;

    public HashMap<String, StrategyADT> strategyHashMap;

    public MainUI() {
        initFrame();
        initComponents();
        initStrategyMap();
    }

    private void initStrategyMap() {
        strategyHashMap = new HashMap<>();
        strategyHashMap.put("Strategy-A", StrategyA.getInstance());
        strategyHashMap.put("Strategy-B", StrategyB.getInstance());
        strategyHashMap.put("Strategy-C", StrategyC.getInstance());
        strategyHashMap.put("Strategy-D", StrategyD.getInstance());
    }

    private void initFrame() {
        frame = new JFrame("Crypto Trading Tool");
        Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((ScreenDimension.width - WindowWidth) / 2, (ScreenDimension.height - WindowHeight) / 2);
        frame.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
//        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                WindowWidth = frame.getWidth();
                WindowHeight = frame.getHeight();
                ScrollWidth = WindowWidth / 5 * 3 - 10;
                ScrollHeight = WindowHeight / 2;
                WestWidth = WindowWidth / 5 * 2 - 10;
                WestHeight = WindowHeight;

                scrollPane.setPreferredSize(new Dimension(ScrollWidth, ScrollHeight));
                try {
                    chartPanel.setPreferredSize(new Dimension(WestWidth, WindowHeight / 2));
                    tablePanel.setPreferredSize(new Dimension(WestWidth, WindowHeight / 2));
                } catch(Exception exception) {
                }
                west.setPreferredSize(new Dimension(WestWidth, WestHeight));

                east.repaint();
                west.repaint();
                east.revalidate();
                west.revalidate();
            }
        });
    }

    private void initComponents() {
        north = new JPanel();
        south = new JPanel();
        east = new JPanel();
        west = new JPanel();
        buttons = new JPanel();
        stats = new JPanel();


        JButton trade = new JButton("Perform Trade");
        south.add(trade);

        trade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> coinList = checkDTM();
                if (coinList == null) {
                    return;
                }

                User.getInstance().trade(coinList);

                stats.removeAll();
                DataVisualizationCreator creator = new DataVisualizationCreator();
                creator.createCharts();
            }
        });

        dtm = new DefaultTableModel(new Object[] {"Trading Client", "Coin List", "Strategy Name"}, 1);
        table = new JTable(dtm);

        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Trading Client Actions",
                TitledBorder.CENTER, TitledBorder.TOP));


        Vector<String> strategyNames = new Vector<>();
        strategyNames.add("(None)");
        strategyNames.add("Strategy-A");
        strategyNames.add("Strategy-B");
        strategyNames.add("Strategy-C");
        strategyNames.add("Strategy-D");
        TableColumn strategyColumn = table.getColumnModel().getColumn(2);
        JComboBox strategyComboBox = new JComboBox(strategyNames);
        strategyColumn.setCellEditor(new DefaultCellEditor(strategyComboBox));



        JButton addRow = new JButton("Add Row");
        addRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dtm.addRow(new String[3]);
            }
        });

        JButton remRow = new JButton("Remove Row");
        remRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1)
                    dtm.removeRow(selectedRow);
            }
        });

        scrollPane.setPreferredSize(new Dimension(ScrollWidth, ScrollHeight));
        table.setFillsViewportHeight(true);

        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        east.add(scrollPane);

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.add(addRow);
        buttons.add(remRow);
        east.add(buttons);

        west.setPreferredSize(new Dimension(WestWidth, WestHeight));
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        stats.setLayout(new GridLayout(2, 1));
        west.add(stats);

        frame.getContentPane().add(north, BorderLayout.NORTH);
        frame.getContentPane().add(east, BorderLayout.EAST);
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.getContentPane().add(south, BorderLayout.SOUTH);
    }

    private ArrayList<String> checkDTM() {
        ArrayList<String> coinList = new ArrayList<>();
        User.getInstance().setBrokerListNull();
        for (int count = 0; count < dtm.getRowCount(); count++){
            Object traderObject = dtm.getValueAt(count, 0);
            if (traderObject == null) {
                JOptionPane.showMessageDialog(frame, "please fill in Trader name on line " + (count + 1) );
                return null;
            }
            String traderName = traderObject.toString();

            Object coinObject = dtm.getValueAt(count, 1);
            if (coinObject == null) {
                JOptionPane.showMessageDialog(frame, "please fill in cryptocoin list on line " + (count + 1) );
                return null;
            }
            String[] coinNames = coinObject.toString().split(",");
            coinList.addAll(List.of(coinNames));

            Object strategyObject = dtm.getValueAt(count, 2);
            if (strategyObject == null) {
                JOptionPane.showMessageDialog(frame, "please fill in strategy name on line " + (count + 1) );
                return null;
            }
            String strategyName = strategyObject.toString();
            System.out.println(traderName + " " + Arrays.toString(coinNames) + " " + strategyName);

            User.getInstance().addBroker(new TradeBroker(traderName, coinNames, strategyHashMap.get(strategyName)));
        }
        return coinList;
    }

    public void updateStats(JComponent component) {
        stats.add(component);
        stats.revalidate();
        stats.repaint();
        west.repaint();
    }

    public void setChartPanel(JScrollPane component) {
        this.chartPanel = component;
        updateStats(this.chartPanel);
    }

    public void setTablePanel(ChartPanel component) {
        this.tablePanel = component;
        updateStats(this.tablePanel);
    }

    public void display() {
        frame.pack();
        frame.setVisible(true);
    }

    public void showMsg(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }
}
