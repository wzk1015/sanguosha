package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class ZhuQueYuShan extends Weapon {
    public ZhuQueYuShan(Color color, int number) {
        super(color, number, 4);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "朱雀羽扇";
    }

    @Override
    public String details() {
        return "你可以将一张普通【杀】当火【杀】使用。";
    }
}
