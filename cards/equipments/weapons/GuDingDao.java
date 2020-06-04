package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

public class GuDingDao extends Weapon {

    public GuDingDao(Color color, int number) {
        super(color, number, 2);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "古锭刀";
    }
}
