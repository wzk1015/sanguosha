package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class GuanShiFu extends Weapon {
    public GuanShiFu(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "贯石斧";
    }
}
