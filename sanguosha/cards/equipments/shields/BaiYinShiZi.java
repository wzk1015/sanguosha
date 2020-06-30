package sanguosha.cards.equipments.shields;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Shield;

public class BaiYinShiZi extends Shield {
    public BaiYinShiZi(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "白银狮子";
    }

    @Override
    public String details() {
        return "锁定技，每当你受到多于1点的伤害时，你防止多余的伤害；锁定技，每当你失去装备区里的【白银狮子】时，你回复1点体力。";
    }
}
