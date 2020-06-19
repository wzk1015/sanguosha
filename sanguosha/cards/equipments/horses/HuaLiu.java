package sanguosha.cards.equipments.horses;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

public class HuaLiu extends Equipment {

    public HuaLiu(Color color, int number) {
        super(color, number, EquipType.plusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "骅骝";
    }
}
