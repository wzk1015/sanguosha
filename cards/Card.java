package cards;

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
        return color.toString() + number + " ";
    }
}
