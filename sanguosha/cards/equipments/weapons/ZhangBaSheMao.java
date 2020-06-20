package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.equipments.Weapon;

import java.util.ArrayList;

public class ZhangBaSheMao extends Weapon {

    public ZhangBaSheMao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        Card card;
        ArrayList<Card> cs = getSource().chooseCards(2, getSource().getCards());
        getSource().loseCard(cs);
        if (cs.get(0).isRed() && cs.get(1).isRed()) {
            card = new Sha(Color.DIAMOND, 0);
        } else if (cs.get(1).isBlack() && cs.get(1).isBlack()) {
            card = new Sha(Color.CLUB, 0);
        } else {
            card = new Sha(Color.NOCOLOR, 0);
        }
        card.setTaken(true);
        card.setThisCard(cs);
        return card;
    }

    @Override
    public String toString() {
        return "丈八蛇矛";
    }
}
