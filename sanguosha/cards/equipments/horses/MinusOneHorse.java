package sanguosha.cards.equipments.horses;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

public abstract class MinusOneHorse extends Equipment {
    public MinusOneHorse(Color color, int number) {
        super(color, number, EquipType.minusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String help() {
        return "锁定技，你与其他角色的距离-1。（可以理解为一种进攻上的优势）" +
                "不同名称的-1坐骑，其效果是相同的。\n\n" + super.help();
    }
}
