package gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import reader.Reader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>This class represents the login process.</p>
 * <p>It handles the login request and verifies the data with database {@link Reader}</p>
 */
public class LoginForm {
    /**
     * A variable representing the main frame
     */
    private JFrame frame;
    /**
     * A variable representing the main panel in the main frame
     */
    private JPanel panel;
    /**
     * A const value containing the Window Width
     */
    private static final int WindowWidth  = 290;
    /**
     * A const value containing the Window Height
     */
    private static final int WindowHeight = 160;
    /**
     * A variable representing the text field for username input
     */
    private JTextField user_TEXT;
    /**
     * A variable representing the text field for password input
     */
    private JPasswordField pass_TEXT;
    /**
     * A variable representing the {@link Reader} Object
     */
    private Reader reader;
    /**
     * <p>A flag represents the login state</p>
     * <p><b>True</b>: Login Success</p>
     * <p><b>False</b>: Login Pending</p>
     */
    private Boolean loginFlag;

    /**
     * {@link LoginForm} class initializer
     */
    public LoginForm() {
        // Create Reader Object
        reader = new Reader();
        // Init Flag to false, meaning pending
        loginFlag = false;
        // Initialize window frame
        initFrame();
        // Initialize window components, such as buttons, chars
        initComponents();
        // Render window and display
        renderFrame();
    }

    /**
     * Initialize window frame object, and set all the properties
     * @see JFrame
     */
    private void initFrame() {
        // Create JFrame Object with window name Login
        frame = new JFrame("Login");
        // Get screen size
        Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        // Set window location to the middle of the screen
        // Formula: (screen size - window - size) / 2
        frame.setLocation((ScreenDimension.width - WindowWidth) / 2, (ScreenDimension.height - WindowHeight) / 2);
        // Set window size to const window size
        frame.setPreferredSize(new Dimension(WindowWidth, WindowHeight));
        // Set window to un-resizeable
        frame.setResizable(false);
        // Set program to end on EXIT event
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create ImageIcon Object, and set window icon to "icon\crypto.png"
        ImageIcon img = new ImageIcon("icon\\crypto.png");
        frame.setIconImage(img.getImage());

        // Create JPanel Object and add it to main frame
        panel = new JPanel();
        frame.add(panel);
    }

    /**
     * Initialize window components
     * @see JPanel
     * @see JComponent
     */
    private void initComponents() {
        // Set panel to no layout
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

        // Set Login Btn to default button
        // Now and hit enter to push button
        JRootPane rootPane = SwingUtilities.getRootPane(login_BTN);
        rootPane.setDefaultButton(login_BTN);

        // Add event listener to Login Button
        login_BTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // verifies username and password
                if (login(getUsername(), getPassword())) {
                    // Set flag to true, login success
                    loginFlag = true;
                    // Dispose main frame
                    frame.dispose();
                } else {
                    // Show error message for failed login
                    JOptionPane.showMessageDialog(frame, "Login Failed: Your username or password is incorrect");
                    // Dispose main frame
                    frame.dispose();
                    // Close program
                    System.exit(0);

                    // Comment Jump to end of file, #1
                }
            }
        });
    }

    /**
     * Login verification
     * @param username {@link String} username
     * @param password {@link String} password
     * @return <P>{@link Boolean}</P><p>True: verification success</p> <p>False: verification failed</p>
     */
    private Boolean login(String username, String password) {
        // Call getter method to get JsonArray of username and password
        JsonArray array = reader.getUserList();
        // For each element in array
        for (int i = 0; i < array.size(); i++) {
            JsonObject object = array.get(i).getAsJsonObject();
            // If username and password matched up return true
            if (object.get("username").getAsString().equals(username) && object.get("password").getAsString().equals(password)) {
                return true;
            }
        }
        // If none matched up return false
        return false;
    }

    /**
     * Render and display window
     */
    private void renderFrame() {
        // Display
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Getter method for loginFlag
     * @return loginFlag
     */
    public Boolean getLoginFlag() {
        return loginFlag;
    }

    /**
     * Getter method for username
     * @return
     */
    private String getUsername() {
        return user_TEXT.getText();
    }

    /**
     * Getter method for password
     * @return
     */
    private String getPassword() {
        return String.valueOf(pass_TEXT.getPassword());
    }
}

// Comment Jump #1
// Second approach for the Error Message
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
