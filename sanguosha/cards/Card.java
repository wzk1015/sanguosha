package sanguosha.cards;

import sanguosha.manager.IO;
import sanguosha.manager.Utils;
import sanguosha.people.Person;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Card implements Serializable {
    private final Color color;
    private final int number;
    private Person target;
    private Person source;
    private boolean isTaken = false;
    private Person owner = null;
    private ArrayList<Card> thisCard = new ArrayList<>();

    public void setThisCard(ArrayList<Card> thisCard) {
        this.thisCard = thisCard;
    }

    public void setThisCard(Card thisCard) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(thisCard);
        this.thisCard = cs;
    }

    public ArrayList<Card> getThisCard() {
        return thisCard;
    }

    public Card(Color color, int number) {
        this.color = color;
        this.number = number;
        this.thisCard.add(this);
    }

    public Color color() {
        if (owner != null && owner.hasHongYan() && color == Color.SPADE) {
            IO.println(owner + " uses 红颜");
            return Color.HEART;
        }
        return color;
    }

    public int number() {
        return number;
    }

    public boolean isBlack() {
        return color() == Color.SPADE || color() == Color.CLUB;
    }

    public boolean isRed() {
        return color() == Color.HEART || color() == Color.DIAMOND;
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
        if (number == 0) {
            num = "-";
        } else if (number == 1) {
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
        if (color() == Color.DIAMOND) {
            col = "方片";
        } else if (color() == Color.HEART) {
            col = "红桃";
        } else if (color() == Color.SPADE) {
            col = "黑桃";
        } else if (color() == Color.CLUB) {
            col = "梅花";
        } else {
            col = "无色";
        }
        return col + num + " ";
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }

    public boolean isNotTaken() {
        return !isTaken;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getOwner() {
        return owner;
    }

    public Person selectTarget(Person user) {
        source = user;
        target = user.selectPlayer(false);
        return target;
    }

    public boolean askTarget(Person user) {
        setSource(user);
        if (!this.needChooseTarget()) {
            setTarget(user);
            return true;
        }

        return selectTarget(user) != null;
    }

    public abstract String help();
}
