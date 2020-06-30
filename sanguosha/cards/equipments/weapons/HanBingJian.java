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
        Card c = getSource().chooseTargetCards(getTarget());
        getTarget().loseCard(c);
        if (!getTarget().getCardsAndEquipments().isEmpty()) {
            c = getSource().chooseTargetCards(getTarget());
            getTarget().loseCard(c);
        }
        return true;
    }

    @Override
    public String toString() {
        return "寒冰剑";
    }

    @Override
    public String details() {
        return "每当你使用【杀】对目标角色造成伤害时，若该角色有牌，你可以防止此伤害，改为依次弃置其两张牌。";
    }
}
