package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;

public class Jiu extends BasicCard {
    public Jiu(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        getTarget().setDrunk(true);
        return null;
    }

    @Override
    public String toString() {
        return "酒";
    }
}
