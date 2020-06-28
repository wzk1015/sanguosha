package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class GuanShiFu extends Weapon {
    public GuanShiFu(Color color, int number) {
        super(color, number, 3);
    }

    @Override
    public Object use() {
        if (getSource().getCardsAndEquipments().size() < 3) {
            getSource().printlnToIO("you don't have enough cards");
        }
        String option = getSource().chooseNoNull(
                "throw two cards and hurt", "pass");
        if (option.equals("throw two cards and hurt")) {
            getSource().throwCard(getSource().chooseCards(2,
                    getTarget().getCardsAndEquipments()));
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "贯石斧";
    }
}
