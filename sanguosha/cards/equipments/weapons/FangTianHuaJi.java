package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class FangTianHuaJi extends Weapon {
    public FangTianHuaJi(Color color, int number) {
        super(color, number, 4);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "方天画戟";
    }
}
