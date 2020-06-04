package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

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
}
