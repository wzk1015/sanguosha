package cards.equipments;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public abstract class Weapon extends Equipment {
    private final int distance;

    public Weapon(Color color, int number, int distance) {
        super(color, number, EquipType.weapon);
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
