package cards.equipments;

import cards.Color;
import cards.EquipType;
import cards.Equipment;

public abstract class Shield extends Equipment {
    private boolean valid;

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
