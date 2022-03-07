package gui;

import com.sun.tools.javac.Main;
import main.TradeBroker;
import main.User;
import org.jfree.chart.ChartPanel;
import strategy.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.*;
import java.util.List;

/**
 * <p>This class represents the main UI</p>
 */
public class MainUI {

    /**
     * A variable representing the main frame
     */
    private JFrame frame;
    /**
     * A variable representing the stats panel
     */
    private JPanel stats;
    /**
     * A variable representing the button panel
     */
    private JPanel buttons;
    /**
     * Four variables representing the four sides of main frame
     */
    private JPanel north, south, east, west;
    /**
     * A variable representing the default table model for {@link TradeBroker}s
     */
    private DefaultTableModel dtm;
    /**
     * A variable representing the table
     */
    private JTable table;
    /**
     * Two variables representing the two scroller in the main frame
     */
    private JScrollPane scrollPane, chartPanel;
    /**
     * A variable representing the table panel
     */
    private ChartPanel tablePanel;

    /**
     * A variable containing the window width
     */
    public int WindowWidth = 900;
    /**
     * A variable containing the window height
     */
    public int WindowHeight = 600;
    /**
     * Four variables containing the width and height for different {@link JComponent}
     */
    private int ScrollWidth, ScrollHeight, WestWidth, WestHeight;
    /**
     * A static variable containing the strategy name, and it's represented strategy class
     */
    public static HashMap<String, StrategyADT> strategyHashMap;

    /**
     * {@link MainUI} class initializer
     */
    public MainUI() {
        // Initialize main frame
        initFrame();
        // Initialize components
        initComponents();
        // Initialize strategy hash map
        initStrategyMap();
        // Create first empty broker
        User.getInstance().newBroker(0);
    }

    /**
     * strategyHashMap initializer
     */
    private void initStrategyMap() {
        strategyHashMap = new HashMap<>();
        strategyHashMap.put("Strategy-A", StrategyA.getInstance());
        strategyHashMap.put("Strategy-B", StrategyB.getInstance());
        strategyHashMap.put("Strategy-C", StrategyC.getInstance());
        strategyHashMap.put("Strategy-D", StrategyD.getInstance());
    }

