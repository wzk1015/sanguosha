package cards.equipments.weapons;

import cards.Color;
import cards.equipments.Weapon;

public class QiLinGong extends Weapon {

    public QiLinGong(Color color, int number) {
        super(color, number, 5);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "麒麟弓";
    }
}
