package sanguosha.cards.equipments.horses;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

public class ChiTu extends Equipment {

    public ChiTu(Color color, int number) {
        super(color, number, EquipType.minusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "赤兔";
    }
}
