package sanguosha.cards.equipments;

import sanguosha.cards.Color;
import sanguosha.cards.EquipType;
import sanguosha.cards.Equipment;

public abstract class Shield extends Equipment {
    private boolean valid = true;

    public Shield(Color color, int number) {
        super(color, number, EquipType.shield);
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
