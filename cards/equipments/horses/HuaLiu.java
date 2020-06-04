package cards.equipments.horses;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public class HuaLiu extends Equipment {

    HuaLiu(Color color, int number) {
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
