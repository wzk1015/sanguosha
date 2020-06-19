package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;

public class Tao extends BasicCard {

    public Tao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        getTarget().recover(1);
        return true;
    }

    @Override
    public String toString() {
        return "桃";
    }
}
