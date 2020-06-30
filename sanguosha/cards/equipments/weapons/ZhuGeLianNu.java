package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

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

    @Override
    public String details() {
        return "锁定技，你于出牌阶段内使用【杀】无次数限制。";
    }
}
