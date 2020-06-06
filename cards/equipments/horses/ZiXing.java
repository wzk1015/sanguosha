package cards.equipments.horses;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public class ZiXing extends Equipment {

    public ZiXing(Color color, int number) {
        super(color, number, EquipType.minusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "紫骍";
    }
}
