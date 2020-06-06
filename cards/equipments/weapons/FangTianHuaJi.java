package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

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
