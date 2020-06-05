package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

public class SanJianLiangRenDao extends Weapon {
    public SanJianLiangRenDao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "三尖两刃刀";
    }
}
