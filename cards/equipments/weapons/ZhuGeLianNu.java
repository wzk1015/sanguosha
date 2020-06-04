package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

public class ZhuGeLianNu extends Weapon {
    public ZhuGeLianNu(Color color, int number) {
        super(color, number, 1);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "诸葛连弩";
    }
}
