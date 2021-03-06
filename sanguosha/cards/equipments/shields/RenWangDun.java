package sanguosha.cards.equipments.shields;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Shield;

public class RenWangDun extends Shield {
    public RenWangDun(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "仁王盾";
    }

    @Override
    public String details() {
        return "锁定技，黑色的【杀】对你无效。";
    }
}
