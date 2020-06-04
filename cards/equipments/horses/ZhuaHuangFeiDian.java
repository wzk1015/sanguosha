package cards.equipments.horses;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public class ZhuaHuangFeiDian extends Equipment {

    ZhuaHuangFeiDian(Color color, int number) {
        super(color, number, EquipType.plusOneHorse);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "爪黄飞电";
    }
}
