package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

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
