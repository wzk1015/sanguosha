package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class HanBingJian extends Weapon {
    public HanBingJian(Color color, int number) {
        super(color, number, 2);
    }

    @Override
    public Object use() {
        if (getTarget().getCardsAndEquipments().isEmpty()) {
            return false;
        }
        Card c = getSource().chooseTargetCardsAndEquipments(getTarget());
        getTarget().loseCard(c);
        if (!getTarget().getCardsAndEquipments().isEmpty()) {
            c = getSource().chooseTargetCardsAndEquipments(getTarget());
            getTarget().loseCard(c);
        }
        return true;
    }

    @Override
    public String toString() {
        return "寒冰剑";
    }
}
