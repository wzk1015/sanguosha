package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

public class QingLongYanYueDao extends Weapon {
    public QingLongYanYueDao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "青龙偃月刀";
    }
}
