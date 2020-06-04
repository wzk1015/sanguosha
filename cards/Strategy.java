package cards;

import manager.GameManager;

public abstract class Strategy extends Card {
    private int distance;

    public Strategy(Color color, int number, int distance) {
        super(color, number);
        this.distance = distance;
    }

    public Strategy(Color color, int number) {
        super(color, number);
        this.distance = 100;
    }

    public boolean gotWuXie() {
        return GameManager.requestWuXie();
    }

    public int getDistance() {
        return distance;
    }
}
