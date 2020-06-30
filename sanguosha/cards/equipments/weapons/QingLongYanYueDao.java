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

    @Override
    public String details() {
        return "每当你使用的【杀】被目标角色使用的【闪】抵消时，你可以对其使用一张【杀】（无距离限制）。\n" +
                "如果有足够的【杀】，可以一直追杀下去，直到目标角色不使用【闪】或使用者无【杀】为止。";
    }
}
