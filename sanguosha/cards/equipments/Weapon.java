package sanguosha.cards.equipments;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

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
