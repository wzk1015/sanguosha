package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class ZhangBaSheMao extends Weapon {

    public ZhangBaSheMao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "丈八蛇矛";
    }
}