    /**
     * main frame initializer
     */
    private void initFrame() {
        // Create a JFrame Object, with the window name Crypto Trading Tool
        frame = new JFrame("Crypto Trading Tool");
        // Get the screen size
        Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Set window location to the middle of the screen
        // Formula: (screen size - window - size) / 2
        frame.setLocation((ScreenDimension.width - WindowWidth) / 2, (ScreenDimension.height - WindowHeight) / 2);
        // Set window size to const window size
        frame.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        // Set window to un-resizeable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create ImageIcon Object, and set window icon to "icon\crypto.png"
        ImageIcon img = new ImageIcon("icon\\crypto.png");
        frame.setIconImage(img.getImage());

        // Add an even listener for window resize movement
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                // When ever window have resized
                // WindowWidth and WindowHeight is equal to the frame width and height
                WindowWidth = frame.getWidth();
                WindowHeight = frame.getHeight();
                // Right side columns takes 3/5 of the frame
                ScrollWidth = WindowWidth / 5 * 3 - 10;
                ScrollHeight = WindowHeight / 2;
                // Left size chars takes 2/5 of the frame
                WestWidth = WindowWidth / 5 * 2 - 10;
                WestHeight = WindowHeight;

                // Resize all the components
                scrollPane.setPreferredSize(new Dimension(ScrollWidth, ScrollHeight));
                try {
                    chartPanel.setPreferredSize(new Dimension(WestWidth, WindowHeight / 2));
                    tablePanel.setPreferredSize(new Dimension(WestWidth, WindowHeight / 2));
                } catch(Exception exception) {
                }
                west.setPreferredSize(new Dimension(WestWidth, WestHeight));

                // Repaint and revalidate the effected components
                east.repaint();
                west.repaint();
                east.revalidate();
                west.revalidate();
            }
        });
    }

    /**
     * frame components initializer
     */
    private void initComponents() {
        // Top panel of the frame
        north = new JPanel();
        // Bottom panel of the frame
        south = new JPanel();
        // Right panel of the frame
        east = new JPanel();
        // Left panel of the frame
        west = new JPanel();
        // Button panel in south panel
        buttons = new JPanel();
        // stats panel in west panel
        stats = new JPanel();

        // Create trade button, named Perform Trade
        JButton trade = new JButton("Perform Trade");
        // Added to the bottom panel
        south.add(trade);

//        // Set trade Btn to default button
//        // Now and hit enter to push button
//        JRootPane rootPane = SwingUtilities.getRootPane(trade);
//        rootPane.setDefaultButton(trade);

        // Add event listener for Trade button
        trade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Checks if the TradeBroker information is complete
                // If it is, it will return all the coins needed for all broker
                ArrayList<String> coinList = checkDTM();
                // If there is not any coin in the list, exit the event
                if (coinList == null) {
                    return;
                }

                // Perform the trading process
                User.getInstance().trade(coinList);

                //  all charts tables from the left panel
                stats.removeAll();
                // Recreate new chats tables and draw them
                DataVisualizationCreator creator = new DataVisualizationCreator();
                creator.createCharts();
            }
        });

        // New table with three column for broker information
        dtm = new DefaultTableModel(new Object[] {"Trading Client", "Coin List", "Strategy Name"}, 1);
        // Add event listener for the table
        dtm.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // When monitored change in the table
                // Three different cases represents three different column change
                switch (e.getColumn()) {
                    // Trading Client, avoid repeating names
                    case 0:
                        // For each row in the table
                        for (int count = 0; count < dtm.getRowCount(); count++) {
                            // If the row number equals to the event triggered row number, or current row have an empty value
                            if (e.getFirstRow() == count ||
                                    dtm.getValueAt(count, 0) == null ||
                                    dtm.getValueAt(count, 0).toString().equals("") ||
                                    dtm.getValueAt(e.getFirstRow(), 0).toString().equals("")) {
                                // Pass this for loop
                                continue;
                            }

                            // If there is an same name, reset the value, and show an error msg
                            if (dtm.getValueAt(count, 0).toString().equals(dtm.getValueAt(e.getFirstRow(), 0).toString())) {
                                showMsg("Name already exists");
                                // Reset value
                                dtm.setValueAt("", e.getFirstRow(), 0);
                                return;
                            }
                        }
                        // Set this broker's name
                        User.getInstance().getBrokerAt(e.getFirstRow()).setName(dtm.getValueAt(e.getFirstRow(), 0).toString());
                        break;
                    case 1:
                        try {
                            // Set this broker's coin list
                            User.getInstance().getBrokerAt(e.getFirstRow()).setCoinList(dtm.getValueAt(e.getFirstRow(), 1).toString());
                        } catch (Exception ex) {
                        }
                        break;
                    case 2:
                        try {
                            // Set this broker's strategy
                            User.getInstance().getBrokerAt(e.getFirstRow()).setStrategy(MainUI.strategyHashMap.get(dtm.getValueAt(e.getFirstRow(), 2).toString()));
                        } catch (Exception ex) {
                        }
                        break;
                    default:
                        break;
                }

            }
        });

        table = new JTable(dtm);

        // New Scroll panel for broker information
        scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
                "Trading Client Actions",
                TitledBorder.CENTER, TitledBorder.TOP));

        // Create a dropdown selector to chose Strategy
        Vector<String> strategyNames = new Vector<>();
        strategyNames.add("Strategy-A");
        strategyNames.add("Strategy-B");
        strategyNames.add("Strategy-C");
        strategyNames.add("Strategy-D");
        // Set to column 2
        TableColumn strategyColumn = table.getColumnModel().getColumn(2);
        JComboBox strategyComboBox = new JComboBox(strategyNames);
        strategyColumn.setCellEditor(new DefaultCellEditor(strategyComboBox));

        // Create add row button
        JButton addRow = new JButton("Add Row");
        // Add event listener
        addRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a row with three column
                dtm.addRow(new String[3]);
                // Add a new broker to the broker list
                User.getInstance().newBroker(dtm.getRowCount() - 1);
            }
        });

        // Create remove row button
        JButton remRow = new JButton("Remove Row");
        // Add event listener
        remRow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gets the row user selects
                int selectedRow = table.getSelectedRow();
                User.getInstance().removeBroker(selectedRow);
                for (int i = selectedRow + 1; i < User.getInstance().getBrokerList().size() - 1; i++) {
                    User.getInstance().getBrokerAt(i).setRowNum(i - 1);
                }
                if (selectedRow != -1)
                    // Remove the row
                    dtm.removeRow(selectedRow);
            }
        });

        // Set scroll panel size
        scrollPane.setPreferredSize(new Dimension(ScrollWidth, ScrollHeight));
        table.setFillsViewportHeight(true);
        // Set layout for right panel
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
        // Add scroll panel to right panel
        east.add(scrollPane);
        // Set layout for button panel
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        // Add addRow and remRow button to button panel
        buttons.add(addRow);
        buttons.add(remRow);
        // Add button panel to right panel
        east.add(buttons);

        // Set left panel size
        west.setPreferredSize(new Dimension(WestWidth, WestHeight));
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        stats.setLayout(new GridLayout(2, 1));
        // Add stats panel to left panel
        west.add(stats);

        // Add four side panel to main frame
        frame.getContentPane().add(north, BorderLayout.NORTH);
        frame.getContentPane().add(east, BorderLayout.EAST);
        frame.getContentPane().add(west, BorderLayout.WEST);
        frame.getContentPane().add(south, BorderLayout.SOUTH);
    }

    /**
     * Checks if all the information required for Broker is there
     * @return The list of coin that the brokers are interested in
     */
    private ArrayList<String> checkDTM() {
        ArrayList<String> coinList = new ArrayList<>();
        // For each row, check if any of them are empty
        for (int count = 0; count < dtm.getRowCount(); count++){
            Object traderObject = dtm.getValueAt(count, 0);
            if (traderObject == null || traderObject.toString().equals("")) {
                JOptionPane.showMessageDialog(frame, "please fill in Trader name on line " + (count + 1) );
                return null;
            }
            String traderName = traderObject.toString();

            Object coinObject = dtm.getValueAt(count, 1);
            if (coinObject == null || coinObject.toString().equals("")) {
                JOptionPane.showMessageDialog(frame, "please fill in cryptocoin list on line " + (count + 1) );
                return null;
            }
            // Split the coins with commas
            String[] coinNames = coinObject.toString().split(",");
            coinList.addAll(List.of(coinNames));

            Object strategyObject = dtm.getValueAt(count, 2);
            if (strategyObject == null) {
                JOptionPane.showMessageDialog(frame, "please fill in strategy name on line " + (count + 1) );
                return null;
            }
            String strategyName = strategyObject.toString();
//            System.out.println(traderName + " " + Arrays.toString(coinNames) + " " + strategyName);
        }
        return coinList;
    }

    /**
     * Add the given component, update the stats panel
     * @param component
     */
    public void updateStats(JComponent component) {
        stats.add(component);
        stats.revalidate();
        stats.repaint();
        west.repaint();
    }

    /**
     * Add the chart panel to stats panel
     * @param component
     */
    public void setChartPanel(JScrollPane component) {
        this.chartPanel = component;
        updateStats(this.chartPanel);
    }

    /**
     * Add the table panel to stats panel
     * @param component
     */
    public void setTablePanel(ChartPanel component) {
        this.tablePanel = component;
        updateStats(this.tablePanel);
    }

    /**
     * Display the window
     */
    public void display() {
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Show a message to the user
     * @param msg
     */
    public void showMsg(String msg) {
        JOptionPane.showMessageDialog(frame, msg);
    }
}
