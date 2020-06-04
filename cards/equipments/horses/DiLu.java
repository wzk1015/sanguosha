package cards.equipments.horses;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public class DiLu extends Equipment {

    DiLu(Color color, int number) {
        super(color, number, EquipType.plusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "的卢";
    }
}
