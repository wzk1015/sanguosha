package sanguosha;

import sanguosha.manager.GameManager;

public class GameLauncher extends Thread {
    private static boolean isGUI = false;

    public static void main(String[] args) {
        GameManager.runGame();
    }

    @Override
    public void run() {
        main(null);
    }

    public GameLauncher(boolean gui) {
        isGUI = gui;
    }

    public static boolean isGraphic() {
        return isGUI;
    }

    public static boolean isCommandLine() {
        return !isGUI;
    }
}
