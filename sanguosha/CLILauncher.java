package sanguosha;

import sanguosha.manager.GameManager;

public class CLILauncher extends Thread {
    private static boolean isGUI = false;

    public static void main(String[] args) {
        GameManager.runGame(3);
    }

    @Override
    public void run() {
        main(null);
    }

    public CLILauncher(boolean gui) {
        isGUI = gui;
    }

    public static boolean isGUI() {
        return isGUI;
    }
}
