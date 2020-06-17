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

    public static boolean noWuXie() {
        boolean hasWuXie = false;
        for (Person p : GameManager.getPlayers()) {
            for (Card c : p.getCards()) {
                if (c.toString().equals("无懈可击")) {
                    hasWuXie = true;
                    break;
                }
            }
        }
        return !hasWuXie;
    }

    public boolean gotWuXie(Person target) {
        if (noWuXie()) {
            return false;
        }
        if (target != null) {
            IO.println("requesting 无懈 for " + this + " towards " + target);
        }
        boolean ans = false;
        while (true) {
            boolean mark = false;
            for (Person p : GameManager.getPlayers()) {
                if (p.requestWuXie()) {
                    mark = true;
                    ans = !ans;
                    if (noWuXie()) {
                        return ans;
                    }
                    break;
                }
            }
            if (mark) {
                IO.println("requesting 无懈 for 无懈可击");
                continue;
            }
            return ans;
        }
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
