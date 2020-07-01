package gui;

import sanguosha.GameLauncher;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.manager.GameManager;
import sanguosha.people.Identity;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

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
    private static JFrame gameFrame;
    private static JFrame startFrame;
    private static String input = "";
    private static PrintStream oldOut;
    private static boolean gameBegin = false;
    private static JComboBox<String> comboBoxNumPlayers;
    private static JComboBox<String> comboBoxOptionsPerPerson;
    private static JComboBox<String> comboBoxMinister;
    private static JComboBox<String> comboBoxTraitor;
    private static JComboBox<String> comboBoxRebel;
    private static JCheckBox checkBoxFeng;
    private static JCheckBox checkBoxHuo;
    private static JCheckBox checkBoxLin;
    private static JCheckBox checkBoxShan;
    private static JCheckBox checkBoxShen;
    private static JRadioButton radioButtonDefault;
    private static JRadioButton radioButtonCustomize;
    private static JLabel startWarn;
    private static final Color bgColor = Color.decode("#E7E9EE");

    public static void run() {
        setUpStartFrame();
        startFrame.setVisible(false);
        setUpGameFrame();
        addActions();
        gameFrame.setVisible(true);
        interact();
    }

    private static JLabel makeLabel(String name, JPanel panel) {
        JLabel label = new JLabel(name);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(label);
        return label;
    }

    private static void makeLabel(String name, int x, int y, int width, int height, JPanel panel) {
        JLabel label = new JLabel(name);
        label.setBounds(x, y, width, height);
        panel.add(label);
        label.setForeground(Color.white);
    }

    private static JComboBox<String> makeComboBox(int min, int max, JPanel panel, boolean enabled) {
        JComboBox<String> comboBox = new JComboBox<>();
        for (int i = min; i <= max; i++) {
            comboBox.addItem(i + "");
        }
        panel.add(comboBox);
        comboBox.setEnabled(enabled);
        return comboBox;
    }

    private static JComboBox<String> makeComboBox(int min, int max, JPanel panel) {
        return makeComboBox(min, max, panel, true);
    }
    
    private static void makeScroll(Component e, JPanel panel) {
        JScrollPane scroll = new JScrollPane(e);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setSize(e.getSize());
        scroll.setBounds(e.getBounds());
        panel.add(scroll);
    }

    private static JTextArea makeTextArea(int x, int y, int w, int h, JPanel panel) {
        JTextArea area = new JTextArea();
        area.setBounds(x, y, w, h);
        area.setEditable(false);
        area.setBackground(bgColor);
        area.setLineWrap(true);
        panel.add(area);
        return area;
    }

    private static JPanel makeOptionsPanel() {
        GridLayout gridLayout = new GridLayout(9, 2);
        gridLayout.setHgap(5);
        gridLayout.setVgap(5);
        JPanel optionsPanel = new JPanel(gridLayout);

        makeLabel("",optionsPanel);
        makeLabel("",optionsPanel);

        makeLabel("玩家人数", optionsPanel);
        comboBoxNumPlayers = makeComboBox(2, 10, optionsPanel);

        makeLabel("配置选项", optionsPanel);
        JPanel subPanel = new JPanel(new GridLayout(1, 2));
        radioButtonDefault = new JRadioButton("default", true);
        subPanel.add(radioButtonDefault);
        radioButtonCustomize = new JRadioButton("customize", false);
        subPanel.add(radioButtonCustomize);
        ButtonGroup group = new ButtonGroup();
        group.add(radioButtonDefault);
        group.add(radioButtonCustomize);
        optionsPanel.add(subPanel);

        makeLabel("扩展包", optionsPanel);
        JPanel subPanel2 = new JPanel(new GridLayout(1, 5));
        JCheckBox checkBox = new JCheckBox("标", true);
        checkBox.setEnabled(false);
        subPanel2.add(checkBox);
        checkBoxFeng = new JCheckBox("风");
        checkBoxHuo = new JCheckBox("火");
        checkBoxLin = new JCheckBox("林");
        checkBoxShan = new JCheckBox("山");
        checkBoxShen = new JCheckBox("神");
        subPanel2.add(checkBoxFeng);
        subPanel2.add(checkBoxHuo);
        subPanel2.add(checkBoxLin);
        subPanel2.add(checkBoxShan);
        subPanel2.add(checkBoxShen);
        optionsPanel.add(subPanel2);

        makeLabel("每人可选武将数", optionsPanel);
        comboBoxOptionsPerPerson = makeComboBox(2, 10, optionsPanel);
        makeLabel("主公数", optionsPanel);
        makeComboBox(1, 1, optionsPanel, false);
        makeLabel("忠臣数", optionsPanel);
        comboBoxMinister = makeComboBox(0, 4, optionsPanel);
        makeLabel("内奸数", optionsPanel);
        comboBoxTraitor = makeComboBox(0, 4, optionsPanel);
        makeLabel("反贼数", optionsPanel);
        comboBoxRebel = makeComboBox(1, 4, optionsPanel);
        return optionsPanel;
    }

    private static void setUpStartFrame() {
        startFrame = new JFrame("sanguosha GUI");
        startFrame.setSize(550, 700);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setLayout(new GridLayout(1, 1));
        startFrame.setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        startFrame.add(mainPanel);

        JPanel imagePanel = new JPanel();
        imagePanel.add(new JLabel(new ImageIcon("src/images/start.jpg")));
        mainPanel.add(imagePanel, BorderLayout.NORTH);

        mainPanel.add(makeOptionsPanel(), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("OK");
        startWarn = makeLabel("", buttonPanel);
        buttonPanel.add(startButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        startButton.addActionListener(e -> startAction());

        startFrame.setVisible(true);
        while (!gameBegin) {
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            comboBoxOptionsPerPerson.setEnabled(radioButtonCustomize.isSelected());
            comboBoxMinister.setEnabled(radioButtonCustomize.isSelected());
            comboBoxTraitor.setEnabled(radioButtonCustomize.isSelected());
            comboBoxRebel.setEnabled(radioButtonCustomize.isSelected());
            checkBoxFeng.setEnabled(radioButtonCustomize.isSelected());
            checkBoxHuo.setEnabled(radioButtonCustomize.isSelected());
            checkBoxLin.setEnabled(radioButtonCustomize.isSelected());
            checkBoxShan.setEnabled(radioButtonCustomize.isSelected());
            checkBoxShen.setEnabled(radioButtonCustomize.isSelected());

            if (radioButtonDefault.isSelected() && comboBoxNumPlayers.getSelectedItem() != null) {
                int numPlayers = Integer.parseInt((String) comboBoxNumPlayers.getSelectedItem());
                comboBoxOptionsPerPerson.setSelectedItem("5");
                comboBoxMinister.setSelectedItem("" + PeoplePool.defaultMinister(numPlayers));
                comboBoxTraitor.setSelectedItem("" + PeoplePool.defaultTraitor(numPlayers));
                comboBoxRebel.setSelectedItem("" + PeoplePool.defaultRebel(numPlayers));
                checkBoxFeng.setSelected(false);
                checkBoxHuo.setSelected(false);
                checkBoxLin.setSelected(false);
                checkBoxShan.setSelected(false);
                checkBoxShen.setSelected(false);
            }
        }
    }

    private static void startAction() {
        PeoplePool.setOptionsPerPerson(Integer.parseInt(
                (String) Objects.requireNonNull(comboBoxOptionsPerPerson.getSelectedItem())));
        GameManager.setNumPlayers(Integer.parseInt(
                (String) Objects.requireNonNull(comboBoxNumPlayers.getSelectedItem())));
        PeoplePool.setNumPlayers(GameManager.getNumPlayers());
        PeoplePool.addStandard();
        if (checkBoxFeng.isSelected()) {
            PeoplePool.addFeng();
        } if (checkBoxHuo.isSelected()) {
            PeoplePool.addHuo();
        } if (checkBoxLin.isSelected()) {
            PeoplePool.addLin();
        } if (checkBoxShan.isSelected()) {
            PeoplePool.addShan();
        } if (checkBoxShen.isSelected()) {
            PeoplePool.addGod();
        }
        PeoplePool.addIdentity(Identity.KING, 1);
        PeoplePool.addIdentity(Identity.MINISTER, Integer.parseInt(
                (String) Objects.requireNonNull(comboBoxMinister.getSelectedItem())));
        PeoplePool.addIdentity(Identity.TRAITOR, Integer.parseInt(
                (String) Objects.requireNonNull(comboBoxTraitor.getSelectedItem())));
        PeoplePool.addIdentity(Identity.REBEL, Integer.parseInt(
                (String) Objects.requireNonNull(comboBoxRebel.getSelectedItem())));
        if (PeoplePool.illegalOptionsPerPerson()) {
            startWarn.setText("Too many options! try less options or more extended packages");
            PeoplePool.restart();
            return;
        }
        if (PeoplePool.illegalIdentityNumber()) {
            startWarn.setText("Sum of identities not consistent with number of players");
            PeoplePool.restart();
            return;
        }
        gameBegin = true;
    }

    private static void setUpGameFrame() {
        gameFrame = new JFrame("sanguosha GUI");
        gameFrame.setSize(930, 720);
        gameFrame.setResizable(false);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel gamePanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                ImageIcon icon = new ImageIcon("src/images/bg.jpg");
                g.drawImage(icon.getImage(), 0, 0, 930, 720, icon.getImageObserver());
            }
        };
        gameFrame.add(gamePanel);

        gamePanel.setLayout(null);

        makeLabel("全局状态", 40, 50, 80, 25, gamePanel);
        overallStatusArea = makeTextArea(30, 80, 300, 400, gamePanel);

        makeLabel("玩家状态", 40, 500, 80, 25, gamePanel);
        playerStatusArea = makeTextArea(30, 530, 300, 150, gamePanel);

        makeLabel("全局日志", 410, 50, 80, 25, gamePanel);
        logArea = makeTextArea(400, 80, 500, 200, gamePanel);

        makeLabel("IO交互", 410, 300, 80, 25, gamePanel);
        ioArea = makeTextArea(400, 330, 500, 200, gamePanel);

        makeLabel("输入区", 410, 550, 80, 25, gamePanel);
        inputField = new JTextField(20);
        inputField.setBounds(400, 580, 500, 50);
        inputField.setBackground(bgColor);
        gamePanel.add(inputField);

        okButton = new JButton("OK");
        okButton.setBounds(820, 640, 80, 40);
        okButton.setBackground(bgColor);
        gamePanel.add(okButton);

        cancelButton = new JButton("Cancel");
        cancelButton.setBounds(720, 640, 80, 40);
        cancelButton.setBackground(bgColor);
        gamePanel.add(cancelButton);

        makeLabel("powered by wzk", 550, 650, 160, 25, gamePanel);

        helpButton = new JButton("Help");
        helpButton.setBounds(400, 640, 80, 40);
        helpButton.setBackground(bgColor);
        gamePanel.add(helpButton);

        makeScroll(logArea, gamePanel);
        makeScroll(playerStatusArea, gamePanel);
        makeScroll(overallStatusArea, gamePanel);
        makeScroll(ioArea, gamePanel);
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

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        oldOut = System.out;
        System.setOut(ps);
        GameLauncher gameLauncher = new GameLauncher(true);
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
        }
        ;
        String a = input;
        input = "";
        debug("get input: " + a);
        GameManager.clearCurrentIOrequest();
        return a;
    }
}
