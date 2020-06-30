package gui;

import sanguosha.GameLauncher;
import sanguosha.manager.GameManager;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.Thread.sleep;

public class GraphicRunner {
    private static JTextArea overallStatusArea;
    private static JTextArea playerStatusArea;
    private static JTextArea logArea;
    private static JTextArea ioArea;
    private static JTextField inputField;
    private static JButton okButton;
    private static JButton cancelButton;
    private static JButton helpButton;
    private static JFrame frame;
    private static String input = "";
    private static ByteArrayOutputStream baos;
    private static PrintStream oldOut;
    private static JScrollPane logScroll;
    private static JScrollPane playerStatusScroll;
    private static JScrollPane overallStatusScroll;
    private static JScrollPane ioScroll;
    private static JPanel panel;
    private static GameLauncher gameLauncher;

    public static void run() {

        frame = new JFrame("sanguosha GUI");
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
        placeComponents();
        addActions();

        frame.setVisible(true);
        interact();
    }

    private static void placeComponents() {
        panel.setLayout(null);

        JLabel label1 = new JLabel("全局状态");
        label1.setBounds(40,20,80,25);
        panel.add(label1);

        overallStatusArea = new JTextArea();
        overallStatusArea.setBounds(30,50,300,400);
        overallStatusArea.setEditable(false);
        panel.add(overallStatusArea);

        JLabel label2 = new JLabel("玩家状态");
        label2.setBounds(40,470,80,25);
        panel.add(label2);

        playerStatusArea = new JTextArea();
        playerStatusArea.setBounds(30,500, 300, 150);
        playerStatusArea.setEditable(false);
        panel.add(playerStatusArea);

        JLabel label3 = new JLabel("全局日志");
        label3.setBounds(410,20,80,25);
        panel.add(label3);

        logArea = new JTextArea();
        logArea.setBounds(400,50,500,200);
        logArea.setEditable(false);
        logArea.setForeground(Color.BLACK);
        panel.add(logArea);

        JLabel label4 = new JLabel("IO交互");
        label4.setBounds(410,270,80,25);
        panel.add(label4);

        ioArea = new JTextArea();
        ioArea.setBounds(400,300, 500, 200);
        ioArea.setEditable(false);
        panel.add(ioArea);

        JLabel label5 = new JLabel("输入区");
        label5.setBounds(410,520,80,25);
        panel.add(label5);

        inputField = new JTextField(20);
        inputField.setBounds(400,550, 500, 50);
        panel.add(inputField);

        okButton = new JButton("OK");
        okButton.setBounds(820, 610, 80, 40);
        panel.add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(720, 610, 80, 40);
        panel.add(cancelButton);

        JLabel label6 = new JLabel("powered by wzk");
        label6.setBounds(550,620,160,25);
        panel.add(label6);

        helpButton = new JButton("Help");
        helpButton.setBounds(400, 610, 80, 40);
        panel.add(helpButton);

        logScroll = newScroll(logArea);
        playerStatusScroll = newScroll(playerStatusArea);
        overallStatusScroll = newScroll(overallStatusArea);
        ioScroll = newScroll(ioArea);
    }

    private static JScrollPane newScroll(Component e) {
        JScrollPane scroll = new JScrollPane(e);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setSize(e.getSize());
        scroll.setBounds(e.getBounds());
        panel.add(scroll);
        return scroll;
    }

    private static void addActions() {
        okButton.addActionListener(e -> inputString(inputField.getText()));
        cancelButton.addActionListener(e -> inputString("q"));
        helpButton.addActionListener(e -> inputString("help"));
        inputField.addActionListener(e -> inputString(inputField.getText()));
    }

    private static void inputString(String s) {
        input = s;
        inputField.setText("");
    }

    private static void debug(String s) {
        if (false) {
            PrintStream ps = System.out;
            System.setOut(oldOut);
            System.out.println(s);
            System.setOut(ps);
        }
    }

    private static void interact() {
        baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        oldOut = System.out;
        System.setOut(ps);

        gameLauncher = new GameLauncher(true);
        try {
            gameLauncher.start();
        } catch (Exception e) {
            e.printStackTrace();
            logArea.append("GAME ERROR!\n\n");
        }

        while (true) {
            logArea.setText(baos.toString());
            logArea.setCaretPosition(logArea.getText().length());
            playerStatusArea.setText(GameManager.getCurrentPlayerStatus());
            overallStatusArea.setText(GameManager.getOverallStatus());
            ioArea.setText(GameManager.getCurrentIOrequest());
            ioArea.setCaretPosition(ioArea.getText().length());
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                logArea.append("GUI ERROR!\n\n");
            }
        }
    }

    public static String getInput() {
        debug("start waiting");
        while (input.isEmpty()) {
            try {
                sleep(100);
                //debug("oh");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        String a = input;
        input = "";
        debug("get input: " + a);
        GameManager.clearCurrentIOrequest();
        return a;
    }
}
