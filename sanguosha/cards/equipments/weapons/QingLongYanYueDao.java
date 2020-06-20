package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.equipments.Weapon;

public class QingLongYanYueDao extends Weapon {
    public QingLongYanYueDao(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        Sha s = getSource().requestSha();
        if (s != null) {
            s.setTarget(getTarget());
            s.setSource(getSource());
            s.use();
        }
        return null;
    }

    @Override
    public String toString() {
        return "青龙偃月刀";
    }
}
