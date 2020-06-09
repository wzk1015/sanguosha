package cards;

import manager.GameManager;
import manager.IO;
import people.Person;

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
        return GameManager.askWuXie(this);
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public void setSource(Person source) {
        super.setSource(source);
        if (source.hasQiCai()) {
            distance = 100;
        }
    }
}
