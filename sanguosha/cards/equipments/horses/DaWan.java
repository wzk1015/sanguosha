package sanguosha.cards.equipments.horses;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

public class DaWan extends Equipment {

    public DaWan(Color color, int number) {
        super(color, number, EquipType.minusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "大宛";
    }
}
