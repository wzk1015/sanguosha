package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

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

    @Override
    public String details() {
        return "锁定技，每当你使用【杀】对目标角色造成伤害时，若该角色没有手牌，此伤害+1。";
    }
}
