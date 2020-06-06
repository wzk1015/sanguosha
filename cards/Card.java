package cards;

import manager.Utils;
import people.Person;

public abstract class Card {
    private Color color;
    private int number;
    private Person target;
    private Person source;

    public Card(Color color, int number, Person target) {
        this.color = color;
        this.number = number;
        this.target = target;
    }

    public Card(Color color, int number) {
        this(color, number, null);
    }

    public Color color() {
        return color;
    }

    public int number() {
        return number;
    }

    public boolean isBlack() {
        return color == Color.SPADE || color == Color.CLUB;
    }

    public boolean isRed() {
        return color == Color.HEART || color == Color.DIAMOND;
    }

    public void setSource(Person source) {
        this.source = source;
    }

    public Person getSource() {
        return source;
    }

    public void setTarget(Person target) {
        this.target = target;
    }

    public Person getTarget() {
        return target;
    }

    public abstract Object use();

    public boolean needChooseTarget() {
        return false;
    }

    public abstract String toString();

    public String info() {
        String num;
        if (number == 1) {
            num = "A";
        } else if (number <= 10 && number >= 2) {
            num = number + "";
        } else if (number == 11) {
            num = "J";
        } else if (number == 12) {
            num = "Q";
        } else {
            Utils.assertTrue(number == 13, "wrong number: " + number);
            num = "K";
        }
        String col;
        if (color == Color.DIAMOND) {
            col = "方片";
        } else if (color == Color.HEART) {
            col = "红桃";
        } else if (color == Color.SPADE) {
            col = "黑桃";
        } else if (color == Color.CLUB) {
            col = "梅花";
        } else {
            col = "无色";
        }
        return col + num + " ";
    }
}
