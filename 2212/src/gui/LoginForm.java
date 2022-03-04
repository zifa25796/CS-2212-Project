package gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import reader.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm {
    private JFrame frame;
    private JPanel panel;
    private static final int WindowWidth  = 290;
    private static final int WindowHeight = 160;
    private JTextField user_TEXT;
    private JPasswordField pass_TEXT;
    private Reader reader;
    private Boolean loginFlag;

    public LoginForm() {
        reader = new Reader();
        loginFlag = false;
        initFrame();
        initComponents();
        renderFrame();
    }

    private void initFrame() {
        // New Window
        frame = new JFrame("Login");
        Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((ScreenDimension.width - WindowWidth) / 2, (ScreenDimension.height - WindowHeight) / 2);
        frame.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
    }

    private void initComponents() {
        panel.setLayout(null);

        // Username Label
        JLabel user_LABEL = new JLabel("Username: ");
        user_LABEL.setBounds(10, 20, 80, 25);
        panel.add(user_LABEL);

        // Username Input
        user_TEXT = new JTextField(20);
        user_TEXT.setBounds(100, 20, 165, 25);
        panel.add(user_TEXT);

        // Password Label
        JLabel pass_LABEL = new JLabel("Password: ");
        pass_LABEL.setBounds(10, 50, 80, 25);
        panel.add(pass_LABEL);

        // Password Input
        pass_TEXT = new JPasswordField(20);
        pass_TEXT.setBounds(100, 50, 165, 25);
        panel.add(pass_TEXT);

        // Login Btn
        JButton login_BTN = new JButton("login");
        login_BTN.setBounds((WindowWidth - 80)/2, 85, 80, 25);
        panel.add(login_BTN);

        JRootPane rootPane = SwingUtilities.getRootPane(login_BTN);
        rootPane.setDefaultButton(login_BTN);

        login_BTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login(getUsername(), getPassword())) {
                    loginFlag = true;
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "Login Failed: Your username or password is incorrect");
                    frame.dispose();
                    System.exit(0);
//                    int errorWndWidth = 250;
//                    int errorWndHeight = 120;
//                    JFrame errorFrame = new JFrame("Login Failed");
//                    Dimension errorDimension = new Dimension(errorWndWidth, errorWndHeight);
//                    Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
//                    errorFrame.setLocation((ScreenDimension.width - errorWndWidth) / 2, (ScreenDimension.height - errorWndHeight) / 2);
//                    errorFrame.setPreferredSize(errorDimension);
//                    errorFrame.setResizable(false);
//                    errorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//                    JPanel errorPanel = new JPanel();
//                    errorFrame.add(errorPanel);
//
//                    JLabel failed_LABEL = new JLabel("Login Failed: ");
//                    failed_LABEL.setBounds((errorWndWidth - 80) / 2, 20, 80, 50);
//                    errorPanel.add(failed_LABEL);
//
//                    JLabel msg_LABEL = new JLabel("Your user ID or password is incorrect");
//                    msg_LABEL.setBounds((errorWndWidth - 80) / 2, 75, 80, 50);
//                    errorPanel.add(msg_LABEL);
//
//                    JButton exit_BTN = new JButton("OK");
//                    exit_BTN.setBounds((errorWndWidth - 40) / 2, 130, 40, 30);
//                    errorPanel.add(exit_BTN);
//
//                    JRootPane errorRootPane = SwingUtilities.getRootPane(exit_BTN);
//                    errorRootPane.setDefaultButton(exit_BTN);
//
//                    exit_BTN.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent e) {
//                            errorFrame.dispose();
//                            frame.dispose();
//                            System.exit(0);
//                        }
//                    });
//
//                    errorFrame.pack();
//                    errorFrame.setVisible(true);
                }
            }
        });
    }

    private Boolean login(String username, String password) {
        JsonArray array = reader.getUserList();
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            if (object.get("username").getAsString().equals(username) && object.get("password").getAsString().equals(password)) {
                return true;
            }
        }
        return false;
    }

    public Boolean getLoginFlag() {
        return loginFlag;
    }

    private void renderFrame() {
        // Display
        frame.pack();
        frame.setVisible(true);
    }

    private String getUsername() {
        return user_TEXT.getText();
    }

    private String getPassword() {
        return String.valueOf(pass_TEXT.getPassword());
    }


}
